package com.ericharlow.spotlight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.ericharlow.spotlightlib.SpotlightFragment;
import com.ericharlow.spotlightlib.SpotlightPoint;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Handle Toolbar
        Toolbar toolbar = inflateToolbarContent();

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentNavigationBar(true)
                .withFullscreen(true)
                .addDrawerItems(
                        new PrimaryDrawerItem(),
                        new SecondaryDrawerItem()
                ).build();

        setSubContentView(R.layout.activity_main);
//        startSpotlightDemo();
    }

    public void onFirstButtonClicked(View v) {
        v.setVisibility(View.GONE);
    }

    public void onSecondButtonClicked(View v) {
        v.setVisibility(View.GONE);
    }

    public void onThirdButtonClicked(View v) {
        v.setVisibility(View.GONE);
    }

    public void onFourthButtonClicked(View v) {
        v.setVisibility(View.GONE);
    }

    public void onNewActivityClicked(View v) {
        v.setVisibility(View.GONE);
        startActivity(new Intent(this, DemoActivity.class));
    }

    public void startSpotlightDemo() {
        ArrayList<SpotlightPoint> points = new ArrayList< SpotlightPoint >();

        // create a list of SpotlightPoints
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder().withText("See What's New!\nTap on the spotlight to continue.").build();
        spotlightPoint.y = 180;
        points.add(spotlightPoint);

        // start SpotlightFragment
        SpotlightFragment coverFragment = SpotlightFragment.newInstance(points);
        coverFragment.show(getFragmentManager(), SpotlightFragment.TAG);
    }

    public void setSubContentView(int layoutResID) {
        ViewGroup container = (ViewGroup) findViewById(R.id.FrameContainer);
        getLayoutInflater().inflate(layoutResID, container, true);
    }

    protected Toolbar inflateToolbarContent() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        ViewStub stub = (ViewStub) toolbar.findViewById(R.id.ToolbarStub);
        stub.setLayoutResource(R.layout.toolbar_default);
        stub.inflate();
        return toolbar;
    }

}
