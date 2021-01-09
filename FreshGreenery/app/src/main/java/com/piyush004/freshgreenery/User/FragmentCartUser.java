package com.piyush004.freshgreenery.User;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.piyush004.freshgreenery.Utilities.AdminHome.CartItems;
import com.piyush004.freshgreenery.Utilities.AdminHome.Holder;
import com.piyush004.freshgreenery.Utilities.AdminHome.HomeModel;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class FragmentCartUser extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageButton arrowItem, arrowAddress, arrowBill;
    private LinearLayout hiddenViewItem, hiddenViewAddress, hiddenViewBill;
    private LinearLayout linearLayoutItem, linearLayoutAddress, linearLayoutBill;

    private View view;
    private Thread threadAddress = null;
    private String mobile, address, city, society, flat, key, orderBy, date, NoOfItems, cartTotChrg, time;
    private MaterialButton materialButton;
    private TextView textViewMobile, textViewCity, textViewAddress, textViewSociety, textViewFlatNo;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private AlertDialog.Builder builderDelete;
    private FirebaseRecyclerOptions<HomeModel> options;
    private FirebaseRecyclerAdapter<HomeModel, Holder> adapter;
    private SimpleDateFormat simpleDateFormat;
    private TextView cart_date, cartNoItems, cartTotalCharge, cartOrderMethod;
    private String value, Weight;
    private double val;
    private RecyclerView recyclerView;
    public TextView TotalPriceCart;
    private AlertDialog.Builder builder;
    private TextView OrderIdDialoge, orderPriceDialoge;
    ArrayList<CartItems> list = new ArrayList<CartItems>();

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

        final Date data = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        date = simpleDateFormat.format(data);

        arrowItem = view.findViewById(R.id.arrow_button_Items);
        hiddenViewItem = view.findViewById(R.id.hidden_view_Items);
        linearLayoutItem = view.findViewById(R.id.linearItem);
        materialButton = view.findViewById(R.id.button_order_Cart);

        hiddenViewBill = view.findViewById(R.id.hidden_view_Bill);
        linearLayoutBill = view.findViewById(R.id.linearBill);
        arrowBill = view.findViewById(R.id.arrow_button_Bill);

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

        // billing Section id
        cart_date = view.findViewById(R.id.cart_date);
        cartNoItems = view.findViewById(R.id.cartNoItems);
        cartTotalCharge = view.findViewById(R.id.cartTotalCharge);
        cartOrderMethod = view.findViewById(R.id.cartOrderMethod);
        TotalPriceCart = view.findViewById(R.id.TotalPriceCart);

        builder = new AlertDialog.Builder(getContext());

        cart_date.setText(date);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        radioButton = (RadioButton) group.findViewById(checkedId);
                        if (null != radioButton && checkedId > -1) {
                            orderBy = radioButton.getText().toString();
                            cartOrderMethod.setText(orderBy);
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

        arrowBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hiddenViewBill.getVisibility() == View.VISIBLE) {

                    TransitionManager.beginDelayedTransition(linearLayoutBill,
                            new AutoTransition());
                    hiddenViewBill.setVisibility(View.GONE);
                    arrowBill.setImageResource(R.drawable.ic_baseline_expand_more_24);
                } else {

                    TransitionManager.beginDelayedTransition(linearLayoutBill,
                            new AutoTransition());
                    hiddenViewBill.setVisibility(View.VISIBLE);
                    arrowBill.setImageResource(R.drawable.ic_baseline_expand_less_24);


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
                }

                date = cart_date.getText().toString();
                NoOfItems = cartNoItems.getText().toString();
                orderBy = cartOrderMethod.getText().toString();
                cartTotChrg = cartTotalCharge.getText().toString();

                mobile = textViewMobile.getText().toString();
                address = textViewAddress.getText().toString();
                city = textViewCity.getText().toString();
                society = textViewSociety.getText().toString();
                flat = textViewFlatNo.getText().toString();


                if (date.isEmpty()) {
                } else if (NoOfItems.isEmpty()) {
                    Toast.makeText(getContext(), "Select Items Quantity...", Toast.LENGTH_SHORT).show();
                } else if (orderBy.isEmpty()) {
                    Toast.makeText(getContext(), "Select Delivery Option...", Toast.LENGTH_SHORT).show();
                } else if (cartTotChrg.isEmpty()) {
                    Toast.makeText(getContext(), "Select Items Quantity...", Toast.LENGTH_SHORT).show();
                } else if (list.isEmpty()) {
                    Toast.makeText(getContext(), "Select Items Quantity...", Toast.LENGTH_SHORT).show();
                } else if (!(date.isEmpty() && NoOfItems.isEmpty() && orderBy.isEmpty() && cartTotChrg.isEmpty() && list.isEmpty())) {

                    materialButton.setVisibility(View.VISIBLE);
                    final Calendar calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("hh:mm");
                    time = simpleDateFormat.format(calendar.getTime());
                    final String Tempkey = generateRandomString(6);
                    key = Tempkey + "-" + date + "-" + time;

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.order_confirm_dialoge, null);
                    OrderIdDialoge = dialogLayout.findViewById(R.id.textVieworderidDialoge);
                    orderPriceDialoge = dialogLayout.findViewById(R.id.textViewMRPDialoge);
                    builder.setTitle("Order Confirmation");
                    OrderIdDialoge.setText(key);
                    orderPriceDialoge.setText(cartTotChrg + "Rs");

                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            String uid = firebaseAuth.getCurrentUser().getUid();


                            final DatabaseReference dfUser = FirebaseDatabase.getInstance().getReference().child("Billing").child(uid);


                            dfUser.child(key).child("Bill").child("OrderID").setValue(key);
                            final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("AppUsers").child(uid);
                            df.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userName = snapshot.child("Name").getValue().toString();

                                    dfUser.child(key).child("Bill").child("UserName").setValue(userName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                            dfUser.child(key).child("Bill").child("Date").setValue(date);
                            dfUser.child(key).child("Bill").child("Time").setValue(time);
                            dfUser.child(key).child("Bill").child("NoOfItems").setValue(NoOfItems);
                            dfUser.child(key).child("Bill").child("OrderMethod").setValue(orderBy);
                            dfUser.child(key).child("Bill").child("TotalRate").setValue(cartTotChrg);

                            dfUser.child(key).child("Bill").child("Mobile").setValue(mobile);
                            dfUser.child(key).child("Bill").child("Address").setValue(address);
                            dfUser.child(key).child("Bill").child("City").setValue(city);
                            dfUser.child(key).child("Bill").child("SocietyName").setValue(society);
                            dfUser.child(key).child("Bill").child("FlatNo").setValue(flat);

                            Iterator itr = list.iterator();
                            while (itr.hasNext()) {

                                CartItems item = (CartItems) itr.next();
                                String itemKey = dfUser.push().getKey();
                                dfUser.child(key).child("ItemList").child(itemKey).child("Name").setValue(item.name);
                                dfUser.child(key).child("ItemList").child(itemKey).child("weight").setValue(item.weight);
                                dfUser.child(key).child("ItemList").child(itemKey).child("rate").setValue(item.rate);

                            }


                            //admin section
                            final DatabaseReference Admin = FirebaseDatabase.getInstance().getReference().child("AdminData").child("Billing");
                            Admin.child(key).child("Bill").child("OrderID").setValue(key);
                            Admin.child(key).child("Bill").child("Date").setValue(date);
                            Admin.child(key).child("Bill").child("Time").setValue(time);
                            Admin.child(key).child("Bill").child("NoOfItems").setValue(NoOfItems);
                            Admin.child(key).child("Bill").child("OrderMethod").setValue(orderBy);
                            Admin.child(key).child("Bill").child("TotalRate").setValue(cartTotChrg);

                            Admin.child(key).child("Bill").child("Mobile").setValue(mobile);
                            Admin.child(key).child("Bill").child("Address").setValue(address);
                            Admin.child(key).child("Bill").child("City").setValue(city);
                            Admin.child(key).child("Bill").child("SocietyName").setValue(society);
                            Admin.child(key).child("Bill").child("FlatNo").setValue(flat);

                            final DatabaseReference admin = FirebaseDatabase.getInstance().getReference().child("AppUsers").child(uid);
                            admin.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userName = snapshot.child("Name").getValue().toString();

                                    Admin.child(key).child("Bill").child("UserName").setValue(userName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                            Iterator itrad = list.iterator();
                            while (itrad.hasNext()) {

                                CartItems item = (CartItems) itrad.next();
                                String itemKey = dfUser.push().getKey();
                                Admin.child(key).child("ItemList").child(itemKey).child("Name").setValue(item.name);
                                Admin.child(key).child("ItemList").child(itemKey).child("weight").setValue(item.weight);
                                Admin.child(key).child("ItemList").child(itemKey).child("rate").setValue(item.rate);
                            }


                            //admin History
                            final DatabaseReference moveToHis = FirebaseDatabase.getInstance().getReference().child("AdminData").child("History");

                            moveToHis.child(key).child("Bill").child("OrderID").setValue(key);
                            moveToHis.child(key).child("Bill").child("Date").setValue(date);
                            moveToHis.child(key).child("Bill").child("Time").setValue(time);
                            moveToHis.child(key).child("Bill").child("NoOfItems").setValue(NoOfItems);
                            moveToHis.child(key).child("Bill").child("OrderMethod").setValue(orderBy);
                            moveToHis.child(key).child("Bill").child("TotalRate").setValue(cartTotChrg);

                            moveToHis.child(key).child("Bill").child("Mobile").setValue(mobile);
                            moveToHis.child(key).child("Bill").child("Address").setValue(address);
                            moveToHis.child(key).child("Bill").child("City").setValue(city);
                            moveToHis.child(key).child("Bill").child("SocietyName").setValue(society);
                            moveToHis.child(key).child("Bill").child("FlatNo").setValue(flat);

                            final DatabaseReference userName = FirebaseDatabase.getInstance().getReference().child("AppUsers").child(uid);
                            userName.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String userName = snapshot.child("Name").getValue().toString();

                                    moveToHis.child(key).child("Bill").child("UserName").setValue(userName);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Iterator itrHis = list.iterator();
                            while (itrHis.hasNext()) {

                                CartItems item = (CartItems) itrHis.next();
                                String itemKey = dfUser.push().getKey();
                                moveToHis.child(key).child("ItemList").child(itemKey).child("Name").setValue(item.name);
                                moveToHis.child(key).child("ItemList").child(itemKey).child("weight").setValue(item.weight);
                                moveToHis.child(key).child("ItemList").child(itemKey).child("rate").setValue(item.rate);
                            }

                            //Stock Maintain Section
                            Iterator itrSearch = list.iterator();
                            while (itrSearch.hasNext()) {

                                final CartItems item = (CartItems) itrSearch.next();
                                String itemName = item.name;
                                final double itemWeight = Double.parseDouble(item.weight);
                                final DatabaseReference dfSearch = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
                                Query query = dfSearch.orderByChild("Name").equalTo(itemName);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                            String key = snapshot.getKey();
                                            double ItemQuantity = Double.parseDouble(snapshot.child("TotalQuantity").getValue(String.class));
                                            ItemQuantity = ItemQuantity - itemWeight;
                                            String s = Double.toString(ItemQuantity);

                                            final DatabaseReference updateVege = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
                                            updateVege.child(key).child("TotalQuantity").setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.e("Project", "Update Complete");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.e("Project", "error :" + e);
                                                }
                                            });

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        System.out.println("On Canceled");
                                    }

                                });
                            }


                            FirebaseDatabase.getInstance().getReference().child("Cart").removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Log.e("Project", "cart delete Complete");
                                }
                            });

                            Toast.makeText(getContext(), "Order Confirmed...", Toast.LENGTH_SHORT).show();

                            FragmentHomeUser homeUser = new FragmentHomeUser();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerHomeUser, homeUser);
                            fragmentTransaction.commit();


                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.setView(dialogLayout);
                    builder.show();

                }

            }
        });


        final String Uid = firebaseAuth.getCurrentUser().getUid();
        final DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("Cart").child(firebaseAuth.getCurrentUser().getUid());
        dff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Cart").child(Uid);

                options = new FirebaseRecyclerOptions.Builder<HomeModel>().setQuery(df, new SnapshotParser<HomeModel>() {

                    @NonNull
                    @Override
                    public HomeModel parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new HomeModel(

                                snapshot.child("CartName").getValue(String.class),
                                snapshot.child("CartPrice").getValue(String.class),
                                snapshot.child("CartQuantity").getValue(String.class),
                                snapshot.child("CardID").getValue(String.class)
                        );

                    }
                }).build();
                adapter = new FirebaseRecyclerAdapter<HomeModel, Holder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull final Holder holder, int position, @NonNull final HomeModel model) {

                        holder.setTxtTitleCart(model.getName());
                        holder.setTxtRateCart(model.getPrice());
                        holder.setTxtWeightCart(model.getQuantity());

                        NoOfItems = String.valueOf(adapter.getItemCount());
                        if (adapter.getItemCount() == 0) {
                            materialButton.setVisibility(View.GONE);
                        } else {
                            materialButton.setVisibility(View.VISIBLE);
                        }
                        cartNoItems.setText(NoOfItems);


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(model.getID());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                value = snapshot.child("TotalQuantity").getValue(String.class);
                                Weight = snapshot.child("TotalWeight").getValue(String.class);

                                holder.setTxtFromEndQuantity(value, Weight);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        holder.imageViewMinusCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                TotalPriceCart.setText(String.valueOf(0.0));
                                holder.imageViewCheck.setVisibility(View.VISIBLE);
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(model.getID());
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        value = snapshot.child("TotalQuantity").getValue(String.class);
                                        val = Double.parseDouble(value);
                                        double i = Double.parseDouble(holder.textViewUserQuantityCard.getText().toString());
                                        if (i < val) {
                                            holder.imageViewPlusCart.setVisibility(View.VISIBLE);
                                        } else {
                                            Toast.makeText(getContext(), "out of stock", Toast.LENGTH_SHORT).show();
                                            holder.imageViewPlusCart.setVisibility(View.INVISIBLE);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                double i = Double.parseDouble(holder.textViewUserQuantityCard.getText().toString());

                                i = i - 0.25;
                                if (i <= 0.25) {

                                    holder.imageViewMinusCart.setVisibility(View.INVISIBLE);

                                } else {

                                    holder.imageViewMinusCart.setVisibility(View.VISIBLE);
                                }

                                holder.setTxtUserQuantCart(String.valueOf(i));

                                if (model.getQuantity().equals("kilo")) {

                                    int p = Integer.parseInt(model.getPrice());
                                    double tot = i * p;
                                    //Log.e(TAG, "inside kilo=======" + tot);
                                    holder.setTxtTotalRate(String.valueOf(tot));

                                } else if (model.getQuantity().equals("gram")) {

                                    int p = Integer.parseInt(model.getPrice());
                                    double tot = i * p;
                                    //Log.e(TAG, "inside kilo=======" + tot);
                                    holder.setTxtTotalRate(String.valueOf(tot));
                                }
                            }
                        });

                        holder.imageViewPlusCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TotalPriceCart.setText(String.valueOf(0.0));

                                holder.imageViewCheck.setVisibility(View.VISIBLE);
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(model.getID());
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        value = snapshot.child("TotalQuantity").getValue(String.class);
                                        val = Double.parseDouble(value);
                                        double i = Double.parseDouble(holder.textViewUserQuantityCard.getText().toString());
                                        if (i < val) {

                                        } else {
                                            Toast.makeText(getContext(), "out of stock", Toast.LENGTH_SHORT).show();
                                            holder.imageViewPlusCart.setVisibility(View.INVISIBLE);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                double i = Double.parseDouble(holder.textViewUserQuantityCard.getText().toString());
                                i = i + 0.25;

                                if (i >= 0.25) {
                                    holder.imageViewMinusCart.setVisibility(View.VISIBLE);
                                } else {

                                    holder.imageViewMinusCart.setVisibility(View.INVISIBLE);
                                }

                                holder.setTxtUserQuantCart(String.valueOf(i));

                                if (model.getQuantity().equals("kilo")) {

                                    int p = Integer.parseInt(model.getPrice());
                                    double tot = i * p;
                                    holder.setTxtTotalRate(String.valueOf(tot));

                                } else if (model.getQuantity().equals("gram")) {

                                    int p = Integer.parseInt(model.getPrice());
                                    double tot = i * p;
                                    holder.setTxtTotalRate(String.valueOf(tot));
                                }

                            }
                        });

                        holder.imageViewCheck.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                double initTotal = Double.parseDouble(TotalPriceCart.getText().toString());
                                double initCart = Double.parseDouble(holder.cart_totalRate.getText().toString());

                                initTotal = initTotal + initCart;
                                TotalPriceCart.setText(String.valueOf(initTotal));
                                holder.imageViewCheck.setVisibility(View.GONE);
                                cartTotalCharge.setText(TotalPriceCart.getText().toString());

                                String Quantity = holder.textViewUserQuantityCard.getText().toString();
                                String rate = holder.cart_totalRate.getText().toString();

                                list.add(new CartItems(model.getName(), Quantity, rate));

                            }
                        });

                        final DatabaseReference Delete = FirebaseDatabase.getInstance().getReference();
                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                builderDelete = new AlertDialog.Builder(getContext());
                                builderDelete.setMessage("Do You Want To Delete Item ?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                TotalPriceCart.setText(String.valueOf(0.0));

                                                Query RoomQuery = Delete.child("Cart").child(firebaseAuth.getCurrentUser().getUid()).orderByChild("CartName").equalTo(model.getName());
                                                RoomQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                            appleSnapshot.getRef().removeValue();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        System.out.println("On Canceled");
                                                    }

                                                });

                                                Toast.makeText(getContext(), "Delete Content Successfully", Toast.LENGTH_LONG).show();

                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert = builderDelete.create();
                                alert.setTitle("Content Delete Alert");
                                alert.show();

                                return false;
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card, parent, false);

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


        threadAddress = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        key = firebaseAuth.getCurrentUser().getUid();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Address");
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
                                    Snackbar snackBar = Snackbar.make(requireView(), "Filled Address Section First..", Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
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

                                } else if (!(mobile == null && address == null)) {

                                    materialButton.setVisibility(View.VISIBLE);

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


    public static String extractInt(String str) {

        str = str.replaceAll("[^\\d]", " ");
        str = str.trim();
        str = str.replaceAll(" +", " ");

        if (str.equals(""))
            return "-1";

        return str;
    }

    public String generateRandomString(int length) {

        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }


}