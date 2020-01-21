package com.github.isuperred.content;

import androidx.leanback.widget.ListRow;

import com.github.isuperred.base.BasePresenterSelector;
import com.github.isuperred.bean.Footer;
import com.github.isuperred.bean.TypeSeven;
import com.github.isuperred.presenter.row.ContentListRowPresenter;
import com.github.isuperred.presenter.ImageRowHeaderPresenter;
import com.github.isuperred.presenter.TypeFiveContentPresenter;
import com.github.isuperred.presenter.row.TypeFiveListRowPresenter;
import com.github.isuperred.presenter.TypeFooterPresenter;
import com.github.isuperred.presenter.TypeFourContentPresenter;
import com.github.isuperred.presenter.TypeOneContentPresenter;
import com.github.isuperred.presenter.TypeSevenPresenter;
import com.github.isuperred.presenter.TypeSixContentPresenter;
import com.github.isuperred.presenter.TypeThreeContentPresenter;
import com.github.isuperred.presenter.TypeTwoContentPresenter;
import com.github.isuperred.presenter.TypeZeroContentPresenter;
import com.github.isuperred.presenter.row.TypeZeroListRowPresenter;


public class ContentPresenterSelector extends BasePresenterSelector {
    public ContentPresenterSelector() {
        ContentListRowPresenter listRowPresenter = new ContentListRowPresenter();
        listRowPresenter.setShadowEnabled(false);
        listRowPresenter.setSelectEffectEnabled(false);
        listRowPresenter.setKeepChildForeground(false);


        TypeZeroListRowPresenter listRowPresenterOne = new TypeZeroListRowPresenter();
        listRowPresenterOne.setShadowEnabled(false);
        listRowPresenterOne.setSelectEffectEnabled(false);
        listRowPresenterOne.setKeepChildForeground(false);

        addClassPresenter(ListRow.class, listRowPresenterOne, TypeZeroContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeOneContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeTwoContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeThreeContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeFourContentPresenter.class);

        TypeFiveListRowPresenter listRowPresenterFive = new TypeFiveListRowPresenter();
        listRowPresenterFive.setShadowEnabled(false);
        listRowPresenterFive.setSelectEffectEnabled(false);
        listRowPresenterFive.setKeepChildForeground(false);
        listRowPresenterFive.setHeaderPresenter(new ImageRowHeaderPresenter());

        addClassPresenter(ListRow.class, listRowPresenterFive, TypeFiveContentPresenter.class);
        addClassPresenter(ListRow.class, listRowPresenterOne, TypeSixContentPresenter.class);

        addClassPresenter(Footer.class, new TypeFooterPresenter());

        addClassPresenter(TypeSeven.class, new TypeSevenPresenter());

    }

}
