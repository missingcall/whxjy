package com.example.myapplication.lcyedu.bean;


import com.example.myapplication.lcyedu.utils.GSONUtils;

import java.io.Serializable;

public class LoginResult implements Serializable {

   public <T> T getData(Class<T> clz) {
      if (data == null) {
         return null;
      }
      return GSONUtils.GSON.fromJson(data.toString(), clz);
   }

   private int code;
   private String msg;
   private Object data;

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public Object getData() {
      return data;
   }

   public void setData(Object data) {
      this.data = data;
   }

   @Override
   public String toString() {
      return "LoginResult{" +
              "code=" + code +
              ", msg='" + msg + '\'' +
              ", data=" + data +
              '}';
   }
}
