package com.piyush004.projectfirst.customer.all_mess;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.projectfirst.Dashboard.CustomerDashboard;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

public class CustomerAllMessActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private String login_name;

    private FirebaseRecyclerOptions<AllMessModel> options;
    private FirebaseRecyclerAdapter<AllMessModel, MyHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_mess);

        login_name = LoginKey.loginKey;

        toolbar = (Toolbar) findViewById(R.id.toolbar2_c);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewAllMess);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name);

        options = new FirebaseRecyclerOptions.Builder<AllMessModel>().setQuery(nm, new SnapshotParser<AllMessModel>() {
            @NonNull
            @Override
            public AllMessModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new AllMessModel(
                        snapshot.child("MessDetails").child("MessName").getValue().toString(),
                        snapshot.child("MessDetails").child("MessAddress").getValue().toString(),
                        snapshot.child("MessDetails").child("MessMobile").getValue().toString(),
                        snapshot.child("MessDetails").child("MessCity").getValue().toString()
                );
            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<AllMessModel, MyHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull AllMessModel model) {

                holder.setTxtTitle(model.getTitle());
                holder.setTxtAddress(model.getAddress());
                holder.setTxtMobile(model.getModile());
                holder.setTxtCity(model.getCity());
            }

            @NonNull
            @Override
            public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                return new MyHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    public void onClickBackAllMess(View view) {
        Intent intentProfile = new Intent(CustomerAllMessActivity.this, CustomerDashboard.class);
        startActivity(intentProfile);
    }

}