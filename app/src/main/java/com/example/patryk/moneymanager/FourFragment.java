package com.example.patryk.moneymanager;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by patryk on 2017-03-07.
 */

public class FourFragment extends Fragment {

    View myView;
    TextView tv1,tv2;
    EditText ed1;
    Button b1;

    ArrayList<String> arrayRodzaj;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.four_layout, container, false);

        tv1 = (TextView) myView.findViewById(R.id.tvDodajNowaKategorie);
        tv2 = (TextView) myView.findViewById(R.id.tvNowaKategoria);

        ed1 = (EditText) myView.findViewById(R.id.edNowaKategoria);

        b1 = (Button) myView.findViewById(R.id.btnNowaKategoria);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrayRodzaj = new ArrayList<>();

                DatabaseManager db = new DatabaseManager(myView.getContext());

                Cursor k = db.dajWszystkieRodzaje();


                while (k.moveToNext())
                {
                    String rodzaj = k.getString(1);
                    arrayRodzaj.add(rodzaj);

                }

                String kat = ed1.getText().toString();
                int wielkoscArray=0;
                wielkoscArray=arrayRodzaj.size();

                Boolean is=false;

                for(int i=0;i<wielkoscArray;i++)
                {

                    String element = arrayRodzaj.get(i);


                    if(element.equals(kat))
                    {
                        is=true;
                    }

                }

                if(is==true)
                {
                    ed1.setError("Blad ! Taka kategoria już istnieje !");
                }
                else
                {
                    if(ed1.getText().toString().length()<10) {
                        db.dodajRodzaj(ed1.getText().toString());
                        ed1.setText("");
                        Toast.makeText(myView.getContext(), "Dodano !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        ed1.setError("Zbyt dluga nazwa ! Wpisz maksymalnie 9 znaków !");
                    }
                }


            }
        });


        return myView;
    }
}
