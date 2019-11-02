package com.github.isuperred.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.isuperred.R;
import com.github.isuperred.adapter.ContentViewPagerAdapter;
import com.github.isuperred.base.BaseActivity;
import com.github.isuperred.bean.Title;
import com.github.isuperred.fragment.ContentFragment;
import com.github.isuperred.presenter.TitlePresenter;
import com.github.isuperred.utils.Constants;
import com.github.isuperred.utils.LocalJsonResolutionUtil;
import com.github.isuperred.widgets.ScaleTextView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends BaseActivity implements ContentFragment.OnFragmentInteractionListener,
        ViewTreeObserver.OnGlobalFocusChangeListener, View.OnKeyListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int MSG_NOTIFY_TITLE = 100;

    private TextView mOldTitle;
    private ImageView mIvNetwork;
    private ArrayObjectAdapter mArrayObjectAdapter;
    private ContentViewPagerAdapter mViewPagerAdapter;

    private int mCurrentPageIndex = 0;
    private boolean isSkipTabFromViewPager = false;
    private NetworkChangeReceiver networkChangeReceiver;

    public ArrayObjectAdapter getArrayObjectAdapter() {
        return mArrayObjectAdapter;
    }

    public HorizontalGridView getHorizontalGridView() {
        return mHorizontalGridView;
    }

    private Handler mHandler = new MyHandler(this);


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e(TAG, "onFragmentInteraction: " + uri);

        switch (uri.toString()) {
            case Constants.URI_HIDE_TITLE:
                handleTitleVisible(false);
                break;
            case Constants.URI_SHOW_TITLE:
                handleTitleVisible(true);
                break;
        }
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        Log.e(TAG, "onGlobalFocusChanged newFocus: " + newFocus);
        Log.e(TAG, "onGlobalFocusChanged oldFocus: " + oldFocus);
        if (newFocus == null || oldFocus == null) {
            return;
        }
        if (newFocus.getId() == R.id.tv_main_title
                && oldFocus.getId() == R.id.tv_main_title) {
            ((TextView) newFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) newFocus).getPaint().setFakeBoldText(true);
            ((TextView) oldFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) oldFocus).getPaint().setFakeBoldText(false);
        } else if (newFocus.getId() == R.id.tv_main_title
                && oldFocus.getId() != R.id.tv_main_title) {
            ((TextView) newFocus).setTextColor(getResources().getColor(R.color.colorWhite));
            ((TextView) newFocus).getPaint().setFakeBoldText(true);
        } else if (newFocus.getId() != R.id.tv_main_title
                && oldFocus.getId() == R.id.tv_main_title) {
            ((TextView) oldFocus).setTextColor(getResources().getColor(R.color.colorBlue));
            ((TextView) oldFocus).getPaint().setFakeBoldText(true);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_BACK) {
//            isPressBack = true;
            switch (v.getId()) {
                case R.id.cl_search:
                case R.id.cl_history:
                case R.id.cl_login:
                case R.id.cl_open_vip:
                case R.id.tv_ad:
                    if (mHorizontalGridView != null) {
                        mHorizontalGridView.requestFocus();
                    }
                    return true;
                default:
                    break;
            }

        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_search:
                startActivity(new Intent(this,AppInstalledActivity.class));
                Toast.makeText(this, "已安装应用", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cl_history:
                Toast.makeText(this, "历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cl_login:
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cl_open_vip:
                Toast.makeText(this, "开通VIP", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_ad:
                Toast.makeText(this, "新人礼包", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private static class MyHandler extends Handler {

        private final WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_NOTIFY_TITLE:
                        @SuppressWarnings("unchecked")
                        List<Title.DataBean> dataBeans = (List<Title.DataBean>) msg.obj;
                        ArrayObjectAdapter adapter = activity.getArrayObjectAdapter();
                        if (adapter != null) {
                            adapter.addAll(0, dataBeans);
                            activity.initViewPager(dataBeans);
                            HorizontalGridView horizontalGridView = activity.getHorizontalGridView();
                            if (dataBeans.size() > Constants.TAG_FEATURE_POSITION) {
                                if (horizontalGridView != null) {
                                    horizontalGridView.setSelectedPositionSmooth(Constants.TAG_FEATURE_POSITION);
                                    View positionView = horizontalGridView.getChildAt(Constants.TAG_FEATURE_POSITION);
                                    if (positionView != null) {
                                        activity.mOldTitle = positionView.findViewById(R.id.tv_main_title);
                                    }
                                }
                            } else if (dataBeans.size() > 0) {
                                if (activity.getHorizontalGridView() != null) {
                                    horizontalGridView.setSelectedPositionSmooth(0);
                                    View position0 = horizontalGridView.getChildAt(0);
                                    if (position0 != null) {
                                        activity.mOldTitle = position0.findViewById(R.id.tv_main_title);
                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        initBroadCast();
    }

//    private boolean isPressUpDownLeftRightBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            switch (keyCode) {
//                case KeyEvent.KEYCODE_DPAD_UP:
//                case KeyEvent.KEYCODE_DPAD_DOWN:
//                case KeyEvent.KEYCODE_BACK:
//                case KeyEvent.KEYCODE_DPAD_LEFT:
//                case KeyEvent.KEYCODE_DPAD_RIGHT:
//                    isPressUpDownLeftRightBack = true;
//                    break;
//                default:
//                    isPressUpDownLeftRightBack = false;
//                    break;
//            }
//
//        }
//        if (mViewPagerAdapter != null) {
//            ContentFragment contentFragment = (ContentFragment)
//                    mViewPagerAdapter.getRegisteredFragment(mCurrentPageIndex);
//            if (contentFragment != null && contentFragment.onKeyEvent(event)) {
//                return true;
//            }
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHorizontalGridView
                .removeOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
        getWindow().getDecorView().getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
        super.onDestroy();
        if (mThread != null) {
            mThread.interrupt();
        }
        unregisterReceiver(networkChangeReceiver);
    }

    private HorizontalGridView mHorizontalGridView;
    private ViewPager mViewPager;
    private Group mGroup;

    public Group getGroup() {
        return mGroup;
    }

    private ConstraintLayout mClSearch;
    private ConstraintLayout mClHistory;
    private ConstraintLayout mClLogin;
    private ConstraintLayout mClOpenVip;
    private ScaleTextView mTvAd;

    private Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            String titleJson = LocalJsonResolutionUtil.getJson(MainActivity.this, "MyTitle.json");
            //转换为对象
            Title title = LocalJsonResolutionUtil.JsonToObject(titleJson, Title.class);
            List<Title.DataBean> dataBeans = title.getData();
            if (dataBeans != null && dataBeans.size() > 0) {
                Message msg = Message.obtain();
                msg.what = MSG_NOTIFY_TITLE;
                msg.obj = dataBeans;
                mHandler.sendMessage(msg);
            }
        }
    });

    private void initView() {
        mHorizontalGridView = findViewById(R.id.hg_title);
        mViewPager = findViewById(R.id.vp_content);
        mGroup = findViewById(R.id.id_group);
        mIvNetwork = findViewById(R.id.iv_network);
        mClSearch = findViewById(R.id.cl_search);
        mClHistory = findViewById(R.id.cl_history);
        mClLogin = findViewById(R.id.cl_login);
        mClOpenVip = findViewById(R.id.cl_open_vip);
        mTvAd = findViewById(R.id.tv_ad);

        mViewPager.setOffscreenPageLimit(2);
        mHorizontalGridView.setHorizontalSpacing((int) getResources().getDimension(R.dimen.px20));
        mArrayObjectAdapter = new ArrayObjectAdapter(new TitlePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mArrayObjectAdapter);
        mHorizontalGridView.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter,
                FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
    }

    private void initData() {
        if (mThread != null) {
            mThread.start();
        }
    }

    private void initListener() {
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalFocusChangeListener(this);
        mHorizontalGridView.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);

        mClSearch.setOnClickListener(this);
        mClHistory.setOnClickListener(this);
        mClLogin.setOnClickListener(this);
        mClOpenVip.setOnClickListener(this);
        mTvAd.setOnClickListener(this);

        mClSearch.setOnKeyListener(this);
        mClHistory.setOnKeyListener(this);
        mClLogin.setOnKeyListener(this);
        mClOpenVip.setOnKeyListener(this);
        mTvAd.setOnKeyListener(this);

    }

    private void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private boolean isFirstIn = true;

    private void initViewPager(List<Title.DataBean> dataBeans) {

        mViewPagerAdapter = new ContentViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.setData(dataBeans);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected position: " + position);
                if (isFirstIn) {
                    isFirstIn = false;
                } else {
                    isSkipTabFromViewPager = true;
                }
                if (position != mCurrentPageIndex) {
//                    mCurrentPageIndex = position;
                    mHorizontalGridView.setSelectedPosition(position);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void handleTitleVisible(boolean isShow) {
        if (isShow) {
            if (mGroup.getVisibility() != View.VISIBLE) {
                mGroup.setVisibility(View.VISIBLE);
            }
        } else {
            if (mGroup.getVisibility() != View.GONE) {
                mGroup.setVisibility(View.GONE);
            }
        }
    }

    private final OnChildViewHolderSelectedListener onChildViewHolderSelectedListener
            = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            super.onChildViewHolderSelected(parent, child, position, subposition);
            if (child != null & position != mCurrentPageIndex) {
                Log.e(TAG, "onChildViewHolderSelected: 000 isSkipTabFromViewPager" + isSkipTabFromViewPager);
                TextView currentTitle = child.itemView.findViewById(R.id.tv_main_title);
                if (isSkipTabFromViewPager) {
                    Log.e(TAG, "onChildViewHolderSelected: 111");

                    if (mOldTitle != null) {
                        Log.e(TAG, "onChildViewHolderSelected: 222");

                        mOldTitle.setTextColor(getResources().getColor(R.color.colorWhite));
                        Paint paint = mOldTitle.getPaint();
                        if (paint != null) {
                            paint.setFakeBoldText(false);
                            //viewpager切页标题不刷新，调用invalidate刷新
                            mOldTitle.invalidate();
                        }
                    }
                    currentTitle.setTextColor(getResources().getColor(R.color.colorBlue));
                    Paint paint = currentTitle.getPaint();
                    if (paint != null) {
                        paint.setFakeBoldText(true);
                        //viewpager切页标题不刷新，调用invalidate刷新
                        currentTitle.invalidate();
                    }
                }
                mOldTitle = currentTitle;
            }

            isSkipTabFromViewPager = false;
            Log.e(TAG, "onChildViewHolderSelected mViewPager != null: " + (mViewPager != null)
                    + " position:" + position);
            setCurrentItemPosition(position);

        }
    };

    private void setCurrentItemPosition(int position) {
        if (mViewPager != null && position != mCurrentPageIndex) {
            mCurrentPageIndex = position;
            mViewPager.setCurrentItem(position);
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                switch (networkInfo.getType()) {
                    case ConnectivityManager.TYPE_ETHERNET:
                        mIvNetwork.setImageResource(R.drawable.ethernet);
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        mIvNetwork.setImageResource(R.drawable.wifi);
                        break;
                    default:
                        break;
                }
            } else {
                mIvNetwork.setImageResource(R.drawable.no_net);
            }
        }
    }


}


