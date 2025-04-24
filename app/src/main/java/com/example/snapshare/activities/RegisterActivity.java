//package com.example.snapshare.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.snapshare.models.RegisterRequest;
//import com.example.snapshare.network.RetrofitClient;
//import com.example.snapshare.network.ApiService;
//import com.example.snapshare.models.RegisterResponse;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import com.example.snapshare.R;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RegisterActivity extends AppCompatActivity {
//
//    private EditText etName, etEmail, etPassword, etSpecificCode;
//    private Button btnVerifyEmail, btnRegister;
//    private boolean isEmailVerified = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        etName = findViewById(R.id.etName);
//        etEmail = findViewById(R.id.etEmail);
//        etPassword = findViewById(R.id.etPassword);
//        etSpecificCode = findViewById(R.id.etSpecificCode);
//        btnVerifyEmail = findViewById(R.id.btnVerifyEmail);
//        btnRegister = findViewById(R.id.btnRegister);
//
//        btnVerifyEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendVerificationEmail();
//            }
//        });
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isEmailVerified) {
//                    registerUser();
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Please verify your email first", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        btnRegister.setEnabled(false); // Disable the register button initially
//    }
//
//    private void sendVerificationEmail() {
//        String email = etEmail.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("email", email);
//
//        Call<RegisterResponse> call = apiService.sendVerificationEmail(requestBody);
//
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("RegisterActivity", "Verification email sent: " + response.body().toString());
//                    Toast.makeText(RegisterActivity.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
//                    btnRegister.setEnabled(true); // Enable the register button after verification email is sent
//                    isEmailVerified = true;
//                } else {
//                    Log.e("RegisterActivity", "Email verification failed: " + response.message());
//                    if (response.errorBody() != null) {
//                        try {
//                            String errorBody = response.errorBody().string();
//                            Log.e("RegisterActivity", "Error body: " + errorBody);
//                        } catch (Exception e) {
//                            Log.e("RegisterActivity", "Error reading error body", e);
//                        }
//                    }
//                    Toast.makeText(RegisterActivity.this, "Email verification failed. Try again.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.e("RegisterActivity", "API call failed: " + t.getMessage());
//                Toast.makeText(RegisterActivity.this, "API call failed. Check your network connection.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void registerUser() {
//        String name = etName.getText().toString().trim();
//        String email = etEmail.getText().toString().trim();
//        String password = etPassword.getText().toString().trim();
//        String specificCode = etSpecificCode.getText().toString().trim();
//
//        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(specificCode)) {
//            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        RegisterRequest registerRequest = new RegisterRequest(name, email, password, specificCode);
//
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//        Call<RegisterResponse> call = apiService.registerUser(registerRequest);
//
//        call.enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("RegisterActivity", "Register successful: " + response.body().toString());
//                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                    navigateToNextActivity(specificCode.startsWith("P"));
//                } else {
//                    Log.e("RegisterActivity", "Register failed: " + response.message());
//                    if (response.errorBody() != null) {
//                        try {
//                            String errorBody = response.errorBody().string();
//                            Log.e("RegisterActivity", "Error body: " + errorBody);
//                        } catch (Exception e) {
//                            Log.e("RegisterActivity", "Error reading error body", e);
//                        }
//                    }
//                    Toast.makeText(RegisterActivity.this, "Invalid credentials or email already registered", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Log.e("RegisterActivity", "API call failed: " + t.getMessage());
//                Toast.makeText(RegisterActivity.this, "API call failed. Check your network connection.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void navigateToNextActivity(boolean isPhotographer) {
//        Intent intent = new Intent(RegisterActivity.this, PhotoUploadActivity.class);
//        intent.putExtra("isPhotographer", isPhotographer);
//        startActivity(intent);
//        finish();
//    }
//}


package com.example.snapshare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snapshare.R;
import com.example.snapshare.models.RegisterRequest;
import com.example.snapshare.models.RegisterResponse;
import com.example.snapshare.network.ApiService;
import com.example.snapshare.network.RetrofitClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Spinner roleSpinner;
    private EditText etName, etEmail, etPassword,etDigit1, etDigit2, etDigit3, etDigit4, etDigit5;
    private Button btnVerifyEmail, btnRegister , btnLogin;
    private ImageView ivTogglePasswordVisibility;

    private boolean isVerified = false;
    private boolean isPasswordVisible = false;
    private String specificCode;
    private String selectedRole = "";// Use this to capture user type

    private boolean isEmailVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnVerifyEmail = findViewById(R.id.btnVerifyEmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        ivTogglePasswordVisibility = findViewById(R.id.ivTogglePasswordVisibility);
        roleSpinner = findViewById(R.id.roleSpinner);

        // Initialize UI components
//        roleSpinner = findViewById(R.id.roleSpinner);
        etDigit1 = findViewById(R.id.etDigit1);
        etDigit2 = findViewById(R.id.etDigit2);
        etDigit3 = findViewById(R.id.etDigit3);
        etDigit4 = findViewById(R.id.etDigit4);
        etDigit5 = findViewById(R.id.etDigit5);

        // Add TextWatchers
        setupDigitTextWatchers();

        btnVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationEmail();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVerified) {
                    registerUser();
                } else {
                    Toast.makeText(RegisterActivity.this, "Pleaseee verify your email first", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        btnRegister.setEnabled(false); // Disable the register button initially
        ivTogglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Create a list of roles
        List<String> roles = new ArrayList<>();
        roles.add("Select User Type"); // This will act as the hint
        roles.add("Photo-Sender");
        roles.add("Photo-Receiver");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner (Select User Type)
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color (grey)
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

// Set the adapter to the Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

// Optional: You can set the initial selection to "Select User Type"
        roleSpinner.setSelection(0);

        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    // Handle valid user selection and store it in selectedRole variable
                    selectedRole = roles.get(position);

                    // Update the text color to black to show it as a selected item, not a hint
                    ((TextView) parentView.getChildAt(0)).setTextColor(Color.BLACK);

                    Toast.makeText(getApplicationContext(), "Selected: " + selectedRole, Toast.LENGTH_SHORT).show();
                } else {
                    // Reset selectedRole if no valid selection is made
                    selectedRole = "";
                    ((TextView) parentView.getChildAt(0)).setTextColor(Color.GRAY);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
                selectedRole = "";
            }
        });

    }

    private void setupDigitTextWatchers() {
        setupNextDigitFocus(etDigit1, etDigit2);
        setupNextDigitFocus(etDigit2, etDigit3);
        setupNextDigitFocus(etDigit3, etDigit4);
        setupNextDigitFocus(etDigit4, etDigit5);
    }

    private void setupNextDigitFocus(EditText current, EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) next.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivTogglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off);
        } else {
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivTogglePasswordVisibility.setImageResource(R.drawable.ic_visibility_on);
        }
        isPasswordVisible = !isPasswordVisible;
        etPassword.setSelection(etPassword.length()); // Move the cursor to the end
    }

    private void sendVerificationEmail() {
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);

        Call<RegisterResponse> call = apiService.sendVerificationEmail(requestBody);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RegisterActivity", "Verification email sent: " + response.body().toString());
                    Toast.makeText(RegisterActivity.this, "Verification email sent. Please check your email.", Toast.LENGTH_SHORT).show();
