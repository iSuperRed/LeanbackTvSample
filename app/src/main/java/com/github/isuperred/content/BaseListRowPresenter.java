package com.github.isuperred.content;

import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowPresenter;


public class BaseListRowPresenter extends ListRowPresenter {
    @Override
    protected void onRowViewAttachedToWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewAttachedToWindow(vh);
        if (getOnItemViewClickedListener() != null) {
            vh.setOnItemViewClickedListener(getOnItemViewClickedListener());
        }
    }

    @Override
    protected void onRowViewDetachedFromWindow(RowPresenter.ViewHolder vh) {
        super.onRowViewDetachedFromWindow(vh);
        if (getOnItemViewClickedListener() != null) {
            vh.setOnItemViewClickedListener(null);
        }
    }

    private BaseOnItemViewClickedListener onItemViewClickedListener;

    public void setOnItemViewClickedListener(BaseOnItemViewClickedListener onItemViewClickedListener) {
        this.onItemViewClickedListener = onItemViewClickedListener;
    }

    public BaseOnItemViewClickedListener getOnItemViewClickedListener() {
        return onItemViewClickedListener;
    }
}
