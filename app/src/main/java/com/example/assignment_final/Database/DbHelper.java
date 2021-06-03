package com.example.assignment_final.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelper extends SQLiteOpenHelper
{
    public static String ID= null;
    public static String IDuser= null;
    public static String PassUser= null;
    public static String Username= null;
    static final String DbName = "QLGYM";
    static final int Version = 1;
    public DbHelper(@Nullable Context context) {
        super(context, DbName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE ACCOUNT(" +
                "accountID INTEGER primary key autoincrement," +
                "userName CHAR(20)," +
                "passWord CHAR(50)," +
                "imgUser BLOB," +
                "fullName CHAR(50)," +
                "address CHAR(100)," +
                "phoneNumber CHAR(10))");

        db.execSQL("INSERT INTO ACCOUNT(userName,passWord,fullName,address,phoneNumber) VALUES('admin','123','Hoàng Thiên Chương','Long Khánh - Đồng Nai','0933961814')");
        db.execSQL("INSERT INTO ACCOUNT(userName,passWord,fullName,address,phoneNumber) VALUES('chuong','123','Hoàng Thiên Chương','Long Khánh - Đồng Nai','0933961814')");
        db.execSQL("INSERT INTO ACCOUNT(userName,passWord,fullName,address,phoneNumber) VALUES('phong','123','Dương Tấn Nhật Phong','An Giang','0936733444')");


        db.execSQL("CREATE TABLE PT" +
        "(PTID INTEGER PRIMARY KEY AUTOINCREMENT,"+
        "namePT CHAR(20), " +
        "birthDayPT CHAR(20)," +
        "moneyPT INTEGER," +
                "imgPT BLOB," +
        "notePT CHAR(100))");

        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Colin','1996/08/20',1000,'Có nhiều kinh nghiệm về fitness')");
        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Mark','1990/10/01',3000,'Có nhiều kinh nghiệm về boxing')");
        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Elia','1998/05/10',500,'Thời gian linh động')");
        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Henry','1996/09/29',1500,'Có nhiều kinh nghiệm về fitness')");
        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Kenvin','1995/01/01',1000,'Có nhiều kinh nghiệm về fitness')");
        db.execSQL("INSERT INTO PT(namePT,birthDayPT,moneyPT,notePT) VALUES('Kenvi','1995/01/01',1000,'Có nhiều kinh nghiệm về fitness')");

        db.execSQL("CREATE TABLE COURSE" +
                "(courseID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nameCourse CHAR(20), " +
                "dateCourse CHAR(20)," +
                "moneyCourse INTEGER," +
                "lesson INTEGER)");

        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Fitness','2018/02/20',2000,90)");
        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Boxing','2020/12/10',1000,30)");
        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Jumba','2020/10/15',400,30)");
        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Belly dance','2020/02/29',500,60)");
        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Yoga','2020/01/11',1000,60)");
        db.execSQL("INSERT INTO COURSE(nameCourse,dateCourse,moneyCourse,lesson) VALUES('Yogu','2020/01/11',1000,60)");

        db.execSQL("CREATE TABLE SCHEDULE" +
                "(scheduleID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dateSchedule CHAR(20)," +
                "hourSchedule CHAR(20)," +
                "courseID integer references COURSE(courseID))");

        db.execSQL("CREATE TABLE BUSINESS(IDBusiness INTEGER PRIMARY KEY AUTOINCREMENT,TenBusiness CHAR(20))");
        db.execSQL("INSERT INTO BUSINESS(TenBusiness) VALUES('PRODUCT')");
        db.execSQL("INSERT INTO BUSINESS(TenBusiness) VALUES('COURSE')");

        db.execSQL("CREATE TABLE BILL" +
                "(billID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dateBill CHAR(20)," +
                "courseID integer references COURSE(courseID)," +
                "IdProduct integer references PRODUCT(IdProduct)," +
                "PTID integer references PT(PTID)," +
                "accountID integer references ACCOUNT(AccountID)," +
                "trangThai CHAR(20)," +
                "soluongbill integer," +
                "IDHD integer references HOADON(IDHD))") ;
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/12/12',2,1,1,2,'',1,1)");
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/09/10',1,2,2,2,'',1,1)");
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/07/02',3,3,5,2,'dathanhtoan',1,1)");
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/10/23',1,2,2,3,'',1,1)");
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/11/10',2,2,4,3,'',1,1)");
        db.execSQL("INSERT INTO BILL(dateBill,courseID,IdProduct,PTID,accountID,trangThai,soluongbill,IDHD) VALUES('2020/08/09',4,2,2,3,'',1,1)");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/12/12',2,1,2,'')");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/09/10',1,2,2,'')");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/07/02',3,5,2,'')");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/10/23',1,2,3,'')");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/11/10',2,4,3,'')");
