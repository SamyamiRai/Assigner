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

public class connect_dept_faculties extends AppCompatActivity {

    private Button logout,more;
    private ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_dept_faculties);


        String dept = getIntent().getStringExtra("EXTRA_DEPT_NAME");


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(connect_dept_faculties.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(connect_dept_faculties.this, LoginActivity.class));
            }
        });
        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(connect_dept_faculties.this, cs_faculty_list.class);
                i.putExtra("EXTRA_DEPT_NAME", dept);
                startActivity(i);


            }
        });

        listView = findViewById(R.id.listView1);

        final ArrayList<String> list = new ArrayList<>();
        final CustomAdapterAssigned adapter = new CustomAdapterAssigned(list, listView.getContext(),dept);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("Assigned").whereEqualTo("Dept",dept).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        Log.d("Document", doc.getId()+ "=>" +doc.getData());
//                        list_data = doc.getString("id") +" "+doc.getString("Name") +" ("+ doc.getString("Department")+")     "+ doc.getString("Email");
                        list_data = doc.getId() ;

                        list.add(list_data);


                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });


//        final ArrayList<String> list = new ArrayList<String>();
//        final ArrayAdapter adapter = new ArrayAdapter(this,R.layout.list_img_item,list);
//        listView.setAdapter(adapter);
//        FirebaseFirestore.getInstance().collection("Assigned").whereEqualTo("Dept", dept).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
////                        Log.d("Document", doc.getId()+ "=>" +doc.getData());
////                        list_data = doc.getString("id") +" "+doc.getString("Name") +" ("+ doc.getString("Department")+")     "+ doc.getString("Email");
//                        list_data = doc.getString("Name") ;
//
//                        Log.d("Done",list_data);
//                        list.add(list_data);
//
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

    }
}
