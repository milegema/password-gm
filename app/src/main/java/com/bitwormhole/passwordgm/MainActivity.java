package com.bitwormhole.passwordgm;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class MainActivity extends PgmActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bootstrap.boot(this);
    }
}
