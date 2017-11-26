package com.chub.signinassistant.util;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chub.signinassistant.bean.LoginEntity;

import java.util.ArrayList;
import java.util.List;

import static com.chub.signinassistant.util.Config.APP_DEBUG;
import static com.chub.signinassistant.util.SQLiteHelp.ID;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_ID;

/**
 * Description：数据库工具
 * Created by Chub on 2017/11/26.
 */
public class UserHelp {

    private static final String TAG = UserHelp.class.getSimpleName();

    /**
     * The Db.
     */
    private SQLiteDatabase db;

    /**
     * The constant clz.
     */
    private static UserHelp clz;

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    public static UserHelp getInstance(Context ctx) {
        if (clz == null) {
            clz = new UserHelp(ctx);
        }
        return clz;
    }

    /**
     * Instantiates a new User help.
     *
     * @param ctx the ctx
     */
    private UserHelp(Context ctx) {
        db = SQLiteHelp.init(ctx).getWritableDatabase();
    }

    /**
     * Insert.
     *
     * @param data the data
     */
    public void insert(LoginEntity data) {
        LoginEntity loginEntity = findByUserId(data.getUser_id());
        if (loginEntity == null) {
            long insert = db.insert(SQLiteHelp.UserTable.KEY_TABLE_NAME, SQLiteHelp.ID, data.buildValues());
            if (insert == -1 && APP_DEBUG) {
                Log.d(TAG, "insert data to table error!");
            }
        } else {
            update(loginEntity.getUser_id(), data);
        }
    }

    /**
     * Update.
     *
     * @param userId the user id
     * @param data   the data
     */
    public void update(String userId, LoginEntity data) {
        int result = db.update(SQLiteHelp.UserTable.KEY_TABLE_NAME, data.buildValues(), KEY_USER_ID + " = ?", new String[]{userId});
        if (result == -1 && APP_DEBUG) {
            Log.e(TAG, "update by id error!");
        }
    }

    /**
     * Select login entity.
     *
     * @param userId the user id
     * @return the login entity
     */
    public LoginEntity findByUserId(String userId) {
        LoginEntity loginEntity = null;
        Cursor cursor = db.query(SQLiteHelp.UserTable.KEY_TABLE_NAME, null, KEY_USER_ID + " = ?", new String[]{userId}, null, null, null);
        if (cursor != null && cursor.moveToNext()) {
            loginEntity = new LoginEntity(cursor);
            cursor.close();
        }
        return loginEntity;
    }

    /**
     * Delete by id.
     *
     * @param userId the user id
     */
    public void deleteById(String userId) {
        int result = db.delete(SQLiteHelp.UserTable.KEY_TABLE_NAME, KEY_USER_ID + " = ?", new String[]{userId});
        if (result == -1 && APP_DEBUG) {
            Log.e(TAG, "delete by id error!");
        }
    }

    /**
     * Delete all.
     */
    public void deleteAll() {
        db.delete(SQLiteHelp.UserTable.KEY_TABLE_NAME, null, null);
    }

    /**
     * Select all list.
     *
     * @return the list
     */
    public List<LoginEntity> findAll() {
        List<LoginEntity> datas = null;
        Cursor cursor = db.query(SQLiteHelp.UserTable.KEY_TABLE_NAME, null, null, null, null, null, ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                if (datas == null) datas = new ArrayList<>();
                datas.add(new LoginEntity(cursor));
            }
            cursor.close();
        }
        return datas;
    }


}
