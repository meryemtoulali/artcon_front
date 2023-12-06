package com.example.artcon_test;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoggedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        // Add any additional logic or UI elements specific to LoggedInActivity

        // Example: Update the text of a TextView
        TextView textViewExample = findViewById(R.id.textViewExample);
        textViewExample.setText("Welcome to the Logged In Activity!");
    }
}
