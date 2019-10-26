package com.github.isuperred.widgets;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import androidx.constraintlayout.widget.Group;
import androidx.leanback.widget.VerticalGridView;

import com.github.isuperred.R;

import java.lang.ref.WeakReference;

public class TabVerticalGridView extends VerticalGridView {

    private static final String TAG = "TabVerticalGridView";
    private static final int EAT_KEY_EVENT = 10010;// 是否屏蔽按键的事件，控制按键的频率
    private static final Long KEY_EVENT_TIME = 50L; //最短的按键事件应该是在 KEY_EVENT_TIME ms
    private static boolean eatKeyEvent = false;
    private Handler mHandler;

    private static class MyHandler extends Handler {

        private final WeakReference<Activity> mActivity;

        MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {

            Activity activity = mActivity.get();
            if (activity != null
                    && msg.what == EAT_KEY_EVENT) {
                eatKeyEvent = false;
            }
        }
    }

    public TabVerticalGridView(Context context) {
        this(context, null);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabVerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHandler = new MyHandler((Activity) context);

    }

    private View mTabView;
    private Group mGroup;
    private Animation mShakeY;
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

//    @Override
//    public View focusSearch(View focused, int direction) {
//        if (focused != null) {
//            final FocusFinder ff = FocusFinder.getInstance();
//            final View found = ff.findNextFocus(this, focused, direction);
//
//            if (direction == View.FOCUS_DOWN) {
//                if (found == null && getScrollState() == SCROLL_STATE_IDLE) {
//                    if(mShakeY){
//
//                    }
//                    focused.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.host_shake_y));
//                    return null;
//                }
//            }
//        }
//        return super.focusSearch(focused, direction);
//    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
            return super.dispatchKeyEvent(event);
        }

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            isPressDown = false;
            isPressUp = false;
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    isPressDown = true;
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    isPressUp = true;
                    if (getSelectedPosition() == 0) {
                        mTabView.requestFocus();
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_BACK:
                    backToTop();
                    return true;
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
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    handled = arrowScroll(FOCUS_DOWN);
                    break;
            }
        }
        return handled;
    }

    public boolean arrowScroll(int direction) {
        Log.e(TAG, "arrowScroll direction: "+direction );

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
            if (direction == FOCUS_DOWN) {
                if (currentFocused != null && getScrollState() == SCROLL_STATE_IDLE) {
                    if (mShakeY == null) {
                        mShakeY = AnimationUtils.loadAnimation(getContext(), R.anim.host_shake_y);
                    }
                    currentFocused.clearAnimation();
                    currentFocused.startAnimation(mShakeY);

                }
                handled = true;
            }
        }
        return handled;
    }

    public void backToTop() {
        if (mTabView != null) {
            if (mGroup != null && mGroup.getVisibility() != View.VISIBLE) {
                mGroup.setVisibility(View.VISIBLE);
            }
            mTabView.requestFocus();
        }
        scrollToPosition(0);
    }
}
