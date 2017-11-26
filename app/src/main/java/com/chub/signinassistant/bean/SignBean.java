package com.chub.signinassistant.bean;

import android.content.ContentValues;
import android.database.Cursor;

import com.chub.signinassistant.util.SQLiteHelp;

/**
 * Description：
 * Created by Chub on 2017/11/26.
 */
public class SignBean {

    /**
     * The User id.
     */
    private String userId;
    /**
     * The Sign time.
     */
    private String signTime;

    /**
     * Instantiates a new Sign bean.
     */
    public SignBean() {
    }

    /**
     * Instantiates a new Sign bean.
     *
     * @param cursor the cursor
     */
    public SignBean(Cursor cursor) {
        userId = cursor.getString(cursor.getColumnIndex(SQLiteHelp.SignTable.KEY_SIGN_ID));
        signTime = cursor.getString(cursor.getColumnIndex(SQLiteHelp.SignTable.KEY_SIGN_TIME));
    }

    /**
     * Instantiates a new Sign bean.
     *
     * @param userId the user id
     * @param time   the time
     */
    public SignBean(String userId, String time) {
        this.userId = userId;
        this.signTime = time;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets sign time.
     *
     * @return the sign time
     */
    public String getSignTime() {
        return signTime;
    }

    /**
     * Sets sign time.
     *
     * @param signTime the sign time
     */
    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + (userId == null ? null : "\"" + userId + "\"") +
                ",\"signTime\":" + (signTime == null ? null : "\"" + signTime + "\"") +
                '}';
    }

    /**
     * Build values content values.
     *
     * @return the content values
     */
    public ContentValues buildValues() {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelp.SignTable.KEY_SIGN_ID, userId);
        values.put(SQLiteHelp.SignTable.KEY_SIGN_TIME, signTime);
        return values;
    }
}
