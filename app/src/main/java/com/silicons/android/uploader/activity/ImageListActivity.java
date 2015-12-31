package com.silicons.android.uploader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.uploader.authen.FlickrOath;
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

    private FloatingActionButton mPhotoUploadButton;
    private FloatingActionButton mCameraUploadButton;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    // store photo path from camera
    private String mCurrentPhotoPath;

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

        mPhotoUploadButton = (FloatingActionButton) findViewById(R.id.fab_photos);
        mCameraUploadButton = (FloatingActionButton) findViewById(R.id.fab_camera);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        // event for selecting photo from library
        mPhotoUploadButton.setOnClickListener(new View.OnClickListener() {
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

        // event for taking screenshot from camera
        mCameraUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        Pair<File, String> result = FileUtils.createImageFile();
                        photoFile = result.first;
                        mCurrentPhotoPath = result.second;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        mCurrentPhotoPath = null;
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

        // event for select item in navigation drawer menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent;
                switch (menuItem.getItemId()) {
                    // when user press setting button. open setting activity
                    case R.id.navigation_item_setting:
                        return true;

                    // when user press logout. confirm dialog and clear all data
                    case R.id.navigation_item_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(ImageListActivity.this);
                        builder.setMessage("Data will be swipe after logout. Are you sure ... ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        FlickrOath.logout();
                                        Intent intent = new Intent(ImageListActivity.this,
                                                LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;

                    default:
                        return true;
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
            if (mCurrentPhotoPath != null) {
                Log.e(TAG, mCurrentPhotoPath);
                Intent intent = new Intent(this, UploaderActivity.class);
                intent.putExtra("camera_photo_path", mCurrentPhotoPath);
                startActivity(intent);
                mCurrentPhotoPath = null;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
