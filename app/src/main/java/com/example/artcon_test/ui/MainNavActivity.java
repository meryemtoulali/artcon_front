package com.example.artcon_test.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.artcon_test.R;
import com.example.artcon_test.databinding.ActivityMainNavBinding;
import com.example.artcon_test.ui.portfolioPost.PortfolioPostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.artcon_test.ui.profile.OnPostClickListener;

public class MainNavActivity extends AppCompatActivity implements OnPostClickListener {
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

        // custom back call
        final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d(TAG, "Fragment back pressed invoked");

                // Get the FragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Check if there are fragments in the back stack
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();

                } else {
                    setEnabled(false);
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onPortfolioPostClick() {
        // used on the profile screen
        // replace the entire ProfileFragment with the PortfolioPostFragment
        PortfolioPostFragment portfolioPostFragment = new PortfolioPostFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, portfolioPostFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
