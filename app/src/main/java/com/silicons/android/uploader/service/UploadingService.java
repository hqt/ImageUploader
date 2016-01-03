package com.silicons.android.uploader.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;

import de.greenrobot.event.EventBus;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class UploadingService extends Service {

    private static final String TAG = makeLogTag(UploadingService.class);

    private EventBus mBus;

    private Context mContext;

    public UploadingService() {
        Log.e(TAG, "empty constructor's GPS Service has been called");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        mBus = EventBus.getDefault();
    }

    private Runnable uploadImageCallback = new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void uploadImageTask() {

    }
}
