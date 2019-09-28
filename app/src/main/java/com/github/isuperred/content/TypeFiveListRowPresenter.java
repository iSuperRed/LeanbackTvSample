package com.github.isuperred.content;

import androidx.leanback.widget.RowPresenter;

import com.github.isuperred.R;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/28.
 * ClassName  :
 * Description  :
 */
public class TypeFiveListRowPresenter extends TypeZeroListRowPresenter {
    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;
        rowViewHolder.getGridView().setNumRows(2);
        rowViewHolder.getGridView().setVerticalSpacing((int) rowViewHolder.getGridView().getResources().getDimension(R.dimen.px48));
    }
}
