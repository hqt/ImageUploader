package com.silicons.android.uploader.uploader.model;

/** Shared model for both flickr and picassa
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class CustomAuthenticator implements ISharedModel {
    private String mOauthToken;
    private String mTokenSecret;
    private String mUserId;
    private String mUserName;



    public String getOauthToken() {
        return mOauthToken;
    }

    public void setOauthToken(String mOauthToken) {
        this.mOauthToken = mOauthToken;
    }

    public String getTokenSecret() {
        return mTokenSecret;
    }

    public void setTokenSecret(String mTokenSecret) {
        this.mTokenSecret = mTokenSecret;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    @Override
    public String toString() {
        return "CustomAuthenticator{" +
                "mOauthToken='" + mOauthToken + '\'' +
                ", mTokenSecret='" + mTokenSecret + '\'' +
                ", mUserId='" + mUserId + '\'' +
                ", mUserName='" + mUserName + '\'' +
                '}';
    }
}
