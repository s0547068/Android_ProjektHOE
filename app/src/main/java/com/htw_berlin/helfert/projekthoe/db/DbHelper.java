package com.htw_berlin.helfert.projekthoe.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.htw_berlin.helfert.projekthoe.objects.Einheit;
import com.htw_berlin.helfert.projekthoe.objects.Kueche;
import com.htw_berlin.helfert.projekthoe.objects.Menue;
import com.htw_berlin.helfert.projekthoe.objects.Typ;
import com.htw_berlin.helfert.projekthoe.objects.Zutat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Deklaration eines DB-Schemas
 * Herstellung einer einheitlichen DB-Organisation
 * Created by Steve on 22.10.2015.
 */
public class DbHelper extends SQLiteOpenHelper
{
    //Der Cache für die Datenbank besteht aus mehreren ArrayListen die vorgehalten werden und regelmäßig geupdatet werden.
    public final ArrayList<Menue> ALL_MENUES = new ArrayList<>();
    public final ArrayList<Menue> BREAKFAST_MENUES = new ArrayList<>();
    public final ArrayList<Menue> LUNCH_MENUES = new ArrayList<>();
    public final ArrayList<Menue> EVENING_MENUES = new ArrayList<>();

    //Datenbank instanz die von anderen daos geholt wird
    private static DbHelper instance = null;

    public static final String TAG_DBHelper = "DBHelper";
    // / die DB-Versionsnummer hochsetzen, wenn man DB aendert
    // die Version dient dazu Aenderungen an der DB vorzunehmen und somit eine neue Version zu erstellen
    private static final int DB_VERSION = 3;
    // Name der DB
    private static final String DB_NAME = "homeofeat";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BLOB_TYPE = " BLOB";
    private static final String COMMA_SEP = ",";


    /**
     * Deklaration eines DB-Schemas (-Organisation der DB)
     * Definition der Tabelleninhalte mittels innerer Klassen
     */
    /*****************************************************************************************/
    // Tabelle: "menue"
    /*****************************************************************************************/
    /* Inner class that defines the TableMenue contents */
    // Tabelle "menue"
    public static abstract class TableMenue implements BaseColumns
    {
        public static final String TABLE_NAME = "menue";
        public static final String COLUMN_NAME_MENUEID = "menueID";
        public static final String COLUMN_NAME_MENUENAME = "menueName";
        public static final String COLUMN_NAME_MENUEDATE = "menueDatum";
        public static final String COLUMN_NAME_MENUEDESCRIPTION = "menueBeschreibung";
        public static final String COLUMN_NAME_MENUEPHOTO = "menuePhoto";
        public static final String COLUMN_NAME_MENUEZUTATEN = "menueZutaten";
        public static final String COLUMN_NAME_TYPNAME = "menueTyp";
        public static final String COLUMN_NAME_TYPID = "typIDvonTypTable";
    }
    // execSQL - String zum Erstellen der "menue"Tabelle
    private static final String SQL_CREATE_TABLEMENUE =
            "CREATE TABLE " + TableMenue.TABLE_NAME + "(" +
                    TableMenue.COLUMN_NAME_MENUEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableMenue.COLUMN_NAME_MENUENAME + TEXT_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_MENUEDATE + TEXT_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_MENUEPHOTO + BLOB_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_TYPNAME + TEXT_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_MENUEDESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_MENUEZUTATEN + TEXT_TYPE + COMMA_SEP +
                    TableMenue.COLUMN_NAME_TYPID+");";

    private static final String SQL_DELETE_TABLEMENUE =
            "DROP TABLE IF EXISTS " + TableMenue.TABLE_NAME;





