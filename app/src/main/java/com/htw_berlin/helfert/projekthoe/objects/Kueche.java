package com.htw_berlin.helfert.projekthoe.objects;

/**
 * Created by Steve on 26.10.2015.
 */
public class Kueche
{
    /**
     * Attribute Decl. and Init
     */
    private int kuecheID;
    // Anzahl an vorhandenen Zutaten in der Kueche
    private int anzahlVorhanden;
    private int zutatID;


    /**
     * Standard Konstruktor
     */
    public Kueche()
    {
        // Hier gibt es nichts zu tun.
    }


    // Getter und Setter
    public int getKuecheID() {
        return kuecheID;
    }

    public void setKuecheID(int kuecheID) {
        this.kuecheID = kuecheID;
    }

    public int getAnzahlVorhanden() {
        return anzahlVorhanden;
    }

    public void setAnzahlVorhanden(int anzahlVorhanden) {
        this.anzahlVorhanden = anzahlVorhanden;
    }

    public int getZutatID() {
        return zutatID;
    }

    public void setZutatID(int zutatID) {
        this.zutatID = zutatID;
    }
} // end of class

