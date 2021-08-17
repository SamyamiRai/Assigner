package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.net.Uri.fromFile;

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
        listView = findViewById(R.id.listView_new);
//        listView_img = findViewById(R.id.listView_img);
//        ImageView image = (ImageView)findViewById(R.id.label);
        final ArrayList<String> list = new ArrayList<>();
        final AdminAdapter adapter = new AdminAdapter(list, listView.getContext());
//        final ArrayList<FirebaseStorage> itemsimg = new ArrayList<FirebaseStorage>();
//        final ArrayAdapter adapter_img = new ArrayAdapter<FirebaseStorage>(this,R.layout.list_img_item,itemsimg);
        listView.setAdapter(adapter);
//        listView_img.setAdapter(adapter_img);

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
                        list_data = doc.getString("id") + "  "+ doc.getString("Department") +"  " + doc.getString("Name")  ;


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
//                                    itemsimg.add(item.getStorage());
                                    File file = new File(item.getPath());
                                    Uri imageURI = fromFile(file);
                                    Log.d("content1",item.getName());

                                    TextView txtview ;
                                    txtview = findViewById(R.id.label);

                                    Glide.with(getApplicationContext())
                                            .load(imageURI)
                                            .apply(RequestOptions.placeholderOf(R.drawable.background_design))
                                            .into(new SimpleTarget<Drawable>() {
                                                @Override
                                                public void onResourceReady(@NonNull Drawable resource,
                                                                            @Nullable Transition<? super Drawable> transition) {

                                                    txtview.setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);

                                                }
                                            });

//                                    Glide.with(getApplicationContext())
//                                            .load(item.getDownloadUrl())
//                                            .into(image)

                                    if (item.getName().equals(doc.getId().toString())){
//                                        image reference in item
//                                        list.add(item.getDownloadUrl());
//                                        Log.d("content",item.getName());
//                                        itemsimg.add(item);
//                                        listView_img.setAdapter(adapter_img);
//                                        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                            @Override
//                                            public void onSuccess(Uri uri) {
//                                                list.add(uri.toString());
//                                            }
//                                        });
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                                Log.d("Failed","abc");
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
        add_new_faculty = findViewById(R.id.add_new);
        add_new_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity_faculty_list.this, activity_contact_form.class);
                startActivity(i);

            }
        });



    }


}
//.whereEqualTo("Department","ISE")