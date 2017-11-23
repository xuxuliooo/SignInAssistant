package com.chub.signinassistant.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import static com.chub.signinassistant.util.Config.SP_FILE_NAME;

/**
 * Created by Chub on 2017/11/23.
 * SharedPreferences工具类
 */
public class SPUtils {

    private SharedPreferences sp;

    private static SPUtils spUtils;

    /**
     * Instantiates a new Sp utils.
     *
     * @param ctx the ctx
     */
    private SPUtils(Context ctx) {
        sp = ctx.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets instance.
     *
     * @param ctx the ctx
     * @return the instance
     */
    private static SPUtils getInstance(Context ctx) {
        if (spUtils == null) {
            spUtils = new SPUtils(ctx);
        }
        return spUtils;
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, String value) {
        getInstance(ctx).sp.edit().putString(key, value).apply();
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, float value) {
        getInstance(ctx).sp.edit().putFloat(key, value).apply();
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, int value) {
        getInstance(ctx).sp.edit().putInt(key, value).apply();
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, long value) {
        getInstance(ctx).sp.edit().putLong(key, value).apply();
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, boolean value) {
        getInstance(ctx).sp.edit().putBoolean(key, value).apply();
    }

    /**
     * Save.
     *
     * @param ctx   the ctx
     * @param key   the key
     * @param value the value
     */
    public static void save(Context ctx, String key, Set<String> value) {
        getInstance(ctx).sp.edit().putStringSet(key, value).apply();
    }

    /**
     * Gets string.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the string
     */
    public static String getString(Context ctx, String key) {
        return getInstance(ctx).sp.getString(key, null);
    }

    /**
     * Gets string.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the string
     */
    public static String getString(Context ctx, String key, String defValue) {
        return getInstance(ctx).sp.getString(key, defValue);
    }

    /**
     * Gets int.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the int
     */
    public static int getInt(Context ctx, String key) {
        return getInstance(ctx).sp.getInt(key, 0);
    }

    /**
     * Gets int.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the int
     */
    public static int getInt(Context ctx, String key, int defValue) {
        return getInstance(ctx).sp.getInt(key, defValue);
    }

    /**
     * Gets float.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the float
     */
    public static float getFloat(Context ctx, String key) {
        return getInstance(ctx).sp.getFloat(key, 0);
    }

    /**
     * Gets float.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the float
     */
    public static float getFloat(Context ctx, String key, float defValue) {
        return getInstance(ctx).sp.getFloat(key, defValue);
    }

    /**
     * Gets long.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the long
     */
    public static long getLong(Context ctx, String key) {
        return getInstance(ctx).sp.getLong(key, 0);
    }

    /**
     * Gets long.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the long
     */
    public static long getLong(Context ctx, String key, long defValue) {
        return getInstance(ctx).sp.getLong(key, defValue);
    }

    /**
     * Gets boolean.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the boolean
     */
    public static boolean getBoolean(Context ctx, String key) {
        return getInstance(ctx).sp.getBoolean(key, false);
    }

    /**
     * Gets boolean.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the boolean
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        return getInstance(ctx).sp.getBoolean(key, defValue);
    }

    /**
     * Gets string set.
     *
     * @param ctx the ctx
     * @param key the key
     * @return the string set
     */
    public static Set<String> getStringSet(Context ctx, String key) {
        return getInstance(ctx).sp.getStringSet(key, null);
    }

    /**
     * Gets string set.
     *
     * @param ctx      the ctx
     * @param key      the key
     * @param defValue the def value
     * @return the string set
     */
    public static Set<String> getStringSet(Context ctx, String key, Set<String> defValue) {
        return getInstance(ctx).sp.getStringSet(key, defValue);
    }

}
