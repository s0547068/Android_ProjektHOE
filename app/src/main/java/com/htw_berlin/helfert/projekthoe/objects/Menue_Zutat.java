package com.htw_berlin.helfert.projekthoe.objects;

/**
 * Created by Steve on 26.10.2015.
 */
public class Menue_Zutat
{
    /**
     * Attribute Decl. and Init
     */
    private int menueID;
    private int zutatID;
    private int anzahlBenoetigt;


    /**
     * Standard Konstruktor
     */
    public Menue_Zutat()
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

    public int getZutatID() {
        return zutatID;
    }

    public void setZutatID(int zutatID) {
        this.zutatID = zutatID;
    }

    public int getAnzahlBenoetigt() {
        return anzahlBenoetigt;
    }

    public void setAnzahlBenoetigt(int anzahlBenoetigt) {
        this.anzahlBenoetigt = anzahlBenoetigt;
    }
}
