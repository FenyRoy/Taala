package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.abi.datatypes.Int;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UidText;
    TextView AvailaNoti;
    String Username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AvailaNoti=findViewById(R.id.avaiNoti);

        UidText = findViewById(R.id.UidText);

        LoginButton = findViewById(R.id.LgnBtn);



        UidText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                AvailaNoti.setText("");
                LoginButton.setBackgroundResource(R.drawable.button_disabled_background);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=5){
                    LoginButton.setBackgroundResource(R.drawable.button_background);
                    AvailaNoti.setText("Username Available");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UidText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "UID Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Username=UidText.getText().toString();
                    loadpassintent();
                }
            }
        });
    }



    private void loadpassintent() {

        Intent downloadIntent = new Intent(LoginActivity.this, DownloadActivity.class);
        downloadIntent.putExtra("user", Username);
        startActivity(downloadIntent);

    }




}
