package com.piyush004.freshgreenery.User;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private Spinner spinner;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builderDelete;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FirebaseRecyclerOptions<HomeModel> options;
    private FirebaseRecyclerAdapter<HomeModel, Holder> adapter;
    int[] animationList = {R.anim.layout_animation_up_to_down,
            R.anim.layout_animation_right_to_left,
            R.anim.layout_animation_down_to_up,
            R.anim.layout_animation_left_to_right};
    private EditText editTextQuanty;

    private String Cardkey, SpinnerQuantity, EditTextQuantity;
    private String[] Quantity = {"/kg", "/Quintal", "0.5kilo"};
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


        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                        snapshot.child("ImageURl").getValue(String.class),
                        snapshot.child("ID").getValue(String.class)
                );

            }
        }).build();
        adapter = new FirebaseRecyclerAdapter<HomeModel, Holder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull final HomeModel model) {

                holder.setTxtName(model.getName());
                holder.setTxtDate(model.getDate());
                holder.setTxtPrice(model.getPrice());
                holder.setTxtQuantity(model.getQuantity());
                holder.setImgURL(model.getImgURL());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cardkey = model.getID();
                        final String Uid = firebaseAuth.getCurrentUser().getUid();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogLayout = inflater.inflate(R.layout.add_to_cart_dialoge, null);

                        editTextQuanty = dialogLayout.findViewById(R.id.editTextQuantityDia);
                        spinner = dialogLayout.findViewById(R.id.spinnerDia);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                SpinnerQuantity = Quantity[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Quantity);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(aa);


                        builder.setTitle("Add Cart Alert");
                        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditTextQuantity = editTextQuanty.getText().toString();

                                final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("UserData").child("Cart").child(Uid);
                                String key = df.push().getKey();
                                df.child(key).child("CardID").setValue(Cardkey);
                                df.child(key).child("Quantity").setValue(EditTextQuantity);
                                df.child(key).child("Quant").setValue(SpinnerQuantity);

                                Toast.makeText(getContext(), "Add to cart Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                        builder.setNegativeButton("Closed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.setView(dialogLayout);
                        builder.show();

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