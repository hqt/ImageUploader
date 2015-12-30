package com.silicons.android.uploader.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.utils.DialogUtils;
import com.silicons.android.uploader.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.silicons.android.uploader.config.AppConstant.*;
import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * activity for display all uploaded images
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class ImageListActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag(ImageListActivity.class);

    private FloatingActionButton photoUploadButton;
    private FloatingActionButton cameraUploadButton;


    // store photo path from camera
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        // set toolbar option
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_camera);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        photoUploadButton = (FloatingActionButton) findViewById(R.id.fab_photos);
        cameraUploadButton = (FloatingActionButton) findViewById(R.id.fab_camera);

        photoUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getPhotoIntent.setType("image/*");
                if (getPhotoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(getPhotoIntent, IntentCode.PICK_PHOTO_INTENT);
                } else {
                    DialogUtils.displayDialog(ImageListActivity.this,
                            "No photo viewer in your system");
                }
            }
        });

        cameraUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        Pair<File, String> result = FileUtils.createImageFile();
                        photoFile = result.first;
                        currentPhotoPath = result.second;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        currentPhotoPath = null;
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, IntentCode.TAKE_CAMERA_INTENT);
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot create file.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    DialogUtils.displayDialog(ImageListActivity.this,
                            "No camera application in your system");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getApplicationContext(), "Something is wrong. Please try again",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // processing for pick photo
        if (requestCode == IntentCode.PICK_PHOTO_INTENT) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Cannot choose photos. Please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // passing URI. instead of decode to bitmap for saving memory :)
            Uri uri = data.getData();
            Log.e(TAG, uri.toString());
            Intent intent = new Intent(this, UploaderActivity.class);
            intent.putExtra("uri_photo_gallery", uri.toString());
            startActivity(intent);
        }

        // processing for getting photo from camera
        else if (requestCode == IntentCode.TAKE_CAMERA_INTENT) {
            // passing camera screenshot path instead of decoded data for saving memory :)
            if (currentPhotoPath != null) {
                Log.e(TAG, currentPhotoPath);
                Intent intent = new Intent(this, UploaderActivity.class);
                intent.putExtra("camera_photo_path", currentPhotoPath);
                startActivity(intent);
                currentPhotoPath = null;
            }
        }
    }
}
