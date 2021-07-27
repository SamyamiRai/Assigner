package com.example.assignerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.ObjectsCompat;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {

    ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        listView=findViewById(R.id.listView);


        ArrayList<Person >arrayList = new ArrayList<>();
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));
        arrayList.add(new Person(R.drawable.man,"Shamanth","Information Science"));

        Person personAdapter =new Person(this,R.layout.list_row,arrayList);
        listView.setAdapter(personAdapter);
    }
}