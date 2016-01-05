package com.silicons.android.uploader.uploader.manager.action;

import com.silicons.android.uploader.config.AppConstant.ImageProviderMode;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class ActionManagerHandler {

    public static IActionManager getInstance(String provider) {
        if (provider.equals(ImageProviderMode.FLICKR)) {
            return new FlickrActionManager();
        } else {
            return new PicasaActionManager();
        }
    }
}
