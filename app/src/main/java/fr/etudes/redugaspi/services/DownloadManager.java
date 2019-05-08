package fr.etudes.redugaspi.services;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadManager {

    public static JSONObject getProductData(String barcode) {

        String url = "http://fr.openfoodfacts.org/api/v0/produit/" + barcode + ".json";

        String data = downloadFile(url);
        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage());
            return new JSONObject();
        }
    }

    public static Bitmap getImage(String url) {
        String rawData = downloadFile(url);
        byte[] bytes = Base64.decode(rawData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private static String downloadFile(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputstream = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(inputstream));
            StringBuilder data = new StringBuilder();
            String line=null;
            while ((line = buffer.readLine())!=null) {
                data.append(line);
            }
            buffer.close();
            inputstream.close();
            return data.toString();
        } catch (IOException ioe) {
            Log.e("ERROR", ioe.getMessage());
            return null;
        }
    }
}
