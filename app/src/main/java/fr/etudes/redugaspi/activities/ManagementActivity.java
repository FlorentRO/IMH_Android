package fr.etudes.redugaspi.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

import fr.etudes.redugaspi.adapters.PagerAdapter;
import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.fragments.FragProducts;


public class ManagementActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST_CODE = 10;
    private final int BARCODE_RECO_REQ_CODE=200;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        TabLayout tabLayout = findViewById(R.id.menu);
        tabLayout.addTab(tabLayout.newTab().setText("Produits"));
        tabLayout.addTab(tabLayout.newTab().setText("Annonces"));
        tabLayout.addTab(tabLayout.newTab().setText("Amis"));
        adapter = new PagerAdapter( getSupportFragmentManager(), tabLayout.getTabCount());

        final ViewPager viewPager = findViewById(R.id.main_pager);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BARCODE_RECO_REQ_CODE){
            if (resultCode == RESULT_OK){
                Bitmap photo = (Bitmap)data.getExtras().get("data");
                barcodeRecognition(photo);
            }
        }
    }

    private void barcodeRecognition(Bitmap photo) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector();
        Task<List<FirebaseVisionBarcode>> result = detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {

                        for (FirebaseVisionBarcode barcode: barcodes) {
                            Rect bounds = barcode.getBoundingBox();
                            Point[] corners = barcode.getCornerPoints();

                            String rawValue = barcode.getRawValue();

                            int valueType = barcode.getValueType();
                            Button button = (Button)findViewById(R.id.AjoutProduit);
                            button.setText(barcode.getDisplayValue());
                            Toast.makeText(ManagementActivity.this, rawValue, Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManagementActivity.this, "Pas de produit", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onPause(){
        super.onPause();
    }


    public void barcodeReco(View v) {
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            callsCamera();
        } else {
            String[] permissionRequested = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequested, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callsCamera();
            } else {
                Toast.makeText(this, "pas de cam", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callsCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,BARCODE_RECO_REQ_CODE);
    }

}
