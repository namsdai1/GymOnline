package com.example.assignment_final.model;

public class Product {
    private String IdProduct;
    private String NameProduct;
    private int MoneyProduct;
    private int soluong;
    private boolean rateProduct;
    private String IdProducttype;
    private  byte[] imagesproduct;
    String imagesproduct2;
    private String  motaProduct;
    private String nameProductType;
    public Product() {
    }

    public Product(String idProduct,String nameProduct, int moneyProduct, String imagesproduct2) {
        IdProduct = idProduct;
        this.NameProduct=nameProduct;
        MoneyProduct = moneyProduct;
        this.imagesproduct2 = imagesproduct2;
    }


    public Product( String nameProduct,String motaProduct, int moneyProduct, int soluong, boolean rateProduct,String idProducttype, String imagesproduct2) {
        this.imagesproduct2 = imagesproduct2;
        NameProduct = nameProduct;
        MoneyProduct = moneyProduct;
        this.soluong = soluong;
        this.rateProduct = rateProduct;
        IdProducttype = idProducttype;
        this.motaProduct=motaProduct;

    }
    public Product( String idProduct,String nameProduct,String motaProduct, int moneyProduct, int soluong, boolean rateProduct,String idProducttype, String imagesproduct2) {
        this.imagesproduct2 = imagesproduct2;
        NameProduct = nameProduct;
        this.IdProduct=idProduct;
        MoneyProduct = moneyProduct;
        this.soluong = soluong;
        this.rateProduct = rateProduct;
        IdProducttype = idProducttype;
        this.motaProduct=motaProduct;

    }
    public Product( String idProduct,String nameProduct,String motaProduct, int moneyProduct, int soluong, boolean rateProduct,String idProducttype, String imagesproduct2,String nameProductType) {
        this.imagesproduct2 = imagesproduct2;
        NameProduct = nameProduct;
        this.IdProduct=idProduct;
        MoneyProduct = moneyProduct;
        this.soluong = soluong;
        this.rateProduct = rateProduct;
        IdProducttype = idProducttype;
        this.motaProduct=motaProduct;
        this.nameProductType=nameProductType;
    }

    public String getNameProductType() {
        return nameProductType;
    }

    public String getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(String idProduct) {
        IdProduct = idProduct;
    }

    public void setNameProductType(String nameProductType) {
        this.nameProductType = nameProductType;
    }

    public byte[] getImagesproduct() {
        return imagesproduct;
    }

    public void setImagesproduct(byte[] imagesproduct) {
        this.imagesproduct = imagesproduct;
    }


    public String getNameProduct() {
        return NameProduct;
    }

    public String getMotaProduct() {
        return motaProduct;
    }

    public void setMotaProduct(String motaProduct) {
        this.motaProduct = motaProduct;
    }

    public void setNameProduct(String NameProduct) {
        this.NameProduct = NameProduct;
    }

    public int getMoneyProduct() {
        return MoneyProduct;
    }

    public void setMoneyProduct(int moneyProduct) {
        MoneyProduct = moneyProduct;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public boolean isRateProduct() {
        return rateProduct;
    }

    public void setRateProduct(boolean rateProduct) {
        this.rateProduct = rateProduct;
    }

    public String getIdProducttype() {
        return IdProducttype;
    }

    public void setIdProducttype(String idProducttype) {
        IdProducttype = idProducttype;
    }

    public String getImagesproduct2() {
        return imagesproduct2;
    }

    public void setImagesproduct2(String imagesproduct2) {
        this.imagesproduct2 = imagesproduct2;
    }

}
