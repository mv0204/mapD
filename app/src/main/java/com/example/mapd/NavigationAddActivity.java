package com.example.mapd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mapd.db.MyDbHandler;
import com.example.mapd.map.MapsActivity;
import com.example.mapd.progressBarClass.LoadingDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NavigationAddActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private static final int REQUEST_CODE = 100;
    ImageView defence, map, safeGirl, helpLine;
    FusedLocationProviderClient fusedLocationProviderClient;
    Button alert;
    ArrayList<DmModel> list;

    SmsManager smsManager;
    FloatingActionButton buttonContact;
    MyDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_add);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            db = new MyDbHandler(getApplicationContext());
        }                           //db
        catch (Exception e){
            e.printStackTrace();
        }              //db


        //______________________________


        safeGirl = findViewById(R.id.imageViewSafeGirl);
        defence = findViewById(R.id.imageViewDefence);
        map = findViewById(R.id.imageViewMap);
        helpLine = findViewById(R.id.imageViewHelpLine);
        alert = findViewById(R.id.buttonAlert);
        buttonContact = findViewById(R.id.buttonContact);



        LoadingDialog loadingDialog = new LoadingDialog(NavigationAddActivity.this);





        helpLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HelplineActivity.class));
            }
        });
        defence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Defence.class));
            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLastLocation();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });
        buttonContact.setOnClickListener(new View.OnClickListener() {               //>>>>>>>>>>>>>>CONTACT
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });//>>>>>>>>>>>>>>CONTACT


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        safeGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = db.viewData();
                for (DmModel d : list) {
                    String n = d.getKEY_NAME();
                    String p = d.getKEY_PHONE();
                    smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(p, null
                            , "Hello " + n + " , I have reached my destination safely (●'◡'●)", null, null);
                }
            }
        });


        //______________________
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( NavigationAddActivity.this,
                drawerLayout,toolbar,R.string.Opendrawer,R.string.Closedrawer);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.optHome) {
                    Intent intent =new Intent(getApplicationContext(),NavigationAddActivity.class);
                    startActivity(intent);
                } else if (id == R.id.optTips) {
                    Intent intent =new Intent(getApplicationContext(), SafetyTipsActivity.class);
                    startActivity(intent);

                } else if (id == R.id.opthelp) {
                    Intent intent= new Intent(getApplicationContext(),Help.class);
                    startActivity(intent);
                } else if(id==R.id.opUpdate){
                    loadingDialog.startLoadingDialog(2000);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent =new Intent(getApplicationContext(), AppUpdateInfo.class);
                            startActivity(intent);

                        }
                    },2000);

                }
                else if(id==R.id.oplaw) {
                    Intent intent = new Intent(getApplicationContext(),Laws.class);
                    startActivity(intent);
                }
                else {
                    Intent intent =new Intent(getApplicationContext(),NavigationAddActivity.class);
                    startActivity(intent);

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    //_____________________
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = null;
                        smsManager = SmsManager.getDefault();
                        MyDbHandler db = new MyDbHandler(getApplicationContext());


                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        list = db.viewData();

                        for (DmModel d : list) {
                            String p = d.getKEY_PHONE();
                            smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(p, null,
                                    "Location is :" + addresses.get(0).getAddressLine(0)+"\nCoordinates are:("+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude()+")", null, null);
                        }


                    }
                }
            });
        } else {
            askPermissions();

        }
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(NavigationAddActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Required permissions", Toast.LENGTH_SHORT).show();
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//_______________________


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_exit:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
            case R.id.item_aboutUs:
                AlertDialog.Builder builder=new AlertDialog.Builder(NavigationAddActivity.this);
                builder.setTitle("Developers").setMessage("Developers Name:" +
                                " Manisha And Mohit \nFor contacting us Visit:\ninsta @so_called_engineers._ ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
