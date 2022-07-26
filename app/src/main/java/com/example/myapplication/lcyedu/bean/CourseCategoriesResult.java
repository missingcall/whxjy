package com.example.myapplication.lcyedu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseCategoriesResult {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CourseCategoriesResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
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
        @SerializedName("courseCategoryList")
        private List<CourseCategoryListDTO> courseCategoryList;

        public List<CourseCategoryListDTO> getCourseCategoryList() {
            return courseCategoryList;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "courseCategoryList=" + courseCategoryList +
                    '}';
        }

        public void setCourseCategoryList(List<CourseCategoryListDTO> courseCategoryList) {
            this.courseCategoryList = courseCategoryList;
        }

        //班级列表
        public static class CourseCategoryListDTO {
            @Override
            public String toString() {
                return "CourseCategoryListDTO{" +
                        "id='" + id + '\'' +
                        ", categoryName='" + categoryName + '\'' +
                        ", floor=" + floor +
                        ", remark=" + remark +
                        ", twoList=" + twoList +
                        '}';
            }

            @SerializedName("id")
            private String id;
            @SerializedName("categoryName")
            private String categoryName;
            @SerializedName("floor")
            private Integer floor;
            @SerializedName("remark")
            private Object remark;
            @SerializedName("twoList")
            private List<TwoListDTO> twoList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public Integer getFloor() {
                return floor;
            }

            public void setFloor(Integer floor) {
                this.floor = floor;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public List<TwoListDTO> getTwoList() {
                return twoList;
            }

            public void setTwoList(List<TwoListDTO> twoList) {
                this.twoList = twoList;
            }

            //年级列表
            public static class TwoListDTO {
                @SerializedName("id")
                private String id;
                @SerializedName("categoryName")
                private String categoryName;

                @Override
                public String toString() {
                    return "TwoListDTO{" +
                            "id='" + id + '\'' +
                            ", categoryName='" + categoryName + '\'' +
                            ", floor=" + floor +
                            ", remark=" + remark +
                            ", threeList=" + threeList +
                            '}';
                }

                @SerializedName("floor")
                private Integer floor;
                @SerializedName("remark")
                private Object remark;
                @SerializedName("threeList")
                private List<ThreeListDTO> threeList;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public Integer getFloor() {
                    return floor;
                }

                public void setFloor(Integer floor) {
                    this.floor = floor;
                }

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public List<ThreeListDTO> getThreeList() {
                    return threeList;
                }

                public void setThreeList(List<ThreeListDTO> threeList) {
                    this.threeList = threeList;
                }

                //科目列表
                public static class ThreeListDTO {
                    @SerializedName("id")
                    private String id;
                    @SerializedName("categoryName")
                    private String categoryName;
                    @SerializedName("floor")
                    private Integer floor;

                    @Override
                    public String toString() {
                        return "ThreeListDTO{" +
                                "id='" + id + '\'' +
                                ", categoryName='" + categoryName + '\'' +
                                ", floor=" + floor +
                                ", remark=" + remark +
                                '}';
                    }

                    @SerializedName("remark")
                    private Object remark;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getCategoryName() {
                        return categoryName;
                    }

                    public void setCategoryName(String categoryName) {
                        this.categoryName = categoryName;
                    }

                    public Integer getFloor() {
                        return floor;
                    }

                    public void setFloor(Integer floor) {
                        this.floor = floor;
                    }

                    public Object getRemark() {
                        return remark;
                    }

                    public void setRemark(Object remark) {
                        this.remark = remark;
                    }
                }
            }
        }
    }
}
