package com.github.isuperred.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.isuperred.R;
import com.github.isuperred.bean.AppInfo;

public class AppInstalledPresenter extends Presenter {
    private Context mContext;

    private static final String TAG = "MoviePresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app_installed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof AppInfo) {
            ViewHolder vh = (ViewHolder) viewHolder;
//            RequestOptions options = new RequestOptions()
//                    .placeholder(R.drawable.shape_default)
//                    .error(R.drawable.shape_default);
            Log.e(TAG, "onBindViewHolder: " + (((AppInfo) item).icon != null) + " " + vh.mIvAppIcon);
            if (((AppInfo) item).icon != null) {
                Glide.with(mContext)
                        .load("")
                        .apply(new RequestOptions()
                                .error(((AppInfo) item).icon)
                                .placeholder(((AppInfo) item).icon))
                        .into(vh.mIvAppIcon);
            }
            if (!TextUtils.isEmpty(((AppInfo) item).name)) {
                vh.mTvAppName.setText(((AppInfo) item).name);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private final ImageView mIvAppIcon;
        private final TextView mTvAppName;

        public ViewHolder(View view) {
            super(view);
            mIvAppIcon = view.findViewById(R.id.iv_app_icon);
            mTvAppName = view.findViewById(R.id.tv_app_name);
        }
    }
}
