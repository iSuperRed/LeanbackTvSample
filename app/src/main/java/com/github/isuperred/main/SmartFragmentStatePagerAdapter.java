package com.github.isuperred.main;

import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/27.
 * ClassName  :
 * Description  :
 */
public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "SmartSVodContent";
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        Log.e(TAG, "instantiateItem: " + registeredFragments.size());
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        Log.e(TAG, "destroyItem: " + registeredFragments.size());
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
