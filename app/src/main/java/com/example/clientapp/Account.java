package com.example.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    private TextView emaild, name,AddT,PhoneT;

    private Button logout;
    private ImageView imageView;
    private FirebaseDatabase db;
    DatabaseReference userRef;
    private DatabaseReference rootRef;
    private TextView verify;
    private Button verifyemail;
    private FirebaseAuth auth;
    private String tmp;
    private final String TAG = this.getClass().getName().toUpperCase();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomBar);
        bottomNavigationView.setSelectedItemId(R.id.account);

        imageView=findViewById(R.id.imageView);

        logout = findViewById(R.id.logout);
        emaild= findViewById(R.id.emaild);

        name= findViewById(R.id.name);

        PhoneT= findViewById(R.id.PhoneT);

        AddT=findViewById(R.id.AddT);

        auth = FirebaseAuth.getInstance();


        verifyemail = findViewById(R.id.verifyemail);
        verify = findViewById(R.id.verify);

//
//        if (!auth.getCurrentUser().isEmailVerified()) {
//            verify.setVisibility(View.VISIBLE);
//            verifyemail.setVisibility(View.VISIBLE);
//        }
//
//


        Intent intent = getIntent();
        tmp = intent.getStringExtra("email");

     rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("customers");
        Log.v("cin", userRef.getKey());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                finish();
            }
        });


        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;

                    case R.id.account:
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });


        // email verification


        auth = FirebaseAuth.getInstance();


        verifyemail = findViewById(R.id.verifyemail);
        verify = findViewById(R.id.verify);

//
//        if (!auth.getCurrentUser().isEmailVerified()) {
//            verify.setVisibility(View.VISIBLE);
//            verifyemail.setVisibility(View.VISIBLE);
//        }


        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send the email

                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Account.this, "verification Email Sent !", Toast.LENGTH_SHORT).show();
                        verify.setVisibility(View.GONE);
                        verifyemail.setVisibility(View.GONE);
                    }
                });
            }
        });







    userRef.addValueEventListener(new ValueEventListener() {
        String add,email,nameT,phone;
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot keyId: dataSnapshot.getChildren()) {
                if (keyId.child("email").getValue().equals(tmp)) {
                    nameT = keyId.child("name").getValue(String.class);
                    email = keyId.child("email").getValue(String.class);
                    add = keyId.child("addresse").getValue(String.class);

                    phone = keyId.child("telephone").getValue(String.class);
                    break;
                }
            }
            name.setText(nameT);
            emaild.setText(email);
            AddT.setText(add);
//            workTxtView.setText(workplace);
            PhoneT.setText(phone);
//            videoTxtView.setText(phone);
        }

        @Override

        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                finish();
            }
        });
    }
}
