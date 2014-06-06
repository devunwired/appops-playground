package com.athoc.panic.appopsplayground.app;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DetailActivity extends Activity {

    private PackageInfo mPackageInfo;
    private AppOpsWrapper mAppOps;

    private TextView mPackageText, mLocStatusText, mNoteStatusText;
    private Button mLocAllowButton, mLocBlockButton;
    private Button mNoteAllowButton, mNoteBlockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mLocAllowButton = (Button) findViewById(R.id.btn_loc_allow);
        mLocBlockButton = (Button) findViewById(R.id.btn_loc_block);
        mNoteAllowButton = (Button) findViewById(R.id.btn_note_allow);
        mNoteBlockButton = (Button) findViewById(R.id.btn_note_block);

        mPackageText = (TextView) findViewById(R.id.text_package);

        mLocStatusText = (TextView) findViewById(R.id.text_loc_status);
        mNoteStatusText = (TextView) findViewById(R.id.text_note_status);

        mPackageInfo = getIntent().getParcelableExtra(AppOpsMonitorService.EXTRA_PACKAGE);
        if (mPackageInfo == null) {
            Toast.makeText(this, "PackageInfo Required", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mAppOps = new AppOpsWrapper(this);

        updateDisplay();
    }

    public void onBlockClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loc_block:
                setLocationMode(AppOpsManager.MODE_IGNORED);
                break;
            case R.id.btn_note_block:
                setNotificationMode(AppOpsManager.MODE_IGNORED);
                break;
            default:
                //Do nothing
                return;
        }

        updateDisplay();
    }

    public void onAllowClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loc_allow:
                setLocationMode(AppOpsManager.MODE_ALLOWED);
                break;
            case R.id.btn_note_allow:
                setNotificationMode(AppOpsManager.MODE_ALLOWED);
                break;
            default:
                //Do nothing
                return;
        }

        updateDisplay();
    }

    public void onMonitorClick(View v) {
        AppOpsMonitorService.startMonitoringPackage(this, mPackageInfo);
        Toast.makeText(this, "Package Monitoring Added", Toast.LENGTH_SHORT).show();
    }

    private void setLocationMode(int mode) {
        mAppOps.setMode(AppOpsWrapper.OP_COARSE_LOCATION, mPackageInfo.applicationInfo.uid, mPackageInfo.packageName, mode);
        mAppOps.setMode(AppOpsWrapper.OP_FINE_LOCATION, mPackageInfo.applicationInfo.uid, mPackageInfo.packageName, mode);
        mAppOps.setMode(AppOpsWrapper.OP_GPS, mPackageInfo.applicationInfo.uid, mPackageInfo.packageName, mode);
    }

    private void setNotificationMode(int mode) {
        mAppOps.setMode(AppOpsWrapper.OP_POST_NOTIFICATION, mPackageInfo.applicationInfo.uid, mPackageInfo.packageName, mode);
    }

    private void updateDisplay() {
        mPackageText.setText(mPackageInfo.packageName);

        //Location Operations
        int result = mAppOps.checkOpNoThrow(AppOpsWrapper.OP_COARSE_LOCATION,
                mPackageInfo.applicationInfo.uid, mPackageInfo.packageName);
        if (result == AppOpsManager.MODE_ALLOWED) {
            mLocBlockButton.setEnabled(true);
            mLocAllowButton.setEnabled(false);

            mLocStatusText.setText("Location operations are currently allowed.");
        } else {
            mLocBlockButton.setEnabled(false);
            mLocAllowButton.setEnabled(true);

            mLocStatusText.setText("Location operations are currently blocked!");
        }

        //Notification Operations
        result = mAppOps.checkOpNoThrow(AppOpsWrapper.OP_POST_NOTIFICATION,
                mPackageInfo.applicationInfo.uid, mPackageInfo.packageName);
        if (result == AppOpsManager.MODE_ALLOWED) {
            mNoteBlockButton.setEnabled(true);
            mNoteAllowButton.setEnabled(false);

            mNoteStatusText.setText("Notification operations are currently allowed.");
        } else {
            mNoteBlockButton.setEnabled(false);
            mNoteAllowButton.setEnabled(true);

            mNoteStatusText.setText("Notification operations are currently blocked!");
        }
    }
}