//        db.execSQL("INSERT INTO BILL(dateBill,courseID,PTID,accountID,trangThai) VALUES('2020/08/09',4,2,3,'')");
        db.execSQL("CREATE TABLE HOADON(IDHD integer,dateHD char(20),tongtien integer,accountID integer references ACCOUNT(AccountID),Status char(20))");
        db.execSQL("INSERT INTO HOADON VALUES (1,'2020/12/1',0,2,'')");


        //Product type
        db.execSQL("CREATE TABLE PRODUCTTYPE (" +
                "IdProducttype INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenProduct CHAR(20)," +
                "IDBusiness INTEGER references BUSINESS(IDBusiness))");

        db.execSQL("INSERT INTO PRODUCTTYPE(TenProduct,IDBusiness) VALUES('Protein',1)");
        db.execSQL("INSERT INTO PRODUCTTYPE(TenProduct,IDBusiness) VALUES('Bcaa',1)");
        db.execSQL("INSERT INTO PRODUCTTYPE(TenProduct,IDBusiness) VALUES('Accessories',1)");
        db.execSQL("INSERT INTO PRODUCTTYPE(TenProduct,IDBusiness) VALUES('Milk',1)");

        //poruct
        db.execSQL("CREATE TABLE PRODUCT" +
                "(IdProduct INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "NameProduct CHAR(20), " +
                "MotaProduct CHAR(50), " +
                "MoneyProduct CHAR(20)," +
                "soluong INTEGER," +
                "rateProduct boolean," +
                "imgProduct BLOB," +
                "IdProducttype INTEGER references PRODUCTTYPE(IdProducttype))");
        db.execSQL("INSERT INTO PRODUCT(NameProduct,MotaProduct,MoneyProduct,soluong,rateProduct,IdProducttype) VALUES('Hydro Whey Zero ','Sữa Tăng Cơ Hydro Whey Zero BiotechUSA',500,5,'true',4)");
        db.execSQL("INSERT INTO PRODUCT(NameProduct,MotaProduct,MoneyProduct,soluong,rateProduct,IdProducttype) VALUES('Găng Tay tập','Găng tay tập GYM là phụ kiện không thể thiếu cho các bạn Nam, Nữ tập các bài tập với tạ, xà đơn,… ',2000,5,'true',3)");
        db.execSQL("INSERT INTO PRODUCT(NameProduct,MotaProduct,MoneyProduct,soluong,rateProduct,IdProducttype) VALUES('BCAA ZERO','Bcaa Zero của BiotechUSA tăng sức bền, kéo dài thời gian tập luyện.',1000,5,'true',2)");

        //exercise online
        db.execSQL("CREATE TABLE EXERCISEONLINE(" +
                "Idexercise integer PRIMARY KEY AUTOINCREMENT," +
                "Nameexercise String," +
                "imgExercise Blog," +
                "courseID integer references COURSE(courseID)) ");
        db.execSQL("INSERT INTO EXERCISEONLINE(Nameexercise,courseID) VALUES('Practice lifting',1)");
        db.execSQL("INSERT INTO EXERCISEONLINE(Nameexercise,courseID) VALUES('Practice dancing',2)");
        db.execSQL("INSERT INTO EXERCISEONLINE(Nameexercise,courseID) VALUES('Practice lifting',3)");

        db.execSQL("CREATE TABLE DISCOUNT(IdDISCOUNT INTEGER PRIMARY KEY AUTOINCREMENT,datekt CHAR(20),moneydiscount float,datebt char(20),IDBusiness integer references BUSINESS(IDBusiness),MaKhuyenMai char(20))");
        db.execSQL("INSERT INTO DISCOUNT(datekt,moneydiscount,datebt,IDBusiness,MaKhuyenMai) VALUES ('2020/11/31',0.1,'2020/11/26',1,'FA')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PT");
        db.execSQL("DROP TABLE IF EXISTS COURSE");
        db.execSQL("DROP TABLE IF EXISTS BILL");
    }
}
