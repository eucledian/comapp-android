package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.Zone;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

/**
 * Created by gustavo on 5/31/16.
 */
@EBean
public class ZoneDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE zones(id PRIMARY KEY, name, lat, lng)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS zones";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String TABLE_NAME = "zones";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_LAT,
            COLUMN_LNG
    };

    public ZoneDataSource(){}

    public Zone getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, Zone.class);
    }

    public Zone getElementById(long id){
        Zone element = null;
        Cursor c = getDb().query(TABLE_NAME, columns, "id = " + id, null, null, null, null, null);
        if (c.moveToNext()){
            element = cursorToElement(c);
        }
        return element;
    }

    public long insertElement(Zone element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_NAME, element.getName());
        values.put(COLUMN_LAT, element.getLat());
        values.put(COLUMN_LNG, element.getLat());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public Zone cursorToElement(Cursor c){
        Zone el = new Zone();
        int i = 0;
        el.setId(c.getLong(i));
        el.setName(c.getString(++i));
        el.setLat(c.getFloat(++i));
        el.setLng(c.getFloat(++i));
        return el;
    }

}
