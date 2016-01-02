package com.silicons.android.uploader.instrument.screen.imagelist;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.ImageListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/** Testing intent on ImageListScreen
 * Created by Huynh Quang Thao on 1/2/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ImageListIntentTest {

    @Rule
    public IntentsTestRule<ImageListActivity> mActivityRule = new IntentsTestRule<>(
            ImageListActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // stub all external intent
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickCameraButton() {
        onView(withId(R.id.fab_menu)).perform(click());
        onView(withId(R.id.fab_camera))
                .perform(click());

        // verify that send suitable intent to system.
        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @Test
    public void clickPhotoButton() {
        onView(withId(R.id.fab_menu)).perform(click());
        onView(withId(R.id.fab_photos))
                .perform(click());

        // verify that send suitable intent to system.
        intended(allOf(
                hasAction(Intent.ACTION_GET_CONTENT),
                hasType("image/*")
        ));
    }

    @Test
    public void selectedPhoto_Accepted() {

    }

    @Test
    public void takenPhotoFromCamera_Accepted() {

    }

    @Test
    public void clickNavigationDrawerMenuButton() {

    }
}
