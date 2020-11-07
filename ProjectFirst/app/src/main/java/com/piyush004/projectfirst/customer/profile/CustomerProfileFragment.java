package com.piyush004.projectfirst.customer.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.piyush004.projectfirst.Dashboard.CustomerDashboard;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText editTextName, editTextAddress, editTextMobile, editTextEmail, editTextCity, editTextCollageName;
    private TextView textViewHeader, textViewName, textViewAddress, textViewMobile, textViewEmail, textViewCity, textViewCollageName;
    private String cust_name, cust_address, cust_mobile, cust_city, cust_email, cust_collage_name;
    Button buttonSave;
    private ImageView imageView;
    private Uri uri;
    private DatabaseReference databaseReference;
    private String login_name;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static int SELECT_PHOTO = 1;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }

    public static CustomerProfileFragment newInstance(String param1, String param2) {
        CustomerProfileFragment fragment = new CustomerProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_profile, container, false);

        login_name = LoginKey.loginKey;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);

        editTextName = view.findViewById(R.id.edit_text_name_c);
        editTextAddress = view.findViewById(R.id.edit_text_address_c);
        editTextMobile = view.findViewById(R.id.edit_text_mobile_c);
        editTextEmail = view.findViewById(R.id.edit_text_email_c);
        editTextCity = view.findViewById(R.id.edit_text_city_c);
        editTextCollageName = view.findViewById(R.id.edit_text_collage_name_c);
        buttonSave = view.findViewById(R.id.button_save_c);
        imageView = view.findViewById(R.id.customerPhoto_c);


        DatabaseReference dff = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        dff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uri = snapshot.child("ImageURl").getValue(String.class);
                String custName = snapshot.child("CustName").getValue(String.class);
                String custAddress = snapshot.child("CustAddress").getValue(String.class);
                String custMob = snapshot.child("CustMobile").getValue(String.class);
                String custEmail = snapshot.child("CustEmail").getValue(String.class);
                String custCity = snapshot.child("CustCity").getValue(String.class);
                String custClgName = snapshot.child("CustCollageName").getValue(String.class);
                System.out.println(uri);

                if (uri == null) {
                    Picasso.get().load(R.drawable.profile).into(imageView);
                } else {
                    Picasso.get().load(uri).into(imageView);
                }
                if (custName == null) {
                    editTextName.setText(" ");
                } else {
                    editTextName.setText(custName);
                }
                if (custAddress == null) {
                    editTextAddress.setText("");
                } else {
                    editTextAddress.setText(custAddress);
                }
                if (custMob == null) {
                    editTextMobile.setText("");
                } else {
                    editTextMobile.setText(custMob);
                }
                if (custEmail == null) {
                    editTextEmail.setText("");
                } else {
                    editTextEmail.setText(custEmail);
                }
                if (custCity == null) {
                    editTextCity.setText("");
                } else {
                    editTextCity.setText(custCity);
                }
                if (custClgName == null) {
                    editTextCollageName.setText("");
                } else {
                    editTextCollageName.setText(custClgName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        progressDialog.dismiss();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Select Image", Toast.LENGTH_LONG).show();
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, SELECT_PHOTO);
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cust_name = editTextName.getText().toString();
                cust_address = editTextAddress.getText().toString();
                cust_mobile = editTextMobile.getText().toString();
                cust_email = editTextEmail.getText().toString();
                cust_city = editTextCity.getText().toString();
                cust_collage_name = editTextCollageName.getText().toString();

                if (cust_name.isEmpty()) {
                    editTextName.setError("Please Enter Name");
                    editTextName.requestFocus();
                } else if (cust_address.isEmpty()) {
                    editTextAddress.setError("Please Enter Address");
                    editTextAddress.requestFocus();
                } else if (cust_mobile.isEmpty()) {
                    editTextMobile.setError("Please Enter Mobile");
                    editTextMobile.requestFocus();
                } else if (cust_email.isEmpty()) {
                    editTextEmail.setError("Please Re-Enter Email-ID");
                    editTextEmail.requestFocus();
                } else if (cust_city.isEmpty()) {
                    editTextCity.setError("Please Enter City");
                    editTextCity.requestFocus();
                } else if (cust_collage_name.isEmpty()) {
                    editTextCollageName.setError("Please Enter Days");
                    editTextCollageName.requestFocus();
                } else if (!(cust_name.isEmpty() && cust_address.isEmpty() && cust_mobile.isEmpty() && cust_email.isEmpty() && cust_city.isEmpty() && cust_collage_name.isEmpty())) {

                    databaseReference.child("CustName").setValue(cust_name);
                    databaseReference.child("CustAddress").setValue(cust_address);
                    databaseReference.child("CustMobile").setValue(cust_mobile);
                    databaseReference.child("CustEmail").setValue(cust_email);
                    databaseReference.child("CustCity").setValue(cust_city);
                    databaseReference.child("CustCollageName").setValue(cust_collage_name);
                    uploadImage();
                    Toast.makeText(getContext(), "Data Added", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), CustomerDashboard.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    private void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("customer/").child(login_name);
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imguri = uri.toString();
                                    DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Customer").child(login_name);
                                    df.child("ImageURl").setValue(imguri);

                                }
                            });

                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
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

}