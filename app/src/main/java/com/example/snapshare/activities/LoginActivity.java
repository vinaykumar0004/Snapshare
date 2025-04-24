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
import com.example.snapshare.models.LoginRequest;
import com.example.snapshare.models.LoginResponse;
import com.example.snapshare.network.ApiService;
import com.example.snapshare.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText  etDigit1, etDigit2, etDigit3, etDigit4, etDigit5;
    private EditText etEmail, etPassword;
    private TextView btnRegister;
    private Button btnLogin;
    private ImageView ivTogglePasswordVisibility;

    private boolean isPasswordVisible = false;

    private String specificCode;
    private String selectedRole = "";  // Use this to capture user type
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        ivTogglePasswordVisibility = findViewById(R.id.ivTogglePasswordVisibility);
        roleSpinner = findViewById(R.id.roleSpinner);

        etDigit1 = findViewById(R.id.etDigit1);
        etDigit2 = findViewById(R.id.etDigit2);
        etDigit3 = findViewById(R.id.etDigit3);
        etDigit4 = findViewById(R.id.etDigit4);
        etDigit5 = findViewById(R.id.etDigit5);

        // Add TextWatchers
        setupDigitTextWatchers();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
        ivTogglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
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

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(selectedRole)) {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return;
        }
        String code = etDigit1.getText().toString().trim() + etDigit2.getText().toString().trim() +
                etDigit3.getText().toString().trim() + etDigit4.getText().toString().trim() +
                etDigit5.getText().toString().trim();


        // Validate that all fields are filled
        if (code.length() != 5) {
            Toast.makeText(LoginActivity.this, "Please enter all 5 digits of the specific code.", Toast.LENGTH_SHORT).show();
            return;  // Stop the process if specific code is incomplete
        }


        // If user type is Photo-Sender, prepend 'P' to the specific code
        if (selectedRole.equals("Photo-Sender")) {
            specificCode = "P" + code;
        } else {
            specificCode = code;
        }

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(specificCode)) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password, specificCode);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<LoginResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LoginActivity", "Login successful: " + response.body().toString());
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Save specificCode in SharedPreferences
//                    String specificCode = etSpecificCode.getText().toString().trim();
//                    if (specificCode.startsWith("P")) {
//                        specificCode = specificCode.substring(1);  // Remove 'P' prefix
//                    }
                    SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("specificCode", specificCode);
                    editor.apply();
                    navigateToNextActivity(specificCode.startsWith("P"));
                } else {
                    Log.e("LoginActivity", "Login failed: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("LoginActivity", "Error body: " + errorBody);
                        } catch (Exception e) {
                            Log.e("LoginActivity", "Error reading error body", e);
                        }
                    }
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("LoginActivity", "API call failed: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void navigateToNextActivity(boolean isPhotographer) {
//        Intent intent = new Intent(LoginActivity.this, PhotoUploadActivity.class);
//        intent.putExtra("isPhotographer", isPhotographer);
//        startActivity(intent);
//        finish();
//    }


    private void navigateToNextActivity(boolean isPhotographer) {
        Intent intent = new Intent(LoginActivity.this, PhotoUploadActivity.class);
        intent.putExtra("isPhotographer", isPhotographer);
        startActivity(intent);
        finish();
    }

    private void handleRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        // This ensures that the app doesn't close when back is pressed
        super.onBackPressed();
        finish(); // Finishes the current activity and returns to the previous one
    }
}








