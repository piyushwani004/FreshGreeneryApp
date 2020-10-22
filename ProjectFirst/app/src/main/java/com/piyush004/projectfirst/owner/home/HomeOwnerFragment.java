package com.piyush004.projectfirst.owner.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

public class HomeOwnerFragment extends Fragment {

    DatabaseReference databaseReference;
    private String latitude, longitude;
    private TextView textView1, textView2, textView3;
    String messName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_owner, container, false);

        final String login_name = LoginKey.loginKey;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessLocation");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messName = dataSnapshot.child("MessName").getValue(String.class);
                latitude = dataSnapshot.child("latitude").getValue(String.class);
                longitude = dataSnapshot.child("longitude").getValue(String.class);

                System.out.println("lati " + latitude + "::" + "longi " + longitude + "mess " + messName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textView1 = (TextView) view.findViewById(R.id.textView17);
        textView2 = (TextView) view.findViewById(R.id.textView18);
        textView3 = (TextView) view.findViewById(R.id.textView19);


        textView1.setText("latitude");
        textView2.setText("longitude");
        textView3.setText("mess name");

        return view;

    }
}
