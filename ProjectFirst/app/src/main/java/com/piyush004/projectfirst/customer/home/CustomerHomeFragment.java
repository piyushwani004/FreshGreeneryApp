package com.piyush004.projectfirst.customer.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.customer.search_mess.SearchMessLocation;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView textViewCurrentMess, textViewProfile;
    private ImageView imageViewLocation, imageViewProfile, imageViewSchedule, imageViewMessMenu;
    private Thread threadMessName = null;
    private Thread threadProfile = null;
    private String login_name;
    private String messCurrentMess;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerHome.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerHomeFragment newInstance(String param1, String param2) {
        CustomerHomeFragment fragment = new CustomerHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_home, container, false);
        login_name = LoginKey.loginKey;
        textViewCurrentMess = view.findViewById(R.id.cust_current_mess_c);
        imageViewLocation = view.findViewById(R.id.locationHome_c);
        imageViewProfile = view.findViewById(R.id.imageViewProfile_c);
        imageViewSchedule = view.findViewById(R.id.imageViewMessRates_c);
        imageViewMessMenu = view.findViewById(R.id.imageViewMessMenu_c);
        textViewProfile = view.findViewById(R.id.textCustProfile_c);


        threadMessName = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {

                        DatabaseReference dfmt = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                        dfmt.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messCurrentMess = snapshot.child("CustCurrentMess").getValue(String.class);
                                System.out.println(messCurrentMess);
                                if (messCurrentMess == null) {
                                    textViewCurrentMess.setText("NOT Set ");
                                } else {
                                    DatabaseReference dfmt = FirebaseDatabase.getInstance().getReference().child("Mess").child(messCurrentMess);
                                    dfmt.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String messName = snapshot.child("MessName").getValue(String.class);
                                            textViewCurrentMess.setText(messName);
                                            System.out.println(messName);
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
            }
        });
        threadMessName.start();

        threadProfile = new Thread(new Runnable() {

            @Override
            public void run() {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String imgprofile = snapshot.child("ImageURl").getValue(String.class);
                        String nameProfile = snapshot.child("CustName").getValue(String.class);

                        if (imgprofile == null) {
                            Picasso.get().load(R.drawable.profile_home).into(imageViewProfile);
                        } else {
                            Picasso.get().load(imgprofile).into(imageViewProfile);
                        }
                        if (nameProfile == null) {
                            textViewProfile.setText("Profile");
                        } else {
                            textViewProfile.setText(nameProfile);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        threadProfile.start();

        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLocation = new Intent(getActivity(), SearchMessLocation.class);
                startActivity(intentLocation);
            }
        });

        return view;
    }
}