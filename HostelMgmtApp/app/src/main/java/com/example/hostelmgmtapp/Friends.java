package com.example.hostelmgmtapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {

    private FirebaseFirestore fstore;
    private TableLayout tblefrnd;
    private List<String> listUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        fstore = FirebaseFirestore.getInstance();
        tblefrnd = findViewById(R.id.friendTable);
        listUID = new ArrayList<>();

        TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView headertv = new TextView(this);
        headertv.setId(20);
        headertv.setText("OTHER STUDENTS");
        headertv.setTextSize(24);
        headertv.setTextColor(Color.WHITE);          // part2
        headertv.setPadding(5, 5, 5, 5);
        tr_head.addView(headertv);// add the column to the table row here

        tblefrnd.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        fstore.collection("usersVerified").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult())
                                listUID.add(document.getId());
                        }else{
                            Toast.makeText(Friends.this,"unable to fetch Database",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        TextView[] textArray = new TextView[listUID.size()];
        TableRow[] tblerow = new TableRow[listUID.size()];

        for(int i=0; i<listUID.size();i++){

            tblerow[i] = new TableRow(this);
            tblerow[i].setId(i+1);
            tblerow[i].setBackgroundColor(Color.GRAY);
            tblerow[i].setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            final String[] str = new String[3];
            fstore.collection("usersVerified").document(listUID.get(i)).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()){
                                str[0] = documentSnapshot.getString("FullName");
                                str[1] = documentSnapshot.getString("RoomNo");
                                str[2] = documentSnapshot.getString("Phone");
                            }else{
                                Toast.makeText(Friends.this,"Error Occured!",Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Friends.this,"Error Occured!",Toast.LENGTH_LONG).show();
                        }
                    });

            textArray[i] = new TextView(this);
            textArray[i].setId(i+111);
            textArray[i].setTextSize(24);
            textArray[i].setText("Room No:" + str[1] + ", " + str[0] + ", Phone:" + str[2]);
            textArray[i].setTextColor(Color.WHITE);
            textArray[i].setPadding(5, 5, 5, 5);
            tblerow[i].addView(textArray[i]);

            tblefrnd.addView(tblerow[i], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }
}