package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.FocusHighlight;


public class ScaleConstraintLayout extends ConstraintLayout implements View.OnFocusChangeListener {

    private MyFocusHighlightHelper.BrowseItemFocusHighlight mBrowseItemFocusHighlight;

    public ScaleConstraintLayout(Context context) {
        this(context, null);
    }

    public ScaleConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        setClipChildren(false);
        setClipToPadding(false);
        setOnFocusChangeListener(this);
        if (mBrowseItemFocusHighlight == null) {
            mBrowseItemFocusHighlight =
                    new MyFocusHighlightHelper
                            .BrowseItemFocusHighlight(MyFocusHighlightHelper.ZOOM_FACTOR_XXXSMALL, false);
        }

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mBrowseItemFocusHighlight != null) {
            mBrowseItemFocusHighlight.onItemFocused(v, hasFocus);
        }
    }
}
