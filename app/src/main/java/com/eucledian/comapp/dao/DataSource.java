package com.eucledian.comapp.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.eucledian.comapp.util.db.SQLiteHelper;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@EBean
public class DataSource {

    public static final String COLUMN_ID = "id";

    private SQLiteDatabase db;

    @Bean
    protected SQLiteHelper dbHelper;

    @RootContext
    protected Context context;

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public static String dateToString(Date t){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.format(t);
    }

    public static Date stringToDate(String s) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.parse(s);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public SQLiteHelper getDbHelper() {
        return dbHelper;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public void setDbHelper(SQLiteHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}