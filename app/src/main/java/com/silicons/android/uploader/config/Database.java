package com.silicons.android.uploader.config;

import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.silicons.android.uploader.config.AppConstant.PhotoStatus.*;

/**
 * Created by Huynh Quang Thao on 1/3/16.
 */
public class Database {

    // temporary create database for testing
    public static List<PhotoItem> createDatabase() {

        List<PhotoItem> res = new ArrayList<>();
        PhotoItem photoItem;

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23775074319");
        photoItem.setFlickrTitle("Khỉ rừng bách thảo");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("24060003505");
        photoItem.setFlickrTitle("Bồ công anh");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);


        photoItem = new PhotoItem();
        photoItem.setFlickrId("23764317260");
        photoItem.setFlickrTitle("Cà phê");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23764315030");
        photoItem.setFlickrTitle("Cây bơ");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23433200193");
        photoItem.setFlickrTitle("Cưỡi voi");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23509557784");
        photoItem.setFlickrTitle("Briones Regional Park");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23977338191");
        photoItem.setFlickrTitle("Thác nước");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("21344230968");
        photoItem.setFlickrTitle("Mai Phương Thuý");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23696473729");
        photoItem.setFlickrTitle("Áo dài");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23692116109");
        photoItem.setFlickrTitle("Phố núi");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("23764261970");
        photoItem.setFlickrTitle("Hoa trạng nguyên");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        photoItem = new PhotoItem();
        photoItem.setFlickrId("24059981105");
        photoItem.setFlickrTitle("Đèo dốc quanh co");
        photoItem.setPath("/usr/mount/testing/folder");
        photoItem.setSize(1024);
        photoItem.setTimeCreated((new Date().getTime()));
        photoItem.setStatus(UPLOADED);
        res.add(photoItem);

        return res;
    }
}
