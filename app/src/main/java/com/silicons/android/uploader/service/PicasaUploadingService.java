package com.silicons.android.uploader.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.NotificationUtils;

public class PicasaUploadingService extends IntentService {

    public PicasaUploadingService() {
        super("PicasaUploadingService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
       throw new NoSuchMethodError("this method need implement");
    }
}