    //
    // nachfolgende Tabellen werden zwar im Db-Schema angelegt und stehen zur Verfuegung sind
    // jedoch noch nicht in der Application einbezogen. Alle Informationen werden ersteinmal in der
    // Tabelle Menue abgespeichert und abgefragt
    // Die benoetigten Objekte (siehe package) mit entsprechenden Attributen sind bereits angelegt
    // Auch viele benoetigte Methoden zu den folgend aufgefuehrten Klassen sind bereits in der DbHelper.class
    // hinterlegt und koennten verwendet werden.
    //
    /*****************************************************************************************/
    // Tabelle: "menuetyp"
    /*****************************************************************************************/
  /* Inner class that defines the TableMenueTyp contents */
    // Tabelle "menuetyp"
    public static abstract class TableMenueTyp implements BaseColumns {
        public static final String TABLE_NAME = "menuetyp";
        public static final String COLUMN_NAME_TYPID = "typID";
        public static final String COLUMN_NAME_TYPBEZ = "typBez";
    }
    // execSQL - String zum Erstellen der "menuetyp"Tabelle
    private static final String SQL_CREATE_TABLEMENUETYP =
            "CREATE TABLE " + TableMenueTyp.TABLE_NAME + "(" +
                    TableMenueTyp.COLUMN_NAME_TYPID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableMenueTyp.COLUMN_NAME_TYPBEZ + TEXT_TYPE +");";

    private static final String SQL_DELETE_TABLEMENUETYP =
            "DROP TABLE IF EXISTS " + TableMenueTyp.TABLE_NAME;

    /*****************************************************************************************/
    // Tabelle: "menue_zutat"
    /*****************************************************************************************/
  /* Inner class that defines the TableMenueZutat contents */
    // Tabelle "menue" + "zutat"
    public static abstract class TableMenueZutat implements BaseColumns {
        public static final String TABLE_NAME = "menue_zutat";
        public static final String COLUMN_NAME_MENUEID = "menueID";
        public static final String COLUMN_NAME_ZUTATID = "zutatID";
        public static final String COLUMN_NAME_ANZAHLBENOETIGT = "anzahlBenoetigt";
    }
    // execSQL - String zum Erstellen der "menue_zutat"Tabelle
    private static final String SQL_CREATE_TABLEMENUEZUTAT =
            "CREATE TABLE " + TableMenueZutat.TABLE_NAME + "("
                    + TableMenueZutat.COLUMN_NAME_MENUEID + INTEGER_TYPE + COMMA_SEP
                    + TableMenueZutat.COLUMN_NAME_ZUTATID + INTEGER_TYPE + COMMA_SEP
                    + TableMenueZutat.COLUMN_NAME_ANZAHLBENOETIGT + INTEGER_TYPE + COMMA_SEP
                    + "FOREIGN KEY("+TableMenueZutat.COLUMN_NAME_MENUEID+") REFERENCES "+TableMenueZutat.TABLE_NAME+"("+TableMenueZutat.COLUMN_NAME_MENUEID+") ON DELETE CASCADE, "
                    + "FOREIGN KEY("+TableMenueZutat.COLUMN_NAME_ZUTATID+") REFERENCES "+TableMenueZutat.TABLE_NAME+"("+TableMenueZutat.COLUMN_NAME_ZUTATID+") ON DELETE CASCADE);";

    private static final String SQL_DELETE_TABLEMENUEZUTAT =
            "DROP TABLE IF EXISTS " + TableMenueZutat.TABLE_NAME;

    /*****************************************************************************************/
    // Tabelle: "zutat"
    /*****************************************************************************************/
  /* Inner class that defines the TableZutat contents */
    // Tabelle "zutat"
    public static abstract class TableZutat implements BaseColumns {
        public static final String TABLE_NAME = "zutat";
        public static final String COLUMN_NAME_ZUTATID = "zutatID";
        public static final String COLUMN_NAME_ZUTATBEZ = "zutatBez";
        public static final String COLUMN_NAME_EINHEITID = "einheitID";
    }
    // execSQL - String zum Erstellen der "zutat"Tabelle
    private static final String SQL_CREATE_TABLEZUTAT =
            "CREATE TABLE " + TableZutat.TABLE_NAME + "(" +
                    TableZutat.COLUMN_NAME_ZUTATID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableZutat.COLUMN_NAME_ZUTATBEZ + TEXT_TYPE + COMMA_SEP +
                    TableZutat.COLUMN_NAME_EINHEITID+");";
                   // "FOREIGN KEY("+TableZutat.COLUMN_NAME_EINHEITID+") REFERENCES "+ TableEinheit.TABLE_NAME+"("+TableEinheit.COLUMN_NAME_EINHEITID+")ON DELETE CASCADE);";
    private static final String SQL_DELETE_TABLEZUTAT =
            "DROP TABLE IF EXISTS " + TableZutat.TABLE_NAME;

