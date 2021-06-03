package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

public class Course {
    private  String ID;
    private  String name;
    private  String date;
    private  Integer money;
    private  Integer lesson;

    public Course() {
    }

    public Course(String ID, String name, String date, Integer money, Integer lesson) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.money = money;
        this.lesson = lesson;
    }

    public Course(String name, String date, Integer money, Integer lesson) {
        this.name = name;
        this.date = date;
        this.money = money;
        this.lesson = lesson;
    }
    @Exclude
    public String getID() {
        return ID;
    }

    @Exclude
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        money = money;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer count) {
        lesson = lesson;
    }

    @Override
    public String toString() {
        return "Course{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", money=" + money +
                ", lesson=" + lesson +
                '}';
    }


}
