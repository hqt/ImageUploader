package com.silicons.android.uploader.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.AppConstant;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.silicons.android.uploader.config.AppConstant.*;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class ImageListActivity extends AppCompatActivity {

    private FloatingActionButton photoUploadButton;
    private FloatingActionButton cameraUploadButton;

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
                }
            }
        });

        cameraUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }


                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, IntentCode.TAKE_CAMERA_INTENT);
                }
            }
        });
    }

    String mCurrentPhotoPath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == IntentCode.PICK_PHOTO_INTENT) && (resultCode == Activity.RESULT_OK)) {
            if (data == null) {
                //Display an error
                return;
            }
            // InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
            // Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }  else if ((requestCode == IntentCode.TAKE_CAMERA_INTENT) && (resultCode == Activity.RESULT_OK)) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
        }
    }



    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }




}
