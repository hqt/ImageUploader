package com.silicons.android.uploader.model;

import com.silicons.android.uploader.uploader.model.PhotoItem;

/**
 * Created by Huynh Quang Thao on 1/4/16.
 */
public class QueuedPhotoNotify {
    private PhotoItem mPhoto;

    public QueuedPhotoNotify(PhotoItem photoItem) {
        this.mPhoto = photoItem;
    }

    public PhotoItem getPhoto() {
        return mPhoto;
    }

    public void setPhoto(PhotoItem photo) {
        this.mPhoto = photo;
    }
}
