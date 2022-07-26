package com.example.myapplication.lcyedu.utils;

import com.example.myapplication.lcyedu.bean.LoginDataToken;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConnUtils {


    private static Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    public static LoginDataToken sLoginDataToken;//请求到的token就保存在这里
    public static String sToken = "";//请求到的token就保存在这里


    public static void get(String url, Object param, Callback callback) {
        try {
            // 1.拿到okhttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            String paramJson = GSONUtils.GSON.toJson(param);
            Map<String, String> params = GSONUtils.GSON.fromJson(paramJson, MAP_TYPE);
            //构造query string
            String queryString = "";
            for (Map.Entry<String, String> kv : params.entrySet()) {
                queryString += kv.getKey() + "=" + kv.getValue() + "&";
            }

            // 3.构造Request
            Request.Builder builder = new Request.Builder();
            addHeaders(builder);
            Request request = builder.url(url + "?" + queryString)
                    .get()
                    .build();
            //4.创建一个Call对象
            Call call = okHttpClient.newCall(request);
            //5.异步请求enqueue(Callback)
            call.enqueue(callback);
        } catch (Throwable e) {
            //TODO log
            return;
        }
    }


    /**
     * @param url
     * @param param
     * @param callback
     * @param hasHeader 是否需要header
     */
    public static void post(String url, Object param, Callback callback, boolean hasHeader) {
        // 1.拿到okhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.创建RequestBody
        RequestBody requestBody = RequestBody.create(null, "");
        // 3.构造Request
        Request.Builder builder = new Request.Builder();
        if (hasHeader) {
            addHeaders(builder);
        }
        Request request = builder.url(url)
                .post(requestBody)
                .build();
        //4.创建一个Call对象
        Call call = okHttpClient.newCall(request);
        //5.异步请求enqueue(Callback)
        call.enqueue(callback);
    }

    /**
     * @param url
     * @param param
     * @param callback
     * @param hasHeader 是否需要header
     */
    public static void postJson(String url, Object param, Callback callback, boolean hasHeader) {
            MediaType contentType = MediaType.parse("application/json; charset=utf-8");
            // 1.拿到okhttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            //通过bean对象获得json
            String paramJson = GSONUtils.GSON.toJson(param);
            // 2.创建RequestBody
            RequestBody requestBody = RequestBody.create(contentType, paramJson);
            // 3.构造Request
            Request.Builder builder = new Request.Builder();
            if (hasHeader) {
                addHeaders(builder);
            }
            Request request = builder.url(url)
                    .post(requestBody)
                    .build();
            //4.创建一个Call对象
            Call call = okHttpClient.newCall(request);
            //5.异步请求enqueue(Callback)
            call.enqueue(callback);
    }

    private static void addHeaders(Request.Builder builder) {
        for (Map.Entry<String, String> header : TokenManager.getHeaders().entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
    }
}
