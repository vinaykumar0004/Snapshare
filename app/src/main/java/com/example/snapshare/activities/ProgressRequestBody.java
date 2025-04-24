package com.example.snapshare.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private final File file;
    private final ProgressListener progressListener;
    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface ProgressListener {
        void onProgressUpdate(int percentage);
    }

    public ProgressRequestBody(final File file, final ProgressListener listener) {
        this.file = file;
        this.progressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                progressListener.onProgressUpdate((int) (100 * uploaded / fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }
}

