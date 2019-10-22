package com.github.isuperred.widgets.focus;

import android.view.View;

import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP_PREFIX;

interface FocusHighlightHandler {
    /**
     * Called when an item gains or loses focus.
     * @hide
     *
     * @param view The view whose focus is changing.
     * @param hasFocus True if focus is gained; false otherwise.
     */
    @RestrictTo(LIBRARY_GROUP_PREFIX)
    void onItemFocused(View view, boolean hasFocus);

    /**
     * Called when the view is being created.
     */
    void onInitializeView(View view);
}
