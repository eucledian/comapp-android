package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUserSurveyResponse;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class AppUserSurveyResponseDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE app_user_survey_responses(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, app_user_survey_id, survey_field_id, response)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS app_user_survey_responses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_APP_USER_SURVEY_ID = "app_user_survey_id";
    private static final String COLUMN_SURVEY_FIELD_ID = "survey_field_id";
    private static final String COLUMN_RESPONSE = "response";
    private static final String TABLE_NAME = "app_user_survey_responses";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_APP_USER_SURVEY_ID,
            COLUMN_SURVEY_FIELD_ID,
            COLUMN_RESPONSE
    };

    public AppUserSurveyResponseDataSource(){}

    public void setParams(Map<String, String> params, ArrayList<AppUserSurveyResponse> elements){
        Iterator<AppUserSurveyResponse> it = elements.iterator();
        while (it.hasNext()){
            AppUserSurveyResponse element = it.next();
            String fieldNamespace = "app_user_survey_response[" + element.getId() + "]";
            String response = element.getResponse();
            if(response == null) response = "";
            params.put(fieldNamespace + "[survey_field_id]", String.valueOf(element.getSurveyFieldId()));
            params.put(fieldNamespace + "[response]", response);
        }
    }

    public ArrayList<AppUserSurveyResponse> getElementsByAppUserSurvey(long appUserSurveyId) {
        Cursor c = getDb().query(TABLE_NAME, columns, "app_user_survey_id=" + appUserSurveyId, null, null, null, null, null);
        ArrayList<AppUserSurveyResponse> results = new ArrayList<AppUserSurveyResponse>();
        AppUserSurveyResponse el = null;
        while (c.moveToNext()){
            el = cursorToElement(c);
            results.add(el);
        }
        c.close();
        return results;
    }

    public long insertElement(AppUserSurveyResponse element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_APP_USER_SURVEY_ID, element.getAppUserSurveyId());
        values.put(COLUMN_SURVEY_FIELD_ID, element.getSurveyFieldId());
        values.put(COLUMN_RESPONSE, element.getResponse());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public int deleteAppUserSurvey(long appUserSurveyId){
        return getDb().delete(TABLE_NAME, "app_user_survey_id=" + appUserSurveyId, null);
    }

    public AppUserSurveyResponse cursorToElement(Cursor c){
        AppUserSurveyResponse el = new AppUserSurveyResponse();
        int i = 0;
        el.setId(c.getLong(i));
        el.setAppUserSurveyId(c.getLong(++i));
        el.setSurveyFieldId(c.getLong(++i));
        el.setResponse(c.getString(++i));
        return el;
    }

}
