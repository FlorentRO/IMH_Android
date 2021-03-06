/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.etudes.redugaspi.activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;

import java.io.IOException;
import java.util.Calendar;

import fr.etudes.redugaspi.R;
import fr.etudes.redugaspi.barcodedetection.BarcodeProcessor;
import fr.etudes.redugaspi.barcodedetection.BarcodeResultFragment;
import fr.etudes.redugaspi.camera.CameraSource;
import fr.etudes.redugaspi.camera.CameraSourcePreview;
import fr.etudes.redugaspi.camera.GraphicOverlay;
import fr.etudes.redugaspi.camera.WorkflowModel;
import fr.etudes.redugaspi.camera.WorkflowModel.WorkflowState;
import fr.etudes.redugaspi.databases.Database;
import fr.etudes.redugaspi.models.Product;

/** Demonstrates the barcode scanning workflow using camera preview. */
public class LiveBarcodeScanningActivity extends AppCompatActivity implements OnClickListener {


  private static final String TAG = "LiveBarcodeActivity";
  private CameraSource cameraSource;
  private CameraSourcePreview preview;
  private GraphicOverlay graphicOverlay;
  private View flashButton;
  private TextView promptChip;
  private AnimatorSet promptChipAnimator;
  private WorkflowModel workflowModel;
  private WorkflowState currentWorkflowState;

  @SuppressLint("ResourceType")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_live_barcode);
    preview = findViewById(R.id.camera_preview);
    graphicOverlay = findViewById(R.id.camera_preview_graphic_overlay);
    graphicOverlay.setOnClickListener(this);
    cameraSource = new CameraSource(graphicOverlay);

    promptChip = findViewById(R.id.bottom_prompt_chip);
    promptChipAnimator =
            (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.bottom_prompt_chip_enter);
    promptChipAnimator.setTarget(promptChip);

    findViewById(R.id.close_button).setOnClickListener(this);
    flashButton = findViewById(R.id.flash_button);
    flashButton.setOnClickListener(this);

    int day, month, year;
    Intent intent = getIntent();
    Calendar c = Calendar.getInstance();
    if (intent != null) {
        day = intent.getIntExtra("day", c.get(Calendar.DATE));
        month = intent.getIntExtra("month", c.get(Calendar.MONTH));
        year = intent.getIntExtra("year", c.get(Calendar.YEAR));
    } else {
      day = c.get(Calendar.DATE);
      month = c.get(Calendar.MONTH);
      year = c.get(Calendar.YEAR);
    }
    setUpWorkflowModel(day, month, year);
  }

  @Override
  protected void onResume() {
    super.onResume();

    workflowModel.markCameraFrozen();
    currentWorkflowState = WorkflowModel.WorkflowState.NOT_STARTED;
    cameraSource.setFrameProcessor(new BarcodeProcessor(graphicOverlay, workflowModel));
    workflowModel.setWorkflowState(WorkflowModel.WorkflowState.DETECTING);
  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    BarcodeResultFragment.dismiss(getSupportFragmentManager());
  }

  @Override
  protected void onPause() {
    super.onPause();
    currentWorkflowState = WorkflowModel.WorkflowState.NOT_STARTED;
    stopCameraPreview();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (cameraSource != null) {
      cameraSource.release();
      cameraSource = null;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onClick(View view) {
    int id = view.getId();
    if (id == R.id.close_button) {
      onBackPressed();

    } else if (id == R.id.flash_button) {
      if (flashButton.isSelected()) {
        flashButton.setSelected(false);
        cameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_OFF);
      } else {
        flashButton.setSelected(true);
        cameraSource.updateFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      }

    }
  }

  private void startCameraPreview() {
    if (!workflowModel.isCameraLive() && cameraSource != null) {
      try {
        workflowModel.markCameraLive();
        preview.start(cameraSource);
      } catch (IOException e) {
        Log.e(TAG, "Failed to start camera preview!", e);
        cameraSource.release();
        cameraSource = null;
      }
    }
  }

  private void stopCameraPreview() {
    if (workflowModel.isCameraLive()) {
      workflowModel.markCameraFrozen();
      flashButton.setSelected(false);
      preview.stop();
    }
  }

  private void setUpWorkflowModel(int day, int month, int year) {
    workflowModel = ViewModelProviders.of(this).get(WorkflowModel.class);

    // Observes the workflow state changes, if happens, update the overlay view indicators and
    // camera preview state.
    workflowModel.workflowState.observe(
            this,
            workflowState -> {
              if (workflowState == null || Objects.equal(currentWorkflowState, workflowState)) {
                return;
              }

              currentWorkflowState = workflowState;
              Log.d(TAG, "Current workflow state: " + currentWorkflowState.name());

              boolean wasPromptChipGone = (promptChip.getVisibility() == View.GONE);

              switch (workflowState) {
                case DETECTING:
                  promptChip.setVisibility(View.VISIBLE);
                  promptChip.setText(R.string.prompt_point_at_a_barcode);
                  Toast.makeText(LiveBarcodeScanningActivity.this,  R.string.prompt_point_at_a_barcode, Toast.LENGTH_LONG).show();
                  startCameraPreview();
                  break;
                case CONFIRMING:
                  promptChip.setVisibility(View.VISIBLE);
                  promptChip.setText(R.string.prompt_move_camera_closer);
                  Toast.makeText(LiveBarcodeScanningActivity.this,  R.string.prompt_move_camera_closer, Toast.LENGTH_LONG).show();
                  startCameraPreview();
                  break;
                case SEARCHING:
                  promptChip.setVisibility(View.VISIBLE);
                  promptChip.setText(R.string.prompt_searching);
                  Toast.makeText(LiveBarcodeScanningActivity.this,  R.string.prompt_searching, Toast.LENGTH_LONG).show();
                  stopCameraPreview();
                  break;
                case DETECTED:
                case SEARCHED:
                  promptChip.setVisibility(View.GONE);
                  stopCameraPreview();
                  break;
                default:
                  promptChip.setVisibility(View.GONE);
                  break;
              }

              boolean shouldPlayPromptChipEnteringAnimation =
                      wasPromptChipGone && (promptChip.getVisibility() == View.VISIBLE);
              if (shouldPlayPromptChipEnteringAnimation && !promptChipAnimator.isRunning()) {
                promptChipAnimator.start();
              }
            });
    workflowModel.detectedBarcode.observe(
            this,
            barcode -> {
              if (barcode != null) {
                Product newProduct = new Product(barcode.getRawValue(), 1, day, month, year);
                Product match = Database.getProducts().getFirst(x->x.equals(newProduct));
                if (match != null) {
                  newProduct.setQuantity(match.getQuantity()+1);
                  Database.getProducts().remove(match);
                  Database.getProducts().add(newProduct);
                } else {
                  Database.getProducts().add(newProduct);
                }
                finish();
              }
            });
  }
}
