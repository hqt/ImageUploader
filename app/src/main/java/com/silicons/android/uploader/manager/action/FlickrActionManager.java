package com.silicons.android.uploader.manager.action;

import com.silicons.android.uploader.action.IAction;
import com.silicons.android.uploader.action.uploaded.FlickrUploadedAction;
import com.silicons.android.uploader.action.uploader.FlickrUploaderAction;
import com.silicons.android.uploader.action.viewer.FlickrViewerAction;
import com.silicons.android.uploader.config.ActionConstant.Flickr;
import com.silicons.android.uploader.manager.IActionManager;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public class FlickrActionManager implements IActionManager {

    @Override
    public IAction getAction(String action) {
        switch (action) {
            case Flickr.UPLOADING_ACTION:
            return new FlickrUploaderAction();
            case Flickr.VIEWER_ACTION:
                return new FlickrViewerAction();
            case Flickr.UPLOADED_ACTION:
                return new FlickrUploadedAction();
        }
        return null;
    }
}
