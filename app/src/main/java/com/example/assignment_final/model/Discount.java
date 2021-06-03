package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

public class Discount {
    String IDdiscout;
    String ngaykt;
    Float giamgia;
    String ngaybt;
    String typeDiscount;
    String maKhuyenmmai;
    String namBusiness;

public Discount(String ngaykt, Float giamgia,String ngaybt,String maKhuyenmmai) {
    this.ngaykt = ngaykt;
    this.giamgia = giamgia;
    this.ngaybt=ngaybt;

    this.maKhuyenmmai=maKhuyenmmai;
}


    public Discount() {
    }

    public Discount(String ngaykt, Float giamgia, String ngaybt, String typeDiscount, String maKhuyenmmai) {
        this.IDdiscout = IDdiscout;
        this.ngaykt = ngaykt;
        this.giamgia = giamgia;
        this.ngaybt = ngaybt;
        this.typeDiscount = typeDiscount;
        this.maKhuyenmmai = maKhuyenmmai;
    }

    @Exclude
    public String getIDdiscout() {
        return IDdiscout;
    }
    @Exclude
    public void setIDdiscout(String IDdiscout) {
        this.IDdiscout = IDdiscout;
    }



    public String getNamBusiness() {
        return namBusiness;
    }

    public void setNamBusiness(String namBusiness) {
        this.namBusiness = namBusiness;
    }

    public String getMaKhuyenmmai() {
        return maKhuyenmmai;
    }

    public void setMaKhuyenmmai(String maKhuyenmmai) {
        this.maKhuyenmmai = maKhuyenmmai;
    }

    public String getNgaybt() {
        return ngaybt;
    }

    public void setNgaybt(String ngaybt) {
        this.ngaybt = ngaybt;
    }

    public String getTypeDiscount() {
        return typeDiscount;
    }

    public void setTypeDiscount(String typeDiscount) {
        this.typeDiscount = typeDiscount;
    }

    public String getNgaykt() {
        return ngaykt;
    }

    public void setNgaykt(String ngaykt) {
        this.ngaykt = ngaykt;
    }

    public Float getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(Float giamgia) {
        this.giamgia = giamgia;
    }
}
