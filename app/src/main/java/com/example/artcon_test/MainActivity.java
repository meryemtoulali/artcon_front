package com.example.artcon_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the Button by its ID
        Button yourButton = findViewById(R.id.login);

        // Set a click listener for the Button
        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the Intent to start the new activity
                Intent intent = new Intent(MainActivity.this, BottomNavbarActivity.class);

                // Start the new activity
                startActivity(intent);
            }
        });
    }

//    private ActivityMainBinding binding;
//    private AppBarConfiguration appBarConfiguration; // Declare appBarConfiguration as a field
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//
//        // Set up navigation controller and AppBarConfiguration
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_job, R.id.navigation_profile)
//                .build();
//
//        // Set up ActionBar and BottomNavigationView with NavController
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//
//        // Set up item click listener for custom behavior
//        navView.setOnItemSelectedListener(item -> {
//
//            // Handle item clicks here
//            int itemId = item.getItemId();
//
//            if (itemId == R.id.navigation_home) {
//                return true;
//            } else if (itemId == R.id.navigation_search) {
//
//                // Handle search click
//                return true;
//            } else if (itemId == R.id.navigation_add) {
//
//                // Handle add click
//                return true;
//            } else if (itemId == R.id.navigation_job) {
//
//                // Handle job click
//                return true;
//            } else if (itemId == R.id.navigation_profile) {
//
//                // Handle profile click
//                return true;
//            }
//            return false;
//        });
//    }
//
//    // Ensure the correct setup for the Up button (Back button) in the ActionBar
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
//    }
}
