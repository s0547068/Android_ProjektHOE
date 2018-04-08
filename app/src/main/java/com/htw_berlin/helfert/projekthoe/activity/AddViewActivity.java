package com.htw_berlin.helfert.projekthoe.activity;


import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupHelper;
import android.app.backup.BackupManager;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.htw_berlin.helfert.projekthoe.R;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;
import com.htw_berlin.helfert.projekthoe.objects.Menue;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Die AddViewActivity setzt das Layout für die Aufnahme eines neues Menues.
 * Desweiteren werden MenueObjekte generiert und weitere Aktionen zur Speicherung
 * in die DB veranlasst. Zudem erfolgen unterschiedliche Ueberpruefungen (CheckUserInput)
 * und das automatische Setzen des Erfassungsdatums und des Typs.
 * Created by Steve on 17.10.2015.
 */
public class AddViewActivity extends AppCompatActivity {

        DbHelper dbHelper;
        /**
         * Benoetigte Widgets fuer die Datenverarbeitung aller Widgets
         */
        // Attribute
        private AddViewActivity addViewActivity = null;

        private int menueID = 0;
        private int zutatID = 0;
        private int einheitID;
        private EditText txtCurrentDate      = null;
        private EditText txtInputMenueName   = null;
        private EditText txtZutaten          = null;
        private EditText txtAnzahl           = null;
        private EditText txtDescription      = null;
        // Typ
        private RadioButton rdbBreakfast = null;
        private RadioButton rdbLunch = null;
        private RadioButton rdbEvening = null;
        // Buttons
        private Button cmdAddMenueNew = null;
        private Button cmdAddPhoto = null;
        // ImageView
        private ImageView takeAPhoto = null;
        // Backup
        private BackupManager bm;

        public static final String NAME = AddViewActivity.class.getSimpleName();
        private static final int CAM_REQUEST = 1313;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // sharedPref- KONSTANTEN
        private static final String RBT1 = "frueh";
        private static final String RBT2 = "mittag";
        private static final String RBT3 = "abend";
        private static final String EDITTXTNAME = "edtxtName";
        private static final String EDITTXTDESCRIPTION = "edtxtDescrpition";
        private static final String EDITTXTZUTATEN = "edtxtZutaten";
        private static final String EDITTXTEDATE = "edtxtDate";
        private static final String EDITTXTVALUE = "edtxtValue";

        private Spinner zutatenSpinner = null;
        ArrayAdapter<CharSequence> adapter;



        /**
         * Standard Konstruktor
         */
        public AddViewActivity()
        {
            // Hier gibt es nichts zu tun
        }

    /**
     * Sobald die AddViewActivity generiert wird,
     * wird die onCreate-Methode geladen.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setzen des Layouts der MainActivity
        setContentView(R.layout.activity_add);

        // App-logo in actionBar einbetten
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_launcher_icon);

        // Alle Widget-Referenzen generieren
        generateWidgetReferences();

        //spinner adapter - layout
        adapter = ArrayAdapter.createFromResource(this, R.array.zutatenArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zutatenSpinner.setAdapter(adapter);

        // SharedPref
        bm = new BackupManager(this);

    }

    /****************************************** Hilfs- Methoden / Funktionen *******************************************************/

    // Folgeaktionen bei Klick auf Menue hinzufuegen (->AddViewActivity)
    public void menueHinzufuegen(View view)
    {
        // Menue abspeichern
        addMenueNew();
    }

