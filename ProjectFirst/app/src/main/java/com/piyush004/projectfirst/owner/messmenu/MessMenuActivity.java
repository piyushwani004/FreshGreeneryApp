package com.piyush004.projectfirst.owner.messmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

public class MessMenuActivity extends AppCompatActivity {

    private String menuName, menuQuantity, menuPrice;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }

    public void onClickBackEvent(View view) {
        Intent intentBack = new Intent(MessMenuActivity.this, OwnerDashboard.class);
        startActivity(intentBack);
    }

    public void onClickAddMenuEvent(View view) {
        Toast.makeText(this, "Click Floating button", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Name");
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

        MessMenuModel[] myListData = new MessMenuModel[]{

                new MessMenuModel(name, quan, price)

        };

        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Toast.makeText(this, "Message is : " + name + quan + price, Toast.LENGTH_SHORT).show();
    }

}