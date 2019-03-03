package com.dev.codesparrow.taala;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.euicc.DownloadableSubscription;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    private static final String LOCALE_KEY = "localekey";
    private static final String HINDI_LOCALE = "hi";
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String LOCALE_PREF_KEY = "localePref";
    private Locale locale;



    Button mainBtn;
    String filename;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    KeyPair keys;
    private List<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listItems = new ArrayList<>();

        FileInputStream fis1;
        try {
            fis1 = openFileInput("userdata");
            ObjectInputStream ois = new ObjectInputStream(fis1);
            listItems = (ArrayList<String>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String name = listItems.get(0);
        String DOB = listItems.get(1);
        String address = listItems.get(2);
        String father = listItems.get(3);
        filename ="Key_Values";

        load_file();
        publicKey=keys.getPublic();
        privateKey=keys.getPrivate();


        Toast.makeText(this, publicKey.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, privateKey.toString(), Toast.LENGTH_SHORT).show();

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

    private void load_file() {
        FileInputStream fis1;
        try {
            fis1 = openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis1);
            keys = (KeyPair) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
