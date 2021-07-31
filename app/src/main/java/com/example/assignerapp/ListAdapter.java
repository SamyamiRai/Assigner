//package com.example.assignerapp;
//
//import android.telecom.TelecomManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.jetbrains.annotations.NotNull;
//import org.w3c.dom.Text;
//
//import java.util.List;
//
//public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
//    public List<Faculties> Faculty_list;
//
//
//    public ListAdapter(List<Faculties> Faculty_list){
//        this.Faculty_list = Faculty_list;
//    }
//    @NonNull
//    @NotNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
//        holder.name_text.setText(Faculty_list.get(position).getName());
//        holder.dept_text.setText(Faculty_list.get(position).getDept());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return Faculty_list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        View mview;
//
//        public TextView name_text;
//        public TextView dept_text;
//
//        public ViewHolder(@NonNull @NotNull View itemView) {
//            super(itemView);
//            mview = itemView;
//            name_text = (TextView) mview.findViewById(R.id.name_text);
//
//            dept_text = (TextView) mview.findViewById(R.id.dept_text);
//
//        }
//    }
//}
