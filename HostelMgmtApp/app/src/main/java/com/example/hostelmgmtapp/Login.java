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
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText etlemail,etlpassword;
    private Button btnLogin,Registerlink;
    private FirebaseAuth mFbAuth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etlemail = findViewById(R.id.etLoginEmail);
        etlpassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        Registerlink = findViewById(R.id.btnRegisterlink);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = etlemail.getText().toString();
                String spassword = etlpassword.getText().toString();
                if(semail.isEmpty()){
                    etlemail.setError("Enter Email ID");
                    etlemail.requestFocus();
                }
                else if(spassword.isEmpty()){
                    etlpassword.setError("Enter Password");
                    etlpassword.requestFocus();
                }
                else if(!(semail.isEmpty() && spassword.isEmpty())){
                    mFbAuth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                Toast.makeText(Login.this,"Error, try again!",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this,"Error Occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Registration.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}