package com.example.snapshare.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.snapshare.R;

public class IdentificationActivity extends AppCompatActivity {

    private Button btnPhotographer, btnPhotoReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        btnPhotographer = findViewById(R.id.btnPhotographer);
        btnPhotoReceiver = findViewById(R.id.btnPhotoReceiver);

        btnPhotographer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdentificationActivity.this, LoginActivity.class);
                intent.putExtra("userType", "photographer");
                startActivity(intent);
            }
        });

        btnPhotoReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IdentificationActivity.this, LoginActivity.class);
                intent.putExtra("userType", "photoReceiver");
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // This ensures that the app doesn't close when back is pressed
        super.onBackPressed();
        finish(); // Finishes the current activity and returns to the previous one
    }
}

//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import com.example.snapshare.R;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class IdentificationActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_identification);
//
//        Button btnPhotographer = findViewById(R.id.btn_photographer);
//        Button btnPhotoReceiver = findViewById(R.id.btn_photo_receiver);
//
//        btnPhotographer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToLogin("photographer");
//            }
//        });
//
//        btnPhotoReceiver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToLogin("photo_receiver");
//            }
//        });
//    }
//
//    private void navigateToLogin(String userType) {
//        Intent intent = new Intent(IdentificationActivity.this, LoginActivity.class);
//        intent.putExtra("user_type", userType);
//        startActivity(intent);
//    }
//}
