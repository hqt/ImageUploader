package com.silicons.android.uploader.task.flickr;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.PhotosInterface;
import com.googlecode.flickrjandroid.photos.SearchParameters;
import com.silicons.android.uploader.config.AppConstant.PhotoType;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;
import com.silicons.android.uploader.utils.ImageUtils;
import com.silicons.android.uploader.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
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

    private WeakReference<ImageView> mImageReference = null;

    private ProgressDialog mProgressDialog;

    private Context mContext;

    // flickr id server
    private String mPhotoId;

    // photo type for downloading
    private int mPhotoType;

    // should display progress to ui or not
    private boolean mIsDisplayProgress;

    public ImageDownloadTask(Context context, ImageView imageView, String photoId,
                             int photoType, boolean displayProgress) {
        this.mContext = context;
        this.mImageReference = new WeakReference<ImageView>(imageView);
        this.mPhotoId = photoId;
        this.mPhotoType = photoType;
        this.mIsDisplayProgress = displayProgress;
    }

    // each task assign with each flickr server photo id. and this unique
    public String getTaskId() {
        return mPhotoId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!mIsDisplayProgress) {
            return;
        }
        mProgressDialog = ProgressDialog.show(mContext, "Downloading", "Downloading photo from Flickr ...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                ImageDownloadTask.this.cancel(true);
            }
        });
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Flickr flikcr = FlickrHelper.getInstance().getFlickr();
        PhotosInterface photoService = flikcr.getPhotosInterface();

        if (!NetworkUtils.isNetworkAvailable()) {
            return null;
        }

        Photo photo = null;
        try {
            photo = photoService.getPhoto(mPhotoId, PrefStore.getFlickTokenSecret());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        /*SearchParameters searchParameters = new SearchParameters();
        searchParameters.setText("điện thoại di động");
        try {
            PhotoList list = flikcr.getPhotosInterface().search(searchParameters, 100, 50);
            for (int i = 0; i < list.size(); i++) {
                Log.e(TAG, "id: " + list.get(i).getId() + "-" +
                      //  list.get(i).getDatePosted().toString() + "-" +
                        list.get(i).getTitle());
            }

            Log.e(TAG, "Size: " + list.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Log.e(TAG, "URL: " + url);

        byte[] data = NetworkUtils.downloadImageFile(url);
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

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        // saving to cache. although miss or not
        if (PrefStore.getAllowDiskCache()) {
            UploaderApplication.getImageDiskCache().put(mPhotoId, result);
        }

        if (PrefStore.getAllowMemoryCache()) {
            UploaderApplication.getImageMemoryCache().addBitmapToMemoryCache(mPhotoId, result);
        }

        if (mImageReference != null && result != null) {
            final ImageView imageView = mImageReference.get();

            // in mode display progress. just has one task assign
            if (mIsDisplayProgress) {
                imageView.setImageBitmap(result);
                return;
            }

            // in multi-mode. maybe there are many asynctask run parallel
            // get the latest task of this image view for checking
            ImageDownloadTask imageDownloadTask = ImageUtils.getPhotoDownloadTask(imageView);
            if ((this == imageDownloadTask) && (imageView != null)) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
