package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

public class Producttype {
    String idProducttype;
    String nameProducttype;

    public Producttype(String idProducttype, String nameProducttype) {
        this.idProducttype = idProducttype;
        this.nameProducttype = nameProducttype;
    }

    @Exclude
    public String getIdProducttype() {
        return idProducttype;
    }
    @Exclude
    public void setIdProducttype(String idProducttype) {
        this.idProducttype = idProducttype;
    }

    public String getNameProducttype() {
        return nameProducttype;
    }

    public void setNameProducttype(String nameProducttype) {
        this.nameProducttype = nameProducttype;
    }

    @Override
    public String toString() {
        return "Producttype{" +
                "idProducttype='" + idProducttype + '\'' +
                ", nameProducttype='" + nameProducttype + '\'' +
                '}';
    }
}
