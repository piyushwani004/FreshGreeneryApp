package com.piyush004.freshgreenery.Admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.freshgreenery.R;
import com.piyush004.freshgreenery.Utilities.AdminHome.Holder;
import com.piyush004.freshgreenery.Utilities.AdminHome.HomeModel;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recyclerView;

    private FirebaseRecyclerOptions<HomeModel> options;
    private FirebaseRecyclerAdapter<HomeModel, Holder> adapter;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recy_frag_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
        options = new FirebaseRecyclerOptions.Builder<HomeModel>().setQuery(df, new SnapshotParser<HomeModel>() {

            @NonNull
            @Override
            public HomeModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new HomeModel(

                        snapshot.child("Name").getValue(String.class),
                        snapshot.child("Date").getValue(String.class),
                        snapshot.child("Price").getValue(String.class),
                        snapshot.child("Quantity").getValue(String.class),
                        snapshot.child("ImageURl").getValue(String.class)
                );

            }
        }).build();

        adapter = new FirebaseRecyclerAdapter<HomeModel, Holder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull HomeModel model) {

                holder.setTxtName(model.getName());
                holder.setTxtDate(model.getDate());
                holder.setTxtPrice(model.getPrice());
                holder.setTxtQuantity(model.getQuantity());
                holder.setImgURL(model.getImgURL());

            }

            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);

                return new Holder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);


        return view;
    }
}