    /*****************************************************************************************/
    // Tabelle: "kueche"
    /*****************************************************************************************/
  /* Inner class that defines the TableKueche contents */
    // Tabelle "kueche"
    public static abstract class TableKueche implements BaseColumns {
        public static final String TABLE_NAME = "kueche";
        public static final String COLUMN_NAME_KUECHEID = "kuecheID";
        public static final String COLUMN_NAME_ANZAHLVORHANDEN = "anzahlVorhanden";
        public static final String COLUMN_NAME_ZUTATID = "zutatID";
    }
    // execSQL - String zum Erstellen der "kueche"Tabelle
    private static final String SQL_CREATE_TABLEKUECHE =
            "CREATE TABLE " + TableKueche.TABLE_NAME + "(" +
                    TableKueche.COLUMN_NAME_KUECHEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableKueche.COLUMN_NAME_ANZAHLVORHANDEN + INTEGER_TYPE + COMMA_SEP +
                    TableKueche.COLUMN_NAME_ZUTATID + INTEGER_TYPE +");";

    private static final String SQL_DELETE_TABLEKUECHE =
            "DROP TABLE IF EXISTS " + TableKueche.TABLE_NAME;

    /*****************************************************************************************/
    // Tabelle: "einheit"
    /*****************************************************************************************/
  /* Inner class that defines the TableEinheit contents */
    // Tabelle "einheit"
    public static abstract class TableEinheit implements BaseColumns {
        public static final String TABLE_NAME = "einheit";
        public static final String COLUMN_NAME_EINHEITID = "einheitID";
        public static final String COLUMN_NAME_EINHEITBEZEICHNUNG = "einheitBez";

    }
    // execSQL - String zum Erstellen der "einheit"Tabelle
    private static final String SQL_CREATE_TABLEEINHEIT =
            "CREATE TABLE " + TableEinheit.TABLE_NAME + "(" +
                    TableEinheit.COLUMN_NAME_EINHEITID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableEinheit.COLUMN_NAME_EINHEITBEZEICHNUNG + TEXT_TYPE +");";

    private static final String SQL_DELETE_TABLEEINHEIT =
            "DROP TABLE IF EXISTS " + TableEinheit.TABLE_NAME;

