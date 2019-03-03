package com.dev.codesparrow.taala;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

public class CheckActivity extends AppCompatActivity {

    CheckBox nameChk,addressChk,dobChk,genderChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        nameChk=findViewById(R.id.nameChkBox);
        addressChk=findViewById(R.id.addressChkBox);
        dobChk=findViewById(R.id.dobChkBox);
        genderChk=findViewById(R.id.genderChkBox);

        nameChk.setChecked(true);
        addressChk.setChecked(true);
        dobChk.setChecked(true);
        genderChk.setChecked(true);


    }
}
