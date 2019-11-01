package com.github.isuperred.widgets.focus;

import android.view.View;

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
                    getOnItemViewClickedListener().onItemClicked(viewHolder.getViewHolder(),
                            viewHolder.getItem());

                }
            });
        }
        super.onBind(viewHolder);
    }

    @Override
    protected void onUnbind(ViewHolder viewHolder) {
        super.onUnbind(viewHolder);
        viewHolder.itemView.setOnClickListener(null);

    }

    public abstract OnItemViewClickedListener getOnItemViewClickedListener();

    public interface OnItemViewClickedListener{
        void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item);
    }

}
