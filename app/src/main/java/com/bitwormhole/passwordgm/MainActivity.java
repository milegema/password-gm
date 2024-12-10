package com.bitwormhole.passwordgm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.boot.Bootstrap;
import com.bitwormhole.passwordgm.tasks.Promise;
import com.bitwormhole.passwordgm.tasks.Result;
import com.bitwormhole.passwordgm.utils.Errors;
import com.bitwormhole.passwordgm.utils.Time;

public class MainActivity extends PgmActivity {


    private TextView mTextMockPassword;
    private MyAnimationDriver mAnimationDriver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mTextMockPassword = findViewById(R.id.text_mock_password);
        mTextMockPassword.setText("");
    }


    private class MyAnimationDriver implements Runnable {
        @Override
        public void run() {
            char[] buffer = new char[6];
            for (int count = 0; isAlive(); count++) {
                this.fill(buffer, count % 12);
                this.updateUI(new String(buffer));
                Time.sleep(200);
            }
        }

        void fill(char[] buffer, int count) {
            final char c1 = '●';
            final char c2 = ' ';
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = (i < count) ? c1 : c2;
            }
        }

        void updateUI(final String text) {
            if (text == null) {
                return;
            }
            MainActivity.this.runOnUiThread(() -> {
                mTextMockPassword.setText(text);
            });
        }


        boolean isAlive() {
            return this.equals(mAnimationDriver);
        }

        public void start() {
            Thread th = new Thread(this);
            th.start();
        }
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


        MyAnimationDriver ad = new MyAnimationDriver();
        mAnimationDriver = ad;
        ad.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAnimationDriver = null;
    }
}
