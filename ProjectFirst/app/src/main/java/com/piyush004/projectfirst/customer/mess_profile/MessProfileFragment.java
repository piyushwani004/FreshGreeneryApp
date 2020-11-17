package com.piyush004.projectfirst.customer.mess_profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.customer.schedule.MessScheduleActivity;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


public class MessProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String Title, Address, Mobile, City, Img, key, login_name , custName;
    private CircleImageView circleImageView;
    private TextView textViewMessName, textViewAddress, textViewMobile, textViewEmail, textViewCity, textViewStatus, textViewBoysRate, textViewGirlsRate;
    private Button button;
    private int month, year, day;
    private Thread thread = null;

    public MessProfileFragment() {
        // Required empty public constructor
    }

    public MessProfileFragment(String LocKey) {
        this.key = LocKey;
    }

    public MessProfileFragment(String title, String address, String mobile, String city, String image, String key) {
        this.Title = title;
        this.Address = address;
        this.Mobile = mobile;
        this.City = city;
        this.Img = image;
        this.key = key;
    }

    public static MessProfileFragment newInstance(String param1, String param2) {
        MessProfileFragment fragment = new MessProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mess_profile, container, false);

        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        login_name = LoginKey.loginKey;
        DatabaseReference databaseReferenceProfile = FirebaseDatabase.getInstance().getReference().child("Mess").child(key);

        circleImageView = view.findViewById(R.id.circle_img_mess);

        textViewMessName = view.findViewById(R.id.mess_name_profile);
        textViewAddress = view.findViewById(R.id.mess_address_profile);
        textViewCity = view.findViewById(R.id.mess_city_profile);
        textViewMobile = view.findViewById(R.id.mess_mobile_profile);
        textViewEmail = view.findViewById(R.id.mess_email_profile);
        textViewBoysRate = view.findViewById(R.id.messRateForBoys);
        textViewGirlsRate = view.findViewById(R.id.messRateForGirls);
        textViewStatus = view.findViewById(R.id.mess_status_profile);

        button = view.findViewById(R.id.mess_button_profile);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        DatabaseReference dfcheck = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                        dfcheck.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String messCurrentMess = snapshot.child("CustCurrentMess").getValue(String.class);
                                Log.d(TAG, "onDataChange: " + messCurrentMess);
                                if (messCurrentMess == null) {
                                    button.setVisibility(View.VISIBLE);
                                } else {
                                    button.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });
        thread.start();

        databaseReferenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String email = snapshot.child("MessEmail").getValue(String.class);
                String status = snapshot.child("Status").getValue(String.class);
                String boysRate = snapshot.child("BoysMessRate").getValue(String.class);
                String girlRate = snapshot.child("GirlsMessRate").getValue(String.class);

                Title = snapshot.child("MessName").getValue(String.class);
                Address = snapshot.child("MessAddress").getValue(String.class);
                Mobile = snapshot.child("MessMobile").getValue(String.class);
                City = snapshot.child("MessCity").getValue(String.class);
                Img = snapshot.child("ImageURl").getValue(String.class);

                Picasso.get().load(Img).into(circleImageView);
                textViewMessName.setText(Title);
                textViewAddress.setText(Address);
                textViewMobile.setText(Mobile);
                textViewCity.setText(City);
                textViewEmail.setText(email);
                textViewStatus.setText(status);
                textViewBoysRate.setText(boysRate + "Rs");
                textViewGirlsRate.setText(girlRate + "Rs");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                databaseReference.child("CustCurrentMess").setValue(key);
                databaseReference.child("CustJoinDay").setValue(day);
                databaseReference.child("CustJoinMonth").setValue(month);
                databaseReference.child("CustJoinYear").setValue(year);

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("ManageCustomer").child(key);
                df.child(login_name).setValue(login_name);

                Toast.makeText(getContext(), "Save Current Mess :", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MessScheduleActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }
}