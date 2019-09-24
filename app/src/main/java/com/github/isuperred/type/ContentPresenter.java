package com.github.isuperred.type;

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
import com.github.isuperred.content.Content;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/24.
 * ClassName  :
 * Description  :
 */
public class ContentPresenter extends Presenter {

    private Context mContext;

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content_type1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Content.DataBean.WidgetsBean) {
            ViewHolder vh = (ViewHolder) viewHolder;
            Glide.with(mContext)
                    .load(R.drawable.movie)
                    .apply(new RequestOptions().override((int) mContext.getResources().getDimension(R.dimen.px300),
                            (int) mContext.getResources().getDimension(R.dimen.px450)))
                    .into(vh.mIvPoster);
            String desc = ((Content.DataBean.WidgetsBean) item).getDesc();
            if (!TextUtils.isEmpty(desc)) {
                vh.mTvDesc.setText(desc);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final TextView mTvDesc;
        private final ImageView mIvPoster;

        public ViewHolder(View view) {
            super(view);
            mTvDesc = view.findViewById(R.id.tv_desc);
            mIvPoster = view.findViewById(R.id.iv_poster);
        }
    }

}
