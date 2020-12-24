package com.piyush004.freshgreenery.Admin.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.piyush004.freshgreenery.Admin.AdminActivity;
import com.piyush004.freshgreenery.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

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
    private Uri uri;
    private CircleImageView circleImageView;
    private EditText editTextPrice, editTextName;
    private MaterialButton materialButtonAdd;
    private View view, viewKey;
    private Spinner spinner;
    private String Name, Price, Quanty;
    private String[] Quantity = {"/kg", "Quintal", "others"};
    private StorageReference storageReference;
    private FirebaseStorage storage;

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
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextName = view.findViewById(R.id.editTextName);
        spinner = view.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);


        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Quantity);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.addCategory(Intent.CATEGORY_OPENABLE);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        final DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
        materialButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = editTextName.getText().toString();
                Price = editTextPrice.getText().toString();

                if (Name.isEmpty()) {
                    editTextName.setError("Please Enter Name");
                    editTextName.requestFocus();
                } else if (Price.isEmpty()) {
                    editTextPrice.setError("Please Enter Price");
                    editTextPrice.requestFocus();
                } else if (!(Name.isEmpty() && Price.isEmpty())) {

                    String key = df.push().getKey();
                    df.child(key).child("ID").setValue(key);
                    df.child(key).child("Name").setValue(Name);
                    df.child(key).child("Price").setValue(Price);
                    df.child(key).child("Quantity").setValue(Quanty);
                    uploadImage();
                    Toast.makeText(getContext(), "Data Save", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), AdminActivity.class));
                }
            }
        });

        viewKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
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
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("Upload" + System.currentTimeMillis() + ".PDF");
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imguri = uri.toString();
                                    DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("VegetableEntry");
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Toast.makeText(getContext(), Quantity[position], Toast.LENGTH_LONG).show();
        Quanty = Quantity[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}