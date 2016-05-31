package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUserSurveyResponse;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class AppUserSurveyResponseDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE app_user_survey_responses(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, survey_field_id, response)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS app_user_survey_responses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURVEY_FIELD_ID = "survey_field_id";
    private static final String COLUMN_RESPONSE = "response";
    private static final String TABLE_NAME = "app_user_survey_responses";

    public AppUserSurveyResponseDataSource(){}

    public long insertElement(AppUserSurveyResponse element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_FIELD_ID, element.getSurveyFieldId());
        values.put(COLUMN_RESPONSE, element.getResponse());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public AppUserSurveyResponse cursorToElement(Cursor c){
        AppUserSurveyResponse el = new AppUserSurveyResponse();
        int i = 0;
        el.setId(c.getLong(i));
        el.setSurveyFieldId(c.getLong(++i));
        el.setResponse(c.getString(++i));
        return el;
    }

}
