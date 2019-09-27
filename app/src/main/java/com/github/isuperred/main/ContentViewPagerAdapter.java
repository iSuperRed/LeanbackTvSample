package com.github.isuperred.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.github.isuperred.content.ContentFragment;
import com.github.isuperred.title.Title;

import java.util.List;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/19.
 * ClassName  :
 * Description  :
 */
public class ContentViewPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static final String TAG = "ContentViewPagerAdapter";

    private List<Title.DataBean> dataBeans;

    public ContentViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ContentFragment.newInstance(position, dataBeans.get(position).getTabCode());
    }

    @Override
    public int getCount() {
        return dataBeans == null ? 0 : dataBeans.size();
    }

    public void setData(List<Title.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }
}
