package com.example.snapshare.models;

import java.util.List;

public class PhotoListResponse {

    private List<Photo> photos;

    public PhotoListResponse(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public static class Photo {
        private String url;
        private String title;

        public Photo(String url, String title) {
            this.url = url;
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title; // This will be displayed in the ListView
        }
    }
}
