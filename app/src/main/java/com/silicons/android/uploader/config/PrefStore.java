package com.silicons.android.uploader.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.silicons.android.uploader.config.AppConstant.ImageProviderMode;
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
     * preference key for using mobile network or not
     */
    public static final String PREF_IS_MOBILE_NETWORK = "pref_mobile_network";

    /**
     * preference key for deleting privacy data before log out or not
     */
    public static final String PREF_PRIVACY_DATA = "pref_privacy_data";

    /**
     * preference key for choosing sort type for uploading photos
     */
    public static final String PREF_UPLOADED_SORT_TYPE = "pref_uploaded_sort_type";

    /**
     * preference key for choosing sort type for failed upload photos
     */
    public static final String PREF_FAILED_SORT_TYPE = "pref_failed_sort_type";


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

    /**
     * preference key for getting current app upload provider mode
     */
    public static final String PREF_PROVIDER_MODE= "pref_provider_mode";


    ///////////////////////////////////////////////////////////////
    /////////////////   DEFAULT VALUE   ///////////////////////////
    /**
     * Default value for {@link PrefStore#PREF_IS_FIRST_RUN}
     */
    private static final boolean DEFAULT_FIRST_RUN = true;

    /**
     * Default value for {@link PrefStore#PREF_UPLOADED_SORT_TYPE}
     */
    private static final int DEFAULT_UPLOAD_SORT_TYPE = 0;

    /**
     * Default value for {@link PrefStore#PREF_FAILED_SORT_TYPE}
     */
    private static final int DEFAULT_FAIL_SORT_TYPE = 0;

    /**
     * Default value for {@link PrefStore#PREF_IS_MOBILE_NETWORK}
     */
    private static final boolean DEFAULT_MOBILE_NETWORK = false;

    /**
     * Default value for {@link PrefStore#PREF_PRIVACY_DATA}
     */
    private static final boolean DEFAULT_PRIVACY_DATA = true;

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

    /**
     * Default value for {@link PrefStore#PREF_PROVIDER_MODE}
     */
    private static final String DEFAULT_PROVIDER_MODE = ImageProviderMode.FLICKR;

    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  GETTER //////////////////////////////
    public static SharedPreferences getSharedPreferencesWithContext(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(UploaderApplication.getAppContext());
    }

    public static boolean getIsMobileNetwork() {
        return getSharedPreferences().getBoolean(PREF_IS_MOBILE_NETWORK, DEFAULT_MOBILE_NETWORK);
    }

    public static boolean getIsPrivacy() {
        return getSharedPreferences().getBoolean(PREF_PRIVACY_DATA, DEFAULT_PRIVACY_DATA);
    }

    public static int getUploadSortType() {
        return getSharedPreferences().getInt(PREF_UPLOADED_SORT_TYPE, DEFAULT_UPLOAD_SORT_TYPE);
    }

    public static int getFailSortType() {
        return getSharedPreferences().getInt(PREF_FAILED_SORT_TYPE, DEFAULT_FAIL_SORT_TYPE);
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

    public static String getFlickrUserName() {
        return getSharedPreferences().getString(PREF_FLICKR_USER_NAME, DEFAULT_FLICKR_USER_NAME);
    }

    public static String getProviderMode() {
        return getSharedPreferences().getString(PREF_PROVIDER_MODE, DEFAULT_PROVIDER_MODE);
    }

    ////////////////////////////////////////////////////////////////////
    /////////////////////////////  SETTER //////////////////////////////

    public static void setDeployedApp() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_FIRST_RUN, false);
        editor.commit();
    }

    public static void setMobileNetwork(boolean state) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_IS_MOBILE_NETWORK, state);
        editor.commit();
    }

    public static void setPrivacyData(boolean state) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PREF_PRIVACY_DATA, state);
        editor.commit();
    }

    public static void setUploadSortType(int type) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_UPLOADED_SORT_TYPE, type);
        editor.commit();
    }

    public static void setFailSortType(int type) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(PREF_FAILED_SORT_TYPE, type);
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

    public static void setProviderMode(String providerMode) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREF_PROVIDER_MODE, providerMode);
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
