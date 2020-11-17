package com.piyush004.projectfirst.customer.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.Dashboard.CustomerDashboard;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

import java.util.Calendar;

public class MessScheduleActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private String login_name;
    private TextView textViewName, textViewJoinDate, textViewRemDays;
    private Thread threadMessName, threadCalender;
    private ImageButton imageButtonbackBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_schedule);

        login_name = LoginKey.loginKey;

        calendarView = findViewById(R.id.calendarView);
        textViewName = findViewById(R.id.textViewMessNameShedule);
        imageButtonbackBTN = findViewById(R.id.messScaduleBackBtn);
        textViewJoinDate = findViewById(R.id.messJoiningDate);
        textViewRemDays = findViewById(R.id.messRemainingDays);

        threadMessName = new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String messKey = snapshot.child("CustCurrentMess").getValue(String.class);
                        if (messKey == null) {
                            textViewName.setText("Not Set Mess Name");
                        } else {
                            DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Mess").child(messKey);
                            df.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String messName = snapshot.child("MessName").getValue(String.class);
                                    textViewName.setText(messName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        threadMessName.start();

        threadCalender = new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                final Calendar calendar = Calendar.getInstance();
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Integer day = snapshot.child("CustJoinDay").getValue(Integer.class);
                        Integer month = snapshot.child("CustJoinMonth").getValue(Integer.class);
                        Integer year = snapshot.child("CustJoinYear").getValue(Integer.class);

                        if (day == null && month == null && year == null) {

                            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
                            long milliTime = calendar.getTimeInMillis();
                            calendarView.setDate(milliTime, true, true);
                            textViewJoinDate.setText(" Not Set Mess ");

                        } else if (!(day == null && month == null && year == null)) {

                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, day);
                            long milliTime = calendar.getTimeInMillis();
                            calendarView.setDate(milliTime, true, true);
                            textViewJoinDate.setText(day + " / " + month + " / " + year);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        threadCalender.start();

        imageButtonbackBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSch = new Intent(MessScheduleActivity.this, CustomerDashboard.class);
                startActivity(intentSch);
            }
        });
    }

}