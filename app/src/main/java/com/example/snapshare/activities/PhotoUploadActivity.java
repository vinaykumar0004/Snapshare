//package com.example.snapshare.activities;
//
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.snapshare.network.RetrofitClient;
//import com.example.snapshare.network.ApiService;
//import com.example.snapshare.models.PhotoUploadResponse;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import com.example.snapshare.R;
//
//import java.io.IOException;
//
//public class PhotoUploadActivity extends AppCompatActivity {
//
//    private static int REQUEST_CODE_SELECT_PHOTOS ;
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private static final int CAMERA_REQUEST = 2;
//
//    private Button btnSelectPhotos, btnEnterGoogleDriveLink, btnSelectPhotosFromDrive;
//    private Button btnUploadPhotoFromCamera, btnSelectPhotoFromGallery;
//    private ImageView imgViewUploadedPhoto;
//    private String userType;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_upload);
//
//        btnSelectPhotos = findViewById(R.id.btnSelectPhotos);
//        btnEnterGoogleDriveLink = findViewById(R.id.btnEnterGoogleDriveLink);
//        btnSelectPhotosFromDrive = findViewById(R.id.btnSelectPhotosFromDrive);
//        btnUploadPhotoFromCamera = findViewById(R.id.btnUploadPhotoFromCamera);
//        btnSelectPhotoFromGallery = findViewById(R.id.btnSelectPhotoFromGallery);
//        imgViewUploadedPhoto = findViewById(R.id.imgViewUploadedPhoto);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, 0);
//        }
//
//        userType = getIntent().getStringExtra("userType");
//
//        if ("photographer".equals(userType)) {
//            btnUploadPhotoFromCamera.setVisibility(View.GONE);
//            btnSelectPhotoFromGallery.setVisibility(View.GONE);
//            imgViewUploadedPhoto.setVisibility(View.GONE);
//        } else {
//            btnSelectPhotos.setVisibility(View.GONE);
//            btnEnterGoogleDriveLink.setVisibility(View.GONE);
//            btnSelectPhotosFromDrive.setVisibility(View.GONE);
//        }
//
//        btnSelectPhotos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectPhotosFromGallery();
//            }
//        });
//
//        btnEnterGoogleDriveLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enterGoogleDriveLink();
//            }
//        });
//
//        btnSelectPhotosFromDrive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectPhotosFromDrive();
//            }
//        });
//
//        btnUploadPhotoFromCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadPhotoFromCamera();
//            }
//        });
//
//        btnSelectPhotoFromGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectPhotoFromGallery();
//            }
//        });
//    }
//
//    /** @noinspection deprecation*/
//    private void selectPhotosFromGallery() {
//        // Implementation for selecting multiple photos from gallery
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTOS);
//    }
//
//    private void enterGoogleDriveLink() {
//        // Implementation for entering Google Drive link
//        String googleDriveLink = btnEnterGoogleDriveLink.getText().toString().trim();
//        if (!googleDriveLink.isEmpty()) {
//            // Save or process the Google Drive link
//            Toast.makeText(this, "Google Drive link saved", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Please enter a valid Google Drive link", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void selectPhotosFromDrive() {
//        // Implementation for selecting photos from Google Drive
//        String googleDriveLink = btnSelectPhotosFromDrive.getText().toString().trim();
//        if (!googleDriveLink.isEmpty()) {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleDriveLink));
//            startActivity(browserIntent);
//        } else {
//            Toast.makeText(this, "Please enter a Google Drive link first", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    /** @noinspection deprecation*/
//    private void uploadPhotoFromCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//    }
//
//    /** @noinspection deprecation*/
//    private void selectPhotoFromGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
//                Uri uri = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    imgViewUploadedPhoto.setImageBitmap(bitmap);
//                    uploadPhoto(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == CAMERA_REQUEST && data != null) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                imgViewUploadedPhoto.setImageBitmap(bitmap);
//                uploadPhoto(bitmap);
//            }
//        }
//    }
//
//    private void uploadPhoto(Bitmap bitmap) {
//        // Convert bitmap to a file and upload it using the Photo Service API
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//        Call<PhotoUploadResponse> call = apiService.uploadPhoto(bitmap); // Assuming the API accepts Bitmap directly
//
//        call.enqueue(new Callback<PhotoUploadResponse>() {
//            @Override
//            public void onResponse(Call<PhotoUploadResponse> call, Response<PhotoUploadResponse> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(PhotoUploadActivity.this, "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(PhotoUploadActivity.this, "Photo upload failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PhotoUploadResponse> call, Throwable t) {
//                Toast.makeText(PhotoUploadActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

