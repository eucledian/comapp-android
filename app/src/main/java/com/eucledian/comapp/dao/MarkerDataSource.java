package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.eucledian.comapp.model.Marker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class MarkerDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE markers(id PRIMARY KEY, name, icon_url)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS markers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ICON_URL = "icon_url";
    private static final String TABLE_NAME = "markers";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_ICON_URL
    };

    public MarkerDataSource(){}

    public Marker getElement(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, Marker.class);
    }

    public long insertElement(Marker element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, element.getId());
        values.put(COLUMN_NAME, element.getName());
        values.put(COLUMN_ICON_URL, element.getIconUrl());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public ArrayList<Marker> getElements() {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, null);
        ArrayList<Marker> results = new ArrayList<Marker>();
        Marker el = null;
        while (c.moveToNext()){
            el = cursorToElement(c);
            results.add(el);
        }
        c.close();
        return results;
    }


    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public Marker cursorToElement(Cursor c){
        Marker el = new Marker();
        int i = 0;
        el.setId(c.getLong(i));
        el.setName(c.getString(++i));
        el.setIconUrl(c.getString(++i));
        return el;
    }

    public Bundle toArgs(Marker element){
        Bundle b = new Bundle();
        b.putLong(COLUMN_ID, element.getId());
        b.putString(COLUMN_NAME, element.getName());
        b.putString(COLUMN_ICON_URL, element.getIconUrl());
        return b;
    }

    public Marker fromArgs(Bundle args){
        Marker element = new Marker();
        element.setId(args.getLong(COLUMN_ID));
        element.setName(args.getString(COLUMN_NAME));
        element.setIconUrl(args.getString(COLUMN_ICON_URL));
        return element;
    }

}
