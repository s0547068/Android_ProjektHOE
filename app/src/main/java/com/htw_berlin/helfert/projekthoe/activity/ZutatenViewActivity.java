package com.htw_berlin.helfert.projekthoe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.htw_berlin.helfert.projekthoe.R;


/**
 * Diese Klasse setzt die Attribue damit dem User die benoetigten Zutaten zu einem ausgewaehlten Menue angezeigt werden koennen
 */
public class ZutatenViewActivity extends AppCompatActivity
{

    /**
     * Decl. and Init Widgets
     */

    /**
     * TextView
     */
    private TextView txtNameOfZutat      = null;
    private TextView txtNameOfMenue      = null;

    /**
     * CheckBox
     */
    private CheckBox chkExisting = null;


    /**
     * Diese Methode wird bei Start der Activity aufgerufen
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        // EditText
        txtNameOfMenue = (TextView) findViewById(R.id.txtNameOfMenue);
        txtNameOfZutat = (TextView) findViewById(R.id.txtNameOfZutat);

        // Zeigt den Text vom intent objekt
        txtNameOfMenue.setText(getIntent().getStringExtra("menueName"));
        txtNameOfZutat.setText(getIntent().getStringExtra("menueZutaten"));
    }

    /**
     * Kommen wir von einer anderen Activity wieder zurueck
     * so wird diese Methode aufgerufen, da die MainActivity fortgesetzt wird
     */
    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    protected void onPause()
    {
        super.onPause();
    }


    /**
     * Sobald die MainActivity generiert wird,
     * wird die onCreate-Methode geladen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Setzen des Layouts der ZutatenViewActivity
        setContentView(R.layout.zutaten_check_view_layout);


        chkExisting = (CheckBox) findViewById(R.id.chkExisting);



    }

    /****************************************** Hilfs- Methoden / Funktionen *******************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}// end of class
