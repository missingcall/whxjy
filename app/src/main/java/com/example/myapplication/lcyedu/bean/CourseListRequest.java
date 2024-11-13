package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

public class CourseListRequest extends BaseRequest{

   @SerializedName("categoryId1")
   private String categoryId1;
   @SerializedName("categoryId2")
   private String categoryId2;
   @SerializedName("categoryId3")
   private String categoryId3;
   @SerializedName("orgNo")
   private String orgNo;
   @SerializedName("pageCurrent")
   private Integer pageCurrent;
   @SerializedName("pageSize")
   private Integer pageSize;
   @SerializedName("isFree")
   private String isFree;
   @SerializedName("isVipFree")
   private String isVipFree;

   public String getCategoryId1() {
      return categoryId1;
   }

   public void setCategoryId1(String categoryId1) {
      this.categoryId1 = categoryId1;
   }

   public String getCategoryId2() {
      return categoryId2;
   }

   public void setCategoryId2(String categoryId2) {
      this.categoryId2 = categoryId2;
   }

   public String getCategoryId3() {
      return categoryId3;
   }

   public void setCategoryId3(String categoryId3) {
      this.categoryId3 = categoryId3;
   }

   public String getOrgNo() {
      return orgNo;
   }

   public void setOrgNo(String orgNo) {
      this.orgNo = orgNo;
   }

   public Integer getPageCurrent() {
      return pageCurrent;
   }

   public void setPageCurrent(Integer pageCurrent) {
      this.pageCurrent = pageCurrent;
   }

   public Integer getPageSize() {
      return pageSize;
   }

   public void setPageSize(Integer pageSize) {
      this.pageSize = pageSize;
   }

   public String getIsFree() {
      return isFree;
   }

   public void setIsFree(String isFree) {
      this.isFree = isFree;
   }

   public String getIsVipFree() {
      return isVipFree;
   }

   public void setIsVipFree(String isVipFree) {
      this.isVipFree = isVipFree;
   }
}
