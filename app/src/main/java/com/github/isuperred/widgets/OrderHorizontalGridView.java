package com.github.isuperred.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.leanback.widget.HorizontalGridView;

public class OrderHorizontalGridView extends HorizontalGridView {
    public OrderHorizontalGridView(Context context) {
        this(context, null);
    }

    public OrderHorizontalGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderHorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public int getChildDrawingOrder(int childCount, int i) {
        final LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof androidx.recyclerview.widget.GridLayoutManager) {
            final View focusedChild = getFocusedChild();
            return orderChildDrawing(childCount, i, focusedChild);
        }
        return super.getChildDrawingOrder(childCount, i);
    }

    private int orderChildDrawing(int childCount, int i, View focusedChild) {
        if (focusedChild == null) {
            return i;
        }
        int focusIndex = indexOfChild(focusedChild);
        // supposely 0 1 2 3 4 5 6 7 8 9, 4 is the center item
        // drawing order is 0 1 2 3 9 8 7 6 5 4
        if (i < focusIndex) {
            return i;
        } else if (i < childCount - 1) {
            return focusIndex + childCount - 1 - i;
        } else {
            return focusIndex;
        }
    }
}
