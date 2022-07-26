package com.example.myapplication.lcyedu.bean;


import com.google.gson.annotations.SerializedName;

/*{
  "ip": "string",
  "periodId": 0,
  "userNo": 0,
  "videoVid": "string"
}*/
public class CourseSignRequest {

   @SerializedName("ip")
   private String ip;
   @SerializedName("periodId")
   private String periodId;
   @SerializedName("userNo")
   private String userNo;
   @SerializedName("videoVid")
   private String videoVid;

   public String getIp() {
      return ip;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getPeriodId() {
      return periodId;
   }

   public void setPeriodId(String periodId) {
      this.periodId = periodId;
   }

   public String getUserNo() {
      return userNo;
   }

   public void setUserNo(String userNo) {
      this.userNo = userNo;
   }

   public String getVideoVid() {
      return videoVid;
   }

   public void setVideoVid(String videoVid) {
      this.videoVid = videoVid;
   }
}