//
//import com.example.snapshare.R;
//import com.example.snapshare.network.ApiService;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//
//
//public class PhotoUploadActivity extends AppCompatActivity {
//
//    // Constants for request codes
//    private static final int REQUEST_CODE_SELECT_PHOTOS = 1;
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private static final int REQUEST_CODE_UPLOAD_FROM_CAMERA = 2;
//    private static final String TAG = "PhotoUploadActivity";
//    private LinearLayout photographerSection, photoReceiverSection;
//    private EditText editTextGoogleDriveLink;
//    private Button buttonSelectPhotos, buttonGoogleDrive, buttonUploadFromCamera, buttonSelectFromGallery, buttonMoveToGallery;
//    private ImageView imageView;
//    private ApiService faceRecognitionService;
//    private String userType;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_upload);
//
//        photographerSection = findViewById(R.id.photographerSection);
//        photoReceiverSection = findViewById(R.id.photoReceiverSection);
//        editTextGoogleDriveLink = findViewById(R.id.editTextGoogleDriveLink);
//        buttonSelectPhotos = findViewById(R.id.buttonSelectPhotos);
//        buttonGoogleDrive = findViewById(R.id.buttonGoogleDrive);
//        buttonUploadFromCamera = findViewById(R.id.buttonUploadFromCamera);
//        buttonSelectFromGallery = findViewById(R.id.buttonSelectFromGallery);
//        buttonMoveToGallery = findViewById(R.id.buttonMoveToGallery);
//        imageView = findViewById(R.id.imageViewUploadedPhoto);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//            }, 0);
//        }
//
//
//        userType = getIntent().getStringExtra("user_type");
//        if (userType.equals("photographer")) {
//            photographerSection.setVisibility(View.VISIBLE);
//        } else if (userType.equals("photo_receiver")) {
//            photoReceiverSection.setVisibility(View.VISIBLE);
//        }
//
//        buttonSelectPhotos.setOnClickListener(new View.OnClickListener() {
//            /** @noinspection deprecation*/
//            @Override
//            public void onClick(View v) {
//                // Implement logic to select multiple photos
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTOS);
//            }
//        });
//
//        buttonGoogleDrive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String driveLink = editTextGoogleDriveLink.getText().toString();
//                if (!driveLink.isEmpty()) {
//                    // Implement logic to open Google Drive link and select photos
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(driveLink));
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(PhotoUploadActivity.this, "Please enter a Google Drive link", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        buttonUploadFromCamera.setOnClickListener(new View.OnClickListener() {
//            /** @noinspection deprecation*/
//            @Override
//            public void onClick(View v) {
//                // Implement logic to upload photo from camera
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, REQUEST_CODE_UPLOAD_FROM_CAMERA);
//                }
//            }
//        });
//
//        buttonSelectFromGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Implement logic to select photo from gallery
//                selectPhotosFromGallery();
//            }
//        });
//
//        buttonMoveToGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Move to gallery view activity
//                Intent intent = new Intent(PhotoUploadActivity.this, GalleryActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    /** @noinspection deprecation*/
//    private void selectPhotosFromGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTOS);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_CODE_SELECT_PHOTOS) {
//                if (data != null) {
//                    if (data.getClipData() != null) {
//                        // Handle multiple images
//                        int count = data.getClipData().getItemCount();
//                        for (int i = 0; i < count; i++) {
//                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                            // TODO: Upload or handle the selected image URI
//                        }
//                    } else if (data.getData() != null) {
//                        // Handle single image
//                        Uri imageUri = data.getData();
//                        // TODO: Upload or handle the selected image URI
//                    }
//                }
//            } else if (requestCode == REQUEST_CODE_UPLOAD_FROM_CAMERA) {
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                // TODO: Upload or handle the captured image bitmap
//                imageView.setImageBitmap(imageBitmap);
//            }
//        }
//    }
//}

package com.example.snapshare.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.snapshare.R;
import com.example.snapshare.models.Photo;
import com.example.snapshare.models.PhotoUploadResponse;
import com.example.snapshare.network.ApiService;
import com.example.snapshare.network.RetrofitClient;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoUploadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SELECT_PHOTOS = 1;
//    private static final int PICK_IMAGE_REQUEST = 1;
//    private static final int CAMERA_REQUEST = 2;
    private static final int REQUEST_CODE_SELECT_PHOTO = 1;
    private static final int REQUEST_CODE_TAKE_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LinearLayout photoUploadSection;
    private RelativeLayout gallerySection;
    private RecyclerView rvGallery;
    private Button btnSelectPhotos, btnSelectPhotosFromDrive;
    private EditText etEnterGoogleDriveLink;
    private Button btnUploadPhotoFromCamera, btnSelectPhotoFromGallery;
    private ImageView imgViewUploadedPhoto;

    private Button btnMoveToGallery;

    private String specificCode;
    private List<Uri> selectedImageUris = new ArrayList<>();
//    private Uri photoURI;
//    private Bitmap selectedPhoto;

    private static final int REQUEST_PERMISSIONS = 100;

    private RecycleAdapter recycleAdapter;
    private Interpreter tflite;
    private static final int EMBEDDING_SIZE = 512;
    private List<Photo> detectedFaces = new ArrayList<>(); // To store detected faces and embeddings

    private boolean isPhotographer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);


        btnSelectPhotos = findViewById(R.id.btnSelectPhotos);
        etEnterGoogleDriveLink = findViewById(R.id.etEnterGoogleDriveLink);
        btnSelectPhotosFromDrive = findViewById(R.id.btnSelectPhotosFromDrive);
        btnUploadPhotoFromCamera = findViewById(R.id.btnUploadPhotoFromCamera);
        btnSelectPhotoFromGallery = findViewById(R.id.btnSelectPhotoFromGallery);
        imgViewUploadedPhoto = findViewById(R.id.imgViewUploadedPhoto);
        btnMoveToGallery = findViewById(R.id.btnMoveToGallery);

        // Initialize views
        photoUploadSection = findViewById(R.id.photoUploadSection);
        gallerySection = findViewById(R.id.gallerySection);
        rvGallery = findViewById(R.id.rvGallery);
        rvGallery.setLayoutManager(new LinearLayoutManager(this));
//        rvGallery.setAdapter(new RecycleAdapter(matchingPhotos));

        // Initialize the adapter with the detectedFaces list
        recycleAdapter = new RecycleAdapter(detectedFaces);
        rvGallery.setAdapter(recycleAdapter);

        // Check for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_IMAGES
            }, REQUEST_PERMISSIONS);
        }

        isPhotographer = getIntent().getBooleanExtra("isPhotographer", false);

        if (isPhotographer) {
            btnUploadPhotoFromCamera.setVisibility(View.GONE);
            btnSelectPhotoFromGallery.setVisibility(View.GONE);
            imgViewUploadedPhoto.setVisibility(View.GONE);
        } else {
            btnSelectPhotos.setVisibility(View.GONE);
            etEnterGoogleDriveLink.setVisibility(View.GONE);
            btnSelectPhotosFromDrive.setVisibility(View.GONE);
        }

