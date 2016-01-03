package com.silicons.android.uploader.config;

import android.net.Uri;

import java.util.HashSet;
import java.util.Set;

/** contain all application app constants
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class AppConstant {

    public static final String FLICKR_PUBLIC_KEY = "583dad0f8468d3c0a294fe48d269be98";
    public static final String FLICKR_SECRET_KEY = "b69ac04b0e1cac72";

    public static final String FLICKR_FOLDER = "flick_folder";
    public static final String FLICKR_RETURN_SCHEMA = "flickr-viewer-oauth";
    public static final Uri OAUTH_CALLBACK_URI = Uri.parse(AppConstant.FLICKR_RETURN_SCHEMA + "://oauth");

    // all approved file extension when uploading to FLickr Service
    public static Set<String> FLICKR_SUPPORTED_EXTENSIONS = new HashSet<String>();
    static {
        FLICKR_SUPPORTED_EXTENSIONS.add("jpg");
        FLICKR_SUPPORTED_EXTENSIONS.add("jpeg");
        FLICKR_SUPPORTED_EXTENSIONS.add("png");
        FLICKR_SUPPORTED_EXTENSIONS.add("gif");
        FLICKR_SUPPORTED_EXTENSIONS.add("tiff");
    }

    // variable for checking current mode. All manager instance will based on this for return accurated object
    public static final class ImageProviderMode {
        public static final String FLICKR = "flickr_mode";
        public static final String PICASA = "picasa_mode";
    }

    public static class IntentCode {
        public static final int PICK_PHOTO_INTENT = 100;
        public static final int TAKE_CAMERA_INTENT = 101;
    }

    public static class PhotoStatus {
        public static final int QUEUED = 0;
        public static final int UPLOADED = 1;
        public static final int FAILED = 2;
    }

    public static class PhotoType {
        public static final int PHOTO_URL = 0;
        public static final int PHOTO_ID_SMALL = 1;
        public static final int PHOTO_ID_SMALL_SQUARE = 2;
        public static final int PHOTO_ID_MEDIUM = 3;
        public static final int PHOTO_ID_LARGE = 4;
        public static final int PHOTO_SET_ID = 5;
        public static final int PHOTO_POOL_ID = 6;
    }

    public static final class FileCache {
        public static final String FOLDER_NAME = "image_cache";
        public static final int FOLDER_SIZE = 10;
        public static final int SYSTEM_SIZE = FOLDER_SIZE * 1024 * 1024;
    }

}
