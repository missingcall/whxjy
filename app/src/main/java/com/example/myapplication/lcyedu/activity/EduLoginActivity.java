package com.example.myapplication.lcyedu.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.myapplication.lcyedu.utils.ConnUtils;
import com.example.myapplication.lcyedu.utils.GSONUtils;
import com.example.myapplication.lcyedu.utils.TokenManager;
import com.xuexiang.xui.XUI;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_login);

        initView();
        initListeners();


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
                ConnUtils.postJson(Constants.BASE_ADDRESS + Constants.LOGIN_ADDRESS, loginRequest, this, false);
                break;

            case R.id.login_auto:
                LoginRequest loginRequest_auto = new LoginRequest();
                loginRequest_auto.setClientId(Constants.CLIENT_ID);
                loginRequest_auto.setMobile("17611546006");
                loginRequest_auto.setPassword("123456");
                loginRequest_auto.setIp("");
                ConnUtils.postJson(Constants.BASE_ADDRESS + Constants.LOGIN_ADDRESS, loginRequest_auto, this, false);
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
        return loginRequest;
    }


    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求失败 : " + e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String bodyString = response.body().string();
        Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "body : " + bodyString);
        LoginResult loginResult = GSONUtils.GSON.fromJson(bodyString, LoginResult.class);
        if (loginResult.getCode() == 200) {
            LoginDataToken loginDataToken = loginResult.getData(LoginDataToken.class);
            //播放页面需要用到
            EduAppcalition.userNo = loginDataToken.getUserNo();
            //为后续请求增加header
            TokenManager.addHeader("token", loginDataToken.getToken());
            Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求成功,token: " + GSONUtils.GSON.toJson(loginDataToken.getToken()));
            //同时启动MainActivity,说是子线程也能启动
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(EduLoginActivity.this, EduMainActivity.class));
                    Toast.makeText(EduLoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d(Constants.EDU_LOGIN_ACTIVITY_LOG, "请求失败: " + loginResult);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(EduLoginActivity.this, "msg: " + loginResult.getMsg() , Toast.LENGTH_SHORT).show();
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