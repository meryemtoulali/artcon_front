package com.example.artcon_test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SignUpFirstFragment extends Fragment {
    private EditText usernameEditText, passwordEditText, confirmpwdEditText, emailEditText;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_first, container, false);

        usernameEditText = view.findViewById(R.id.editTextUsernameSignUp);
        passwordEditText = view.findViewById(R.id.editTextPasswordSignUp);
        confirmpwdEditText = view.findViewById(R.id.editTextConfirmPwdSignUp);
        emailEditText = view.findViewById(R.id.editTextEmailSignUp);

        Button continueButton = view.findViewById(R.id.buttonContinueSignUp);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input (add your validation logic)
                if (isValidInput()) {
                    // Call the method in the hosting activity to navigate to the second step
                    ((SignupActivity) requireActivity()).navigateToSecondStep();
                } else {
                    // Show an error message or handle invalid input
                    Toast.makeText(requireContext(), "Please enter valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private boolean isValidInput() {
        String password = passwordEditText.getText().toString().trim();
        String confirmpwd = confirmpwdEditText.getText().toString().trim();
        Log.d("Debug", "Password: " + password);
        Log.d("Debug", "Confirm Password: " + confirmpwd);
        Log.d("Debug", "isPasswordValid: " + password.equals(confirmpwd));

        return !TextUtils.isEmpty(usernameEditText.getText())
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirmpwd)
                && Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText()).matches()
                && password.equals(confirmpwd);
    }

    public String getUsername() {
        if (usernameEditText != null) {
            return usernameEditText.getText().toString();
        } else {
            return null; // Handle the case when the usernameEditText is not initialized
        }
    }

    public String getPassword() {
        if (passwordEditText != null) {
            return passwordEditText.getText().toString();
        } else {
            return null; // Handle the case when the usernameEditText is not initialized
        }
    }
    public String getEmail() {
        if (emailEditText != null) {
            return emailEditText.getText().toString();
        } else {
            return null; // Handle the case when the usernameEditText is not initialized
        }
    }

}
