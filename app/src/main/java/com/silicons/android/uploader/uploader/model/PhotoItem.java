package com.silicons.android.uploader.uploader.model;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.AppConstant.PhotoStatus;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class PhotoItem extends RealmObject implements Serializable {

    @PrimaryKey
    @Index
    private int id;

    private String flickrId;

    private String flickrSetTitle;

    private int retries;

    private int status;

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    private long timeCreated;

    private String path;

    public PhotoItem() {

    }

    public String getFlickrId() {
        return flickrId;
    }

    public void setFlickrId(String flickrId) {
        if ((flickrId != null) && (flickrId.length() > 0)) {
            this.flickrId = flickrId;
            setStatus(PhotoStatus.UPLOADED);
        } else {
            flickrId = null;
        }
    }

    public String getFlickrSetTitle() {
        return flickrSetTitle;
    }

    public void setFlickrSetTitle(String flickrSetTitle) {
        this.flickrSetTitle = flickrSetTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
