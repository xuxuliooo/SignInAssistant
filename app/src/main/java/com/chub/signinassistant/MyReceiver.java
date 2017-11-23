package com.chub.signinassistant;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import static com.chub.signinassistant.util.Config.APP_DEBUG;

/**
 * Created by Chub on 2017/11/23.
 * 对手机监听实现自动唤醒服务功能
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (APP_DEBUG) {
            Log.e("onReceive ----------> ", action);
        }
        if (!isServiceWork(context, SignInService.class.getName())) {
            context.startService(new Intent(context, SignInService.class));
        }
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext    the m context
     * @param serviceName the service name
     * @return true代表正在运行 ，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
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
}
