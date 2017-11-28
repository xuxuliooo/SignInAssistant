package com.chub.signinassistant.util;

import android.text.TextUtils;

import com.chub.signinassistant.R;

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
     * 间隔时间
     */
    public static final long INTERVAL_TIME_MILLI = 60 * 60 * 1000;

    /**
     * 中国时间风格
     */
    public static final String CHINA_TIME_STYLE = "yyyy年MM月dd日 HH时mm分";


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
    /**
     * 图片服务器前缀
     */
    private static final String URL_IMG_PREFIX = "https://cdn.yi-youtrip.com";

    /**
     * 添加Url前缀
     */
    public static String AppendImageUrlPrefix(String url) {
        return TextUtils.isEmpty(url) || url.startsWith("http") ? url : URL_IMG_PREFIX + url;
    }

    private static final int[] COLOR_RES_VALUE = {
            R.color.default0,
            R.color.default1,
            R.color.default2,
            R.color.default3,
            R.color.default4,
            R.color.default5,
            R.color.default6,
            R.color.default7,
            R.color.default8,
            R.color.default9,
            R.color.default10,
            R.color.default11,
            R.color.default12,
            R.color.default13,
            R.color.default14,
            R.color.default15,
            R.color.default16,
            R.color.default17,
            R.color.default18,
            R.color.default19,
            R.color.default20,
            R.color.default21,
            R.color.default22,
    };

    /**
     * 获取指定位置的颜色
     */
    public static int getDefaultColor(int position) {
        return COLOR_RES_VALUE[position % COLOR_RES_VALUE.length];
    }
}
