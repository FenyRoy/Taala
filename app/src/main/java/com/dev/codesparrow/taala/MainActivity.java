package com.dev.codesparrow.taala;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static final String LOCALE_KEY = "localekey";
    private static final String HINDI_LOCALE = "hi";
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String LOCALE_PREF_KEY = "localePref";
    private Locale locale;

    Button mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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



        SharedPreferences sp = getSharedPreferences(LOCALE_PREF_KEY, MODE_PRIVATE);
        String localeString = sp.getString(LOCALE_KEY, ENGLISH_LOCALE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.language: AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                                alertDialogBuilder.setTitle("Select Language");
                                alertDialogBuilder.setPositiveButton("Hindi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                alertDialogBuilder.setNegativeButton("English", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
