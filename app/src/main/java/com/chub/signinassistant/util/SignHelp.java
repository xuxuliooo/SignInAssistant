package com.chub.signinassistant.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.chub.signinassistant.bean.SignBean;
import com.chub.signinassistant.bean.SignInLogBean;

import java.util.ArrayList;
import java.util.List;

import static com.chub.signinassistant.util.Config.APP_DEBUG;

/**
 * Description：
 * Created by Chub on 2017/11/26.
 */
public class SignHelp {

    private static final String TAG = SignHelp.class.getSimpleName();
    /**
     * The Db.
     */
    private SQLiteDatabase db;

    /**
     * Instantiates a new Sign help.
     *
     * @param ctx the ctx
     */
    private SignHelp(Context ctx) {
        db = SQLiteHelp.init(ctx).getWritableDatabase();
    }

    /**
     * The constant clz.
     */
    private static SignHelp clz;

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static SignHelp getInstance(Context ctx) {
        if (clz == null) {
            clz = new SignHelp(ctx);
        }
        return clz;
    }

    /**
     * Insert.
     *
     * @param bean the bean
     */
    public void insert(SignBean bean) {
        db.beginTransaction();
        try {
            long result = db.insert(SQLiteHelp.SignTable.KEY_TABLE_NAME, SQLiteHelp.ID, bean.buildValues());
            if (result == -1 && APP_DEBUG) {
                Log.d(TAG, "insert data to table error!");
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            if (APP_DEBUG)
                Log.e(TAG, "e:" + e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * Find all by id list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<SignBean> findAllById(String userId) {
        List<SignBean> data = null;
        Cursor cursor = db.query(SQLiteHelp.SignTable.KEY_TABLE_NAME, null,
                TextUtils.isEmpty(userId) ? null : SQLiteHelp.SignTable.KEY_SIGN_ID + " = ?",
                TextUtils.isEmpty(userId) ? null : new String[]{userId},
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (data == null) data = new ArrayList<>();
                data.add(new SignBean(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return data;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<SignBean> findAll() {
        return findAllById(null);
    }

    /**
     * Find all log list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<SignInLogBean> findAllLogById(String userId) {
        List<SignInLogBean> list = null;
        String sql = "select st.*,ut." + SQLiteHelp.UserTable.KEY_NICK_NAME +
                ",ut." +
                SQLiteHelp.UserTable.KEY_USER_TEL +
                ",ut." +
                SQLiteHelp.UserTable.KEY_USER_ICON +
                " from " +
                SQLiteHelp.SignTable.KEY_TABLE_NAME +
                " as st left join " +
                SQLiteHelp.UserTable.KEY_TABLE_NAME +
                " as ut on ut." +
                SQLiteHelp.UserTable.KEY_USER_ID +
                " = st." +
                SQLiteHelp.SignTable.KEY_SIGN_ID
                + ((TextUtils.isEmpty(userId) ? "" : " where st."
                + SQLiteHelp.SignTable.KEY_SIGN_ID
                + " = " + userId)
                + " order by st." + SQLiteHelp.SignTable.KEY_SIGN_TIME
                + "  desc");
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                if (list == null) list = new ArrayList<>();
                list.add(new SignInLogBean(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    /**
     * Find all log list.
     *
     * @return the list
     */
    public List<SignInLogBean> findAllLog() {
        return findAllLogById(null);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(int id) {
        db.beginTransaction();
        try {
            int result = db.delete(SQLiteHelp.SignTable.KEY_TABLE_NAME, SQLiteHelp.ID + " = ?", new String[]{String.valueOf(id)});
            if (result == -1 && APP_DEBUG) {
                Log.e(TAG, "delete data by id error!");
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            if (APP_DEBUG)
                Log.e(TAG, "e:" + e);
        } finally {
            db.endTransaction();
        }
    }
}
