package com.piyush004.freshgreenery.Admin.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.piyush004.freshgreenery.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class AddFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static int SELECT_PHOTO = 1;
    private Uri uri, UpUri;
    private CircleImageView circleImageView;
    private EditText editTextPrice, editTextName, editTextTotalQuantity;
    private MaterialButton materialButtonAdd, materialButtonUpdate;
    private View view, viewKey;
    private Spinner spinner, spinnerTot;
    private SimpleDateFormat simpleDateFormat;
    private String date, key;
    private String Name, Rate, RateQuantity, ImgURL, TotalQuantity, TotalWeight;
    private String UpName, UpPrice, UpTotalQuantity, UpTotalWeight, UpImgURL;
    private String[] Quantity = {"kilo", "gram", "piece", "dozen"};
    private String[] TotalQuantWeight = {"kilo", "gram", "piece", "dozen"};
    private SwipeRefreshLayout swipeRefreshLayout;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public ArrayList<String> arrayList = new ArrayList<>();
    private MaterialBetterSpinner materialBetterSpinner;
    public ArrayAdapter arrayAdapter;

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

        viewKey = view.findViewById(R.id.viewFragAdd);
        circleImageView = view.findViewById(R.id.circular_add_images);
        materialButtonAdd = view.findViewById(R.id.buttonAdd);
        materialButtonUpdate = view.findViewById(R.id.buttonUpdate);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextName = view.findViewById(R.id.Address_address);
        editTextTotalQuantity = view.findViewById(R.id.editTextTotalQuantity);
        swipeRefreshLayout = view.findViewById(R.id.refreshFragAdd);
        spinner = view.findViewById(R.id.spinner);
        spinnerTot = view.findViewById(R.id.spinnerTot);
        spinner.setOnItemSelectedListener(this);

        materialButtonUpdate.setClickable(false);

        materialBetterSpinner = (MaterialBetterSpinner) view.findViewById(R.id.material_spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);
        //materialBetterSpinner.setAdapter(adapter);
        showDataSpinner(getContext());
        materialBetterSpinner.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                final String Search = materialBetterSpinner.getText().toString();
                if (!(Search.equals("Nothing"))) {
                    materialButtonUpdate.setClickable(true);
                    databaseReference.child("VegetableEntry").orderByChild("Name").equalTo(Search).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                key = child.getKey();

                                databaseReference.child("VegetableEntry").child(key).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        editTextName.setText(snapshot.child("Name").getValue(String.class));
                                        editTextPrice.setText(snapshot.child("Rate").getValue(String.class));
                                        Picasso.get().load(snapshot.child("ImageURl").getValue(String.class))
                                                .resize(500, 500)
                                                .centerCrop()
                                                .rotate(0)
                                                .into(circleImageView);
                                        UpUri = Uri.parse(snapshot.child("ImageURl").getValue(String.class));
                                        editTextTotalQuantity.setText(snapshot.child("TotalQuantity").getValue(String.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    materialButtonUpdate.setClickable(false);
                    editTextName.setText("");
                    editTextPrice.setText("");
                    editTextTotalQuantity.setText("");
                    Glide.with(getContext()).load(R.drawable.uploadvege).into(circleImageView);
                }
            }
        });

        materialButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(key);

                UpName = editTextName.getText().toString();
                UpPrice = editTextPrice.getText().toString();
                UpTotalQuantity = editTextTotalQuantity.getText().toString();

                if (UpName.isEmpty()) {
                    editTextName.setError("Please Enter Name");
                    editTextName.requestFocus();
                } else if (UpPrice.isEmpty()) {
                    editTextPrice.setError("Please Enter Price");
                    editTextPrice.requestFocus();
                } else if (!(UpName.isEmpty() && UpPrice.isEmpty() && UpTotalQuantity.isEmpty())) {

                    final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(key);
                    df.child("Name").setValue(UpName);
                    df.child("Rate").setValue(UpPrice);
                    df.child("RateWeight").setValue(RateQuantity);
                    df.child("TotalQuantity").setValue(UpTotalQuantity);
                    df.child("TotalWeight").setValue(TotalWeight);

                    uploadUpdatedImage(key, getContext());

                    editTextName.setText("");
                    editTextPrice.setText("");
                    editTextTotalQuantity.setText("");
                    Glide.with(getContext()).load(R.drawable.carrots).into(circleImageView);
                    Toast.makeText(getContext(), "Data Updated...", Toast.LENGTH_SHORT).show();

                }

            }
        });
        Date data = new Date();
        simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        date = simpleDateFormat.format(data);


        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Quantity);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);

        ArrayAdapter Taa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, TotalQuantWeight);
        Taa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTot.setAdapter(Taa);
        spinnerTot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(), "Tot" + TotalQuantWeight[position], Toast.LENGTH_SHORT).show();
                TotalWeight = TotalQuantWeight[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });


        materialButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = editTextName.getText().toString();
                Rate = editTextPrice.getText().toString();
                TotalQuantity = editTextTotalQuantity.getText().toString();

                if (Name.isEmpty()) {
                    editTextName.setError("Please Enter Name");
                    editTextName.requestFocus();
                } else if (Rate.isEmpty()) {
                    editTextPrice.setError("Please Enter Price");
                    editTextPrice.requestFocus();
                } else if (TotalQuantity.isEmpty()) {
                    editTextTotalQuantity.setError("Please Enter Quantity");
                    editTextTotalQuantity.requestFocus();
                } else if (!(Name.isEmpty() && Rate.isEmpty())) {

                    uploadImage();

                }
            }
        });

        viewKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                editTextName.setText("");
                editTextPrice.setText("");
                editTextTotalQuantity.setText("");
                Glide.with(getContext()).load(R.drawable.carrots).into(circleImageView);
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter.clear();
                materialBetterSpinner.setSelection(0);
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
                                Toast.makeText(getContext(), "Add image from Gallery", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
    }

    private void uploadImage() {
        storage = getInstance();
        storageReference = storage.getReference();
        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child(Name + System.currentTimeMillis() + ".img");
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ImgURL = uri.toString();
                                    String key = df.push().getKey();
                                    df.child(key).child("ID").setValue(key);
                                    df.child(key).child("Name").setValue(Name);
                                    df.child(key).child("Rate").setValue(Rate);
                                    df.child(key).child("RateWeight").setValue(RateQuantity);
                                    df.child(key).child("ImageURl").setValue(ImgURL);
                                    df.child(key).child("Date").setValue(date);
                                    df.child(key).child("TotalQuantity").setValue(TotalQuantity);
                                    df.child(key).child("TotalWeight").setValue(TotalWeight);
                                }
                            });

                            Toast.makeText(getContext(), "Save Data", Toast.LENGTH_SHORT).show();
                            editTextName.setText("");
                            editTextPrice.setText("");
                            editTextTotalQuantity.setText("");
                            Glide.with(getContext()).load(R.drawable.carrots).into(circleImageView);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    public void uploadUpdatedImage(String Key, final Context context) {
        storage = getInstance();
        storageReference = storage.getReference();
        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry").child(Key);
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Updating...");
            progressDialog.show();
            final StorageReference ref = storageReference.child(UpName + System.currentTimeMillis() + ".img");
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ImgURL = uri.toString();
                                    df.child("ImageURl").setValue(ImgURL);

                                }
                            });

                            Toast.makeText(getContext(), "Save Data", Toast.LENGTH_SHORT).show();
                            editTextName.setText("");
                            editTextPrice.setText("");
                            editTextTotalQuantity.setText("");
                            Glide.with(context).load(R.drawable.carrots).into(circleImageView);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {

            UpImgURL = UpUri.toString();
            df.child("ImageURl").setValue(UpImgURL);

            Toast.makeText(getContext(), "Image Updated...", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextPrice.setText("");
            editTextTotalQuantity.setText("");
            Glide.with(context).load(R.drawable.carrots).into(circleImageView);
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(getContext(), Quantity[position], Toast.LENGTH_LONG).show();
        RateQuantity = Quantity[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showDataSpinner(final Context context) {
        databaseReference.child("VegetableEntry").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    arrayList.add(item.child("Name").getValue(String.class));
                }
                arrayList.add("Nothing");
                arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayList);
                materialBetterSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}