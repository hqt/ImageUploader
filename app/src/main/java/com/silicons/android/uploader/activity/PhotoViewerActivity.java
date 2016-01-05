package com.silicons.android.uploader.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.action.viewer.ViewerAction;
import com.silicons.android.uploader.config.ActionConstant;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.widgets.TouchImageView;

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

    private ViewerAction mAction;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        mAction = (ViewerAction) UploaderApplication.getActionManager()
                .getAction(ActionConstant.Flickr.VIEWER_ACTION);
        mAction.setActivity(this);


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
            mAction.download(mImageView, photoId);
        } else {
            Toast.makeText(this, "No photo for display.", Toast.LENGTH_SHORT).show();
        }
    }
}
