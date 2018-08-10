package com.dvl.tablayout_materialdesign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Administrator on 7/19/2016.
 */
public class QuanLyCongViec {
    Context context;
    SQLiteDatabase db;
    private final static int DB_VERSION = 1;
    private final static String DB_PATH = "/data/data/com.dvl.tablayout_materialdesign/databases/";
    private final static String DB_NAME = "quanlycongviec";

    QuanLyCongViec(Context context) {
        this.context = context;
    }

    public void khoitao_Database() {
        if (!check_Database()) {
            db = context.openOrCreateDatabase("quanlycongviec", Context.MODE_APPEND, null);
            String sql = "create table qlcv (_id integer primary key autoincrement,noidung text, thoigian text, important text)";
            db.execSQL(sql);
            db.setVersion(DB_VERSION);
            db.close();
        }
    }

    public boolean check_Database() {
        SQLiteDatabase check_db = null;
        try {
            String mypath = DB_PATH + DB_NAME;
            check_db = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
        }
        if (check_db != null) check_db.close();
        return check_db != null ? true : false;
    }

    public void themCongViec(CongViec c) {
        String noidung = c.noidung;
        String thoigian = c.thoigian;
        String important = c.important;
        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        String sql = "INSERT INTO qlcv (noidung, thoigian, important)" +
                "VALUES ('" + noidung + "', '" + thoigian + "', '" + important + "')";
        db.execSQL(sql);
        db.close();
    }


    public ArrayList<CongViec> xemdsCongViec() {
        ArrayList<CongViec> ds = new ArrayList<CongViec>();
        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.rawQuery("select * from qlcv", null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String noidung = c.getString(1);
                String thoigian = c.getString(2);
                String important = c.getString(3);
                CongViec cv = new CongViec(id, noidung, thoigian, important);
                ds.add(cv);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return ds;
    }

    public void xoaCongViec(int id_db) {
        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("qlcv", "_id=?", new String[]{id_db + ""});
        db.close();
    }

    public void suaCongViec(CongViec c) {
        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("noidung", c.noidung);
        values.put("thoigian", c.thoigian);
        values.put("important", c.important);
        db.update("qlcv", values, "_id=?", new String[]{c.id + ""});
        db.close();
    }

    public ArrayList<CongViec> sxCongViec() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd/MM/yyyy");
        Date date = null;
        ArrayList<CongViec> data = xemdsCongViec();
        ArrayList<Date> dataDate = new ArrayList<Date>();
        //tach lay ngay/thang/nam => sap xep tang dan
        for (int i = 0; i < data.size(); i++) {
            String chuoi = data.get(i).thoigian;
            int vitridau = chuoi.indexOf(", ");
            chuoi = chuoi.substring(vitridau + 2);
            try {
                date = simpleDateFormat.parse(chuoi);
                dataDate.add(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(dataDate);//sx tang dan
        //chuyen Date ve chuoi de so sanh,chuyen CongViec da sx qua ArrayList moi
        ArrayList<CongViec> dataNew = new ArrayList<CongViec>();
        for (int i = 0; i < dataDate.size(); i++) {
            String chuoiDate = simpleDateFormat.format(dataDate.get(i));
            for (int j = 0; j < data.size(); j++) {
                String chuoi = data.get(j).thoigian;
                Date temp = null;
                int vitridau = chuoi.indexOf(", ");
                chuoi = chuoi.substring(vitridau + 2);
                try {
                    temp = simpleDateFormat.parse(chuoi);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String chuoi2 = simpleDateFormat.format(temp);
                if (chuoiDate.equalsIgnoreCase(chuoi2)) {
                    dataNew.add(data.get(j));
                    data.remove(j);
                    break;
                }

            }
        }
        return dataNew;
    }
}