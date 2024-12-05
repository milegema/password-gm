package com.bitwormhole.passwordgm;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.boot.Bootstrap;
import com.bitwormhole.passwordgm.tasks.Promise;
import com.bitwormhole.passwordgm.tasks.Result;
import com.bitwormhole.passwordgm.utils.Errors;

public class MainActivity extends PgmActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Promise.init(this, Long.class).Try(() -> {

            Bootstrap.boot(this);

            return new Result<>(Long.getLong("000"));
        }).Then((res) -> {

            return res;
        }).Catch((res) -> {
            int flags = Errors.LOG | Errors.ALERT;
            Errors.handle(this, res.getError(), flags);
            return res;
        }).start();
    }
}
