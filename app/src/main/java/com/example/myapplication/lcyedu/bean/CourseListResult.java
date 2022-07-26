package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseListResult {

   @SerializedName("code")
   private Integer code;
   @SerializedName("msg")
   private String msg;
   @SerializedName("data")
   private DataDTO data;

   public Integer getCode() {
      return code;
   }

   public void setCode(Integer code) {
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
      @SerializedName("totalCount")
      private Integer totalCount;
      @SerializedName("totalPage")
      private Integer totalPage;
      @SerializedName("pageCurrent")
      private Integer pageCurrent;
      @SerializedName("pageSize")
      private Integer pageSize;
      @SerializedName("list")
      private List<ListDTO> list;

      public Integer getTotalCount() {
         return totalCount;
      }

      public void setTotalCount(Integer totalCount) {
         this.totalCount = totalCount;
      }

      public Integer getTotalPage() {
         return totalPage;
      }

      public void setTotalPage(Integer totalPage) {
         this.totalPage = totalPage;
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

      public List<ListDTO> getList() {
         return list;
      }

      public void setList(List<ListDTO> list) {
         this.list = list;
      }

      public static class ListDTO {
         @SerializedName("id")
         private String id;
         @SerializedName("courseName")
         private String courseName;
         @SerializedName("courseLogo")
         private String courseLogo;
         @SerializedName("isFree")
         private Integer isFree;
         @SerializedName("courseOriginal")
         private Integer courseOriginal;
         @SerializedName("courseDiscount")
         private Integer courseDiscount;

         public String getId() {
            return id;
         }

         public void setId(String id) {
            this.id = id;
         }

         public String getCourseName() {
            return courseName;
         }

         public void setCourseName(String courseName) {
            this.courseName = courseName;
         }

         public String getCourseLogo() {
            return courseLogo;
         }

         public void setCourseLogo(String courseLogo) {
            this.courseLogo = courseLogo;
         }

         public Integer getIsFree() {
            return isFree;
         }

         public void setIsFree(Integer isFree) {
            this.isFree = isFree;
         }

         public Integer getCourseOriginal() {
            return courseOriginal;
         }

         public void setCourseOriginal(Integer courseOriginal) {
            this.courseOriginal = courseOriginal;
         }

         public Integer getCourseDiscount() {
            return courseDiscount;
         }

         public void setCourseDiscount(Integer courseDiscount) {
            this.courseDiscount = courseDiscount;
         }
      }
   }
}