    // Intent fuer Kamera starten
    public void machEinMenuePhoto(View view)
    {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camaraIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(camaraIntent, CAM_REQUEST);
        }
    }

    /**
     * Diese Methode setzt ein aufgenommenes Photo
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            // convertieren von bitmap zu byte array um es spaeter in der Db ablegen zukoennen
            image.compress(Bitmap.CompressFormat.PNG, 0, stream);
            // Setzen des aufgenommenen Photos
            takeAPhoto.setImageBitmap(image);
        }
    }


    /**
     * ******************************************************************************************************************************************
     * Diese Methode fuegt ein neues Menue der DB zu,
     * Und Sie taetigt alle notwendigen Ueberpruefungen:
     * - User-Eingaben
     * - ob der Dateinamen bereits vorhanden ist
     * ******************************************************************************************************************************************
     */
    private void addMenueNew()
    {
        Log.d("Funktion neues Menue" , "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        // Eingabedaten ueberpruefen
        if(checkUserInput())
        {
            //Init / Decl und Setzen der Attribute

            String strCurrentDate = txtCurrentDate.getText().toString();
            String strInputMenueName = txtInputMenueName.getText().toString();
            String strTypName = getTypName();
            int intTypID = getTypID();
            String strAnzahl = txtAnzahl.getText().toString();
            String strZutaten = txtZutaten.getText().toString();
            String strDescription = txtDescription.getText().toString();
            byte[] photo = stream.toByteArray();
            String spinnerText = zutatenSpinner.getSelectedItem().toString();


            // Checken, ob der Menuename bereits vorhanden ist
            if (!this.menueNameAlreadyExists(strInputMenueName))
            {
                try
                {
                    // Generierung des Menues und Setzen der Attribute
                    DbHelper helper = dbHelper.getInstance(getApplicationContext());
                    helper.createMenue(strCurrentDate, strInputMenueName, photo, strZutaten, strDescription, strTypName, intTypID);

                    Log.d("Liste der Menues", helper.getALL_MENUES().toString());

                    // UserHinweis Erfassen war erfolgreich
                    Toast.makeText(this, "Neues Menü erfasst",
                            Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Log.d("Fehlermeldung",ex.toString());

                    // UserHinweis Hinzufuegen fehlgeschlagen
                    Toast.makeText(this, "Menü konnte nicht erfasst werden",
                            Toast.LENGTH_LONG).show();
                }

                // Resetten der Widgets Texte
                resetTexts();

                // EditViewActivity beenden
                // danach springt die Anzeigen-Activity wieder zurueck auf die MainActivity, da sie miteinander gekoppelt ist -> onResume()
                this.finish();

            }
            else
            {
                // UserMsg Dateiname existiert bereits!
                Toast.makeText(this, "Menüname existiert bereits!",
                        Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            // UserMsg Menuename, Zutaten und Beschreibung ausfuellen
            Toast.makeText(this, "Bitte Menüname, Zutaten und Beschreibung ausfüllen",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Diese Methode gibt das aktuelle Datum zurueck.
     * Sie passt es auf die deutsche Schreibweise mit Punkten an.
     */
    private String getCurrentDate() {
        // Decl. and Init
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date currentTime = new Date();

        return formatter.format(currentTime);

    }

    /**
     * Diese Methode generiert die Referenzen zu
     * den Widgets in der MainActivity
     */
    private void generateWidgetReferences()
    {
        /*
        * Widgetreferenzen generieren
         */
        // Datum
        txtCurrentDate = (EditText) this.findViewById(R.id.txtCurrentDate);
        txtInputMenueName = (EditText) this.findViewById(R.id.txtInputMenueName);
        txtZutaten = (EditText) this.findViewById(R.id.txtZutaten);
        txtAnzahl = (EditText) this.findViewById(R.id.txtWert);
        txtDescription = (EditText) this.findViewById(R.id.txtDescription);
        // Typ
        rdbBreakfast = (RadioButton) this.findViewById(R.id.rdbBreakfast);
        rdbLunch = (RadioButton) this.findViewById(R.id.rdbLunch);
        rdbEvening = (RadioButton) this.findViewById(R.id.rdbEvening);
        // Buttons
        cmdAddMenueNew = (Button) this.findViewById(R.id.cmdAddMenueNew);
        cmdAddPhoto = (Button) this.findViewById(R.id.cmdAddPhoto);
        // ImageView
        takeAPhoto = (ImageView) this.findViewById(R.id.photo);
        // spinner
        zutatenSpinner = (Spinner) findViewById(R.id.zutatenSpinner);

    }


    /**
     * Diese Funktion prueft, ob der Menuename, Zutaten, Beschreibung eingetragen ist.
     *
     * @return valid : boolean : true : alles eingeben
     */
    private boolean checkUserInput()
    {
        // Decl. and Init
        boolean valid = true;
        String strInputMenueName = txtInputMenueName.getText().toString();
        String strZutaten = txtZutaten.getText().toString();
        String strDescrition = txtDescription.getText().toString();
        // Ueberpruefen, ob beide Felder ausgefuellt sind. --> LeerString??
        if (strInputMenueName.equalsIgnoreCase("") || strZutaten.equalsIgnoreCase("") || strDescrition.equalsIgnoreCase(""))
        {
            valid = false;
        }
        return valid;
    }

    /**
     * Diese Funktion gibt den Typ des Menues als String zurueck,
     * in dem Sie alle RadioButtons ueberprueft. Basierend auf der RadioGroup
     * kann nur ein RadioButton geklickt werden.
     *
     * @return String : Text des gecheckten RadioButtons
     */
    private String getTypName()
    {
        // Decl. and Init
        String strTyp = "";

        if (rdbBreakfast.isChecked())
        {
            strTyp = rdbBreakfast.getText().toString();
        }

        if (rdbLunch.isChecked())
        {
            strTyp = rdbLunch.getText().toString();
        }

        if (rdbEvening.isChecked())
        {
            strTyp = rdbEvening.getText().toString();
        }

        return strTyp;
    }

    /**
     * Diese Funktion gibt den Typ des Menues als String zurueck,
     * in dem Sie alle RadioButtons ueberprueft. Basierend auf der RadioGroup
     * kann nur ein RadioButton geklickt werden.
     *
     * @return int : ID des gecheckten RadioButtons
     */
    private int getTypID()
    {
        // Decl. and Init
        int typID = 0;

        if (rdbBreakfast.isChecked())
        {
            typID = 1;
        }

        if (rdbLunch.isChecked())
        {
            typID = 2;
        }

        if (rdbEvening.isChecked())
        {
            typID = 3;
        }

        return typID;
    }


    /**
     * Diese Methode loescht die Eingabewerte
     */
    private void resetTexts()
    {
        // Resetten der Eingabefelder
        txtCurrentDate.setText(getCurrentDate());
        txtInputMenueName.setText("");
        txtZutaten.setText("");
        txtDescription.setText("");
        txtAnzahl.setText("");
        takeAPhoto.setImageBitmap(null);
    }

    /**
     * Diese Methode wird bei Start der Activity aufgerufen
     */
    @Override
    protected void onStart() {
        super.onStart();

    }
    /**
     * Kommen wir von einer anderen Activity wieder zurueck
     * so wird diese Methode aufgerufen, da die MainActivity fortgesetzt wird
     */
    @Override
    protected void onResume() {
        super.onResume();

        // wiederherstellen der Attribute
        SharedPreferences sharedPref = this.getSharedPreferences(NAME, MODE_PRIVATE);
        rdbBreakfast.setChecked(sharedPref.getBoolean(RBT1, false));
        rdbLunch.setChecked(sharedPref.getBoolean(RBT2, false));
        rdbEvening.setChecked(sharedPref.getBoolean(RBT3, false));
        txtCurrentDate.setText(getCurrentDate());
        txtInputMenueName.setText(sharedPref.getString(EDITTXTNAME, ""));
        txtDescription.setText(sharedPref.getString(EDITTXTDESCRIPTION, ""));
        txtAnzahl.setText(sharedPref.getString(EDITTXTVALUE, ""));
        txtZutaten.setText(sharedPref.getString(EDITTXTZUTATEN, ""));

        bm.dataChanged();
    }
    @Override
    protected void onPause() {
        super.onPause();

        // sichern der Attribute
        SharedPreferences sharedPref = this.getSharedPreferences(NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor;
        editor = sharedPref.edit();
        editor.putBoolean(RBT1, rdbBreakfast.isChecked());
        editor.putBoolean(RBT2, rdbLunch.isChecked());
        editor.putBoolean(RBT3, rdbEvening.isChecked());
        editor.putString(EDITTXTNAME, txtInputMenueName.getText().toString());
        editor.putString(EDITTXTEDATE, txtCurrentDate.getText().toString());
        editor.putString(EDITTXTDESCRIPTION, txtDescription.getText().toString());
        editor.putString(EDITTXTZUTATEN, txtZutaten.getText().toString());
        editor.putString(EDITTXTVALUE, txtAnzahl.getText().toString());

        editor.apply(); // statt commit

        bm.dataChanged();
    }
    public class BackupAgent extends BackupAgentHelper
    {
        @Override
        public void onCreate() {
            super.onCreate();

            BackupHelper myHelper = new SharedPreferencesBackupHelper( this, AddViewActivity.NAME);
            addHelper( myHelper.getClass().getName(), myHelper);
        }
    }


    /**
     * Diese Funktion ueberprueft, ob der Menue bereits vorhanden ist.
     *
     * @param strInputMenueName : String : Menuename
     *
     * @return menueNameExists : false = nicht vorhanden
     */
    public boolean menueNameAlreadyExists(String strInputMenueName)
    {
        // Delc and Init
        boolean menueNameExists = false;
        DbHelper helper = dbHelper.getInstance(getApplicationContext());

        // Wenn die Liste leer ist kann nicht ueberprueft werden
        if (!helper.getALL_MENUES().isEmpty())
        {
            for (Menue menue : helper.getALL_MENUES())
            {
                if (menue.getStrInputMenueName().equalsIgnoreCase(strInputMenueName))
                {
                    menueNameExists = true;
                }
            }
        }
        else
        {
            menueNameExists = false;
        }

        return menueNameExists;
    }

}// end of class
