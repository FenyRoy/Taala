package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

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

    Integer x;
    Button LoginButton;
    EditText UidText;
    KeyPairGenerator kpg;
    KeyPair kp,keys;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted,result,ans,filename,xml;
    TextView AvailaNoti;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x=0;
        filename ="Key_Values";
        AvailaNoti=findViewById(R.id.avaiNoti);

        UidText = findViewById(R.id.UidText);

        FirebaseMessaging.getInstance().subscribeToTopic("mytopic")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "completed";
                        if (!task.isSuccessful()) {
                            msg = "Failed to succeed";
                        }
                        Log.d("Subscribed", msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCMToken", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.d("FCMToken", msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        UidText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                AvailaNoti.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>=5){
                    AvailaNoti.setText("Username Available");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {

            String input;
            input= ParseJSON("feny","10-09-19","Pukkunnel House","Roy Paul");
            
            String encoded=encryptThisString(input);


            Toast.makeText(this, encoded, Toast.LENGTH_SHORT).show();
            result = RSAEncrypt(encoded);
            Log.i("RSA Encrypted: ",result);
            Toast.makeText(getBaseContext(), result,Toast.LENGTH_SHORT).show();

            ans= RSADecrypt(result);
            Log.i("RSA Decrypted: ",result);

            System.out.println("Result is"+ans);
            Toast.makeText(getBaseContext(), ans,Toast.LENGTH_LONG).show();

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();
            Toast.makeText(getBaseContext(), e.toString(),Toast.LENGTH_LONG).show();

        }


        LoginButton = findViewById(R.id.LgnBtn);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UidText.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "UID Empty", Toast.LENGTH_SHORT).show();
                } else {

                    if(x==0)
                    {
                        webview();
                        x=1;
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("text/xml");
                        startActivityForResult(intent, 7);
                    }else
                      loadpassintent();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    FileInputStream fis1;
                    try {





                        File sdcard = Environment.getExternalStorageDirectory();
                        File file = new File(sdcard,PathHolder);
                        StringBuilder text = new StringBuilder();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;

                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                        }
                        catch (IOException e) {
                            Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

//Here We Stopped

//                        fis1 = openFileInput(PathHolder);
//                        ObjectInputStream ois = new ObjectInputStream(fis1);
//                        xml = (String) ois.readObject();
//                        ois.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(this, xml, Toast.LENGTH_SHORT).show();

//                    Toast.makeText(LoginActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void loadpassintent() {

        Intent OtpIntent = new Intent(LoginActivity.this, OtpActivity.class);
        startActivity(OtpIntent);

    }

    private void webview() {


        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://resident.uidai.gov.in/offlineaadhaar"));
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccent));
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

        String pubText = Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
        String priText = Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT);

        keys=kp;
        saveToFile();

        Log.i("Public Key", pubText);
        Log.i("Private Key",priText);


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
