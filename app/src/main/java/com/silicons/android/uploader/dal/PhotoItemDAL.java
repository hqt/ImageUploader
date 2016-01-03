package com.silicons.android.uploader.dal;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/** Data Access Layer for PhotoItem
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class PhotoItemDAL {

    public static PhotoItem insertOrUpdatePhoto(PhotoItem photoItem) {
        Realm realm = Realm.getDefaultInstance();

        // this photo is new. generate new id :)
        if (photoItem.getId() == 0) {
            photoItem.setId(new Date().getTime());
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(photoItem);
        realm.commitTransaction();
        return photoItem;
    }

    public static List<PhotoItem> getAllFailedPhotos() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<PhotoItem> query = realm.where(PhotoItem.class).equalTo("status", AppConstant.PhotoStatus.FAILED);
        RealmResults<PhotoItem> photos = query.findAll();
        List<PhotoItem> res =  photos.subList(0, photos.size());

        // sort again based on user setting
        Collections.sort(res, getFailedComparator());
        return res;
    }

    public static List<PhotoItem> getAllUploadedPhotos() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<PhotoItem> query = realm.where(PhotoItem.class).equalTo("status", AppConstant.PhotoStatus.UPLOADED);
        RealmResults<PhotoItem> photos = query.findAll();
        List<PhotoItem> res =  photos.subList(0, photos.size());

        // sort again based on user setting
        // Collections.sort(res, getUploadedComparator());
        return res;
    }

    public static List<PhotoItem> getAllQueuedPhotos() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<PhotoItem> query = realm.where(PhotoItem.class).equalTo("status", AppConstant.PhotoStatus.QUEUED);
        RealmResults<PhotoItem> photos = query.findAll();
        List<PhotoItem> res =  photos.subList(0, photos.size());

        return res;
    }

    public static boolean deleteAllPhotos() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(PhotoItem.class).findAll().clear();
        realm.commitTransaction();
        return true;
    }

    private static Comparator<PhotoItem> getFailedComparator() {
        // sort by name
        if (PrefStore.getFailSortType() == 0) {
            return new Comparator<PhotoItem>() {
                @Override
                public int compare(PhotoItem lhs, PhotoItem rhs) {
                    return lhs.getFlickrTitle().compareTo(rhs.getFlickrTitle());
                }
            };
        }
        // sort by date
        else {
            return new Comparator<PhotoItem>() {
                @Override
                public int compare(PhotoItem lhs, PhotoItem rhs) {
                    return (int) (lhs.getTimeCreated() - rhs.getTimeCreated());
                }
            };
        }
    }

    private static Comparator<PhotoItem> getUploadedComparator() {
        // sort by name
        if (PrefStore.getUploadSortType() == 0) {
            return new Comparator<PhotoItem>() {
                @Override
                public int compare(PhotoItem lhs, PhotoItem rhs) {
                    return lhs.getFlickrTitle().compareTo(rhs.getFlickrTitle());
                }
            };
        }
        // sort by date
        else {
            return new Comparator<PhotoItem>() {
                @Override
                public int compare(PhotoItem lhs, PhotoItem rhs) {
                    return (int) (lhs.getTimeCreated() - rhs.getTimeCreated());
                }
            };
        }
    }
}
