package com.silicons.android.uploader.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.task.flickr.ImageDownloadTask;
import com.silicons.android.uploader.widgets.TouchImageView;

import java.io.File;

/** View detail uploaded photo
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class PhotoViewerActivity extends AppCompatActivity {

    // the Photo id from intent extra
    private String mPhotoId;

    // local path of photo get from intent extra
    private String mPath;

    // the bitmap of big photos
    private Bitmap mPhotoBitmap;

    private TouchImageView mImageView;

    String samplePhotoId = "24080343646";

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Preview Image");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImageView = (TouchImageView) findViewById(R.id.image_view);

        Bundle extras = getIntent().getExtras();
        String photoId = extras.getString("photo_id");
        if (photoId != null) {
            ImageDownloadTask task = new ImageDownloadTask(this, mImageView, photoId,
                    AppConstant.PhotoType.PHOTO_ID_LARGE, true);
            task.execute();
        } else {
            Toast.makeText(this, "No photo for display.", Toast.LENGTH_SHORT).show();
        }

    }
}
