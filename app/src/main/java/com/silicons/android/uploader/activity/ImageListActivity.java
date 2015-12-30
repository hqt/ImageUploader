package com.silicons.android.uploader.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.silicons.android.uploader.R;

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

            }
        });

        cameraUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
