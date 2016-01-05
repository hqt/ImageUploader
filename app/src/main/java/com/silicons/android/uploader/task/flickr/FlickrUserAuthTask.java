package com.silicons.android.uploader.task.flickr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.oauth.OAuth;
import com.googlecode.flickrjandroid.oauth.OAuthInterface;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.googlecode.flickrjandroid.people.User;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.activity.LoginActivity;
import com.silicons.android.uploader.manager.FlickrHelper;
import com.silicons.android.uploader.uploader.authen.FlickrOath;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Task for getting username and user id after get user's token
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class FlickrUserAuthTask extends AsyncTask<Void, Integer, OAuth> {

    private static final String TAG = makeLogTag(FlickrUserAuthTask.class);

    private Context mContext;
    private String mOauthToken;
    private String mOauthTokenSecret;
    private String mOauthVerifier;

    private ProgressDialog mProgressDialog;

    public FlickrUserAuthTask(Context context, String oauthToken, String oauthSecret, String oauthVerifier) {
        this.mContext = context;
        this.mOauthToken = oauthToken;
        this.mOauthTokenSecret = oauthSecret;
        this.mOauthVerifier = oauthVerifier;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "Login", "Step 2. Getting user information ...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                FlickrUserAuthTask.this.cancel(true);
            }
        });
    }

    @Override
    protected OAuth doInBackground(Void... params) {

        Flickr flickr = FlickrHelper.getInstance().getFlickr();
        OAuthInterface oauthApi = flickr.getOAuthInterface();
        try {
            return oauthApi.getAccessToken(mOauthToken, mOauthTokenSecret, mOauthVerifier);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(OAuth result) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (result == null) {
            Toast.makeText(mContext,
                    "Failed to access your Flickr account. Please try again",
                    Toast.LENGTH_LONG).show();
        } else {
            User user = result.getUser();
            OAuthToken token = result.getToken();
            if (user == null || user.getId() == null || token == null
                    || token.getOauthToken() == null
                    || token.getOauthTokenSecret() == null) {
                Toast.makeText(mContext,
                        "Failed to access your Flickr account. Please try again",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Log.e(TAG, "Saving user " + user);
            FlickrOath.saveAuthInformation(result);

            // open ImageListActivity
            Intent intent = new Intent(mContext, ImageListActivity.class);
            mContext.startActivity(intent);
            ((LoginActivity)mContext).finish();
        }
    }
}
