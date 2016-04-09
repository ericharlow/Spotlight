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

import java.util.ArrayList;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;

/**
 * Created by ericharlow on 4/9/16.
 */
public class CoverFragment extends DialogFragment {

    public static final String TAG = "CoverFragment";
    public final static String COVER_FRAGMENT_ID = "coverFragment";
    public final static String COVER_FRAGMENT_SHOW = "coverFragmentShow";

    private ArrayList<SpotlightPoint> spotlightPoints;
    private String coverFragmentId;
    private int resourceId;

    private DrawView drawView;
    private CheckBox checkBox;

    /**
     * Create a CoverFragment using a list of {@link SpotlightPoint}.
     * @param arrayListPoints - list of SpotlighPoints to show.
     * @return The CoverFragment.
     */
    public static CoverFragment newInstance(ArrayList< SpotlightPoint > arrayListPoints) {
        return newInstance(R.layout.fragment_cover, arrayListPoints);
    }

    /**
     * Create a customized CoverFragment with a xml layout and supplied list of {@link SpotlightPoint}.
     * @param resource - custom layout.
     * @param arrayListPoints - list of SpotlightPoints to show.
     * @return The CoverFragment.
     * @see DrawView
     */
    public static CoverFragment newInstance(int resource, ArrayList< SpotlightPoint > arrayListPoints) {
        CoverFragment f = new CoverFragment();
        f.coverFragmentId = COVER_FRAGMENT_ID;
        f.resourceId = resource;
        f.spotlightPoints = arrayListPoints;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_RoboDemo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (resourceId == 0) {
            resourceId = savedInstanceState.getInt(COVER_FRAGMENT_ID);
        }

        View v = inflater.inflate(resourceId, null);

        View temp = v.findViewById( R.id.drawView_move_content_demo );
        if (temp != null) {
            drawView = (DrawView) temp;
        }

        temp = v.findViewById( R.id.checkbox_demo_never_again );
        if (temp != null)
            checkBox = (CheckBox) temp;

        temp = v.findViewById(R.id.textview_demo_never_again);
        if (temp != null)
            temp.setOnClickListener(createDemoNeverAgainListener());

        temp = v.findViewById(R.id.button_demo_finish);
        if (temp != null)
            temp.setOnClickListener(createFinishButtonListener());

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (drawView == null)
            return;

        drawView.setAnimationListener(new DrawViewAnimationListener());
        drawView.setDrawViewAdapterSpotlightPoints(spotlightPoints);
        drawView.setDispatchTouchHandler(new FragmentDispatchTouchHandler());
    }

    /**
     * Removes any previous fragment identified by the tag.
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        Fragment prev = manager.findFragmentByTag(tag);
        if (prev != null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.remove(prev).commit();
        }
        super.show(manager, tag);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(COVER_FRAGMENT_ID, resourceId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK){
                    finish(null);// need to take care of our mess and state
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    public void checkNeverShowAgain( View view ) {
        checkBox.setChecked( !checkBox.isChecked() );
    }

    public void finish( View view ) {
//        if ( checkBox != null && checkBox.isChecked() ) {
//            RoboDemo.setNeverShowAgain(getActivity(), demoFragmentId, true);
//        } else {
//            RoboDemo.showAgain(getActivity(), demoFragmentId);
//        }
//
//        RoboDemo.setShowDemo(getActivity(), DemoFragment.DEMO_FRAGMENT_SHOW, false);
        removeSelf();
    }

    private void setButtonsVisible( boolean visible ) {
        View view = getView();
        if (view == null)
            return;

        final View layoutButtons = view.findViewById( R.id.layout_demo_buttons );
        if (layoutButtons == null)
            return;

        int animationResId = visible ? android.R.anim.fade_in : android.R.anim.fade_out;
        Animation animation = AnimationUtils.loadAnimation( getActivity(), animationResId );
        animation.setDuration( getResources().getInteger( android.R.integer.config_shortAnimTime ) );
        animation.setAnimationListener( new ButtonsAnimationListener( visible, layoutButtons ) );
        layoutButtons.startAnimation( animation );
    }

    private boolean removeSelf() {
        FragmentManager fManager = getFragmentManager();
        fManager.beginTransaction().remove(this).commit();
        return true;
    }

    public String getCoverFragmentId() {
        return coverFragmentId;
    }

    public void setCoverFragmentId(String coverFragmentId) {
        this.coverFragmentId = coverFragmentId;
    }

    private OnClickListener createFinishButtonListener() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish(v);
            }
        };
    }

    private OnClickListener createDemoNeverAgainListener() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                checkNeverShowAgain(v);
            }
        };
    }

    /**
     * Animate the buttons at the bottom of the screen.
     */
    private final class DrawViewAnimationListener implements AnimationListener {

        @Override
        public void onAnimationStart( Animation animation ) {
            setButtonsVisible( false );
        }

        @Override
        public void onAnimationRepeat( Animation animation ) {

        }

        @Override
        public void onAnimationEnd( Animation animation ) {
            setButtonsVisible( true );
        }
    }

    private final class ButtonsAnimationListener implements AnimationListener {
        private final boolean visibleAtEnd;
        private final View layoutButtons;

        private ButtonsAnimationListener( boolean visibleAtEnd, View layoutButtons ) {
            this.visibleAtEnd = visibleAtEnd;
            this.layoutButtons = layoutButtons;
        }

        @Override
        public void onAnimationStart( Animation animation ) {
            layoutButtons.setVisibility( View.VISIBLE );
        }

        @Override
        public void onAnimationRepeat( Animation animation ) {
        }

        @Override
        public void onAnimationEnd( Animation animation ) {
            layoutButtons.setVisibility( visibleAtEnd ? View.VISIBLE : View.GONE );
        }
    }

    /**
     * Gives the Context for the {@link DispatchTouchHandler}.
     * @author ericharlow
     */
    private class FragmentDispatchTouchHandler implements DispatchTouchHandler {

        @Override
        public boolean sendTouchEvent(MotionEvent event) {
            View v = getActivity().getWindow().getDecorView();
            if (v != null)
                return v.dispatchTouchEvent(event);
            else
                return false;
        }

    }

}
