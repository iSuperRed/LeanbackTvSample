package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
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

    private Animation shakeX;

    @Override
    public View focusSearch(View focused, int direction) {
        if (focused != null) {
            final FocusFinder ff = FocusFinder.getInstance();
            final View found = ff.findNextFocus(this, focused, direction);
            if (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) {
                if ((found == null || found.getId() != R.id.tv_main_title)
                        && getScrollState() == SCROLL_STATE_IDLE) {
                    if (shakeX == null) {
                        shakeX = AnimationUtils.loadAnimation(getContext(), R.anim.host_shake);
                    }
                    focused.clearAnimation();
                    focused.startAnimation(shakeX);
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
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    handled = arrowScroll(FOCUS_LEFT);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    handled = arrowScroll(FOCUS_RIGHT);
                    break;
            }
        }
        return handled;
    }

    public boolean arrowScroll(int direction) {

        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                 parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                // This would cause the focus search down below to fail in fun ways.
                final StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                     parent = parent.getParent()) {
                    sb.append(" => ").append(parent.getClass().getSimpleName());
                }
                currentFocused = null;
            }
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);
        if (nextFocused == null || nextFocused == currentFocused) {
            if (direction == FOCUS_LEFT || direction == FOCUS_RIGHT) {
                shakeX(currentFocused);
                handled = true;
            }
        }
        return handled;
    }

    private Animation mShakeX;


    private void shakeX(View currentFocused) {
        if (currentFocused != null && getScrollState() == SCROLL_STATE_IDLE) {
            if (mShakeX == null) {
                mShakeX = AnimationUtils.loadAnimation(getContext(), R.anim.host_shake);
            }
            currentFocused.clearAnimation();
            currentFocused.startAnimation(mShakeX);
        }
    }
}
