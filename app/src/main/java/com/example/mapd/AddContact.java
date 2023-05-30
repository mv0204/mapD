package com.example.mapd;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapd.db.MyDbHandler;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
public class AddContact extends AppCompatActivity {
    TextInputEditText name, phoneNo;Button insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        name = findViewById(R.id.editTextName);
        phoneNo = findViewById(R.id.editTextPhoneNo);
        insert = findViewById(R.id.insertbtn);
        MyDbHandler db = new MyDbHandler(this);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName = name.getText().toString();
                if (sName.isEmpty()) {
                    name.setError("Enter a name !");
                } else if (phoneNo.getText().toString().isEmpty()) {
                    phoneNo.setError("Enter a Phone number");
                } else if (phoneNo.getText().toString().length() < 10 || phoneNo.getText().toString().length() > 10) {
                    phoneNo.setError("Enter a valid no");
                } else {
                    ArrayList<DmModel> list = db.viewData();
                    ArrayList<String> sList = new ArrayList<>();
                    for (DmModel d : list) {
                        sList.add(d.getKEY_NAME());
                    }
                    if (sList.contains(name.getText().toString())) {
                        name.setError("Enter a name that is not previously used!");
                        name.setText("");

                    } else {
                        boolean res = db.insertData(name.getText().toString(), phoneNo.getText().toString());
                        if (res) {
                            Toast.makeText(AddContact.this, "Successful", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            phoneNo.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }


                }
                finish();

            }

        });

    }
}