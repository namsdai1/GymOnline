package com.example.assignment_final.model;

import java.util.Arrays;

public class HoaDonChiTiet {
    private int tongTien,lesson,soluongproduct,moneyProduct, soluong;
    private String IDProduct, IDPT,maHD,nameUsser,namePT,accountID,nameCourse, ngayThanhToan,ngayMoKhoa,trangThai,nameProduct,motaProduct,fullnameUser,phonnumber,address,IDCourse,Idproducttype,IDHoaDon;
    private Product product;
    private User user;
    byte[] hinhanh;
    boolean rateProduct;
    int maHDxtt;
    String imgProduct;
    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String IDProduct,int lesson, String IDPT,String IDCourse, String accountID,int tongTien, String nameUsser,String namePT,String nameCourse, String ngayThanhToan, String trangThai,String ngayMoKhoa) {
        this.lesson = lesson;
        this.IDPT = IDPT;
        this.maHD = accountID;
        this.IDCourse= IDCourse;
        this.tongTien = tongTien;
        this.nameUsser=nameUsser;
        this.namePT = namePT;
        this.nameCourse = nameCourse;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.ngayMoKhoa= ngayMoKhoa;
        this.IDProduct=IDProduct;
    }




    //product

    //
    public HoaDonChiTiet(int moneyProduct, int soluongproduct, String nameProduct, String imgProduct) {
        this.moneyProduct = moneyProduct;
        this.soluongproduct = soluongproduct;
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
    }

    public HoaDonChiTiet(String IDHoaDon,String IDProduct, int lesson, String IDPT, String IDCourse, String accountID, int tongTien, String nameUsser, String namePT, String nameCourse, String ngayThanhToan, String trangThai, String ngayMoKhoa,int soluongproduct) {
        this.lesson = lesson;
        this.soluongproduct=soluongproduct;
        this.IDPT = IDPT;
        this.maHD = accountID;
        this.IDCourse= IDCourse;
        this.tongTien = tongTien;
        this.nameUsser=nameUsser;
        this.namePT = namePT;
        this.nameCourse = nameCourse;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.ngayMoKhoa= ngayMoKhoa;
        this.IDHoaDon=IDHoaDon;
        this.IDProduct=IDProduct;
    }

    //product
    public HoaDonChiTiet(String maHD, String ngayThanhToan, String IDProduct , int soluongproduct, String nameUsser, String trangThai, String nameProduct, int moneyProduct, int soluong, String idProductType, String imgProduct) {
        this.maHD = maHD;
        this.nameUsser = nameUsser;
        this.IDProduct = IDProduct;
        this.soluongproduct = soluongproduct;
        this.moneyProduct = moneyProduct;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThai = trangThai;
        this.nameProduct = nameProduct;
        this.imgProduct = imgProduct;
        this.Idproducttype = idProductType;
        this.soluong=soluong;
    }

    public HoaDonChiTiet(String IDCourse, String IDProduct, String IDPT, String accountID, String trangThai) {
        this.IDPT = IDPT;
        this.IDCourse = IDCourse;
        this.accountID = accountID;
        this.IDProduct = IDProduct;
        this.trangThai = trangThai;
    }

    public String getIDHoaDon() {
        return IDHoaDon;
    }

    public void setIDHoaDon(String IDHoaDon) {
        this.IDHoaDon = IDHoaDon;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public int getSoluongproduct() {
        return soluongproduct;
    }

    public void setSoluongproduct(int soluongproduct) {
        this.soluongproduct = soluongproduct;
    }

    public int getMoneyProduct() {
        return moneyProduct;
    }

    public void setMoneyProduct(int moneyProduct) {
        this.moneyProduct = moneyProduct;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public String getIDPT() {
        return IDPT;
    }

    public void setIDPT(String IDPT) {
        this.IDPT = IDPT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNameUsser() {
        return nameUsser;
    }

    public void setNameUsser(String nameUsser) {
        this.nameUsser = nameUsser;
    }

    public String getNamePT() {
        return namePT;
    }

    public void setNamePT(String namePT) {
        this.namePT = namePT;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getNgayMoKhoa() {
        return ngayMoKhoa;
    }

    public void setNgayMoKhoa(String ngayMoKhoa) {
        this.ngayMoKhoa = ngayMoKhoa;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getMotaProduct() {
        return motaProduct;
    }

    public void setMotaProduct(String motaProduct) {
        this.motaProduct = motaProduct;
    }

    public String getFullnameUser() {
        return fullnameUser;
    }

    public void setFullnameUser(String fullnameUser) {
        this.fullnameUser = fullnameUser;
    }

    public String getPhonnumber() {
        return phonnumber;
    }

    public void setPhonnumber(String phonnumber) {
        this.phonnumber = phonnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIDCourse() {
        return IDCourse;
    }

    public void setIDCourse(String IDCourse) {
        this.IDCourse = IDCourse;
    }

    public String getIdproducttype() {
        return Idproducttype;
    }

    public void setIdproducttype(String idproducttype) {
        Idproducttype = idproducttype;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public boolean isRateProduct() {
        return rateProduct;
    }

    public void setRateProduct(boolean rateProduct) {
        this.rateProduct = rateProduct;
    }

    public int getMaHDxtt() {
        return maHDxtt;
    }

    public void setMaHDxtt(int maHDxtt) {
        this.maHDxtt = maHDxtt;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    @Override
    public String toString() {
        return "HoaDonChiTiet{" +
                "tongTien=" + tongTien +
                ", lesson=" + lesson +
                ", soluongproduct=" + soluongproduct +
                ", moneyProduct=" + moneyProduct +
                ", soluong=" + soluong +
                ", IDProduct='" + IDProduct + '\'' +
                ", IDPT='" + IDPT + '\'' +
                ", maHD='" + maHD + '\'' +
                ", nameUsser='" + nameUsser + '\'' +
                ", namePT='" + namePT + '\'' +
                ", accountID='" + accountID + '\'' +
                ", nameCourse='" + nameCourse + '\'' +
                ", ngayThanhToan='" + ngayThanhToan + '\'' +
                ", ngayMoKhoa='" + ngayMoKhoa + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", motaProduct='" + motaProduct + '\'' +
                ", fullnameUser='" + fullnameUser + '\'' +
                ", phonnumber='" + phonnumber + '\'' +
                ", address='" + address + '\'' +
                ", IDCourse='" + IDCourse + '\'' +
                ", Idproducttype='" + Idproducttype + '\'' +
                ", IDHoaDon='" + IDHoaDon + '\'' +
                ", product=" + product +
                ", user=" + user +
                ", hinhanh=" + Arrays.toString(hinhanh) +
                ", rateProduct=" + rateProduct +
                ", maHDxtt=" + maHDxtt +
                ", imgProduct='" + imgProduct + '\'' +
                '}';
    }
}
