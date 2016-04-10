package com.ericharlow.spotlight;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ericharlow.spotlightlib.CoverFragment;
import com.ericharlow.spotlightlib.SpotlightPoint;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        SpotlightPoint spotlightPoint = new SpotlightPoint.Builder().build();
        points.add(spotlightPoint);

        // start CoverFragment
        CoverFragment coverFragment = CoverFragment.newInstance(points);
        coverFragment.show(getFragmentManager(), CoverFragment.TAG);
    }
}
