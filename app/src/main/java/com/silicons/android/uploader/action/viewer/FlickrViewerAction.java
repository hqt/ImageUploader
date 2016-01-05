package com.silicons.android.uploader.action.viewer;

import android.widget.ImageView;

import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.task.flickr.FlickrImageDownloadTask;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class FlickrViewerAction extends ViewerAction {

    @Override
    public void download(ImageView imageView, String photoId) {
        FlickrImageDownloadTask task = new FlickrImageDownloadTask(mActivity, imageView, photoId,
                AppConstant.PhotoType.PHOTO_ID_LARGE, true);
        task.execute();
    }
}
