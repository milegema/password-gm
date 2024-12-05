package com.bitwormhole.passwordgm.utils;

public class Time {

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Logs.warn("", e);
        }
    }
}
