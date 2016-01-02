package com.silicons.android.uploader.instrument.screen.imagelist;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.activity.SettingActivity;
import com.silicons.android.uploader.config.PrefStore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/** Testing on ImageList screen
 * Created by Huynh Quang Thao on 1/2/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ImageListScreen {

    @Rule
    public IntentsTestRule<ImageListActivity> mActivityRule = new IntentsTestRule<>(
            ImageListActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // stub all external intent
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void checkViewComponent() {
        ImageListActivity activity = mActivityRule.getActivity();

         FloatingActionButton mPhotoUploadButton = (FloatingActionButton) activity.findViewById(R.id.fab_photos);
         FloatingActionButton mCameraUploadButton = (FloatingActionButton) activity.findViewById(R.id.fab_camera);
         FloatingActionsMenu mGeneralFloatingButton = (FloatingActionsMenu) activity.findViewById(R.id.fab_menu);
         DrawerLayout mDrawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
         TabLayout mTabLayout = (TabLayout) activity.findViewById(R.id.tab_layout);
         ViewPager mViewPager = (ViewPager) activity.findViewById(R.id.pager);

        assertThat(mPhotoUploadButton, notNullValue());
        assertThat(mCameraUploadButton, notNullValue());
        assertThat(mGeneralFloatingButton, notNullValue());
        assertThat(mViewPager, notNullValue());
        assertThat(mTabLayout, notNullValue());
        assertThat(mDrawerLayout, notNullValue());
    }
}
