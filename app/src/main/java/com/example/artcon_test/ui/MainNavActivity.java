package com.example.artcon_test.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.ActivityMainNavBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainNavActivity extends AppCompatActivity {
String TAG ="hatsunemiku";
    private ActivityMainNavBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setItemIconTintList(null);
        navView.setAnimation(null);
        navView.setItemRippleColor(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_job, R.id.navigation_profile)
                .build();

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //====================================================================
        //                      bottom menu
        //=====================================================================
        // Initialize the PopupMenu
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.navigation_add), Gravity.CENTER_HORIZONTAL);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add, popupMenu.getMenu());

        // Set the item click listener for the "Add" button
      /*  findViewById(R.id.navigation_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });*/
        findViewById(R.id.navigation_add).setOnClickListener(view -> popupMenu.show());

        // Set the item click listener for the sub-items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Handle the click events for the sub-items
                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_add_post) {
                    //Toast.makeText(getApplicationContext(), "Add post", Toast.LENGTH_SHORT).show();
                    // Add your logic here
                    Log.d("hi", "add_post if");
                    navController.navigate(R.id.action_global_navigation_add_post);

                   // navigateToFragment(R.id.action_navigation_home_to_navigation_add_post);
                    Log.d("hi", "after navigation");
                    return true;
                } else if (itemId == R.id.menu_add_portfolio_post) {
                    Toast.makeText(getApplicationContext(), "Add portfolio post", Toast.LENGTH_SHORT).show();
                    // Add your logic here
                    return true;
                } else if (itemId == R.id.menu_add_job_post) {
                    Toast.makeText(getApplicationContext(), "Add job post", Toast.LENGTH_SHORT).show();
                    // Add your logic here
                    return true;
                }
                return false;
            }
        });

    } //onCreate end

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    //=====================================================

    private void navigateToFragment(int fragmentId) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(fragmentId);
    }
}
