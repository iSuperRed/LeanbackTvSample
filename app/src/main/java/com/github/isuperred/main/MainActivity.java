package com.github.isuperred.main;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
import com.github.isuperred.content.ContentFragment;
import com.github.isuperred.title.Title;
import com.github.isuperred.title.TitlePresenter;
import com.github.isuperred.utils.Constants;
import com.github.isuperred.utils.LocalJsonResolutionUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContentFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    private static final int MSG_NOTIFY_TITLE = 100;


//    private static final int MSG_NOTIFY_TITLE = 1;

    private ArrayObjectAdapter mArrayObjectAdapter;
    private ContentViewPagerAdapter mViewPagerAdapter;

    private View mCurrentTitle;

    private int mCurrentPageIndex = 0;

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
            case Constants.URI_TITLE_REQUEST_FOCUS:
                currentTitleRequestFocus();
                break;
            case Constants.URI_HIDE_TITLE:
                handleTitleVisible(false);
                break;
            case Constants.URI_SHOW_TITLE:
                handleTitleVisible(true);
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
                                }
                            } else {
                                if (activity.getHorizontalGridView() != null) {
                                    horizontalGridView.setSelectedPositionSmooth(0);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
        super.onDestroy();
    }

    private HorizontalGridView mHorizontalGridView;
    private ViewPager mViewPager;
    private Group mGroup;

    public Group getGroup() {
        return mGroup;
    }

    private void initView() {
        mHorizontalGridView = findViewById(R.id.hg_title);
        mViewPager = findViewById(R.id.vp_content);
        mGroup = findViewById(R.id.id_group);

        mHorizontalGridView.setHorizontalSpacing((int) getResources().getDimension(R.dimen.px20));
        mArrayObjectAdapter = new ArrayObjectAdapter(new TitlePresenter());
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mArrayObjectAdapter);
        mHorizontalGridView.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter, FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String titleJson = LocalJsonResolutionUtil.getJson(MainActivity.this, "MyTitle.json");
                Log.e(TAG, "run: " + titleJson);
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
        }).start();
    }

    private void initListener() {
        mHorizontalGridView.setOnKeyListener(onKeyListener);
        mHorizontalGridView.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

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
                mCurrentPageIndex = position;
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged state: " + state);

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

    private void currentTitleRequestFocus() {
        handleTitleVisible(true);
        if (mCurrentTitle != null) {
            mCurrentTitle.requestFocus();
        }
    }

    private final OnChildViewHolderSelectedListener onChildViewHolderSelectedListener
            = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            super.onChildViewHolderSelected(parent, child, position, subposition);

            mCurrentPageIndex = position;
            if (child != null) {
                mCurrentTitle = child.itemView.findViewById(R.id.tv_main_title);
            }
            Log.e(TAG, "onChildViewHolderSelected mViewPager != null: " + (mViewPager != null)
                    + " position:" + position);
            if (mViewPager != null) {
                mViewPager.setCurrentItem(position);
            }
        }
    };

    private final View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Log.e(TAG, "onKey: "+keyCode );
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                if (mCurrentPageIndex != Constants.TAG_FEATURE_POSITION) {
                    if (mHorizontalGridView != null) {
                        mHorizontalGridView.scrollToPosition(Constants.TAG_FEATURE_POSITION);
                        return true;
                    }
                } else {
                    finish();
                }

            }
            return false;
        }
    };

}


