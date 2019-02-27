package com.dev.codesparrow.taala;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class OtpActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Custom_DialogBox custom_dialog = new Custom_DialogBox(this);
        custom_dialog.show();
    }
}
