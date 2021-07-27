import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignerapp.Person;
import com.example.assignerapp.R;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter {
    private Context mContext;
    private int mResource;

    public PersonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects) {
        super(context, resource, objects);
        this.mContext=context;
        this.mResource=resource;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);
        ImageView imageView=convertView.findViewById(R.id.image);
        TextView textView=convertView.findViewById(R.id.textName);
        TextView textDept=convertView.findViewById(R.id.textDept);

        imageView.setImageResource(getItem(position).getImage());
        textView.setText(getItem(position).getName());
        textDept.setText(getItem(position).getDepartment());

        return convertView;
    }
}
