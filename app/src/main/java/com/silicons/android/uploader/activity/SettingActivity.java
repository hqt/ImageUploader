package com.silicons.android.uploader.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.PrefStore;


/** Setting activity for configure application
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class SettingActivity extends AppCompatActivity {

    private Spinner mUploadedPhotoSpinner;
    private Spinner mFailedPhotoSpinner;
    private CheckBox mNetworkCheckBox;
    private CheckBox mPrivacyCheckBox;
    private CheckBox mMemoryCacheCheckBox;
    private CheckBox mFileCacheCheckBox;
    private Button mOkButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Setting");

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUploadedPhotoSpinner = (Spinner) findViewById(R.id.uploaded_sort_spinner);
        mFailedPhotoSpinner = (Spinner) findViewById(R.id.failed_sort_spinner);
        mNetworkCheckBox = (CheckBox) findViewById(R.id.networkCheckBox);
        mPrivacyCheckBox = (CheckBox) findViewById(R.id.privacyCheckBox);
        mMemoryCacheCheckBox = (CheckBox) findViewById(R.id.memoryCachedCheckbox);
        mFileCacheCheckBox = (CheckBox) findViewById(R.id.fileCachedCheckbox);

        // set spinner
        mUploadedPhotoSpinner.setSelection(PrefStore.getUploadSortType());
        mFailedPhotoSpinner.setSelection(PrefStore.getFailSortType());

        // set checkbox
        mNetworkCheckBox.setChecked(PrefStore.getIsMobileNetwork());
        mPrivacyCheckBox.setChecked(PrefStore.getIsPrivacy());
        mFileCacheCheckBox.setChecked(PrefStore.getAllowDiskCache());
        mMemoryCacheCheckBox.setChecked(PrefStore.getAllowMemoryCache());

        mOkButton = (Button) findViewById(R.id.btn_ok);
        mCancelButton = (Button) findViewById(R.id.btn_cancel);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefStore.setUploadSortType(mUploadedPhotoSpinner.getSelectedItemPosition());
                PrefStore.setFailSortType(mFailedPhotoSpinner.getSelectedItemPosition());
                PrefStore.setMobileNetwork(mNetworkCheckBox.isChecked());
                PrefStore.setPrivacyData(mPrivacyCheckBox.isChecked());
                PrefStore.setDiskCacheMode(mFileCacheCheckBox.isChecked());
                PrefStore.setMemoryCacheMode(mMemoryCacheCheckBox.isChecked());

                finish();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
