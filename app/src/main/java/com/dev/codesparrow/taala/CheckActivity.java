package com.dev.codesparrow.taala;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.common.api.Response;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    CheckBox nameChk,addressChk,dobChk,genderChk;
    TextView nameTxt,dobTxt,addrTxt,genderTxt;
    Button cnfrmBtn;

    private List<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        nameChk=findViewById(R.id.nameChkBox);
        addressChk=findViewById(R.id.addressChkBox);
        dobChk=findViewById(R.id.dobChkBox);
        genderChk=findViewById(R.id.genderChkBox);

        nameTxt=findViewById(R.id.text1);
        dobTxt=findViewById(R.id.text2);
        addrTxt=findViewById(R.id.text3);
        genderTxt=findViewById(R.id.text4);

        cnfrmBtn=findViewById(R.id.CnfrmLogin);

        nameChk.setChecked(true);
        addressChk.setChecked(true);
        dobChk.setChecked(true);
        genderChk.setChecked(true);

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
        nameTxt.setText("Name: "+name);
        String DOB = listItems.get(1);
        dobTxt.setText("DOB: "+DOB);
        String address = listItems.get(2);
        addrTxt.setText("Address: "+address);
        String father = listItems.get(3);
        genderTxt.setText("Gender: "+father);

        cnfrmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent mainIntent= new Intent(CheckActivity.this, MainActivity.class);;
               startActivity(mainIntent);
               
            }
        });




    }

}
