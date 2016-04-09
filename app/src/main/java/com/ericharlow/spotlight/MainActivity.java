package com.ericharlow.spotlight;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}
