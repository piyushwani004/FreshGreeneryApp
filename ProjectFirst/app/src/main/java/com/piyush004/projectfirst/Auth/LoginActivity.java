package com.piyush004.projectfirst.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.Dashboard.CustomerDashboard;
import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.MainActivity;
import com.piyush004.projectfirst.R;

public class LoginActivity extends AppCompatActivity {

    private Button button;
    private EditText emailText, passwordText, nameText;
    private ImageView imageView;
    private String email, pass, type, name;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String mess = "Mess-Owner";
    private String cust = "Customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        button = findViewById(R.id.LoginBtn);
        emailText = findViewById(R.id.emailTextField);
        passwordText = findViewById(R.id.passwordTextField);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        nameText = findViewById(R.id.editTextPersonName);

    }

    public void onClickSignIn(View view) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("RegisterType");

        progressBar.setVisibility(View.VISIBLE);

        email = emailText.getText().toString();
        pass = passwordText.getText().toString();
        name = nameText.getText().toString();

        if (email.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emailText.setError("Please Enter Email");
            emailText.requestFocus();
        } else if (pass.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            passwordText.setError("Please Enter Password");
            passwordText.requestFocus();
        } else if (!(email.isEmpty() && pass.isEmpty())) {
            System.out.println(email + pass);
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        type = dataSnapshot.child(nameText.getText().toString()).child("type").getValue(String.class);

                                        System.out.println("Firebase : " + type);

                                        if (type.equals(mess)) {
                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("login_name_mess_Dashboard", name);
                                            bundle.putString("EmailID",email);
                                            Intent intent = new Intent(LoginActivity.this, OwnerDashboard.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else if (type.equals(cust)) {
                                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(LoginActivity.this, CustomerDashboard.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Please try again later", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnClickSignUpLink(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickBackBtn(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void ShowHidePass(View view) {
        if (view.getId() == R.id.imageView3) {
            if (passwordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Password
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

}