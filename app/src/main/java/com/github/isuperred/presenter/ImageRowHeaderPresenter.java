package com.github.isuperred.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RestrictTo;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;

import com.github.isuperred.R;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

public class ImageRowHeaderPresenter extends RowHeaderPresenter {

    private final int mLayoutResourceId;
    private final boolean mAnimateSelect;

    public ImageRowHeaderPresenter() {
        this(R.layout.lb_img_row_header);
    }

    /**
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP_PREFIX)
    public ImageRowHeaderPresenter(int layoutResourceId) {
        this(layoutResourceId, true);
    }

    /**
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP_PREFIX)
    public ImageRowHeaderPresenter(int layoutResourceId, boolean animateSelect) {
        mLayoutResourceId = layoutResourceId;
        mAnimateSelect = animateSelect;
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);
        HeadViewHolder viewHolder =  new HeadViewHolder(root);
        if (mAnimateSelect) {
            setSelectLevel(viewHolder, 0);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        HeaderItem headerItem = item == null ? null : ((Row) item).getHeaderItem();
        if (headerItem == null) {
            if ( viewHolder.view.findViewById(R.id.row_header) != null) {
                ((TextView)viewHolder.view.findViewById(R.id.row_header)).setText(null);
            }
            viewHolder.view.setContentDescription(null);
            viewHolder.view.setVisibility(View.GONE);

        } else {
            if (viewHolder.view.findViewById(R.id.row_header) != null) {
                ((TextView)viewHolder.view.findViewById(R.id.row_header)).setText(headerItem.getName());
            }
            viewHolder.view.setContentDescription(headerItem.getContentDescription());
            viewHolder.view.setVisibility(View.VISIBLE);
        }
    }

    public static class HeadViewHolder extends ViewHolder {

        public HeadViewHolder(View view) {
            super(view);

        }
    }
}
