package com.chub.signinassistant.bean;

import android.database.Cursor;

import com.chub.signinassistant.util.DefaultUtil;
import com.chub.signinassistant.util.SQLiteHelp;

import static com.chub.signinassistant.util.Config.CHINA_TIME_STYLE;

/**
 * Description：
 * Created by Chub on 2017/11/26.
 */
public class SignInLogBean {

    /**
     * The Id.
     */
    private int id;
    /**
     * The User id.
     */
    private String userId;
    /**
     * The Nick name.
     */
    private String nickName;
    /**
     * The User tel.
     */
    private String userTel;
    /**
     * The User icon.
     */
    private String userIcon;
    /**
     * The Sign time.
     */
    private String signTime;

    /**
     * Instantiates a new Sign in log bean.
     */
    public SignInLogBean() {
    }

    /**
     * Instantiates a new Sign in log bean.
     *
     * @param cursor the cursor
     */
    public SignInLogBean(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(SQLiteHelp.ID));
        userId = cursor.getString(cursor.getColumnIndex(SQLiteHelp.SignTable.KEY_SIGN_ID));
        signTime = cursor.getString(cursor.getColumnIndex(SQLiteHelp.SignTable.KEY_SIGN_TIME));
        nickName = cursor.getString(cursor.getColumnIndex(SQLiteHelp.UserTable.KEY_NICK_NAME));
        userTel = cursor.getString(cursor.getColumnIndex(SQLiteHelp.UserTable.KEY_USER_TEL));
        userIcon = cursor.getString(cursor.getColumnIndex(SQLiteHelp.UserTable.KEY_USER_ICON));
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Gets user tel.
     *
     * @return the user tel
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return userTel.startsWith("0086") ? userTel.substring(4) : userTel;
    }

    /**
     * Sets user tel.
     *
     * @param userTel the user tel
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    /**
     * Gets user icon.
     *
     * @return the user icon
     */
    public String getUserIcon() {
        return userIcon;
    }

    /**
     * Sets user icon.
     *
     * @param userIcon the user icon
     */
    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    /**
     * Gets sign time.
     *
     * @return the sign time
     */
    public String getSignTime() {
        return DefaultUtil.dateToString(Long.valueOf(signTime), CHINA_TIME_STYLE);
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
                "\"id\":" + id +
                ",\"userId\":" + (userId == null ? null : "\"" + userId + "\"") +
                ",\"nickName\":" + (nickName == null ? null : "\"" + nickName + "\"") +
                ",\"userTel\":" + (userTel == null ? null : "\"" + userTel + "\"") +
                ",\"userIcon\":" + (userIcon == null ? null : "\"" + userIcon + "\"") +
                ",\"signTime\":" + (signTime == null ? null : "\"" + signTime + "\"") +
                '}';
    }
}
