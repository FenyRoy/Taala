package com.dev.codesparrow.taala;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    Button mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainBtn = findViewById(R.id.MainBtn);

        String timeSettings = android.provider.Settings.System.getString(
                this.getContentResolver(),
                android.provider.Settings.System.AUTO_TIME);
        if (timeSettings.contentEquals("0")) {
            android.provider.Settings.System.putString(
                    this.getContentResolver(),
                    android.provider.Settings.System.AUTO_TIME, "1");
        }
        Date now = new Date(System.currentTimeMillis());
        Toast.makeText(this, "Date "+now.toString(), Toast.LENGTH_LONG).show();

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();

            }
        });


        

    }


}
