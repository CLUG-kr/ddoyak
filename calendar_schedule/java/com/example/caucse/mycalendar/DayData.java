package com.example.caucse.mycalendar;

public class DayData {
    private int itYear;
    private int itMonth;
    private int itDay;

    private int hour;
    private int min;
    private String txt;

    public DayData(){
        init();
    }

    public DayData(int Y, int M, int D){
        itYear = Y;
        itMonth = M;
        itDay = D;
        init();
    }

    public void init() {
        hour = 0;
        min = 0;
        txt = "";
    }

    public void setYear(int Y){
        itYear = Y;
    }

    public void setMonth(int M){
        itMonth = M;
    }

    public void setDay(int D){
        itDay = D;
    }

    public int getYear(){
        return itYear;
    }

    public int getMonth(){
        return itMonth;
    }

    public int getDay(){
        return itDay;
    }

    public void setString(String T){
        txt = T;
    }

    public void setTime(int H, int M){
        hour = H;
        min = M;
    }

    public String getString(){
        return txt;
    }

    public String getTime(){
        return ""+hour+" : "+min;
    }

    public String getSchedule(){
        return ""+hour+" : " + min + "  " +txt;
    }
}