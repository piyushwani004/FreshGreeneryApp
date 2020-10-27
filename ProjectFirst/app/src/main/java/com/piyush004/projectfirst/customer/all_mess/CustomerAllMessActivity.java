package com.piyush004.projectfirst.customer.all_mess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.Dashboard.CustomerDashboard;
import com.piyush004.projectfirst.R;

public class CustomerAllMessActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_mess);

        toolbar = (Toolbar) findViewById(R.id.toolbar2_c);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewAllMess);
    }

    public void onClickBackAllMess(View view) {
        Intent intentProfile = new Intent(CustomerAllMessActivity.this, CustomerDashboard.class);
        startActivity(intentProfile);
    }

}