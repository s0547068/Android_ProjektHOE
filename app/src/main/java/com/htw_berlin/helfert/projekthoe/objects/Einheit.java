package com.htw_berlin.helfert.projekthoe.objects;

/**
 * Created by Steve on 25.10.2015.
 */
public class Einheit {
    /**
     * Attribute Decl. and Init
     */
    private int einheitID;
    // Name der Einheit
    private String einheitBez = "";


    /**
     * Standard Konstruktor
     */
    public Einheit()
    {
        // Hier gibt es nichts zu tun.
    }


    // Getter und Setter
    public int getEinheitID() {
        return einheitID;
    }

    public void setEinheitID(int einheitID) {
        this.einheitID = einheitID;
    }

    public String getEinheitBez() {
        return einheitBez;
    }

    public void setEinheitBez(String einheitBez) {
        this.einheitBez = einheitBez;
    }
} // end of class
