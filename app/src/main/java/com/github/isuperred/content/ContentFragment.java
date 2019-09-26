package com.github.isuperred.content;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.VerticalGridView;

import com.github.isuperred.R;
import com.github.isuperred.main.MainActivity;
import com.github.isuperred.type.ContentPresenter;
import com.github.isuperred.type.TypeThreeContentPresenter;
import com.github.isuperred.type.TypeTwoContentPresenter;
import com.github.isuperred.type.TypeZeroContentPresenter;
import com.github.isuperred.type.TypeOneContentPresenter;
import com.github.isuperred.utils.LocalJsonResolutionUtil;
import com.github.isuperred.utils.Type;

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
            initListener();
        }
        return mRootView;
    }

    private void initView() {
        mVerticalGridView = mRootView.findViewById(R.id.hg_content);
        mVerticalGridView.setVerticalSpacing((int) mActivity.getResources().getDimension(R.dimen.px48));
        ContentPresenterSelector presenterSelector = new ContentPresenterSelector();
        mAdapter = new ArrayObjectAdapter(presenterSelector);
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mAdapter);
        mVerticalGridView.setAdapter(itemBridgeAdapter);

    }

    protected void initListener() {
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
                    addItem(dataBean);
                }
            }
        }).start();
    }

    private void addItem(Content.DataBean dataBean) {

        switch (dataBean.getContentCode()) {
            case Type.TYPE_ZERO:
                ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(new TypeZeroContentPresenter());
                List<Content.DataBean.WidgetsBean> listZero = dataBean.getWidgets();
                if (listZero != null && listZero.size() > 2) {
                    listZero = listZero.subList(0, 2);
                }
                arrayObjectAdapter.addAll(0, listZero);
                ListRow listRow = new ListRow(arrayObjectAdapter);
                mAdapter.add(listRow);
                break;
            case Type.TYPE_ONE:
                ArrayObjectAdapter arrayObjectAdapterOne = new ArrayObjectAdapter(new TypeOneContentPresenter());
                List<Content.DataBean.WidgetsBean> listOne = dataBean.getWidgets();
                if (listOne == null) {
                    return;
                }
                if (listOne.size() > 4) {
                    listOne = listOne.subList(0, 4);
                }
                arrayObjectAdapterOne.addAll(0, listOne);
                HeaderItem headerItem = null;
                if (dataBean.getShowTitle()) {
                    Log.e(TAG, "addItem: "+dataBean.getTitle() );
                    headerItem = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowOne = new ListRow(headerItem, arrayObjectAdapterOne);
                mAdapter.add(listRowOne);
                break;
            case Type.TYPE_TWO:
                ArrayObjectAdapter arrayObjectAdapterTwo = new ArrayObjectAdapter(new TypeTwoContentPresenter());
                List<Content.DataBean.WidgetsBean> listTwo = dataBean.getWidgets();
                if (listTwo == null) {
                    return;
                }
                if (listTwo.size() > 3) {
                    listTwo = listTwo.subList(0, 3);
                }
                arrayObjectAdapterTwo.addAll(0, listTwo);
                HeaderItem headerItemTwo = null;
                if (dataBean.getShowTitle()) {
                    Log.e(TAG, "addItem: "+dataBean.getTitle() );
                    headerItemTwo = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowTwo = new ListRow(headerItemTwo, arrayObjectAdapterTwo);
                mAdapter.add(listRowTwo);
                break;
            case Type.TYPE_THREE:
                ArrayObjectAdapter arrayObjectAdapterThree = new ArrayObjectAdapter(new TypeThreeContentPresenter());
                List<Content.DataBean.WidgetsBean> listThree = dataBean.getWidgets();
                if (listThree == null) {
                    return;
                }
                if (listThree.size() > 6) {
                    listThree = listThree.subList(0, 6);
                }
                arrayObjectAdapterThree.addAll(0, listThree);
                HeaderItem headerItemThree = null;
                if (dataBean.getShowTitle()) {
                    Log.e(TAG, "addItem: "+dataBean.getTitle() );
                    headerItemThree = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowThree = new ListRow(headerItemThree, arrayObjectAdapterThree);
                mAdapter.add(listRowThree);
                break;
            case Type.TYPE_FOUR:
                ArrayObjectAdapter arrayObjectAdapterFour = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemFour = new HeaderItem("大闹天宫");
                ListRow listRowFour = new ListRow(8, headerItemFour,
                        arrayObjectAdapterFour);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterFour.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowFour);
                break;
            case Type.TYPE_FIVE:
                ArrayObjectAdapter arrayObjectAdapterFive = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemFive = new HeaderItem("大闹天宫");
                ListRow listRowFive = new ListRow(8, headerItemFive,
                        arrayObjectAdapterFive);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterFive.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowFive);
                break;
            case Type.TYPE_SIX:
                ArrayObjectAdapter arrayObjectAdapterSix = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemSix = new HeaderItem("大闹天宫");
                ListRow listRowSix = new ListRow(8, headerItemSix,
                        arrayObjectAdapterSix);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterSix.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowSix);
                break;
            case Type.TYPE_SEVEN:
                ArrayObjectAdapter arrayObjectAdapterSeven = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemSeven = new HeaderItem("大闹天宫");
                ListRow listRowSeven = new ListRow(8, headerItemSeven,
                        arrayObjectAdapterSeven);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterSeven.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowSeven);
                break;
            case Type.TYPE_EIGHT:
                ArrayObjectAdapter arrayObjectAdapterEight = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemEight = new HeaderItem("大闹天宫");
                ListRow listRowEight = new ListRow(8, headerItemEight,
                        arrayObjectAdapterEight);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterEight.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowEight);
                break;
            case Type.TYPE_NINE:
                ArrayObjectAdapter arrayObjectAdapterNine = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemNine = new HeaderItem("大闹天宫");
                ListRow listRowNine = new ListRow(8, headerItemNine,
                        arrayObjectAdapterNine);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterNine.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowNine);
                break;
            case Type.TYPE_TEN:
                ArrayObjectAdapter arrayObjectAdapterTen = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemTen = new HeaderItem("大闹天宫");
                ListRow listRowTen = new ListRow(8, headerItemTen,
                        arrayObjectAdapterTen);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterTen.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowTen);
                break;
            case Type.TYPE_ELEVEN:
                ArrayObjectAdapter arrayObjectAdapterEleven = new ArrayObjectAdapter(new ContentPresenter());
                HeaderItem headerItemEleven = new HeaderItem("大闹天宫");
                ListRow listRowEleven = new ListRow(8, headerItemEleven,
                        arrayObjectAdapterEleven);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterEleven.addAll(0, dataBean.getWidgets());

                mAdapter.add(listRowEleven);
                break;
        }
    }


}
