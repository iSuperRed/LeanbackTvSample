package com.github.isuperred.content;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.VerticalGridView;

import com.github.isuperred.R;
import com.github.isuperred.main.MainActivity;
import com.github.isuperred.title.Title;
import com.github.isuperred.type.ContentPresenter;
import com.github.isuperred.utils.LocalJsonResolutionUtil;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/19.
 * ClassName  :
 * Description  :
 */
public class ContentFragment extends BaseLazyLoadFragment {

    private static final String TAG = "ContentFragment";

    private static final String BUNDLE_KEY_POSITION = "bundleKeyPosition";
    private static final String BUNDLE_KEY_TAB_CODE = "bundleKeyTabCode";
    private static final int MSG_ADD_ITEM = 100;

    private VerticalGridView mVerticalGridView;

    private int mCurrentTabPosition;
    private String mCurrentTabCode;

    private MainActivity mActivity;
    private View mRootView;

    private ArrayObjectAdapter mAdapter;

    public static ContentFragment newInstance(int position, String tabCode) {
        Log.e(TAG + " pos:" + position, "new Instance status: " + position + " tab:" + tabCode);
        ContentFragment fragment = new ContentFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_POSITION, position);
        bundle.putString(BUNDLE_KEY_TAB_CODE, tabCode);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG + " pos:", "onCreate: ");
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        mCurrentTabPosition = getArguments().getInt(BUNDLE_KEY_POSITION);
        mCurrentTabCode = getArguments().getString(BUNDLE_KEY_TAB_CODE);
        Log.e(TAG + " pos:" + mCurrentTabPosition, " tabCode: " + mCurrentTabCode);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_content, container, false);
            initView();
        }
        return mRootView;
    }

    private void initView() {
        mVerticalGridView = mRootView.findViewById(R.id.hg_content);
//        mVerticalGridView.setVerticalSpacing((int) mActivity.getResources().getDimension(R.dimen.px10));
        ContentPresenterSelector presenterSelector = new ContentPresenterSelector();
        mAdapter = new ArrayObjectAdapter(presenterSelector);
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mAdapter);
        mVerticalGridView.setAdapter(itemBridgeAdapter);

    }

    protected void initListener() {
//        mVerticalGridView.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
//        mVerticalGridView.addOnScrollListener(onScrollListener);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void fetchData() {
        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = LocalJsonResolutionUtil.getJson(mActivity, "movie.json");
                Log.e(TAG, "run json: " + json);
                Content content = LocalJsonResolutionUtil.JsonToObject(json, Content.class);
                List<Content.DataBean> dataBeans = content.getData();
                for (int i = 0; i < dataBeans.size(); i++) {
                    Content.DataBean dataBean = dataBeans.get(i);
                    ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(new ContentPresenter());
                    HeaderItem headerItem = new HeaderItem("大闹天宫");
                    ListRow listRow = new ListRow(8, headerItem,
                            arrayObjectAdapter);
//                    headerItem.setContentDescription("大闹天宫");

                    arrayObjectAdapter.addAll(0, dataBean.getWidgets());

                    mAdapter.add(listRow);
                }
            }
        }).start();
    }
}
