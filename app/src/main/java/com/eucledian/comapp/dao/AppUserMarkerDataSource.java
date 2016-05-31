package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUserMarker;

import org.androidannotations.annotations.EBean;

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

    public AppUserMarkerDataSource(){}

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

}
