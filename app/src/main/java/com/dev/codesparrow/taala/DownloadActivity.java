package com.dev.codesparrow.taala;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static java.lang.Thread.sleep;

public class DownloadActivity extends AppCompatActivity {

    Button webBtn,fileBtn,continueBtn;
    TextView mytextA,mytext5;
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    KeyPairGenerator kpg;
    KeyPair kp,keys;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    byte [] encryptedBytes,decryptedBytes;
    Cipher cipher,cipher1;
    String encrypted,decrypted,result,ans,filename,xml,Username;
    Integer x=0;
    private List<String> listItems;
    static String myData ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Intent intent = getIntent();
        Username = intent.getExtras().getString("user");

        listItems = new ArrayList<>();
        webBtn=findViewById(R.id.webview);
        fileBtn=findViewById(R.id.uploadFile);
        continueBtn=findViewById(R.id.Continue);

        mytextA=findViewById(R.id.mytextA);
        mytext5=findViewById(R.id.mytext5);

        filename ="Key_Values";



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.clear();
                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference users = db.collection("users");
                DocumentReference docRef = users.document("username");
                Task<DocumentSnapshot> ref = docRef.get();
                ref.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(getBaseContext(), "Sorry you already have another account",Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, String> data1 = new HashMap<>();
                                data1.put("name", "feny");
                                listItems.add("feny");
                                data1.put("dob", "10-09-19");
                                listItems.add("10-09-19");
                                data1.put("address", "Pukkunnel House");
                                listItems.add("Pukkunnel House");
                                data1.put("father", "Roy Paul");
                                listItems.add("Roy Paul");
                                data1.put("signature", "gjdhghughrduo");
                                data1.put("hash", "grfgjirjg");

                                FileOutputStream fos1;
                                try {
                                    fos1 = getApplicationContext().openFileOutput("userdata", Context.MODE_PRIVATE);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos1);
                                    oos.writeObject(listItems);
                                    oos.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Task<Void> reff = users.document(Username).set(data1);

                                reff.addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        try {
                                            sleep(10000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        loadpassintent();
                                    }
                                });
                                reff.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(DownloadActivity.this, "Upload Failed Try Again", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                Toast.makeText(getBaseContext(), "Success",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            task.getException().printStackTrace();
                            Log.d("Firestore", "get failed with ", task.getException());
                            Toast.makeText(getBaseContext(), "Firestore Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ref.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(DownloadActivity.this, "Upload Failed Try Again", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview();
                x++;
                mytextA.setVisibility(View.VISIBLE);
                mytext5.setVisibility(View.VISIBLE);
                fileBtn.setVisibility(View.VISIBLE);


            }
        });

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/xml");
                startActivityForResult(intent, 7);
                x++;
                continueBtn.setVisibility(View.VISIBLE);
            }
        });

        try {

            JSONObject inputObject;
            inputObject= JSONify("feny","10-09-19","Pukkunnel House","Roy Paul");
            String input;
            input = ParseJSON("feny","10-09-19","Pukkunnel House","Roy Paul");

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
                    Uri uri=data.getData();
                    LaterFunction(uri);

                }
        }

    }

    public void LaterFunction(Uri uri) {
        BufferedReader br;
        FileOutputStream os;
        try {
            br = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            //WHAT TODO ? Is this creates new file with
            //the name NewFileName on internal app storage?

            String line = null;
            while ((line = br.readLine()) != null) {
                myData = myData+line;
            }
            lastFunction("newFileName");


            Toast.makeText(this, myData, Toast.LENGTH_SHORT).show();
            JSONObject xmlJSONObj = XML.toJSONObject(myData);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            Toast.makeText(this, jsonPrettyPrintString, Toast.LENGTH_SHORT).show();

            Toast.makeText(this, jsonPrettyPrintString, Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void lastFunction(String newFileName) {
        //WHAT TODO? How to read line line the file
        //now from internal app storage?
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

    public JSONObject JSONify(String name, String dob, String address, String father) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("dob", dob);
            jsonObject.put("address", address);
            jsonObject.put("father", father);

            return jsonObject;

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
        System.out.println("Decrypted?????"+decrypted);
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
