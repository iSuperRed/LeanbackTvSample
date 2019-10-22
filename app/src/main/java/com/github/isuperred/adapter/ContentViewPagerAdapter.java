package com.github.isuperred.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.github.isuperred.adapter.SmartFragmentStatePagerAdapter;
import com.github.isuperred.content.ContentFragment;
import com.github.isuperred.bean.Title;

import java.util.List;


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
