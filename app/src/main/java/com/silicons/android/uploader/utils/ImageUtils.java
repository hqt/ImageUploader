package com.silicons.android.uploader.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.silicons.android.uploader.task.flickr.ImageDownloadTask;
import com.silicons.android.uploader.widgets.AsyncDrawable;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class ImageUtils {


    public static boolean isNewImage(String taskId, ImageView imageView) {
        final ImageDownloadTask bitmapWorkerTask = getPhotoDownloadTask(imageView);

        if (bitmapWorkerTask != null) {
            final String currentImageViewTaskId = bitmapWorkerTask.getTaskId();
            // If taskId for this image is not set. or different from current
            if ((currentImageViewTaskId == null)
                    || (!currentImageViewTaskId.equals(taskId))) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
                // create new task for this ImageView
                return true;
            } else {
                // This is same task and in progress. do nothing :)
                return false;
            }
        }

        // No task currently associated with the ImageView. allow new task
        return true;
    }

    /**
     * Get Current task working on this image view
     */
    public static ImageDownloadTask getPhotoDownloadTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
