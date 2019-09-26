package com.github.isuperred.content;

import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;

import com.github.isuperred.type.ContentPresenter;
import com.github.isuperred.type.TypeOneContentPresenter;
import com.github.isuperred.type.TypeThreeContentPresenter;
import com.github.isuperred.type.TypeTwoContentPresenter;
import com.github.isuperred.type.TypeZeroContentPresenter;

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

        ListRowPresenter listRowPresenterOne = new TypeZeroListRowPresenter();
        listRowPresenterOne.setShadowEnabled(false);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeZeroContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeOneContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeTwoContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeThreeContentPresenter.class);
    }

}
