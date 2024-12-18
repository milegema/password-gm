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

    private TextView[] mTextMockPassword;
    private MyAnimationDriver mAnimationDriver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mTextMockPassword = this.findPasswordTextViews();
        // mTextMockPassword.setText("");
    }


    private TextView[] findPasswordTextViews() {
        TextView tv1 = findViewById(R.id.text_mock_password_c1);
        TextView tv2 = findViewById(R.id.text_mock_password_c2);
        TextView tv3 = findViewById(R.id.text_mock_password_c3);
        TextView tv4 = findViewById(R.id.text_mock_password_c4);
        TextView tv5 = findViewById(R.id.text_mock_password_c5);
        TextView tv6 = findViewById(R.id.text_mock_password_c6);
        return new TextView[]{tv1, tv2, tv3, tv4, tv5, tv6};
    }


    private class MyAnimationDriver implements Runnable {
        @Override
        public void run() {
            for (int count = 0; isAlive(); count++) {
                this.updateUI(count % 12);
                Time.sleep(200);
            }
        }


        void updateUI(final int char_count) {
            MainActivity.this.runOnUiThread(() -> {
                updateMockPasswordDisplay(char_count);
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

    private void updateMockPasswordDisplay(int char_count) {
        TextView[] all = this.mTextMockPassword;
        for (int i = 0; i < all.length; ++i) {
            TextView tv = all[i];
            tv.setVisibility((i < char_count) ? TextView.VISIBLE : TextView.INVISIBLE);
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
