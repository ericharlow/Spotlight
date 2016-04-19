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

import com.ericharlow.spotlightlib.handler.HandlerType;
import com.ericharlow.spotlightlib.handler.TouchHandler;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by ericharlow on 4/9/16.
 */
public class SpotlightPointTests {

    @Test
    public void shouldCreateADefaultSpotlightPoint() {
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder().build();
        assertThat("spotlightPoint text after default creation",spotlightPoint.text, notNullValue());
    }

    @Test
    public void shouldBuildASpotlightPointWithText() {
        String text = new String("text point");
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder()
                .withText(text).build();

        assertThat("spotlight point text", spotlightPoint.text, equalTo(text));
    }

    @Test
    public void shouldBuildASpotlightPointWithCoordinates() {
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder()
                .withX(234)
                .withY(123).build();

        assertThat("spotlight point x location", spotlightPoint.x, is(234));
        assertThat("spotlight point y location", spotlightPoint.y, is(123));
    }

    @Test
    public void shouldBuildASpotlightPointWithTouchHandlerType() {
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder()
                .withTouchHandlerOfType(HandlerType.PASSTHROUGH).build();

        assertThat("touch handler type", spotlightPoint.touchHandlerType, is(HandlerType.PASSTHROUGH));
    }

    @Test
    public void shouldBuildASpotlightPointWithTouchHandler() {
        TouchHandler touchHandler = mock(TouchHandler.class);
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder()
                .withTouchHandler(touchHandler).build();

        assertThat("touch handler", spotlightPoint.touchHandler, equalTo(touchHandler));
    }

}
