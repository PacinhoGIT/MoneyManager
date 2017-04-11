package com.example.patryk.moneymanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.patryk.moneymanager.R.id.kosztTV;
import static com.example.patryk.moneymanager.R.id.nazwaED;

/**
 * Created by user on 12/31/15.
 */
public class MainFragment extends Fragment  {

    View myView;

    TextView tv1,tv2,tv3,tv4;
    TextClock tC;
    // ImageView logo;

    // String koszt;

    double kosztI;

    public MainFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.mainfragment_layout, container, false);

        tv1 = (TextView) myView.findViewById(R.id.ostatniWydatekTV);
        tv2 = (TextView) myView.findViewById(R.id.ostatniWydatekShow);
        tv3 = (TextView) myView.findViewById(R.id.sumaWydatkow);
        tv4 = (TextView) myView.findViewById(R.id.sumaWydatkowShow);

        tC = (TextClock) myView.findViewById(R.id.textClock);


        DatabaseManager zb = new DatabaseManager(myView.getContext());

        kosztI=0;

        Cursor k = zb.dajWszystkie();

            while (k.moveToNext()) {
                int nr = k.getInt(0);

                String nazwa = k.getString(1);
                String rodzaj = k.getString(2);
                String data = k.getString(3);
                Double koszt = k.getDouble(4);

                kosztI=kosztI+koszt;


                nazwa = formatText(nazwa);
                rodzaj = formatText(rodzaj);
                data = formatText(data);

                String dane = nazwa + " " + rodzaj + " " + data + " " + "    " + koszt;

                tv2.setText(dane);


            }
            if(kosztI==0) {

                tv2.setText("Brak WPISOW!");
                tv4.setText("Brak WPISOW!");
            }
            else {
                String kosztS = String.valueOf(kosztI);
                tv4.setText(kosztS + " PLN");
            }

        int ilosc=0;

        Cursor k1 = zb.dajWszystkieRodzaje();
        while (k1.moveToNext()) {

            ilosc++;

        }

        if(ilosc==0)
        {
            zb.dodajRodzaj("Jedzenie");
            zb.dodajRodzaj("Napoje");
            zb.dodajRodzaj("Uslugi");
            zb.dodajRodzaj("Transport");


        }
        else{}




        return myView;

    }


        public String formatText(String text)
        {
            while(text.length()<10) {
                text += " ";
            }

            return text;
        }


}


