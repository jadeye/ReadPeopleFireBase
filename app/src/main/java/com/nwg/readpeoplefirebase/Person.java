package com.nwg.readpeoplefirebase;

public class Person {

    private String name;
    private String day;
    private String month;
    private String year;
    private String details;
    private int _id;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int get_id() { return _id; }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Person() {
    }

}
