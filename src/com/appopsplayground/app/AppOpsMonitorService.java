package com.appopsplayground.app;

import android.app.AppOpsManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.util.Log;

public class AppOpsMonitorService extends Service {
    private static final String TAG = "AppOpsMonitoringService";

    public static final String EXTRA_PACKAGE = "com.doubleencore.appopsmonitoringservice.EXTRA_PACKAGE";

    public static void startMonitoringPackage(Context context, PackageInfo info) {
        Intent intent = new Intent(context, AppOpsMonitorService.class);
        intent.putExtra(EXTRA_PACKAGE, info);
        context.startService(intent);
    }

    AppOpsWrapper mAppOps;

    private AppOpsManager.OnOpChangedListener mOpChangedListener = new AppOpsManager.OnOpChangedListener() {
        @Override
        public void onOpChanged(String op, String packageName) {
            Log.v(TAG, op + " changed in " + packageName);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service Initializing...");
        mAppOps = new AppOpsWrapper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return START_STICKY;
        if (intent.getExtras() == null) return START_STICKY;

        PackageInfo packageToWatch = intent.getParcelableExtra(EXTRA_PACKAGE);

        if (packageToWatch == null) return START_STICKY;

        final String packageName = packageToWatch.packageName;

        Log.i(TAG, "Beginning monitoring for " + packageToWatch);
        mAppOps.startWatchingMode(AppOpsWrapper.OP_COARSE_LOCATION, packageName, mOpChangedListener);
        mAppOps.startWatchingMode(AppOpsWrapper.OP_FINE_LOCATION, packageName, mOpChangedListener);
        mAppOps.startWatchingMode(AppOpsWrapper.OP_GPS, packageName, mOpChangedListener);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Terminating...");
        mAppOps.stopWatchingMode(mOpChangedListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
