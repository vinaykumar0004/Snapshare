package com.example.snapshare.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {
    @SerializedName("_id")
    private String id;

    @SerializedName("path")
    private String path;

    @SerializedName("specificCode")
    private String specificCode;

    private Bitmap bitmap;


    private float[] matchingPhotos;
    private Bitmap image; // Full image
    private List<float[]> embeddings;

    public Photo(Bitmap bitmap, List<float[]> embeddings) {
        this.bitmap = bitmap;
        this.embeddings = embeddings;
    }

    public Photo(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Photo(String path) {
        this.path = path;
    }
    public Bitmap getImage() {
        return image;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public List<float[]> getEmbeddings() {
        return embeddings;
    }

    public float[] getEmbedding() {
        return matchingPhotos;
    }
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSpecificCode() {
        return specificCode;
    }

    public void setSpecificCode(String specificCode) {
        this.specificCode = specificCode;
    }

    public void setEmbeddings(List<float[]> embeddings) {
        this.embeddings = embeddings;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isBitmapAvailable() {
        return bitmap != null;
    }
}
