package com.example.artcon_test;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.artcon_test.databinding.ActivityBottomNavbarBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavbarActivity extends AppCompatActivity {

    private ActivityBottomNavbarBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setItemIconTintList(null);
        navView.setAnimation(null);
        navView.setItemRippleColor(null);

        navView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_add) {
                // Naviguer vers la nouvelle destination (fragment) lorsque le bouton "Add" est cliqué
                navController.navigate(R.id.navigation_add_view);
                return true;
            } else {
                // Autres gestionnaires de clics
                return false;
            }
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navbar);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_job, R.id.navigation_profile)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
