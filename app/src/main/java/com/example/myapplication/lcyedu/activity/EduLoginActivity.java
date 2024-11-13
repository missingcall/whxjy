package com.example.myapplication.lcyedu.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Browser;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.EduAppcalition;
import com.example.myapplication.R;
import com.example.myapplication.lcyedu.Constants;
import com.example.myapplication.lcyedu.bean.LoginDataToken;
import com.example.myapplication.lcyedu.bean.LoginRequest;
import com.example.myapplication.lcyedu.bean.LoginResult;
import com.example.myapplication.lcyedu.bean.UpdateRequest;
import com.example.myapplication.lcyedu.bean.UpdateResult;
import com.example.myapplication.lcyedu.ui.view.VersionUpdateDialog;
import com.example.myapplication.lcyedu.utils.ConnUtils;
import com.example.myapplication.lcyedu.utils.DownFileHelper;
import com.example.myapplication.lcyedu.utils.GSONUtils;
import com.example.myapplication.lcyedu.utils.InstallApk;
import com.example.myapplication.lcyedu.utils.TokenManager;
import com.example.myapplication.lcyedu.utils.Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuexiang.xui.XUI;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EduLoginActivity extends BaseActivity implements View.OnClickListener, Callback {

    private EditText mMobile;
    private EditText mPassword;
    private Button mLogin;
    private Button mLoginAuto;
    private Button mLoginExit;
    private Callback mUpdateCallBack;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new InstallApk(EduLoginActivity.this).installApk(new File(Environment.getExternalStorageDirectory(), "whxjy.apk"));
                    new InstallApk(EduLoginActivity.this).installApk(new File(EduAppcalition.getInstance().getExternalFilesDir(null).getPath()+"myApk" , ("/"+"whxjy.apk")));

                    break;
                case 1:
                    Toast.makeText(EduLoginActivity.this, "连接失败，请检查网络设置", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_login);


    }

    @Override
    protected void onResume() {
        super.onResume();

        initView();
        initListeners();
        checkUpdateVersion();//检查版本
    }

    private void checkUpdateVersion() {

        //更新
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setAppver(Utils.getVersionCode(this) + "");
        updateRequest.setDid(Utils.getUniqueId(this));
        mUpdateCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bodyString = response.body().string();
                Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "update body : " + bodyString);
                UpdateResult updateResult = GSONUtils.GSON.fromJson(bodyString, UpdateResult.class);

                if (updateResult == null || updateResult.getData().getAppUrl() == null || updateResult.getCode() != 200) {
                    return;
                }

                if (!updateResult.getData().getAppUrl().isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            VersionUpdateDialog dialog = new VersionUpdateDialog(EduLoginActivity.this, updateResult);
                            dialog.getUpdateButton().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    dialog.cancel();
                                    dialog.getUpdateButton().setVisibility(View.GONE);
                                    new DownFileHelper(EduLoginActivity.this, handler)
                                            .downFile(updateResult.getData().getAppUrl());

                                }
                            });
                            dialog.show();
                        }
                    });


                }
            }
        };
        ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.UPDATE_ADDRESS), updateRequest, mUpdateCallBack, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //杀掉进程
        System.exit(0);
    }

    private void initView() {
        mMobile = (EditText) findViewById(R.id.mobile);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = findViewById(R.id.login);
        mLoginAuto = findViewById(R.id.login_auto);
        mLoginExit = findViewById(R.id.login_exit);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login:
                LoginRequest loginRequest = getLoginRequest();
                ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.LOGIN_ADDRESS), loginRequest, this, false);
                break;

            case R.id.login_auto:
                /*LoginRequest loginRequest_auto = new LoginRequest();
                loginRequest_auto.setClientId(Constants.CLIENT_ID);
                loginRequest_auto.setMobile("15222093888");
                loginRequest_auto.setPassword("123456");
                loginRequest_auto.setDid(Utils.getUniqueId(this));
                loginRequest_auto.setIp("");
                ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.LOGIN_ADDRESS), loginRequest_auto, this, false);*/

                startActivity(new Intent(EduLoginActivity.this, BrowserActivity.class));
                break;

            case R.id.login_exit:
                finish();
                System.exit(0);
                break;
        }
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setClientId(Constants.CLIENT_ID);
        loginRequest.setMobile(mMobile.getText().toString());
        loginRequest.setPassword(mPassword.getText().toString());
        loginRequest.setIp("");
        loginRequest.setClientSource("jinzhou");
        loginRequest.setDid(Utils.getUniqueId(this));
        return loginRequest;
    }


    @Override
    public void onFailure(Call call, IOException e) {
//        Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求失败 : " + e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String bodyString = response.body().string();
        Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "login body : " + bodyString);
        LoginResult loginResult = GSONUtils.GSON.fromJson(bodyString, LoginResult.class);
        if (loginResult.getCode() == 200) {
            LoginDataToken loginDataToken = loginResult.getData(LoginDataToken.class);
            //播放页面需要用到
            EduAppcalition.userNo = loginDataToken.getUserNo();
            //为后续请求增加header
            TokenManager.addHeader("token", loginDataToken.getToken());
//            Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求成功,token: " + GSONUtils.GSON.toJson(loginDataToken.getToken()));
            //同时启动MainActivity,说是子线程也能启动
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(EduLoginActivity.this, EduMainActivity.class));
                    Toast.makeText(EduLoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
//            Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求失败: " + loginResult);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EduLoginActivity.this, "msg: " + loginResult.getMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void initListeners() {
        mMobile.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mLoginAuto.setOnClickListener(this);
        mLoginExit.setOnClickListener(this);
    }
}