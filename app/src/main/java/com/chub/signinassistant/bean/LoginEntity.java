package com.chub.signinassistant.bean;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.chub.signinassistant.util.DefaultUtil;
import com.chub.signinassistant.util.SQLiteHelp;

import static com.chub.signinassistant.util.Config.CHINA_TIME_STYLE;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_LAST_SIGN_TIME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_NICK_NAME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_EMAIL;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_FLAG;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_ICON;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_ID;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_NAME;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_USER_TEL;
import static com.chub.signinassistant.util.SQLiteHelp.UserTable.KEY_US_LOG_TIME;

/**
 * Description：登录返回信息
 * Created by Chub on 2017/11/25.
 */
public class LoginEntity {

    /**
     * user_id : 20170413015856624163
     * login_name : B79D051B8F6C0025059829DF6F019784
     * login_tel : 008617721125121
     * login_mail : null
     * userflag : 1
     * nname : 事过境迁。。。
     * usex : null
     * u_pic_url : http://q.qlogo.cn/qqapp/1104755296/B79D051B8F6C0025059829DF6F019784/100
     */
    private String user_id;
    private String login_name;
    private String login_tel;
    private String login_mail;
    private String userflag;
    private String nname;
    private String usex;
    private String u_pic_url;
    private String logTime;
    private String lastSignTime;

    /**
     * Instantiates a new Login entity.
     */
    public LoginEntity() {
    }

    /**
     * Instantiates a new Login entity.
     *
     * @param cursor the cursor
     */
    public LoginEntity(Cursor cursor) {
        login_mail = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL));
        user_id = cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
        login_name = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
        nname = cursor.getString(cursor.getColumnIndex(KEY_NICK_NAME));
        login_tel = cursor.getString(cursor.getColumnIndex(KEY_USER_TEL));
        u_pic_url = cursor.getString(cursor.getColumnIndex(KEY_USER_ICON));
        userflag = cursor.getString(cursor.getColumnIndex(KEY_USER_FLAG));
        logTime = cursor.getString(cursor.getColumnIndex(KEY_US_LOG_TIME));
        lastSignTime = cursor.getString(cursor.getColumnIndex(KEY_LAST_SIGN_TIME));
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Sets user id.
     *
     * @param user_id the user id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Gets login name.
     *
     * @return the login name
     */
    public String getLogin_name() {
        return login_name;
    }

    /**
     * Sets login name.
     *
     * @param login_name the login name
     */
    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    /**
     * Gets login tel.
     *
     * @return the login tel
     */
    public String getLogin_tel() {
        return login_tel;
    }

    /**
     * Sets login tel.
     *
     * @param login_tel the login tel
     */
    public void setLogin_tel(String login_tel) {
        this.login_tel = login_tel;
    }

    /**
     * Gets login mail.
     *
     * @return the login mail
     */
    public String getLogin_mail() {
        return login_mail;
    }

    /**
     * Sets login mail.
     *
     * @param login_mail the login mail
     */
    public void setLogin_mail(String login_mail) {
        this.login_mail = login_mail;
    }

    /**
     * Gets userflag.
     *
     * @return the userflag
     */
    public String getUserflag() {
        return userflag;
    }

    /**
     * Sets userflag.
     *
     * @param userflag the userflag
     */
    public void setUserflag(String userflag) {
        this.userflag = userflag;
    }

    /**
     * Gets nname.
     *
     * @return the nname
     */
    public String getNname() {
        return nname;
    }

    /**
     * Sets nname.
     *
     * @param nname the nname
     */
    public void setNname(String nname) {
        this.nname = nname;
    }

    /**
     * Gets usex.
     *
     * @return the usex
     */
    public String getUsex() {
        return usex;
    }

    /**
     * Sets usex.
     *
     * @param usex the usex
     */
    public void setUsex(String usex) {
        this.usex = usex;
    }

    /**
     * Gets u pic url.
     *
     * @return the u pic url
     */
    public String getU_pic_url() {
        return u_pic_url;
    }

    /**
     * Sets u pic url.
     *
     * @param u_pic_url the u pic url
     */
    public void setU_pic_url(String u_pic_url) {
        this.u_pic_url = u_pic_url;
    }

    /**
     * Gets log time.
     *
     * @return the log time
     */
    public String getLogTime() {
        return DefaultUtil.dateToString(Long.valueOf(logTime), CHINA_TIME_STYLE);
    }

    /**
     * Sets log time.
     *
     * @param logTime the log time
     */
    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    /**
     * Gets last sign time.
     *
     * @return the last sign time
     */
    public String getLastSignTime() {
        return DefaultUtil.dateToString(Long.valueOf(lastSignTime), CHINA_TIME_STYLE);
    }

    /**
     * Sets last sign time.
     *
     * @param lastSignTime the last sign time
     */
    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    /**
     * Sign in is one day.
     *
     * @return boolean boolean
     */
    public boolean signInIsOneDay() {
        String signTime = DefaultUtil.dateToString(Long.valueOf(lastSignTime), "yyyyMMdd");
        String sysTime = DefaultUtil.dateToString(System.currentTimeMillis(), "yyyyMMdd");
        return TextUtils.equals(signTime, sysTime);
    }

    @Override
    public String toString() {
        return "{" +
                "\"user_id\":" + (user_id == null ? null : "\"" + user_id + "\"") +
                ",\"login_name\":" + (login_name == null ? null : "\"" + login_name + "\"") +
                ",\"login_tel\":" + (login_tel == null ? null : "\"" + login_tel + "\"") +
                ",\"login_mail\":" + (login_mail == null ? null : "\"" + login_mail + "\"") +
                ",\"userflag\":" + (userflag == null ? null : "\"" + userflag + "\"") +
                ",\"nname\":" + (nname == null ? null : "\"" + nname + "\"") +
                ",\"usex\":" + (usex == null ? null : "\"" + usex + "\"") +
                ",\"u_pic_url\":" + (u_pic_url == null ? null : "\"" + u_pic_url + "\"") +
                '}';
    }

    /**
     * Build values content values.
     *
     * @return the content values
     */
    public ContentValues buildValues() {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelp.UserTable.KEY_USER_ID, user_id);
        values.put(SQLiteHelp.UserTable.KEY_USER_NAME, login_name);
        values.put(SQLiteHelp.UserTable.KEY_NICK_NAME, nname);
        values.put(SQLiteHelp.UserTable.KEY_USER_SEX, TextUtils.isEmpty(usex) ? "1" : usex);
        values.put(SQLiteHelp.UserTable.KEY_USER_TEL, login_tel);
        values.put(SQLiteHelp.UserTable.KEY_USER_ICON, u_pic_url);
        values.put(SQLiteHelp.UserTable.KEY_USER_FLAG, userflag);
        values.put(SQLiteHelp.UserTable.KEY_US_LOG_TIME, logTime);
        values.put(SQLiteHelp.UserTable.KEY_LAST_SIGN_TIME, lastSignTime);
        return values;
    }
}
