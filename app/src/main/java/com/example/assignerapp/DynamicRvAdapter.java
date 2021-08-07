package com.example.assignerapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignerapp.DRVinterface.LoadMore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;

    public LoadingViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        progressBar=itemView.findViewById(R.id.progress_bar);
    }
}

class  ItemViewHolder extends RecyclerView.ViewHolder{

    public TextView name;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
    }
}
public class DynamicRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING=1;
    LoadMore loadMore;
    boolean isLoading;
    Activity activity;
    List<DynamicRvModel> items;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;
    TextView tv;

    public DynamicRvAdapter(RecyclerView recyclerView, Activity activity, List<DynamicRvModel> items) {
        this.activity = activity;
        this.items = items;

        final LinearLayoutManager linearLayoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount=linearLayoutManager.getItemCount();
                lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount<=(lastVisibleItem+visibleThreshold)){
                    if(loadMore!=null)
                        loadMore.onLoadMore();
                    isLoading=true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position)==null? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore){
        this.loadMore=loadMore;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        List<String> item_new = new ArrayList();
        View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout, parent, false);
        if (viewType == VIEW_TYPE_ITEM) {
            FirebaseFirestore.getInstance().collection("faculties").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout, parent, false);
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            String list_data = doc.getString("id") + " " + doc.getString("Name") + " (" + doc.getString("Department") + ")     " + doc.getString("Email");

//                        item_new.add(list_data);
                            view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout, parent, false);

//                      String data = item_new.get(i);
                            tv = view.findViewById(R.id.name);
                            tv.setText(list_data);
//                        String data = doc.getId();
//                        tv = view.findViewById(R.id.name);
//                        tv.setText(data);

                        }


                    }

                }


            });
//            View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout, parent, false);
//            for(int i=0;i<item_new.size();i++){
//               View view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_item_layout, parent, false);

//                String data = item_new.get(i);
//                tv = view.findViewById(R.id.name);
//                tv.setText(item_new.toString());
//                item_new.remove(i);
//            }
            return new LoadingViewHolder(view);

        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(activity).inflate(R.layout.dynamic_rv_progress_bar, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            DynamicRvModel item=items.get(position);
            ItemViewHolder viewHolder=(ItemViewHolder) holder;
            viewHolder.name.setText(items.get(position).getName());
        }
        else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder=(LoadingViewHolder) holder;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoaded(){
        isLoading=false;
    }
}
