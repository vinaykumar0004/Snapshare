package com.example.snapshare.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snapshare.R;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class IdentifyUserActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button identifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identifyuseractivity);

        emailEditText = findViewById(R.id.etEmail);
        identifyButton = findViewById(R.id.identifyButton);

        identifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyUser();
            }
        });
    }

    private void identifyUser() {
        final String email = emailEditText.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.3:4000/identifyUser");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);

                    JSONObject jsonInput = new JSONObject();
                    jsonInput.put("email", email);

                    try (OutputStream os = conn.getOutputStream()) {
                        byte[] input = jsonInput.toString().getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    int code = conn.getResponseCode();
                    Scanner sc;
                    if (code == 200) {
                        sc = new Scanner(conn.getInputStream());
                    } else {
                        sc = new Scanner(conn.getErrorStream());
                    }
                    StringBuilder result = new StringBuilder();
                    while (sc.hasNext()) {
                        result.append(sc.nextLine());
                    }
                    sc.close();

                    JSONObject response = new JSONObject(result.toString());
                    boolean isPhotographer = response.getBoolean("isPhotographer");

                    Intent intent;
                    if (isPhotographer) {
                        intent = new Intent(IdentifyUserActivity.this, LoginActivity.class);
                    } else {
                        intent = new Intent(IdentifyUserActivity.this, RegisterActivity.class);
                    }
                    intent.putExtra("isPhotographer", isPhotographer);
                    startActivity(intent);

                } catch (Exception e) {
                    Log.e("IdentifyUserActivity", "Error identifying user", e);
                }
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        // This ensures that the app doesn't close when back is pressed
        super.onBackPressed();
        finish(); // Finishes the current activity and returns to the previous one
    }
}