    /*****************************************************************************************/



    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Methode zum holen der Instanze oder erzeugen der Instanz
    public static synchronized DbHelper getInstance(Context context){

        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d("Datenbankzugriff", " onCreate");

        //ForeinKey-Freigabe
        db.execSQL("PRAGMA foreign_keys = ON;");
        //Erstellen der "menuetyp"Tabelle
        db.execSQL(SQL_CREATE_TABLEMENUETYP);
        //Erstellen der "menue"Tabelle
        db.execSQL(SQL_CREATE_TABLEMENUE);
        //Erstellen der "menue_zutat"Tabelle -> Zwischentabelle als n:m Beziehung von menue und zutat
        db.execSQL(SQL_CREATE_TABLEMENUEZUTAT);
        //Erstellen der "zutat"Tabelle
        db.execSQL(SQL_CREATE_TABLEZUTAT);
        //Erstellen der "kueche"Tabelle
        db.execSQL(SQL_CREATE_TABLEKUECHE);
        //Erstellen der "menue"Tabelle
        db.execSQL(SQL_CREATE_TABLEEINHEIT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(TAG, "Upgrading der Datenbank von Version " + oldVersion + " zu " + newVersion);
        db.execSQL(SQL_DELETE_TABLEMENUE);
        db.execSQL(SQL_DELETE_TABLEMENUETYP);
        db.execSQL(SQL_DELETE_TABLEZUTAT);
        db.execSQL(SQL_DELETE_TABLEKUECHE);
        db.execSQL(SQL_DELETE_TABLEEINHEIT);
        db.execSQL(SQL_DELETE_TABLEMENUEZUTAT);

        onCreate(db);
    }

    //------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------
// MENUE

    private String[] mAllColumns = {
            DbHelper.TableMenue.COLUMN_NAME_MENUEID,
            DbHelper.TableMenue.COLUMN_NAME_MENUENAME,
            DbHelper.TableMenue.COLUMN_NAME_MENUEDATE,
            DbHelper.TableMenue.COLUMN_NAME_MENUEDESCRIPTION,
            DbHelper.TableMenue.COLUMN_NAME_MENUEPHOTO,
            DbHelper.TableMenue.COLUMN_NAME_MENUEZUTATEN,
            DbHelper.TableMenue.COLUMN_NAME_TYPNAME,
            DbHelper.TableMenue.COLUMN_NAME_TYPID};


    //Menue wird in der Datenbank erstellt
    public void createMenue(String strDateOfAdding, String strInputMenueName, byte[] photo ,
                            String strZutaten, String strDescription, String strTypName, int typID)
    {
        Log.d("createMENUE", ": wird ausgeführt");
        Log.d("Werte byte[]: ", photo.toString() );
        SQLiteDatabase mDatabase = instance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TableMenue.COLUMN_NAME_MENUENAME, strInputMenueName);
        values.put(DbHelper.TableMenue.COLUMN_NAME_MENUEPHOTO, photo);
        values.put(DbHelper.TableMenue.COLUMN_NAME_MENUEDATE, strDateOfAdding);
        values.put(DbHelper.TableMenue.COLUMN_NAME_MENUEDESCRIPTION, strDescription);
        values.put(DbHelper.TableMenue.COLUMN_NAME_MENUEZUTATEN, strZutaten);
        values.put(DbHelper.TableMenue.COLUMN_NAME_TYPNAME, strTypName);
        values.put(DbHelper.TableMenue.COLUMN_NAME_TYPID, typID);

        Log.d("values", ": gesetzt");

        // insertbefehl
        mDatabase.insert(DbHelper.TableMenue.TABLE_NAME, null, values);
        Log.d("insert", ": durchgefuehrt");

        mDatabase.close();
        Log.d("db", ": geschlossen");
        Menue neuesMenue = new Menue(getMenueID(strInputMenueName), photo, strDateOfAdding, strInputMenueName, strTypName, strDescription, strZutaten);
        Log.d("neues Menue", ": erstellt");
        ALL_MENUES.add(neuesMenue);
        if(neuesMenue.getStrTypMenue().equals("Frühstück"))
        {
            BREAKFAST_MENUES.add(neuesMenue);
            Log.d("BREAKFAST_MENUES", BREAKFAST_MENUES.toString());
        }
        if(neuesMenue.getStrTypMenue().equals("Mittag"))
        {
            LUNCH_MENUES.add(neuesMenue);
            Log.d("LUNCH_MENUES", LUNCH_MENUES.toString());
        }
        if(neuesMenue.getStrTypMenue().equals("Abend"))
        {
            EVENING_MENUES.add(neuesMenue);
            Log.d("EVENING_MENUES", EVENING_MENUES.toString());
        }

    }

    //Der Cache für die Menues wird in der jeweiligen Liste geladen
    public void updateMenueLists(){
        Log.d("updateMenueLists","; Liste wird geupdatet");

        ALL_MENUES.clear();
        BREAKFAST_MENUES.clear();
        LUNCH_MENUES.clear();
        EVENING_MENUES.clear();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TableMenue.TABLE_NAME, null);

        Menue menue;

