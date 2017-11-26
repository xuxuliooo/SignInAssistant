package com.chub.signinassistant.util;

import android.accounts.NetworkErrorException;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.chub.signinassistant.util.Config.APP_DEBUG;

/**
 * Description：网络工具类
 * Created by Chub on 2017/11/24.
 */
public class HttpUtil {

    private static final String TAG = HttpUtil.class.getSimpleName();

    private static HttpUtil httpUtil;
    private static int code = Integer.MAX_VALUE >> 2;

    private OkHttpClient okHttpClient;
    private Handler mHandler;
    private Gson gson;

    private HttpUtil() {
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    private static HttpUtil getInstance() {
        if (httpUtil == null) {
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    private String buildUrl(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url)
                .append("?");
        for (String key : params.keySet()) {
            builder.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        params.clear();
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * Create get request.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param header      the header
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    private <T> void createGetRequest(String url, int requestCode, @Nullable Map<String, String> header, @Nullable Map<String, String> params, @Nullable final Class<? extends T> clz, final OnRequestCallBack<T> callBack) {
        String mUrl = buildUrl(url, params);
        Request.Builder builder = new Request.Builder().get()
                .url(mUrl)
                .tag(requestCode);
        if (header != null && !header.isEmpty()) {
            for (String key : header.keySet()) {
                builder.addHeader(key, header.get(key));
                if (APP_DEBUG)
                    Log.i(TAG, key + ":" + header.get(key));
            }
            header.clear();
        }
        if (APP_DEBUG)
            Log.i(TAG, mUrl);

        final Request request = builder.build();
        Callback call = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                buildFailureResponse(request, e, callBack);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                buildSuccessResponse(request, response, clz, callBack);
            }
        };
        okHttpClient.newCall(request).enqueue(call);
    }

    /**
     * Create post request.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param header      the header
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    private <T> void createPostRequest(String url, int requestCode, @Nullable Map<String, String> header, @Nullable Map<String, String> params, @Nullable final Class<? extends T> clz, final OnRequestCallBack<T> callBack) {
        Request.Builder builder = new Request.Builder()
                .tag(requestCode)
                .url(url);
        if (APP_DEBUG)
            Log.i(TAG, url);

        if (header != null && !header.isEmpty()) {
            for (String key : header.keySet()) {
                builder.addHeader(key, header.get(key));
                if (APP_DEBUG)
                    Log.i(TAG, key + ":" + header.get(key));
            }
            header.clear();
        }
        if (params != null && !params.isEmpty()) {
            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
                if (APP_DEBUG)
                    Log.i(TAG, key + ":" + params.get(key));
            }
            params.clear();
            builder.post(bodyBuilder.build());
        }
        final Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                buildFailureResponse(request, e, callBack);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                buildSuccessResponse(request, response, clz, callBack);
            }
        };
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * Build failure response.
     *
     * @param <T>      the type parameter
     * @param request  the request
     * @param e        the e
     * @param callBack the call back
     */
    private <T> void buildFailureResponse(final Request request, final IOException e, final OnRequestCallBack<T> callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onRequestFailure(request, e);
            }
        });
    }

    /**
     * Build success response.
     *
     * @param <T>      the type parameter
     * @param request  the request
     * @param response the response
     * @param clz      the clz
     * @param callBack the call back
     */
    private <T> void buildSuccessResponse(final Request request, final Response response, final Class<? extends T> clz, final OnRequestCallBack<T> callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        try {
                            T t;
                            String data = body.string();
                            if (APP_DEBUG)
                                Log.d(TAG, data);
                            if (clz != null) {
                                t = gson.fromJson(data, clz);
                            } else {
                                //noinspection unchecked
                                t = (T) data;

                            }
                            callBack.onRequestSuccess(t);
                        } catch (IOException e) {
                            callBack.onRequestFailure(request, e);
                        }
                    } else {
                        callBack.onRequestFailure(request, new NullPointerException("response body is null!"));
                    }
                } else {
                    callBack.onRequestFailure(request, new NetworkErrorException(response.toString()));
                }
            }
        });
    }

    /**
     * Get.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param header      the header
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    public static <T> void get(String url, int requestCode, @Nullable Map<String, String> header, @Nullable Map<String, String> params, Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        getInstance().createGetRequest(url, requestCode, header, params, clz, callBack);
    }

    /**
     * Get.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    public static <T> void get(String url, int requestCode, @Nullable Map<String, String> params, Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        get(url, requestCode, null, params, clz, callBack);
    }

    /**
     * Get.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param params   the params
     * @param clz      the clz
     * @param callBack the call back
     */
    public static <T> void get(String url, @Nullable Map<String, String> params, Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        get(url, code--, params, clz, callBack);
    }

    /**
     * Get.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param clz         the clz
     * @param callBack    the call back
     */
    public static <T> void get(String url, int requestCode, @Nullable Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        get(url, requestCode, null, clz, callBack);
    }

    /**
     * Get.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param clz      the clz
     * @param callBack the call back
     */
    public static <T> void get(String url, @Nullable Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        get(url, code--, clz, callBack);
    }

    /**
     * Get.
     *
     * @param url      the url
     * @param callBack the call back
     */
    public static void get(String url, OnRequestCallBack<String> callBack) {
        get(url, null, callBack);
    }


    /**
     * Post.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param header      the header
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    public static <T> void post(String url, int requestCode, @Nullable Map<String, String> header, @Nullable Map<String, String> params, @Nullable Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        getInstance().createPostRequest(url, requestCode, header, params, clz, callBack);
    }

    /**
     * Post.
     *
     * @param <T>         the type parameter
     * @param url         the url
     * @param requestCode the request code
     * @param params      the params
     * @param clz         the clz
     * @param callBack    the call back
     */
    public static <T> void post(String url, int requestCode, @Nullable Map<String, String> params, @Nullable Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        post(url, requestCode, null, params, clz, callBack);
    }

    /**
     * Post.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param params   the params
     * @param clz      the clz
     * @param callBack the call back
     */
    public static <T> void post(String url, @Nullable Map<String, String> params, @Nullable Class<? extends T> clz, OnRequestCallBack<T> callBack) {
        post(url, code--, params, clz, callBack);
    }

    /**
     * Post.
     *
     * @param url      the url
     * @param params   the params
     * @param callBack the call back
     */
    public static void post(String url, @Nullable Map<String, String> params, OnRequestCallBack<String> callBack) {
        post(url, code--, params, null, callBack);
    }

    /**
     * The interface On request call back.
     *
     * @param <T> the type parameter
     */
    public interface OnRequestCallBack<T> {
        /**
         * 请求成功，返回 T 类型。
         * 如果没有设置类型，则以String形式返回
         *
         * @param response the response
         */
        void onRequestSuccess(T response);

        /**
         * 请求失败，返回请求信息和错误信息
         *
         * @param request the request
         * @param error   the error
         */
        void onRequestFailure(Request request, Exception error);
    }
}
