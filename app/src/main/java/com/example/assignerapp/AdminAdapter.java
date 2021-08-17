package com.example.assignerapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private ListView ls;

    public AdminAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        return pos;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_admin_fac_list, null);
        }

        //Handle TextView and display string from your list
        TextView name= (TextView)view.findViewById(R.id.label);
        name.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button del= (Button)view.findViewById(R.id.del);
//
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                RelativeLayout rl = (RelativeLayout)(v.getParent());
                TextView child = (TextView)rl.getChildAt(0);
                String item = child.getText().toString();

                String firstThreeChars = item.substring(0, 3);
                Log.d("CCC",firstThreeChars);
                FirebaseFirestore.getInstance().collection("faculties").document(firstThreeChars)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("CC", "DocumentSnapshot successfully deleted!");
                                Toast.makeText(v.getContext(),  "Deleted", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("C", "Error deleting document", e);
                            }
                        });
                notifyDataSetChanged();

//                context.startActivity();

//                Log.d("Document this", String.valueOf(i));

            }
        });
//        Button set_class= (Button)view.findViewById(R.id.set_for_class);
//
//        set_class.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//                RelativeLayout rl = (RelativeLayout)(v.getParent());
//                TextView child = (TextView)rl.getChildAt(0);
//                String item = child.getText().toString();
//
//                Log.d("Name of fac", dept.toString());
//                String name = item.substring(0, item.lastIndexOf(" "));
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                Map<String, Object> faculty = new HashMap<>();
//                faculty.put("Name",name);
//                faculty.put("Dept",dept.toString());
//
//                db.collection("Assigned").document(item).set(faculty).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(v.getContext(), "Assigned", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//            }
//        });
//        ls = (ListView) view.findViewById(R.id.listView);
//        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("See2",view.toString());
//            }
//        });
//        ls.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //do something
//                notifyDataSetChanged();
//            }
//        });

        return view;
    }
}