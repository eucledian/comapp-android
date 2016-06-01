package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUserSurvey;
import com.eucledian.comapp.model.AppUserSurveyResponse;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class AppUserSurveyDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE app_user_surveys(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, survey_id)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS app_user_surveys";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURVEY_ID = "survey_id";
    private static final String TABLE_NAME = "app_user_surveys";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_SURVEY_ID
    };


    public AppUserSurveyDataSource(){}

    public ArrayList<AppUserSurvey> getElements(AppUserSurveyResponseDataSource appUserSurveyResponseDataSource) {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, null);
        ArrayList<AppUserSurvey> results = new ArrayList<AppUserSurvey>();
        AppUserSurvey el = null;
        while (c.moveToNext()){
            el = cursorToElement(c);
            el.setResponses(appUserSurveyResponseDataSource.getElementsByAppUserSurvey(el.getId()));
            results.add(el);
        }
        c.close();
        return results;
    }

    public long insertElement(AppUserSurvey element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_ID, element.getSurveyId());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public int deleteSurvey(long id){
        return getDb().delete(TABLE_NAME, "id=" + id, null);
    }

    public AppUserSurvey cursorToElement(Cursor c){
        AppUserSurvey el = new AppUserSurvey();
        int i = 0;
        el.setId(c.getLong(i));
        el.setSurveyId(c.getLong(++i));
        return el;
    }

}
