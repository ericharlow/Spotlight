/*
 * Copyright 2016 Eric Harlow
 * Copyright 2012 Stéphane NICOLAS
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

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.TextPaint;
import android.widget.BaseAdapter;

/**
 * Base class for creating a {@link DrawViewAdapter}. It holds a collection of {@link SpotlightPoint} to render. This
 * adapter will provide a {@link DrawView} with all informations needed to render a given {@link SpotlightPoint}. It is
 * responsible for associating a {@link Drawable}, a {@link TextPaint} and every other data needed at rendering time.
 *
 * This class must be subclassed and works more or less like an Android {@link BaseAdapter}.
 *
 * @see DrawView#setDrawViewAdapter(DrawViewAdapter)
 * @author sni
 *
 */
public interface DrawViewAdapter {

    /**
     * @return the number of {@link SpotlightPoint} to draw.
     */
    public int getPointsCount();

    /**
     * Give the {@link Drawable} of the {@link SpotlightPoint} at a given position.
     *
     * @param position
     *            the position of the {@link SpotlightPoint} to render.
     * @return the {@link Drawable} of the {@link SpotlightPoint} at a given position.
     */
    public Drawable getDrawableAt( int position );

    /**
     * Give the {@link Point} to use when rendering the text of the {@link SpotlightPoint} at a given position.
     *
     * @param position
     *            the position of the {@link SpotlightPoint} to render.
     * @return the {@link Point} to use when rendering the text of the {@link SpotlightPoint} at a given position.
     */
    public Point getTextPointAt( int position );

    /**
     * Give the {@link Layout} to use when rendering the text of the {@link SpotlightPoint} at a given position.
     *
     * @param position
     *            the position of the {@link SpotlightPoint} to render.
     * @return the {@link Layout} to use when rendering the text of the {@link SpotlightPoint} at a given position.
     */
    public Layout getTextLayoutAt( int position );

    /**
     * Get the text of the {@link SpotlightPoint} at position.
     * @param position - the position of the {@link SpotlightPoint} to render.
     * @return the text of the {@link SpotlightPoint}.
     */
    public String getTextAt(int position);

}
