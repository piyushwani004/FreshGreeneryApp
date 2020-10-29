package com.piyush004.projectfirst.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piyush004.projectfirst.MainActivity;
import com.piyush004.projectfirst.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText, emailText, passwordText, rePasswordText;
    CheckBox agreement;
    Button register;
    ImageView imageView;
    private String name, email, pass, repass, regType;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        nameText = findViewById(R.id.editTextName);
        emailText = findViewById(R.id.editTextEmail);
        passwordText = findViewById(R.id.editTextPassword);
        rePasswordText = findViewById(R.id.editTextRePassword);
        agreement = findViewById(R.id.checkBoxAgreement);
        register = findViewById(R.id.buttonRegister);
        imageView = findViewById(R.id.imageView2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButtonOwner);


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


    public void onClickRegister(View view) {
        name = nameText.getText().toString();
        email = emailText.getText().toString();
        pass = passwordText.getText().toString();
        repass = rePasswordText.getText().toString();

        String EmailResult = emailSplit(email);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(EmailResult);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton1 = (RadioButton) findViewById(selectedId);
        if (selectedId == -1) {
            Toast.makeText(SignUpActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            regType = radioButton1.getText().toString();
            Toast.makeText(SignUpActivity.this, "Registered As " + regType, Toast.LENGTH_SHORT).show();
        }


        if (name.isEmpty()) {
            nameText.setError("Please Enter Name");
            nameText.requestFocus();
        } else if (email.isEmpty()) {
            emailText.setError("Please Enter Email");
            emailText.requestFocus();
        } else if (pass.isEmpty()) {
            passwordText.setError("Please Enter Password");
            passwordText.requestFocus();
        } else if (repass.isEmpty()) {
            rePasswordText.setError("Please Re-Enter Password");
            rePasswordText.requestFocus();
        } else if (!(isValidEmail(email))) {
            emailText.setError("Please Enter Valid Email..!!!");
        } else if (!(isValidPassword(pass))) {
            passwordText.setError("Please Enter Valid Password..!!!");
        } else if (!(pass.equals(repass))) {
            Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show();
            rePasswordText.setError("Password Not Match");
        } else if (!(agreement.isChecked())) {
            agreement.setError(" Please Select Agreement");
        } else if (email.isEmpty() && pass.isEmpty()) {
            Toast.makeText(this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
        } else if (!(email.isEmpty() && pass.isEmpty())) {
            firebaseAuth.createUserWithEmailAndPassword(email, repass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(SignUpActivity.this.getApplicationContext(),
                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.child("username").setValue(email);
                        databaseReference.child("type").setValue(regType);
                        Toast.makeText(SignUpActivity.this, "Register Completed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    }
                }
            });
        }
    }

    public String emailSplit(String str) {
        String resultStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > 64 && str.charAt(i) <= 122) {
                resultStr = resultStr + str.charAt(i);
            }
        }
        return resultStr;
    }

    public void OnClickLogInLink(View view) {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickBackBtn(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void ShowHidePass(View view) {
        if (view.getId() == R.id.imageView4) {
            if (passwordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Password
                passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    public void ShowHideRePass(View view) {
        if (view.getId() == R.id.imageView5) {
            if (rePasswordText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                //Show Password
                rePasswordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //Hide Password
                rePasswordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}