package com.example.artcon_test.ui.signup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Location;
import com.example.artcon_test.network.LocationService;
import com.example.artcon_test.ui.signup.SignupActivity;
import com.example.artcon_test.viewmodel.LocationViewModel;
import com.example.artcon_test.viewmodel.SignupViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpSecondFragment extends Fragment {
    private EditText firstNameEditText, lastNameEditText,phoneEditText, birthdayEditText;
    SignupViewModel signupViewModel;
    private Spinner genderSpinner;
    private Spinner locationSpinner;
    private ArrayAdapter<CharSequence> genderAdapter;
    private ArrayAdapter<String> locationAdapter;
    private List<Location> locations = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_second, container, false);
        signupViewModel = new ViewModelProvider(requireActivity()).get(SignupViewModel.class);

        firstNameEditText = view.findViewById(R.id.editTextFirstNameSignUp);
        lastNameEditText = view.findViewById(R.id.editTextLastNameSignUp);
        //genderEditText = view.findViewById(R.id.editTextGenderSignUp);
        phoneEditText = view.findViewById(R.id.editTextPhoneSignUp);
        birthdayEditText = view.findViewById(R.id.editTextBirthdaySignUp);
        //locationEditText = view.findViewById(R.id.spinnerLocationSignUp);

        genderSpinner = view.findViewById(R.id.spinnerGenderSignUp);
        genderAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        locationSpinner = view.findViewById(R.id.spinnerLocationSignUp);
        Log.d("location2","i am here 1");
        populateLocationDropdown();

        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(v -> {
            // Validate input (add your validation logic)
            if (isValidInput()) {
                showButtonClickIndicator(signUpButton);
                // Call the method in the hosting activity to perform sign-up
                signupViewModel.setFirstName(getFirstName());
                signupViewModel.setLastName(getLastName());
                signupViewModel.setGender(getGender());
                signupViewModel.setPhonenumber(getPhone());
                signupViewModel.setBirthday(getBirthday());
                signupViewModel.setLocation(getLocation());
                ((SignupActivity) requireActivity()).performSignUp();
            } else {
                // Show an error message or handle invalid input
                Toast.makeText(requireContext(), "Please enter valid information", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showButtonClickIndicator(Button signUpButton) {
        Drawable originalBackground = signUpButton.getBackground();
        signUpButton.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.selected_button));
        new Handler().postDelayed(() -> signUpButton.setBackground(originalBackground), 1000);
    }

    private void populateLocationDropdown() {
        LocationService locationService = LocationViewModel.getLocationService();
        Call<List<Location>> call = locationService.getLocations();
        Log.d("location2","Here i am by the call");
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    Log.d("location2","success");
                    locations = response.body();
                    setupLocationDropdown();
                } else {
                    Log.d("location","location response has failed!");
                }
            }

            private void setupLocationDropdown() {
                List<String> locationNames = new ArrayList<>();
                Log.d("location2","success setup");
                locationNames.add(getString(R.string.select_location_hint)); // Add a hint or default value

                for (Location location : locations) {
                    locationNames.add(location.getName());
                }

                locationAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        locationNames
                );
                locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                locationSpinner.setAdapter(locationAdapter);
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.e("location2", "Huge Error: " + t.getMessage());
            }
        });
    }

    private boolean isValidInput() {
        return !TextUtils.isEmpty(firstNameEditText.getText())
                && !TextUtils.isEmpty(lastNameEditText.getText())
                && !TextUtils.isEmpty(birthdayEditText.getText())
                && !TextUtils.isEmpty(genderSpinner.getSelectedItem().toString())
                && !TextUtils.isEmpty(locationSpinner.getSelectedItem().toString())
                && !TextUtils.isEmpty(phoneEditText.getText());
    }
    public String getFirstName() {
        if (firstNameEditText != null) {
            return firstNameEditText.getText().toString();
        } else {
            return null;
        }
    }
    public String getLastName() {
        if (lastNameEditText != null) {
            return lastNameEditText.getText().toString();
        } else {
            return null;
        }
    }
    public Date getBirthday() {
        if (birthdayEditText != null) {
            String dateString = birthdayEditText.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Method to get the location from the EditText field
    public String getLocation() {
        if (locationSpinner != null) {
            String selectedLocation = locationSpinner.getSelectedItem().toString();
            if (!selectedLocation.equals(getString(R.string.select_gender_hint))) {
                return selectedLocation;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // Method to get the gender from the EditText field
    public String getGender() {
        if (genderSpinner != null) {
            String selectedGender = genderSpinner.getSelectedItem().toString();
            if (!selectedGender.equals(getString(R.string.select_gender_hint))) {
                return selectedGender;
            } else {
                return null;
            }
        } else {
            return null; // Handle the case when the genderSpinner is not initialized
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