//

        btnUploadPhotoFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takePhotoWithCamera();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
//                }
            }
        });

        btnSelectPhotoFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoFromGallery();
            }
        });


        specificCode = getIntent().getStringExtra("specificCode");
        if (specificCode == null || specificCode.isEmpty()) {
            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            specificCode = sharedPref.getString("specificCode", "");
            Log.e("specificCode ","specific code like that's " + specificCode);

//            String SpecificCode = "P" + specificCode;  // Add 'P' prefix
        }

        if (specificCode.isEmpty()) {
            Toast.makeText(this, "Specific code is missing.", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSelectPhotos.setOnClickListener(v -> uploadPhotos());
        btnSelectPhotosFromDrive.setOnClickListener(v -> uploadGoogleDrivePhotos());
        btnMoveToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the photo upload section and show the gallery section
                photoUploadSection.setVisibility(View.GONE);
                gallerySection.setVisibility(View.VISIBLE);
            }
        });

        // Initialize ArcFace model
        try {
            MappedByteBuffer tfliteModel = FileUtil.loadMappedFile(this, "arcface_model.tflite");
            tflite = new Interpreter(tfliteModel);
        } catch (Exception e) {
            Log.e("Archhhh", "Error loading ArcFace model", e);
        }

        // Request necessary permissions
        requestPermissions();



    }
    @Override
    public void onBackPressed() {
        // Handle back button press here
        if (rvGallery != null && rvGallery.getChildCount() > 0) {
            // If RecyclerView is visible and has items, finish the current activity and go back
            finish(); // This will return to the previous activity
        } else {
            super.onBackPressed(); // Default behavior, in case RecyclerView is empty
        }
    }

    private void uploadPhotos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photos"), REQUEST_CODE_SELECT_PHOTOS);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void selectPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), REQUEST_CODE_SELECT_PHOTO);
    }

