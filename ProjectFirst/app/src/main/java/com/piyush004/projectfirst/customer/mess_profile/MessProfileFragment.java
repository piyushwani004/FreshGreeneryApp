package com.piyush004.projectfirst.customer.mess_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    private String Title, Address, Mobile, City, Img, key;

    private CircleImageView circleImageView;
    private TextView textViewMessName;

    public MessProfileFragment() {
        // Required empty public constructor
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
        
        DatabaseReference databaseReferenceProfile = FirebaseDatabase.getInstance().getReference().child("Mess").child(key);

        circleImageView = view.findViewById(R.id.circle_img_mess);
        textViewMessName = view.findViewById(R.id.mess_name_profile);

        Picasso.get().load(Img).into(circleImageView);
        textViewMessName.setText(Title);


        return view;
    }
}