package com.silicons.android.uploader.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.AppConstant.PhotoStatus;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.model.FailedPhotoNotify;
import com.silicons.android.uploader.model.QueuedPhotoNotify;
import com.silicons.android.uploader.model.UploadedPhotoNotify;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.FileUtils;
import com.silicons.android.uploader.utils.NetworkUtils;
import com.silicons.android.uploader.utils.NotificationUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import de.greenrobot.event.EventBus;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Uploading Image Service. Using for upload to Flickr.
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class UploadingService extends IntentService {

    private static final String TAG = makeLogTag(UploadingService.class);

    // EventBus for sending data back to activity. Faster than using BroastCastReceiver
    private EventBus mBus;

    private Context mContext;

    // unique notification id for this download turn. so system will not flushed by notification
    private int mNotificationId;

    // PhotoItem need to download
    private PhotoItem mPhoto;

    // using for processing some UI task such as display Toast.
    // Because IntentService runs from different thread
    private Handler mHandler;

    public UploadingService() {
        super(UploadingService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        mNotificationId = (int) new Date().getTime();
        mBus = EventBus.getDefault();
        mHandler = new Handler();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        mPhoto = (PhotoItem) intent.getSerializableExtra("photo");
        Log.e(TAG, "HandleIntent for photo " + mPhoto.getFlickrTitle());

        NotificationUtils.run(mContext, mNotificationId,
                "Download " + mPhoto.getFlickrTitle(), "In progress");
        displayToast("Downloading " + mPhoto.getFlickrTitle());

        uploadImageTask();
    }

    private void uploadImageTask() {
        Flickr flickr = FlickrHelper.getInstance()
                .getFlickrAuthed(PrefStore.getFlickrToken(), PrefStore.getFlickTokenSecret());

        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(mPhoto.getFlickrTitle());
        uploadMetaData.setContentType("1");
        uploadMetaData.setPublicFlag(true);

        File f = new File(mPhoto.getPath());
        FileInputStream fis = null;

        String imageId = null;
        String errorCode = null;
        try {
            fis = new FileInputStream(f);
            imageId =  flickr. getUploader().upload(mPhoto.getFlickrTitle(),
                    fis, uploadMetaData);
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = e.getMessage();
            Log.e(TAG, "Upload error:" + errorCode);
        }

        if (imageId != null) {
            NotificationUtils.run(mContext, mNotificationId,
                    "Download " + mPhoto.getFlickrTitle(), "Finish");
            displayToast("Finish download " + mPhoto.getFlickrTitle());
            mPhoto.setFlickrId(imageId);
            mPhoto.setStatus(PhotoStatus.UPLOADED);
            PhotoItemDAL.insertOrUpdatePhoto(mPhoto);
            mBus.post(new UploadedPhotoNotify(mPhoto));
        } else {
            NotificationUtils.run(mContext, mNotificationId,
                    "Download " + mPhoto.getFlickrTitle(), "Failed");
            displayToast("Download " + mPhoto.getFlickrTitle() + " fail.Error:" + errorCode);
            mPhoto.setStatus(PhotoStatus.FAILED);
            PhotoItemDAL.insertOrUpdatePhoto(mPhoto);
            mBus.post(new FailedPhotoNotify(mPhoto));
        }
    }

    private void displayToast(String text) {
        mHandler.post(new DisplayToast(this, text));
    }

    public class DisplayToast implements Runnable {
        private final Context mContext;
        String mText;

        public DisplayToast(Context mContext, String text){
            this.mContext = mContext;
            mText = text;
        }

        public void run(){
            Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show();
        }
    }
}
