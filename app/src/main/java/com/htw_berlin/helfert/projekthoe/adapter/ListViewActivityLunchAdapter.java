package com.htw_berlin.helfert.projekthoe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.htw_berlin.helfert.projekthoe.R;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;
import com.htw_berlin.helfert.projekthoe.objects.Menue;
import com.htw_berlin.helfert.projekthoe.wrapper.ListViewItemWidgetWrapper;

import java.util.List;


/**
 * Adapterklasse zum Setzen der richtigen Attribute und Rückgabe der View
 */
public class ListViewActivityLunchAdapter extends BaseAdapter {
 /*
    * Der LayoutInflater erzeugt das Layout aus dem Context
    * der Ihm uebertragen wird. Der Context ist die ListViewActivity
     */

    private LayoutInflater layoutInflater = null;
    private Context context;
    private DbHelper dbHelper;
    private List<Menue> lunchListe;

    /**
     * Ueberladener Konstruktor
     * zur Uebergabe des Contextes
     * --> ListViewActivityBreakfast
     * @param context
     */

    public ListViewActivityLunchAdapter(Context context, DbHelper dbHelper)
    {
        this.dbHelper = dbHelper;
        lunchListe = dbHelper.getLUNCH_MENUES();
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return lunchListe.size();
    }

    /**
     * Index eines einzelnen ListViewItems
     * @param location
     * @return : int : index eines ListViewItem
     */
    @Override
    public Object getItem(int location)
    {
        return this.lunchListe.get(location);
    }
    /**
     * Id eines ListViewItems / wird von der API benötigt, um den Elementen eine eindeutige Id mitzugeben
     * Parameter ist ein Interger, aber Rueckgabewert ist ein Long
     * @param index
     * @return
     */
    @Override
    public long getItemId(int index)
    {
        return this.lunchListe.get(index).getMenueID();
    }
    /**
     * Diese Funktion erstellt die einzelnen ListViewItems aus den im list_view_item_layout.xml definierten Steuerelementen.
     *
     * @param index : int : Index eines Elements in der ArrayList und der View
     * @param  currentView: (@Link View) zum Erstellen eines einzelnen ListViewItems
     * @param parent : (@Link ViewGroup) : GUI-Elternklasse
     */
    @Override
    public View getView(int index, View currentView, ViewGroup parent)
    {
        ListViewItemWidgetWrapper lvItemWidgetWrapper = new ListViewItemWidgetWrapper();
        /*
        * Ist die aktuelle View >null<, so wurde Sie noch nicht generiert.
        * Die View repraesentiert ein einzelnes Item der ListView
            */
        if ( currentView == null)
        {
            /*
            * Hier wird das einzelne ListViewItem generiert,
            * dies geschieht mit Hilfe des zuvor definierten Layouts.
            * Ich uebergebe dem LayoutInflater als Parameter
            * das Layout
            * den Parent
            * den Parameter attachToRoot als False, da nicht benoetigt.
             */

            currentView = layoutInflater.inflate(R.layout.fragment_breakfast, parent, false);



            /*
            * Ich nutze den lvItemWidgetsWapper um alle Widgets zu generieren, die ein
            * einzelnes ListViewItem beinhalten soll.
            * Die Layoutinformationen erhalte ich ueber die Funktion findViewById, die jede
            * View beinhaltet. Die Layoutinformation erhalte ich ueber die R-Datei.
             */



            // neue TextView fuer den Menuenamen generieren
            lvItemWidgetWrapper.setTxtvMenueName(
                    (TextView) currentView.findViewById(R.id.txtNameOfMenue));

            // neue TextView Infos zum Menue
            lvItemWidgetWrapper.setTxtvAllValues(
                    (TextView) currentView.findViewById(R.id.txtAllValues));

            // neue TextView fuer den Datum generieren
            lvItemWidgetWrapper.setTxtvDateOfAdding(
                    (TextView) currentView.findViewById(R.id.txtDateOfAdding));

            lvItemWidgetWrapper.setTxtvMenuePhoto(
                    (ImageView) currentView.findViewById(R.id.photo));

                   /*
            * Hinzufuegen des ListViewItemWidgetWrappers
            * mit allen seinen Widgets zum ListViewItem
             */
            currentView.setTag(lvItemWidgetWrapper);
        }
        else
        {
            lvItemWidgetWrapper = (ListViewItemWidgetWrapper)currentView.getTag();
        }
        //Extrahieren des Menue zum Nutzen der Werte
        // Herausziehen des richtigen Items gemaess Index
        // Explicit Cast!!!
        Menue menue;
        menue = (Menue) this.getItem(index);
        // Befuellen der einzelnen Widgets mit Werten - dazu wird auf die Bean zugegriffen
        lvItemWidgetWrapper.getTxtvMenueName().setText(menue.getStrInputMenueName());
        lvItemWidgetWrapper.getTxtvAllValues().setText(menue.toString());
        lvItemWidgetWrapper.getTxTvDateOfAdding().setText(menue.getStrDateOfAdding());
        lvItemWidgetWrapper.getTxtvMenuePhoto().setImageBitmap(menue.getBitmap());


        // Rueckgabe der generierten View
        return currentView;
    }
}// end of class

