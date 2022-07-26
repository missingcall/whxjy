package com.example.myapplication.lcyedu.activity;

import android.app.Activity;
import android.os.Bundle;

import com.xuexiang.xui.XUI;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      XUI.initTheme(this);

   }

   /**
    * 初始化监听
    */
   protected abstract void initListeners();
}
