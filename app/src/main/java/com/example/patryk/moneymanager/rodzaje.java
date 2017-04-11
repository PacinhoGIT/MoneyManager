package com.example.patryk.moneymanager;

/**
 * Created by patryk on 2017-03-21.
 */

public class rodzaje {

    String rodzaj;
    int img;

    public rodzaje(String rodzaj, int img)
    {
        this.rodzaj=rodzaj;
        this.img=img;
    }

    public String getText(){
        return rodzaj;
    }

   public Integer getImageId(){
       return img;
  }
}
