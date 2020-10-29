package com.piyush004.projectfirst.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.piyush004.projectfirst.Auth.LoginActivity;
import com.piyush004.projectfirst.R;
import com.piyush004.projectfirst.customer.all_mess.CustomerAllMessActivity;
import com.piyush004.projectfirst.customer.home.CustomerHomeFragment;
import com.piyush004.projectfirst.customer.profile.CustomerProfileFragment;
import com.piyush004.projectfirst.customer.search_mess.SearchMessLocation;

public class CustomerDashboard extends AppCompatActivity {

    private TextView textViewName, textViewEmail;
    private String login_name, login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            login_name = bundle.getString("login_name_mess_Dashboard");
            login_email = bundle.getString("EmailID");
        }

        final DrawerLayout drawerLayout = findViewById(R.id.drawercustomer);
        findViewById(R.id.menuIconIdcustomer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navmenucustomer);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);
        textViewName = (TextView) header.findViewById(R.id.DashboardHeaderName);
        textViewEmail = (TextView) header.findViewById(R.id.DashboardHeaderEmail);
        textViewName.setText(login_name);
        textViewEmail.setText(login_email);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_c,
                    new CustomerHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home_c:
                        Toast.makeText(getApplicationContext(), "Home Panel is Open", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_c,
                                new CustomerHomeFragment()).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_profile_c:
                        Toast.makeText(getApplicationContext(), "Profile Panel is Open", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_c,
                                new CustomerProfileFragment()).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_all_mess_c:
                        Toast.makeText(getApplicationContext(), "Mess Panel is Open", Toast.LENGTH_LONG).show();
                        Intent intentmenu = new Intent(CustomerDashboard.this, CustomerAllMessActivity.class);
                        startActivity(intentmenu);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_messLocation_c:
                        Toast.makeText(getApplicationContext(), "Mess Location is Open", Toast.LENGTH_LONG).show();
                        Intent intentLocation = new Intent(CustomerDashboard.this, SearchMessLocation.class);
                        startActivity(intentLocation);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_setting_c:
                        Toast.makeText(getApplicationContext(), "Setting Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_share_c:
                        Toast.makeText(getApplicationContext(), "Share Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_help_c:
                        Toast.makeText(getApplicationContext(), "Help Panel is Open", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_logout_c:
                        Intent intent = new Intent(CustomerDashboard.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

    }
}