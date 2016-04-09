/*
 * Copyright (c) 2016. Eric Harlow
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  	you may not use this file except in compliance with the License.
 *  	You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  	Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  	See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

package com.ericharlow.spotlight;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ericharlow on 4/9/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DemoActivityTest {
    @Rule
    public ActivityTestRule<DemoActivity> demoActivityActivityTestRule = new ActivityTestRule<>(
            DemoActivity.class);

    @Test
    public void shouldClickOnSwitch() {
        onView(withId(R.id.DemoSwitch)).perform(click());
    }

    @Test
    public void shouldClickOnCheckbox() {
        onView(withId(R.id.checkBox)).perform(click());
    }
}