//                    btnRegister.setEnabled(true); // Enable the register button after verification email is sent
                    isVerified = true;
                } else {
                    Log.e("RegisterActivity", "Email verification failed: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("RegisterActivity", "Error body: " + errorBody);

                            // Handle specific error messages from the API
                            if (errorBody.contains("Email is already exist and also verified")) {
                                Toast.makeText(RegisterActivity.this, "Email is already exist and also verified.", Toast.LENGTH_SHORT).show();
                            } else if (errorBody.contains("Failed to send verification email")) {
                                Toast.makeText(RegisterActivity.this, "Failed to send verification email. Try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("RegisterActivity", "Error reading error body", e);
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email verification failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("RegisterActivity", "API call failed: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "API call failed. Check your network connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {

        if (!isVerified) {
            Toast.makeText(this, "Please verify your email first", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(selectedRole)) {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct specific code from EditText inputs
        String code = etDigit1.getText().toString() +
                etDigit2.getText().toString() +
                etDigit3.getText().toString() +
                etDigit4.getText().toString() +
                etDigit5.getText().toString();

        // Validate that all fields are filled
        if (code.length() != 5) {
            Toast.makeText(RegisterActivity.this, "Please enter all 5 digits of the specific code.", Toast.LENGTH_SHORT).show();
            return;  // Stop the process if specific code is incomplete
        }

        // If user type is Photo-Sender, prepend 'P' to the specific code
        if (selectedRole.equals("Photo-Sender")) {
            specificCode = "P" + code;
        } else {
            specificCode = code;
        }

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(specificCode)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(name, email, password, specificCode);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<RegisterResponse> call = apiService.registerUser(registerRequest);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("RegisterActivity", "Register successful: " + response.body().toString());
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Save specificCode to SharedPreferences
                    SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("specificCode", specificCode);
                    editor.apply();

                    navigateToNextActivity(specificCode.startsWith("P"));
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        if (errorBody.contains("This specific code is already in use")) {
                            Toast.makeText(RegisterActivity.this, "Specific code already in use. Please use a different code.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("RegisterActivity", "Error reading error body", e);
                        Toast.makeText(RegisterActivity.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


//    private void saveSpecificCode(String specificCode) {
//        SharedPreferences sharedPreferences = getSharedPreferences("SnapSharePrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("specificCode", specificCode);
//        editor.apply();
//    }


    private void navigateToNextActivity(boolean isPhotographer) {
        Intent intent = new Intent(RegisterActivity.this, PhotoUploadActivity.class);
        intent.putExtra("isPhotographer", isPhotographer);
        startActivity(intent);
        finish();
    }
    private void handleLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // This ensures that the app doesn't close when back is pressed
        super.onBackPressed();
        finish(); // Finishes the current activity and returns to the previous one
    }
}

