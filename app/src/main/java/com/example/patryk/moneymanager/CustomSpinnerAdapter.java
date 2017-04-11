package com.example.patryk.moneymanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by patryk on 2017-03-21.
 */


public class CustomSpinnerAdapter extends ArrayAdapter<rodzaje> {

    public CustomSpinnerAdapter(Context context,int resourses, ArrayList<rodzaje> rodzajee) {
        super(context,resourses, rodzajee);
    }

    private  rodzaje rodzajee;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


      rodzajee = getItem(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner_layout, parent, false);
        }

        TextView  tv= (TextView) convertView.findViewById(R.id.spinnerTV);
        ImageView iv = (ImageView) convertView.findViewById(R.id.spinnerIV);

        //String rodzaj = r.getText();

        tv.setText(rodzajee.rodzaj);
        iv.setImageResource(rodzajee.img);

        /*
        if(rodzaj.equals("Jedzenie"))
        {
            iv.setImageResource(mThumbIds[0]);
        }
        else if(rodzaj.equals("Napoje"))
        {
            iv.setImageResource(mThumbIds[1]);
        }

        else if(rodzaj.equals("Uslugi"))
        {
            iv.setImageResource(mThumbIds[2]);
        }
        else if(rodzaj.equals("Transport"))
        {
            iv.setImageResource(mThumbIds[3]);
        }

        else
        {
            iv.setImageResource(mThumbIds[4]);
        }
        */

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
