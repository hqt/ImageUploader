package com.silicons.android.uploader.uploader.manager.action;

import android.os.AsyncTask;

import com.silicons.android.uploader.action.IAction;

/**
 * Created by Huynh Quang Thao on 1/5/16.
 */
public interface IActionManager {
    public IAction getAction(String action);
}
