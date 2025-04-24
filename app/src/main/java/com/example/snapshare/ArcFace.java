package com.example.snapshare;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ArcFace {
    private Interpreter interpreter;
    private static final String MODEL_PATH = "arcface_model.tflite";
    private static final int INPUT_SIZE = 112; // Assuming the model input size is 112x112

    public ArcFace(AssetManager assetManager) throws IOException {
        Interpreter.Options options = new Interpreter.Options();
        options.setNumThreads(4); // Use 4 threads for inference
        interpreter = new Interpreter(loadModelFile(assetManager, MODEL_PATH), options);
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float[] getEmbeddings(Bitmap bitmap) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, true);
        ByteBuffer imgData = ByteBuffer.allocateDirect(4 * INPUT_SIZE * INPUT_SIZE * 3);
        imgData.order(ByteOrder.nativeOrder());

        int[] intValues = new int[INPUT_SIZE * INPUT_SIZE];
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < INPUT_SIZE; ++i) {
            for (int j = 0; j < INPUT_SIZE; ++j) {
                final int val = intValues[pixel++];
                imgData.putFloat(((val >> 16) & 0xFF) / 255.0f);
                imgData.putFloat(((val >> 8) & 0xFF) / 255.0f);
                imgData.putFloat((val & 0xFF) / 255.0f);
            }
        }

        float[][] embeddings = new float[1][512]; // Assuming the model outputs a 512-dimensional embedding
        interpreter.run(imgData, embeddings);

        return embeddings[0];
    }
}