        if (cursor.moveToFirst()){
            do{
                menue = new Menue(cursor.getInt(cursor.getColumnIndex("menueID")), cursor.getBlob(cursor.getColumnIndex("menuePhoto")),
                        cursor.getString(cursor.getColumnIndex("menueDatum")), cursor.getString(cursor.getColumnIndex("menueName")),
                        cursor.getString(cursor.getColumnIndex("menueTyp")), cursor.getString(cursor.getColumnIndex("menueBeschreibung")),
                        cursor.getString(cursor.getColumnIndex("menueZutaten")));

                ALL_MENUES.add(menue);
                if(menue.getStrTypMenue().equals("Frühstück"))
                {
                    BREAKFAST_MENUES.add(menue);
                }
                if(menue.getStrTypMenue().equals("Mittag"))
                {
                    LUNCH_MENUES.add(menue);
                }
                if(menue.getStrTypMenue().equals("Abend"))
                {
                    EVENING_MENUES.add(menue);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    //Abfragen der Position(Index) des Menues in der Gesamtliste
    public int getMenuePosition(int id){
        int position = 0;
        for (int i = 0; i < ALL_MENUES.size(); i++) {
            if(id==ALL_MENUES.get(i).getMenueID()){
                position = i;
            }
        }
        return position;
    }

    public int getMenueID(String menueBez)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT " + DbHelper.TableMenue.COLUMN_NAME_MENUEID + " as ID FROM " + DbHelper.TableMenue.TABLE_NAME + " WHERE " + DbHelper.TableMenue.COLUMN_NAME_MENUENAME + "=?",new String[] {menueBez});
        int id = 0;

        if(cursor.moveToFirst())
        {
            id = cursor.getInt(cursor.getColumnIndex("ID"));
            Log.d("MenueId getter: ", String.valueOf(id));
        }
        cursor.close();
        mDatabase.close();
        return id;
    }


    // loeschen eines menues aus der Tabelle menue
    public void deleteMenue(int id)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        mDatabase.delete(DbHelper.TableMenue.TABLE_NAME, DbHelper.TableMenue.COLUMN_NAME_MENUEID + " = " + id, null);
        mDatabase.close();

        updateMenueLists();
    }

    // Liste aller eingestellten Menues
    public List<Menue> getAllMenues() {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Cursor cursor = mDatabase.query(DbHelper.TableMenue.TABLE_NAME, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Menue menue = cursorToMenue(cursor);
            ALL_MENUES.add(menue);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return ALL_MENUES;
    }


    // alle Menues zu einem Typen
    public List<Menue> getMenuesOfATyp(long typId)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Menue> listMenues = new ArrayList<Menue>();

        Cursor cursor = mDatabase.query(DbHelper.TableMenue.TABLE_NAME, mAllColumns,
                DbHelper.TableMenue.COLUMN_NAME_TYPID + " = ?",
                new String[] { String.valueOf(typId) }, null, null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Menue menue = cursorToMenue(cursor);
            listMenues.add(menue);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return listMenues;
    }


    private Menue cursorToMenue(Cursor cursor)
    {
        Menue menue = new Menue();

        menue.setMenueID((cursor.getInt(0)-1));
        menue.setiArray(cursor.getBlob(1));
        menue.setStrInputMenueName(cursor.getString(2));
        menue.setStrDateOfAdding(cursor.getString(3));
        menue.setStrDescription(cursor.getString(4));
        menue.setStrZutaten(cursor.getString(4));
        //menue.setStrAnzahl();
        //menue.setSpEinheitText();

        // Typ nach typId holen
        int typId = cursor.getInt(5);
        Typ typ = this.getTypById(typId);
        if(typ != null)
        {
            menue.setStrTypMenue(typ.getTypBez());
        }

        return menue;
    }


    public ArrayList<Menue> getALL_MENUES() {
        return ALL_MENUES;
    }

    public ArrayList<Menue> getBREAKFAST_MENUES() {
        return BREAKFAST_MENUES;
    }

    public ArrayList<Menue> getLUNCH_MENUES() {
        return LUNCH_MENUES;
    }

    public ArrayList<Menue> getEVENING_MENUES() {
        return EVENING_MENUES;
    }



    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
// TYP


    private String[] tAllColumns = {
            DbHelper.TableMenueTyp.COLUMN_NAME_TYPID,
            DbHelper.TableMenueTyp.COLUMN_NAME_TYPBEZ};



    //Typ wird in der Datenbank erstellt anhand einer MenueBean
    public Typ createTyp(int typId, String typBez)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Log.d("createTYP", ": wird ausgeführt");

        ContentValues values = new ContentValues();
        values.put(DbHelper.TableMenueTyp.COLUMN_NAME_TYPID, typId);
        values.put(DbHelper.TableMenueTyp.COLUMN_NAME_TYPBEZ, typBez);

        long insertId = mDatabase.insert(DbHelper.TableMenueTyp.TABLE_NAME, null, values);

        Cursor cursor = mDatabase.query(DbHelper.TableMenueTyp.TABLE_NAME,
                tAllColumns, DbHelper.TableMenueTyp.COLUMN_NAME_TYPID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Typ newTyp = cursorToTyp(cursor);
        cursor.close();

        mDatabase.close();
        return newTyp;
    }



    public List<Typ> getAllTyps()
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Typ> listTyps = new ArrayList<Typ>();

        Cursor cursor = mDatabase.query(DbHelper.TableMenueTyp.TABLE_NAME, tAllColumns,
                null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Typ typ = cursorToTyp(cursor);
                listTyps.add(typ);
                cursor.moveToNext();
            }
            cursor.close();
        }
        mDatabase.close();
        return listTyps;
    }

