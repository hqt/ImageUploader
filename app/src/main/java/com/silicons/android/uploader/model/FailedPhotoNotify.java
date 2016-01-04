package com.silicons.android.uploader.model;

import com.silicons.android.uploader.uploader.model.PhotoItem;

/**
 * Created by Huynh Quang Thao on 1/4/16.
 */
public class FailedPhotoNotify {
    private PhotoItem mPhoto;

    public FailedPhotoNotify(PhotoItem photoItem) {
        this.mPhoto = photoItem;
    }

    public PhotoItem getPhoto() {
        return mPhoto;
    }

    public void setPhoto(PhotoItem photo) {
        this.mPhoto = photo;
    }
}
