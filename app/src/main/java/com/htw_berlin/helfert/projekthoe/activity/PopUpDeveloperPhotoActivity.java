package com.htw_berlin.helfert.projekthoe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.htw_berlin.helfert.projekthoe.R;

/**
 * Diese Klasse setzt ein vordefiniertes Layout (Enwickler-Info) um es dann
 * in ein popup-window einzubetten
 * Created by Steve on 21.10.2015.
 */
public class PopUpDeveloperPhotoActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow_developer);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.6));
    }
}