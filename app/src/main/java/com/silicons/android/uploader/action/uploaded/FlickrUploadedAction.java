package com.silicons.android.uploader.action.uploaded;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.task.flickr.FlickrImageDownloadTask;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class FlickrUploadedAction extends UploadedAction {

    @Override
    public AsyncTask<Void, Void, Bitmap> getTask(Context context, ImageView imageView, String photoId) {
        FlickrImageDownloadTask task = new FlickrImageDownloadTask(context, imageView,
                photoId, AppConstant.PhotoType.PHOTO_ID_SMALL, false);
        return task;
    }
}
