package com.piyush004.projectfirst.owner.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.owner.map.MapsOwnerActivity;
import com.piyush004.projectfirst.owner.messmenu.MessMenuActivity;
import com.piyush004.projectfirst.owner.profile.ProfileOwnerActivity;

import java.util.ArrayList;

public class HomeOwnerFragment extends Fragment {

    private TextView textView;
    private String MenuItem;
    private String login_name, messName;
    private DatabaseReference databaseReference , databaseReferenceLoc;
    private ToggleButton toggleButton;
    private ImageView imageViewLocation, imageViewTodaysMenu, imageViewProfile, imageViewCustomer, imageViewMessMenu;
    private ChipGroup chipGroup;
    private EditText editText;
    private ImageButton imageButton;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment_owner, container, false);

        textView = view.findViewById(R.id.textViewProfileHeading);
        toggleButton = view.findViewById(R.id.toggleButton);
        imageViewLocation = view.findViewById(R.id.locationHome);
        imageViewTodaysMenu = view.findViewById(R.id.imageViewTodaysMenu);
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        imageViewCustomer = view.findViewById(R.id.imageViewCustomer);
        imageViewMessMenu = view.findViewById(R.id.imageViewMessMenu);

        login_name = LoginKey.loginKey;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Mess").child(login_name);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messName = snapshot.child("MessName").getValue(String.class);
                if (messName == null) {
                    textView.setText("Not Set Mess Name");
                } else {
                    textView.setText(messName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Mess").child(login_name);
        df.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Messstatus = snapshot.child("Status").getValue(String.class);
                if (Messstatus == null) {
                    toggleButton.setText("Status");
                } else {
                    toggleButton.setText(Messstatus);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Mess").child(login_name);
                Toast.makeText(getContext(), toggleButton.getText(), Toast.LENGTH_SHORT).show();
                String status = toggleButton.getText().toString();
                databaseReference.child("Status").setValue(status);

                switch (status) {
                    case "Open":
                        toggleButton.setTextColor(Color.GREEN);
                        break;
                    case "Closed":
                        toggleButton.setTextColor(Color.RED);
                        break;
                }
            }
        });

        imageViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "ImageView Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MapsOwnerActivity.class);
                startActivity(intent);
            }
        });

        imageViewTodaysMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> list = new ArrayList<String>();
                final DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("Mess").child(login_name);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.todays_menu_dialog, null);

                editText = dialogLayout.findViewById(R.id.editTextTodayMenuEntry);
                imageButton = dialogLayout.findViewById(R.id.addMenuItemBtn);
                chipGroup = dialogLayout.findViewById(R.id.chipGroup);

                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Chip chip = new Chip(getContext());
                        ChipDrawable chipDrawable = ChipDrawable.createFromAttributes(getContext(), null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
                        chip.setChipDrawable(chipDrawable);
                        chip.setCheckable(false);
                        chip.setClickable(false);
                        chip.setChipIconResource(R.drawable.ic_baseline_restaurant_menu_24);
                        chip.setIconStartPadding(3f);
                        chip.setPadding(60, 10, 60, 10);
                        chip.setText(editText.getText().toString());

                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chipGroup.removeView(chip);
                            }
                        });
                        chipGroup.addView(chip);
                        editText.setText("");
                    }
                });

                builder.setTitle("Todays Menu Entry Form");
                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < chipGroup.getChildCount(); j++) {
                            Chip chip = (Chip) chipGroup.getChildAt(j);
                            Log.i("outside if ", j + " chip = " + chip.getText().toString());
                            list.add(chip.getText().toString());
                        }
                        nm.child("todayMenu").setValue(list);
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
                Toast.makeText(getContext(), "ImageView Click", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewTodaysMenu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), "long Click Listener", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ImageView Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProfileOwnerActivity.class);
                startActivity(intent);
            }
        });

        imageViewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "ImageView Click", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewMessMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessMenuActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
