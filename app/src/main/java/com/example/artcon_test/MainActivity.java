package com.example.artcon_test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            PostFragment postFragment = new PostFragment();
            //PortfolioPostFragment portfolioPostFragment = new PortfolioPostFragment();


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, postFragment).commit();
            //getSupportFragmentManager().beginTransaction()
                    //.add(R.id.fragment_container, portfolioPostFragment).commit();
        }
    }
}
