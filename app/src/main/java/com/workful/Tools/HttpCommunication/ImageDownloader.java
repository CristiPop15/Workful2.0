package com.workful.Tools.HttpCommunication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.workful.Tools.ImgHandler;
import com.workful.templates.AccountInfo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import java.lang.Object;

/**
 * Created by Cristian on 5/3/2017.
 */
public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {
    private String url = "http://192.168.100.3:8080/Image/getThumbImage?path=E:/ImgApp/";
    private String extension = ".png";
    private final WeakReference<ImageView> imageViewReference;

    public ImageDownloader(ImageView imageView, String email) {
        imageViewReference = new WeakReference<>(imageView);
        url = url+email+extension;
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(Void... params) {
        // params comes from the execute() call: params[0] is the url.
        return downloadBitmap(url);
    }

    private Bitmap downloadBitmap(String url) {

        Log.e("MainActivity", url);

        try {

            Log.e("MainActivity", "downloading");


            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream is = urlConnection.getInputStream();


            Log.e("MainActivity", "bytestring");

            byte[] bytes = readFully(is);


            return ImgHandler.convArrayToBmp(bytes);


        }catch (Exception e){
            e.printStackTrace();
        }


        return null;

    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {

        Log.e("MainActivity", bitmap.toString());

        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public static byte[] readFully(InputStream input) throws IOException
    {
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
}
