package com.bitwormhole.passwordgm.utils;

import android.util.Log;

public final class Logs {

    private static final String TAG = Logs.class.getName();

    private Logs() {
    }


    public static void debug(String msg) {
        Log.d(TAG, msg);
    }

    public static void debug(String msg, Throwable err) {
        Log.d(TAG, msg, err);
    }


    public static void info(String msg) {
        Log.i(TAG, msg);
    }

    public static void info(String msg, Throwable err) {
        Log.i(TAG, msg, err);
    }


    public static void warn(String msg) {
        Log.w(TAG, msg);
    }

    public static void warn(String msg, Throwable err) {
        Log.w(TAG, msg, err);
    }


    public static void error(String msg) {
        Log.e(TAG, msg);
    }

    public static void error(String msg, Throwable err) {
        Log.e(TAG, msg, err);
    }
}
