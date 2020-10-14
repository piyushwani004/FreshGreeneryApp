package com.piyush004.projectfirst.owner.messmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

import java.util.List;

public class MessMenuActivity extends AppCompatActivity {

    private String menuName, menuQuantity, menuPrice;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private String login_name;
    private List<MessMenuModel> listData;
    private FirebaseRecyclerOptions<MessMenuModel> options;
    private FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            login_name = bundle.getString("LoginNameMessDetails");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessMenu");

        options = new FirebaseRecyclerOptions.Builder<MessMenuModel>().setQuery(nm, MessMenuModel.class).build();
        adapter = new FirebaseRecyclerAdapter<MessMenuModel, MyListAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyListAdapter holder, int position, @NonNull MessMenuModel model) {

                holder.textViewName.setText(" Hi"+model.getMessMenuName());
                holder.textViewQuant.setText("hello "+model.getMessMenuQuantity());
                holder.textViewPrice.setText("bye"+model.getMessMenuPrice());

                System.out.println(model.getMessMenuName());
                System.out.println(model.getMessMenuQuantity());
                System.out.println(model.getMessMenuPrice());
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

    }

    public void onClickBackEvent(View view) {
        Intent intentBack = new Intent(MessMenuActivity.this, OwnerDashboard.class);
        startActivity(intentBack);
    }

    public void onClickAddMenuEvent(View view) {
        //Toast.makeText(this, "Click Floating button", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Menu");
        final View customLayout = getLayoutInflater().inflate(R.layout.mess_menu_form_dialog, null);
        builder.setView(customLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editTextName = customLayout.findViewById(R.id.editTextMenuName);
                EditText editTextQuantity = customLayout.findViewById(R.id.editTextMenuUnit);
                EditText editTextPrice = customLayout.findViewById(R.id.editTextMenuPrice);

                menuName = editTextName.getText().toString().trim();
                menuQuantity = editTextQuantity.getText().toString().trim();
                menuPrice = editTextPrice.getText().toString().trim();

                sendDialogDataToActivity(menuName, menuQuantity, menuPrice);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void sendDialogDataToActivity(String name, String quan, String price) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessMenu");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).child("ItemName").setValue(name);
        databaseReference.child(key).child("ItemQuantity").setValue(quan);
        databaseReference.child(key).child("ItemPrice").setValue(price);
        //Toast.makeText(this, "Message is : " + name + quan + price, Toast.LENGTH_SHORT).show();
    }

}