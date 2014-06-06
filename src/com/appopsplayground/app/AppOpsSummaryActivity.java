package com.appopsplayground.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.android.settings.applications.AppOpsCategory;
import com.android.settings.applications.AppOpsDetails;
import com.android.settings.applications.AppOpsSummary;

/**
 * Double Encore, Inc.
 * Date: 6/5/14
 * AppOpsSummaryActivity
 */
public class AppOpsSummaryActivity extends PreferenceActivity {
    private static final String META_DATA_KEY_FRAGMENT_CLASS = "com.android.settings.FRAGMENT_CLASS";

    private String mFragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getMetaData();
        super.onCreate(savedInstanceState);
    }

    private void getMetaData() {
        try {
            ActivityInfo ai = getPackageManager().getActivityInfo(getComponentName(),
                    PackageManager.GET_META_DATA);
            if (ai == null || ai.metaData == null) return;
            
            mFragmentClass = ai.metaData.getString(META_DATA_KEY_FRAGMENT_CLASS);
            Log.d("AppOpsPlayground", "FragmentClass: "+mFragmentClass);
        } catch (NameNotFoundException nnfe) {
            // No recovery
        }
    }

    @Override
    public Intent getIntent() {
    	Intent superIntent = super.getIntent();
    	String startingFragment = getStartingFragmentClass(superIntent);
        Log.d("AppOpsPlayground", "StartingFragment: "+startingFragment);
        if (startingFragment != null && !superIntent.hasExtra(EXTRA_SHOW_FRAGMENT)) {
    	   Intent modIntent = new Intent(superIntent);
    	   modIntent.putExtra(EXTRA_SHOW_FRAGMENT, startingFragment);

           return modIntent;
        }

    	return superIntent;
    }

    protected String getStartingFragmentClass(Intent intent) {
        if (mFragmentClass != null) return mFragmentClass;

        String intentClass = intent.getComponent().getClassName();
        if (intentClass.equals(getClass().getName())) return null;

        return intentClass;
    }

    @Override
    public boolean isValidFragment(String className) {
        if (AppOpsSummary.class.getName().equals(className)
            || AppOpsDetails.class.getName().equals(className)
            || AppOpsCategory.class.getName().equals(className)) {
            return true;
        }
        return super.isValidFragment(className);
    }
}
