package com.silicons.android.uploader.widgets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.silicons.android.uploader.task.flickr.FlickrImageDownloadTask;

import java.lang.ref.WeakReference;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<AsyncTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         AsyncTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
                new WeakReference<AsyncTask>(bitmapWorkerTask);
    }

    public AsyncTask getBitmapWorkerTask() {
        return bitmapWorkerTaskReference.get();
    }
}
