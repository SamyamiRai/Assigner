package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignerapp.DRVinterface.LoadMore;
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
import java.util.List;
import java.util.UUID;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;
    private Button logout;

    private String list_data;
    List<DynamicRvModel> items=new ArrayList();
    List<DynamicRvAdapter> item_new=new ArrayList();
    DynamicRvAdapter dynamicRvAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        ArrayList<StaticRvModel> item=new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.analysis,"CSE"));
        item.add(new StaticRvModel(R.drawable.computer,"ISE"));
        item.add(new StaticRvModel(R.drawable.electronicsignature,"ECE"));
        item.add(new StaticRvModel(R.drawable.cogwheels,"ME"));
        item.add(new StaticRvModel(R.drawable.construction,"CE"));
        item.add(new StaticRvModel(R.drawable.humanresources,"Non Teaching"));

        recyclerView=findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(staticRvAdapter);

        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));
//        items.add(new DynamicRvModel("Lecturer"));

//        RecyclerView drv=findViewById(R.id.rv_2);
//        drv.setLayoutManager(new LinearLayoutManager(this));
//        dynamicRvAdapter=new DynamicRvAdapter(drv,this,items);
//        drv.setAdapter(dynamicRvAdapter);

        FirebaseFirestore.getInstance().collection("faculties").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot doc :task.getResult()){
//                        Log.d("Document", doc.getId()+ "=>" +doc.getData());
                        list_data = doc.getString("id") +" "+doc.getString("Name") +" ("+ doc.getString("Department")+")     "+ doc.getString("Email");

                        String data = doc.getId();
                        items.add(new DynamicRvModel(data));
//                        fileRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                            @Override
//                            public void onSuccess(ListResult listResult) {
//
////                                for (StorageReference prefix : listResult.getPrefixes()) {
////                                    // All the prefixes under listRef.
////                                    // You may call listAll() recursively on them.
////                                }
//
//                                for (StorageReference item : listResult.getItems()) {
//                                    // All the items under listRef.
//                                    if (item.getName() == doc.getId().toString()){
////                                        image reference in item
//                                    }
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull @NotNull Exception e) {
//
//                            }
//                        });
                    }
//                    Log.d("Document", fileRef.listAll().toString());



                }
            }
        });
        RecyclerView drv=findViewById(R.id.rv_2);
        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicRvAdapter=new DynamicRvAdapter(drv,this,items);
        drv.setAdapter(dynamicRvAdapter);


        dynamicRvAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {
                if(items.size()<=10){
                    item.add(null);
                    dynamicRvAdapter.notifyItemInserted(items.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size()-1);
                                dynamicRvAdapter.notifyItemRemoved(items.size());


                                int index=items.size();
                                int end=index+10;
                                for(int i=index;i<end;i++){
                                    String name= UUID.randomUUID().toString();
                                    DynamicRvModel item=new DynamicRvModel(name);
                                    items.add(item);
                                }
                                dynamicRvAdapter.notifyDataSetChanged();
                                dynamicRvAdapter.setLoaded();
                            }
                        }, 4000);

                    }
                else
                    Toast.makeText(DashboardActivity.this,"Data Completed",Toast.LENGTH_SHORT).show();
                }
            });


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(DashboardActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            }
        });



    }
}