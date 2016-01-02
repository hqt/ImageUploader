package com.silicons.android.uploader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;

import com.silicons.android.uploader.config.TestAppConstant;

/**
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class TestUtils {
    /**
     * Clears everything in the SharedPreferences
     */
    public static void clearSharedPrefs() {
        Context context = InstrumentationRegistry.getTargetContext();
        SharedPreferences sharedPreferences = context.
                getSharedPreferences(TestAppConstant.PREF_APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
