package com.example.hostelmgmtapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Button btnlogout,btnFriends,btnMarket;
    private TextView dashboardName, dashboardRoomno;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlogout = findViewById(R.id.btnLogout);
        btnFriends = findViewById(R.id.btnfindMates);
        btnMarket = findViewById(R.id.btnMarket);
        dashboardName = findViewById(R.id.nameDashboard);
        dashboardRoomno = findViewById(R.id.dashboardroomno);
        fStore = FirebaseFirestore.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this, Registration.class);
            startActivity(intent);
            return;
        }
    }
}