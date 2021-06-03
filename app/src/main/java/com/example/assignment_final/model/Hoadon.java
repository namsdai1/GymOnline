package com.example.assignment_final.model;

public class Hoadon {
    String IDHD;
    String dateHD;
    int tongtien;
    String user;
    String fullname;
    String phone;
    String address;
    String status;

    public Hoadon(String dateHD, int tongtien, String user, String status) {
        this.dateHD = dateHD;
        this.tongtien = tongtien;
        this.user = user;
        this.status = status;
    }

    public Hoadon() {
    }

    public Hoadon(String IDHD, String dateHD, int tongtien, String user, String status, String fullname, String phone, String address) {
        this.IDHD = IDHD;
        this.user=user;
        this.dateHD = dateHD;
        this.tongtien = tongtien;
        this.fullname=fullname;
        this.phone=phone;
        this.address=address;
        this.status=status;
    }

    public Hoadon(String IDHD, String dateHD, String status) {
        this.IDHD = IDHD;
        this.dateHD = dateHD;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIDHD() {
        return IDHD;
    }

    public void setIDHD(String IDHD) {
        this.IDHD = IDHD;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getDateHD() {
        return dateHD;
    }

    public void setDateHD(String dateHD) {
        this.dateHD = dateHD;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
