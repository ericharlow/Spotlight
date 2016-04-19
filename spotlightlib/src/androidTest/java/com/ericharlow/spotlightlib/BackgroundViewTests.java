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

package com.ericharlow.spotlightlib;

import android.content.res.Resources;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.AttributeSet;
import android.util.Xml;

import com.ericharlow.spotlightlib.view.BackgroundView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ericharlow on 4/10/16.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class BackgroundViewTests extends ViewTest {

    @Test
    public void shouldCreateABackgroundView() {
        Resources resources = context.getResources();
        XmlPullParser parser = resources.getXml(R.xml.backgroundviewtests);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        BackgroundView backgroundView = new BackgroundView(context, attributes, 0);
        assertThat("background view", backgroundView, notNullValue());
    }

}
