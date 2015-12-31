package com.silicons.android.uploader.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.task.PhotoUploadTask;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.widgets.TouchImageView;

import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * preview and upload image screen
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class UploaderActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private ImageButton mUploadButton;

    // file name for image
    private String mFileName;

    // bitmap for assign to ImageView. keep this object for uploading later
    private Bitmap mBitmap;

    // meta data for image. based on from photo gallery or from camera
    private UploadMetaData mUploadMetaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mImageView = (TouchImageView) findViewById(R.id.image_view);
        mUploadButton = (ImageButton) findViewById(R.id.btn_upload);

        Bundle extras = getIntent().getExtras();
        String uriStr = extras.getString("uri_photo_gallery");
        String cameraPhotoPath = extras.getString("camera_photo_path");
        if (uriStr != null) {
            Uri uri = Uri.parse(uriStr);
            parseImageFromUri(uri);
        } else if (cameraPhotoPath != null) {
            parseImageFromPath(cameraPhotoPath);
        }

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] data = FileUtils.convertBitmaptoByte(mBitmap);
                PhotoUploadTask task = new PhotoUploadTask(UploaderActivity.this, mFileName, data, mUploadMetaData);
                task.execute();

            }
        });
    }

    private void parseImageFromUri(Uri uri) {
        try {
            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            //String filePath = FileUtils.getRealPathFromURI(uri);
            //setPic(filePath);

            mFileName = FileUtils.getImageNameFromUri(uri);
            mUploadMetaData = new UploadMetaData();
            mUploadMetaData.setDescription("Image from photo gallery");
            mUploadMetaData.setTitle("Image from photo gallery");

            mImageView.setImageBitmap(mBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseImageFromPath(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        mBitmap = BitmapFactory.decodeFile(path, bmOptions);

        mFileName = "Camera_Upload.jpg";
        mUploadMetaData = new UploadMetaData();
        mUploadMetaData.setDescription("Screenshot from camera");
        mUploadMetaData.setTitle("Screenshot from camera");

        mImageView.setImageBitmap(mBitmap);
    }

    private void setPic(String path) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        mBitmap = BitmapFactory.decodeFile(path, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(mBitmap);
    }
}
