package com.bitwormhole.passwordgm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class DebugActivity extends PgmActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_debug);

        this.setupButtonToStartActivity(R.id.button_login, LoginActivity.class);
        this.setupButtonToStartActivity(R.id.button_unlock, UnlockActivity.class);
    }

    private void setupButtonToStartActivity(int res_id, Class<?> activity_class) {
        final Context ctx = this;
        findViewById(res_id).setOnClickListener((view) -> {
            ctx.startActivity(new Intent(ctx, activity_class));
        });
    }
}
