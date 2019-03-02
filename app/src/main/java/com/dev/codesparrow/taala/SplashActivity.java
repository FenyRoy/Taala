package com.dev.codesparrow.taala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = this.getSharedPreferences(getPackageName(),MODE_PRIVATE);

        isLoggedIn = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isLoggedIn",false);

        Toast.makeText(this, String.valueOf(isLoggedIn), Toast.LENGTH_SHORT).show();

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.top_bar));

        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    if(isLoggedIn){
                        startActivity(mainIntent);
                    }
                    else if(!isLoggedIn) {
                        startActivity(loginIntent);
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();

    }
}
