package com.piyush004.freshgreenery.Auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.freshgreenery.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button buttonSignUp;
    private EditText editTextName, editTextEmail, editTextPassword, editTextVfPassword;
    private String name, email, pass, vfpass;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private View viewSignout;
    private ProgressBar progressBar;

    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignUp = view.findViewById(R.id.button_sign_up);
        editTextName = view.findViewById(R.id.Address_address);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextVfPassword = view.findViewById(R.id.editTextVerifyPassword);
        viewSignout = view.findViewById(R.id.viewFragSignUp);
        progressBar = view.findViewById(R.id.progressBar);


        viewSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.hideKeyboard(getActivity());
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                pass = editTextPassword.getText().toString();
                vfpass = editTextVfPassword.getText().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Please Enter Name");
                    editTextName.requestFocus();
                } else if (email.isEmpty()) {
                    editTextEmail.setError("Please Enter Email");
                    editTextEmail.requestFocus();
                } else if (pass.isEmpty()) {
                    editTextPassword.setError("Please Enter Password");
                    editTextPassword.requestFocus();
                } else if (vfpass.isEmpty()) {
                    editTextVfPassword.setError("Please Enter Password");
                    editTextVfPassword.requestFocus();
                } else if (!(isValidEmail(email))) {
                    editTextEmail.setError("Please Enter Valid Email..!!!");
                } else if (!(isValidPassword(pass))) {
                    Toast.makeText(getContext(), "Passwords must contain at least six characters, including uppercase, lowercase letters and numbers", Toast.LENGTH_LONG).show();
                    editTextPassword.setError("Passwords must contain at least six characters, including uppercase, lowercase letters and numbers");
                } else if (!(pass.equals(vfpass))) {
                    Toast.makeText(getContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                    editTextVfPassword.setError("Password Does Not Match");
                } else if (!(email.isEmpty() && pass.isEmpty())) {
                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (!task.isSuccessful()) {

                                        Toast.makeText(getContext(),
                                                "Registration Error " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {

                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("AppUsers");
                                        String key = databaseReference.push().getKey();
                                        databaseReference.child(key).child("ID").setValue(key);
                                        databaseReference.child(key).child("Name").setValue(name);
                                        databaseReference.child(key).child("Email").setValue(email);
                                        databaseReference.child(key).child("Password").setValue(pass);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                                        editTextName.setText(" ");
                                        editTextEmail.setText(" ");
                                        editTextPassword.setText(" ");
                                        editTextVfPassword.setText(" ");
                                        startActivity(new Intent(getContext(), AuthActivity.class));
                                    }
                                }
                            });

                }


            }
        });

        return view;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

}