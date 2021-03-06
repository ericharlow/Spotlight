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

import android.view.MotionEvent;

/**
 * Created by ericharlow on 4/18/16.
 */
public interface TouchDispatcher {
    /**
     * Send MotionEvents to be handled.
     * Intend to send from a Fragment's custom semi-translucent view to the Activity beneath the Fragment.
     * @param event - The motion event to be dispatched.
     * @return True if the event was handled by the view, false otherwise.
     */
    boolean sendTouchEvent(MotionEvent event);
}
