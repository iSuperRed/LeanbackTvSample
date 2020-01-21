package com.github.isuperred.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;
import com.github.isuperred.base.BaseActivity;
import com.github.isuperred.bean.Video;
import com.github.isuperred.presenter.EpisodeContentPresenter;
import com.github.isuperred.presenter.EpisodeGroupPresenter;
import com.github.isuperred.utils.FontDisplayUtil;
import com.github.isuperred.utils.LocalJsonResolutionUtil;
import com.github.isuperred.widgets.focus.MyItemBridgeAdapter;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "VideoDetailActivity";
    private static final int MSG_ADD_EPISODE = 10010;
    private static final int MSG_ADD_EPISODE_CONTENT = 10011;
    private static final String MSG_BUNDLE_KEY_VIDEO = "msgBundleKeyVideo";
    private StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;

    private HorizontalGridView mHgEpisodeContent;
    private HorizontalGridView mHgEpisodeGroup;

    private ArrayObjectAdapter mEpisodeContentAdapter;
    private ArrayObjectAdapter mEpisodeGroupAdapter;

    private int mCurrentGroupPosition = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_ADD_EPISODE:
                    Video video = (Video) msg.obj;
                    if (video == null) {
                        return;
                    }
                    Video.DataBean dataBean = video.getData();
                    if (dataBean == null) {
                        return;
                    }
                    List<Video.DataBean.EpisodeBean> episodeBeans = dataBean.getEpisode();
                    if (episodeBeans == null || episodeBeans.size() == 0) {
                        return;
                    }
                    if (msg.arg1 == 1) {//msg.arg1: 1：组别也添加数据 0：组别不添加数据
                        mEpisodeGroupAdapter.clear();
                        mEpisodeGroupAdapter.addAll(0, episodeBeans);
                    }
                    addEpisodeContent(episodeBeans.get(mCurrentGroupPosition));

                    break;
                case MSG_ADD_EPISODE_CONTENT:
                    addEpisodeContent((Video.DataBean.EpisodeBean) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    private void addEpisodeContent(Video.DataBean.EpisodeBean episodeBean) {
        if (episodeBean == null) {
            return;
        }
        List<Video.DataBean.EpisodeBean.GroupDetailBean> groupDetailBeans = episodeBean.getGroupDetail();
        if (groupDetailBeans == null || groupDetailBeans.size() == 0) {
            return;
        }
        mEpisodeContentAdapter.clear();
        mEpisodeContentAdapter.addAll(0, groupDetailBeans);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        init();
        initView();
        initData();
        initListener();
    }

    private void init() {
        videoPlayer = findViewById(R.id.video_player);

        String source1 = "http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8";
        videoPlayer.setUp(source1, true, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    private void initView() {
        initEpisodeContent();
        initEpisodeGroup();
        initOthers();
    }

    @SuppressLint("RestrictedApi")
    private void initEpisodeContent() {
        mHgEpisodeContent = findViewById(R.id.hg_episode_content);
        mHgEpisodeContent.setItemAnimator(null);
//        mHgEpisodeContent.setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);
        mHgEpisodeContent.setHorizontalSpacing(FontDisplayUtil.dip2px(this, 6));
        mEpisodeContentAdapter = new ArrayObjectAdapter(new EpisodeContentPresenter());
        ItemBridgeAdapter itemBridgeAdapter = new MyItemBridgeAdapter(mEpisodeContentAdapter) {
            @Override
            public OnItemViewClickedListener getOnItemViewClickedListener() {
                return new OnItemViewClickedListener() {
                    @Override
                    public void onItemClicked(View focusView,
                                              Presenter.ViewHolder itemViewHolder,
                                              Object item) {
                        if (item instanceof Video.DataBean.EpisodeBean.GroupDetailBean) {
                            Video.DataBean.EpisodeBean.GroupDetailBean groupDetailBean = (Video.DataBean.EpisodeBean.GroupDetailBean) item;
                            String sourceURL = groupDetailBean.getUrl();
                            if (!TextUtils.isEmpty(sourceURL)) {
                                videoPlayer.setUp(sourceURL, true, "测试视频");
                                videoPlayer.startPlayLogic();
                            }
                            Toast.makeText(VideoDetailActivity.this, groupDetailBean.getNumber(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
            }
        };
        mHgEpisodeContent.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter,
                FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
    }

    @SuppressLint("RestrictedApi")
    private void initEpisodeGroup() {
        mHgEpisodeGroup = findViewById(R.id.hg_episode_group);
//        mHgEpisodeGroup.setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);
        mHgEpisodeGroup.setItemAnimator(null);
        mHgEpisodeGroup.setHorizontalSpacing(FontDisplayUtil.dip2px(this, 6));
        mEpisodeGroupAdapter = new ArrayObjectAdapter(new EpisodeGroupPresenter());
        ItemBridgeAdapter itemBridgeAdapter = new MyItemBridgeAdapter(mEpisodeGroupAdapter) {
            @Override
            public OnItemViewClickedListener getOnItemViewClickedListener() {
                return new OnItemViewClickedListener() {
                    @Override
                    public void onItemClicked(View focusView,
                                              Presenter.ViewHolder itemViewHolder,
                                              Object item) {
                        if (item instanceof Video.DataBean.EpisodeBean) {
                            Video.DataBean.EpisodeBean episodeBean = (Video.DataBean.EpisodeBean) item;
                            Toast.makeText(VideoDetailActivity.this, episodeBean.getGroupName(), Toast.LENGTH_SHORT).show();
                        }

                    }
                };
            }

            @Override
            public OnItemFocusChangedListener getOnItemFocusChangedListener() {
                return new OnItemFocusChangedListener() {
                    @Override
                    public void onItemFocusChanged(View focusView,
                                                   Presenter.ViewHolder itemViewHolder,
                                                   Object item,
                                                   boolean hasFocus,
                                                   int pos) {
                        if (mCurrentGroupPosition != pos) {
                            mCurrentGroupPosition = pos;
                            if (item instanceof Video.DataBean.EpisodeBean) {
                                Video.DataBean.EpisodeBean episodeBean = (Video.DataBean.EpisodeBean) item;
                                final Message msg = Message.obtain();
                                msg.what = MSG_ADD_EPISODE_CONTENT;
                                msg.obj = episodeBean;
                                //延迟1秒模拟加载数据过程
                                mHandler.sendMessageDelayed(msg, 0);
                            }

                        }

                    }

                };
            }
        };
        mHgEpisodeGroup.setAdapter(itemBridgeAdapter);
        FocusHighlightHelper.setupBrowseItemFocusHighlight(itemBridgeAdapter,
                FocusHighlight.ZOOM_FACTOR_LARGE, false);

    }

    private TextView mTvFullScreen;
    private TextView mTvOpenVip;
    private TextView mTvFavourite;
    private TextView mTvMoreIntroduction;

    private void initOthers() {
        mTvFullScreen = findViewById(R.id.tv_full_screen);
        mTvFullScreen.requestFocus();
        mTvOpenVip = findViewById(R.id.tv_open_vip);
        mTvFavourite = findViewById(R.id.tv_favourite);
        mTvMoreIntroduction = findViewById(R.id.tv_more_introduction);
    }

    private final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            String json =
                    LocalJsonResolutionUtil.getJson(VideoDetailActivity.this, "Video.json");
            Video video = LocalJsonResolutionUtil.JsonToObject(json, Video.class);
            final Message msg = Message.obtain();
            msg.what = MSG_ADD_EPISODE;
            msg.obj = video;
            msg.arg1 = 1;
            //延迟1秒模拟加载数据过程
            mHandler.sendMessageDelayed(msg, 0);
        }
    });

    private void initData() {
        thread.start();
    }

    private void initListener() {
        videoPlayer.setOnClickListener(this);
        mTvFullScreen.setOnClickListener(this);
        mTvOpenVip.setOnClickListener(this);
        mTvFavourite.setOnClickListener(this);
        mTvMoreIntroduction.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            toggleFullScreen();
            return;
        } else {
            //先返回正常状态
            if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                videoPlayer.getFullscreenButton().performClick();
                return;
            }
            //释放所有
            videoPlayer.setVideoAllCallBack(null);
        }

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.video_player:
                toggleFullScreen();
                Toast.makeText(VideoDetailActivity.this, "video_player", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_full_screen:
                toggleFullScreen();
                Toast.makeText(VideoDetailActivity.this, "tv_full_screen", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_open_vip:
                Toast.makeText(VideoDetailActivity.this, "tv_open_vip", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_favourite:
                Toast.makeText(VideoDetailActivity.this, "tv_favourite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_introduction:
                Toast.makeText(VideoDetailActivity.this, "tv_more_introduction", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private boolean isFullScreen = false;

    private void toggleFullScreen() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) videoPlayer.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT
                    && layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                layoutParams.width = FontDisplayUtil.dip2px(VideoDetailActivity.this, 420);
                layoutParams.height = FontDisplayUtil.dip2px(VideoDetailActivity.this, 237);

                layoutParams.leftMargin = FontDisplayUtil.dip2px(VideoDetailActivity.this, 48);
                layoutParams.topMargin = FontDisplayUtil.dip2px(VideoDetailActivity.this, 74);
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                int padding = FontDisplayUtil.dip2px(VideoDetailActivity.this, 2);
                videoPlayer.setPadding(padding, padding, padding, padding);
                isFullScreen = false;
            } else {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.leftMargin = 0;
                layoutParams.topMargin = 0;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                isFullScreen = true;
                videoPlayer.setPadding(0, 0, 0, 0);
            }
            videoPlayer.setLayoutParams(layoutParams);
        }
    }
}
