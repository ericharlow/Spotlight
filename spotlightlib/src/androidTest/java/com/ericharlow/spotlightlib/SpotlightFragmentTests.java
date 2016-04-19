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

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ericharlow on 4/18/16.
 */
@SmallTest
@RunWith(AndroidJUnit4.class)
public class SpotlightFragmentTests {

    @Test
    public void shouldCreateFragmentWithSpotlightPoints() {
        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        assertThat("spotlight fragment", spotlightFragment, notNullValue());
    }

    @Test
    public void shouldShowSpotlightFragment() {
        FragmentManager fragmentManager = mock(FragmentManager.class);
        FragmentTransaction fragmentTransaction = mock(FragmentTransaction.class);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);

        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        spotlightFragment.show(fragmentManager, SpotlightFragment.SPOTLIGHT_FRAGMENT_SHOW);
        verify(fragmentTransaction).add(spotlightFragment, SpotlightFragment.SPOTLIGHT_FRAGMENT_SHOW);
    }

    @Test
    public void shouldUpdateSpotlightFragmentWithNewInstance() {
        FragmentManager mockFragmentManager = mock(FragmentManager.class);
        FragmentTransaction mockFragmentTransaction = mock(FragmentTransaction.class);
        SpotlightFragment mockSpotlightFragment = mock(SpotlightFragment.class);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);
        when(mockFragmentManager.findFragmentByTag(SpotlightFragment.SPOTLIGHT_FRAGMENT_SHOW))
                .thenReturn(mockSpotlightFragment);
        when(mockFragmentTransaction.remove(any(Fragment.class))).thenReturn(mockFragmentTransaction);

        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        spotlightFragment.show(mockFragmentManager, SpotlightFragment.SPOTLIGHT_FRAGMENT_SHOW);
        verify(mockFragmentTransaction).remove(mockSpotlightFragment);
    }

    @Test
    public void shouldSaveResourceId() {
        int resourceId = 102;
        SpotlightFragment spotlightFragment = SpotlightFragment.newInstance(resourceId,
                new ArrayList<SpotlightPoint>());
        Bundle bundle = new Bundle();

        spotlightFragment.onSaveInstanceState(bundle);
        assertThat("resource id", bundle.getInt(SpotlightFragment.SPOTLIGHT_FRAGMENT_ID), is(resourceId));
    }

    @Test
    public void shouldUpdateSpotlightFragmentId() {
        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        String test = new String("test string");
        spotlightFragment.setSpotlightFragmentId(test);
        assertThat("", spotlightFragment.getSpotlightFragmentId(), equalTo(test));
    }

    @Test
    public void shouldRemoveSelfOnBackKey() {
        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        final FragmentManager mockFragmentManager = mock(FragmentManager.class);
        FragmentTransaction mockFragmentTransaction = mock(FragmentTransaction.class);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);
        when(mockFragmentTransaction.remove(any(Fragment.class)))
                .thenReturn(mockFragmentTransaction);
        spotlightFragment.setFragmentManagerProvider(new FragmentManagerProvider() {
            public FragmentManager provide() {
                return mockFragmentManager;
            }
        });

        DialogInterface.OnKeyListener onKeyListener = spotlightFragment.createOnBackKeyListener();
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK);
        boolean handled = onKeyListener.onKey(null,KeyEvent.KEYCODE_BACK, keyEvent);
        assertThat("key handled", handled, is(true));
    }

    @Test
    public void shouldNotHandleKeyPress() {
        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        final FragmentManager mockFragmentManager = mock(FragmentManager.class);
        FragmentTransaction mockFragmentTransaction = mock(FragmentTransaction.class);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);
        when(mockFragmentTransaction.remove(any(Fragment.class)))
                .thenReturn(mockFragmentTransaction);
        spotlightFragment.setFragmentManagerProvider(new FragmentManagerProvider() {
            public FragmentManager provide() {
                return mockFragmentManager;
            }
        });

        DialogInterface.OnKeyListener onKeyListener = spotlightFragment.createOnBackKeyListener();
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK);
        boolean handled = onKeyListener.onKey(null,KeyEvent.KEYCODE_0, keyEvent);
        assertThat("key handled", handled, is(false));
    }

    @Test
    public void shouldInflateResourceId() {
        LayoutInflater mockLayoutInflater = mock(LayoutInflater.class);
        int resourceId = R.layout.fragment_spotlight;
        Bundle saved = new Bundle();
        saved.putInt(SpotlightFragment.SPOTLIGHT_FRAGMENT_ID, resourceId);
        SpotlightFragment spotlightFragment = SpotlightFragment.newInstance(0, new ArrayList<SpotlightPoint>());
        spotlightFragment.onCreateView(mockLayoutInflater, null, saved);
        verify(mockLayoutInflater).inflate(resourceId, null);
    }

    @Test
    public void shouldCreate() {
        SpotlightFragment spotlightFragment = getDefaultSpotlightFragment();
        spotlightFragment.onCreate(new Bundle());
    }

    private SpotlightFragment getDefaultSpotlightFragment() {
        return SpotlightFragment.newInstance(new ArrayList<SpotlightPoint>());
    }
}
