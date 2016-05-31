package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.SurveyFieldOption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */
@EBean
public class SurveyFieldOptionDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE survey_field_options(id PRIMARY KEY, survey_field_id, option_value)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS survey_field_options";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURVEY_FIELD_ID = "survey_field_id";
    private static final String COLUMN_OPTION_VALUE = "option_value";
    private static final String TABLE_NAME = "survey_field_options";

    public SurveyFieldOptionDataSource(){}

    public SurveyFieldOption getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, SurveyFieldOption.class);
    }

    public long insertElement(SurveyFieldOption element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_NAME, element.getName());
        values.put(COLUMN_SURVEY_FIELD_ID, element.getSurveyFieldId());
        values.put(COLUMN_OPTION_VALUE, element.getOptionValue());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public SurveyFieldOption cursorToElement(Cursor c){
        SurveyFieldOption el = new SurveyFieldOption();
        int i = 0;
        el.setId(c.getLong(i));
        el.setName(c.getString(++i));
        el.setSurveyFieldId(c.getLong(++i));
        el.setOptionValue(c.getString(++i));
        return el;
    }

}
