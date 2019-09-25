package com.github.isuperred.content;

import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowHeaderPresenter;

import com.github.isuperred.type.ContentPresenter;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/24.
 * ClassName  :
 * Description  :
 */
public class ContentPresenterSelector extends BasePresenterSelector {
    public ContentPresenterSelector() {
        ListRowPresenter listRowPresenter = new ContentListRowPresenter();
        listRowPresenter.setShadowEnabled(false);

        addClassPresenter(ListRow.class, listRowPresenter, ContentPresenter.class);
    }

}
