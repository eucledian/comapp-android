package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUserSurvey;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class AppUserSurveyDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE app_user_surveys(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, survey_id)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS app_user_surveys";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURVEY_ID = "id";
    private static final String TABLE_NAME = "app_user_surveys";

    public AppUserSurveyDataSource(){}

    public long insertElement(AppUserSurvey element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_ID, element.getSurveyId());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public AppUserSurvey cursorToElement(Cursor c){
        AppUserSurvey el = new AppUserSurvey();
        int i = 0;
        el.setId(c.getLong(i));
        el.setSurveyId(c.getLong(++i));
        return el;
    }

}