    public Typ getTypById(int id)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Cursor cursor = mDatabase.query(DbHelper.TableMenueTyp.TABLE_NAME, tAllColumns,
                DbHelper.TableMenueTyp.COLUMN_NAME_TYPID + " = ?",
                new String[] { String.valueOf(id) }, null, null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        Typ typ = cursorToTyp(cursor);
        mDatabase.close();
        return typ;
    }

    protected Typ cursorToTyp(Cursor cursor)
    {
        Typ typ = new Typ();
        typ.setTypID(cursor.getInt(0));
        typ.setTypBez(cursor.getString(1));

        return typ;
    }


    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
// ZUTAT


    public static final String TAGzutat = "ZutatDAO";

    // Datenbankattribute
    private SQLiteDatabase mDatabase;
    private DbHelper mDBHelper;
    private Context mContext;
    private String[] zuAllColumns = {
            DbHelper.TableZutat.COLUMN_NAME_ZUTATID,
            DbHelper.TableZutat.COLUMN_NAME_ZUTATBEZ,
            DbHelper.TableZutat.COLUMN_NAME_EINHEITID};


    //Zutat wird in der Datenbank erstellt
    public Zutat createZutat(int zutatID, String zutatBez, int einheitID)
    {
        Log.d("createZUTAT", ": wird ausgeführt");
        SQLiteDatabase mDatabase = instance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TableZutat.COLUMN_NAME_ZUTATID, zutatID);
        values.put(DbHelper.TableZutat.COLUMN_NAME_ZUTATBEZ, zutatBez);
        values.put(DbHelper.TableZutat.COLUMN_NAME_EINHEITID, einheitID);

