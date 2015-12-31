package com.silicons.android.uploader.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.silicons.android.uploader.utils.LogUtils;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class PrefStore {
    public static final String TAG = LogUtils.makeLogTag(PrefStore.class);

    ///////////////////////////////////////////////////
    ///////////////  PREFERENCE KEY   /////////////////
    /**
     * preference key that mark this is first run or not
     */
    public static final String PREF_IS_FIRST_RUN = "is_first_run";

    /**
     * preference key that store flick token of flickr
     */
    public static final String PREF_FLICKR_TOKEN = "pref_flick_token";

    /**
     * preference key that store flick token of flickr
     */
    public static final String PREF_FLICKR_SECRET = "pref_flick_secret";

    /**
     * preference key that store flick token of flickr
     */
    public static final String PREF_FLICKR_USER_ID = "pref_flick_user_id";

    /**
     * preference key that store flick token of flickr
     */
    public static final String PREF_FLICKR_USER_NAME= "pref_flick_user_name";


    ///////////////////////////////////////////////////////////////
    /////////////////   DEFAULT VALUE   ///////////////////////////
    /**
     * Default value for {@link PrefStore#PREF_IS_FIRST_RUN}
     */
    private static final boolean DEFAULT_FIRST_RUN = true;

    /**
     * Default value for {@link PrefStore#PREF_FLICKR_TOKEN}
     */
    private static final String DEFAULT_FLICKR_TOKEN = null;

    /**
     * Default value for {@link PrefStore#PREF_FLICKR_SECRET}
     */
    private static final String DEFAULT_FLICKR_SECRET = null;

    /**
     * Default value for {@link PrefStore#PREF_FLICKR_USER_ID}
     */
    private static final String DEFAULT_FLICKR_USER_ID = null;

    /**
     * Default value for {@link PrefStore#PREF_FLICKR_USER_NAME}
     */
    private static final String DEFAULT_FLICKR_USER_NAME = null;


    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTER //////////////////////////////
    public static SharedPreferences getSharedPreferencesWithContext(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(UploaderApplication.getAppContext());
    }

    public static String getFlickrToken() {
        return getSharedPreferences().getString(PREF_FLICKR_TOKEN, DEFAULT_FLICKR_TOKEN);
    }

    public static String getFlickTokenSecret() {
        return getSharedPreferences().getString(PREF_FLICKR_SECRET, DEFAULT_FLICKR_SECRET);
    }

    public static String getFlickrUserId() {
        return getSharedPreferences().getString(PREF_FLICKR_USER_ID, DEFAULT_FLICKR_USER_ID);
    }

    public static String getPrefFlickrUserName() {
        return getSharedPreferences().getString(PREF_FLICKR_USER_NAME, DEFAULT_FLICKR_USER_NAME);
    }

    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  SETTER //////////////////////////////

    public static void setDeployedApp() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_FIRST_RUN, false);
        editor.commit();
    }

    public static void setFlickrToken(String flickrToken) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_FLICKR_TOKEN, flickrToken);
        editor.commit();
    }

    public static void setFlickrSecret(String flickrSecret) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_FLICKR_SECRET, flickrSecret);
        editor.commit();
    }

    public static void setFlickrUserId(String flickrUserId) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_FLICKR_USER_ID, flickrUserId);
        editor.commit();
    }

    public static void setFlickrUserName(String flickrUserName) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_FLICKR_USER_NAME, flickrUserName);
        editor.commit();
    }


    //////////////////////////////////////////////////////////////////////
    ///////////////////// QUERY DATA EXIST ///////////////////////////////
    public static boolean isFirstRun() {
        return getSharedPreferences().getBoolean(PREF_IS_FIRST_RUN, DEFAULT_FIRST_RUN);
    }

    public static boolean isLogin() {
        return (getFlickrUserId() != null);
    }
}
