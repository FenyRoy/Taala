package com.dev.codesparrow.taala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UidText;
    KeyPair keypair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        keypair = generateKeys();

        final byte[] publicKey = keypair.getPublic().getEncoded();
        final byte[] privateKey = keypair.getPrivate().getEncoded();


        UidText = findViewById(R.id.UidText);

        LoginButton = findViewById(R.id.LgnBtn);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UidText.getText().toString().isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "UID Empty", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, publicKey.toString()+" diff "+privateKey.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent OtpIntent = new Intent(LoginActivity.this,OtpActivity.class);
                    startActivity(OtpIntent);
                }


            }
        });
    }

    public KeyPair generateKeys() {
        KeyPair keyPair = null;
        try {
            // get instance of rsa cipher
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);            // initialize key generator
            keyPair = keyGen.generateKeyPair(); // generate pair of keys
        } catch(GeneralSecurityException e) {
            System.out.println(e);
        }
        return keyPair;
    }
}
