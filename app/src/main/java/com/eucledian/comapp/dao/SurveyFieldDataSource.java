package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.SurveyField;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */
@EBean
public class SurveyFieldDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE survey_fields(id PRIMARY KEY, survey_id, position, data_type, identity, name)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS survey_fields";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURVEY_ID = "survey_id";
    private static final String COLUMN_POSITION = "position";
    private static final String COLUMN_DATA_TYPE = "data_type";
    private static final String COLUMN_IDENTITY = "identity";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_NAME = "survey_fields";

    public SurveyFieldDataSource(){}

    public SurveyField getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, SurveyField.class);
    }

    public long insertElement(SurveyField element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_SURVEY_ID, element.getSurveyId());
        values.put(COLUMN_POSITION, element.getPosition());
        values.put(COLUMN_DATA_TYPE, element.getDataType());
        values.put(COLUMN_IDENTITY, element.getIdentity());
        values.put(COLUMN_NAME, element.getName());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public SurveyField cursorToElement(Cursor c){
        SurveyField el = new SurveyField();
        int i = 0;
        el.setId(c.getLong(i));
        el.setSurveyId(c.getLong(++i));
        el.setPosition(c.getInt(++i));
        el.setDataType(c.getInt(++i));
        el.setIdentity(c.getInt(++i));
        el.setName(c.getString(++i));
        return el;
    }

}
