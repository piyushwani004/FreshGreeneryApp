package com.piyush004.freshgreenery.User;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.freshgreenery.R;
import com.piyush004.freshgreenery.Utilities.AdminHome.Holder;
import com.piyush004.freshgreenery.Utilities.AdminHome.HomeModel;

import static android.content.ContentValues.TAG;

public class FragmentCartUser extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private ImageButton arrowItem, arrowAddress;
    private LinearLayout hiddenViewItem, hiddenViewAddress;
    private LinearLayout linearLayoutItem, linearLayoutAddress;
    private View view;
    private Thread threadAddress = null;
    private Thread threadRecycle = null;
    private String mobile, address, city, society, flat, key, orderBy;
    private MaterialButton materialButton;
    private TextView textViewMobile, textViewCity, textViewAddress, textViewSociety, textViewFlatNo;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private FirebaseRecyclerOptions<HomeModel> options;
    private FirebaseRecyclerAdapter<HomeModel, Holder> adapter;

    private RecyclerView recyclerView;

    public FragmentCartUser() {
        // Required empty public constructor
    }

    public static FragmentCartUser newInstance(String param1, String param2) {
        FragmentCartUser fragment = new FragmentCartUser();
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

        view = inflater.inflate(R.layout.fragment_cart_user, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        arrowItem = view.findViewById(R.id.arrow_button_Items);
        hiddenViewItem = view.findViewById(R.id.hidden_view_Items);
        linearLayoutItem = view.findViewById(R.id.linearItem);
        materialButton = view.findViewById(R.id.button_order_Cart);

        recyclerView = view.findViewById(R.id.recyItemCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        textViewMobile = view.findViewById(R.id.TextMobileCart);
        textViewAddress = view.findViewById(R.id.TextAddressCart);
        textViewCity = view.findViewById(R.id.TextCityCard);
        textViewSociety = view.findViewById(R.id.TextSocietyNameCart);
        textViewFlatNo = view.findViewById(R.id.TextFlatNoCart);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        arrowAddress = view.findViewById(R.id.arrow_button_Address);
        hiddenViewAddress = view.findViewById(R.id.hidden_view_Address);
        linearLayoutAddress = view.findViewById(R.id.linearAddress);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radioButton = (RadioButton) group.findViewById(checkedId);
                        if (null != radioButton && checkedId > -1) {
                            orderBy = radioButton.getText().toString();
                            // Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        arrowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenViewItem.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(linearLayoutItem,
                            new AutoTransition());
                    hiddenViewItem.setVisibility(View.GONE);
                    arrowItem.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {

                    TransitionManager.beginDelayedTransition(linearLayoutItem,
                            new AutoTransition());
                    hiddenViewItem.setVisibility(View.VISIBLE);
                    arrowItem.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        arrowAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenViewAddress.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(linearLayoutAddress,
                            new AutoTransition());
                    hiddenViewAddress.setVisibility(View.GONE);
                    arrowAddress.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {

                    TransitionManager.beginDelayedTransition(linearLayoutAddress,
                            new AutoTransition());
                    hiddenViewAddress.setVisibility(View.VISIBLE);
                    arrowAddress.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(getContext(), "No Order Method has been selected", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
                    orderBy = radioButton.getText().toString();
                    //Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        threadRecycle = new Thread(new Runnable() {
            @Override
            public void run() {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        final String Uid = firebaseAuth.getCurrentUser().getUid();
                        final DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("UserData").child("Cart").child(firebaseAuth.getCurrentUser().getUid());
                        dff.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("UserData").child("Cart").child(Uid);

                                    options = new FirebaseRecyclerOptions.Builder<HomeModel>().setQuery(df, new SnapshotParser<HomeModel>() {

                                        @NonNull
                                        @Override
                                        public HomeModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                                            return new HomeModel(
                                                    snapshot.child("CartName").getValue(String.class),
                                                    snapshot.child("UserQuantity").getValue(String.class),
                                                    snapshot.child("CartPrice").getValue(String.class),
                                                    snapshot.child("UserQuant").getValue(String.class),
                                                    snapshot.child("CartImageURl").getValue(String.class),
                                                    snapshot.child("CardID").getValue(String.class)
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

                                            Log.e(TAG, "inside=======" + model.toString());
                                            HomeModel homeModel = new HomeModel();
                                            Log.e(TAG, "=======" + homeModel.toString());
                                        }

                                        @NonNull
                                        @Override
                                        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_home_card, parent, false);

                                            return new Holder(view);
                                        }
                                    };
                                    adapter.startListening();
                                    recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }
        });
        threadRecycle.start();

        threadAddress = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        key = firebaseAuth.getCurrentUser().getUid();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserData").child("Address");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                mobile = snapshot.child(key).child("Mobile").getValue(String.class);
                                address = snapshot.child(key).child("Address").getValue(String.class);
                                city = snapshot.child(key).child("City").getValue(String.class);
                                society = snapshot.child(key).child("SocietyName").getValue(String.class);
                                flat = snapshot.child(key).child("FlatNo").getValue(String.class);

                                if (mobile == null && address == null) {

                                    materialButton.setVisibility(View.GONE);
                                    Snackbar snackBar = Snackbar.make(getView(), "Filled Address Section First..", Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            FragmentLocationUser fragment2 = new FragmentLocationUser();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.containerHomeUser, fragment2);
                                            fragmentTransaction.commit();
                                        }
                                    });
                                    snackBar.setActionTextColor(Color.BLUE);
                                    View snackBarView = snackBar.getView();
                                    TextView textView = snackBarView.findViewById(R.id.snackbar_text);
                                    textView.setTextColor(Color.RED);
                                    snackBar.show();

                                    //Toast.makeText(getContext(), "Filled Address Section First..", Toast.LENGTH_LONG).show();
                                } else if (!(mobile == null && address == null)) {

                                    textViewMobile.setText(mobile);
                                    textViewAddress.setText(address);
                                    textViewCity.setText(city);
                                    textViewSociety.setText(society);
                                    textViewFlatNo.setText(flat);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });
            }
        });
        threadAddress.start();

        return view;
    }
}