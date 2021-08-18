package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class cs_faculty_list extends AppCompatActivity {

    private Button logout;
    private ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads");
    private String list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_faculty_list);
        String dept = getIntent().getStringExtra("EXTRA_DEPT_NAME");
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(cs_faculty_list.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(cs_faculty_list.this, LoginActivity.class));
            }
        });
        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Hello",view.toString());
                Log.d("Hello1",listView.getItemAtPosition(i).toString());
                Intent intent = new Intent(cs_faculty_list.this,faculty_profile.class);
                intent.putExtra("FAC_DET",listView.getItemAtPosition(i).toString());
                intent.putExtra("FAC_DEPT",dept);
                startActivity(intent);
            }
        });
        final ArrayList<String> list = new ArrayList<>();
        final MyCustomAdpter adapter = new MyCustomAdpter(list, listView.getContext(),dept);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("faculties").whereEqualTo("Department",dept).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc :task.getResult()){
                        list_data = doc.getString("Name") +" \n"+doc.getString("Email");

                        list.add(list_data);

                        fileRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                for (StorageReference item : listResult.getItems()) {
                                    // All the items under listRef.
                                    if (item.getName() == doc.getId().toString()){
//                                        image reference in item
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                            }
                        });
                    }
                    Log.d("Document", fileRef.listAll().toString());
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }
}
