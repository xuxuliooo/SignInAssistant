package com.chub.signinassistant.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.chub.signinassistant.util.Config.APP_DEBUG;

/**
 * Description：
 * Created by Chub on 2017/11/24.
 */
public class DefaultUtil {

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext    the m context
     * @param serviceName the service name
     * @return true代表正在运行 ，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(200);
            if (services == null || services.isEmpty()) {
                return false;
            }
            for (ActivityManager.RunningServiceInfo service : services) {
                if (TextUtils.equals(service.service.getClassName(), serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * MD5 加密
     *
     * @param text the text
     * @return the string
     */
    public static String MD5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(text.getBytes("utf-8"));
            return toHex(bytes);
        } catch (Exception e) {
            if (APP_DEBUG)
                Log.e("DefaultUtil", "e:" + e);
        }
        return "";
    }

    private static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte by : bytes) {
            builder.append(Character.forDigit((by & 0xF0) >> 4, 16))
                    .append(Character.forDigit(by & 0xF, 16));
        }
        return builder.toString();
    }

    /**
     * Date to string string.
     *
     * @param time the time
     * @return the string
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

}
