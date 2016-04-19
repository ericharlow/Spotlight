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
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ericharlow on 4/9/16.
 */
public class SpotlightFragment extends DialogFragment {

    public static final String TAG = "SpotlightFragment";
    public final static String SPOTLIGHT_FRAGMENT_ID = TAG;
    public final static String SPOTLIGHT_FRAGMENT_SHOW = "spotlightFragmentShow";

    public FragmentManagerProvider fragmentManagerProvider;

    private ArrayList<SpotlightPoint> spotlightPoints;
    private String spotlightFragmentId;
    private int resourceId;

//    private CheckBox checkBox;

    public SpotlightFragment() {
        fragmentManagerProvider = new LocalFragmentManagerProvider();
    }

    /**
     * Create a SpotlightFragment using a list of {@link SpotlightPoint}.
     * @param arrayListPoints - list of SpotlighPoints to show.
     * @return The SpotlightFragment.
     */
    public static SpotlightFragment newInstance(ArrayList< SpotlightPoint > arrayListPoints) {
        return newInstance(R.layout.fragment_spotlight, arrayListPoints);
    }

    /**
     * Create a customized SpotlightFragment with a xml layout and supplied list of {@link SpotlightPoint}.
     * @param resource - custom layout.
     * @param arrayListPoints - list of SpotlightPoints to show.
     * @return The SpotlightFragment.
     */
    public static SpotlightFragment newInstance(int resource, ArrayList< SpotlightPoint > arrayListPoints) {
        SpotlightFragment f = new SpotlightFragment();
        f.spotlightFragmentId = SPOTLIGHT_FRAGMENT_ID;
        f.resourceId = resource;
        f.spotlightPoints = arrayListPoints;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Theme_Spotlight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (resourceId == 0) {
            resourceId = savedInstanceState.getInt(SPOTLIGHT_FRAGMENT_ID);
        }

        View v = inflater.inflate(resourceId, null);

        return v;
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
        outState.putInt(SPOTLIGHT_FRAGMENT_ID, resourceId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnKeyListener(createOnBackKeyListener());
        return dialog;
    }

    @NonNull
    protected OnKeyListener createOnBackKeyListener() {
        return new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK){
                    removeSelf();
                    return true;
                }
                return false;
            }
        };
    }

    private boolean removeSelf() {
        FragmentManager fManager = fragmentManagerProvider.provide();
        fManager.beginTransaction().remove(this).commit();
        return true;
    }

    public String getSpotlightFragmentId() {
        return spotlightFragmentId;
    }

    public void setSpotlightFragmentId(String spotlightFragmentId) {
        this.spotlightFragmentId = spotlightFragmentId;
    }

    public void setFragmentManagerProvider(FragmentManagerProvider fragmentManagerProvider) {
        this.fragmentManagerProvider = fragmentManagerProvider;
    }

    private class LocalFragmentManagerProvider implements FragmentManagerProvider {

        @Override
        public FragmentManager provide() {
            return getFragmentManager();
        }
    }



    //    public void checkNeverShowAgain( View view ) {
//        checkBox.setChecked( !checkBox.isChecked() );
//    }
//
//    private void setButtonsVisible( boolean visible ) {
//        View view = getView();
//        if (view == null)
//            return;
//
//        final View layoutButtons = view.findViewById( R.id.layout_demo_buttons );
//        if (layoutButtons == null)
//            return;
//
//        int animationResId = visible ? android.R.anim.fade_in : android.R.anim.fade_out;
//        Animation animation = AnimationUtils.loadAnimation( getActivity(), animationResId );
//        animation.setDuration( getResources().getInteger( android.R.integer.config_shortAnimTime ) );
//        layoutButtons.startAnimation( animation );
//    }
//    private OnClickListener createFinishButtonListener() {
//        return new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                removeSelf();
//            }
//        };
//    }
//
//    private OnClickListener createDemoNeverAgainListener() {
//        return new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                checkNeverShowAgain(v);
//            }
//        };
//    }

    /**
     * Gives the Context for the .
     * @author ericharlow
     */
//    private class FragmentTouchDispatcher implements TouchDispatcher {
//
//        @Override
//        public boolean sendTouchEvent(MotionEvent event) {
//            View v = getActivity().getWindow().getDecorView();
//            if (v != null)
//                return v.dispatchTouchEvent(event);
//            else
//                return false;
//        }
//
//    }

}
