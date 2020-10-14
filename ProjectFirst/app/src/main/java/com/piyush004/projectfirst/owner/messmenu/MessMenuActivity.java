package com.piyush004.projectfirst.owner.messmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

public class MessMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
    }

    public void onClickBackEvent(View view) {
        Intent intentBack = new Intent(MessMenuActivity.this, OwnerDashboard.class);
        startActivity(intentBack);
    }

    public void onClickAddMenuEvent(View view) {
        Toast.makeText(this, "Click Floating button", Toast.LENGTH_SHORT).show();
    }

}