package com.example.yuvrajtalukdar.expensemonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SQLite_Handler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    String CATEGORY = "category";
    String COLUMN_ID = "id";
    String COST = "cost";
    String DAY = "day";
    String ITEM = "item";
    String MONTH = "month";
    String TABLE_NAME1 = "category_table";
    String TABLE_NAME2 = "expense";
    String YEAR = "year";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + this.TABLE_NAME1 + " ( " + this.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + this.CATEGORY + " VARCHAR UNIQUE);");
        db.execSQL("create table " + this.TABLE_NAME2 + " ( " + this.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + this.COST + " REAL, " + this.ITEM + " VARCHAR, " + this.CATEGORY + " VARCHAR, " + this.DAY + " INTEGER, " + this.MONTH + " INTEGER, " + this.YEAR + " INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_NAME2);
        onCreate(db);
    }

    public SQLite_Handler(Context context, CursorFactory factory) {
        super(context, DATABASE_NAME, factory, 1);
    }

    public String return_category_record(int sino) {
        String s = "";
        Cursor c = getWritableDatabase().rawQuery("SELECT * FROM " + this.TABLE_NAME1 + " WHERE ID=" + Integer.toString(sino + 1) + "", null);
        c.moveToFirst();
        return c.getString(c.getColumnIndex(this.CATEGORY));
    }

    public long add_category(String category_name) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.CATEGORY, category_name);
        long s = db.insert(this.TABLE_NAME1, null, contentValues);
        db.close();
        return s;
    }

    public long delete_category(String category_name) {
        SQLiteDatabase db = getReadableDatabase();
        long s = (long) db.delete(this.TABLE_NAME1, this.CATEGORY + " = '" + category_name + "'", null);
        db.close();
        return s;
    }

    public ArrayList get_category_list() {
        ArrayList column_name_list = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(this.TABLE_NAME1, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            column_name_list.add(c.getString(c.getColumnIndex(this.CATEGORY)));
            c.moveToNext();
        }
        db.close();
        return column_name_list;
    }

    public long add_data(float cost, String item, String category, int day, int month, int year) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.COST, Float.valueOf(cost));
        contentValues.put(this.ITEM, item);
        contentValues.put(this.CATEGORY, category);
        contentValues.put(this.DAY, Integer.valueOf(day));
        contentValues.put(this.MONTH, Integer.valueOf(month));
        contentValues.put(this.YEAR, Integer.valueOf(year));
        long s = db.insert(this.TABLE_NAME2, null, contentValues);
        db.close();
        return s;
    }

    public ArrayList<database_class> get_data() {
        ArrayList<database_class> dbc_array_list = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(this.TABLE_NAME2, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            dbc_array_list.add(new database_class(c.getFloat(c.getColumnIndex(this.COST)), c.getString(c.getColumnIndex(this.ITEM)), c.getString(c.getColumnIndex(this.CATEGORY)), c.getInt(c.getColumnIndex(this.DAY)), c.getInt(c.getColumnIndex(this.MONTH)), c.getInt(c.getColumnIndex(this.YEAR)), c.getInt(c.getColumnIndex(this.COLUMN_ID))));
            c.moveToNext();
        }
        db.close();
        return dbc_array_list;
    }

    public ArrayList<String> return_categorylist_from_record_database() {
        ArrayList<String> x = new ArrayList();
        Cursor c = getWritableDatabase().query(this.TABLE_NAME2, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            x.add(c.getString(c.getColumnIndex(this.CATEGORY)));
            c.moveToNext();
        }
        int b = 0;
        ArrayList<String> y = new ArrayList();
        y.clear();
        for (int a = 0; a < x.size(); a++) {
            String year1 = (String) x.get(a);
            for (int d = 0; d < y.size(); d++) {
                if (year1.equals((String) y.get(d))) {
                    b++;
                }
            }
            if (b == 0) {
                y.add(year1);
            } else {
                b = 0;
            }
        }
        return y;
    }

    public database_class get_data_according_to_id(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + this.TABLE_NAME2 + " WHERE ID=" + Integer.toString(id) + "", null);
        c.moveToFirst();
        database_class obj1 = new database_class(c.getFloat(c.getColumnIndex(this.COST)), c.getString(c.getColumnIndex(this.ITEM)), c.getString(c.getColumnIndex(this.CATEGORY)), c.getInt(c.getColumnIndex(this.DAY)), c.getInt(c.getColumnIndex(this.MONTH)), c.getInt(c.getColumnIndex(this.YEAR)), c.getInt(c.getColumnIndex(this.COLUMN_ID)));
        db.close();
        return obj1;
    }

    public void delete_record(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + this.TABLE_NAME2 + " where " + this.COLUMN_ID + "=" + Integer.toString(id));
        db.close();
    }
}
