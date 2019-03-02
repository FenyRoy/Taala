package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class LoginActivity extends AppCompatActivity {

    Button LoginButton;
    EditText UidText;
    KeyPairGenerator kpg;
    KeyPair kp,keys;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted,result,ans,filename;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        filename ="Key_Values";


        try {

            String input;
            input= ParseJSON("feny","10-09-19","Pukkunnel House","Roy Paul");
            
            String encoded=encryptThisString(input);


            Toast.makeText(this, encoded, Toast.LENGTH_SHORT).show();
            result = RSAEncrypt(encoded);
            Log.i("Err",result);
            Toast.makeText(getBaseContext(), result,Toast.LENGTH_SHORT).show();

            ans= RSADecrypt(result);
            System.out.println("Result is"+ans);
            Toast.makeText(getBaseContext(), ans,Toast.LENGTH_LONG).show();

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(),Toast.LENGTH_LONG).show();

        }

        UidText = findViewById(R.id.UidText);


        LoginButton = findViewById(R.id.LgnBtn);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UidText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "UID Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Intent OtpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                    startActivity(OtpIntent);
                }

            }
        });
    }

    public String ParseJSON(String name, String dob, String address, String father) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("dob", dob);
            jsonObject.put("address", address);
            jsonObject.put("father", father);

            return jsonObject.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();

        keys=kp;
        saveToFile();
        byte[] publicKeyBytes = publicKey.getEncoded();
        String publicKeyBytesBase64 = new String(Base64.encode(publicKeyBytes, Base64.DEFAULT));

        PrivateKey privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String privateKeyBytesBase64 = new String(Base64.encode(privateKeyBytes, Base64.DEFAULT));

        Toast.makeText(this, publicKeyBytesBase64, Toast.LENGTH_LONG).show();
        Toast.makeText(this, privateKeyBytesBase64, Toast.LENGTH_LONG).show();

        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptedBytes = cipher.doFinal(plain.getBytes());
        encrypted = new String(encryptedBytes);
        System.out.println("EEncrypted?????"+encrypted);
        return encrypted;

    }

    public String RSADecrypt (final String result) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {

        cipher1=Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher1.doFinal(result.getBytes());
        decrypted = new String(decryptedBytes);
        System.out.println("DDecrypted?????"+decrypted);
        return decrypted;
    }

    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile() {
        FileOutputStream fos1;
        try {
            fos1 = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos1);
            oos.writeObject(keys);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
