package com.github.isuperred.base;


import androidx.collection.ArrayMap;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;

import java.util.ArrayList;


public class BasePresenterSelector extends PresenterSelector {
    private final ArrayList<Presenter> mPresenters = new ArrayList<>();

    private final ArrayMap<Class<?>, ArrayMap<Class<?>, Presenter>> mClassMap = new ArrayMap<>();
    private final ArrayMap<Class<?>, Presenter> mClassSingleMap = new ArrayMap<>();

    /**
     * Adds a presenter to be used for the given class.
     * @param cls item 类型
     * @param presenter  presenter
     */
    public void addClassPresenter(Class<?> cls, Presenter presenter) {
        mClassSingleMap.put(cls, presenter);
        if (!mPresenters.contains(presenter)) {
            mPresenters.add(presenter);
        }
    }

    /**
     * Adds a presenter to be used for the given class.
     * @param cls item 类型
     * @param presenter presenter
     * @param childType  当包含多个相同{@param cls}时，并且presenter不同，则通过子item{@param childType}区分
     */
    public void addClassPresenter(Class<?> cls, Presenter presenter, Class<?> childType) {
        ArrayMap<Class<?>, Presenter> classPresenterArrayMap = mClassMap.get(cls);
        if (classPresenterArrayMap == null) {
            classPresenterArrayMap = new ArrayMap<>();
        }
        classPresenterArrayMap.put(childType, presenter);
        mClassMap.put(cls, classPresenterArrayMap);
        if (!mPresenters.contains(presenter)) {
            mPresenters.add(presenter);
        }
    }

    @Override
    public Presenter getPresenter(Object item) {
        Class<?> cls = item.getClass();
        Presenter presenter;
        presenter = mClassSingleMap.get(cls);
        if (presenter != null) {
            return presenter;
        }
        ArrayMap<Class<?>, Presenter> presenters = mClassMap.get(cls);
        if (presenters != null) {
            if (presenters.size() == 1) {
                return presenters.valueAt(0);
            } else if (presenters.size() > 1) {
                if (item instanceof ListRow) {
                    ListRow listRow = (ListRow) item;
                    Presenter childPresenter = listRow.getAdapter().getPresenter(listRow);
                    Class<?> childCls = childPresenter.getClass();
                    do {
                        presenter = presenters.get(childCls);
                        childCls = childCls.getSuperclass();
                    } while (presenter == null && childCls != null);
                } else {
                    throw new NullPointerException("presenter == null, please add presenter to PresenterSelector");
                }
            }
        }
        if (presenter == null) {
            throw new NullPointerException("presenter == null, please add presenter to PresenterSelector");
        }
        return presenter;
    }

    @Override
    public Presenter[] getPresenters() {
        return mPresenters.toArray(new Presenter[0]);
    }

    public void enable(ListRowPresenter listRowPresenter, boolean flag) {
        listRowPresenter.setSelectEffectEnabled(flag);
        listRowPresenter.setShadowEnabled(flag);
        listRowPresenter.setKeepChildForeground(flag);
    }
}
