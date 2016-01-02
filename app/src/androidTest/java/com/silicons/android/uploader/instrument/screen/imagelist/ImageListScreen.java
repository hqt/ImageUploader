package com.silicons.android.uploader.instrument.screen.imagelist;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.silicons.android.uploader.activity.ImageListActivity;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class ImageListScreen extends ActivityInstrumentationTestCase2<ImageListActivity> {

    private ImageListActivity mActivity;

    public ImageListScreen() {
        super(ImageListActivity.class);
    }

    public ImageListScreen(Class<ImageListActivity> activityClass) {
        super(activityClass);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }



}
