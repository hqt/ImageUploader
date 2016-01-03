package com.silicons.android.uploader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.adapter.PhotoPagerAdapter;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.fragment.FailUploadFragment;
import com.silicons.android.uploader.fragment.QueuePhotoFragment;
import com.silicons.android.uploader.fragment.UploadedPhotoFragment;
import com.silicons.android.uploader.uploader.authen.FlickrOath;
import com.silicons.android.uploader.utils.DialogUtils;
import com.silicons.android.uploader.utils.FileUtils;

import java.io.File;

import static com.silicons.android.uploader.config.AppConstant.*;
import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * activity for display all uploaded images
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class ImageListActivity extends AppCompatActivity
        implements UploadedPhotoFragment.OnFragmentInteractionListener,
        QueuePhotoFragment.OnFragmentInteractionListener,
        FailUploadFragment.OnFragmentInteractionListener {

    private static final String TAG = makeLogTag(ImageListActivity.class);

    private FloatingActionButton mPhotoUploadButton;
    private FloatingActionButton mCameraUploadButton;
    private FloatingActionsMenu mGeneralFloatingButton;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    // store photo path from camera
    private String mPhotoPath;

    // Uri for picture
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        // set toolbar option
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_three_dot);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mPhotoUploadButton = (FloatingActionButton) findViewById(R.id.fab_photos);
        mCameraUploadButton = (FloatingActionButton) findViewById(R.id.fab_camera);
        mGeneralFloatingButton = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        if (PrefStore.isFirstRun()) {
            Toast.makeText(this, "Temporary database will be created for demo.", Toast.LENGTH_LONG).show();
            PrefStore.setFirstRun(false);
        }

        // Set up tab view
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);

        // configure custom tab view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(mTabSelectedListener);

        // event for selecting photo from library
        mPhotoUploadButton.setOnClickListener(mPhotoSelectClickListener);

        // event for taking screenshot from camera
        mCameraUploadButton.setOnClickListener(mCameraClickListener);

        // event for select item in navigation drawer menu
        mNavigationView.setNavigationItemSelectedListener(mNavigationItemListener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode != Activity.RESULT_OK) && (resultCode != Activity.RESULT_CANCELED)) {
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
            mImageUri = data.getData();
            Log.e(TAG, "Photo: " + mImageUri.toString());
            Intent intent = new Intent(this, UploaderActivity.class);
            intent.putExtra("uri_photo_gallery", mImageUri.toString());
            startActivity(intent);
        }

        // processing for getting photo from camera
        else if (requestCode == IntentCode.TAKE_CAMERA_INTENT) {
            Log.e(TAG, "Camera: " + mPhotoPath);
            Intent intent = new Intent(this, UploaderActivity.class);
            intent.putExtra("photo_camera_path", mPhotoPath);
            startActivity(intent);
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


    //region Listener callback
    private View.OnClickListener mPhotoSelectClickListener = new View.OnClickListener() {
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
    };

    private View.OnClickListener mCameraClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photo = null;
                try {
                    // place where to store camera taken picture
                    photo = FileUtils.createTemporaryFile("picture", ".jpg");
                    mPhotoPath = photo.getPath();
                    //photo.delete();
                } catch (Exception e) {
                    Log.v(TAG, "Can't create file to take picture!");
                    Toast.makeText(getApplicationContext(), "Please check SD card. Image shot is impossible",
                            Toast.LENGTH_SHORT).show();
                }

                if (photo != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                    startActivityForResult(takePictureIntent, IntentCode.TAKE_CAMERA_INTENT);
                }
            } else {
                DialogUtils.displayDialog(ImageListActivity.this,
                        "No camera application in your system");
            }
        }
    };

    private NavigationView.OnNavigationItemSelectedListener mNavigationItemListener =
            new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            Intent intent;
            switch (menuItem.getItemId()) {
                // when user press setting button. open setting activity
                case R.id.navigation_item_setting:
                    intent = new Intent(ImageListActivity.this, SettingActivity.class);
                    startActivity(intent);
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
                                    PhotoItemDAL.deleteAllPhotos();
                                    PrefStore.setFirstRun(true) ;

                                    if (PrefStore.getIsPrivacy()) {
                                        PrefStore.setMobileNetwork(true);
                                        PrefStore.setPrivacyData(true);
                                        PrefStore.setFailSortType(0);
                                        PrefStore.setUploadSortType(0);
                                        PrefStore.setProviderMode(ImageProviderMode.FLICKR);
                                        UploaderApplication.getImageDiskCache().clearCache();
                                        UploaderApplication.getImageMemoryCache().clearCache();
                                    }

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
    };

    private TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };
    //endregion

}
