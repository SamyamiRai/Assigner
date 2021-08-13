package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class activity_faculty_list extends AppCompatActivity {
    private Button logout, add_new_faculty;
    private ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads");


    private String list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(activity_faculty_list.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_faculty_list.this, LoginActivity.class));
            }
        });
        listView = findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance().collection("faculties").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc :task.getResult()){
//                        Log.d("Document", doc.getId()+ "=>" +doc.getData());

//                        6 char id, 13 char name, 3 char dept, 13 char email
//                        StringBuilder spaces_id = new StringBuilder();
//                        int count = 0;
//                        if (doc.getString("id").length() > 0 )
//                            count = 6 - doc.getString("id").length();
//                        for(int i =0; i<count;i++ )
//                            spaces_id.append(" ");

//                        Log.d("Document","A"+doc.getString("id").length()+"b");

//                        StringBuilder spaces_name = new StringBuilder();
//                        if (doc.getString("Name").length() > 0 )
//                            count = 13 - doc.getString("Name").length();
//                        else
//                            count = 0;
//                        for(int i =0; i<count;i++ )
//                            spaces_name.append(" ");
//
//                        StringBuilder spaces_dept = new StringBuilder();
//                        if (doc.getString("Department").length() > 0 )
//                            count = 3 - doc.getString("Department").length();
//                        else
//                            count = 0;
//                        for(int i =0; i<count;i++ )
//                            spaces_dept.append(" ");


//                        String spaces_email = "";
//                        for(int i =0; i<3 - doc.getString("id").length();i++ )
//                            spaces_email = spaces_email + " ";

//                        String name = doc.getString("Name");
//                        int count = (15-doc.getString("Name").length());
//                        for (int i = 0; i<count;i++)
//                            name = name + " ";
//                        Log.d("Document",name+"a");
                        list_data = doc.getString("id") + "  "+ doc.getString("Department") +"  " + doc.getString("Name") +"  " + doc.getString("Email") ;


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
                Intent intent = new Intent(activity_faculty_list.this, LoginActivity.class);
//                intent.putExtra("TEXT",text);
                startActivity(intent);
            }
        });

//        To add new factuly:
        add_new_faculty = findViewById(R.id.add);
        add_new_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_faculty_list.this, activity_contact_form.class);
                startActivity(intent);

            }
        });


    }


}
//.whereEqualTo("Department","ISE")