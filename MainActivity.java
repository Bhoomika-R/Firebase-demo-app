package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.CallCredentials;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private EditText edit;
    private Button add;
    private ListView listView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });
//for a few number of json resonses.
        //``````FirebaseDatabase.getInstance().getReference().child("Programming knowledge").child("Android").setValue("abcd");
        //for more number of json and its child we need to create a HASHMAP
//        HashMap<String , Object> map = new HashMap<>();
//        map.put("Name", "Bhoomika");
//        map.put("Email", "iambhumi5@gmail.com");
//        FirebaseDatabase.getInstance().getReference().child("programming knowledge").child("Multiple values").updateChildren(map);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String txt_name = edit.getText().toString();
//                if(txt_name.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "No name entered", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    //FirebaseDatabase.getInstance().getReference().child("programming knowledge").push().child("name").setValue(txt_name);
//                    //Retrieve single information:
//                    FirebaseDatabase.getInstance().getReference().child("languages").child("name").setValue(txt_name);
//                }

                uploadImage();
            }
        });

        //Retrieve value
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list);
        listView.setAdapter(adapter);
        // to access a database we need a reference to the database, we can do it with database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Information"); //reference of root branch
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                // to iterate through each branch of key-value fair we can use for
                list.clear();
                //to retrieve a group of information
              for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                  Information info = snapshot.getValue(Information.class);
                  String txt = info.getName() + " : " + info.getEmail();
                  list.add(txt);  //get value of children
              }
              adapter.notifyDataSetChanged();//notify the adapter about the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //COULD FIREBASE
        //to get instance to cloud firebase
  //     FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String , Object> city = new HashMap<>();
//        city.put("Name","Bangalore");
//        city.put("State","Karnataka");
//        city.put("Country","India");
//
//        db.collection("cities").document("BLR").set(city).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull  Task<Void> task) {
//                if(task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Values Added", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        //to merge values to database
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("capital", false);
//        db.collection("cities").document("BLR").set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "Merge successful", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //to add a set of values to db with unique id we use .add method
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> data = new HashMap<>();
//        data.put("Name", "Tokyo");
//        data.put("Capital", "Japan");
//        db.collection("cities").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if(task.isSuccessful()) {
//                    Toast.makeText(MainActivity.this, "values added successfully!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //to update existing data
//        DocumentReference ref = FirebaseFirestore.getInstance().collection("cities").document("BLR");
//        ref.update("capital", true);


        //to retrieve data from database

//        DocumentReference docref = FirebaseFirestore.getInstance().collection("cities").document("SF");
//        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()) {
//                    DocumentSnapshot doc = task.getResult();
//                    if(doc.exists()) {
//                        Log.d("Document", doc.getData().toString()); //returns data inside document snapshot
//                    }
//                    else {
//                        Log.d("Document","No data");
//                    }
//                }
//            }
//        });

        //document with conditions ex: download cities with their respective capital
//        FirebaseFirestore.getInstance().collection("cities").whereEqualTo("capital", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        Log.d("Document", doc.getId() + "=> "+ doc.getData().toString());
//                    }
//                }
//            }
//        });

        //add realtime listener to any cities
//        FirebaseFirestore.getInstance().collection("cities").document("TN").addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//            }
//        });
    }

    private void uploadImage() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("uploading");
        pd.show();
    }
}