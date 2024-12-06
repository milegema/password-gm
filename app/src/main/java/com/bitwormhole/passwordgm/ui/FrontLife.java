package com.bitwormhole.passwordgm.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

public interface FrontLife {

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