        // insertbefehl
        long insertId = mDatabase.insert(DbHelper.TableZutat.TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DbHelper.TableZutat.TABLE_NAME,
                zuAllColumns, DbHelper.TableZutat.COLUMN_NAME_ZUTATID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Zutat newZutat = cursorToZutat(cursor);
        cursor.close();
        mDatabase.close();
        return newZutat;
    }
    // loeschen einer Zutat aus der Tabelle zutat
    public void deleteZutat(Zutat zutat)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        long id = zutat.getZutatID();
        mDatabase.delete(DbHelper.TableZutat.TABLE_NAME, DbHelper.TableZutat.COLUMN_NAME_ZUTATID + " = " + id, null);
        mDatabase.close();
    }
    // Liste aller eingestellten Zutaten
    public List<Zutat> getAllZutaten()
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Zutat> listZutaten = new ArrayList<Zutat>();

        Cursor cursor = mDatabase.query(DbHelper.TableZutat.TABLE_NAME, zuAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Zutat zutat = cursorToZutat(cursor);
            listZutaten.add(zutat);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return listZutaten;
    }


    // alle Zutaten zu einer Einheit
    public List<Zutat> getZutatenOfAEinheit(long einheitId)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Zutat> listZutaten = new ArrayList<Zutat>();

        Cursor cursor = mDatabase.query(DbHelper.TableZutat.TABLE_NAME, zuAllColumns,
                DbHelper.TableZutat.COLUMN_NAME_EINHEITID + " = ?",
                new String[] { String.valueOf(einheitId) }, null, null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Zutat zutat = cursorToZutat(cursor);
            listZutaten.add(zutat);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return listZutaten;
    }

    public Zutat getZutatById(int id)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Cursor cursor = mDatabase.query(DbHelper.TableZutat.TABLE_NAME, zuAllColumns,
                DbHelper.TableZutat.COLUMN_NAME_ZUTATID + " = ?",
                new String[] { String.valueOf(id) }, null, null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        Zutat zutat = cursorToZutat(cursor);
        mDatabase.close();
        return zutat;
    }

    private Zutat cursorToZutat(Cursor cursor)
    {
        Zutat zutat = new Zutat();
        zutat.setZutatID(cursor.getInt(0));
        zutat.setZutatBez(cursor.getString(1));

        // Einheit nach einheitId holen
        int einheitId = cursor.getInt(2);
        Einheit einheit = this.getEinheitById(einheitId);
        if(einheit != null)
        {
            zutat.setStrEinheitZutat(einheit.getEinheitBez());
        }

        return zutat;
    }


    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
// EINHEIT


    public static final String TAG = "EinheitDAO";

    private String[] eAllColumns = {
            DbHelper.TableEinheit.COLUMN_NAME_EINHEITID,
            DbHelper.TableEinheit.COLUMN_NAME_EINHEITBEZEICHNUNG};


    //Einheit wird in der Datenbank erstellt anhand eines EinheitDAO
    public Einheit createEinheit(int einheitId, String einheitBez)
    {
        Log.d("createEINHEIT", ": wird ausgeführt");
        SQLiteDatabase mDatabase = instance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TableEinheit.COLUMN_NAME_EINHEITID, einheitId);
        values.put(DbHelper.TableEinheit.COLUMN_NAME_EINHEITBEZEICHNUNG, einheitBez);

        long insertId = mDatabase.insert(DbHelper.TableEinheit.TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DbHelper.TableEinheit.TABLE_NAME,
                eAllColumns, DbHelper.TableEinheit.COLUMN_NAME_EINHEITID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Einheit newEinheit = cursorToEinheit(cursor);
        cursor.close();
        mDatabase.close();
        return newEinheit;
    }

    public void deleteEinheit(Einheit einheit)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        long id = einheit.getEinheitID();
        // alle zutaten von dieser Einheit loeschen
        List<Zutat> listZutaten = this.getZutatenOfAEinheit(id);
        if(listZutaten != null && !listZutaten.isEmpty())
        {
            for(Zutat z : listZutaten)
            {
                this.deleteZutat(z);
            }
        }
        mDatabase.delete(DbHelper.TableEinheit.TABLE_NAME, DbHelper.TableEinheit.COLUMN_NAME_EINHEITID + " = " + id, null);
        mDatabase.close();
    }

    public List<Einheit> getAllEinheiten()
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Einheit> listEinheiten = new ArrayList<Einheit>();

        Cursor cursor = mDatabase.query(DbHelper.TableEinheit.TABLE_NAME, eAllColumns,
                null, null, null, null, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Einheit einheit = cursorToEinheit(cursor);
                listEinheiten.add(einheit);
                cursor.moveToNext();
            }
            cursor.close();
        }
        mDatabase.close();
        return listEinheiten;
    }

    public Einheit getEinheitById(int id)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        Cursor cursor = mDatabase.query(DbHelper.TableZutat.TABLE_NAME, eAllColumns,
                DbHelper.TableZutat.COLUMN_NAME_EINHEITID + " = ?",
                new String[] { String.valueOf(id) }, null, null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        Einheit einheit = cursorToEinheit(cursor);
        mDatabase.close();
        return einheit;
    }

    protected Einheit cursorToEinheit(Cursor cursor)
    {
        Einheit einheit = new Einheit();
        einheit.setEinheitID(cursor.getInt(0));
        einheit.setEinheitBez(cursor.getString(1));

        return einheit;
    }


    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
