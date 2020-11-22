package com.piyush004.projectfirst.owner.manage_customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.owner.home.HomeOwnerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManageCustomerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView textViewDay, textViewDate, textViewTime;
    private String login_name;
    private String name, address, city, mobile, img, date;
    private int day, month, year;
    private Button buttonSubmit;
    List<String> list = new ArrayList<String>();
    private FirebaseRecyclerOptions<MessCustomerModel> options;
    private FirebaseRecyclerAdapter<MessCustomerModel, MyMessCustHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__customer_activity);

        login_name = LoginKey.loginKey;
        toolbar = (Toolbar) findViewById(R.id.toolbar2_o);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewMessCustomer);
        textViewDay = (TextView) findViewById(R.id.textViewDay);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmitSchedule);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textViewDate.setText(currentDate());
        textViewDay.setText(currentDay());
        textViewTime.setText(getRemainingTime());

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("ManageCustomer").child(login_name);

        options = new FirebaseRecyclerOptions.Builder<MessCustomerModel>().setQuery(nm, new SnapshotParser<MessCustomerModel>() {
            @NonNull
            @Override
            public MessCustomerModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new MessCustomerModel(
                        snapshot.getValue(String.class)
                );

            }
        }).build();

        adapter = new FirebaseRecyclerAdapter<MessCustomerModel, MyMessCustHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final MyMessCustHolder holder, int position, @NonNull final MessCustomerModel model) {
                final String key = model.getKey();

                //System.out.println("key : " + key);

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Customer").child(key);
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        name = snapshot.child("CustName").getValue(String.class);
                        address = snapshot.child("CustAddress").getValue(String.class);
                        mobile = snapshot.child("CustMobile").getValue(String.class);
                        city = snapshot.child("CustCity").getValue(String.class);
                        img = snapshot.child("ImageURl").getValue(String.class);
                        day = snapshot.child("CustJoinDay").getValue(Integer.class);
                        month = snapshot.child("CustJoinMonth").getValue(Integer.class);
                        year = snapshot.child("CustJoinYear").getValue(Integer.class);
                        date = day + "/" + month + "/" + year;

                        holder.setTxtTitle(name);
                        holder.setTxtAddress(address);
                        holder.setTxtMobile(mobile);
                        holder.setTxtCity(date);
                        holder.setTxtImg(img);

                        holder.checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (holder.checkBox.isChecked()) {
                                    list.add(key);
                                } else {
                                    list.remove(key);
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


            }

            @NonNull
            @Override
            public MyMessCustHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_customer_profile_card, parent, false);

                return new MyMessCustHolder(view);
            }

        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void onClickBackAllCust(View view) {
        Intent intentProfile = new Intent(ManageCustomerActivity.this, OwnerDashboard.class);
        startActivity(intentProfile);
    }

    public String currentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public String currentDay() {
        return new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime());
    }

    private String getRemainingTime() {
        String delegate = "hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }

    public void onClickGetData(View view) {
        DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("ScheduleCustomer").child(login_name);
        Log.d("List :", list.toString());
        String date = currentDate();
        String time = getRemainingTime();
        Log.d("Time :", time);

        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.AM_PM) == Calendar.AM) {
            dff.child(date).child("AM").setValue(list);
        } else {
            dff.child(date).child("PM").setValue(list);
        }

        Toast.makeText(this, "Data Submitted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeOwnerFragment.class);
        startActivity(intent);
    }


}