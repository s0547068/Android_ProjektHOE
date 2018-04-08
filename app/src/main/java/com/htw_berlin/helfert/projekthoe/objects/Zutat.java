package com.htw_berlin.helfert.projekthoe.objects;

/**
 * Created by Steve on 26.10.2015.
 */
public class Zutat
{
    /**
     * Attribute Decl. and Init
     */
    private int zutatID;
    private String zutatBez = "";



    private String strEinheitZutat = "";
    private int einheitID;


    /**
     * Standard Konstruktor
     */
    public Zutat()
    {
        // Hier gibt es nichts zu tun.
    }



    // Getter und Setter
    public int getZutatID() {
        return zutatID;
    }

    public void setZutatID(int zutatID) {
        this.zutatID = zutatID;
    }

    public String getZutatBez() {
        return zutatBez;
    }

    public void setZutatBez(String zutatBez) {
        this.zutatBez = zutatBez;
    }

    public int getEinheitID() {
        return einheitID;
    }

    public void setEinheitID(int einheitID) {
        this.einheitID = einheitID;
    }

    public String getStrEinheitZutat() {
        return strEinheitZutat;
    }

    public void setStrEinheitZutat(String strEinheitZutat) {
        this.strEinheitZutat = strEinheitZutat;
    }
} // end of class