// Kueche

    // Datenbankattribute
    private String[] kAllColumns = {
            DbHelper.TableKueche.COLUMN_NAME_KUECHEID,
            DbHelper.TableKueche.COLUMN_NAME_ANZAHLVORHANDEN,
            DbHelper.TableKueche.COLUMN_NAME_ZUTATID};

    //Kueche wird in der Datenbank erstellt
    public Kueche createKueche(int kuecheID, int anzahlVorhanden, int zutatID)
    {
        Log.d("createKueche", ": wird ausgeführt");
        SQLiteDatabase mDatabase = instance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.TableKueche.COLUMN_NAME_KUECHEID, kuecheID);
        values.put(DbHelper.TableKueche.COLUMN_NAME_ANZAHLVORHANDEN, anzahlVorhanden);
        values.put(DbHelper.TableKueche.COLUMN_NAME_ZUTATID, zutatID);

        // insertbefehl
        long insertId = mDatabase.insert(DbHelper.TableKueche.TABLE_NAME, null, values);
        Cursor cursor = mDatabase.query(DbHelper.TableKueche.TABLE_NAME,
                kAllColumns, DbHelper.TableKueche.COLUMN_NAME_KUECHEID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Kueche newKueche = cursorToKueche(cursor);
        cursor.close();
        mDatabase.close();
        return newKueche;
    }
    // loeschen einer Kueche aus der Tabelle kueche
    public void deleteKueche(Kueche kueche)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        long id = kueche.getKuecheID();
        mDatabase.delete(DbHelper.TableKueche.TABLE_NAME, DbHelper.TableKueche.COLUMN_NAME_KUECHEID + " = " + id, null);
        mDatabase.close();
    }
    // Liste aller vorhandenen Zutaten in der Kueche = alle Kuechenobjekte
    public List<Kueche> getAllKuechen()
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Kueche> listKuechen = new ArrayList<Kueche>();

        Cursor cursor = mDatabase.query(DbHelper.TableKueche.TABLE_NAME, kAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Kueche kueche = cursorToKueche(cursor);
            listKuechen.add(kueche);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return listKuechen;
    }


    // alle Kuechen zu einer Zutat
    public List<Kueche> getKuechenOfAZutat(long zutatID)
    {
        SQLiteDatabase mDatabase = instance.getWritableDatabase();
        List<Kueche> listKuechen = new ArrayList<Kueche>();

        Cursor cursor = mDatabase.query(DbHelper.TableKueche.TABLE_NAME, kAllColumns,
                DbHelper.TableKueche.COLUMN_NAME_ZUTATID + " = ?",
                new String[] { String.valueOf(zutatID) }, null, null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Kueche kueche = cursorToKueche(cursor);
            listKuechen.add(kueche);
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return listKuechen;
    }


    private Kueche cursorToKueche(Cursor cursor)
    {
        Kueche kueche = new Kueche();
        kueche.setKuecheID(cursor.getInt(0));
        kueche.setAnzahlVorhanden(cursor.getInt(1));

        // Zutat nach zutatID holen
        int zutatId = cursor.getInt(2);
        Zutat zutat = this.getZutatById(zutatId);
        if(zutat != null)
        {
            kueche.setZutatID(zutat.getZutatID());
        }

        return kueche;
    }


    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
// MENUE_ZUTAT




}



