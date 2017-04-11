package com.example.patryk.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by patryk on 2017-03-07.
 */

public class ListAdapter extends ArrayAdapter<pola> {

    private ImageView katIcon;
  //  private String katName;

    private Integer[] mThumbIds = {
            R.drawable.jedzenie,
            R.drawable.napoje,
            R.drawable.ic_menu_manage,
            R.drawable.transport,
            R.drawable.inne
    };
    @Override
public View getView(int position, View convertView, ViewGroup parent) {


        pola pola = getItem(position);

        if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_adapter, parent, false);
        }
        // Lookup view for data population
        TextView tvNazwa = (TextView) convertView.findViewById(R.id.tvNazwaListAdapter);
        TextView tvKoszt = (TextView) convertView.findViewById(R.id.tvKosztListAdapter);
        //TextView tvRodzaj = (TextView) convertView.findViewById(R.id.rodzajTVListAdapter);

         katIcon = (ImageView)  convertView.findViewById(R.id.imgViewListAdapter);



        // Populate the data into the template view using the data object
        tvNazwa.setText(pola.nazwa);
        tvKoszt.setText(pola.koszt + " PLN");
        String rodzaj = pola.rodzaj;


        if(rodzaj.equals("Jedzenie"))
        {
            katIcon.setImageResource(mThumbIds[0]);
        }
        else if(rodzaj.equals("Napoje"))
        {
            katIcon.setImageResource(mThumbIds[1]);
        }

        else if(rodzaj.equals("Uslugi"))
        {
            katIcon.setImageResource(mThumbIds[2]);
        }
        else if(rodzaj.equals("Transport"))
        {
            katIcon.setImageResource(mThumbIds[3]);
        }

        else
        {
            katIcon.setImageResource(mThumbIds[4]);
        }

        // Return the completed view to render on screen
        return convertView;
        }

    public ListAdapter(Context context, ArrayList<pola> pola) {
        super(context, 0, pola);
        }

}

