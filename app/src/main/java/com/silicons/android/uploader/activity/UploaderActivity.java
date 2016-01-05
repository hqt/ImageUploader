package com.silicons.android.uploader.activity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.action.uploader.UploaderAction;
import com.silicons.android.uploader.config.ActionConstant;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.utils.ImageUtils;
import com.silicons.android.uploader.widgets.TouchImageView;

import java.io.File;

import static com.silicons.android.uploader.config.AppConstant.PhotoType;

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

    private UploaderAction mUploaderAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mUploaderAction = (UploaderAction) UploaderApplication.getActionManager()
                .getAction(ActionConstant.Flickr.UPLOADING_ACTION);
        mUploaderAction.setActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        toolbar.setTitle("Preview Image");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mImageView = (TouchImageView) findViewById(R.id.image_view);
        mUploadButton = (ImageButton) findViewById(R.id.btn_upload);

        Bundle extras = getIntent().getExtras();
        String uriStr = extras.getString("uri_photo_gallery");
        if (uriStr != null) {
            mUri = Uri.parse(uriStr);
            parseImageFromUri(mUri);
        } else {
            String filePath = extras.getString("file_path");
            parseImageFromCameraUri(filePath);
        }

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUploaderAction.upload(mPhotoItem);
            }
        });
    }

    private void parseImageFromUri(Uri uri) {
        mPhotoItem = new PhotoItem();

        String filePath = FileUtils.getRealPathFromURI(uri);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        mBitmap = ImageUtils.decodeSampledBitmapFromPath(filePath, width, height);
        mFileName = new File(filePath).getName();

        // create information for photo item
        mPhotoItem.setPath(filePath);
        mPhotoItem.setSize(mBitmap.getByteCount());
        mPhotoItem.setFlickrTitle(mFileName);
        mPhotoItem.setType(PhotoType.PICTURE);
        Log.e("hqthao", toString(mPhotoItem));

        mImageView.setImageBitmap(mBitmap);
    }

    private void parseImageFromCameraUri(String filePath) {
        mPhotoItem = new PhotoItem();

        try {
            File file = new File(filePath);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            mBitmap = ImageUtils.decodeSampledBitmapFromPath(filePath, width, height);
            mFileName = file.getName();

            // create information for photo item
            mPhotoItem.setPath(filePath);
            mPhotoItem.setSize(mBitmap.getByteCount());
            mPhotoItem.setFlickrTitle(mFileName);
            mPhotoItem.setType(PhotoType.CAMERA);
            Log.e("hqthao", toString(mPhotoItem));

            mImageView.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
