package com.github.isuperred.content;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.isuperred.R;
import com.github.isuperred.base.BaseLazyLoadFragment;
import com.github.isuperred.bean.Content;
import com.github.isuperred.main.MainActivity;
import com.github.isuperred.bean.Footer;
import com.github.isuperred.presenter.TypeFiveContentPresenter;
import com.github.isuperred.presenter.TypeFourContentPresenter;
import com.github.isuperred.presenter.TypeOneContentPresenter;
import com.github.isuperred.presenter.TypeSixContentPresenter;
import com.github.isuperred.presenter.TypeThreeContentPresenter;
import com.github.isuperred.presenter.TypeTwoContentPresenter;
import com.github.isuperred.presenter.TypeZeroContentPresenter;
import com.github.isuperred.utils.Constants;
import com.github.isuperred.utils.LocalJsonResolutionUtil;
import com.github.isuperred.widgets.TabVerticalGridView;

import java.lang.ref.WeakReference;
import java.util.List;


public class ContentFragment extends BaseLazyLoadFragment {

    private static final String TAG = "ContentFragment";

    private static final String BUNDLE_KEY_POSITION = "bundleKeyPosition";
    private static final String BUNDLE_KEY_TAB_CODE = "bundleKeyTabCode";
    private static final int MSG_ADD_ITEM = 100;
    private static final String MSG_BUNDLE_KEY_ADD_ITEM = "msgBundleKeyItem";

    private TabVerticalGridView mVerticalGridView;

    private int mCurrentTabPosition;
    private String mCurrentTabCode;

    private MainActivity mActivity;
    private View mRootView;
    private Handler mHandler;
    public ProgressBar mPbLoading;
    private ArrayObjectAdapter mAdapter;
//    public static final int MSG_ADD_ITEM = 100;

    private class MyHandler extends Handler {

        private final WeakReference<Activity> mActivity;

        MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            MainActivity activity = (MainActivity) mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_ADD_ITEM:

                        Content content = msg.getData().getParcelable(MSG_BUNDLE_KEY_ADD_ITEM);
                        if (content == null) {
                            break;
                        }
                        List<Content.DataBean> dataBeans = content.getData();
                        for (int i = 0; i < dataBeans.size(); i++) {
                            Content.DataBean dataBean = dataBeans.get(i);
                            addItem(dataBean);
                        }
                        addFooter();
                        mPbLoading.setVisibility(View.GONE);
                        mVerticalGridView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private ContentFragment.OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

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

        if (context instanceof ContentFragment.OnFragmentInteractionListener) {
            mListener = (ContentFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mActivity = (MainActivity) context;
        mHandler = new MyHandler(mActivity);

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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initView() {
        mPbLoading = mRootView.findViewById(R.id.pb_loading);
        mVerticalGridView = mRootView.findViewById(R.id.hg_content);
        mVerticalGridView.setTabView(mActivity.getHorizontalGridView());
        mVerticalGridView.setGroup(mActivity.getGroup());
        mVerticalGridView.setVerticalSpacing((int) getResources().getDimension(R.dimen.px48));
        ContentPresenterSelector presenterSelector = new ContentPresenterSelector();
        mAdapter = new ArrayObjectAdapter(presenterSelector);
        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(mAdapter);
        mVerticalGridView.setAdapter(itemBridgeAdapter);

    }

    protected void initListener() {
        mVerticalGridView.addOnScrollListener(onScrollListener);
        mVerticalGridView.addOnChildViewHolderSelectedListener(onSelectedListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mVerticalGridView != null) {
            mVerticalGridView.removeOnScrollListener(onScrollListener);
            mVerticalGridView.removeOnChildViewHolderSelectedListener(onSelectedListener);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint mCurrentTabPosition: " + mCurrentTabPosition
                + " isVisibleToUser:" + isVisibleToUser);
        if (!isVisibleToUser) {
            scrollToTop();
        }
    }

    @Override
    public void fetchData() {
        loadData();
    }

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (getActivity() == null) {
                mPbLoading.setVisibility(View.GONE);
                return;
            }

            String json = null;
            if (mCurrentTabCode == null) {
                mPbLoading.setVisibility(View.GONE);
                return;
            }
            switch (mCurrentTabCode) {
                case "c40248cac1f44c278f8bd23a0bba8b4f":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Mine.json");
                    break;
                case "7359d189a049468d9d4e280fd1ec15c5":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "WatchTv.json");
                    break;
                case "1b14cb1608d3449c83585b48d47b53c1":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Clear4k.json");
                    break;
                case "5f6874e8106e41a680e05fe49fe4a198":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Children.json");
                    break;
                case "50e4dfe685a84f929ba08952d6081877":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Featured.json");
                    break;
                case "dae28835ebac4f629cc610b4d5a8df25":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Years70.json");
                    break;
                case "5e1958d0cf9341589db884d83aca79e3":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Everything.json");
                    break;
                case "c4a72503d2374b188cf74767f2276220":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "VIP.json");
                    break;
                case "8146c5ff88a245b9af2ce7d2bf301b27":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "TVSeries.json");
                    break;
                case "7412804a6aa24ca9be25fd8cd26f1995":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Movie.json");
                    break;
                case "d179143bacc948d28748338562a94648":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Variety.json");
                    break;
                case "9c58bbdacc1449a4bb84ad6af16ba20d":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Classroom.json");
                    break;
                case "c33db6793aba48bea06b075c35c8be5a":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Anime.json");
                    break;
                case "65504aa451fb4b159bbfeb7161750411":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Basketball.json");
                    break;
                case "a4c28944cb0448579007c6c20c037127":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Physical.json");
                    break;
                case "d971d4585bd14e6fadab1aa2d27b71d6":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Game.json");
                    break;
                case "a868db298ef84dcbb22d919d02f473cb":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Documentary.json");
                    break;
                case "634e89b44aeb4b2a99e9a1bb449daf8b":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Life.json");
                    break;
                case "9a5fd09ddfa64c4b95b3dc02b27c7576":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "OrientalTheatre.json");
                    break;
                case "695ed6a510934a93a9593b034a99fc01":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Car.json");
                    break;
                case "b9c9229ef6534682919d7af67438e4d6":
                    json = LocalJsonResolutionUtil.getJson(getActivity(), "Funny.json");
                    break;
            }
            if (json == null) {
                return;
            }
            Content content = LocalJsonResolutionUtil.JsonToObject(json, Content.class);
            final Message msg = Message.obtain();
            msg.what = MSG_ADD_ITEM;
            Bundle b = new Bundle();
            b.putParcelable(MSG_BUNDLE_KEY_ADD_ITEM, content);
            msg.setData(b);
            //延迟1秒模拟加载数据过程
            mHandler.sendMessageDelayed(msg,1000);

        }
    });

    private void loadData() {
        mPbLoading.setVisibility(View.VISIBLE);
        mVerticalGridView.setVisibility(View.INVISIBLE);
        thread.start();
    }

