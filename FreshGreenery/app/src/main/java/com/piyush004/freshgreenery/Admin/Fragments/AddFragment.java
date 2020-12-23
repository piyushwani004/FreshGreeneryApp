package com.piyush004.freshgreenery.Admin.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.piyush004.freshgreenery.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AddFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Bitmap yourBitmap;
    private static int SELECT_PHOTO = 1;
    private Uri uri;
    private CircleImageView circleImageView;
    private EditText editTextPrice;
    private MaterialButton materialButtonAdd;
    private View view;

    public AddFragment() {
        // Required empty public constructor
    }


    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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

        view = inflater.inflate(R.layout.fragment_add, container, false);

        circleImageView = view.findViewById(R.id.circular_add_images);
        materialButtonAdd = view.findViewById(R.id.buttonAdd);
        editTextPrice = view.findViewById(R.id.editTextPrice);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {

            if (resultCode == RESULT_OK) {
                uri = data.getData();
                Picasso.get()
                        .load(uri)
                        .resize(500, 500)
                        .centerCrop().rotate(0)
                        .into(circleImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getContext(), "Fetched image from Gallery", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }


}