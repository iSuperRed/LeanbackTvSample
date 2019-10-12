package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.leanback.widget.HorizontalGridView;

import com.github.isuperred.R;
import com.github.isuperred.utils.Constants;


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
    public View focusSearch(View focused, int direction) {
        if (focused != null) {
            final FocusFinder ff = FocusFinder.getInstance();
            final View found = ff.findNextFocus(this, focused, direction);
            if (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) {
                if ((found == null || found.getId() != R.id.tv_main_title) && getScrollState() == SCROLL_STATE_IDLE) {
                    focused.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.host_shake));
                    return null;
                }
            }
        }
        return super.focusSearch(focused, direction);
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
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    break;
                default:
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
