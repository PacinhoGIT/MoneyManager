package com.example.patryk.moneymanager;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by user on 12/31/15.
 */
public class ThirdFragment extends Fragment{

    View myView;


    private ArrayList<String> rodzajeList;
    TextView tv1,tv2,tv33;
    Button b1;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    RadioGroup radioGroup,radioGroup1;
    ImageButton imgBtn2;
    Spinner s;

    double koszt1;
    String rodzaj;
    String kryteria;
    String sortowanie ;
    String param;

    private String[] arraySpinner;
    int year,day,month;
    String day1,month1;
    EditText ed1;

    boolean valid=true;

    String rodzaj11 ;

    int nr2;
    int indexEdit;
    String nazwa2 ;
    String rodzaj2;
    String data2 ;
    double koszt2; ;
    String monthS;
    EditText ed3;

    String day111;

    private int indexRemove;

    private ArrayList<Integer> index;

    //private ArrayAdapter<String> adapter;
    //private ArrayList<String> arrayList;

    private ArrayList<pola> polaList;
    private ArrayList<rodzaje> listOfRodzaje;
    private ArrayList<String> rodzajeArray;
    private CustomAdapter adapter1;
    private ListAdapter listAdapter;

    pola p;


    Integer[] imgRodzaje = {
            R.drawable.jedzenie,
            R.drawable.napoje,
            R.drawable.ic_menu_manage,
            R.drawable.transport,
            R.drawable.inne
    };

