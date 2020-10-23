package com.piyush004.projectfirst.owner.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.piyush004.projectfirst.R;

public class HomeOwnerActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner);

    }

}