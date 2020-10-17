package com.piyush004.projectfirst.owner.messmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

public class MessMenuActivity extends AppCompatActivity {

    private String menuName, menuQuantity, menuPrice;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String login_name;
    private FirebaseRecyclerOptions<MessMenuModel> options;
    private FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter> adapter;
    private EditText editTextName, editTextQuantity, editTextPrice;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            login_name = bundle.getString("LoginNameMessDetails");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextPrice = findViewById(R.id.editTextPrice);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessMenu");

        options = new FirebaseRecyclerOptions.Builder<MessMenuModel>().setQuery(nm, new SnapshotParser<MessMenuModel>() {
            @NonNull
            @Override
            public MessMenuModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new MessMenuModel(
                        snapshot.child("ItemName").getValue().toString(),
                        snapshot.child("ItemPrice").getValue().toString(),
                        snapshot.child("ItemQuantity").getValue().toString()

                );
            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter>(options) {

            @Override
            protected void onBindViewHolder(@NonNull MyListAdapter holder, int position, @NonNull MessMenuModel model) {

                holder.setTxtName(model.getMessMenuName());
                holder.setTxtQuant(model.getMessMenuQuantity());
                holder.setTxtPrice(model.getMessMenuPrice());
            }

            @NonNull
            @Override
            public MyListAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                return new MyListAdapter(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        }).attachToRecyclerView(recyclerView);

    }

    public void onClickBackEvent(View view) {
        Intent intentBack = new Intent(MessMenuActivity.this, OwnerDashboard.class);
        startActivity(intentBack);
    }

    public void onClickAddMenu(View view) {

        menuName = editTextName.getText().toString();
        menuQuantity = editTextQuantity.getText().toString();
        menuPrice = editTextPrice.getText().toString();

        if (menuName.isEmpty()) {
            editTextName.setError("Enter Menu Name...");
            editTextName.requestFocus();
        } else if (menuQuantity.isEmpty()) {
            editTextQuantity.setError("Enter Menu Quantity...");
            editTextQuantity.requestFocus();
        } else if (menuPrice.isEmpty()) {
            editTextPrice.setError("Enter Menu Price...");
            editTextPrice.requestFocus();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessMenu");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).child("ItemName").setValue(menuName);
        databaseReference.child(key).child("ItemPrice").setValue(menuPrice);
        databaseReference.child(key).child("ItemQuantity").setValue(menuQuantity);

        Toast.makeText(MessMenuActivity.this, "Data Added To Database", Toast.LENGTH_SHORT).show();
        editTextName.setText("");
        editTextQuantity.setText("");
        editTextPrice.setText("");

    }
}