package com.silicons.android.uploader.action.uploader;

import android.content.Intent;
import android.widget.Toast;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.service.FlickrUploadingService;
import com.silicons.android.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.utils.NetworkUtils;

import java.io.File;

import static com.silicons.android.uploader.config.AppConstant.FLICKR_SUPPORTED_EXTENSIONS;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class FlickrUploaderAction extends UploaderAction {

    @Override
    public void upload(PhotoItem photoItem) {
        // validate image before upload
        String errorMessage = null;
        String extension = FileUtils.getExtension(photoItem);
        String uri = photoItem.getPath();
        File file = new File(uri);
        if (!FLICKR_SUPPORTED_EXTENSIONS.contains(extension)) {
            errorMessage = "This file extension is not supported";
        } else if (!file.exists()) {
            errorMessage = "File no longer exist";
        } else if (file.length() <= 10) {
            errorMessage = "File is empty";
        } else if (file.length() > 1024 * 1024 * 1024L) {
            errorMessage = "File too big";
        } else if (!NetworkUtils.isNetworkAvailable()) {
            errorMessage = "Network not available now. Please try again later";
        }

        if (errorMessage != null) {
            Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        // write to database first
        photoItem.setStatus(AppConstant.PhotoStatus.QUEUED);
        PhotoItemDAL.insertOrUpdatePhoto(photoItem);

        // trigger intent service for uploading. uploading photos will put into queue
        Intent intent = new Intent(mActivity.getApplicationContext(), FlickrUploadingService.class);
        intent.putExtra("photo", photoItem);
        mActivity.startService(intent);

        // go back previous activity
        mActivity.finish();

    }


}
