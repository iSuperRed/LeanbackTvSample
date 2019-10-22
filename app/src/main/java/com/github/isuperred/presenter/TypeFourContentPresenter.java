package com.github.isuperred.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.isuperred.R;
import com.github.isuperred.bean.Content;


public class TypeFourContentPresenter extends Presenter {
    private Context mContext;

    private static final String TAG = "TypeFourContentPresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_four_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Content.DataBean.WidgetsBean) {
            ViewHolder vh = (ViewHolder) viewHolder;
            Glide.with(mContext)
                    .load(((Content.DataBean.WidgetsBean) item).getUrl())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .override((int) mContext.getResources().getDimension(R.dimen.px400),
                                    (int) mContext.getResources().getDimension(R.dimen.px222))
                            .placeholder(R.drawable.shape_default))
                    .into(vh.mIvTypeFourPoster);
            if (!TextUtils.isEmpty(((Content.DataBean.WidgetsBean) item).getName())) {
                vh.mIvTypeFourName.setText(((Content.DataBean.WidgetsBean) item).getName());
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final ImageView mIvTypeFourPoster;
        private final TextView mIvTypeFourName;

        public ViewHolder(View view) {
            super(view);
            mIvTypeFourPoster = view.findViewById(R.id.iv_type_four_poster);
            mIvTypeFourName = view.findViewById(R.id.tv_type_four_name);
        }
    }
}

