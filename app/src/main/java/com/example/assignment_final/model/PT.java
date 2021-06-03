package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

import java.util.Arrays;

public class PT {
    private  Integer ID,count;
    private  String Name;
    private  String Date;
    private  Integer Money;
    private  String Note;
    private byte[] images;
    private String images2;
    private String ID2;
    private int rate;
    public PT() {
    }

    public PT( String name, String date, Integer money, String note,String images2) {
        Name = name;
        Date = date;
        Money = money;
        Note = note;
        this.images2= images2;
    }



    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getImages2() {
        return images2;
    }

    public void setImages2(String images2) {
        this.images2 = images2;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Exclude
    public String getID2() {
        return ID2;
    }

    @Exclude
    public void setID2(String ID2) {
        this.ID2 = ID2;
    }

    @Override
    public String toString() {
        return "PT{" +
                "ID=" + ID +
                ", count=" + count +
                ", Name='" + Name + '\'' +
                ", Date='" + Date + '\'' +
                ", Money=" + Money +
                ", Note='" + Note + '\'' +
                ", images=" + Arrays.toString( images ) +
                ", images2='" + images2 + '\'' +
                ", ID2='" + ID2 + '\'' +
                ", rate=" + rate +
                '}';
    }
}
