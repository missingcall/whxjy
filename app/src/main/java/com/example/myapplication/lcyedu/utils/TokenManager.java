package com.example.myapplication.lcyedu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private static Map<String, String> HEADERS = new HashMap<>();

    //保存获取到的token到本地
    public static void saveTokenBySP(Context context ,String token){
        SharedPreferences sp = context.getSharedPreferences("TokenConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String getTokenBySP(Context context){
        SharedPreferences sp = context.getSharedPreferences("TokenConfig", Context.MODE_PRIVATE);
        String token = sp.getString("token","");
        return token;
    }

    /**
     * 添加需要保持的请求头
     *
     * @param headerName
     * @param headerValue
     */
    public static void addHeader(String headerName, String headerValue) {
        if (StringUtils.isAnyBlank(headerName, headerValue)) {
            return;
        }
        HEADERS.put(headerName, headerValue);
    }

    /**
     * 获取需要保持的请求头
     *
     * @return
     */
    public static Map<String, String> getHeaders() {
        //返回不可修改的MAP
        return Collections.unmodifiableMap(HEADERS);
    }
}
