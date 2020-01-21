package com.github.isuperred.presenter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;
import com.github.isuperred.bean.Video;


public class EpisodeContentPresenter extends Presenter {

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item instanceof Video.DataBean.EpisodeBean.GroupDetailBean) {
            Video.DataBean.EpisodeBean.GroupDetailBean groupDetailBean = (Video.DataBean.EpisodeBean.GroupDetailBean) item;
            String number = groupDetailBean.getNumber();
            if (!TextUtils.isEmpty(number)) {
                ViewHolder vh = (ViewHolder) viewHolder;
                vh.mTvEpisodeContent.setText(number);
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private TextView mTvEpisodeContent;

        ViewHolder(View view) {
            super(view);
            mTvEpisodeContent = view.findViewById(R.id.tv_episode_content);
        }
    }
}
