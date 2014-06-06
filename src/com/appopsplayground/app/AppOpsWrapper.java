package com.athoc.panic.appopsplayground.app;

import android.app.AppOpsManager;
import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Double Encore, Inc.
 * Date: 6/2/14
 * AppOpsWrapper
 * Class to expose the necessary methods on AppOpsManager to
 * tinker with toggling operation modes.
 */
public class AppOpsWrapper {

    /** No operation specified. */
    public static final int OP_NONE = -1;
    /** Access to coarse location information. */
    public static final int OP_COARSE_LOCATION = 0;
    /** Access to fine location information. */
    public static final int OP_FINE_LOCATION = 1;
    /** Causing GPS to run. */
    public static final int OP_GPS = 2;

    public static final int OP_VIBRATE = 3;

    public static final int OP_READ_CONTACTS = 4;

    public static final int OP_WRITE_CONTACTS = 5;

    public static final int OP_READ_CALL_LOG = 6;

    public static final int OP_WRITE_CALL_LOG = 7;

    public static final int OP_READ_CALENDAR = 8;

    public static final int OP_WRITE_CALENDAR = 9;

    public static final int OP_WIFI_SCAN = 10;

    public static final int OP_POST_NOTIFICATION = 11;

    public static final int OP_NEIGHBORING_CELLS = 12;

    public static final int OP_CALL_PHONE = 13;

    public static final int OP_READ_SMS = 14;

    public static final int OP_WRITE_SMS = 15;

    public static final int OP_RECEIVE_SMS = 16;

    public static final int OP_RECEIVE_EMERGECY_SMS = 17;

    public static final int OP_RECEIVE_MMS = 18;

    public static final int OP_RECEIVE_WAP_PUSH = 19;

    public static final int OP_SEND_SMS = 20;

    public static final int OP_READ_ICC_SMS = 21;

    public static final int OP_WRITE_ICC_SMS = 22;

    public static final int OP_WRITE_SETTINGS = 23;

    public static final int OP_SYSTEM_ALERT_WINDOW = 24;

    public static final int OP_ACCESS_NOTIFICATIONS = 25;

    public static final int OP_CAMERA = 26;

    public static final int OP_RECORD_AUDIO = 27;

    public static final int OP_PLAY_AUDIO = 28;

    public static final int OP_READ_CLIPBOARD = 29;

    public static final int OP_WRITE_CLIPBOARD = 30;

    public static final int OP_TAKE_MEDIA_BUTTONS = 31;

    public static final int OP_TAKE_AUDIO_FOCUS = 32;

    public static final int OP_AUDIO_MASTER_VOLUME = 33;

    public static final int OP_AUDIO_VOICE_VOLUME = 34;

    public static final int OP_AUDIO_RING_VOLUME = 35;

    public static final int OP_AUDIO_MEDIA_VOLUME = 36;

    public static final int OP_AUDIO_ALARM_VOLUME = 37;

    public static final int OP_AUDIO_NOTIFICATION_VOLUME = 38;

    public static final int OP_AUDIO_BLUETOOTH_VOLUME = 39;

    public static final int OP_WAKE_LOCK = 40;
    /** Continually monitoring location data. */
    public static final int OP_MONITOR_LOCATION = 41;
    /** Continually monitoring location data with a relatively high power request. */
    public static final int OP_MONITOR_HIGH_POWER_LOCATION = 42;

    /* Internal service */
    private AppOpsManager mManager;

    /* Reflected methods */
    private Method mSetModeMethod;
    private Method mResetModesMethod;
    private Method mCheckOpMethod;
    private Method mStartWatchingMethod;

    public AppOpsWrapper(Context context) {
        mManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        //Grab reflected methods
        try {
            mSetModeMethod = mManager.getClass().getMethod("setMode", Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE);
            mCheckOpMethod = mManager.getClass().getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            mResetModesMethod = mManager.getClass().getMethod("resetAllModes");
            mStartWatchingMethod = mManager.getClass().getMethod("startWatchingMode", Integer.TYPE, String.class, AppOpsManager.OnOpChangedListener.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /** Reflected Hidden Methods */

    public int checkOpNoThrow(int code, int uid, String packageName) {
        try {
            Object value = mCheckOpMethod.invoke(mManager, code, uid, packageName);
            return ((Integer) value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void setMode(int code, int uid, String packageName, int mode) {
        try {
            mSetModeMethod.invoke(mManager, code, uid, packageName, mode);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void resetAllModes() {
        try {
            mResetModesMethod.invoke(mManager);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void startWatchingMode(int op, String packageName, AppOpsManager.OnOpChangedListener listener) {
        try {
            mStartWatchingMethod.invoke(mManager, op, packageName, listener);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** Public Pass-throughs */

    public void stopWatchingMode(AppOpsManager.OnOpChangedListener listener) {
        mManager.stopWatchingMode(listener);
    }
}
