package com.github.isuperred.presenter;

import android.annotation.SuppressLint;
import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.widget.BaseOnItemViewClickedListener;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.RowPresenter;

import com.github.isuperred.R;
import com.github.isuperred.base.BaseListRowPresenter;
import com.github.isuperred.bean.Content;
import com.github.isuperred.utils.FontDisplayUtil;


public class ContentListRowPresenter extends BaseListRowPresenter {

    private static final String TAG = "ContentListRowPresenter";

    @SuppressLint("RestrictedApi")
    @Override
    protected void initializeRowViewHolder(final RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);

        final ViewHolder rowViewHolder = (ViewHolder) holder;
        rowViewHolder.getGridView().setHorizontalSpacing(
                FontDisplayUtil.dip2px(rowViewHolder.getGridView().getContext(), 24));
        RowHeaderPresenter.ViewHolder headerViewHolder = holder.getHeaderViewHolder();
        final TextView tv = headerViewHolder.view.findViewById(R.id.row_header);
        tv.setTextColor(tv.getContext().getResources().getColor(R.color.colorWhite));
        tv.setPadding(0, 20, 0, 20);
        tv.setTextSize(FontDisplayUtil.dip2px(rowViewHolder.getGridView().getContext(), 24));
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
