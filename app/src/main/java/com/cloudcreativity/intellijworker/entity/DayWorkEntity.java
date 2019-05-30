package com.cloudcreativity.intellijworker.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DayWorkEntity {
    private String direction;
    private long createTime;
    private int id;
    private int year;
    private int month;
    private int day;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String formatDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return simpleDateFormat.format(new Date(this.createTime));
    }
}
