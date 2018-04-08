package com.htw_berlin.helfert.projekthoe.wrapper;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Diese Klasse kapselt die Widgets, die wir innerhalb eines einzelnen
 * ListViewItems darstellen wollen.
 * Das erleichtert uns das Handling in der ListViewActivity-Klassen und
 * deren Adapter -> ListViewActivityAdapter....
 */
public class ListViewItemWidgetWrapper
{
    /*
    * Objekt Attribute deklarieren und initialisieren
     */

    // Anzeige Dateiname
    private TextView txtvMenueName = null;
    // Anzeige des Status
    private TextView txtvAllValues = null;
    // Anzeige Datum der Erfassung
    private TextView txtvDateOfAdding = null;


    // MenuePhoto
    private ImageView txtvMenuePhoto = null;

    /*
    * Standard Konstruktor
     */
    public ListViewItemWidgetWrapper()
    {
        // hier gibt es nichts zu tun
    }

    /**
     * Ueberladener Konstruktor
     * fuer das Setzen der Widget-Referenzen
     *
     * @param txtvMenueName : TextView
     * @param txtvAllValues : TextView
     * @param txtvDateOfAdding : TextView
     */
    public ListViewItemWidgetWrapper(TextView txtvMenueName, TextView txtvAllValues, TextView txtvDateOfAdding, ImageView txtvMenuePhoto)
    {
        // Setzen der Attribute
        setTxtvMenueName(txtvMenueName);
        setTxtvAllValues(txtvAllValues);
        setTxtvDateOfAdding(txtvDateOfAdding);
        setTxtvMenuePhoto(txtvMenuePhoto);
    }

    /*
    * Getter und Setter
     */

    public TextView getTxtvMenueName() {
        return txtvMenueName;
    }

    public void setTxtvMenueName(TextView txtvMenueName) {
        this.txtvMenueName = txtvMenueName;
    }

    public TextView getTxtvAllValues()
    {
        return txtvAllValues;
    }

    public void setTxtvAllValues(TextView txtvAllValues)
    {
        this.txtvAllValues = txtvAllValues;
    }

    public TextView getTxTvDateOfAdding()
    {
        return txtvDateOfAdding;
    }

    public void setTxtvDateOfAdding(TextView txtDateOfAdding)
    {
        this.txtvDateOfAdding = txtDateOfAdding;
    }
    public ImageView getTxtvMenuePhoto() {
        return txtvMenuePhoto;
    }

    public void setTxtvMenuePhoto(ImageView txtvMenuePhoto) {
        this.txtvMenuePhoto = txtvMenuePhoto;
    }

}// end of class
