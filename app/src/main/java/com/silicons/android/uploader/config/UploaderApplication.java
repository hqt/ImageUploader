package com.silicons.android.uploader.config;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.silicons.android.uploader.cache.DiskLruImageCache;
import com.silicons.android.uploader.cache.MemoryImageCache;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.service.UploadingService;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**Entry point of android application
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class UploaderApplication extends Application {
    public static Context mContext;

    private static DiskLruImageCache mImageDiskCache;

    private static MemoryImageCache mImageMemoryCache;

    /** this is just application context. Use this function carefully to avoid error */
    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        // build realm default configuration
        RealmConfiguration config = new RealmConfiguration.Builder(mContext).build();
        Realm.setDefaultConfiguration(config);

        // start uploading service
        //Intent intent = new Intent(mContext, UploadingService.class);
        //startService(intent);

        mImageDiskCache = new DiskLruImageCache(mContext, AppConstant.FileCache.FOLDER_NAME, AppConstant.FileCache.SYSTEM_SIZE,
                Bitmap.CompressFormat.JPEG, 100);

        mImageMemoryCache = new MemoryImageCache();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
    }

    public static DiskLruImageCache getImageDiskCache() {
        return mImageDiskCache;
    }

    public static MemoryImageCache getImageMemoryCache() {
        return mImageMemoryCache;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
