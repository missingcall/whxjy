package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

public class LoginDataToken {

   @SerializedName("userNo")
   private String userNo;
   @SerializedName("mobile")
   private String mobile;
   @SerializedName("token")
   private String token;

   public String getUserNo() {
      return userNo;
   }

   public void setUserNo(String userNo) {
      this.userNo = userNo;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}
