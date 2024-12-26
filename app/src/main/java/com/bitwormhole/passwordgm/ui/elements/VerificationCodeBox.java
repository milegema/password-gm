package com.bitwormhole.passwordgm.ui.elements;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.R;
import com.bitwormhole.passwordgm.tasks.Promise;
import com.bitwormhole.passwordgm.tasks.Result;
import com.bitwormhole.passwordgm.utils.Errors;
import com.bitwormhole.passwordgm.utils.Time;

public class VerificationCodeBox extends LinearLayout {

    private MyRetryController mController;
    private Runnable sender;

    public VerificationCodeBox(Context context) {
        super(context);
        this.init_layout(context);
    }

    public VerificationCodeBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init_layout(context);
    }

    public VerificationCodeBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init_layout(context);
    }

    public VerificationCodeBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init_layout(context);
    }


    private void init_layout(Context ctx) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.element_verification_code_box, this);
        MyRetryController controller = new MyRetryController();
        controller.editText = v.findViewById(R.id.edit_text_v_code);
        controller.button = v.findViewById(R.id.button_send_v_code);
        controller.retryTimerMax = 1000 * 60;
        controller.handler = new Handler();
        controller.button.setOnClickListener(this::handleClickSendCode);
        this.mController = controller;
    }

    private class MyRetryController implements Runnable {

        private EditText editText;
        private Button button;
        private int retryTimerMax;
        private int retryTimerNow;
        private Handler handler;


        public void start() {
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            final VerificationCodeBox self = VerificationCodeBox.this;
            final int step = 1000;
            int current = this.retryTimerMax;
            for (; current > 0; current -= step) {
                this.retryTimerNow = current;
                this.handler.post(self::updateUI);
                Time.sleep(step);
            }
            this.retryTimerNow = 0;
            this.handler.post(self::updateUI);
        }
    }

    private void updateUI() {
        final MyRetryController controller = this.mController;
        int now = controller.retryTimerNow / 1000;
        String text = this.getContext().getString(R.string.send_v_code);
        boolean en = true;
        if (now > 0) {
            text = text + "(" + now + ")";
            en = false;
        }
        controller.button.setEnabled(en);
        controller.button.setText(text);
    }


    private void startRetryTimer() {
        MyRetryController controller = this.mController;
        controller.button.setEnabled(false);
        controller.start();
    }

    private void handleClickSendCode(View v) {
        final Activity activity = (Activity) v.getContext();
        final VerificationCodeBox self = this;
        this.mController.button.setEnabled(false);
        Promise.init(activity, MyRetryController.class).Try(() -> {
            Result<MyRetryController> res = new Result<>();
            run(self.sender);
            res.setValue(null);
            return res;
        }).Then((res) -> {
            startRetryTimer();
            return res;
        }).Catch((res) -> {
            Errors.handle(activity, res.getError());
            this.mController.button.setEnabled(true);
            return res;
        }).start();
    }

    private static void run(Runnable r) {
        if (r == null) {
            return;
        }
        try {
            r.run();
        } catch (Exception e) {
            Time.sleep(3000);
            throw new RuntimeException(e);
        }
    }

    public String getVerificationCode() {
        EditText et = this.mController.editText;
        return et.getText().toString();
    }

    public Runnable getSender() {
        return sender;
    }

    public void setSender(Runnable sender) {
        this.sender = sender;
    }
}
