package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.VerticalGridView;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/28.
 * ClassName  :
 * Description  :
 */
public class TabVerticalGridView extends VerticalGridView {

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
