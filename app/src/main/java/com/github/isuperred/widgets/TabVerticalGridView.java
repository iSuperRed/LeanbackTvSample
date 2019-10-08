package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.VerticalGridView;

import com.github.isuperred.R;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/28.
 * ClassName  :
 * Description  :
 */
public class TabVerticalGridView extends VerticalGridView {

    private static final String TAG = "TabVerticalGridView";

    public TabVerticalGridView(Context context) {
        super(context);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private View mTabView;
    private Group mGroup;
    private boolean isPressUp = false;
    private boolean isPressDown = false;

    public void setTabView(View tabView) {
        this.mTabView = tabView;
    }

    public void setGroup(Group mGroup) {
        this.mGroup = mGroup;
    }

    public boolean isPressUp() {
        return isPressUp;
    }

    public boolean isPressDown() {
        return isPressDown;
    }


    @Override
    public View focusSearch(View focused, int direction) {
        if (focused != null) {
            final FocusFinder ff = FocusFinder.getInstance();
            final View found = ff.findNextFocus(this, focused, direction);
            /*if (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) {
                Log.e(TAG, "focusSearch: " + (found == null));
                if (found == null) {
                    focused.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.host_shake));
                    return null;
                }
            } else */if (direction == View.FOCUS_DOWN) {
                if (found == null) {
                    focused.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.host_shake_y));
                    return null;
                }
            }
        }
        return super.focusSearch(focused, direction);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            isPressDown = false;
            isPressUp = false;
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    isPressDown = true;
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    isPressUp = true;
                    break;
                case KeyEvent.KEYCODE_BACK:
                    if (mTabView != null) {
                        if (mGroup != null && mGroup.getVisibility() != View.VISIBLE) {
                            mGroup.setVisibility(View.VISIBLE);
                        }
                        mTabView.requestFocus();
                        scrollToPosition(0);
                    }
                    return true;
                default:
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
