package com.example.myapplication.lcyedu.bean;

import java.io.Serializable;

public class DeviceCheckResult implements Serializable {


   private int code;
   private String msg;


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


}
