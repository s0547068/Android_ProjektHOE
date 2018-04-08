package com.htw_berlin.helfert.projekthoe.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by Steve on 25.10.2015.
 */
public class Menue
{
    /**
     * Attribute Decl. and Init
     */
    private int menueID;
    // Datum der Erfassung
    private String strDateOfAdding = "";
    // Name des Menues
    private String strInputMenueName = "";
    // Typ: Frühstück - Mittag - Abend
    private String strTypMenue = "";
    // Die Zutat - Anzahl - Einheitspinner
    private String spEinheitText;
    private String strAnzahl = "";
    private String strZutaten = "";
    // Die Beschreibung im multiLine-EditText
    private String strDescription = "";
    // photo zum menue
    private byte[] iArray;
    private Bitmap bitmap;



    public Menue(int menueID, byte[] imageArray, String strDateOfAdding, String strInputMenueName, String strTypMenue, String strDescription, String strZutaten) {
        this.menueID = menueID;
        this.strDateOfAdding = strDateOfAdding;
        this.strInputMenueName = strInputMenueName;
        this.strTypMenue = strTypMenue;
        this.strDescription = strDescription;
        this.strZutaten = strZutaten;
        this.iArray = imageArray;
        // convertieren von byte array zu bitmap
        this.bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
            }

    /**
     * Standard Konstruktor
     */
    public Menue()
    {
        // Hier gibt es nichts zu tun.
    }


    // Getter und Setter

    public int getMenueID() {
        return menueID;
    }

    public void setMenueID(int menueID) {
        this.menueID = menueID;
    }
    public String getStrDateOfAdding() {
        return strDateOfAdding;
    }

    public void setStrDateOfAdding(String strDateOfAdding) {
        this.strDateOfAdding = strDateOfAdding;
    }

    public String getStrInputMenueName() {
        return strInputMenueName;
    }

    public void setStrInputMenueName(String strInputMenueName) {
        this.strInputMenueName = strInputMenueName;
    }

    public String getStrTypMenue() {
        return strTypMenue;
    }

    public void setStrTypMenue(String strTypMenue) {
        this.strTypMenue = strTypMenue;
    }

    public String getStrZutaten() {
        return strZutaten;
    }

    public void setStrZutaten(String strZutaten) {
        this.strZutaten = strZutaten;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public byte[] getiArray() {
        return iArray;
    }

    public void setiArray(byte[] iArray) {
        this.iArray = iArray;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public String getSpEinheitText() {
        return spEinheitText;
    }

    public void setSpEinheitText(String spEinheitText) {
        this.spEinheitText = spEinheitText;
    }

    public String getStrAnzahl() {
        return strAnzahl;
    }

    public void setStrAnzahl(String strAnzahl) {
        this.strAnzahl = strAnzahl;
    }


    public String toString()
    {
        // Decl. and Init
        String strReturn = "";

        strReturn = "Typ: " + this.strTypMenue + "\n";
        strReturn += "Zutaten: \n" + this.strZutaten + "\n";
        strReturn += "Beschreibung: \n" + this.strDescription + "\n";

        return strReturn;
    }
}
