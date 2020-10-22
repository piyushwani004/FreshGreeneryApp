package com.piyush004.projectfirst.owner.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

public class HomeOwnerActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);

        final String login_name = LoginKey.loginKey;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                latitude = snapshot.child(login_name).child("MessLocation").child("latitude").getValue(Double.class);
                longitude = snapshot.child(login_name).child("MessLocation").child("longitude").getValue(Double.class);
                String messName = snapshot.child(login_name).child("MessLocation").child("MessName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}