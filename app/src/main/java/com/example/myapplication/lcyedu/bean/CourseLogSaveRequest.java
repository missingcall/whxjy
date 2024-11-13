package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


/*
*   private Date gmtCreate;

    private Long courseId;

    private String courseName;

    private Long chapterId;

    private String chapterName;

    private Long periodId;

    private String periodName;

    private Long userNo;
*
* */
@Deprecated
public class CourseLogSaveRequest extends BaseRequest{

   @SerializedName("beginGmtCreate")
   private String beginGmtCreate;
   @SerializedName("chapterId")
   private Integer chapterId;
   @SerializedName("courseId")
   private Integer courseId;
   @SerializedName("endGmtCreate")
   private String endGmtCreate;
   @SerializedName("gmtCreate")
   private String gmtCreate;
   @SerializedName("id")
   private Integer id;
   @SerializedName("pageCurrent")
   private Integer pageCurrent;
   @SerializedName("pageSize")
   private Integer pageSize;
   @SerializedName("periodId")
   private Integer periodId;
   @SerializedName("userNo")
   private Integer userNo;

   public String getBeginGmtCreate() {
      return beginGmtCreate;
   }

   public void setBeginGmtCreate(String beginGmtCreate) {
      this.beginGmtCreate = beginGmtCreate;
   }

   public Integer getChapterId() {
      return chapterId;
   }

   public void setChapterId(Integer chapterId) {
      this.chapterId = chapterId;
   }

   public Integer getCourseId() {
      return courseId;
   }

   public void setCourseId(Integer courseId) {
      this.courseId = courseId;
   }

   public String getEndGmtCreate() {
      return endGmtCreate;
   }

   public void setEndGmtCreate(String endGmtCreate) {
      this.endGmtCreate = endGmtCreate;
   }

   public String getGmtCreate() {
      return gmtCreate;
   }

   public void setGmtCreate(String gmtCreate) {
      this.gmtCreate = gmtCreate;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
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

   public Integer getPeriodId() {
      return periodId;
   }

   public void setPeriodId(Integer periodId) {
      this.periodId = periodId;
   }

   public Integer getUserNo() {
      return userNo;
   }

   public void setUserNo(Integer userNo) {
      this.userNo = userNo;
   }
}
