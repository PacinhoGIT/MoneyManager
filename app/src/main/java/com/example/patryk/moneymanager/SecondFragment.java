package com.example.patryk.moneymanager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 12/31/15.
 */
public class SecondFragment extends Fragment{

    View myView;

    ListView lV;

    TextView suma;

    double kosztI=0.0;
    double koszt1=0;

    int year,day,month;
    String day1,month1,monthS;

    private ArrayList<String> arrayList;
    private ArrayList<String> rodzajeList;
    private ArrayList<pola> polaList;
    private ArrayList<Integer> index;

    private ListAdapter listAdapter;
    private CustomAdapter adapter1;

    boolean valid=true;

    String rodzaj11 ;
    String nazwa ;
    String rodzaj;
    String data ;

    int nr;
    int indexEdit;
    int indexRemove;

    double koszt; ;

    EditText ed3;

    pola p;

    Integer[] imgRodzaje = {
            R.drawable.jedzenie,
            R.drawable.napoje,
            R.drawable.ic_menu_manage,
            R.drawable.transport,
            R.drawable.inne
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);

        lV = (ListView) myView.findViewById(R.id.listView);
        suma=(TextView) myView.findViewById(R.id.sumaTV);

        final DatabaseManager zb = new DatabaseManager(myView.getContext());

        arrayList = new ArrayList<String>();
        polaList = new ArrayList<pola>();
        index = new ArrayList<>();

        listAdapter = new ListAdapter(myView.getContext(),polaList);

        lV.setAdapter(listAdapter);

