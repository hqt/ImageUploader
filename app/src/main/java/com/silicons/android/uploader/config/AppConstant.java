package com.silicons.android.uploader.config;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class AppConstant {
    public static final String FLICKR_PUBLIC_KEY = "583dad0f8468d3c0a294fe48d269be98";
    public static final String FLICKR_SECRET_KEY = "b69ac04b0e1cac72";

    public static final String FLICKR_FOLDER = "flick_folder";
    public static final String FLICKR_RETURN_SCHEMA = "flickr-viewer-oauth";

    public static class IntentCode {
        public static final int PICK_PHOTO_INTENT = 100;
        public static final int TAKE_CAMERA_INTENT = 101;
    }

    public static class FlickrService {
        public static final String FLICKR_BASE_URL = "http://api.flickr.com/services/rest/?method=";
        private static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
        private static final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
        private static final int FLICKR_PHOTOS_SEARCH_ID = 1;
        private static final int FLICKR_GET_SIZES_ID = 2;
        private static final int NUMBER_OF_PHOTOS = 20;
    }
}
