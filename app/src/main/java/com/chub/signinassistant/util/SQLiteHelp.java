package com.chub.signinassistant.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.chub.signinassistant.util.SQLiteHelp.SignTable.KEY_SIGN_ID;
import static com.chub.signinassistant.util.SQLiteHelp.SignTable.KEY_SIGN_TIME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_LAST_SIGN_TIME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_NICK_NAME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_EMAIL;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_FLAG;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_ICON;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_ID;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_NAME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_SEX;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_TEL;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_US_LOG_TIME;

/**
 * Description：数据库
 * Created by Chub on 2017/11/26.
 */

public class SQLiteHelp extends SQLiteOpenHelper {

    static SQLiteHelp init(Context ctx) {
        return new SQLiteHelp(ctx, DB_NAME, null, 1);
    }

    private SQLiteHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SIGN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static final String ID = "id";
    private static final String DB_NAME = "assistant.db";

    public interface UserTable {
        String KEY_TABLE_NAME = "user_table";
        String KEY_USER_ID = "uid";
        String KEY_USER_NAME = "uname";
        String KEY_NICK_NAME = "nickname";
        String KEY_USER_SEX = "sex";
        String KEY_USER_TEL = "tel";
        String KEY_USER_EMAIL = "email";
        String KEY_USER_ICON = "icon";
        String KEY_USER_FLAG = "flag";
        String KEY_US_LOG_TIME = "log_time";
        String KEY_LAST_SIGN_TIME = "last_sign_time";
    }

    public interface SignTable {
        String KEY_TABLE_NAME = "sign_table";
        String KEY_SIGN_ID = "sign_id";
        String KEY_SIGN_TIME = "sign_time";
    }

    private static final String CREATE_USER_TABLE = "create table if not exists " + UserTable.KEY_TABLE_NAME + "(" +
            ID + " integer primary key," +
            KEY_USER_ID + " varchar," +
            KEY_USER_NAME + " varchar," +
            KEY_NICK_NAME + " varchar," +
            KEY_USER_SEX + " varchar(1)," +
            KEY_USER_TEL + " varchar," +
            KEY_USER_EMAIL + " varchar," +
            KEY_USER_ICON + " varchar," +
            KEY_USER_FLAG + " varchar(1)," +
            KEY_US_LOG_TIME + " varchar," +
            KEY_LAST_SIGN_TIME + " varchar" +
            ")";

    private static final String CREATE_SIGN_TABLE = "create table if not exists " + SignTable.KEY_TABLE_NAME + "(" +
            ID + " integer primary key," +
            KEY_SIGN_ID + " varchar," +
            KEY_SIGN_TIME + " varchar" +
            ")";

}
