package com.example.assignment_final.model;

import com.google.firebase.database.Exclude;

public class Schedule {
    private String nameCourse,dateCourse,dateSchedule,hourSchedule,courseID,scheduleID;
    private int moneyCourse,lesson;

    public Schedule(String dateSchedule, String courseID,String hourSchedule) {
        this.dateSchedule = dateSchedule;
        this.courseID = courseID;
        this.hourSchedule = hourSchedule;
    }

    public Schedule() {
    }


    public String getHourSchedule() {
        return hourSchedule;
    }

    public void setHourSchedule(String hourSchedule) {
        this.hourSchedule = hourSchedule;
    }

    @Exclude
    public String getScheduleID() {
        return scheduleID;
    }
    @Exclude
    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getDateCourse() {
        return dateCourse;
    }

    public void setDateCourse(String dateCourse) {
        this.dateCourse = dateCourse;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getDateSchedule() {
        return dateSchedule;
    }

    public void setDateSchedule(String dateSchedule) {
        this.dateSchedule = dateSchedule;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getMoneyCourse() {
        return moneyCourse;
    }

    public void setMoneyCourse(int moneyCourse) {
        this.moneyCourse = moneyCourse;
    }
}
