package com.piyush004.projectfirst.owner.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

public class LocationProfile extends Fragment {

    private GoogleMap mMap;
    private String login_name, messName;
    View view;
    private DatabaseReference databaseReference;
    private String latitude, longitude;
    FragmentManager fm;
    SupportMapFragment myMapFragment;

    public LocationProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location_profile, container, false);
        fm = getChildFragmentManager();
        myMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.locationMess);

        final String login_name = LoginKey.loginKey;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessLocation");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messName = dataSnapshot.child("MessName").getValue(String.class);
                latitude = dataSnapshot.child("latitude").getValue(String.class);
                longitude = dataSnapshot.child("longitude").getValue(String.class);

                final double lati = Double.parseDouble(latitude);
                final double longi = Double.parseDouble(longitude);

                myMapFragment.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap googlemap) {

                        mMap = googlemap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        //LatLng latLng = new LatLng(19.663280, 75.300293);
                        LatLng latLng = new LatLng(lati, longi);
                        mMap.addMarker(new MarkerOptions().position(latLng).title(messName));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                });

               // System.out.println("lati " + latitude + "::" + "longi " + longitude + "mess " + messName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

}