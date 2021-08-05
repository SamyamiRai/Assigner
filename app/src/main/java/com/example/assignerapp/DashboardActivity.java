package com.example.assignerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.assignerapp.DRVinterface.LoadMore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;

    List<DynamicRvModel> items=new ArrayList();
    DynamicRvAdapter dynamicRvAdapter;

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
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));
        items.add(new DynamicRvModel("Lecturer"));

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





    }
}