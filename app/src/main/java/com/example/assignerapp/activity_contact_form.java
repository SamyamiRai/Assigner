package com.example.assignerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class activity_contact_form extends AppCompatActivity {
    private EditText id, name, email,dept;
    String id_facuty;
    private Uri imageUri;
    private static final int IMAGE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        id = findViewById(R.id.faculty_id);
        name = findViewById(R.id.faculty_name);
        email = findViewById(R.id.faculty_email);
        dept = findViewById(R.id.faculty_dept);
        Button submit = findViewById(R.id.submit);
        Button add_image = findViewById(R.id.add_image);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> faculty = new HashMap<>();
                faculty.put("id",id.getText().toString());
                faculty.put("Name",name.getText().toString());
                faculty.put("Department",dept.getText().toString());
                faculty.put("Email",email.getText().toString());
                id_facuty = id.getText().toString();

                db.collection("faculties").document(id_facuty).set(faculty).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity_contact_form.this, "Faculty Details Added", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(activity_contact_form.this,activity_faculty_list.class));
                        }
                    }
                });
            }
        });
//        Button logout = findViewById(R.id.logout);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Toast.makeText(activity_contact_form.this, "Logged Out!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(activity_contact_form.this, LoginActivity.class));
//            }
//        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();

            uploadImage();
        }
    }

    private  String getFileExtension (Uri uri) {
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {

        if(imageUri != null) {
            if (id.getText().toString().equals("")) {
                Toast.makeText(this, "Enter Faculty ID ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_contact_form.this, activity_contact_form.class));
            } else {
                ProgressDialog pd = new ProgressDialog(this);
                pd.setMessage("Uploading");
                pd.show();
                final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(id.getText().toString() + "." + getFileExtension(imageUri));

                fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    LinearLayout l1 =new LinearLayout(activity_contact_form.this);
                    @Override
                    public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();

                                Log.d("DownloadUrl", url);
                                pd.dismiss();
                                Toast.makeText(activity_contact_form.this, "Image Upload Successful", Toast.LENGTH_SHORT).show();
//                                TextView dynamic_url_text = new TextView(activity_contact_form.this);
//                                dynamic_url_text.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
//                                dynamic_url_text.setText("URL");
//                                setContentView(l1);

                            }
                        });
                    }
                });
            }

        }
    }
}