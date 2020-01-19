package com.github.isuperred.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;
import com.github.isuperred.bean.AppInfo;
import com.github.isuperred.utils.FontDisplayUtil;

public class AppInstalledPresenter extends Presenter {
    private Context mContext;

    private static final String TAG = "MoviePresenter";

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_app_installed, parent, false);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                v.findViewById(R.id.tv_app_name).setSelected(hasFocus);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof AppInfo) {
            ViewHolder vh = (ViewHolder) viewHolder;
            if (((AppInfo) item).icon != null) {
                Bitmap bitmap = getBitmapFromDrawable(((AppInfo) item).icon);//适配Android 8.0
                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitmap);
                drawable.setCornerRadius(FontDisplayUtil.dip2px(mContext, 10));
                vh.mIvAppIcon.setImageDrawable(drawable);
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

    private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }
}
