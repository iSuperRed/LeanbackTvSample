package com.github.isuperred.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.github.isuperred.R;
import com.github.isuperred.bean.Video;


public class EpisodeGroupPresenter extends Presenter {

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode_group_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if(item instanceof Video.DataBean.EpisodeBean){
            Video.DataBean.EpisodeBean episodeBean = (Video.DataBean.EpisodeBean) item;
            ViewHolder vh = (ViewHolder) viewHolder;
            vh.mTvEpisodeGroup.setText(episodeBean.getGroupName());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {

    }

    public static class ViewHolder extends Presenter.ViewHolder {

        private TextView mTvEpisodeGroup;

        ViewHolder(View view) {
            super(view);
            mTvEpisodeGroup = view.findViewById(R.id.tv_episode_group);
        }
    }
}
