package com.piyush004.projectfirst.owner.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

public class ProfileOwnerActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_owner);

        Toolbar toolbar = findViewById(R.id.toolbarOwnerProfile);
        setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tabLayoutProfile);
        viewPager = findViewById(R.id.ViewPagerProfile);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new MessDetails(), "Mess Details");
        viewPagerAdapter.addFragment(new MessMenuProfile(), " Mess Menu");
        viewPagerAdapter.addFragment(new LocationProfile(), "Mess Location");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.mess_details);
        tabLayout.getTabAt(1).setIcon(R.drawable.mess_menu);
        tabLayout.getTabAt(2).setIcon(R.drawable.mess_locatiion);
    }

    public void onClickProfileBackBtn(View view) {
        Intent intentProfile = new Intent(ProfileOwnerActivity.this, OwnerDashboard.class);
        startActivity(intentProfile);
    }


}