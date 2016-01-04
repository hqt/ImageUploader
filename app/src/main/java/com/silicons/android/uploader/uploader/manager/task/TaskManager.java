package com.silicons.android.uploader.uploader.manager.task;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.AppConstant.ImageProviderMode;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class TaskManager {

    @Override
    public ITaskManager getManager(String provider) {
        if (provider.equals(ImageProviderMode.FLICKR)) {
            return new FlickrTaskManager();
        } else {
            return new PicasaTaskManager();
        }
    }
}
