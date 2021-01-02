package com.piyush004.freshgreenery.User;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.freshgreenery.R;
import com.piyush004.freshgreenery.Utilities.AdminHome.Holder;
import com.piyush004.freshgreenery.Utilities.AdminHome.HomeModel;

public class FragmentHomeUser extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth firebaseAuth;
    private View view;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builderDelete;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRecyclerOptions<HomeModel> options;
    private FirebaseRecyclerAdapter<HomeModel, Holder> adapter;
    int[] animationList = {R.anim.layout_animation_up_to_down,
            R.anim.layout_animation_right_to_left,
            R.anim.layout_animation_down_to_up,
            R.anim.layout_animation_left_to_right};
    private String Cardkey;
    private String ImgURL, Name, Price, VegQuant;
    public ArrayAdapter arrayAdapter;

    public FragmentHomeUser() {
        // Required empty public constructor
    }

    public static FragmentHomeUser newInstance(String param1, String param2) {
        FragmentHomeUser fragment = new FragmentHomeUser();
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

        view = inflater.inflate(R.layout.fragment_home_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.recyFragHomeUser);
        swipeRefreshLayout = view.findViewById(R.id.swipeFragHomeUser);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(manager);

        FirebaseDatabase.getInstance().getReference().child("Cart").removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Log.e("Project", "cart  delete Complete");
            }
        });

        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
        options = new FirebaseRecyclerOptions.Builder<HomeModel>().setQuery(df, new SnapshotParser<HomeModel>() {

            @NonNull
            @Override
            public HomeModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new HomeModel(

                        snapshot.child("Name").getValue(String.class),
                        snapshot.child("Date").getValue(String.class),
                        snapshot.child("Rate").getValue(String.class),
                        snapshot.child("RateWeight").getValue(String.class),
                        snapshot.child("ImageURl").getValue(String.class),
                        snapshot.child("ID").getValue(String.class),
                        snapshot.child("TotalQuantity").getValue(String.class),
                        snapshot.child("TotalWeight").getValue(String.class)
                );

            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<HomeModel, Holder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull final HomeModel model) {

                holder.setTxtName(model.getName());
                holder.setTxtDate(model.getDate());
                holder.setTxtUserRate(model.getPrice(), model.getQuantity());
                holder.setImgURL(model.getImgURL());
                holder.setTxtTotalQuantity(model.getTotalQuantity(), model.getTotalWeight());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cardkey = model.getID();
                        final String Uid = firebaseAuth.getCurrentUser().getUid();

                        builderDelete = new AlertDialog.Builder(getContext());
                        builderDelete.setMessage("Do You Want To Add this content to your Cart ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Cart").child(Uid);

                                        final DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(Cardkey);
                                        dff.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                ImgURL = snapshot.child("ImageURl").getValue(String.class);
                                                Name = snapshot.child("Name").getValue(String.class);
                                                Price = snapshot.child("Rate").getValue(String.class);
                                                VegQuant = snapshot.child("RateWeight").getValue(String.class);

                                                df.child(Cardkey).child("CardID").setValue(Cardkey);
                                                df.child(Cardkey).child("CartImageURl").setValue(ImgURL);
                                                df.child(Cardkey).child("CartName").setValue(Name);
                                                df.child(Cardkey).child("CartPrice").setValue(Price);
                                                df.child(Cardkey).child("CartQuantity").setValue(VegQuant);
                                                Log.e("Project", "cart Add");
                                                // Toast.makeText(getContext(), "Add to your cart Successfully", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builderDelete.create();
                        alert.setTitle("Add Cart Alert");
                        alert.show();

                    }
                });

                holder.circleImageViewHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            }

            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_card, parent, false);

                return new Holder(view);
            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                runAnimationAgain();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });


        return view;
    }

    private void runAnimationAgain() {
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getContext(), animationList[1]);
        recyclerView.setLayoutAnimation(controller);
        adapter.notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


}