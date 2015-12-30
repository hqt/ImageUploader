package com.silicons.android.uploader.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.test.TestInterface;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.utils.DialogUtils;
import com.silicons.android.uploader.utils.FileUtils;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

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
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    // store photo path from camera
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        Log.e("hqthao", "fucking");

        // set toolbar option
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_camera);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        photoUploadButton = (FloatingActionButton) findViewById(R.id.fab_photos);
        cameraUploadButton = (FloatingActionButton) findViewById(R.id.fab_camera);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // event for selecting photo from library
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

        // event for taking screenshot from camera
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

        // event for select item in navigation drawer menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_setting:
                        //intent = new Intent(MainActivity.this, SettingActivity.class);
                        //startActivity(intent);
                        return true;
                    case R.id.navigation_item_logout:
                        //intent = new Intent(MainActivity.this,DeveloperSettingActivity.class);
                        //startActivity(intent);
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
            if (currentPhotoPath != null) {
                Log.e(TAG, currentPhotoPath);
                Intent intent = new Intent(this, UploaderActivity.class);
                intent.putExtra("camera_photo_path", currentPhotoPath);
                startActivity(intent);
                currentPhotoPath = null;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
