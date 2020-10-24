package com.piyush004.projectfirst.owner.profile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessDetailsProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessDetailsProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private TextView textViewName, textViewAddress, textViewMobile, textViewEmail, textViewCity, textViewClosedDay;
    private Button buttonEdit;
    private DatabaseReference databaseReference;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String login_name;
    private String Name, Address, Mobile, Email, City, ClosedDays;

    public MessDetailsProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static MessDetailsProfile newInstance(String param1, String param2) {
        MessDetailsProfile fragment = new MessDetailsProfile();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mess_details, container, false);

        textViewName = (TextView) view.findViewById(R.id.textViewMessNameProfile);
        textViewAddress = (TextView) view.findViewById(R.id.textViewMessAdressProfile);
        textViewMobile = (TextView) view.findViewById(R.id.textViewMessMobileProfile);
        textViewEmail = (TextView) view.findViewById(R.id.textViewMessEmailProfile);
        textViewCity = (TextView) view.findViewById(R.id.textViewMessCityProfile);
        textViewClosedDay = (TextView) view.findViewById(R.id.textViewMessClosedDayProfile);
        login_name = LoginKey.loginKey;
        buttonEdit = (Button) view.findViewById(R.id.buttonEditProfile);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name = snapshot.child(login_name).child("MessDetails").child("MessName").getValue(String.class);
                Address = snapshot.child(login_name).child("MessDetails").child("MessAddress").getValue(String.class);
                Mobile = snapshot.child(login_name).child("MessDetails").child("MessMobile").getValue(String.class);
                Email = snapshot.child(login_name).child("MessDetails").child("MessEmail").getValue(String.class);
                City = snapshot.child(login_name).child("MessDetails").child("MessCity").getValue(String.class);
                ClosedDays = snapshot.child(login_name).child("MessDetails").child("MessClosedDays").getValue(String.class);

                if (Name.isEmpty()) {
                    textViewName.setText("");
                } else if (Address.isEmpty()) {
                    textViewAddress.setText("");
                } else if (Mobile.isEmpty()) {
                    textViewMobile.setText("");
                } else if (Email.isEmpty()) {
                    textViewEmail.setText("");
                } else if (City.isEmpty()) {
                    textViewCity.setText("");
                } else if (ClosedDays.isEmpty()) {
                    textViewClosedDay.setText("");
                } else if (!(Name.isEmpty() || Address.isEmpty() || Mobile.isEmpty() || Email.isEmpty() || City.isEmpty() || ClosedDays.isEmpty())) {

                    textViewName.setText(Name);
                    textViewAddress.setText(Address);
                    textViewMobile.setText(Mobile);
                    textViewEmail.setText(Email);
                    textViewCity.setText(City);
                    textViewClosedDay.setText(ClosedDays);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.mess_details_edit_dialogbox, null);

                final TextView name = dialogLayout.findViewById(R.id.edit_text_nameDialog);
                final TextView address = dialogLayout.findViewById(R.id.edit_text_addressDialog);
                final TextView mobile = dialogLayout.findViewById(R.id.edit_text_mobileDialog);
                final TextView email = dialogLayout.findViewById(R.id.edit_text_emailDialog);
                final TextView city = dialogLayout.findViewById(R.id.edit_text_cityDialog);
                final TextView closedDays = dialogLayout.findViewById(R.id.edit_text_closed_daysDialog);

                name.setText(textViewName.getText());
                address.setText(textViewAddress.getText());
                mobile.setText(textViewMobile.getText());
                email.setText(textViewEmail.getText());
                city.setText(textViewCity.getText());
                closedDays.setText(textViewClosedDay.getText());

                builder.setTitle("Mess Details Edit Form");
                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType").child(login_name).child("MessDetails");
                        databaseReference.child("MessName").setValue(name.getText().toString());
                        databaseReference.child("MessAddress").setValue(address.getText().toString());
                        databaseReference.child("MessMobile").setValue(mobile.getText().toString());
                        databaseReference.child("MessEmail").setValue(email.getText().toString());
                        databaseReference.child("MessCity").setValue(city.getText().toString());
                        databaseReference.child("MessClosedDays").setValue(closedDays.getText().toString());

                        Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Closed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(dialogLayout);
                builder.show();
            }
        });

        return view;
    }

}

