package com.example.houseit.views;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.houseit.R;
import com.example.houseit.fragments.FamilyFragment;
import com.example.houseit.fragments.HomeFragment;
import com.example.houseit.fragments.ListsFragment;
import com.example.houseit.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

// the Dashboard is a view. it observes information from the dashboardViewmodel
//      sent from firebase(
public class Dashboard extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        // pull up the house the user is in

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        // this line defines the default fragment to display
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new ProfileFragment()).commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();

                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_lists:
                            selectedFragment = new ListsFragment();
                            break;
                        case R.id.nav_family:
                            selectedFragment = new FamilyFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true; // this shows the item is selected
                }
            };
    private void initHouseView(){

    }
}
