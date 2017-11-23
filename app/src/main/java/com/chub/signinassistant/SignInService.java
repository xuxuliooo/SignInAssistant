package com.chub.signinassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chub.signinassistant.util.SPUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.chub.signinassistant.util.Config.APP_DEBUG;
import static com.chub.signinassistant.util.Config.LAST_TIME;
import static com.chub.signinassistant.util.Config.ONE_DAY_MS;
import static com.chub.signinassistant.util.Config.UPLOAD_NUMBERS;

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
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (APP_DEBUG)
            Log.e(TAG, "onCreate()");
        executorService = Executors.newSingleThreadScheduledExecutor();
        i = SPUtils.getInt(getApplicationContext(), UPLOAD_NUMBERS, i);
        long last_time = SPUtils.getLong(getApplicationContext(), LAST_TIME, System.currentTimeMillis());
        long intervalTime = System.currentTimeMillis() - last_time;
        delayTime = intervalTime % ONE_DAY_MS == 0 ? 0 : ONE_DAY_MS - intervalTime % ONE_DAY_MS;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (APP_DEBUG)
            Log.e(TAG, "onStartCommand()");
        executorService.scheduleWithFixedDelay(run, delayTime, ONE_DAY_MS, TimeUnit.MILLISECONDS);
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
