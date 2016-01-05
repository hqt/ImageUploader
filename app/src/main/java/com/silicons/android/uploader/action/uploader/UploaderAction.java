package com.silicons.android.uploader.action.uploader;

import com.silicons.android.uploader.action.IAction;
import com.silicons.android.uploader.activity.UploaderActivity;
import com.silicons.android.uploader.model.PhotoItem;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public abstract class UploaderAction implements IAction {

    protected UploaderActivity mActivity;

    public UploaderAction() {
    }

    public void setActivity(UploaderActivity activity) {
        this.mActivity = activity;
    }

    public abstract void upload(PhotoItem photoItem);
}
