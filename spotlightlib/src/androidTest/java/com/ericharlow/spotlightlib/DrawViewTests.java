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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.Layout;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ericharlow on 4/10/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DrawViewTests {
    @Mock
    Canvas canvasMock;
    @Mock
    DefaultDrawViewAdapter defaultDrawViewAdapterMock;

    Context context = InstrumentationRegistry.getTargetContext();
    DrawView drawView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        drawView = new DrawView(context);
    }

    @Test
    public void shouldCreateDrawView() {
        DrawView drawView = new DrawView(context);
        assertThat(drawView, notNullValue());
    }

    @Test
    public void shouldUpdateDrawViewAdapter() {
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);
        assertThat("DrawViewAdapter", drawView.getDrawViewAdapter(), notNullValue());
        assertThat("DrawViewAdapter", drawView.getDrawViewAdapter(), equalTo((DrawViewAdapter) defaultDrawViewAdapterMock));
    }

    @Test
    public void shouldDrawText() {
        // setup
        when(defaultDrawViewAdapterMock.getTextPointAt(anyInt())).thenReturn(new Point());
        when(defaultDrawViewAdapterMock.getTextLayoutAt(anyInt())).thenReturn(mock(Layout.class));
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);

        drawView.drawText(0, canvasMock);
        verify(canvasMock).restore();
    }

    @Test
    public void shouldDrawDrawable() {
        // PowerMock is probably better than this confusion for a final method stub
        Drawable drawableStub = new Drawable() {
            public int drawCount = 0;
            @Override
            public void draw(Canvas canvas) {
                drawCount++;
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return drawCount;
            }

            @Override
            public int getIntrinsicWidth() {
                return 0;
            }
        };
        assertThat("drawable has bounds", drawableStub.getBounds(), notNullValue());

        when(defaultDrawViewAdapterMock.getDrawableAt(anyInt())).thenReturn(drawableStub);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);

        drawView.drawDrawable(0, canvasMock);
        assertThat("Drawable's draw count", drawableStub.getOpacity(), is(1));
    }

    @Test
    public void shouldDrawPoint() {
        // setup
        String text = "test point";
        when(defaultDrawViewAdapterMock.getTextPointAt(anyInt())).thenReturn(new Point());
        when(defaultDrawViewAdapterMock.getTextLayoutAt(anyInt())).thenReturn(mock(Layout.class));
        when(defaultDrawViewAdapterMock.getTextAt(anyInt())).thenReturn(text);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);

        drawView.drawPoint(0, canvasMock);
        assertContentDescription(drawView, text);
    }

    private void assertContentDescription(View v, String description) {
        CharSequence contentDescription = drawView.getContentDescription();
        assertThat("content description", contentDescription, notNullValue());
        assertThat("content description", contentDescription.toString(), equalTo(description));
    }

}
