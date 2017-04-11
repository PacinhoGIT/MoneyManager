package com.example.patryk.moneymanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.patryk.moneymanager.R.id.nazwaED;

/**
 * Created by user on 12/31/15.
 */
public class FirstFragment extends Fragment {

    View myView;

    TextView tv1, tv2, tv3, tv4, tv5;
    Button b1;
    public EditText ed1, ed3, ed4;

    private String editt = "N";

    private int year;
    private int month;
    private int day;
    private Spinner s;

    private ImageButton imgKalendarz;

    double kosztD;
    String month1;
    String day1;
    String rodzaj;
    private String[] arraySpinner;
    private ArrayList<String> rodzajeList;
    private ArrayList<rodzaje> listOfRodzaje;


    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.first_layout, container, false);

        imgKalendarz = (ImageButton) myView.findViewById(R.id.imageButton);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        month += 1;

        if (month < 10) {
            month1 = "0" + month;
        }

        if (day < 10) {
            day1 = "0" + day;
        }
        else
        {
            day1=""+day;
        }


        ed3 = (EditText) myView.findViewById(R.id.dataED);

        ed4 = (EditText) myView.findViewById(R.id.kosztED);

        ed3.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day1)
                .append("/")
                .append(month1)
                .append("/")
                .append(year));

        rodzajeList = new ArrayList<>();
      // ArrayList<rodzaje> arrayRodazje = new ArrayList<rodzaje>();


         Integer[] imgRodzaje = {
                R.drawable.jedzenie,
                R.drawable.napoje,
                R.drawable.ic_menu_manage,
                R.drawable.transport,
                R.drawable.inne
        };

        listOfRodzaje = new ArrayList<rodzaje>();
        int z=0;

        DatabaseManager db2 = new DatabaseManager(myView.getContext());

        Cursor k = db2.dajWszystkieRodzaje();
        while (k.moveToNext()) {

            //int nr=k.getInt(0);
            String rodzaj = k.getString(1);

            rodzajeList.add(rodzaj);
            if(z<4)
            {
                rodzaje r = new rodzaje(rodzaj,imgRodzaje[z]);
                listOfRodzaje.add(r);

            }
            else
            {
                rodzaje r = new rodzaje(rodzaj,imgRodzaje[4]);
                listOfRodzaje.add(r);
            }

            z++;


        }


        //arrayRodazje.add()

        s = (Spinner) myView.findViewById(R.id.spinner);

        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(myView.getContext(),R.layout.custom_spinner_layout,listOfRodzaje);
        s.setAdapter(adap);

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(myView.getContext(),
        //       android.R.layout.simple_spinner_dropdown_item, rodzajeList);
       // s.setAdapter(adapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rodzaj = rodzajeList.get(position).toString();

            //  Toast.makeText(FirstFragment.this.getActivity(),rodzaj,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rodzaj = "Jedzenie";
            }

        });

        tv1 = (TextView) myView.findViewById(R.id.pokazWydatek);
        tv2 = (TextView) myView.findViewById(R.id.nazwaTV);
        tv3 = (TextView) myView.findViewById(R.id.rodzajTV);
        tv4 = (TextView) myView.findViewById(R.id.dataTV);
        tv5 = (TextView) myView.findViewById(R.id.kosztTV);


        ed1 = (EditText) myView.findViewById(nazwaED);

        imgKalendarz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                if (month < 9) {
                    month1 = "0" + month;
                }

                DatePickerDialog dpd = new DatePickerDialog(myView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String monthS = "";
                                monthOfYear += 1;

                                if (monthOfYear < 10) {
                                    monthS = "0" + monthOfYear;
                                } else {
                                    monthS = Integer.toString(monthOfYear);
                                }

                                if (dayOfMonth < 10) {
                                    day1 = "0" + dayOfMonth;
                                } else {
                                    day1 = Integer.toString(dayOfMonth);
                                }


                                ed3.setText(day1 + "/"
                                        + (monthS) + "/" + year);

                            }
                        }, year, month, day);
                dpd.show();

            }
        });



        /*
        if(editt.equals("T"))
        {
            ed1.setText(nazwa1);
            ed3.setText(data1);
            ed4.setText(String.valueOf(koszt1));

            int posit = adapter.getPosition(kategoria1);

            s.setSelection(posit);

        }
        */


        b1 = (Button) myView.findViewById(R.id.dodajBtn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int next = 0;
                String nazwa = ed1.getText().toString();
                //String rodzaj = ed2.getText().toString();
                String data = ed3.getText().toString();
                // data=data.substring(0,10);
                ed3.setText(data);
                String koszt = ed4.getText().toString();

                if (nazwa.length() < 4 || nazwa.length() > 10) {
                    ed1.setError("Wpisz minimum 4 znaki i maximum 10!");
                } else {
                    next++;
                }

                if (koszt.length() < 1) {
                    ed4.setError("Wpisz minimum 1 cyfre !");
                } else {
                    if (koszt.substring(0, 1).equals("-")) {
                        ed4.setError("Wartosc kwoty nie moze byc ujemna !");
                    } else {
                         kosztD = 0;
                        int err = 0;

                        String koszt1 = ed4.getText().toString();
                        try {
                            kosztD = Double.valueOf(koszt1);
                        } catch (NumberFormatException numberError) {
                            ed4.setError("Zla wartosc kwoty ! Podaj wartosc liczbowa !");
                            err++;
                        }

                        if (kosztD == 0) {
                            if (err == 0) {
                                ed4.setError("Wartosc nie moze byc 0 !");
                            }
                        }

                        if (kosztD != 0) {
                            next++;
                        }
                    }
                }

                if (data.length() >= 10) {
                    data = data.substring(0, 10);
                    if (data.substring(2, 3).equals("/") && data.substring(5, 6).equals("/")) {

                        // next++;
                        String date = data.substring(0, 10);
                        try {
                            int dayInt = Integer.parseInt(date.substring(0, 2));
                            if (dayInt > 31 && dayInt<1) {
                                ed3.setError("Zly format daty. Wartosc dnia 1-31 !");
                            } else {
                                next++;
                            }
                            int monthInt = Integer.parseInt(date.substring(3, 5));
                            if (monthInt > 12 && monthInt<1) {
                                ed3.setError("Zly format daty. Wartosc miesiaca 1-12!");
                            } else {
                                next++;
                            }
                            int yearInt = Integer.parseInt(date.substring(6, 10));
                            next++;
                        } catch (NumberFormatException e) {
                            ed3.setError("Zly format daty. Wprowadz dd/mm/yyyy !");
                        }

                    } else {
                        ed3.setError("Zly format daty. Wprowadz dd/mm/yyyy !");
                    }

                    if (next == 5) {

                        data = data.substring(0, 10);
                        DatabaseManager zb = new DatabaseManager(myView.getContext());
                        zb.dodajWydatek(nazwa, rodzaj, data, kosztD);


                        Toast.makeText(FirstFragment.this.getActivity(), "Dodano ! ", Toast.LENGTH_SHORT).show();

                        ed1.setText("");
                        // ed2.setText("");
                        ed4.setText("");

                    }
                } else {
                    ed3.setError("Zly format daty. Wprowadz dd/mm/yyyy !");
                }

            }
        });

        return myView;


    }


}


