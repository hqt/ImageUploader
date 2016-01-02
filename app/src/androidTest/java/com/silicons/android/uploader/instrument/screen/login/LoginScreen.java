package com.silicons.android.uploader.instrument.screen.login;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.LoginActivity;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

/** Testing for login screen
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class LoginScreen extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity mActivity;

    public LoginScreen() {
        super(LoginActivity.class);
    }

    public LoginScreen(Class<LoginActivity> activityClass) {
        super(activityClass);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testChangeText_sameActivity() {
        onView(ViewMatchers.withId(R.id.login_btn))
                .perform(click());
    }
}
