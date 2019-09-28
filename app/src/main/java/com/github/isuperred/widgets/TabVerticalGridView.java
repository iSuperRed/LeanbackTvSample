package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.leanback.widget.VerticalGridView;

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

    private View tabView;
    public void setTabView(View tabView) {
        this.tabView = tabView;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (tabView != null) {
                        if (tabView.getVisibility() != View.VISIBLE) {
                            tabView.setVisibility(View.VISIBLE);
                        }
                        tabView.requestFocus();
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
