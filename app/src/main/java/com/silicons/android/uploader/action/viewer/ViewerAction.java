package com.silicons.android.uploader.action.viewer;

import android.widget.ImageView;

import com.silicons.android.uploader.action.IAction;
import com.silicons.android.uploader.activity.PhotoViewerActivity;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public abstract class ViewerAction implements IAction {
    protected PhotoViewerActivity mActivity;

    public void setActivity(PhotoViewerActivity activity) {
        this.mActivity = activity;
    }

    public abstract void download(ImageView imageView, String photoId);
}
