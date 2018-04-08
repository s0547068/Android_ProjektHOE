package com.htw_berlin.helfert.projekthoe.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.htw_berlin.helfert.projekthoe.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Die Klasse MenueFragment entfaltet die Layouts Container fuer das MenueFragment und der einzelnen Fragmente
 * BreakfastFragment - LunchFragment - EveningFragment
 * Created by Steve on 17.10.2015.
 */
public class MenueFragment extends Fragment
{
    private Fragment frag;
    private FragmentTransaction fragmentTransaction;


    private Button btnBreakfast = null;
    private Button btnLunch = null;
    private Button btnEvening = null;

    public MenueFragment()
    {

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        // Entfalten des Layouts (Frueh - Mittag - Abend)
        View view = inflater.inflate(R.layout.fragment_menue, container, false);
         /*
        * Widgetreferenzen generieren
         */
        btnBreakfast = (Button)view.findViewById(R.id.btnBreakfast);
        btnLunch = (Button)view.findViewById(R.id.btnLunch);
        btnEvening = (Button)view.findViewById(R.id.btnEvening);

        //background aendern - zuerst werden die Frühstücksgerichte angezeigt
        btnBreakfast.setBackgroundColor(Color.RED);
        btnLunch.setBackgroundColor(Color.GRAY);
        btnEvening.setBackgroundColor(Color.GRAY);

        // Hinzufuegen des BreakfastFragment - Erste Ansicht sind die Frühstücksgerichte
        frag = new BreakfastFragment();
        fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.container, frag);
        fragmentTransaction.commit();



        // Listener fuer Button
        btnBreakfast.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //background aendern
                btnBreakfast.setBackgroundColor(Color.RED);
                btnLunch.setBackgroundColor(Color.GRAY);
                btnEvening.setBackgroundColor(Color.GRAY);
                // Anzeige und updaten der Fruehstuecksgerichte
                frag = new BreakfastFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                fragmentTransaction.commit();
            }
        });

        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //background aendern
                btnBreakfast.setBackgroundColor(Color.GRAY);
                btnLunch.setBackgroundColor(Color.RED);
                btnEvening.setBackgroundColor(Color.GRAY);
                // Anzeige und updaten der Mittagsgerichte
                frag = new LunchFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container,frag);
                fragmentTransaction.commit();
            }
        });

        btnEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //background aendern
                btnBreakfast.setBackgroundColor(Color.GRAY);
                btnLunch.setBackgroundColor(Color.GRAY);
                btnEvening.setBackgroundColor(Color.RED);
                // Anzeige und updaten der Abendgerichte
                frag = new EveningFragment();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container,frag);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
