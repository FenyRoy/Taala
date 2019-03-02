package com.dev.codesparrow.taala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    boolean isLoggedIn;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = this.getSharedPreferences(getPackageName(),MODE_PRIVATE);

        isLoggedIn = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("isLoggedIn",false);


        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.top_bar));

        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    Intent passIntent = new Intent(SplashActivity.this, PassActivity.class);
                    passIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    if(isLoggedIn){
                        startActivity(passIntent);
                        finish();
                    }
                    else if(!isLoggedIn) {
                        startActivity(loginIntent);
                        finish();
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
