package com.android.settings;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;

import com.athoc.panic.appopsplayground.app.R;

/**
 * Double Encore, Inc.
 * Date: 6/5/14
 * Utils
 * Extraction of pieces necessary for AppOps from com.android.settings.Utils
 */
public class Utils {

    public static final String APP_CHG = "chg";

    /**
     * Prepare a custom preferences layout, moving padding to {@link ListView}
     * when outside scrollbars are requested. Usually used to display
     * {@link ListView} and {@link TabWidget} with correct padding.
     */
    public static void prepareCustomPreferencesList(
            ViewGroup parent, View child, View list, boolean ignoreSidePadding) {
        final boolean movePadding = list.getScrollBarStyle() == View.SCROLLBARS_OUTSIDE_OVERLAY;
        if (movePadding && parent instanceof PreferenceFrameLayout) {
            ((PreferenceFrameLayout.LayoutParams) child.getLayoutParams()).removeBorders = true;

            final Resources res = list.getResources();
            final int paddingSide = res.getDimensionPixelSize(R.dimen.settings_side_margin);
            final int paddingBottom = res.getDimensionPixelSize(
                    com.android.internal.R.dimen.preference_fragment_padding_bottom);

            final int effectivePaddingSide = ignoreSidePadding ? 0 : paddingSide;
            list.setPaddingRelative(effectivePaddingSide, 0, effectivePaddingSide, paddingBottom);
        }
    }
}
