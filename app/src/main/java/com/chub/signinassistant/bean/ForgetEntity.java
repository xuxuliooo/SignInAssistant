package com.chub.signinassistant.bean;

/**
 * Description：重置密码返回信息
 * Created by Chub on 2017/11/25.
 */
public class ForgetEntity {

    /**
     * uid : 20170413015856624163
     * tel : 008617721125121
     * identify : 957135
     */

    private String uid;
    private String tel;
    private String identify;

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets tel.
     *
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * Sets tel.
     *
     * @param tel the tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Gets identify.
     *
     * @return the identify
     */
    public String getIdentify() {
        return identify;
    }

    /**
     * Sets identify.
     *
     * @param identify the identify
     */
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String toString() {
        return "{" +
                "\"uid\":" + (uid == null ? null : "\"" + uid + "\"") +
                ",\"tel\":" + (tel == null ? null : "\"" + tel + "\"") +
                ",\"identify\":" + (identify == null ? null : "\"" + identify + "\"") +
                '}';
    }
}
