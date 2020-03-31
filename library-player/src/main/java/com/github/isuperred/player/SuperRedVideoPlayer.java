package com.github.isuperred.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class SuperRedVideoPlayer extends StandardGSYVideoPlayer {
    public SuperRedVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        hideFullScreenButton();
    }

    public SuperRedVideoPlayer(Context context) {
        super(context);
        hideFullScreenButton();
    }

    public SuperRedVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        hideFullScreenButton();
    }

    private void hideFullScreenButton() {
        mTopContainer.findViewById(R.id.back).setVisibility(View.GONE);
        mTopContainer.findViewById(R.id.title).setVisibility(View.GONE);
        getFullscreenButton().setVisibility(View.GONE);
    }
}
