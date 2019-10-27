package com.github.isuperred.presenter;

import android.widget.Toast;

import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;

import com.github.isuperred.R;
import com.github.isuperred.base.BaseListRowPresenter;
import com.github.isuperred.bean.Content;


public class TypeFiveListRowPresenter extends BaseListRowPresenter {
    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;

        rowViewHolder.getGridView().setHorizontalSpacing((int) rowViewHolder.getGridView().getResources().getDimension(R.dimen.px48));
        rowViewHolder.getGridView().setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);

        setOnItemViewClickedListener(new BaseOnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder,
                                      Object item, RowPresenter.ViewHolder rowViewHolder, Object row) {
                if (item instanceof Content.DataBean.WidgetsBean) {
                    Toast.makeText(((ViewHolder) rowViewHolder).getGridView().getContext(),
                            "位置:" + ((ViewHolder) rowViewHolder).getGridView().getSelectedPosition(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
