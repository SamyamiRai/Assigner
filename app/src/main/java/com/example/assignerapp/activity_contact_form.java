package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class activity_contact_form extends AppCompatActivity {
    private EditText id, name, email,dept;
    private Button submit;
    String id_facuty;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        id = findViewById(R.id.faculty_id);
        name = findViewById(R.id.faculty_name);
        email = findViewById(R.id.faculty_email);
        dept = findViewById(R.id.faculty_dept);
        submit = findViewById(R.id.submit);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> faculty = new HashMap<>();
                faculty.put("id",id.getText().toString());
                faculty.put("Name",name.getText().toString());
                faculty.put("Department",dept.getText().toString());
                faculty.put("Email",email.getText().toString());
                id_facuty = id.getText().toString();

                db.collection("faculties").document(id_facuty).set(faculty).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_contact_form.this, "Faculty Details Added", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(activity_contact_form.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_contact_form.this, LoginActivity.class));
            }
        });
    }
}