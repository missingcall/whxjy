package com.example.myapplication.lcyedu.bean;

import java.io.Serializable;

public class UpdateResult implements Serializable {


   private int code;
   private String msg;
   private DataDTO data;

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

   public DataDTO getData() {
      return data;
   }

   public void setData(DataDTO data) {
      this.data = data;
   }

   public static class DataDTO {
      private String appUrl;
      private boolean forceUpdate;

      public String getAppUrl() {
         return appUrl;
      }

      public void setAppUrl(String appUrl) {
         this.appUrl = appUrl;
      }

      public boolean isForceUpdate() {
         return forceUpdate;
      }

      public void setForceUpdate(boolean forceUpdate) {
         this.forceUpdate = forceUpdate;
      }
   }
}
