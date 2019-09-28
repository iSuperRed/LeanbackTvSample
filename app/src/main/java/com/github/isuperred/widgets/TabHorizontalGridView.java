package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.leanback.widget.HorizontalGridView;

import com.github.isuperred.utils.Constants;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/28.
 * ClassName  :
 * Description  :
 */
public class TabHorizontalGridView extends HorizontalGridView {
    public TabHorizontalGridView(Context context) {
        super(context);
    }

    public TabHorizontalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabHorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (getSelectedPosition() != Constants.TAG_FEATURE_POSITION) {
                        if (getVisibility() != View.VISIBLE) {
                            setVisibility(View.VISIBLE);
                        }
                        requestFocus();
                        scrollToPosition(Constants.TAG_FEATURE_POSITION);
                        return true;
                    }

                default:
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
