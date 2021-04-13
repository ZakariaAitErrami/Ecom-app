package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText confi;

    Button send;
    TextView gotologin;
    FirebaseAuth fAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.email);
        pass= findViewById(R.id.pass);
        confi = findViewById(R.id.confi);
        send = findViewById(R.id.send);
        gotologin = findViewById(R.id.gotologin);
        fAuth = FirebaseAuth.getInstance();

        gotologin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
                                     });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = email.getText().toString();
                String txtpass= pass.getText().toString();
                String txtconfi= confi.getText().toString();

                // validation des donnees


                if(txtemail.isEmpty()){
                    email.setError("Full name is required!");
                    return;
                }

                if(txtpass.isEmpty()){
                    pass.setError("Full name is required!");
                    return;
                }
                if(txtconfi.isEmpty()){
                    confi.setError("Full name is required!");
                    return;
                }

                if(!txtpass.equals(txtconfi)){
                    pass.setError("Password don't match");
                    return;
                }
                //validation  effectue

                Toast.makeText(RegisterActivity.this, "Data validated" ,Toast.LENGTH_SHORT ).show();


                fAuth.createUserWithEmailAndPassword(txtemail,txtpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });


    }
}