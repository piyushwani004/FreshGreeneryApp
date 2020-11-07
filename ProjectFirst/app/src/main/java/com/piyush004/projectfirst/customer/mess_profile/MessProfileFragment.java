package com.piyush004.projectfirst.customer.mess_profile;

import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String Title, Address, Mobile, City, Img, key , login_name;
    private CircleImageView circleImageView;
    private TextView textViewMessName, textViewAddress, textViewMobile, textViewEmail, textViewCity, textViewStatus, textViewBoysRate, textViewGirlsRate;
    private Button button;

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


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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
                Toast.makeText(getContext(), "Save Current Mess :", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}