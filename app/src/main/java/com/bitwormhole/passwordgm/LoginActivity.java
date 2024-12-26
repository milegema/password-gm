package com.bitwormhole.passwordgm;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bitwormhole.passwordgm.ui.elements.VerificationCodeBox;

public class LoginActivity extends PgmActivity {

    private EditText mEditEmailAddress;
    private VerificationCodeBox mVCodeBox;


    private String mStringEmailAddress;
    private String mStringVCode;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mEditEmailAddress = findViewById(R.id.edit_text_email);
        mVCodeBox = findViewById(R.id.input_v_code);

        mVCodeBox.setSender(this::runSendCodeTask);
        this.setupButtonHandler(R.id.button_login, this::handleClickLogin);
        this.setupButtonHandler(R.id.button_next_step, this::handleClickNextStep);
    }

    private void setupButtonHandler(int id, View.OnClickListener l) {
        this.findViewById(id).setOnClickListener(l);
    }


    private void handleClickNextStep(View v) {
        this.mStringEmailAddress = this.mEditEmailAddress.getText().toString();
    }

    private void handleClickLogin(View v) {
    }

    private void runSendCodeTask() {




    }

}
