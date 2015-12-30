package com.silicons.android.uploader.uploader;

import android.os.Environment;

import com.googlecode.flickrjandroid.RequestContext;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.PrefStore;

import java.io.File;

/**
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class FlickrOath {

    public static OAuth loadSavedOAuth() {
        String userId = PrefStore.getFlickrUserId();
        String userName = PrefStore.getPrefFlickrUserName();
        String token = PrefStore.getFlickrToken();
        String tokenSecret = PrefStore.getFlickTokenSecret();

        if (userId == null || token == null || tokenSecret == null) {
            return null;
        }

        OAuth oauth = new OAuth();
        oauth.setToken(new OAuthToken(token, tokenSecret));
        User user = new User();
        user.setId(userId);
        user.setRealName(userName);
        oauth.setUser(user);
        RequestContext.getRequestContext().setOAuth(oauth);
        return oauth;
    }

    public static void saveFlickrAuthToken(OAuth oauth) {
        String oauthToken = null;
        String tokenSecret = null;
        String userId = null;
        String userName = null;

        if (oauth != null) {
            oauthToken = oauth.getToken().getOauthToken();
            tokenSecret = oauth.getToken().getOauthTokenSecret();
            userId = oauth.getUser().getId();
            userName = oauth.getUser().getUsername();
        }

        PrefStore.setFlickrToken(oauthToken);
        PrefStore.setFlickrSecret(tokenSecret);
        PrefStore.setFlickrUserId(userId);
        PrefStore.setFlickrUserName(userName);
    }

    /**
     * Clear the user token
     */
    public static void logout() {
        // delete the user cache file.
        String token = PrefStore.getFlickrToken();
        File root = new File(Environment.getExternalStorageDirectory(),
                AppConstant.FLICKR_FOLDER);
        if (root.exists()) {
            File cacheFile = new File(root, token + ".dat"); //$NON-NLS-1$
            if (cacheFile.exists()) {
                cacheFile.delete();
            }
        }
        saveFlickrAuthToken(null);
    }
}
