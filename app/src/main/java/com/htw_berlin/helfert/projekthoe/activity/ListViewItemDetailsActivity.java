package com.htw_berlin.helfert.projekthoe.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.htw_berlin.helfert.projekthoe.R;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;
import com.htw_berlin.helfert.projekthoe.fragment.MultiMenueFragmentActivity;


/**
 * Created by Steve on 29.10.2015.
 */
public class ListViewItemDetailsActivity extends AppCompatActivity
{
    private DbHelper dbHelper;
    /*
    * Decl. and Init Attribute - Widgets
    */

    /*
    * Buttons
     */
    private Button cmdDeleteThisMenue = null;
    private Button cmdShowEk = null;

    /*
    * RadioButtons
    */
    private RadioButton rdbEditBreakfast = null;
    private RadioButton rdbEditLunch = null;
    private RadioButton rdbEditEvening = null;

    /*
    * EditTexts
     */
    private TextView txtEditMenueName = null;
    private TextView txtEditZutaten = null;
    private TextView txtEditDescription = null;
    private TextView txtEditDate = null;
    private String typ;
    private ImageView txtEditMenuePhoto = null;
    private Bitmap bitmap = null;


    @Override
    protected void onStart()
    {
        super.onStart();

        // EditText
        txtEditMenueName = (TextView) findViewById(R.id.txtEditInputMenueName);
        txtEditDate = (TextView) findViewById(R.id.txtEditCurrentDate);
        txtEditZutaten = (TextView) findViewById(R.id.txtEditZutaten);
        txtEditDescription = (TextView) findViewById(R.id.txtEditDescription);
        txtEditMenuePhoto = (ImageView) findViewById(R.id.EditImageView);

        // Zeigt den Text vom intent objekt
        txtEditMenueName.setText(getIntent().getStringExtra("menueName"));
        txtEditDate.setText(getIntent().getStringExtra("menueDatum"));
        txtEditDescription.setText(getIntent().getStringExtra("menueBeschreibung"));
        txtEditZutaten.setText(getIntent().getStringExtra("menueZutaten"));

        typ = getIntent().getStringExtra("menueTyp");
        if(typ.equals("Frühstück"))
        {
            rdbEditBreakfast.setChecked(true);
        }
        if(typ.equals("Mittag"))
        {
            rdbEditLunch.setChecked(true);
        }
        if(typ.equals("Abend"))
        {
            rdbEditEvening.setChecked(true);
        }

        // convertieren von byte array zu bitmap und Setzen des Photos auf Activity
        Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("menuePhoto"), 0, getIntent().getByteArrayExtra("menuePhoto").length);
        this.txtEditMenuePhoto.setImageBitmap(bitmap);

    }

    /**
     * Kommt man von einer anderen Activity wieder zurueck
     * so wird diese Methode aufgerufen, da die ListViewItemDetailActivity fortgesetzt wird
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
     * Sobald die ListViewItemDetailsActivity generiert wird,
     * wird die onCreate-Methode geladen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Setzen des Layouts der ListViewItemDetailsActivity -> braucht xlm-layout
        setContentView(R.layout.list_view_item_details_activity_layout);

        /**
         * Generieren der Widgets
         */

        // Buttons


        cmdDeleteThisMenue = (Button) findViewById(R.id.cmdDeleteThisMenue);
        //Loeschen eines Menues
        cmdDeleteThisMenue.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                String name = txtEditMenueName.getText().toString();
                Log.d("MenueName: ", name);
                DbHelper helper = dbHelper.getInstance(getApplicationContext());
                int menueID = helper.getMenueID(name);
                int id = menueID;
                Log.d("MenueId: ", String.valueOf(id));

                helper.deleteMenue(id);

                Intent intentNewMenue = new Intent(getApplicationContext(), MultiMenueFragmentActivity.class);
                startActivity(intentNewMenue);
            }
        });
        cmdShowEk = (Button) findViewById(R.id.cmdShowEk);
        cmdShowEk.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                showZutaten(v);
            }
        });


        // RadioButtons
        rdbEditBreakfast = (RadioButton) findViewById(R.id.rdbEditBreakfast);
        rdbEditLunch = (RadioButton) findViewById(R.id.rdbEditLunch);
        rdbEditEvening = (RadioButton) findViewById(R.id.rdbEditEvening);
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
        return super.onOptionsItemSelected(item);
    }

        /**
         * ***********************************************************************************************************************************
         * Diese Methode ruft eine neue Activity auf und zeigt die benoetigten Zutaten zu dem entsprechendem Menue
         * ***********************************************************************************************************************************
         */
        private void showZutaten(View v)
        {
            try
            {
                // Eingaben checken
                if (checkUserInput())
                {

                    String menueName = txtEditMenueName.getText().toString();
                    Log.d("Text menueName!!!! ", menueName);
                    String menueZutaten = txtEditZutaten.getText().toString();
                    Log.d("Text Zutaten!!!! ", menueZutaten);
                    // EditView in einem Intent kapseln
                    Intent intentZutatenViewActivity = new Intent(v.getContext(), ZutatenViewActivity.class);
                    intentZutatenViewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentZutatenViewActivity.putExtra("menueName", menueName);
                    intentZutatenViewActivity.putExtra("menueZutaten", menueZutaten);
                    // Intent starten durch geerbte Funktion startActivity
                    v.getContext().startActivity(intentZutatenViewActivity);

                    // UserMsg, das die Liste leer ist
                    Toast.makeText(this, "Hier sehen Sie alle benötigten Zutaten zu diesem Menü",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "Bitte einen Menünamen, Zutaten und eine Beschreibung eingeben",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                Log.d("Exception!!!! ", e.toString());
                Toast.makeText(this, "Die Einkaufsliste konnte nicht geöffnet werden",
                        Toast.LENGTH_LONG).show();
            }
        }

        /**
         * **********************************************************************************************************************
         * Diese Funktion ueberprueft, ob
         * die Textfelder fuer den Menuenamen, Zutaten
         * und die Beschreibung selbst ausgefuellt sind.
         *
         * @return : boolean : true : ausgefuellt
         * **********************************************************************************************************************
         */
        private boolean checkUserInput()
        {
            // Decl. and Init
            boolean valid = true;

            // Felder duerfen nicht leer sein
            if (txtEditMenueName.getText().toString().equals(
                    "") || txtEditZutaten.getText().toString().equals("")
                    || txtEditDescription.getText().toString().equals(""))
            {
                valid = false;
            }

            return valid;
        }


        /**
         * Diese Funktion gibt den Typ des Menues
         * als String zurueck, in dem Sie alle RadioButtons ueberprueft.
         * Basierend auf der RadioGroup kann nur ein RadioButton geklickt werden.
         *
         * @return String : Text des gecheckten RadionButtons
         */
        private String getTyp()
        {
            // Decl and Init
            String strTyp = "";

            if (rdbEditBreakfast.isChecked())
            {
                strTyp = rdbEditBreakfast.getText().toString();
            }

            if (rdbEditLunch.isChecked())
            {
                strTyp = rdbEditLunch.getText().toString();
            }
            if (rdbEditEvening.isChecked())
            {
                strTyp = rdbEditEvening.getText().toString();
            }

            return  strTyp;
        }
    }// End of Class




