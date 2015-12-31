package com.silicons.android.uploader.uploader.authen;

import com.googlecode.flickrjandroid.oauth.OAuth;

/** interface for flickr and picassa using when processing authentication
 * Created by Huynh Quang Thao on 12/31/15.
 */
public interface IOath {
    public OAuth loadSavedOAuth();
    public void saveAuthInformation(OAuth oauth);
    public void logout();
}
