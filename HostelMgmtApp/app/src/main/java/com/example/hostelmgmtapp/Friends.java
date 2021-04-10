package com.example.hostelmgmtapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {

    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        fstore = FirebaseFirestore.getInstance();
        fstore.collection("usersVerified").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<String> listUID = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                                listUID.add(document.getId());
                        }else{
                            Toast.makeText(Friends.this,"unable to fetch Database",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}