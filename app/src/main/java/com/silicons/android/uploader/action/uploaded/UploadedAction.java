package com.silicons.android.uploader.action.uploaded;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.silicons.android.uploader.action.IAction;
import com.silicons.android.uploader.fragment.UploadedPhotoFragment;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public abstract class UploadedAction implements IAction {
    private UploadedPhotoFragment mFragment;

    public void setFragment(UploadedPhotoFragment fragment) {
        this.mFragment = fragment;
    }

    public abstract AsyncTask<Void, Void, Bitmap> getTask(Context context, ImageView imageView, String photoId);
}
