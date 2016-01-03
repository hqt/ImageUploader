package com.silicons.android.uploader.widgets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.silicons.android.uploader.task.flickr.ImageDownloadTask;

import java.lang.ref.WeakReference;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<ImageDownloadTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
                         ImageDownloadTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference =
                new WeakReference<ImageDownloadTask>(bitmapWorkerTask);
    }

    public ImageDownloadTask getBitmapWorkerTask() {
        return bitmapWorkerTaskReference.get();
    }
}