        Cursor k = zb.dajWszystkieSort();
        while(k.moveToNext()) {
            int nr1 = k.getInt(0);
            String nazwa1 = k.getString(1);
            String rodzaj1 = k.getString(2);
            String data1 = k.getString(3);
            double koszt1 = k.getDouble(4);

            index.add(nr1);

            kosztI=kosztI+koszt1;

            p = new pola(nazwa1,rodzaj1,data1,koszt1,nr1);
            polaList.add(p);
            listAdapter.notifyDataSetChanged();



        }

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {

                int ind = index.get(position);
                indexEdit =ind;

                indexRemove = position;

                Cursor k = zb.dajWydatekPoID(ind);
                while(k.moveToNext()) {

                         nr = k.getInt(0);
                         nazwa = k.getString(1);
                        rodzaj = k.getString(2);
                         data = k.getString(3);
                          koszt = k.getDouble(4);
                         koszt1=koszt;

                }
                makeAlertDialog(nr,nazwa,rodzaj,data,koszt);

            }
        });

        suma.setText("Suma : " + " " + kosztI + " PLN");

        return myView;
    }

    public void makeAlertDialog(int nr, final String nazwa, final String rodzaj, final String data, final double koszt)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
        builder.setTitle("Szczegoly wydatku nr : " +nr);
        View viewInflated = LayoutInflater.from(myView.getContext()).inflate(R.layout.input_dialog, (ViewGroup) getView(), false);

        TextView tv1 = (TextView) myView.findViewById(R.id.inputNazwaTV);
        TextView tv2 = (TextView) myView.findViewById(R.id.inputKategoriaTV);
        TextView tv3 = (TextView) myView.findViewById(R.id.inputDataTV);
        TextView tv4 = (TextView) myView.findViewById(R.id.inputKosztTV);

        final TextView tv5 = (TextView) viewInflated.findViewById(R.id.inputShowNazwa);
        final TextView tv6 = (TextView) viewInflated.findViewById(R.id.inputShowKategoria);
        final TextView tv7 = (TextView) viewInflated.findViewById(R.id.inputShowData);
        final TextView tv8 = (TextView) viewInflated.findViewById(R.id.inputShowKoszt);

        tv5.setText(nazwa);
        tv6.setText(rodzaj);
        tv7.setText(data);
        tv8.setText(String.valueOf(koszt) + " PLN" );

        final int nr1 = nr;


        builder.setView(viewInflated)
                .setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
               makeEditDialog(nr1,nazwa,rodzaj,data,koszt);

            }
        })
        .setNegativeButton("Usun", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                confirmDialog(nr1,koszt);

            }
        })
        .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}});

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void confirmDialog(int id1,  double kosztUsun)
    {

        final int index1 = id1;

        new AlertDialog.Builder(myView.getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Kasuj wydatek")
                .setMessage("Na pewno skasowac wybrany wydatek ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseManager zb = new DatabaseManager(myView.getContext());
                        zb.kasujWydatek(index1);

                        listAdapter.clear();
                        index.clear();

                        kosztI=0;

                        Cursor k = zb.dajWszystkieSort();

                        while(k.moveToNext()) {
                            int nr1 = k.getInt(0);
                            String nazwa1 = k.getString(1);
                            String rodzaj1 = k.getString(2);
                            String data1 = k.getString(3);
                            double koszt1 = k.getDouble(4);

                            kosztI=kosztI+koszt1;
                            index.add(nr1);

                            p = new pola(nazwa1,rodzaj1,data1,koszt1,nr1);
                            polaList.add(p);

                        }

                        suma.setText("Suma : " + " " + kosztI + " PLN");
                        listAdapter.notifyDataSetChanged();



                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void makeEditDialog(int nr, String nazwa, String rodzaj, String data, double koszt)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
        builder.setTitle("Edycja wydatku nr : " +nr);
        View viewInflated = LayoutInflater.from(myView.getContext()).inflate(R.layout.edit_dialog, (ViewGroup) getView(), false);

        TextView tv1 = (TextView) myView.findViewById(R.id.editNazwaTV);
        TextView tv2 = (TextView) myView.findViewById(R.id.editKategoriaTV);
        TextView tv3 = (TextView) myView.findViewById(R.id.editDaataTV);
        TextView tv4 = (TextView) myView.findViewById(R.id.editKosztTV);

        final EditText ed1 = (EditText) viewInflated.findViewById(R.id.editNazwaET);
        ed3 = (EditText) viewInflated.findViewById(R.id.editDataET);
        final EditText ed4 = (EditText) viewInflated.findViewById(R.id.editKosztET);

        ImageButton calendarBtn = (ImageButton) viewInflated.findViewById(R.id.imgButtonEditCalendar);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                wybierzdate();

            }
        });

        rodzajeList = new ArrayList<>();

        DatabaseManager db2 = new DatabaseManager(myView.getContext());

        ArrayList<rodzaje> listOfRodzaje1 = new ArrayList<rodzaje>();

        int z=0;


        Cursor k = db2.dajWszystkieRodzaje();
        while (k.moveToNext()) {

            String rodzaj1 = k.getString(1);

            rodzajeList.add(rodzaj1);

            if(z<4)
            {
                rodzaje r = new rodzaje(rodzaj1,imgRodzaje[z]);
                listOfRodzaje1.add(r);

            }
            else
            {
                rodzaje r = new rodzaje(rodzaj1,imgRodzaje[4]);
                listOfRodzaje1.add(r);
            }

            z++;

        }

        rodzaj11 ="Jedzenie";

        Spinner s = (Spinner) viewInflated.findViewById(R.id.editSpinner);

        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(myView.getContext(),R.layout.custom_spinner_layout,listOfRodzaje1);
        s.setAdapter(adap);


       // Spinner s = (Spinner) viewInflated.findViewById(R.id.editSpinner);
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(myView.getContext(),
          //      android.R.layout.simple_spinner_item, rodzajeList);
       // s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rodzaj11 = parent.getSelectedItem().toString();
                rodzaj11 = rodzajeList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rodzaj11 = "Jedzenie";
            }

        });


        for(int i=0; i < rodzajeList.size(); i++) {
            if(rodzaj.trim().equals(rodzajeList.get(i).toString())){

                Toast.makeText(myView.getContext(),listOfRodzaje1.get(i).toString(),Toast.LENGTH_SHORT);
                s.setSelection(i);
                break;
            }
        }

        ed1.setText(nazwa);
        ed3.setText(data);
        ed4.setText(String.valueOf(koszt) );

        final int nr1 = nr;

        builder.setView(viewInflated)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nazwa = ed1.getText().toString();
                        String data = ed3.getText().toString();
                        String koszt = ed4.getText().toString();

                        valid = true;

                        if (nazwa.length() < 4 || nazwa.length() > 10) {

                            ed1.setError("Wpisz minimum 4 znaki i maximum 10!");
                            valid = false;

                        } else
                        {

                        }

                        if (koszt.length() < 1) {
                            ed4.setError("Wpisz minimum 1 cyfre !");
                            //Toast.makeText(myView.getContext(),"koszt!",Toast.LENGTH_SHORT).show();
                            valid = false;
                        } else {
                            if (koszt.substring(0, 1).equals("-")) {
                                ed4.setError("Wartosc kwoty nie moze byc ujemna !");
                                //Toast.makeText(myView.getContext(),"koszt!",Toast.LENGTH_SHORT).show();
                                valid = false;
                            } else {
                                double kosztD = 0;


                                try {
                                    kosztD = Double.valueOf(koszt);
                                } catch (NumberFormatException numberError) {
                                    //Toast.makeText(myView.getContext(),"koszt!",Toast.LENGTH_SHORT).show();
                                }

                                if (kosztD == 0) {
                                    ed4.setError("Wartosc kwoty nie moze byc zerowa !");
                                    valid = false;
                                    //Toast.makeText(myView.getContext(),"koszt!",Toast.LENGTH_SHORT).show();

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
                                    if (dayInt > 31 && dayInt< 1) {
                                        ed3.setError("Zly format daty. Wartosc dnia 1-31 !");
                                        valid = false;
                                        //Toast.makeText(myView.getContext(),"data!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        //next++;
                                    }
                                    int monthInt = Integer.parseInt(date.substring(3, 5));
                                    if (monthInt > 12 && monthInt<1) {
                                        ed3.setError("Zly format daty. Wartosc miesiaca 1-12!");
                                        valid = false;
                                        //Toast.makeText(myView.getContext(),"data!",Toast.LENGTH_SHORT).show();
                                    } else {
                                        // next++;
                                    }
                                    int yearInt = Integer.parseInt(date.substring(6, 10));

                                } catch (NumberFormatException e) {
                                    valid = false;
                                    ed3.setError("Zly format daty. Wprowadz dd/mm/yyyy");
                                    //Toast.makeText(myView.getContext(),"data!",Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                ed3.setError("Zly format daty. Wprowadz dd/mm/yyyy");
                                //Toast.makeText(myView.getContext(),"data!",Toast.LENGTH_SHORT).show();
                                valid = false;
                            }

                        }

                        if(valid==true) {

                            double kosztDouble = Double.parseDouble(koszt);
                            DatabaseManager db1 = new DatabaseManager(myView.getContext());
                            db1.aktualizujWydatek(nr1, nazwa, rodzaj11, data, kosztDouble);

                            Toast.makeText(myView.getContext(),"Edycja zakończona sukcesem !",Toast.LENGTH_SHORT).show();

                            listAdapter.clear();

                            kosztI=0;

                            Cursor k = db1.dajWszystkieSort();
                            while(k.moveToNext()) {
                                int nr1 = k.getInt(0);
                                String nazwa1 = k.getString(1);
                                String rodzaj1 = k.getString(2);
                                String data1 = k.getString(3);
                                double koszt1 = k.getDouble(4);

                                kosztI=kosztI+koszt1;
                                index.add(nr1);


                                p = new pola(nazwa1,rodzaj1,data1,koszt1,nr1);
                                polaList.add(p);




                            }

                            listAdapter.notifyDataSetChanged();

                            suma.setText("Suma :" + " " + kosztI + " PLN");


                        }

                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();

        alert.show();


    }

    public boolean valid(String nazwa, String data, String koszt) {




        if(valid==false)
        {
            Toast.makeText(myView.getContext(),"Zle dane !",Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    public void wybierzdate()
    {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        month=month+1;
        if(month<10)
        {
            month1="0"+month;
        }

        DatePickerDialog dpd = new DatePickerDialog(myView.getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        monthS="";
                        monthOfYear=monthOfYear+1;
                        if(monthOfYear<10)
                        {
                            monthS="0"+monthOfYear;
                        }
                        else
                        {
                            monthS=Integer.toString(monthOfYear);
                        }

                        if(dayOfMonth<10)
                        {
                            day1="0"+dayOfMonth;
                        }
                        else
                        {
                            day1=Integer.toString(dayOfMonth);
                        }


                        ed3.setText(day1 + "/"
                                + (monthS) + "/" + year);

                    }
                }, year, month-1, day);
        dpd.show();

    }

}
