package com.chub.signinassistant.bean;

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
}
