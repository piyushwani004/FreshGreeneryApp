package com.piyush004.projectfirst;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.piyush004.projectfirst.Auth.LoginActivity;
import com.piyush004.projectfirst.Auth.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin, buttonSignUp;
    private Animation topAnimation, bottomAnimation, middleAnimation;
    private ImageView imageView1, imageView2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        buttonLogin = (Button) findViewById(R.id.LoginBtn);
        buttonSignUp = (Button) findViewById(R.id.signUpBtn);
        imageView1 = findViewById(R.id.imageViewTop);
        imageView2 = findViewById(R.id.imageViewMiddel);
        textView = findViewById(R.id.textView);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        imageView1.setAnimation(topAnimation);
        textView.setAnimation(middleAnimation);
        imageView2.setAnimation(middleAnimation);
        buttonSignUp.setAnimation(bottomAnimation);
        buttonLogin.setAnimation(bottomAnimation);

    }

    public void onClickLogin(View view) {
        //LoginActivity.class
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

}