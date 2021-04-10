package com.example.hostelmgmtapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText etname,etrollno,etemail,etpassword,etphone;
    private Button btnRegister,loginlink;
    private FirebaseAuth mFbAuth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etname = findViewById(R.id.etName);
        etemail = findViewById(R.id.etEmail);
        etpassword = findViewById(R.id.etPassword);
        etrollno = findViewById(R.id.etRoll);
        btnRegister = findViewById(R.id.btnRegister);
        loginlink = findViewById(R.id.btnLoginlink);
        etphone = findViewById(R.id.etphone);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = etname.getText().toString();
                String sroll = etrollno.getText().toString();
                String semail = etemail.getText().toString();
                String spassword = etpassword.getText().toString();
                String sphone = etphone.getText().toString();
                if(semail.isEmpty()){
                    etemail.setError("Enter Email ID");
                    etemail.requestFocus();
                }
                else if(spassword.isEmpty()){
                    etpassword.setError("Enter Password");
                    etpassword.requestFocus();
                }
                else if(sroll.isEmpty()){
                    etrollno.setError("Enter Password");
                    etrollno.requestFocus();
                }
                else if(sname.isEmpty()){
                    etname.setError("Enter Password");
                    etname.requestFocus();
                }
                else if(sphone.isEmpty()){
                    etphone.setError("Enter phone no");
                    etphone.requestFocus();
                }
                else if(spassword.length() < 6){
                    etpassword.setError("Password length should be greater than 6");
                    etpassword.requestFocus();
                }
                else if(sphone.length()!=10){
                    etphone.setError("Enter valid phone no");
                    etphone.requestFocus();
                }
                else if(!(semail.isEmpty() && spassword.isEmpty())){
                    mFbAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Registration.this,"Register failed. Try Again!",Toast.LENGTH_SHORT).show();
                            }else{
                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                fstore = FirebaseFirestore.getInstance();
                                DocumentReference documentReference = fstore.collection("usersAll")
                                        .document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("FullName", sname);
                                user.put("RollNo", sroll);
                                user.put("Type","Student");
                                user.put("Email", semail);
                                user.put("Phone",sphone);
                                documentReference.set(user);

                                Intent i = new Intent(Registration.this,Login.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Registration.this,"Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registration.this,Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}