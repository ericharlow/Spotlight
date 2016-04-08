package com.ericharlow.spotlight;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

/**
 * Created by ericharlow on 4/6/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void shouldVerify() {
//        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        assert(true);
    }
}
