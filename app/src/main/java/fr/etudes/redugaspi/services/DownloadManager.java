package fr.etudes.redugaspi.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.ProductName;

public class DownloadManager {

    public static JSONObject getProductData(Context context, String barcode) {
        JSONObject json = null;
        try {
             json = getProductDataFromFile(context, barcode);
        } catch (Exception e) {
            json = getProductDataFromInternet(context, barcode);
        }

        try {
            if (Database.getNames().getFirst(x->x.getBarcode().equals(barcode))==null) {
                String name = json.getJSONObject("product").getString("product_name");
                Database.getNames().add(context, new ProductName(barcode, name));
            }
        } catch (JSONException ignored) {}

        return json;
    }

    public static Bitmap getImage(Context context, String barcode) {
        Bitmap bitmap = null;
        try {
            bitmap = getImageFromFile(context, barcode);
            Log.e("ERROR", "loaded png from cache: " + barcode);
        } catch (Exception e) {
            bitmap = getImageFromInternet(context, barcode);
            Log.e("ERROR", "loaded png from internet: " + barcode);
        }
        return bitmap;
    }

    private static String download(String url) {
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

    private static JSONObject getProductDataFromInternet(Context context, String barcode) {
        String url = "https://fr.openfoodfacts.org/api/v0/produit/" + barcode + ".json";
        String data = download(url);
        try {
            if (data != null) {
                FileOutputStream f = context.openFileOutput(barcode+".json", Context.MODE_PRIVATE);
                f.write(data.getBytes());
                f.close();
            }
            return new JSONObject(data);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            return new JSONObject();
        }
    }

    private static JSONObject getProductDataFromFile(Context context, String barcode) throws Exception {
        FileInputStream in = context.openFileInput(barcode + ".json");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder data = new StringBuilder();
        String line = null;
        while ((line = reader.readLine())!=null) {
            data.append(line);
        }
        in.close();
        return new JSONObject(data.toString());
    }

    private static Bitmap getImageFromInternet(Context context, String barcode) {
        try {
            String url = getProductData(context, barcode).getJSONObject("product").getString("image_thumb_url");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputstream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
            FileOutputStream f = context.openFileOutput(barcode+".png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, f);
            f.close();
            inputstream.close();
            return bitmap;
        } catch (Exception ioe) {
            Log.e("ERROR", ioe.getMessage());
            return null;
        }
    }

    private static Bitmap getImageFromFile(Context context, String barcode) throws Exception {
        FileInputStream in = context.openFileInput(barcode + ".png");
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        in.close();
        return bitmap;
    }
}
