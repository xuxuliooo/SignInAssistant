package com.chub.signinassistant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chub.signinassistant.bean.CheckResult;
import com.chub.signinassistant.bean.CheckSignEntity;
import com.chub.signinassistant.bean.LoginEntity;
import com.chub.signinassistant.bean.Response;
import com.chub.signinassistant.bean.SignBean;
import com.chub.signinassistant.util.DefaultUtil;
import com.chub.signinassistant.util.HttpUtil;
import com.chub.signinassistant.util.SPUtils;
import com.chub.signinassistant.util.SignHelp;
import com.chub.signinassistant.util.UserHelp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;

import static com.chub.signinassistant.util.Config.APP_DEBUG;
import static com.chub.signinassistant.util.Config.INTERVAL_TIME_MILLI;
import static com.chub.signinassistant.util.Config.LAST_TIME;
import static com.chub.signinassistant.util.Config.UPLOAD_NUMBERS;
import static com.chub.signinassistant.util.Config.URL_CHECK_SIGN_IN;
import static com.chub.signinassistant.util.Config.URL_SIGN_IN;

/**
 * Created by Chub on 2017/11/23.
 * 签到后台服务
 */

public class SignInService extends Service {

    private static final String TAG = SignInService.class.getSimpleName() + "---------------->";

    private ScheduledExecutorService executorService;
    private long delayTime;
    private int i;


    /**
     * 处理后台操作程序
     */
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            i++;
            if (APP_DEBUG)
                Log.e(TAG, "i:" + i);

            SPUtils.save(getApplicationContext(), LAST_TIME, System.currentTimeMillis());
            SPUtils.save(getApplicationContext(), UPLOAD_NUMBERS, i);
            List<LoginEntity> users = UserHelp.getInstance(getApplicationContext()).findAll();
            if (users != null) {
                for (LoginEntity user : users) {
                    if (user != null) {
                        if (APP_DEBUG) Log.e(TAG, "user:" + user.toString());
                        if (!user.signInIsOneDay())
                            signIn(user);
                    }
                }
            }
        }
    };

    private void signIn(final LoginEntity data) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", data.getUser_id());
        HttpUtil.get(URL_CHECK_SIGN_IN, params, CheckResult.class, new HttpUtil.OnRequestCallBack<CheckResult>() {
            @Override
            public void onRequestSuccess(CheckResult response) {
                if (APP_DEBUG) Log.e(TAG, response.toString());
                if (response.getCode() == 200) {
                    CheckSignEntity signEntity = response.getData();
                    if (signEntity != null) {
                        if (!signEntity.Sign()) {
                            userSign(data);
                        } else {
                            if (APP_DEBUG) Log.e(TAG, "保存签到记录");
                            data.setLastSignTime(String.valueOf(System.currentTimeMillis()));
                            UserHelp.getInstance(getApplication()).update(data.getUser_id(), data);
                        }
                    }
                }
            }

            @Override
            public void onRequestFailure(Request request, Exception error) {
                if (APP_DEBUG) Log.e(TAG, "服务器响应超时");
            }
        });
    }

    private void userSign(final LoginEntity data) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", data.getUser_id());
        HttpUtil.get(URL_SIGN_IN, params, Response.class, new HttpUtil.OnRequestCallBack<Response>() {

            @Override
            public void onRequestSuccess(Response response) {
                if (response.getCode() == 200) {//保存签到时间
                    if (APP_DEBUG) Log.e(TAG, "签到成功");
                    SignHelp.getInstance(getApplicationContext()).insert(new SignBean(data.getUser_id(), String.valueOf(System.currentTimeMillis())));
                    data.setLastSignTime(String.valueOf(System.currentTimeMillis()));
                    UserHelp.getInstance(getApplicationContext()).update(data.getUser_id(), data);
                }
                if (APP_DEBUG) Log.e(TAG, "签到失败");
            }

            @Override
            public void onRequestFailure(Request request, Exception error) {
                if (APP_DEBUG) Log.e(TAG, "服务器响应超时");
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (APP_DEBUG)
            Log.e(TAG, "onCreate()");
        executorService = Executors.newSingleThreadScheduledExecutor();
        i = SPUtils.getInt(getApplicationContext(), UPLOAD_NUMBERS, i);
        long last_time = SPUtils.getLong(getApplicationContext(), LAST_TIME, System.currentTimeMillis());
        long intervalTime = System.currentTimeMillis() - last_time;
        delayTime = intervalTime % INTERVAL_TIME_MILLI == 0 ? 0 : INTERVAL_TIME_MILLI - intervalTime % INTERVAL_TIME_MILLI;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (APP_DEBUG)
            Log.e(TAG, "onStartCommand()");
        executorService.scheduleWithFixedDelay(run, delayTime, INTERVAL_TIME_MILLI, TimeUnit.MILLISECONDS);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (APP_DEBUG)
            Log.e(TAG, "onLowMemory()");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (APP_DEBUG)
            Log.e(TAG, "onTaskRemoved()");
        if (!DefaultUtil.isServiceWork(getApplicationContext(), SignInService.class.getName()))
            startService(new Intent(getApplicationContext(), SignInService.class));
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (APP_DEBUG)
            Log.e(TAG, "onTrimMemory()");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
