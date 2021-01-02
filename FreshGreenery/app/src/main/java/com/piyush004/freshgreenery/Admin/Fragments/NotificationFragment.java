package com.piyush004.freshgreenery.Admin.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.freshgreenery.R;
import com.piyush004.freshgreenery.Utilities.AdminHome.AdminHolder;
import com.piyush004.freshgreenery.Utilities.AdminHome.AdminModel;

public class NotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<AdminModel> options;
    private FirebaseRecyclerAdapter<AdminModel, AdminHolder> adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.adminNotificationRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final DatabaseReference fetchAdminBill = FirebaseDatabase.getInstance().getReference().child("AdminData").child("Billing");
        options = new FirebaseRecyclerOptions.Builder<AdminModel>().setQuery(fetchAdminBill, new SnapshotParser<AdminModel>() {

            @NonNull
            @Override
            public AdminModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new AdminModel(

                        snapshot.child("Bill").child("OrderID").getValue(String.class),
                        snapshot.child("Bill").child("UserName").getValue(String.class),
                        snapshot.child("Bill").child("TotalRate").getValue(String.class),
                        snapshot.child("Bill").child("Date").getValue(String.class),
                        snapshot.child("Bill").child("Mobile").getValue(String.class),
                        snapshot.child("Bill").child("Time").getValue(String.class),
                        snapshot.child("Bill").child("NoOfItems").getValue(String.class),
                        snapshot.child("Bill").child("Address").getValue(String.class),
                        snapshot.child("Bill").child("City").getValue(String.class),
                        snapshot.child("Bill").child("SocietyName").getValue(String.class),
                        snapshot.child("Bill").child("FlatNo").getValue(String.class)
                );

            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<AdminModel, AdminHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final AdminHolder holder, int position, @NonNull final AdminModel model) {

                holder.imageButtonArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (holder.hiddenView.getVisibility() == View.VISIBLE) {

                            TransitionManager.beginDelayedTransition(holder.cardView,
                                    new AutoTransition());
                            holder.hiddenView.setVisibility(View.GONE);
                            holder.imageButtonArrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                        } else {

                            TransitionManager.beginDelayedTransition(holder.cardView,
                                    new AutoTransition());
                            holder.hiddenView.setVisibility(View.VISIBLE);
                            holder.imageButtonArrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                        }

                    }
                });

                holder.textViewOrderId.setText(model.getOrderId());
                holder.textViewUserName.setText(model.getUserName());
                holder.textViewRateAdmin.setText(model.getRate() + "Rs");
                holder.textViewDateAdmin.setText(model.getDate());

                holder.textViewMobile.setText(model.getMobile());
                holder.textViewAddress.setText(model.getAddress());
                holder.textViewTime.setText(model.getTime());
                holder.textViewNoItems.setText(model.getNoItems());
                holder.textViewCity.setText(model.getCity());
                holder.textViewSociety.setText(model.getSocietyName());
                holder.textViewFlatNo.setText(model.getFlatNo());

                holder.imageViewCheckOrderConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Order Ready", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @NonNull
            @Override
            public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_noti_cart, parent, false);

                return new AdminHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return view;
    }
}