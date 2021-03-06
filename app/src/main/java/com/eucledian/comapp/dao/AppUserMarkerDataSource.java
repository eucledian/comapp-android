package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.eucledian.comapp.model.AppUserMarker;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gustavo on 5/31/16.
 */

@EBean
public class AppUserMarkerDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE app_user_markers(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, zone_id, marker_id, lat, lng)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS app_user_markers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ZONE_ID = "zone_id";
    private static final String COLUMN_MARKER_ID = "marker_id";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String TABLE_NAME = "app_user_markers";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_ZONE_ID,
            COLUMN_MARKER_ID,
            COLUMN_LAT,
            COLUMN_LNG
    };


    public AppUserMarkerDataSource(){}

    public ArrayList<AppUserMarker> getElements() {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, null);
        ArrayList<AppUserMarker> results = new ArrayList<AppUserMarker>();
        AppUserMarker el;
        while (c.moveToNext()){
            el = cursorToElement(c);
            results.add(el);
        }
        c.close();
        return results;
    }

    public void setParams(Map<String, String> params, ArrayList<AppUserMarker> elements){
        Iterator<AppUserMarker> it = elements.iterator();
        while (it.hasNext()){
            AppUserMarker element = it.next();
            String fieldNamespace = "app_user_marker[" + element.getId() + "]";

            params.put(fieldNamespace + "[zone_id]", String.valueOf(element.getZoneId()));
            params.put(fieldNamespace + "[marker_id]", String.valueOf(element.getMarkerId()));
            params.put(fieldNamespace + "[lat]", String.valueOf(element.getLat()));
            params.put(fieldNamespace + "[lng]", String.valueOf(element.getLng()));
        }
    }

    public long insertElement(AppUserMarker element){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ZONE_ID, element.getZoneId());
        values.put(COLUMN_MARKER_ID, element.getMarkerId());
        values.put(COLUMN_LAT, element.getLat());
        values.put(COLUMN_LNG, element.getLng());
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteAllElements(){
        return getDb().delete(TABLE_NAME, null, null);
    }

    public AppUserMarker cursorToElement(Cursor c){
        AppUserMarker el = new AppUserMarker();
        int i = 0;
        el.setId(c.getLong(i));
        el.setZoneId(c.getLong(++i));
        el.setMarkerId(c.getLong(++i));
        el.setLat(c.getDouble(++i));
        el.setLng(c.getDouble(++i));
        return el;
    }


    public Bundle toArgs(AppUserMarker element){
        Bundle b = new Bundle();
        b.putLong(COLUMN_ID, element.getId());
        b.putLong(COLUMN_MARKER_ID, element.getMarkerId());
        b.putLong(COLUMN_ZONE_ID, element.getZoneId());
        b.putDouble(COLUMN_LAT, element.getLat());
        b.putDouble(COLUMN_LNG, element.getLng());
        return b;
    }

    public AppUserMarker fromArgs(Bundle args){
        AppUserMarker element = new AppUserMarker();
        element.setId(args.getLong(COLUMN_ID));
        element.setMarkerId(args.getLong(COLUMN_MARKER_ID));
        element.setZoneId(args.getLong(COLUMN_ZONE_ID));
        element.setLat(args.getDouble(COLUMN_LAT));
        element.setLng(args.getDouble(COLUMN_LNG));
        return element;
    }

}
