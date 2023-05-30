package com.example.mapd;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mapd.db.MyDbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DmModel> userlist;
    SwipeRefreshLayout swipeRefreshLayout;
    RecAdapter recAdapter;
    ArrayList<RecModel> userlist2;
    FloatingActionButton addData;
    MyDbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.rec);
        swipeRefreshLayout= findViewById(R.id.swipe);

        addData=findViewById(R.id.floatingActionButton);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userlist.size()>=10){
                    Toast.makeText(MainActivity.this,"You can only add maximum 10 numbers\n" +
                            "Please delete a number or more",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,AddContact.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }}
        });

        db= new MyDbHandler(this);
        userlist=db.viewData();
        db.close();
        userlist2=new ArrayList<>();
        for(int i=0;i<userlist.size();i++){
            userlist2.add(new RecModel(R.drawable.u, userlist.get(i).getKEY_NAME(),userlist.get(i).getKEY_PHONE()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recAdapter=new RecAdapter(MainActivity.this,userlist2,this);
        recyclerView.setAdapter(recAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                recreate();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                overridePendingTransition(0, 0);
                startActivity(i);
                overridePendingTransition(0, 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }
}