package com.htw_berlin.helfert.projekthoe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.htw_berlin.helfert.projekthoe.activity.AddViewActivity;
import com.htw_berlin.helfert.projekthoe.activity.PopUpDescriptionHoeActivity;
import com.htw_berlin.helfert.projekthoe.activity.PopUpDeveloperPhotoActivity;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;
import com.htw_berlin.helfert.projekthoe.fragment.MultiMenueFragmentActivity;


/**
 * Die MainActivity ist der Einstiegspunkt in die App.
 * Diese App soll es dem Benutzer ermoeglichen einfach Menues zu erfassen / Menues anzulegen und
 * zu loeschen. Desweiteren sollen Menues kategorisiert werden koennen.
 */
public class MainActivity extends AppCompatActivity {

    //Datenbank zugriff
    DbHelper dbHelper;

    /**
     * Diese Methode wird bei Start der Activity aufgerufen
     */
    @Override
    protected void onStart()
    {
        super.onStart();
            }

    /**
     * Kommt man von einer anderen Activity wieder zurueck
     * so wird diese Methode aufgerufen, da die MainActivity fortgesetzt wird
     */
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    /**
     * Sobald die MainActivity generiert wird,
     * wird die onCreate-Methode geladen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // App-logo in actionBar einbetten
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_launcher_icon);


    }

    /**
     * weiterAddMenue wertet alle Klicks der MainActivity aus und leitet die
     * entsprechenden Aktionen ein.
     */
    public void weiterAddMenue(View v)
    {
        Intent intentNewMenue = new Intent(getApplicationContext(), AddViewActivity.class);
        startActivity(intentNewMenue);
    }
    /**
     * weiterShowAllMenues wertet alle Klicks der MainActivity aus und leitet die
     * entsprechenden Aktionen ein.
     */
    public void weiterShowAllMenues(View v)
    {
        //Holen der Datenbankinstance
        dbHelper = DbHelper.getInstance(getApplicationContext());
        // Cache -> Listen mit Menues fuellen von DB
        dbHelper.updateMenueLists();
        Intent intentNewMenue = new Intent(getApplicationContext(), MultiMenueFragmentActivity.class);
        startActivity(intentNewMenue);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.menu_new_menue:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                displayToast("neues Menue selected");
                Intent intentNewMenue = new Intent(getApplicationContext(), AddViewActivity.class);
                startActivity(intentNewMenue);
                return true;

            case R.id.menu_description:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                displayToast("Beschreibung Selected");
                Intent intentDescription = new Intent(getApplicationContext(), PopUpDescriptionHoeActivity.class);
                startActivity(intentDescription);

                return true;
            case R.id.menu_developer:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                displayToast("Entwickler Selected");
                Intent intentShowDeveloper = new Intent(getApplicationContext(), PopUpDeveloperPhotoActivity.class);
                startActivity(intentShowDeveloper);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    void displayToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}// end of mainActivity
