package com.github.isuperred.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;

public class TypeFooterPresenter extends Presenter {
    private Context mContext;

    private static final String TAG = "TypeFooterPresenter";
    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_footer_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {

    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final TextView mIvBackToTop;
        private final TextView mIvLookAround;

        public ViewHolder(View view) {
            super(view);
            mIvBackToTop = view.findViewById(R.id.tv_back_to_top);
            mIvLookAround = view.findViewById(R.id.tv_look_around);
        }
    }
}
