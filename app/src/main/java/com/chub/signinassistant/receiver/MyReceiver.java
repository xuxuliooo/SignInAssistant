package com.chub.signinassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.chub.signinassistant.service.SignInService;

import static com.chub.signinassistant.util.Config.APP_DEBUG;
import static com.chub.signinassistant.util.DefaultUtil.isServiceWork;

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
        if (!isServiceWork(context, SignInService.class.getName()))
            context.startService(new Intent(context, SignInService.class));
    }

}
