package com.silicons.android.uploader.config;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class UploaderApplication extends Application {
    public static Context mContext;

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

    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // MultiDex.install(this);
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
