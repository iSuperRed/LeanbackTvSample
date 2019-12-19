package com.github.isuperred.widgets.focus;

import android.view.View;
import android.widget.AdapterView;

import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;

public abstract class MyItemBridgeAdapter extends ItemBridgeAdapter {

    protected MyItemBridgeAdapter(ObjectAdapter adapter) {
        super(adapter, null);
    }

    @Override
    protected void onBind(final ViewHolder viewHolder) {
        if (getOnItemViewClickedListener() != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemViewClickedListener().onItemClicked(v, viewHolder.getViewHolder(),
                            viewHolder.getItem());

                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getOnItemViewLongClickedListener() != null) {
                        return getOnItemViewLongClickedListener().onItemLongClicked(v, viewHolder.getViewHolder(),
                                viewHolder.getItem());
                    }
                    return true;
                }
            });
        }
        if (getOnItemFocusChangedListener() != null) {
            viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    getOnItemFocusChangedListener().onItemFocusChanged(v, viewHolder.getViewHolder(),
                            viewHolder.getItem(), hasFocus);
                }
            });
        }
        super.onBind(viewHolder);
    }

    @Override
    protected void onUnbind(ViewHolder viewHolder) {
        super.onUnbind(viewHolder);
        viewHolder.itemView.setOnClickListener(null);
        if (getOnItemFocusChangedListener() != null) {
            viewHolder.itemView.setOnFocusChangeListener(null);
        }
    }

    public abstract OnItemViewClickedListener getOnItemViewClickedListener();

    public OnItemViewLongClickedListener getOnItemViewLongClickedListener() {
        return null;
    }

    public OnItemFocusChangedListener getOnItemFocusChangedListener() {
        return null;
    }

    public interface OnItemViewClickedListener {
        void onItemClicked(View focusView, Presenter.ViewHolder itemViewHolder, Object item);
    }

    public interface OnItemViewLongClickedListener {
        boolean onItemLongClicked(View focusView, Presenter.ViewHolder itemViewHolder, Object item);
    }

    public interface OnItemFocusChangedListener {
        void onItemFocusChanged(View focusView, Presenter.ViewHolder itemViewHolder, Object item, boolean hasFocus);
    }
}
