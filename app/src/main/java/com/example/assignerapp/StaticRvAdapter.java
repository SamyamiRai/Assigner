package com.example.assignerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRvViewHolder> {


    private ArrayList<StaticRvModel> items;
    int row_index=-1;

    public StaticRvAdapter(ArrayList<StaticRvModel> items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public StaticRvViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.static_rv_item,parent,false);
        StaticRvViewHolder staticRvViewHolder =new StaticRvViewHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StaticRvViewHolder holder, int position) {
        StaticRvModel currentItem= items.get(position);
        holder.image.setImageResource(currentItem.getImage());
        holder.text.setText(currentItem.getText());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                notifyDataSetChanged();
            }
        });

        if(row_index==position){
            holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
        }
        else{
            holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticRvViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView image;
        LinearLayout linearLayout;

        public StaticRvViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            text=itemView.findViewById(R.id.text);
            linearLayout=itemView.findViewById(R.id.linearLayout);
        }
    }
}
