package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class faculty_profile extends AppCompatActivity {

    private TextView tv,id_fac;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_profile);
        String fac_det = getIntent().getStringExtra("FAC_DET");
        String dept = getIntent().getStringExtra("FAC_DEPT");

        tv = findViewById(R.id.name);
        tv.setText(fac_det);
        logout= findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(faculty_profile.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(faculty_profile.this, LoginActivity.class));
            }
        });



        String lastWord = fac_det.substring(fac_det.lastIndexOf(" ")+2);
//        String text = "AA";
//        Log.d("CHECK",lastWord);
//        Log.d("AAA",text);
        FirebaseFirestore.getInstance().collection("faculties").whereEqualTo("Email",lastWord).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc : task.getResult()){
                        Log.d("New",doc.getId());
                        id_fac = findViewById(R.id.id);
                        id_fac.setText(doc.getId().toString());

                    }
                }
            }
        });


    }
}