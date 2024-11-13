package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

   @SerializedName("clientId")
   private String clientId;
   @SerializedName("ip")
   private String ip;
   @SerializedName("mobile")
   private String mobile;
   @SerializedName("password")
   private String password;
   @SerializedName("appver")
   private String appver;
   @SerializedName("did")
   private String did;

   public String getAppver() {
      return appver;
   }

   public void setAppver(String appver) {
      this.appver = appver;
   }

   public String getDid() {
      return did;
   }

   public void setDid(String did) {
      this.did = did;
   }

   @SerializedName("clientSource")
   private String clientSource;

   public String getClientSource() {
      return clientSource;
   }

   public void setClientSource(String clientSource) {
      this.clientSource = clientSource;
   }

   public String getClientId() {
      return clientId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getMobile() {
      return mobile;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
