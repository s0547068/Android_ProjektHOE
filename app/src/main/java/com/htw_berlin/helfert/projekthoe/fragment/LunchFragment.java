package com.htw_berlin.helfert.projekthoe.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.htw_berlin.helfert.projekthoe.R;
import com.htw_berlin.helfert.projekthoe.activity.ListViewItemDetailsActivity;
import com.htw_berlin.helfert.projekthoe.adapter.ListViewActivityLunchAdapter;
import com.htw_berlin.helfert.projekthoe.db.DbHelper;
import com.htw_berlin.helfert.projekthoe.objects.Menue;

/**
 * Diese Klasse dient dazu alle erfassten Menues anzuzeigen und eine grafische Moeglichkeit zu bieten.
 *
 * Die Besonderheit jedoch ist, dass sie kein XML-Layout als Basis fuer die
 * Generierung des Designs hat. Das uebernimmt der sogenannte ListViewActivityAdapter,
 * welcher von der Klasse (@Link android.widget.BaseAdapter) abgeleitet ist.
 *
 * Dieser greift auf die einzelnen Menues zu und nutzt diese, um das ListFragment aufzubauen
 */
public class LunchFragment extends android.support.v4.app.Fragment
{

/*
    * Decl. and Init Attribute
     */

    // ListView Adapter, welcher den Inhalt verwaltet
    private ListViewActivityLunchAdapter listViewActivityLunchAdapter = null;

    // OnItemClickListener: er wird aufgerufen, wenn ein einzelnes Item geklickt wird
    private ListViewOnLunchItemClickListener listViewOnLunchItemClickListener = null;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Layout Breakfast entfalten
        View rootView = inflater.inflate(R.layout.fragment_kategorie_list, container, false);
        Log.d("RootView", "LunchFragment onCreateView");


        Context context = getActivity().getApplicationContext();

        DbHelper dbHelper = DbHelper.getInstance(context);

        listViewActivityLunchAdapter = new ListViewActivityLunchAdapter(context, dbHelper);

        ListView lv = (ListView) rootView.findViewById(R.id.listViewAllKategorien);
        lv.setAdapter(listViewActivityLunchAdapter);

            /*
            * Generieren des ListViewOnItemClickListener,
            * welcher in Kraft tritt, sobald ein Item geklickt wird.
            * Er ruft eine weitere Activity auf, die es uns erlaubt,
            * das Menue zu bearbeiten, zu loeschen
             */
        listViewOnLunchItemClickListener = new ListViewOnLunchItemClickListener();

        // HInzufuegen des Listeners
        lv.setOnItemClickListener(listViewOnLunchItemClickListener);

        return rootView;


    }

    public ListViewActivityLunchAdapter getAdapter() {
        return listViewActivityLunchAdapter;
    }

    private class ListViewOnLunchItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int index, long id)
        {
            String menueName = ((Menue) getAdapter().getItem(index)).getStrInputMenueName();
            String menueBeschreibung = ((Menue) getAdapter().getItem(index)).getStrDescription();
            String menueDatum = ((Menue) getAdapter().getItem(index)).getStrDateOfAdding();
            String menueZutaten = ((Menue) getAdapter().getItem(index)).getStrZutaten();
            String menueTyp = ((Menue) getAdapter().getItem(index)).getStrTypMenue();
            byte[] photo = ((Menue) getAdapter().getItem(index)).getiArray();

            // Generieren eines Intents fuer die ListViewItemDetailsActivity zu wrappen
            Intent intentListViewItemDetailsActivity = new Intent(view.getContext(), ListViewItemDetailsActivity.class);
            // Flag setzen, activity von außen zum context aufrufen zukoennen
            intentListViewItemDetailsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Hinzufuegen von Daten zum Intent objekt
            intentListViewItemDetailsActivity.putExtra("menueName", menueName);
            intentListViewItemDetailsActivity.putExtra("menueBeschreibung", menueBeschreibung);
            intentListViewItemDetailsActivity.putExtra("menueDatum", menueDatum);
            intentListViewItemDetailsActivity.putExtra("menueZutaten", menueZutaten);
            intentListViewItemDetailsActivity.putExtra("menueTyp", menueTyp);
            intentListViewItemDetailsActivity.putExtra("menuePhoto", photo);

            // Aufrufen der Activity
            view.getContext().startActivity(intentListViewItemDetailsActivity);
        }
    }// End of Class
}

