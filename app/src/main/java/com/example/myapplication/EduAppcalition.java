package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.easefun.polyvsdk.PolyvSDKClient;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class EduAppcalition extends MultiDexApplication {

    private static EduAppcalition mInstance;

    //SDK加密串,用的临时的
    public static final String SDK_PASSWORD = "5oEroYLVGgOGUT3grWOZbinA8mJ1/+M0rRGy9QI0DCSkF0ch+nYgPLfGIm4cxugXZZIsPGzcHX69AnKaf05vGpcGeOmYmT4po4ztKIZKCQx43hVo0FDPWHkbEhbvgHU7ncvGzbDR/ppgPbpsVwS4fA==";
    public static final String AES_KEY = "y59XGpgNImOVEbTJ";
    public static final String AES_IV = "7SaLQL72uj4o6D4e";

    public static String userNo;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        //获取 PolyvSDKClient实例
        PolyvSDKClient client = PolyvSDKClient.getInstance();
        //设置SDK加密串
        client.settingsWithConfigString(SDK_PASSWORD, AES_KEY, AES_IV);
        //初始化SDK设置
        client.initSetting(getApplicationContext());
        //设置学员唯一标识
//        client.setViewerId("学员唯一标识");
        //设置下载保存目录
//        PolyvSDKClient.getInstance().setDownloadDir("下载视频保存的目录");
//        CrashReport.initCrashReport(getApplicationContext(), "53458f3a7a", false);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包解决4.x崩溃问题
        MultiDex.install(this);
    }

    public static EduAppcalition getInstance() {
        return mInstance;
    }
}