    ListView lV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.third_layout, container, false);

        tv1 = (TextView)myView.findViewById(R.id.pokazWydatek);
        tv2 = (TextView)myView.findViewById(R.id.tvSortowanie);
        tv33 = (TextView)myView.findViewById(R.id.sumaShow);

        b1=(Button)myView.findViewById(R.id.szukajBtn);

        rb1=(RadioButton)myView.findViewById(R.id.nazwaRB);
        rb2=(RadioButton)myView.findViewById(R.id.rodzajRB);
        rb3=(RadioButton)myView.findViewById(R.id.dataRB);

        rb4=(RadioButton)myView.findViewById(R.id.rbMalejaco);
        rb5=(RadioButton)myView.findViewById(R.id.rbRosnaco);



        imgBtn2=(ImageButton)myView.findViewById(R.id.imageButton2);
        imgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                wybierzdate();

            }
        });


        imgBtn2.setVisibility(View.INVISIBLE);
        ed1 = (EditText) myView.findViewById(R.id.szukaj);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        month+=1;

        if(month<10)
        {
            month1="0"+month;
        }

        if(day<10)
        {
            day1="0"+day;
        }
        else
        {
            day1=""+day;
        }

        rodzajeArray = new ArrayList<>();
        index = new ArrayList<>();
        listOfRodzaje = new ArrayList<rodzaje>();
        int z=0;


        DatabaseManager db2 = new DatabaseManager(myView.getContext());

        Cursor k = db2.dajWszystkieRodzaje();
        while (k.moveToNext()) {

            String rodzaj = k.getString(1);

            rodzajeArray.add(rodzaj);

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

        s = (Spinner) myView.findViewById(R.id.spinner2);

        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(myView.getContext(),R.layout.custom_spinner_layout,listOfRodzaje);
        s.setAdapter(adap);

        s.setVisibility(View.INVISIBLE);

        rodzaj = "Jedzenie";
        kryteria="nazwa";
        sortowanie ="DESC";

        lV = (ListView) myView.findViewById(R.id.listView2) ;

        polaList = new ArrayList<pola>();

        adapter1 = new CustomAdapter(myView.getContext(),polaList);

        listAdapter = new ListAdapter(myView.getContext(),polaList);

        lV.setAdapter(listAdapter);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {

                int ind = index.get(position);
                indexEdit =ind;

                indexRemove = position;


                DatabaseManager zb = new DatabaseManager(myView.getContext());
                Cursor k = zb.dajWydatekPoID(ind);
                while(k.moveToNext()) {

                    nr2 = k.getInt(0);
                    nazwa2 = k.getString(1);
                    rodzaj2 = k.getString(2);
                    data2 = k.getString(3);
                    koszt2 = k.getDouble(4);
                    //koszt1=koszt;



                    // makeAlertDialog(nr,nazwa,rodzaj,data,koszt);
                    // Toast.makeText(myView.getContext(),nr + nazwa + rodzaj +data + koszt,Toast.LENGTH_SHORT).show();

                }
                makeAlertDialog(nr2,nazwa2,rodzaj2,data2,koszt2);

                //Toast.makeText(myView.getContext(),pozycja,Toast.LENGTH_SHORT).show();
            }
        });

        radioGroup= (RadioGroup)myView.findViewById(R.id.radioCryteria);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                listAdapter.clear();
                index.clear();

                if (rb1.isChecked()) {

                    //filtrujBaze("nazwa", param);
                    kryteria="nazwa";
                    ed1.setText("");
                    ed1.setVisibility(View.VISIBLE);
                    s.setVisibility(View.INVISIBLE);
                    imgBtn2.setVisibility(View.INVISIBLE);

                }
                if (rb2.isChecked()) {

                    //filtrujBaze("rodzaj", param);
                    kryteria="rodzaj";
                    param="Jedzenie";
                    s.setVisibility(View.VISIBLE);
                    ed1.setVisibility(View.INVISIBLE);
                    imgBtn2.setVisibility(View.INVISIBLE);
                    s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ed1.setVisibility(View.INVISIBLE);
                           // param = parent.getSelectedItem().toString();
                            param = rodzajeArray.get(position).toString();
                            //Toast.makeText(FirstFragment.this.getActivity(),rodzaj,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            param="Jedzenie";
                        }

                    });

                }
                if (rb3.isChecked()) {

                    s.setVisibility(View.INVISIBLE);
                    ed1.setVisibility(View.VISIBLE);
                    imgBtn2.setVisibility(View.VISIBLE);

                    kryteria="data";

                    ed1.setText(new StringBuilder()
                            // Month is 0 based, just add 1
                            .append(day1)
                            .append("/")
                            .append(month1)
                            .append("/")
                            .append(year));

                }

            }
        });



        radioGroup1= (RadioGroup)myView.findViewById(R.id.radioSort);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (rb4.isChecked()) {

                    sortowanie = "DESC";


                }
                if (rb5.isChecked()) {

                    sortowanie = "ASC";

                }


                listAdapter.clear();
                index.clear();

                //filtrujBaze(kryteria, param,sortowanie);



            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter1.clear();
                // adapter.notifyDataSetChanged();




                if(kryteria.equals("rodzaj"))
                {
                    filtrujBaze("rodzaj", param,sortowanie);
                }
                else {

                     param = ed1.getText().toString();
                    if (param.length() > 0) {

                        if(kryteria.equals("data"))
                        {
                            if(param.substring(2,3).equals("/") && param.substring(5,6).equals("/"))
                            {
                                filtrujBaze(kryteria, param,sortowanie);
                            }
                            else
                            {
                                ed1.setError("Blad ! Zly format daty ! Podaj dd/mm/yyy !");
                            }
                        }
                        else {
                            filtrujBaze(kryteria, param,sortowanie);
                        }

                    } else {
                        ed1.setError("Blad ! Wprowadz wartosc pola !");
                    }
                }
            }

        });
        return myView;


    }


    public void filtrujBaze(String pole, String wartosc, String sort)
    {
        DatabaseManager DBm = new DatabaseManager(myView.getContext());


        Cursor k = DBm.dajWydatki(pole, wartosc,sort);



        koszt1=0.0;

        index.clear();


        while (k.moveToNext()) {
            int nr = k.getInt(0);
            String nazwa = k.getString(1);
            String rodzajj = k.getString(2);
            String data = k.getString(3);
            double koszt = k.getDouble(4);

            koszt1=koszt1+koszt;
            index.add(nr);

            //String dane = nazwa + " " + rodzaj + " " + data + " " + "    " + koszt;


            pola p = new pola(nazwa,rodzajj,data,koszt,nr);
            polaList.add(p);
            listAdapter.notifyDataSetChanged();

        }

        if(koszt1==0.0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());

            builder.setTitle("Ups...");

            builder.setMessage("Brak wyników dla podanych kryteriów !")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            listAdapter.clear();
        }
        tv33.setText("Suma : " +koszt1 + " PLN");


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
        else
        {
            month1=""+month;
        }

        if(day<10)
        {
            day1="0"+day;
        }
        else
        {
            day1=""+day;
        }

        //ed1.setText(day1 + "/" + (month1) + "/" + year);



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


                       ed1.setText(day1 + "/" + (monthS) + "/" + year);

                    }
                }, year, month-1, day);
        dpd.show();

    }

    public void makeAlertDialog(int nr, final String nazwa, final String rodzaj99, final String data, final double koszt)
    {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(myView.getContext());
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
        tv6.setText(rodzaj99);
        tv7.setText(data);
        tv8.setText(String.valueOf(koszt) + " PLN" );

        final int nr1 = nr;


        builder.setView(viewInflated)
                .setPositiveButton("Edytuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        makeEditDialog(nr1,nazwa,rodzaj99,data,koszt);

                    }
                })
                .setNegativeButton("Usun", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        confirmDialog(nr1);


                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

        //tv5.setText(nazwa);
        //tv6.setText(rodzaj);
        //tv7.setText(data);
        //tv8.setText(String.valueOf(koszt));

        android.app.AlertDialog alert = builder.create();

        alert.show();


    }

    public void confirmDialog(int id1)
    {

        final int index1 = id1;
        new android.app.AlertDialog.Builder(myView.getContext())
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

                        filtrujBaze(kryteria, param,sortowanie);

                        //koszt1 = koszt1 - koszt1;
                        tv33.setText("Suma : "+ " " + koszt1 + " PLN");


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

    public void makeEditDialog(int nr, String nazwa, String rodzaj100, String data, double koszt)
    {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(myView.getContext());
        builder.setTitle("Edycja wydatku nr : " +nr);
        View viewInflated = LayoutInflater.from(myView.getContext()).inflate(R.layout.edit_dialog, (ViewGroup) getView(), false);

        TextView tv1 = (TextView) myView.findViewById(R.id.editNazwaTV);
        TextView tv2 = (TextView) myView.findViewById(R.id.editKategoriaTV);
        TextView tv3 = (TextView) myView.findViewById(R.id.editDaataTV);
        TextView tv4 = (TextView) myView.findViewById(R.id.editKosztTV);

        final EditText ed1 = (EditText) viewInflated.findViewById(R.id.editNazwaET);
        final EditText ed3 = (EditText) viewInflated.findViewById(R.id.editDataET);
        final EditText ed4 = (EditText) viewInflated.findViewById(R.id.editKosztET);

        ImageButton calendarBtn = (ImageButton) viewInflated.findViewById(R.id.imgButtonEditCalendar);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year1 = c.get(Calendar.YEAR);
                int month1 = c.get(Calendar.MONTH);
                int day1 = c.get(Calendar.DAY_OF_MONTH);

                final String month11;
                final String day11;

                 month1=month1+1;

                if(month1<10)
                {
                    month11="0"+month1;
                }
                else
                {
                    month11=""+month1;
                }

                if(day<10)
                {
                    day111="0"+day1;
                }
                else
                {
                    day111=""+day1;
                }

                DatePickerDialog dpd = new DatePickerDialog(myView.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String monthSS="";
                                monthOfYear=monthOfYear+1;
                                if(monthOfYear<10)
                                {
                                    monthSS="0"+monthOfYear;
                                }
                                else
                                {
                                    monthSS=Integer.toString(monthOfYear);
                                }

                                if(dayOfMonth<10)
                                {
                                    day111="0"+dayOfMonth;
                                }
                                else
                                {
                                    day111=Integer.toString(dayOfMonth);
                                }


                                ed3.setText(day111 + "/" + (monthSS) + "/" + year);

                            }
                        }, year, month-1, day);
                dpd.show();
            }
        });

        //Spinner s = (Spinner) viewInflated.findViewById(R.id.editSpinner);


        rodzajeList = new ArrayList<>();
         ArrayList<rodzaje> listOfRodzaje1 = new ArrayList<rodzaje>();
        DatabaseManager db2 = new DatabaseManager(myView.getContext());

        int z=0;

        Cursor k = db2.dajWszystkieRodzaje();
        while (k.moveToNext()) {

            //int nr=k.getInt(0);
            String rodzaj101 = k.getString(1);

            rodzajeList.add(rodzaj101);

            if(z<4)
            {
                rodzaje r = new rodzaje(rodzaj101,imgRodzaje[z]);
                listOfRodzaje1.add(r);

            }
            else
            {
                rodzaje r = new rodzaje(rodzaj101,imgRodzaje[4]);
                listOfRodzaje1.add(r);
            }

            z++;


    }

    rodzaj11 ="Jedzenie";

       Spinner s = (Spinner) viewInflated.findViewById(R.id.editSpinner);

     //   s.setBackgroundColor(getResources().getColor(R.color.colorSpinner));

        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(myView.getContext(),R.layout.custom_spinner_layout,listOfRodzaje1);
        s.setAdapter(adap);

  //  ArrayAdapter<String> adapter = new ArrayAdapter<>(myView.getContext(),
        //    android.R.layout.simple_spinner_item, rodzajeList);
  //  s.setAdapter(adapter);
    s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           // rodzaj11 = parent.getSelectedItem().toString();
            //Toast.makeText(FirstFragment.this.getActivity(),rodzaj,Toast.LENGTH_SHORT).show();
            rodzaj11 = rodzajeList.get(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            rodzaj11 = "Jedzenie";
        }

    });


    for(int i=0; i < rodzajeList.size(); i++) {
            if(rodzaj100.trim().equals(rodzajeList.get(i).toString())){
                s.setSelection(i);
                break;
            }
        }

        ed3.setText(data);
        ed1.setText(nazwa);
        ed4.setText(String.valueOf(koszt) );


        final int nr1 = nr;



        builder.setView(viewInflated)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nazwa = ed1.getText().toString();
                        String data = ed3.getText().toString();
                        String koszt = ed4.getText().toString();

                        //valid(nazwa,data,koszt);

                        valid = true;

                        if (nazwa.length() < 4 || nazwa.length() > 10) {
                            ed1.setError("Wpisz minimum 4 znaki i maximum 10!");
                            //Toast.makeText(myView.getContext(),"nazwa !",Toast.LENGTH_SHORT).show();
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
                            index.clear();

                            filtrujBaze(kryteria,param,sortowanie);
                            listAdapter.notifyDataSetChanged();


                            tv33.setText("Suma :" + " " + koszt1 + " PLN");


                        }

                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });





        android.app.AlertDialog alert = builder.create();

        alert.show();


    }


}
