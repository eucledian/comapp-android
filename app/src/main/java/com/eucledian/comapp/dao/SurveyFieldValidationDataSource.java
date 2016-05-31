package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.SurveyFieldValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */
@EBean
public class SurveyFieldValidationDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE survey_field_validations(id PRIMARY KEY, survey_field_id, identity, validation_args)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS survey_field_validations";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SURVEY_FIELD_ID = "survey_field_id";
    private static final String COLUMN_IDENTITY = "identity";
    private static final String COLUMN_VALIDATION_ARGS = "validation_args";
    private static final String TABLE_NAME = "survey_field_validations";

    public SurveyFieldValidationDataSource(){}

    public SurveyFieldValidation getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, SurveyFieldValidation.class);
    }

    public long insertElement(SurveyFieldValidation element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_SURVEY_FIELD_ID, element.getSurveyFieldId());
        values.put(COLUMN_IDENTITY, element.getIdentity());
        values.put(COLUMN_VALIDATION_ARGS, element.getValidationArgs());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public SurveyFieldValidation cursorToElement(Cursor c){
        SurveyFieldValidation el = new SurveyFieldValidation();
        int i = 0;
        el.setId(c.getLong(i));
        el.setSurveyFieldId(c.getLong(++i));
        el.setIdentity(c.getInt(++i));
        el.setValidationArgs(c.getString(++i));
        return el;
    }

}
