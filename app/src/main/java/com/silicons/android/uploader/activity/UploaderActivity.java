package com.silicons.android.uploader.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.service.UploadingService;
import com.silicons.android.uploader.task.flickr.PhotoUploadTask;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.widgets.TouchImageView;

import java.io.File;
import java.io.IOException;

/**
 * preview and upload image screen
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class UploaderActivity extends AppCompatActivity {

    private TouchImageView mImageView;
    private ImageButton mUploadButton;

    // uri for image
    private Uri mUri;

    // file name for image
    private String mFileName;

    // bitmap for assign to ImageView. keep this object for uploading later
    private Bitmap mBitmap;

    // PhotoItem. used for persistence to database
    private PhotoItem mPhotoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mImageView = (TouchImageView) findViewById(R.id.image_view);
        mUploadButton = (ImageButton) findViewById(R.id.btn_upload);

        Bundle extras = getIntent().getExtras();
        String uriStr = extras.getString("uri_photo_gallery");
        String cameraPhotoPath = extras.getString("photo_camera_path");
        if (uriStr != null) {
            mUri = Uri.parse(uriStr);
            parseImageFromUri(mUri);
        } else if (cameraPhotoPath != null) {
            parseImageFromPath(cameraPhotoPath);
        }

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //mBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

                /*String extension = FileUtils.getExtension(media);
                if (unsupportedExtensions.contains(extension)) {
                    throw new UploadException("Unsupported extension: " + extension, false);
                }
                String uri = media.getPath();
                File file = new File(uri);
                if (!file.exists()) {
                    throw new UploadException("File no longer exists: " + file.getAbsolutePath(), false);
                }
                if (file.length() <= 10) {
                    throw new UploadException("File is empty: " + file.getAbsolutePath(), false);
                }
                if (file.length() > 1024 * 1024 * 1024L) {
                    throw new UploadException("File too big: " + file.getAbsolutePath(), false);
                }*/


                // write to database first
                mPhotoItem.setStatus(AppConstant.PhotoStatus.QUEUED);
                PhotoItemDAL.insertOrUpdatePhoto(mPhotoItem);

                // trigger intent service for uploading. uploading photos will put into queue
                Intent intent = new Intent(getApplicationContext(), UploadingService.class);
                intent.putExtra("photo", mPhotoItem);
                startService(intent);

                // go back previous activity
                finish();

            }
        });
    }

    private void parseImageFromUri(Uri uri) {
        try {
            mPhotoItem = new PhotoItem();

            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            String filePath = FileUtils.getRealPathFromURI(uri);
            mFileName = new File(filePath).getName();

            // create information for photo item
            mPhotoItem.setPath(filePath);
            mPhotoItem.setSize(mBitmap.getByteCount());
            mPhotoItem.setFlickrTitle(mFileName);
            Log.e("hqthao",toString(mPhotoItem));

            mImageView.setImageBitmap(mBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseImageFromPath(String path) {
        mPhotoItem = new PhotoItem();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmap = BitmapFactory.decodeFile(path, bmOptions);
        mFileName = new File(path).getName();
        Log.e("hqthao", "file name: " + mFileName);

        // create information for photo item
        mPhotoItem.setPath(path);
        mPhotoItem.setSize(mBitmap.getByteCount());
        mPhotoItem.setFlickrTitle(mFileName);
        Log.e("hqthao",toString(mPhotoItem));

        mImageView.setImageBitmap(mBitmap);
    }

    public String toString(PhotoItem photoItem) {
        return "PhotoItem{" +
                "flickrId='" + photoItem.getFlickrId() + '\'' +
                ", id=" + photoItem.getId() +
                ", flickrTitle='" + photoItem.getFlickrTitle() + '\'' +
                ", path='" + photoItem.getPath() + '\'' +
                ", size='" + photoItem.getSize() + "\'" +
                '}';
    }
}
