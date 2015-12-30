package com.silicons.android.uploader.config;

/**
 * Created by Huynh Quang Thao on 12/30/15.
 */
public class AppConstant {
    public static final String FLICKR_PUBLIC_KEY = "b5d217d0f1111ee2fe13b5aae3536afc";
    public static final String FLICKR_SECRET_KEY = "fa506977ada6eb1a";

    public static final String FLICKR_FOLDER = "flick_folder";

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
