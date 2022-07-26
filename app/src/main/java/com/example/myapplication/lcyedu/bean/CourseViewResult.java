package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CourseViewResult implements Serializable {

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

    public static class DataDTO implements Serializable {
        @SerializedName("id")
        private String id;
        @SerializedName("lecturerUserNo")
        private String lecturerUserNo;
        @SerializedName("courseName")
        private String courseName;
        @SerializedName("courseLogo")
        private String courseLogo;
        @SerializedName("introduceId")
        private String introduceId;
        @SerializedName("isFree")
        private Integer isFree;
        @SerializedName("courseOriginal")
        private Integer courseOriginal;
        @SerializedName("courseDiscount")
        private Integer courseDiscount;
        @SerializedName("countBuy")
        private Integer countBuy;
        @SerializedName("countStudy")
        private Integer countStudy;
        @SerializedName("periodTotal")
        private Integer periodTotal;
        @SerializedName("introduce")
        private String introduce;
        @SerializedName("lecturer")
        private LecturerDTO lecturer;
        @SerializedName("chapterList")
        private List<ChapterListDTO> chapterList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLecturerUserNo() {
            return lecturerUserNo;
        }

        public void setLecturerUserNo(String lecturerUserNo) {
            this.lecturerUserNo = lecturerUserNo;
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

        public String getIntroduceId() {
            return introduceId;
        }

        public void setIntroduceId(String introduceId) {
            this.introduceId = introduceId;
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

        public Integer getCountBuy() {
            return countBuy;
        }

        public void setCountBuy(Integer countBuy) {
            this.countBuy = countBuy;
        }

        public Integer getCountStudy() {
            return countStudy;
        }

        public void setCountStudy(Integer countStudy) {
            this.countStudy = countStudy;
        }

        public Integer getPeriodTotal() {
            return periodTotal;
        }

        public void setPeriodTotal(Integer periodTotal) {
            this.periodTotal = periodTotal;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public LecturerDTO getLecturer() {
            return lecturer;
        }

        public void setLecturer(LecturerDTO lecturer) {
            this.lecturer = lecturer;
        }

        public List<ChapterListDTO> getChapterList() {
            return chapterList;
        }

        public void setChapterList(List<ChapterListDTO> chapterList) {
            this.chapterList = chapterList;
        }

        public static class LecturerDTO implements Serializable {
            @SerializedName("id")
            private String id;
            @SerializedName("gmtCreate")
            private String gmtCreate;
            @SerializedName("gmtModified")
            private String gmtModified;
            @SerializedName("statusId")
            private Integer statusId;
            @SerializedName("sort")
            private Integer sort;
            @SerializedName("lecturerUserNo")
            private String lecturerUserNo;
            @SerializedName("lecturerName")
            private String lecturerName;
            @SerializedName("lecturerMobile")
            private String lecturerMobile;
            @SerializedName("lecturerEmail")
            private String lecturerEmail;
            @SerializedName("position")
            private Object position;
            @SerializedName("headImgUrl")
            private Object headImgUrl;
            @SerializedName("introduce")
            private String introduce;
            @SerializedName("lecturerProportion")
            private Double lecturerProportion;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(String gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public String getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(String gmtModified) {
                this.gmtModified = gmtModified;
            }

            public Integer getStatusId() {
                return statusId;
            }

            public void setStatusId(Integer statusId) {
                this.statusId = statusId;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getLecturerUserNo() {
                return lecturerUserNo;
            }

            public void setLecturerUserNo(String lecturerUserNo) {
                this.lecturerUserNo = lecturerUserNo;
            }

            public String getLecturerName() {
                return lecturerName;
            }

            public void setLecturerName(String lecturerName) {
                this.lecturerName = lecturerName;
            }

            public String getLecturerMobile() {
                return lecturerMobile;
            }

            public void setLecturerMobile(String lecturerMobile) {
                this.lecturerMobile = lecturerMobile;
            }

            public String getLecturerEmail() {
                return lecturerEmail;
            }

            public void setLecturerEmail(String lecturerEmail) {
                this.lecturerEmail = lecturerEmail;
            }

            public Object getPosition() {
                return position;
            }

            public void setPosition(Object position) {
                this.position = position;
            }

            public Object getHeadImgUrl() {
                return headImgUrl;
            }

            public void setHeadImgUrl(Object headImgUrl) {
                this.headImgUrl = headImgUrl;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public Double getLecturerProportion() {
                return lecturerProportion;
            }

            public void setLecturerProportion(Double lecturerProportion) {
                this.lecturerProportion = lecturerProportion;
            }
        }

        public static class ChapterListDTO implements Serializable {
            @SerializedName("id")
            private String id;
            @SerializedName("statusId")
            private Integer statusId;
            @SerializedName("courseId")
            private String courseId;
            @SerializedName("chapterName")
            private String chapterName;
            @SerializedName("chapterDesc")
            private Object chapterDesc;
            @SerializedName("isFree")
            private Integer isFree;
            @SerializedName("chapterOriginal")
            private Object chapterOriginal;
            @SerializedName("chapterDiscount")
            private Object chapterDiscount;
            @SerializedName("periodList")
            private List<PeriodListDTO> periodList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getStatusId() {
                return statusId;
            }

            public void setStatusId(Integer statusId) {
                this.statusId = statusId;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public Object getChapterDesc() {
                return chapterDesc;
            }

            public void setChapterDesc(Object chapterDesc) {
                this.chapterDesc = chapterDesc;
            }

            public Integer getIsFree() {
                return isFree;
            }

            public void setIsFree(Integer isFree) {
                this.isFree = isFree;
            }

            public Object getChapterOriginal() {
                return chapterOriginal;
            }

            public void setChapterOriginal(Object chapterOriginal) {
                this.chapterOriginal = chapterOriginal;
            }

            public Object getChapterDiscount() {
                return chapterDiscount;
            }

            public void setChapterDiscount(Object chapterDiscount) {
                this.chapterDiscount = chapterDiscount;
            }

            public List<PeriodListDTO> getPeriodList() {
                return periodList;
            }

            public void setPeriodList(List<PeriodListDTO> periodList) {
                this.periodList = periodList;
            }

            public static class PeriodListDTO implements Serializable {
                @SerializedName("id")
                private String id;
                @SerializedName("statusId")
                private Integer statusId;
                @SerializedName("courseId")
                private String courseId;
                @SerializedName("chapterId")
                private String chapterId;
                @SerializedName("periodName")
                private String periodName;
                @SerializedName("periodDesc")
                private Object periodDesc;
                @SerializedName("isFree")
                private Integer isFree;
                @SerializedName("periodOriginal")
                private Integer periodOriginal;
                @SerializedName("periodDiscount")
                private Integer periodDiscount;
                @SerializedName("countBuy")
                private Integer countBuy;
                @SerializedName("countStudy")
                private Integer countStudy;
                @SerializedName("isDoc")
                private Integer isDoc;
                @SerializedName("docName")
                private Object docName;
                @SerializedName("docUrl")
                private Object docUrl;
                @SerializedName("isVideo")
                private String isVideo;
                @SerializedName("videoNo")
                private String videoNo;
                @SerializedName("videoName")
                private String videoName;
                @SerializedName("videoLength")
                private String videoLength;
                @SerializedName("videoVid")
                private String videoVid;
                @SerializedName("videoOasId")
                private Object videoOasId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public Integer getStatusId() {
                    return statusId;
                }

                public void setStatusId(Integer statusId) {
                    this.statusId = statusId;
                }

                public String getCourseId() {
                    return courseId;
                }

                public void setCourseId(String courseId) {
                    this.courseId = courseId;
                }

                public String getChapterId() {
                    return chapterId;
                }

                public void setChapterId(String chapterId) {
                    this.chapterId = chapterId;
                }

                public String getPeriodName() {
                    return periodName;
                }

                public void setPeriodName(String periodName) {
                    this.periodName = periodName;
                }

                public Object getPeriodDesc() {
                    return periodDesc;
                }

                public void setPeriodDesc(Object periodDesc) {
                    this.periodDesc = periodDesc;
                }

                public Integer getIsFree() {
                    return isFree;
                }

                public void setIsFree(Integer isFree) {
                    this.isFree = isFree;
                }

                public Integer getPeriodOriginal() {
                    return periodOriginal;
                }

                public void setPeriodOriginal(Integer periodOriginal) {
                    this.periodOriginal = periodOriginal;
                }

                public Integer getPeriodDiscount() {
                    return periodDiscount;
                }

                public void setPeriodDiscount(Integer periodDiscount) {
                    this.periodDiscount = periodDiscount;
                }

                public Integer getCountBuy() {
                    return countBuy;
                }

                public void setCountBuy(Integer countBuy) {
                    this.countBuy = countBuy;
                }

                public Integer getCountStudy() {
                    return countStudy;
                }

                public void setCountStudy(Integer countStudy) {
                    this.countStudy = countStudy;
                }

                public Integer getIsDoc() {
                    return isDoc;
                }

                public void setIsDoc(Integer isDoc) {
                    this.isDoc = isDoc;
                }

                public Object getDocName() {
                    return docName;
                }

                public void setDocName(Object docName) {
                    this.docName = docName;
                }

                public Object getDocUrl() {
                    return docUrl;
                }

                public void setDocUrl(Object docUrl) {
                    this.docUrl = docUrl;
                }

                public String getIsVideo() {
                    return isVideo;
                }

                public void setIsVideo(String isVideo) {
                    this.isVideo = isVideo;
                }

                public String getVideoNo() {
                    return videoNo;
                }

                public void setVideoNo(String videoNo) {
                    this.videoNo = videoNo;
                }

                public String getVideoName() {
                    return videoName;
                }

                public void setVideoName(String videoName) {
                    this.videoName = videoName;
                }

                public String getVideoLength() {
                    return videoLength;
                }

                public void setVideoLength(String videoLength) {
                    this.videoLength = videoLength;
                }

                public String getVideoVid() {
                    return videoVid;
                }

                public void setVideoVid(String videoVid) {
                    this.videoVid = videoVid;
                }

                public Object getVideoOasId() {
                    return videoOasId;
                }

                public void setVideoOasId(Object videoOasId) {
                    this.videoOasId = videoOasId;
                }
            }
        }
    }
}
