package com.example.artcon_test;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpSecondFragment extends Fragment {
    private EditText firstNameEditText, lastNameEditText, genderEditText,phoneEditText, birthdayEditText, locationEditText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_second, container, false);

        firstNameEditText = view.findViewById(R.id.editTextFirstNameSignUp);
        lastNameEditText = view.findViewById(R.id.editTextLastNameSignUp);
        genderEditText = view.findViewById(R.id.editTextGenderSignUp);
        phoneEditText = view.findViewById(R.id.editTextPhoneSignUp);
        birthdayEditText = view.findViewById(R.id.editTextBirthdaySignUp);
        locationEditText = view.findViewById(R.id.editTextLocationSignUp);

        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input (add your validation logic)
                if (isValidInput()) {
                    // Call the method in the hosting activity to perform sign-up
                    ((SignupActivity) requireActivity()).performSignUp();
                } else {
                    // Show an error message or handle invalid input
                    Toast.makeText(requireContext(), "Please enter valid information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private boolean isValidInput() {
        return !TextUtils.isEmpty(firstNameEditText.getText())
                && !TextUtils.isEmpty(lastNameEditText.getText())
                && !TextUtils.isEmpty(birthdayEditText.getText())
                && !TextUtils.isEmpty(genderEditText.getText())
                && !TextUtils.isEmpty(locationEditText.getText())
                && !TextUtils.isEmpty(phoneEditText.getText());
    }
    public String getFirstName() {
        if (firstNameEditText != null) {
            return firstNameEditText.getText().toString();
        } else {
            return null; // Handle the case when the firstNameEditText is not initialized
        }
    }
    public String getLastName() {
        if (lastNameEditText != null) {
            return lastNameEditText.getText().toString();
        } else {
            return null; // Handle the case when the firstNameEditText is not initialized
        }
    }
    public Date getBirthday() {
        if (birthdayEditText != null) {
            String dateString = birthdayEditText.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy"); // Adjust the pattern based on your date format

            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the parse exception appropriately
            }
        }
        return null; // Handle the case when the birthdayEditText is not initialized or parsing fails
    }

    // Method to get the location from the EditText field
    public String getLocation() {
        if (locationEditText != null) {
            return locationEditText.getText().toString();
        } else {
            return null; // Handle the case when the locationEditText is not initialized
        }
    }

    // Method to get the gender from the EditText field
    public String getGender() {
        if (genderEditText != null) {
            return genderEditText.getText().toString();
        } else {
            return null; // Handle the case when the genderEditText is not initialized
        }
    }

    // Method to get the phone number from the EditText field
    public String getPhone() {
        if (phoneEditText != null) {
            return phoneEditText.getText().toString();
        } else {
            return null; // Handle the case when the phoneEditText is not initialized
        }
    }
}
