package com.eucledian.comapp.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.eucledian.comapp.model.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.androidannotations.annotations.EBean;

@EBean
public class TokenDataSource extends DataSource {

    public static final String CREATE_TABLE = "CREATE TABLE tokens(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, app_user_id INTEGER, token, secret, identity)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS tokens";
    private static final String COLUMN_TOKEN = "token";
    private static final String COLUMN_SECRET = "secret";
    private static final String TABLE_NAME = "tokens";
    private String[] columns = {
            COLUMN_ID,
            COLUMN_TOKEN,
            COLUMN_SECRET
    };

    public TokenDataSource() {}

    public Token getToken(ObjectNode tree, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.treeToValue(tree, Token.class); // Jackson data-bind
    }

    public long insertToken(Token t) {
        return insertToken(t.getToken(), t.getSecret());
    }

    public long insertToken(String token, String secret) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, token);
        values.put(COLUMN_SECRET, secret);
        return getDb().insert(TABLE_NAME, null, values);
    }

    public int deleteTokens() {
        return getDb().delete(TABLE_NAME, null, null);
    }

    public Token getToken() {
        Cursor c = getDb().query(TABLE_NAME, columns, null, null, null, null, null, "0,1");
        Token el = null;
        if(c.moveToFirst())
            el = cursorToElement(c);
        c.close();
        return el;
    }

    public Token cursorToElement(Cursor c) {
        Token el = new Token();
        el.setId(c.getLong(0));
        el.setToken(c.getString(1));
        el.setSecret(c.getString(2));
        return el;
    }

}