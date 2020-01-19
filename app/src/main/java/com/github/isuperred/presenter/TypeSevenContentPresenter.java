package com.github.isuperred.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.leanback.widget.FocusHighlight;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.isuperred.R;
import com.github.isuperred.bean.Content;
import com.github.isuperred.utils.FontDisplayUtil;
import com.github.isuperred.widgets.ImgConstraintLayout;
import com.github.isuperred.widgets.focus.MyFocusHighlightHelper;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/12/19.
 * ClassName  :
 * Description  :
 */
public class TypeSevenContentPresenter extends Presenter {
    public Context mContext;
    private MyFocusHighlightHelper.BrowseItemFocusHighlight mBrowseItemFocusHighlight;

    private static final String TAG = "TypeSevenContentPresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mBrowseItemFocusHighlight == null) {
            mBrowseItemFocusHighlight =
                    new MyFocusHighlightHelper
                            .BrowseItemFocusHighlight(FocusHighlight.ZOOM_FACTOR_MEDIUM, false);
        }
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_seven_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mClTypeSeven.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mBrowseItemFocusHighlight != null) {
                    mBrowseItemFocusHighlight.onItemFocused(v, hasFocus);
                }
                viewHolder.mClTypeSeven.onFocusChange(v, hasFocus);
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Content.DataBean.WidgetsBean) {
            ViewHolder vh = (ViewHolder) viewHolder;
            if (((Content.DataBean.WidgetsBean) item).isBigPic()) {
                ImgConstraintLayout.LayoutParams params = (ImgConstraintLayout.LayoutParams) vh.mIvTypeSevenPoster.getLayoutParams();
                params.width = FontDisplayUtil.dip2px(mContext, 420);
                params.height =   FontDisplayUtil.dip2px(mContext, 246);
                vh.mClTypeSeven.setLayoutParams(params);


                Glide.with(mContext)
                        .load(((Content.DataBean.WidgetsBean) item).getUrl())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .override(FontDisplayUtil.dip2px(mContext, 420),
                                        FontDisplayUtil.dip2px(mContext, 246))
                                .placeholder(R.drawable.bg_shape_default))
                        .into(vh.mIvTypeSevenPoster);
            } else {
                ImgConstraintLayout.LayoutParams params = (ImgConstraintLayout.LayoutParams) vh.mIvTypeSevenPoster.getLayoutParams();
                params.width = FontDisplayUtil.dip2px(mContext, 198);
                params.height =   FontDisplayUtil.dip2px(mContext, 111);
                vh.mClTypeSeven.setLayoutParams(params);
                Glide.with(mContext)
                        .load(((Content.DataBean.WidgetsBean) item).getUrl())
                        .apply(new RequestOptions()
                                .centerCrop()
                                .override(FontDisplayUtil.dip2px(mContext, 198),
                                          FontDisplayUtil.dip2px(mContext, 111))
                                .placeholder(R.drawable.bg_shape_default))
                        .into(vh.mIvTypeSevenPoster);
            }
        }
    }


    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        final ImageView mIvTypeSevenPoster;
        final ImgConstraintLayout mClTypeSeven;

        public ViewHolder(View view) {
            super(view);
            mIvTypeSevenPoster = view.findViewById(R.id.iv_type_seven_poster);
            mClTypeSeven = view.findViewById(R.id.cl_type_seven);
        }
    }
}
