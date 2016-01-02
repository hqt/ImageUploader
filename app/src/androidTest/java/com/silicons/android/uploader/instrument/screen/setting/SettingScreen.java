package com.silicons.android.uploader.instrument.screen.setting;

import android.app.Activity;
import android.app.Instrumentation;
import android.preference.CheckBoxPreference;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.SettingActivity;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/** Test setting screen
 * Created by Huynh Quang Thao on 1/2/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SettingScreen {

    @Rule
    public IntentsTestRule<SettingActivity> mActivityRule = new IntentsTestRule<>(
            SettingActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // stub all external intent
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Before
    public void setUpComponent() throws Exception {
        PrefStore.setMobileNetwork(false);
        PrefStore.setPrivacyData(true);
    }

    @Test
    public void checkViewComponent() {
        SettingActivity activity = mActivityRule.getActivity();

        Spinner mUploadedPhotoSpinner = (Spinner) activity.findViewById(R.id.uploaded_sort_spinner);
        Spinner mFailedPhotoSpinner = (Spinner) activity.findViewById(R.id.failed_sort_spinner);
        CheckBox mNetworkCheckBox = (CheckBox) activity.findViewById(R.id.networkCheckBox);
        CheckBox mPrivacyCheckBox = (CheckBox) activity.findViewById(R.id.privacyCheckBox);
        Button mOkButton = (Button) activity.findViewById(R.id.btn_ok);
        Button mCancelButton = (Button) activity.findViewById(R.id.btn_cancel);

        assertThat(mOkButton, notNullValue());
        assertThat(mCancelButton, notNullValue());
        assertThat(mPrivacyCheckBox, notNullValue());
        assertThat(mNetworkCheckBox, notNullValue());
        assertThat(mFailedPhotoSpinner, notNullValue());
        assertThat(mUploadedPhotoSpinner, notNullValue());
    }

    @Test
    public void testNetworkCheck() {
        onView(withId(R.id.networkCheckBox)).perform(click());
        onView(withId(R.id.btn_ok)).perform(click());
        assertThat(PrefStore.getIsMobileNetwork(), equalTo(true));
    }

    @Test
    public void testPrivacyCheck() {
        onView(withId(R.id.privacyCheckBox)).perform(click());
        onView(withId(R.id.btn_ok)).perform(click());
        assertThat(PrefStore.getIsMobileNetwork(), equalTo(false));
    }

    @After
    public void clearResource() throws Exception {
        PrefStore.setMobileNetwork(false);
        PrefStore.setPrivacyData(true);
    }
}
