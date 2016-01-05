package com.silicons.android.uploader.task.flickr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.auth.Permission;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.silicons.android.uploader.activity.LoginActivity;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;

import java.net.URL;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Task for getting user's token. This is the must for some action such as getting protected resource
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class FlickrOAuthTask extends AsyncTask<Void, Integer, String> {

    private final String TAG = makeLogTag(FlickrOAuthTask.class);

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public FlickrOAuthTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext,"Login", "Step 1 Login from Flickr ...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                FlickrOAuthTask.this.cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Flickr flickr = FlickrHelper.getInstance().getFlickr();
            OAuthToken oauthToken = flickr.getOAuthInterface().getRequestToken(
                    AppConstant.OAUTH_CALLBACK_URI.toString());

            // save token information to database
            PrefStore.setFlickrToken(oauthToken.getOauthToken());
            PrefStore.setFlickrSecret(oauthToken.getOauthTokenSecret());

            URL oauthUrl = flickr.getOAuthInterface().buildAuthenticationUrl(
                    Permission.DELETE, oauthToken);
            return oauthUrl.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error to oauth: " + e);
            return "error:" + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (result != null && !result.startsWith("error") ) {
            Log.e(TAG, "URI: " + Uri.parse(result).toString());
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse(result)));
            ((LoginActivity)mContext).finish();
        } else {
            Toast.makeText(mContext, "Error: " + result, Toast.LENGTH_LONG).show();
        }
    }
}
