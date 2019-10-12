package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

public class ScaleTextView extends AppCompatTextView implements View.OnFocusChangeListener {
    private MyFocusHighlightHelper.BrowseItemFocusHighlight mBrowseItemFocusHighlight;

    public ScaleTextView(Context context) {
        this(context,null);
    }

    public ScaleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScaleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        setFocusableInTouchMode(true);
        if(mBrowseItemFocusHighlight==null){
            mBrowseItemFocusHighlight =
                    new MyFocusHighlightHelper
                            .BrowseItemFocusHighlight(MyFocusHighlightHelper.ZOOM_FACTOR_XXXSMALL, false);
        }
        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mBrowseItemFocusHighlight != null) {
            mBrowseItemFocusHighlight.onItemFocused(v, hasFocus);
        }
    }
}
