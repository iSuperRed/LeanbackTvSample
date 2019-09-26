package com.github.isuperred.content;

import android.widget.TextView;

import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;

import com.github.isuperred.R;

/**
 * Copyright  : 2015-2033 Beijing Startimes Communication & Network Technology Co.Ltd
 * Created by（dongch） on 2019/9/25.
 * ClassName  :
 * Description  :
 */
public class TypeZeroListRowPresenter extends ListRowPresenter {
    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;

        rowViewHolder.getGridView().setHorizontalSpacing((int) rowViewHolder.getGridView().getResources().getDimension(R.dimen.px48));
        rowViewHolder.getGridView().setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);

        RowHeaderPresenter.ViewHolder vh = rowViewHolder.getHeaderViewHolder();
        TextView textView = vh.view.findViewById(R.id.row_header);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorWhite));
        textView.setTextSize(textView.getContext().getResources().getDimensionPixelSize(R.dimen.px32));
        textView.setBackgroundResource(R.color.colorAccent);
        textView.setPadding(0, 0, 0, 20);
        rowViewHolder.getGridView().setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);


    }
}
