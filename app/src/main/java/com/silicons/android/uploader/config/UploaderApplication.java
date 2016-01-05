package com.silicons.android.uploader.config;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.silicons.android.uploader.cache.DiskLruImageCache;
import com.silicons.android.uploader.cache.MemoryImageCache;
import com.silicons.android.uploader.manager.action.ActionManagerHandler;
import com.silicons.android.uploader.manager.action.IActionManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**Entry point of android application
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class UploaderApplication extends Application {
    public static Context mContext;

    private static DiskLruImageCache mImageDiskCache;

    private static MemoryImageCache mImageMemoryCache;

    private static IActionManager mActionManager;

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

        mImageDiskCache = new DiskLruImageCache(mContext, AppConstant.FileCache.FOLDER_NAME, AppConstant.FileCache.SYSTEM_SIZE,
                Bitmap.CompressFormat.JPEG, 100);

        mImageMemoryCache = new MemoryImageCache();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static DiskLruImageCache getImageDiskCache() {
        return mImageDiskCache;
    }

    public static MemoryImageCache getImageMemoryCache() {
        return mImageMemoryCache;
    }

    public static IActionManager getActionManager() {
        if (mActionManager == null) {
            mActionManager = ActionManagerHandler.getInstance(PrefStore.getProviderMode());
        }
        return mActionManager;
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
