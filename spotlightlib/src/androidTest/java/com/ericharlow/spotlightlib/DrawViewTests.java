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
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.xmlpull.v1.XmlPullParser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
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
        defaultDrawViewAdapterMock = getFullyMockedDefaultDrawViewAdapter("test point text");
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
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);

        drawView.drawText(0, canvasMock);
        verify(canvasMock).restore();
    }

    @Test
    public void shouldDrawDrawable() {
        // PowerMock is probably better than this confusion for overcoming a final method stub
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

        when(defaultDrawViewAdapterMock.getDrawableAt(anyInt())).thenReturn(drawableStub);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);

        drawView.drawDrawable(0, canvasMock);
        assertThat("Drawable's draw count", drawableStub.getOpacity(), is(1));
    }

    @Test
    public void shouldDrawPoint() {
        String text = "test point";
        DefaultDrawViewAdapter drawViewAdapter = getFullyMockedDefaultDrawViewAdapter(text);
        drawView.setDrawViewAdapter(drawViewAdapter);

        drawView.drawPoint(0, canvasMock);
        assertContentDescription(drawView, text);
    }

    @Test
    public void shouldCreateDrawViewWithAttributeSet() {
        Resources resources = context.getResources();
        XmlPullParser parser = resources.getXml(R.xml.drawviewtests);
        AttributeSet attributes = Xml.asAttributeSet(parser);

        DrawView drawView = new DrawView(context, attributes);
        assertThat("draw view is drawing one point at a time", drawView.isDrawingOnePointAtATime(), is(true));
    }

    @Test
    public void shouldDrawOnePointAtATime() {
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);
        drawView.setDrawingOnePointAtATime(true);
        DrawView drawViewSpy = spy(drawView);
        drawViewSpy.onDraw(canvasMock);
        verify(drawViewSpy, times(1)).drawPoint(anyInt(), any(Canvas.class));
    }

    @Test
    public void shouldDrawSecondPointWithOnePointAtATime() {
        when(defaultDrawViewAdapterMock.getPointsCount()).thenReturn(5);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);
        drawView.setDrawingOnePointAtATime(true);
        drawView.showNextPoint();

        DrawView drawViewSpy = spy(drawView);
        drawViewSpy.onDraw(canvasMock);
        verify(drawViewSpy, times(1)).drawPoint(anyInt(), any(Canvas.class));
    }

    @Test
    public void shouldDrawUpToCurrentPoint() {
        when(defaultDrawViewAdapterMock.getPointsCount()).thenReturn(5);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);
        drawView.showNextPoint();
        drawView.showNextPoint();
        drawView.showNextPoint();

        DrawView drawViewSpy = spy(drawView);
        drawViewSpy.onDraw(canvasMock);
        verify(drawViewSpy, times(4)).drawPoint(anyInt(), any(Canvas.class));
    }

    @Test
    public void shouldDrawAllThePointsAfterAnimation() {
        when(defaultDrawViewAdapterMock.getPointsCount()).thenReturn(3);
        drawView.setDrawViewAdapter(defaultDrawViewAdapterMock);
        drawView.setShowingAllPointsAtTheEndOfAnimation(true);
        drawView.showNextPoint();
        drawView.showNextPoint();

        DrawView drawViewSpy = spy(drawView);
        drawViewSpy.onDraw(canvasMock);
        verify(drawViewSpy, times(3)).drawPoint(anyInt(), any(Canvas.class));
    }

    private DefaultDrawViewAdapter getFullyMockedDefaultDrawViewAdapter(String pointText) {
        DefaultDrawViewAdapter drawViewAdapter = mock(DefaultDrawViewAdapter.class);
        when(drawViewAdapter.getTextPointAt(anyInt())).thenReturn(new Point());
        when(drawViewAdapter.getTextLayoutAt(anyInt())).thenReturn(mock(Layout.class));
        when(drawViewAdapter.getTextAt(anyInt())).thenReturn(pointText);
        // PowerMock is probably better than this confusion for overcoming a final method stub
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

        when(drawViewAdapter.getDrawableAt(anyInt())).thenReturn(drawableStub);
        return drawViewAdapter;
    }

    private void assertContentDescription(View v, String description) {
        CharSequence contentDescription = drawView.getContentDescription();
        assertThat("content description", contentDescription, notNullValue());
        assertThat("content description", contentDescription.toString(), equalTo(description));
    }

    private void assertPointDrawn() {

    }

}
