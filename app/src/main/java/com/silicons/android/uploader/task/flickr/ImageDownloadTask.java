package com.silicons.android.uploader.task.flickr;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotosInterface;
import com.googlecode.flickrjandroid.photosets.Photoset;
import com.googlecode.flickrjandroid.photosets.PhotosetsInterface;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.AppConstant.PhotoType;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.utils.NetworkUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * Represents the image download task which takes an image url as the parameter,
 * after the download, set the bitmap to an associated ImageView.
 * Using WeakReference pattern because we don't know this image view will be GC in future
 * Created by Huynh Quang Thao on 1/2/16.
 */

public class ImageDownloadTask extends AsyncTask<Void, Void, Bitmap> {

    private static final String TAG = makeLogTag(ImageDownloadTask.class);

    private WeakReference<ImageView> imgRef = null;

    // flickr id server
    private String mPhotoId;

    // photo type for downloading
    private int mPhotoType;

    public ImageDownloadTask(ImageView imageView, String photoId, int photoType) {
        this.imgRef = new WeakReference<ImageView>(imageView);
        this.mPhotoId = photoId;
        this.mPhotoType = photoType;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Flickr flikcr = FlickrHelper.getInstance().getFlickr();
        PhotosInterface photoService = flikcr.getPhotosInterface();
        Photo photo = null;
        String url = null;

        switch (mPhotoType) {
            case PhotoType.PHOTO_ID_SMALL_SQUARE:
                url = photo.getSmallSquareUrl();
                break;
            case PhotoType.PHOTO_ID_SMALL:
                url = photo.getSmallUrl();
                break;
            case PhotoType.PHOTO_ID_MEDIUM:
                url = photo.getMediumUrl();
                break;
            case PhotoType.PHOTO_ID_LARGE:
                url = photo.getLargeUrl();
                break;
        }

        if (url == null) {
            return null;
        }

        byte[] data = NetworkUtils.downloadSoundFile(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        return bitmap;

    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.isCancelled()) {
            result = null;
            return;
        }

        // ImageCache.saveToCache(mUrl, result);

        if (imgRef != null) {
            ImageView imageView = imgRef.get();
            ImageDownloadTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
            // Change bitmap only if this process is still associated with it
            // Or if we don't use any bitmap to task association
            // (NO_DOWNLOADED_DRAWABLE mode)
            if (this == bitmapDownloaderTask && bitmapDownloaderTask != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

    private ImageDownloadTask getBitmapDownloaderTask(ImageView imageView) {
       /* if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }*/
        return null;
    }
}
