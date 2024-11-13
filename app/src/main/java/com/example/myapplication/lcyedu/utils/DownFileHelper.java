package com.example.myapplication.lcyedu.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


import com.example.myapplication.EduAppcalition;
import com.example.myapplication.R;
import com.example.myapplication.lcyedu.activity.EduDetailActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;

/**
 * Created by damon on 2018/9/5.
 */
public class DownFileHelper {

    Handler handler;
    Context mContext;
    NotificationManager mNotifyManager;
    Notification.Builder builder;
    NotificationCompat.Builder compatBuilder;
    private String pushId = "2";

    public DownFileHelper(Context mContext, Handler handler) {
        this.handler = handler;
        this.mContext = mContext;
    }

    /**
     * 下载最新版本的apk
     *
     * @param path apk下载地址
     */
    public void downFile(final String path) {
        Toast.makeText(mContext, "正在下载中..." , Toast.LENGTH_SHORT).show();
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap btm = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.launcher_icon);//可以换成你的app的logo
        if (Build.VERSION.SDK_INT >= 26) {

            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            NotificationChannel channel = new NotificationChannel(pushId,
                    "Channel2", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(false);//是否在桌面icon右上角展示小红点
            channel.enableVibration(false);
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.setSound(null,null);
            mNotifyManager.createNotificationChannel(channel);
        }
            compatBuilder = new NotificationCompat.Builder(mContext, pushId)
                    //设置通知显示图标、文字等
                    .setSmallIcon(R.mipmap.launcher_icon)//可以换成你的app的logo
                    .setLargeIcon(btm)
                    .setTicker("正在下载")
                    .setContentTitle(mContext.getResources().getString(R.string.app_name))
                    .setContentText("正在下载")
                    .setSound(null)
                    .setAutoCancel(true)
                    .setShowWhen(true)
                    .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_MAX);
            mNotifyManager.notify(2, compatBuilder.build());

//        } else {
//            builder = new Notification.Builder(mContext);
//            builder.setSmallIcon(R.drawable.system_touxiang)//可以换成你的app的logo
//                    .setLargeIcon(btm)
//                    .setTicker("正在下载")
//                    .setAutoCancel(false)//可以滑动删除通知栏
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .build();
//            mNotifyManager.notify(1, builder.build());
//        }
        new Thread() {
            public void run() {
                HttpURLConnection con = null;
                InputStream is = null;
                try {

                    URL url = new URL(path);
                    con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode() == 200) {
                        int length = con.getContentLength();// 获取文件大小
                        is = con.getInputStream();

                        FileOutputStream fileOutputStream = null;
                        if (is != null) {
                            //对apk进行保存
//                            File file = new File(Environment.getExternalStorageDirectory().getPath(), "whxjy.apk");
//                            File file = new File(EduAppcalition.getInstance().getExternalFilesDir(null).getPath()+"whxjy");

                            //创建文件路径
                            File dir=new File(EduAppcalition.getInstance().getExternalFilesDir(null).getPath()+"myApk");
                            if (!dir.exists()){
                                dir.mkdir();
                            }
                            //创建文件
                            File file = new File(dir+"/"+"whxjy.apk");
                            if (!file.exists()){
                                file.createNewFile();
                            }

                            fileOutputStream = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int ch;
                            int process = 0;
                            NumberFormat numberFormat = NumberFormat.getInstance();
                            // 设置精确到小数点后2位
                            numberFormat.setMaximumFractionDigits(2);
                            String result;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);
                                process += ch;
                                //更新进度条
                                result = numberFormat.format((float) process / (float) length * 100);
                                compatBuilder.setContentText("下载进度：" + result + "%");
                                compatBuilder.setProgress(length, process, false);
                                mNotifyManager.notify(2,compatBuilder.build());


                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                        //apk下载完成，使用Handler()通知安装apk
                        compatBuilder.setContentText("下载完成");
                        compatBuilder.setProgress(length, length, false);
                        mNotifyManager.notify(2,compatBuilder.build());
                        mNotifyManager.cancelAll();
                        handler.sendEmptyMessage(0);

                    }else{
                        handler.sendEmptyMessage(1);
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(1);
                    e.printStackTrace();
                }finally {
                    try {
                        if(con != null){
                            con.disconnect();
                        }
                        if(is != null ){
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();

    }
}

