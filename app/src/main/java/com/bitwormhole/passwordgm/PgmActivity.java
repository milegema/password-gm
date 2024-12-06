package com.bitwormhole.passwordgm;

import android.app.Activity;

import com.bitwormhole.passwordgm.ui.FrontContext;

public class PgmActivity extends Activity {

    private final FrontContext frontContext;

    public PgmActivity() {
        this.frontContext = new FrontContext(this);
    }

    public FrontContext getFrontContext() {
        return frontContext;
    }
}
