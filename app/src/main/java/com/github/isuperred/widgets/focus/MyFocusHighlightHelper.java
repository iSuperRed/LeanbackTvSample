package com.github.isuperred.widgets.focus;

import android.animation.TimeAnimator;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.leanback.graphics.ColorOverlayDimmer;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.ShadowOverlayContainer;
import androidx.leanback.widget.ShadowOverlayHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.github.isuperred.R;
import com.github.isuperred.widgets.focus.FocusHighlightHandler;

import static androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_LARGE;
import static androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_MEDIUM;
import static androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_NONE;
import static androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_SMALL;
import static androidx.leanback.widget.FocusHighlight.ZOOM_FACTOR_XSMALL;

public class MyFocusHighlightHelper {
    public static final int ZOOM_FACTOR_XXSMALL = 5;
    public static final int ZOOM_FACTOR_XXXSMALL = 6;
    static boolean isValidZoomIndex(int zoomIndex) {
        return zoomIndex == ZOOM_FACTOR_NONE || getResId(zoomIndex) > 0;
    }

    static int getResId(int zoomIndex) {
        switch (zoomIndex) {
            case ZOOM_FACTOR_SMALL:
                return R.fraction.lb_focus_zoom_factor_small;
            case ZOOM_FACTOR_XSMALL:
                return R.fraction.lb_focus_zoom_factor_xsmall;
            case ZOOM_FACTOR_XXSMALL:
                return R.fraction.lb_focus_zoom_factor_xxsmall;
            case ZOOM_FACTOR_XXXSMALL:
                return R.fraction.lb_focus_zoom_factor_xxxsmall;
            case ZOOM_FACTOR_MEDIUM:
                return R.fraction.lb_focus_zoom_factor_medium;
            case ZOOM_FACTOR_LARGE:
                return R.fraction.lb_focus_zoom_factor_large;
            default:
                return 0;
        }
    }


    static class FocusAnimator implements TimeAnimator.TimeListener {
        private final View mView;
        private final int mDuration;
        private final ShadowOverlayContainer mWrapper;
        private final float mScaleDiff;
        private float mFocusLevel = 0f;
        private float mFocusLevelStart;
        private float mFocusLevelDelta;
        private final TimeAnimator mAnimator = new TimeAnimator();
        private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
        private final ColorOverlayDimmer mDimmer;

        void animateFocus(boolean select, boolean immediate) {
            endAnimation();
            final float end = select ? 1 : 0;
            if (immediate) {
                setFocusLevel(end);
            } else if (mFocusLevel != end) {
                mFocusLevelStart = mFocusLevel;
                mFocusLevelDelta = end - mFocusLevelStart;
                mAnimator.start();
            }
        }

        FocusAnimator(View view, float scale, boolean useDimmer, int duration) {
            mView = view;
            mDuration = duration;
            mScaleDiff = scale - 1f;
            if (view instanceof ShadowOverlayContainer) {
                mWrapper = (ShadowOverlayContainer) view;
            } else {
                mWrapper = null;
            }
            mAnimator.setTimeListener(this);
            if (useDimmer) {
                mDimmer = ColorOverlayDimmer.createDefault(view.getContext());
            } else {
                mDimmer = null;
            }
        }

        void setFocusLevel(float level) {
            mFocusLevel = level;
            float scale = 1f + mScaleDiff * level;
            mView.setScaleX(scale);
            mView.setScaleY(scale);
            if (mWrapper != null) {
                mWrapper.setShadowFocusLevel(level);
            } else {
                ShadowOverlayHelper.setNoneWrapperShadowFocusLevel(mView, level);
            }
            if (mDimmer != null) {
                mDimmer.setActiveLevel(level);
                int color = mDimmer.getPaint().getColor();
                if (mWrapper != null) {
                    mWrapper.setOverlayColor(color);
                } else {
                    ShadowOverlayHelper.setNoneWrapperOverlayColor(mView, color);
                }
            }
        }

        float getFocusLevel() {
            return mFocusLevel;
        }

        void endAnimation() {
            mAnimator.end();
        }

