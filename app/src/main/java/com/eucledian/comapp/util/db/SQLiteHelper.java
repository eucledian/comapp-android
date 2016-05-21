package com.eucledian.comapp.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eucledian.comapp.Config;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Transactional;

@EBean(scope = EBean.Scope.Singleton)
public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context c){
        super(c, Config.Database.NAME, null, Config.Database.VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        execStmtArray(db, Config.Database.SQL_CREATE_STMT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        execStmtArray(db, Config.Database.SQL_DELETE_STMT);
        onCreate(db);
    }

    @Transactional
    public void execStmtArray(SQLiteDatabase db, String stmt[]){
        for (String aStmt : stmt) {
            db.execSQL(aStmt);
        }
    }

}
