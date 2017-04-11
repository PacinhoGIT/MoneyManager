package com.example.patryk.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.patryk.moneymanager.pola.*;

/**
 * Created by patryk on 2017-03-01.
 */

public class CustomAdapter extends ArrayAdapter<pola> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        pola pola = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_adapter2, parent, false);
        }

        TextView tvNazwa = (TextView) convertView.findViewById(R.id.tvnazwaAdapter);
        TextView tvRodzaj = (TextView) convertView.findViewById(R.id.tvRodzajAdapter);
        TextView tvData = (TextView) convertView.findViewById(R.id.tvDataAdapter);
        TextView tvKoszt = (TextView) convertView.findViewById(R.id.tvKosztAdapter);

        tvNazwa.setText(pola.nazwa);
        tvRodzaj.setText(pola.rodzaj);
        tvData.setText(pola.data);
        tvKoszt.setText(pola.koszt+" PLN");

        return convertView;
    }


    public CustomAdapter(Context context, ArrayList<pola> pola) {
        super(context, 0, pola);
    }
}