        @Override
        public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
            float fraction;
            if (totalTime >= mDuration) {
                fraction = 1;
                mAnimator.end();
            } else {
                fraction = (float) (totalTime / (double) mDuration);
            }
            if (mInterpolator != null) {
                fraction = mInterpolator.getInterpolation(fraction);
            }
            setFocusLevel(mFocusLevelStart + fraction * mFocusLevelDelta);
        }
    }

    public static class BrowseItemFocusHighlight implements FocusHighlightHandler {
        private static final int DURATION_MS = 150;

        private int mScaleIndex;
        private final boolean mUseDimmer;

        public BrowseItemFocusHighlight(int zoomIndex, boolean useDimmer) {
            if (!isValidZoomIndex(zoomIndex)) {
                throw new IllegalArgumentException("Unhandled zoom index");
            }
            mScaleIndex = zoomIndex;
            mUseDimmer = useDimmer;
        }

        private float getScale(Resources res) {
            return mScaleIndex == ZOOM_FACTOR_NONE ? 1f :
                    res.getFraction(getResId(mScaleIndex), 1, 1);
        }

        @Override
        public void onItemFocused(View view, boolean hasFocus) {
            view.setSelected(hasFocus);
            getOrCreateAnimator(view).animateFocus(hasFocus, false);
        }

        @Override
        public void onInitializeView(View view) {
            getOrCreateAnimator(view).animateFocus(false, true);
        }

        private FocusAnimator getOrCreateAnimator(View view) {
            FocusAnimator animator = (FocusAnimator) view.getTag(R.id.lb_focus_animator);
            if (animator == null) {
                animator = new FocusAnimator(
                        view, getScale(view.getResources()), mUseDimmer, DURATION_MS);
                view.setTag(R.id.lb_focus_animator, animator);
            }
            return animator;
        }

    }

    static class HeaderItemFocusHighlight implements FocusHighlightHandler {
        private boolean mInitialized;
        private float mSelectScale;
        private int mDuration;

        HeaderItemFocusHighlight() {
        }

        void lazyInit(View view) {
            if (!mInitialized) {
                Resources res = view.getResources();
                TypedValue value = new TypedValue();
                res.getValue(R.dimen.lb_browse_header_select_scale, value, true);
                mSelectScale = value.getFloat();
                res.getValue(R.dimen.lb_browse_header_select_duration, value, true);
                mDuration = value.data;
                mInitialized = true;
            }
        }

        static class HeaderFocusAnimator extends FocusAnimator {

            ItemBridgeAdapter.ViewHolder mViewHolder;
            HeaderFocusAnimator(View view, float scale, int duration) {
                super(view, scale, false, duration);

                ViewParent parent = view.getParent();
                while (parent != null) {
                    if (parent instanceof RecyclerView) {
                        break;
                    }
                    parent = parent.getParent();
                }
                if (parent != null) {
                    mViewHolder = (ItemBridgeAdapter.ViewHolder) ((RecyclerView) parent)
                            .getChildViewHolder(view);
                }
            }

            @Override
            void setFocusLevel(float level) {
                Presenter presenter = mViewHolder.getPresenter();
                if (presenter instanceof RowHeaderPresenter) {
                    ((RowHeaderPresenter) presenter).setSelectLevel(
                            ((RowHeaderPresenter.ViewHolder) mViewHolder.getViewHolder()), level);
                }
                super.setFocusLevel(level);
            }

        }

        private void viewFocused(View view, boolean hasFocus) {
            lazyInit(view);
            view.setSelected(hasFocus);
            FocusAnimator animator = (FocusAnimator) view.getTag(R.id.lb_focus_animator);
            if (animator == null) {
                animator = new HeaderItemFocusHighlight.HeaderFocusAnimator(view, mSelectScale, mDuration);
                view.setTag(R.id.lb_focus_animator, animator);
            }
            animator.animateFocus(hasFocus, false);
        }

        @Override
        public void onItemFocused(View view, boolean hasFocus) {
            viewFocused(view, hasFocus);
        }

        @Override
        public void onInitializeView(View view) {
        }

    }

}
