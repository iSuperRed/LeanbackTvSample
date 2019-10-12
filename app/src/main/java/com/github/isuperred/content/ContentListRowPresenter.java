package com.github.isuperred.content;

import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;

import com.github.isuperred.R;


public class ContentListRowPresenter extends BaseListRowPresenter {

    private static final String TAG = "ContentListRowPresenter";

    @Override
    protected void initializeRowViewHolder(final RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);

        final ViewHolder rowViewHolder = (ViewHolder) holder;
        rowViewHolder.getGridView().setHorizontalSpacing(
                rowViewHolder.getGridView().getContext().getResources().getDimensionPixelSize(R.dimen.px48));
        RowHeaderPresenter.ViewHolder headerViewHolder = holder.getHeaderViewHolder();
        final TextView tv = headerViewHolder.view.findViewById(R.id.row_header);
        tv.setTextColor(tv.getContext().getResources().getColor(R.color.colorWhite));
        tv.setPadding(0, 20, 0, 20);
        tv.setTextSize(tv.getContext().getResources().getDimensionPixelSize(R.dimen.px48));
        rowViewHolder.getGridView().setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_ITEM);

        setOnItemViewClickedListener(new BaseOnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder,
                                      Object item, RowPresenter.ViewHolder rowViewHolder, Object row) {
                if (item instanceof Content.DataBean.WidgetsBean) {
                    Toast.makeText(tv.getContext(),
                            "位置:" + ((ViewHolder) rowViewHolder).getGridView().getSelectedPosition(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
