package com.example.yuvrajtalukdar.expensemonitor;

public class database_class {
    public String category = "";
    public float cost;
    public int day;
    public int id;
    public String item = "";
    public int month;
    public int year;

    database_class(float c, String i, String ca, int d, int m, int y, int id_no) {
        this.cost = c;
        this.item = i;
        this.category = ca;
        this.day = d;
        this.month = m;
        this.year = y;
        this.id = id_no;
    }
}
