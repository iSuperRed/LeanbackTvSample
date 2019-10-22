package com.github.isuperred.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.VerticalGridView;

import com.github.isuperred.R;
import com.github.isuperred.widgets.TabVerticalGridView;

public class TypeFooterPresenter extends Presenter {
    private Context mContext;

    private static final String TAG = "TypeFooterPresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_footer_layout, parent, false);
        view.findViewById(R.id.cl_back_to_top)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getParent().getParent() instanceof TabVerticalGridView) {
                            ((TabVerticalGridView) v.getParent().getParent()).backToTop();
                        }
                    }
                });
        view.findViewById(R.id.cl_look_around).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "随心看", Toast.LENGTH_SHORT).show();
                    }
                });
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
