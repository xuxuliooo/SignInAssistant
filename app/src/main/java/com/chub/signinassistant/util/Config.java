package com.chub.signinassistant.util;

/**
 * Created by Chub on 2017/11/23.
 * 常量
 */

public class Config {

    /**
     * 是否调试模式
     */
    public static final boolean APP_DEBUG = true;
    /**
     * 状态保存文件
     */
    static final String SP_FILE_NAME = "Config";
    /**
     * 上一次保存的时间
     */
    public static final String LAST_TIME = "last_time";
    /**
     * 上传次数
     */
    public static final String UPLOAD_NUMBERS = "upload_the_number";
    /**
     * 一天的毫秒数
     * 间隔时间
     */
    public static final long INTERVAL_TIME_MILLI = 2 * 60 * 60 * 1000;
    /**
     * 保存账号信息
     */
    public static final String KEY_ID = "account_number";
    /**
     * 签到日期
     */
    public static final String KEY_SIGN_DATE = "sign_date";


    /**
     * Yi游 服务器地址
     */
    private static final String URL_HOST = "https://www.yi-youtrip.com/";
    /**
     * Yi游 API
     */
    private static final String URL_HOST_API = URL_HOST + "api/";
    /**
     * Yi游 API
     */
    private static final String URL_HOME_PHP = URL_HOST + "cgi/epaytripMain.php";
    /**
     * Yi游 登陆
     */
    public static final String URL_LOGIN = URL_HOME_PHP + "?command=getUser";
    /**
     * 签到
     */
    public static final String URL_SIGN_IN = URL_HOST_API + "ggapp/integral/getSignedIntegral";
    /**
     * 检查是否签到
     */
    public static final String URL_CHECK_SIGN_IN = URL_HOST_API + "ggapp/integral/getSearchSign";
    /**
     * 重置密码
     */
    public static final String URL_RESET_PW = URL_HOME_PHP + "?command=setPassword";
    /**
     * 获取短信验证码
     */
    public static final String URL_GET_CONFIRM_CODE = URL_HOME_PHP + "?command=sendTelByForget&tel=0086";
}
