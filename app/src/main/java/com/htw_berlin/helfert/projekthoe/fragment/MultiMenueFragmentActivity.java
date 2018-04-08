package com.htw_berlin.helfert.projekthoe.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.htw_berlin.helfert.projekthoe.R;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;

/**
 * Die MultiMenueFragmentActivity setzt das Layout fuer die Fragmente
 */
public class MultiMenueFragmentActivity extends AppCompatActivity {


    public MultiMenueFragmentActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Setzen der Container - Layout
        setContentView(R.layout.fragment_multi_menue);

        // App-logo in actionBar einbetten
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_launcher_icon);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.menueContainer, new MenueFragment()).commit();
        }
    }


}
