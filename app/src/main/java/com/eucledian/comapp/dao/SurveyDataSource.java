package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.eucledian.comapp.model.Survey;
import com.eucledian.comapp.model.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class SurveyDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE surveys(id PRIMARY KEY, zone_id, is_active, name, description)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS surveys";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ZONE_ID = "zone_id";
    private static final String COLUMN_IS_ACTIVE = "is_active";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "name";
    private static final String TABLE_NAME = "surveys";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_ZONE_ID,
            COLUMN_IS_ACTIVE,
            COLUMN_NAME,
            COLUMN_DESCRIPTION
    };

    public SurveyDataSource(){}

    public Survey getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, Survey.class);
    }

    public long insertElement(Survey element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_ZONE_ID, element.getZoneId());
        values.put(COLUMN_IS_ACTIVE, element.isActive());
        values.put(COLUMN_NAME, element.getName());
        values.put(COLUMN_DESCRIPTION, element.getDescription());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public ArrayList<Survey> getElements(ZoneDataSource zoneDataSource) {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, null);
        ArrayList<Survey> results = new ArrayList<Survey>();
        Survey el = null;
        while (c.moveToNext()){
            el = cursorToElement(c);
            Zone zone = zoneDataSource.getElementById(el.getZoneId());
            el.setZone(zone);
            results.add(el);
        }
        c.close();
        return results;
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public Survey cursorToElement(Cursor c){
        Survey el = new Survey();
        int i = 0;
        el.setId(c.getLong(i));
        el.setZoneId(c.getLong(++i));
        el.setIsActive(c.getInt(++i) > 0);
        el.setName(c.getString(++i));
        el.setDescription(c.getString(++i));
        return el;
    }

    public Bundle toArgs(Survey element){
        Bundle bundle = new Bundle();

        bundle.putLong(COLUMN_ID, element.getId());
        bundle.putLong(COLUMN_ZONE_ID, element.getZoneId());
        bundle.putBoolean(COLUMN_IS_ACTIVE, element.isActive());
        bundle.putString(COLUMN_NAME, element.getName());
        bundle.putString(COLUMN_DESCRIPTION, element.getDescription());

        return bundle;
    }

    public Survey fromBundle(Bundle args){
        Survey element = new Survey();

        element.setId(args.getLong(COLUMN_ID));
        element.setZoneId(args.getLong(COLUMN_ZONE_ID));
        element.setIsActive(args.getBoolean(COLUMN_IS_ACTIVE));
        element.setName(args.getString(COLUMN_NAME));
        element.setDescription(args.getString(COLUMN_DESCRIPTION));

        return element;
    }

}
