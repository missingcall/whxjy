package com.example.myapplication.lcyedu.utils;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.example.myapplication.BuildConfig;

import java.io.File;

/**
 * Created by damon on 2018/9/5.
 */


public class InstallApk {


    Activity context;

    public InstallApk(Activity context) {
        this.context = context;
    }

    public void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = context.getPackageManager().canRequestPackageInstalls();
//            if (b) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        BuildConfig.APPLICATION_ID+".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                context.startActivity(intent);
//            } else {
//                //请求安装未知应用来源的权限
//                SureCancleDialog confirmDialog =  new SureCancleDialog(context);
//                confirmDialog.setmTvHintTitle("安装权限");
//                confirmDialog.setmTvHintInfo("Android8.0安装应用需要打开未\n知来源权限，请去设置中开启权限");
//                confirmDialog.setmTvSureText("去设置");
//                confirmDialog.setmTvCancelText("取消");
//                confirmDialog.getSureView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String[] mPermissionList = new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES};
//                        ActivityCompat.requestPermissions(context, mPermissionList, 2);
//                    }
//                });
//                confirmDialog.getCancelView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        confirmDialog.cancel();
//                    }
//                });
//                confirmDialog.show();
//                ConfirmDialog confirmDialog =new ConfirmDialog(context);
//                confirmDialog.setStyle("安装权限","Android8.0安装应用需要打开未\n知来源权限，请去设置中开启权限",
//                        "去设置","取消");
//                confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
//                    @Override
//                    public void doConfirm() {
//                        String[] mPermissionList = new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES};
//                        ActivityCompat.requestPermissions(context, mPermissionList, 2);
//                    }
//                });
//                confirmDialog.show();
//            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        BuildConfig.APPLICATION_ID+".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }

    }
}

