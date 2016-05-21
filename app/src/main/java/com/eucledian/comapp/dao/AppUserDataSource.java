package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

@EBean
public class AppUserDataSource extends DataSource{

    public static final String CREATE_TABLE = "CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name, last_names, mail)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String COLUMN_NAME= "name";
    private static final String COLUMN_LAST_NAMES = "last_names";
    private static final String COLUMN_MAIL = "mail";
    private static final String TABLE_NAME = "users";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_LAST_NAMES,
            COLUMN_MAIL
    };

    public AppUser getAppUser(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, AppUser.class);
    }

    public long insertUser(AppUser u){
        return insertUser(u.getName(), u.getLastNames(), u.getMail());
    }

    public long insertUser(String name, String last_names, String mail) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LAST_NAMES, last_names);
        values.put(COLUMN_MAIL, mail);
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteUsers() {
        return getDb().delete(TABLE_NAME, null, null);
    }

    public AppUser getUser() {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, "0,1");
        AppUser el = null;
        if(c.moveToFirst())
            el = cursorToElement(c);
        c.close();
        return el;
    }

    public AppUser cursorToElement(Cursor c) {
        AppUser el = new AppUser ();
        el.setId(c.getLong(0));
        el.setName(c.getString(1));
        el.setLastNames(c.getString(2));
        el.setMail(c.getString(3));
        return el;
    }
}