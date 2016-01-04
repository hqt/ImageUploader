package com.silicons.android.uploader.uploader.model;

import com.silicons.android.uploader.config.AppConstant.PhotoStatus;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/** Model for Photo for persistent to database
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class PhotoItem extends RealmObject implements Serializable {

    @Index
    @PrimaryKey
    private long id;

    private String flickrId;

    private String flickrTitle;

    private String path;

    private long timeCreated;

    private int retries;

    private int status;

    private int size;

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public PhotoItem() {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getFlickrTitle() {
        return flickrTitle;
    }

    public void setFlickrTitle(String flickrTitle) {
        this.flickrTitle = flickrTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
