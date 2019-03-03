package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DownloadActivity extends AppCompatActivity {

    Button webBtn,fileBtn,continueBtn;
    KeyPairGenerator kpg;
    KeyPair kp,keys;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted,result,ans,filename,xml;
    Integer x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        webBtn=findViewById(R.id.webview);
        fileBtn=findViewById(R.id.uploadFile);
        continueBtn=findViewById(R.id.Continue);
        filename ="Key_Values";

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x>=2) {
                    loadpassintent();
                }
            }
        });

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview();
                x++;
            }
        });

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/xml");
                startActivityForResult(intent, 7);
                x++;
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

        Intent OtpIntent = new Intent(DownloadActivity.this, OtpActivity.class);
        startActivity(OtpIntent);

    }

    private void webview(){

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