//    private void takePhotoWithCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_PHOTOS && data != null) {
                detectedFaces.clear();  // Clear previous results

            if (data.getClipData() != null) { // Multiple images selected
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    processImageUri(imageUri);
                }
            } else if (data.getData() != null) { // Single image selected
                Uri imageUri = data.getData();
                processImageUri(imageUri);
                }
            } else if (requestCode == REQUEST_CODE_TAKE_PHOTO && data != null) { // Handling camera capture
                // Capture photo from camera and convert to Bitmap
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");

                if (capturedImage != null) {
                    // Display the captured photo in the ImageView
                    imgViewUploadedPhoto.setImageBitmap(capturedImage);

                    // Proceed to face detection on the captured photo
                    detectFace(capturedImage, null); // No URI for camera photos
                }
            }

//            recycleAdapter.notifyDataSetChanged();
        }
    }

    private void processImageUri(Uri imageUri) {
        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            // Display the selected image in the ImageView
            imgViewUploadedPhoto.setImageBitmap(selectedImage);

            // Proceed to face detection
            detectFace(selectedImage, imageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    private void detectAndRecognizeFace(Bitmap bitmap) {
    private void detectFace(Bitmap image, Uri imageUri) {
        InputImage inputImage = InputImage.fromBitmap(image, 0);

        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .build();

        FaceDetector detector = FaceDetection.getClient(options);

        detector.process(inputImage)
                .addOnSuccessListener(faces -> handleFaces(faces, image, imageUri))
                .addOnFailureListener(e -> Log.e("FaceDetection", "Face detection failed", e));
    }

    private void handleFaces(List<Face> faces, Bitmap image, Uri imageUri) {
        if (faces.isEmpty()) {
            Toast.makeText(this, "No faces detected.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<float[]> embeddingsList = new ArrayList<>();
        for (Face face : faces) {
            Bitmap croppedFace = cropFaceFromImage(face, image);
            float[] embedding = recognizeFace(croppedFace);
            embeddingsList.add(embedding);
        }

        Toast.makeText(this, "Face's"+" Detect successfully", Toast.LENGTH_SHORT).show();

        if (isPhotographer) {

        // Store the photo and its embeddings
        detectedFaces.add(new Photo(image, embeddingsList));


        // Upload photo and embeddings to the server
        uploadPhotoAndEmbeddingsToServer(imageUri, image, embeddingsList);
        } else {

            // User: compare photo with stored embeddings
            fetchAndComparePhotos(embeddingsList);
        }


        // Notify the adapter to refresh the UI
        recycleAdapter.notifyDataSetChanged();
    }

    private Bitmap cropFaceFromImage(Face face, Bitmap image) {
        return Bitmap.createBitmap(image, face.getBoundingBox().left, face.getBoundingBox().top,
                face.getBoundingBox().width(), face.getBoundingBox().height());
    }

    private float[] recognizeFace(Bitmap faceBitmap) {
        Bitmap resizedFace = Bitmap.createScaledBitmap(faceBitmap, 112, 112, true);
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(resizedFace);

        float[][] faceEmbedding = new float[1][EMBEDDING_SIZE];
        tflite.run(inputBuffer, faceEmbedding);

//        Log.e("embeddingggggggggg", "embeddinggghbaifu9",+ faceEmbedding);

        return faceEmbedding[0];
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 1 * 112 * 112 * 3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[112 * 112];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        int pixelIndex = 0;
        for (int i = 0; i < 112; ++i) {
            for (int j = 0; j < 112; ++j) {
                final int val = intValues[pixelIndex++];
                byteBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                byteBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                byteBuffer.putFloat((val & 0xFF) / 255.0f);
            }
        }

        return byteBuffer;
    }

    // Method to upload photo and embeddings to the server
    private void uploadPhotoAndEmbeddingsToServer(Uri imageUri, Bitmap image, List<float[]> embeddingsList) {

        Log.e("private void uploadPhotoAndEmbeddingsToServer(Uri imageUri, Bitmap image, List<float[]> embeddingsList)", "private void uploadPhotoAndEmbeddingsToServer(Uri imageUri, Bitmap image, List<float[]> embeddingsList)" );
        String realPath = getRealPathFromURI(imageUri);


        if (realPath == null) {
            // Fallback: Save the bitmap to a temporary file
            File tempFile = saveBitmapToFile(image);
            if (tempFile == null) {
                Toast.makeText(this, "Failed to save the image for upload", Toast.LENGTH_SHORT).show();
                return;
            }
            realPath = tempFile.getAbsolutePath();
        }
        ProgressBar progressBar = findViewById(R.id.uploadProgressBar); // Reference to the progress bar
        progressBar.setVisibility(View.VISIBLE);  // Show the progress bar
        progressBar.setProgress(0);

        File imageFile = new File(getRealPathFromURI(imageUri));
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
//        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", imageFile.getName(), requestFile);

        ProgressRequestBody fileBody = new ProgressRequestBody(imageFile, percentage -> runOnUiThread(() -> {
            progressBar.setProgress(percentage);
        }));
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photos", imageFile.getName(), fileBody);
// 2       MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photos", imageFile.getName(), requestFile);

        JSONArray embeddingsJson = new JSONArray();  // Use JSONArray for cleaner structure
        for (float[] embedding : embeddingsList) {
            JSONArray faceEmbeddingArray = new JSONArray();
            for (float value : embedding) {
                try {
                    faceEmbeddingArray.put(value);  // Put each value of the embedding into the array
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            embeddingsJson.put(faceEmbeddingArray);  // Add the face embedding array to the main array
        }
//1
        RequestBody embeddingsBody = RequestBody.create(MediaType.parse("application/json"), embeddingsJson.toString());



        if (specificCode.startsWith("P")) {
            String specificCodee = specificCode.substring(1);  // Remove 'P' prefix

            // Prepare request body for embeddings
//            RequestBody embeddingsBody = RequestBody.create(MediaType.parse("application/json"), embeddingsJson.toString());

            // Prepare the specific code body if needed
//            RequestBody specificCodeBody = RequestBody.create(MediaType.parse("text/plain"), "74950");
            RequestBody specificCodeBody = RequestBody.create(MultipartBody.FORM, specificCodee);
            // Make API call
            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ResponseBody> call = apiService.uploadPhotos(specificCodeBody, List.of(photoPart), embeddingsBody);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // Always hide the progress bar on success or failure
                    progressBar.setVisibility(View.GONE);  // Hide progress bar after completion

                    try {
                        int statusCode = response.code();
                        String responseBody = response.body() != null ? response.body().string() : null;

                        Log.d("Upload Response", "Status Code: " + statusCode);
                        Log.d("Upload Response", "Response Body: " + responseBody);

                        if (response.isSuccessful()) {
                            Log.i(  "onResponse: ","Photo Upload Successfully " );
                        } else {
                            Toast.makeText(PhotoUploadActivity.this, "Upload failed with status: " + statusCode, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(PhotoUploadActivity.this, "Photos uploaded successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(PhotoUploadActivity.this, "Upload failed due to an exception", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PhotoUploadActivity.this, "API call failed. Check your network connection.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    private void fetchAndComparePhotos(List<float[]> embeddingsList) {
        // Fetch photos from the API
        // Assuming specificCode is entered by the user
//        String specificCode = "74950"; // Replace with dynamic input from user




        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Photo>> call = apiService.getPhotos(specificCode);

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Photo> allPhotos = response.body();
                    List<Photo> matchedPhotos = new ArrayList<>();

//                    matchingPhotos.clear();
                    // Compare embeddings
                    if (allPhotos != null) {
                        for (Photo photo : allPhotos) {
                            if (photo.getEmbeddings() != null) {
                                for (float[] storedEmbedding : photo.getEmbeddings()) {
                                    for (float[] uploadedEmbedding : embeddingsList) {
                                        if (isMatching(storedEmbedding, uploadedEmbedding)) {
                                            matchedPhotos.add(photo);
                                            break;
                                        }
                                    }
                                }
                            }   Log.i(" if (photo.getEmbeddings() != null)", " if (photo.getEmbeddings() != null)" + photo.getEmbeddings());
                        }   Log.i("for (Photo photo : allPhotos)", "for (Photo photo : allPhotos) " + allPhotos);
                    }
                    // Update RecyclerView with matched photos
                    detectedFaces.clear();
                    detectedFaces.addAll(matchedPhotos);
                    recycleAdapter.notifyDataSetChanged();
                } else {
                    Log.i("Failed to fetch photos", "Failed to fetch photos" );

                    Toast.makeText(PhotoUploadActivity.this, "Failed to fetch photos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(PhotoUploadActivity.this, "Error fetching photos: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isMatching(float[] embedding1, float[] embedding2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += Math.pow(embedding1[i], 2);
            norm2 += Math.pow(embedding2[i], 2);
        }

        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);

        double similarity = dotProduct / (norm1 * norm2); // Cosine similarity

        // Threshold for 75% similarity (cosine similarity is between -1 and 1, higher is more similar)
        return similarity > 0.70; // 75% similarity threshold
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        try {
            File tempFile = File.createTempFile("upload_image", ".jpg", getCacheDir());
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void uploadGoogleDrivePhotos() {
        String googleDriveLink = etEnterGoogleDriveLink.getText().toString().trim();
        if (!googleDriveLink.isEmpty()) {
            // Implement logic to open Google Drive link and select photos
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleDriveLink));
            startActivity(intent);
        } else {
            Toast.makeText(PhotoUploadActivity.this, "Please enter a Google Drive link", Toast.LENGTH_SHORT).show();
//
            return;
        }

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        RequestBody specificCodeBody = RequestBody.create(MediaType.parse("text/plain"), specificCode);
        RequestBody photosBody = RequestBody.create(MediaType.parse("text/plain"), googleDriveLink);

        Call<PhotoUploadResponse> call = apiService.uploadGoogleDrivePhotos(specificCodeBody, photosBody);
        call.enqueue(new Callback<PhotoUploadResponse>() {
            @Override
            public void onResponse(Call<PhotoUploadResponse> call, Response<PhotoUploadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PhotoUploadActivity", "Google Drive photo upload successful: " + response.body().toString());
                    Toast.makeText(PhotoUploadActivity.this, "Google Drive photo upload successful", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("PhotoUploadActivity", "Google Drive photo upload failed: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            String errorBody = response.errorBody().string();
                            Log.e("PhotoUploadActivity", "Error body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("PhotoUploadActivity", "Error reading error body", e);
                        }
                    }
                    Toast.makeText(PhotoUploadActivity.this, "Google Drive photo upload failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhotoUploadResponse> call, Throwable t) {
                Log.e("PhotoUploadActivity", "API call failed: " + t.getMessage());
                Toast.makeText(PhotoUploadActivity.this, "API call failed. Check your network connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    @Override
//    public void onBackPressed() {
//        // This ensures that the app doesn't close when back is pressed
//        super.onBackPressed();
//        finish(); // Finishes the current activity and returns to the previous one
//    }

    class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
        private List<Photo> photos;


        public RecycleAdapter(List<Photo> photos) {

            this.photos = photos;
        }

        @Override
        public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_photo, parent, false);
            return new RecycleAdapter.MyViewHolder(itemView);
        }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Photo photo = photos.get(position);
                String originalPath = photo.getPath(); // Get the path from the photo object

                if (originalPath != null) {

                    // If the path is not null, replace backslashes with forward slashes
                    String formattedPath = originalPath.replace("P:\\photoss\\", "/photoss/").replace("/\\/g", "/"); // Convert backslashes to forward slashes
                    String photoPath = "http://192.168.1.3:4001" + formattedPath;
                    Log.i("paaataa", "pathh photos: " + photoPath);

                    // Load the image using Glide
                    Glide.with(holder.itemView.getContext())
                            .load(photoPath)
                            .placeholder(R.drawable.bgimagesnapshare)
                            .fitCenter()
                            .into(holder.imageView);

                    holder.downloadButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                if (ContextCompat.checkSelfPermission(PhotoUploadActivity.this,
                                        Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PhotoUploadActivity.this,
                                            new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                                } else {
                                    new DownloadImageTask(photoPath).execute();
                                }
                            } else {
                                if (ContextCompat.checkSelfPermission(PhotoUploadActivity.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(PhotoUploadActivity.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                                } else {
                                    new DownloadImageTask(photoPath).execute();
                                }
                            }
                        }
                    });
                } else if (photo.getBitmap() != null) {

                    // If the path is null but we have a Bitmap, display the Bitmap
                    Glide.with(holder.itemView.getContext())
                            .load(photo.getBitmap())
                            .placeholder(R.drawable.bgimagesnapshare)
                            .fitCenter()   // Adjusts the image to fit in the ImageView, preserving the aspect ratio
                            .into(holder.imageView);
                } else {
                    // If both path and Bitmap are null, log a message or handle the error
                    Log.e("RecycleAdapter", "Photo path and Bitmap are both null");
                    holder.imageView.setImageResource(R.drawable.bgimagesnapshare); // Set a placeholder image if no data is available
                }

            }


            @Override
        public int getItemCount() {
            return photos.size() ;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            ImageButton downloadButton;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                downloadButton = itemView.findViewById(R.id.downloadButton);
            }
        }
    }

    /** @noinspection deprecation*/
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;

        public DownloadImageTask(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(imageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                saveImage(result);
            } else {
                Toast.makeText(PhotoUploadActivity.this, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
        }

        private void saveImage(Bitmap bitmap) {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File myDir = new File(root + "/DownloadedImages");
            myDir.mkdirs();
            String name = "Image-" + System.currentTimeMillis() + ".jpg";
            File file = new File(myDir, name);
            if (file.exists()) file.delete();
            try {

                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Toast.makeText(PhotoUploadActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(PhotoUploadActivity.this, "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}






