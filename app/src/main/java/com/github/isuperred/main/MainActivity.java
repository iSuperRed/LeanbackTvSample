package com.github.isuperred.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.github.isuperred.R;
import com.github.isuperred.content.ContentViewPagerAdapter;
import com.github.isuperred.title.Title;
import com.github.isuperred.title.TitlePresenter;
import com.github.isuperred.utils.LocalJsonResolutionUtil;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int MSG_NOTIFY_TITLE = 100;
//    private static final int MSG_NOTIFY_TITLE = 1;

    private ArrayObjectAdapter mArrayObjectAdapter;
    private ContentViewPagerAdapter mViewPagerAdapter;

    private int mCurrentPageIndex;

    public ArrayObjectAdapter getArrayObjectAdapter() {
        return mArrayObjectAdapter;
    }

    private Handler mHandler = new MyHandler(this);


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
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mHorizontalGridView
                .removeOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
        super.onDestroy();
    }

    private HorizontalGridView mHorizontalGridView;
    private ViewPager mViewPager;

    private void initView() {
        mHorizontalGridView = findViewById(R.id.hg_title);
        mViewPager = findViewById(R.id.vp_content);
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
                String fileName = "title.json";
                String titleJson = LocalJsonResolutionUtil.getJson(MainActivity.this, fileName);
                Log.e(TAG, "run: " + titleJson);
                //转换为对象
                Title title = LocalJsonResolutionUtil.JsonToObject(titleJson, Title.class);
                List<Title.DataBean> dataBeans = title.getData();
                for (int i = 0; i < dataBeans.size(); i++) {
//                    dataBeans.get(i).getName();
                    Log.e(TAG, "run: " + dataBeans.get(i).getName());
                }
                Message msg = Message.obtain();
                msg.what = MSG_NOTIFY_TITLE;
                msg.obj = dataBeans;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void initListener() {
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

    private final OnChildViewHolderSelectedListener onChildViewHolderSelectedListener
            = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            super.onChildViewHolderSelected(parent, child, position, subposition);
            if (mViewPager != null) {
                mViewPager.setCurrentItem(position);
            }

        }

        @Override
        public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
            super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition);
        }
    };
}


