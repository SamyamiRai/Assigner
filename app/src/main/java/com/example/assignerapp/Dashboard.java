package com.example.assignerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    private Button logout;
    private RelativeLayout cs, is,ec,me,cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Dashboard.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this, LoginActivity.class));
            }
        });

        cs = findViewById(R.id.computer_sc);
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, cs_faculty_list.class);
                i.putExtra("EXTRA_DEPT_NAME", "CSE");
                startActivity(i);
            }
        });
        is = findViewById(R.id.information_sc);
        is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Dashboard.this, cs_faculty_list.class);
                in.putExtra("EXTRA_DEPT_NAME", "ISE");
                startActivity(in);
            }
        });
        ec = findViewById(R.id.ec);
        ec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, cs_faculty_list.class);
                i.putExtra("EXTRA_DEPT_NAME", "ECE");
                startActivity(i);
            }
        });
        me = findViewById(R.id.me);
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, cs_faculty_list.class);
                i.putExtra("EXTRA_DEPT_NAME", "ME");
                startActivity(i);
            }
        });
        cv = findViewById(R.id.cv);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, cs_faculty_list.class);
                i.putExtra("EXTRA_DEPT_NAME", "CV");
                startActivity(i);
            }
        });
    }
}