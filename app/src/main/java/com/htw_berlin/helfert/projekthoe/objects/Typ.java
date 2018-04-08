package com.htw_berlin.helfert.projekthoe.objects;



/**
 * Created by Steve on 25.10.2015.
 */
public class Typ
{
    /**
     * Attribute Decl. and Init
     */
    private int typID;
    private String typBez = "";


    /**
     * Standard Konstruktor
     */
    public Typ()
    {
        // Hier gibt es nichts zu tun.
    }


    // Getter und Setter
    public int getTypID() {
        return typID;
    }

    public void setTypID(int typID) {
        this.typID = typID;
    }

    public String getTypBez() {
        return typBez;
    }

    public void setTypBez(String typBez) {
        this.typBez = typBez;
    }
} // end of class
