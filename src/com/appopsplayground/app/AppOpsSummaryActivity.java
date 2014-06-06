package com.athoc.panic.appopsplayground.app;

import android.preference.PreferenceActivity;

import com.android.settings.applications.AppOpsSummary;

/**
 * Double Encore, Inc.
 * Date: 6/5/14
 * AppOpsSummaryActivity
 */
public class AppOpsSummaryActivity extends PreferenceActivity {

    @Override
    public boolean isValidFragment(String className) {
        if (AppOpsSummary.class.getName().equals(className)) {
            return true;
        }
        return super.isValidFragment(className);
    }
}
