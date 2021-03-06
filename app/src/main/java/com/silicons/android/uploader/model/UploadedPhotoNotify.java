package com.silicons.android.uploader.model;

/**
 * Created by Huynh Quang Thao on 1/4/16.
 */
public class UploadedPhotoNotify {
    private PhotoItem mPhoto;

    public UploadedPhotoNotify(PhotoItem photoItem) {
        this.mPhoto = photoItem;
    }

    public PhotoItem getPhoto() {
        return mPhoto;
    }

    public void setPhoto(PhotoItem photo) {
        this.mPhoto = photo;
    }
}
