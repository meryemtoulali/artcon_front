package com.example.artcon_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.artcon_test.fragment.setupAccounttype;
import com.example.artcon_test.fragment.setupProfilepicture;

public class ProfileSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.setup_profile_fragment, new setupProfilepicture(), null)
                    .commit();
        }


//        loadFragment(new setupProfilepicture());
//        TextView next_button = findViewById(R.id.skip_button);
        Button next_button = findViewById(R.id.next_button);
        next_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFragment(new setupAccounttype());
                    }
                }
        );

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
                .replace(R.id.setup_profile_fragment, fragment)
                .commit();
    }
    // Switch fragment;


}