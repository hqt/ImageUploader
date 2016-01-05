package com.silicons.android.uploader.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.silicons.android.uploader.task.flickr.FlickrImageDownloadTask;
import com.silicons.android.uploader.widgets.AsyncDrawable;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class ImageUtils {


    public static boolean isNewImage(String taskId, ImageView imageView) {
        final FlickrImageDownloadTask bitmapWorkerTask = getPhotoDownloadTask(imageView);

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
    public static FlickrImageDownloadTask getPhotoDownloadTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
}
