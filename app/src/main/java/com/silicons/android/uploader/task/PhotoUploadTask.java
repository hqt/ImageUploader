package com.silicons.android.uploader.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.uploader.UploadMetaData;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.activity.UploaderActivity;
import com.silicons.android.uploader.uploader.manager.FlickrHelper;

import org.xml.sax.SAXException;

import java.io.IOException;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Task for uploading photo to flickr
 * Created by Huynh Quang Thao on 12/31/15.
 */
public class PhotoUploadTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = makeLogTag(PhotoUploadTask.class);

    private ProgressDialog mProgressDialog;
    private Context mContext;

    private String mImageName;
    private byte[] mData;
    private UploadMetaData mUploadMetaData;

    public PhotoUploadTask(Context context, String imageName, byte[] data, UploadMetaData uploadMetaData) {
        this.mContext = context;
        this.mImageName = imageName;
        this.mData = data;
        this.mUploadMetaData = uploadMetaData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext,"Uploading", "Please wait for uploading image ...");
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dlg) {
                PhotoUploadTask.this.cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(Void... params) {
        Flickr flickr = FlickrHelper.getInstance().getFlickr();
        String imageId = null;
        try {
            imageId = flickr.getUploader().upload(mImageName, mData, mUploadMetaData);
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return imageId;
    }

    @Override
    protected void onPostExecute(String result) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (result == null) {
            Toast.makeText(mContext, "Cannot download image. Please try again.",
                    Toast.LENGTH_LONG).show();
        }

        // open again image list activity
        Intent intent = new Intent(mContext, ImageListActivity.class);
        mContext.startActivity(intent);
        ((UploaderActivity)mContext).finish();
    }
}
