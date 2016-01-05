package com.silicons.android.uploader.widgets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.silicons.android.uploader.task.flickr.FlickrImageDownloadTask;

import java.lang.ref.WeakReference;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<FlickrImageDownloadTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         FlickrImageDownloadTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
                new WeakReference<FlickrImageDownloadTask>(bitmapWorkerTask);
    }

    public FlickrImageDownloadTask getBitmapWorkerTask() {
        return bitmapWorkerTaskReference.get();
    }
}
