package com.silicons.android.uploader.action.uploaded;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class PicasaUploadedAction extends UploadedAction {

    @Override
    public AsyncTask<Void, Void, Bitmap> getTask(Context context, ImageView imageView, String photoId) {
        throw new NoSuchMethodError("this method must be implemented before use");
    }
}
