package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import androidx.leanback.widget.VerticalGridView;

public class AppVerticalGridView extends VerticalGridView {

    private static final String TAG = "AppVerticalGridView";
    private int mNumColumns;

    public AppVerticalGridView(Context context) {
        this(context, null);
    }

    public AppVerticalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppVerticalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setColumnNumbers(int numColumns) {
        this.mNumColumns = numColumns;
        setNumColumns(numColumns);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    if (getSelectedPosition() > 0) {
                        setSelectedPosition(0);
                        return true;
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:

                    if (mNumColumns == 0 || getAdapter() == null) {
                        break;
                    }

                    int itemCount = getAdapter().getItemCount();
                    if (itemCount + 1 < mNumColumns || itemCount % mNumColumns == 0) {//这2种情况不需要单独处理
                        break;
                    }

                    int currentPosition = getSelectedPosition();
                    int lineNumbers = itemCount / mNumColumns + 1;
                    int currentLine;
                    if (currentPosition + 1 % mNumColumns == 0) {
                        currentLine = currentPosition / mNumColumns;
                    } else {
                        currentLine = currentPosition/ mNumColumns + 1;
                    }
                    if ((currentLine == lineNumbers - 1)//倒数第二行
                            && ((currentPosition + 1 + mNumColumns) > itemCount)) {
                        setSelectedPositionSmooth(itemCount - 1);
                        return true;
                    }
                    break;
            }

        }
        return super.dispatchKeyEvent(event);
    }
}
