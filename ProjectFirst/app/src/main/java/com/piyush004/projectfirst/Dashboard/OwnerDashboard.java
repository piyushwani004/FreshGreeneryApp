package com.piyush004.projectfirst.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piyush004.projectfirst.Auth.LoginActivity;
import com.piyush004.projectfirst.LoginKey;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.owner.home.HomeOwnerFragment;
import com.piyush004.projectfirst.owner.manage_customer.ManageCustomerActivity;
import com.piyush004.projectfirst.owner.map.MapsOwnerActivity;
import com.piyush004.projectfirst.owner.messdetails.MessDetailsActivity;
import com.piyush004.projectfirst.owner.messmenu.MessMenuActivity;
import com.piyush004.projectfirst.owner.profile.ProfileOwnerActivity;
import com.squareup.picasso.Picasso;

public class OwnerDashboard extends AppCompatActivity {

    private TextView textViewName, textViewEmail;
    private String login_name, login_email;
    private FirebaseAuth auth;
    private ImageView imageViewHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        login_name = LoginKey.loginKey;

        textViewName = findViewById(R.id.DashboardHeaderName);
        textViewEmail = findViewById(R.id.DashboardHeaderEmail);
        imageViewHead = findViewById(R.id.DashboardHeaderImg);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            login_name = bundle.getString("login_name_mess_Dashboard");
            login_email = bundle.getString("EmailID");
        }

        final DrawerLayout drawerLayout = findViewById(R.id.drawer);
        findViewById(R.id.menuIconId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navmenu);
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);
        textViewName = (TextView) header.findViewById(R.id.DashboardHeaderName);
        textViewEmail = (TextView) header.findViewById(R.id.DashboardHeaderEmail);
        imageViewHead = (ImageView) header.findViewById(R.id.DashboardHeaderImg);
        textViewEmail.setText(login_email);

        DatabaseReference nm = FirebaseDatabase.getInstance().getReference().child("Mess").child(login_name);
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uri = snapshot.child("ImageURl").getValue(String.class);
                String messName = snapshot.child("MessName").getValue(String.class);
                Picasso.get().load(uri).into(imageViewHead);
                textViewName.setText(messName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeOwnerFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.menu_home:
                        Toast.makeText(getApplicationContext(), "Home Panel is Open", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeOwnerFragment()).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_profile:
                        Toast.makeText(getApplicationContext(), "Profile Panel is Open", Toast.LENGTH_SHORT).show();
                        Intent intentProfile = new Intent(OwnerDashboard.this, ProfileOwnerActivity.class);
                        intentProfile.putExtra("LoginNameMessDetails", login_name);
                        intentProfile.putExtra("LoginEmailMessDetails", login_email);
                        startActivity(intentProfile);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_mess_details:
                        Toast.makeText(getApplicationContext(), "mess Details Panel is Open", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OwnerDashboard.this, MessDetailsActivity.class);
                        intent.putExtra("LoginNameMessDetails", login_name);
                        intent.putExtra("LoginEmailMessDetails", login_email);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_messMenu:
                        Toast.makeText(getApplicationContext(), "mess Menu Panel is Open", Toast.LENGTH_SHORT).show();
                        Intent intentmenu = new Intent(OwnerDashboard.this, MessMenuActivity.class);
                        intentmenu.putExtra("LoginNameMessDetails", login_name);
                        intentmenu.putExtra("LoginEmailMessDetails", login_email);
                        startActivity(intentmenu);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_messLocation:
                        Toast.makeText(getApplicationContext(), "mess Location Panel is Open", Toast.LENGTH_SHORT).show();
                        Intent intentloc = new Intent(OwnerDashboard.this, MapsOwnerActivity.class);
                        intentloc.putExtra("LoginNameMessDetails", login_name);
                        startActivity(intentloc);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_manageCustomer:
                        Toast.makeText(getApplicationContext(), "Customers Panel is Open", Toast.LENGTH_SHORT).show();
                        Intent intentCust = new Intent(OwnerDashboard.this, ManageCustomerActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_share:
                        Toast.makeText(getApplicationContext(), "Share Panel is Open", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_help:
                        Toast.makeText(getApplicationContext(), "Help Panel is Open", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_setting:
                        Toast.makeText(getApplicationContext(), "Setting Panel is Open", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout:
                        Toast.makeText(getApplicationContext(), "Logout Successful", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        //auth.signOut();
                        Intent intentlogout = new Intent(OwnerDashboard.this, LoginActivity.class);
                        startActivity(intentlogout);
                        break;
                }

                return true;
            }
        });
    }
}