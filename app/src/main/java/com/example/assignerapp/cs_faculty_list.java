package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("faculties").whereEqualTo("Department",dept).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc :task.getResult()){
//                        Log.d("Document", doc.getId()+ "=>" +doc.getData());
                        list_data = doc.getString("id") +" "+doc.getString("Name") +" ("+ doc.getString("Department")+")     "+ doc.getString("Email");
                        list.add(list_data);
                        fileRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {

//                                for (StorageReference prefix : listResult.getPrefixes()) {
//                                    // All the prefixes under listRef.
//                                    // You may call listAll() recursively on them.
//                                }

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = "ABC";
                String item = adapterView.getItemAtPosition(i).toString();

                Log.d("Document this", item);
                String clicked_fac_id = item.substring(0,3);
                Log.d("Document this", clicked_fac_id);
                Intent intent = new Intent(cs_faculty_list.this, LoginActivity.class);
//                intent.putExtra("TEXT",text);
                startActivity(intent);
            }
        });


    }
}
