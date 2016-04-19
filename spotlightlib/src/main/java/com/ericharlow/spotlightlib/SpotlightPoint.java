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

/**
 * Created by ericharlow on 4/9/16.
 */
public class SpotlightPoint {
    public int x;
    public int y;
    public String text;
    public HandlerType touchHandlerType;
    public TouchHandler touchHandler;

    //    public boolean usePreferredSize;
//    public int preferredWidth = 0;
//    public int preferredHeight = 0;

    private SpotlightPoint() {}

    public static class Builder {
        private SpotlightPoint spotlightPoint;

        public Builder() {
            spotlightPoint = new SpotlightPoint();
            spotlightPoint.text="Default Point";
            spotlightPoint.x = 100;
            spotlightPoint.y = 100;
            spotlightPoint.touchHandlerType = HandlerType.PASSTHROUGH;
            spotlightPoint.touchHandler = new TouchHandler() {};
        }

        public Builder withText(String text) {
            spotlightPoint.text = text;
            return this;
        }

        public Builder withX(int x) {
            spotlightPoint.x = x;
            return this;
        }

        public Builder withY(int y) {
            spotlightPoint.y = y;
            return this;
        }

        public Builder withTouchHandlerOfType(HandlerType handlerType) {
            spotlightPoint.touchHandlerType = handlerType;
            // build a touch handler
            return this;
        }

        public Builder withTouchHandler(TouchHandler handler) {
            spotlightPoint.touchHandler = handler;
            return this;
        }

        public SpotlightPoint build() {
            return spotlightPoint;
        }
    }
}
