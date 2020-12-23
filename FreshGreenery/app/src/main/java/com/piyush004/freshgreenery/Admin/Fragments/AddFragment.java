package com.piyush004.freshgreenery.Admin.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.piyush004.freshgreenery.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AddFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private CircleImageView circleImageView;
    private EditText editTextPrice;
    private MaterialButton materialButtonAdd;
    private View view;

    private final static int ALL_PERMISSIONS_RESULT = 107;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add, container, false);

        circleImageView = view.findViewById(R.id.circular_add_images);
        materialButtonAdd = view.findViewById(R.id.buttonAdd);
        editTextPrice = view.findViewById(R.id.editTextPrice);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}