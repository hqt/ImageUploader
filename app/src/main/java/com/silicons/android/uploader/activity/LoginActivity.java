package com.silicons.android.uploader.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.cache.MemoryImageCache;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.Database;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.task.flickr.OAuthTask;
import com.silicons.android.uploader.task.flickr.UserAuthTask;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.ImageUtils;
import com.silicons.android.uploader.utils.NetworkUtils;

import java.util.List;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Login Activity. User must login before using application. :)
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = makeLogTag(LoginActivity.class);

    private Button mLoginButton;
    ImageView imageView;

    private static Bitmap mBitmap;

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
        imageView = (ImageView) findViewById(R.id.wallpaper);

        if (mBitmap == null) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            mBitmap = ImageUtils.decodeSampledBitmapFromResource(this.getResources(), R.drawable.login_background, width, height);
        }

        imageView.setImageBitmap(mBitmap);

        mLoginButton = (Button) findViewById(R.id.login_btn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // first step. authentication for getting token
                if (NetworkUtils.isNetworkAvailable()) {
                    new OAuthTask(LoginActivity.this).execute();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.network_not_available_message,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // when first step done. will return a uri-schema.
        // LoginActivity will handle this schema (declared in manifest file)
        // come to second step. process this uri. for getting user information
        Intent intent = getIntent();
        String schema = intent.getScheme();
        if (AppConstant.FLICKR_RETURN_SCHEMA.equals(schema)) {
            Uri uri = intent.getData();
            String query = uri.getQuery();
            Log.e(TAG, "Returned Query: " + query);
            String[] data = query.split("&");
            if (data.length == 2) {
                String oauthToken = data[0].substring(data[0].indexOf("=") + 1);
                String oauthVerifier = data[1].substring(data[1].indexOf("=") + 1);
                Log.e(TAG, "OAuth Token: " + oauthToken);
                Log.e(TAG, "OAuth Verifier: " + oauthVerifier);

                String oauthSecret = PrefStore.getFlickTokenSecret();
                if (oauthSecret != null) {
                    if (NetworkUtils.isNetworkAvailable()) {
                        UserAuthTask task = new UserAuthTask(this, oauthToken, oauthSecret, oauthVerifier);
                        task.execute();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.network_not_available_message,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
