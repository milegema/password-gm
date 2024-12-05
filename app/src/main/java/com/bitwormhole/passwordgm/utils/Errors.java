package com.bitwormhole.passwordgm.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public final class Errors {


    public static final int LOG = 0x01;
    public static final int TOAST = 0x02;
    public static final int ALERT = 0x04;


    private Errors() {
    }

    public static void handle(Context ctx, Throwable err) {
        handle(ctx, err, LOG | TOAST);
    }

    public static void handle(Context ctx, Throwable err, int flags) {
        if (err == null) {
            return;
        }
        if (ctx == null) {
            flags = LOG;
        }
        tryHandleWithAlert(ctx, err, flags);
        tryHandleWithLog(ctx, err, flags);
        tryHandleWithToast(ctx, err, flags);
    }


    // private methods

    private static void tryHandleWithAlert(Context ctx, Throwable err, int flags) {
        if ((flags & ALERT) == 0) {
            return;
        }
        String msg = err.getLocalizedMessage();
        AlertDialog.Builder b = new AlertDialog.Builder(ctx);
        b.setTitle("Error").setMessage(msg);
        b.setPositiveButton("close", (p1, p2) -> {
        });
        b.show();
    }

    private static void tryHandleWithToast(Context ctx, Throwable err, int flags) {
        if ((flags & TOAST) == 0) {
            return;
        }
        String msg = err.getLocalizedMessage();
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static void tryHandleWithLog(Context ctx, Throwable err, int flags) {
        if ((flags & LOG) == 0) {
            return;
        }
        Logs.error("error", err);
    }
}
