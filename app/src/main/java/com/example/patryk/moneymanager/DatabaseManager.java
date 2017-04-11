package com.example.patryk.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DatabaseManager extends SQLiteOpenHelper{

    public DatabaseManager(Context context) {
        super(context, "money1.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table wydatki(" +
                        "nr integer primary key autoincrement," +
                        "nazwa text," +
                        "rodzaj text," +
                        "data text," +
                        "koszt double);" +
                        ""
                        );

       db.execSQL(
               "cmreate table rodzaje(" +
                        "nrR integer primary key autoincrement," +
                        "nazwaR text);" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void dodajWydatek(String nazwa, String rodzaj, String data, double koszt){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa", nazwa);
        wartosci.put("rodzaj",rodzaj);
        wartosci.put("data",data );
        wartosci.put("koszt", koszt);
        db.insertOrThrow("wydatki",null, wartosci);
    }

    public Cursor dajWszystkie(){
        String[] kolumny={"nr","nazwa","rodzaj","data","koszt"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("wydatki",kolumny,null,null,null,null,null);
        return kursor;
    }

    public Cursor dajWszystkieSort(){
        String[] kolumny={"nr","nazwa","rodzaj","data","koszt"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("wydatki",kolumny,null,null,null,null,"koszt DESC",null);
        return kursor;
    }

    public Cursor dajWydatki(String pole, String wartosc, String sort){

        SQLiteDatabase db = getReadableDatabase();
        String[] kolumny={"nr","nazwa","rodzaj","data","koszt"};
        String args[]={wartosc+""};
        Cursor kursor=db.query("wydatki",kolumny,pole+"=?",args,null,null,"koszt "+sort ,null);

        return kursor;
    }

    public Cursor dajWydatekPoID(int nr){

        SQLiteDatabase db = getReadableDatabase();
        String[] kolumny={"nr","nazwa","rodzaj","data","koszt"};
        String args[]={nr+""};
        Cursor kursor=db.query("wydatki",kolumny,"nr"+"=?",args,null,null,null,null);

        return kursor;
    }


    public void dodajRodzaj(String nazwa){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwaR", nazwa);
        db.insertOrThrow("rodzaje",null, wartosci);
    }

    public Cursor dajWszystkieRodzaje(){
        String[] kolumny={"nrR","nazwaR"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("rodzaje",kolumny,null,null,null,null,null);
        return kursor;
    }

    public void kasujWydatek(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] argumenty ={ ""+id};
        db.delete("wydatki","nr=?",argumenty);
    }

    public void aktualizujWydatek(int nr,String nazwa, String kategoria, String data,double koszt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa", nazwa);
        wartosci.put("rodzaj",kategoria);
        wartosci.put("data", data);
        wartosci.put("koszt", koszt);
        String args[]={nr+""};
        db.update("wydatki", wartosci,"nr=?",args);
    }



}
