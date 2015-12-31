package com.silicons.android.uploader.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.auth.Permission;
import com.googlecode.flickrjandroid.oauth.OAuthToken;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.uploader.FlickrHelper;

import java.net.URL;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Login Activity. User must login before using application. :)
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag(LoginActivity.class);

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if already login. switch to list activity
        if (PrefStore.isLogin()) {
            Log.e(TAG, "Authen token: " + PrefStore.getFlickrToken());
            Intent intent = new Intent(this, ImageListActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_btn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OAuthTask().execute();
            }
        });
    }

    public class OAuthTask extends AsyncTask<Void, Integer, String> {

        private final String TAG = makeLogTag(OAuthTask.class);

        private final Uri OAUTH_CALLBACK_URI = Uri.parse(AppConstant.ID_SCHEME + "://oauth");

        private ProgressDialog mProgressDialog;
        private Context mContext;

        public OAuthTask() {
            mContext = LoginActivity.this;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = ProgressDialog.show(mContext,"Login", "Login from Flickr ...");
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dlg) {
                    OAuthTask.this.cancel(true);
                }
            });
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Flickr f = FlickrHelper.getInstance().getFlickr();
                OAuthToken oauthToken = f.getOAuthInterface().getRequestToken(
                        OAUTH_CALLBACK_URI.toString());

                // save token information to database
                PrefStore.setFlickrToken(oauthToken.getOauthToken());
                PrefStore.setFlickrSecret(oauthToken.getOauthTokenSecret());

                URL oauthUrl = f.getOAuthInterface().buildAuthenticationUrl(
                        Permission.WRITE, oauthToken);
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
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(result)));
            } else {
                Toast.makeText(mContext, "Error: " + result, Toast.LENGTH_LONG).show();
            }
        }
    }

}
