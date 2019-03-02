package com.dev.codesparrow.taala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UidText = findViewById(R.id.UidText);


        LoginButton = findViewById(R.id.LgnBtn);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UidText.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "UID Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent OtpIntent = new Intent(LoginActivity.this,OtpActivity.class);
                    startActivity(OtpIntent);
                }


            }
        });
    }

    public String ParseJSON(String name,String dob,String address,String father){
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);
            jsonObject.put("dob",dob);
            jsonObject.put("address",address);
            jsonObject.put("father",father);

            return jsonObject.toString();

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
