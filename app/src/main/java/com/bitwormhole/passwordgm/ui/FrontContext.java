package com.bitwormhole.passwordgm.ui;

import android.app.Activity;

public final class FrontContext {

    private final FrontLifeManager lifeManager;
    private final Activity activity;

    public FrontContext(Activity owner) {
        this.activity = owner;
        this.lifeManager = new FrontLifeManagerImpl();
    }

    public FrontLifeManager getLifeManager() {
        return lifeManager;
    }

    public Activity getActivity() {
        return activity;
    }
}
