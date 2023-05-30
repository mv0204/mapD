package com.example.mapd;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class HelplineActivity extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private TextView callText,callText1,callText2,callText3,callText4;
    private Button callBtn,callBtn1,callBtn2,callBtn3,callBtn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        callText = findViewById(R.id.calltxt);
        callText1 = findViewById(R.id.calltxt1);
        callText2 = findViewById(R.id.calltxt2);
        callText3 = findViewById(R.id.calltxt3);
        callText4 = findViewById(R.id.calltxt4);

        callBtn = findViewById(R.id.callbutton);
        callBtn1 = findViewById(R.id.callbutton1);
        callBtn2 = findViewById(R.id.callbutton2);
        callBtn3 = findViewById(R.id.callbutton3);
        callBtn4 = findViewById(R.id.callbutton4);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton();
            }
        });
        callBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton1();
            }
        });
        callBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton2();
            }
        });
        callBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton3();
            }
        });
        callBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callButton4();
            }
        });
    }

    private void callButton() {
        String number = callText.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton1() {
        String number = callText1.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton2() {
        String number = callText2.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton3() {
        String number = callText3.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton4() {
        String number = callText4.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(HelplineActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RESULT_OK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callButton();
                callButton1();
                callButton2();
                callButton3();
                callButton4();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}