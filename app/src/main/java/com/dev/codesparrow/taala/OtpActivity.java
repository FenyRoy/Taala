package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class OtpActivity extends AppCompatActivity {

    EditText OtpText1;
    EditText OtpText2;
    EditText OtpText3;
    EditText OtpText4;

    int x1,x2,x3,x4;
    String s1,s2,s3,s4;

    Button OtpCnfrmBtn;


    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.OTP1:
                    if(text.length()==1)
                        OtpText2.requestFocus();
                    break;
                case R.id.OTP2:
                    if(text.length()==1)
                        OtpText3.requestFocus();
                    break;
                case R.id.OTP3:
                    if(text.length()==1)
                        OtpText4.requestFocus();
                    break;
                case R.id.OTP4:
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);



        OtpText1 = findViewById(R.id.OTP1);
        OtpText2 = findViewById(R.id.OTP2);
        OtpText3 = findViewById(R.id.OTP3);
        OtpText4 = findViewById(R.id.OTP4);

        OtpCnfrmBtn = findViewById(R.id.CnfrmOtpBtn);

        OtpText1.addTextChangedListener(new GenericTextWatcher(OtpText1));
        OtpText2.addTextChangedListener(new GenericTextWatcher(OtpText2));
        OtpText3.addTextChangedListener(new GenericTextWatcher(OtpText3));
        OtpText4.addTextChangedListener(new GenericTextWatcher(OtpText4));

        OtpCnfrmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1=OtpText1.getText().toString();
                s2=OtpText2.getText().toString();
                s3=OtpText3.getText().toString();
                s4=OtpText4.getText().toString();

                SharedPreferences sharedPreferences = OtpActivity.this.getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = OtpActivity.this.getBaseContext().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();


                if(s1.isEmpty()||s2.isEmpty()||s3.isEmpty()||s4.isEmpty())
                {

                    Toast.makeText(OtpActivity.this,"Invalid OTP",Toast.LENGTH_SHORT).show();

                }else
                {
                    x1=Integer.parseInt(s1);
                    x2=Integer.parseInt(s2);
                    x3=Integer.parseInt(s3);
                    x4=Integer.parseInt(s4);
                    Toast.makeText(OtpActivity.this,"OTP is "+x1+x2+x3+x4+" ",Toast.LENGTH_SHORT).show();
                    String Password= ""+x1+x2+x3+x4+"";
                    FileOutputStream fos1;
                    try {
                        fos1 = getApplicationContext().openFileOutput("screenPass", Context.MODE_PRIVATE);
                        ObjectOutputStream oos = new ObjectOutputStream(fos1);
                        oos.writeObject(Password);
                        oos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent mainIntent = new Intent(OtpActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                }

            }
        });
    }
}
