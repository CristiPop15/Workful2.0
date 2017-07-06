package com.workful.Tools.HttpCommunication;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.cristian.workful20.R;
import com.workful.Tools.ImgHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Cristian on 5/3/2017.
 */

public class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {
    private String url = "http://192.168.100.3:8080/Image/getThumbImage?path=E:/ImgApp/";
    private String extension = ".png";
    private final ImageView imageViewReference;

    public ImageDownloader(ImageView imageView, String email) {
        imageViewReference = imageView;
        url = url+email+extension;
    }

    @Override
    protected void onPreExecute() {
        Log.e("MainActivity", "preex imgDownloader");
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(Void... params) {
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


        if (isCancelled()) {
            bitmap = null;
        }
        try {
            Log.e("MainActivity", bitmap.toString());
        }catch (NullPointerException e){
            imageViewReference.setImageResource(R.drawable.default_profile_image);

        }

        if (imageViewReference != null) {
                if(bitmap != null)
                    imageViewReference.setImageBitmap(bitmap);
            }   else
                    imageViewReference.setImageResource(R.drawable.default_profile_image);
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