//    public boolean onKeyEvent(KeyEvent keyEvent) {
//        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//
//
//        }
//        return false;
//    }

    private void scrollToTop() {
        if (mVerticalGridView != null) {
            mVerticalGridView.scrollToPosition(0);
//            currentTitleRequestFocus();
            if (mActivity.getGroup() != null && mActivity.getGroup().getVisibility() != View.VISIBLE) {
                mActivity.getGroup().setVisibility(View.VISIBLE);
            }
        }
    }

    private void addItem(Content.DataBean dataBean) {
        switch (dataBean.getContentCode()) {
            case Constants.TYPE_ZERO:
                ArrayObjectAdapter arrayObjectAdapter = new ArrayObjectAdapter(new TypeZeroContentPresenter());
                List<Content.DataBean.WidgetsBean> listZero = dataBean.getWidgets();
                if (listZero != null && listZero.size() > 2) {
                    listZero = listZero.subList(0, 2);
                }
                arrayObjectAdapter.addAll(0, listZero);
                ListRow listRow = new ListRow(arrayObjectAdapter);
                addWithTryCatch(listRow);

                break;
            case Constants.TYPE_ONE:
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
                    headerItem = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowOne = new ListRow(headerItem, arrayObjectAdapterOne);
                addWithTryCatch(listRowOne);

                break;
            case Constants.TYPE_TWO:
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
                    headerItemTwo = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowTwo = new ListRow(headerItemTwo, arrayObjectAdapterTwo);
                addWithTryCatch(listRowTwo);

                break;
            case Constants.TYPE_THREE:
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
                    headerItemThree = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowThree = new ListRow(headerItemThree, arrayObjectAdapterThree);
                addWithTryCatch(listRowThree);

                break;
            case Constants.TYPE_FOUR:
                ArrayObjectAdapter arrayObjectAdapterFour = new ArrayObjectAdapter(new TypeFourContentPresenter());
                List<Content.DataBean.WidgetsBean> listFour = dataBean.getWidgets();
                if (listFour == null) {
                    return;
                }
                if (listFour.size() > 6) {
                    listFour = listFour.subList(0, 6);
                }
                arrayObjectAdapterFour.addAll(0, listFour);
                HeaderItem headerItemFour = null;
                if (dataBean.getShowTitle()) {
                    headerItemFour = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowFour = new ListRow(headerItemFour, arrayObjectAdapterFour);
                addWithTryCatch(listRowFour);

                break;
            case Constants.TYPE_FIVE:
                ArrayObjectAdapter arrayObjectAdapterFive = new ArrayObjectAdapter(new TypeFiveContentPresenter());
                List<Content.DataBean.WidgetsBean> listFive = dataBean.getWidgets();
                if (listFive == null) {
                    return;
                }
                if (listFive.size() > 6) {
                    listFive = listFive.subList(0, 6);
                }
                arrayObjectAdapterFive.addAll(0, listFive);
                HeaderItem headerItemFive = null;
                if (dataBean.getShowTitle()) {
                    headerItemFive = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowFive = new ListRow(headerItemFive, arrayObjectAdapterFive);
                addWithTryCatch(listRowFive);
                break;
            case Constants.TYPE_SIX:
                ArrayObjectAdapter arrayObjectAdapterSix = new ArrayObjectAdapter(new TypeSixContentPresenter());
                List<Content.DataBean.WidgetsBean> listSix = dataBean.getWidgets();
                if (listSix == null) {
                    return;
                }
                if (listSix.size() > 6) {
                    listSix = listSix.subList(0, 6);
                }
                arrayObjectAdapterSix.addAll(0, listSix);
                HeaderItem headerItemSix = null;
                if (dataBean.getShowTitle()) {
                    headerItemSix = new HeaderItem(dataBean.getTitle());
                }
                ListRow listRowSix = new ListRow(headerItemSix, arrayObjectAdapterSix);
                addWithTryCatch(listRowSix);
                break;
            case Constants.TYPE_SEVEN:
                ArrayObjectAdapter arrayObjectAdapterSeven = new ArrayObjectAdapter(new TypeSixContentPresenter());
                HeaderItem headerItemSeven = new HeaderItem("大闹天宫");
                ListRow listRowSeven = new ListRow(8, headerItemSeven,
                        arrayObjectAdapterSeven);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterSeven.addAll(0, dataBean.getWidgets());
                addWithTryCatch(listRowSeven);
                break;
            case Constants.TYPE_EIGHT:
                ArrayObjectAdapter arrayObjectAdapterEight = new ArrayObjectAdapter(new TypeSixContentPresenter());
                HeaderItem headerItemEight = new HeaderItem("大闹天宫");
                ListRow listRowEight = new ListRow(8, headerItemEight,
                        arrayObjectAdapterEight);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterEight.addAll(0, dataBean.getWidgets());
                addWithTryCatch(listRowEight);
                break;
            case Constants.TYPE_NINE:
                ArrayObjectAdapter arrayObjectAdapterNine = new ArrayObjectAdapter(new TypeSixContentPresenter());
                HeaderItem headerItemNine = new HeaderItem("大闹天宫");
                ListRow listRowNine = new ListRow(8, headerItemNine,
                        arrayObjectAdapterNine);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterNine.addAll(0, dataBean.getWidgets());
                addWithTryCatch(listRowNine);
                break;
            case Constants.TYPE_TEN:
                ArrayObjectAdapter arrayObjectAdapterTen = new ArrayObjectAdapter(new TypeSixContentPresenter());
                HeaderItem headerItemTen = new HeaderItem("大闹天宫");
                ListRow listRowTen = new ListRow(8, headerItemTen,
                        arrayObjectAdapterTen);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterTen.addAll(0, dataBean.getWidgets());
                addWithTryCatch(listRowTen);
                break;
            case Constants.TYPE_ELEVEN:
                ArrayObjectAdapter arrayObjectAdapterEleven = new ArrayObjectAdapter(new TypeSixContentPresenter());
                HeaderItem headerItemEleven = new HeaderItem("大闹天宫");
                ListRow listRowEleven = new ListRow(8, headerItemEleven,
                        arrayObjectAdapterEleven);
//                    headerItem.setContentDescription("大闹天宫");

                arrayObjectAdapterEleven.addAll(0, dataBean.getWidgets());
                addWithTryCatch(listRowEleven);
                break;
        }
    }

    private void addFooter() {
        addWithTryCatch(new Footer());
    }


    private final RecyclerView.OnScrollListener onScrollListener
            = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState) {
                //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                case RecyclerView.SCROLL_STATE_SETTLING:
                    Glide.with(mActivity).pauseRequests();
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    Glide.with(mActivity).resumeRequests();
            }
        }
    };

    private final OnChildViewHolderSelectedListener onSelectedListener
            = new OnChildViewHolderSelectedListener() {
        @Override
        public void onChildViewHolderSelected(RecyclerView parent,
                                              RecyclerView.ViewHolder child,
                                              int position, int subposition) {
            super.onChildViewHolderSelected(parent, child, position, subposition);
            Log.e(TAG, "onChildViewHolderSelected: " + position
            );

            if (mVerticalGridView == null) {
                return;
            }
            Log.e(TAG, "onChildViewHolderSelected: " + "　isPressUp:" + mVerticalGridView.isPressUp()
                    + " isPressDown:" + mVerticalGridView.isPressDown());

            if (mVerticalGridView.isPressUp() && position == 0) {
                mListener.onFragmentInteraction(Uri.parse(Constants.URI_SHOW_TITLE));
            } else if (mVerticalGridView.isPressDown() && position == 1) {
                mListener.onFragmentInteraction(Uri.parse(Constants.URI_HIDE_TITLE));
            }
        }
    };

    private void addWithTryCatch(Object item) {
        try {
            if (!mVerticalGridView.isComputingLayout()) {
                mAdapter.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
