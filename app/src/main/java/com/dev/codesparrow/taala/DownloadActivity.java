package com.dev.codesparrow.taala;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class DownloadActivity extends AppCompatActivity {

    Button webBtn,fileBtn,continueBtn;
    Integer x=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        webBtn=findViewById(R.id.webview);
        fileBtn=findViewById(R.id.uploadFile);
        continueBtn=findViewById(R.id.Continue);

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

}
