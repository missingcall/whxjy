package com.example.myapplication.lcyedu;

/*需要用到的常量类*/
public class Constants {
    //基础网址
    public static final String BASE_ADDRESS = "http://123.56.121.190";
    //登录后缀
    public static final String LOGIN_ADDRESS = "/user/api/user/login/password";
    //更新
    public static final String UPDATE_ADDRESS = "/user/api/user/app/update";
    //课程分类列表后缀
    public static final String COURSE_CATEGORIES_ADDRESS = "/course/api/course/category/list";
    //课程列表后缀
    public static final String COURSE_LIST_ADDRESS = "/course/api/course/list";
    //课程详情
    public static final String COURSE_LIST_VIEW = "/course/api/course/view";
    //校验设备
    public static final String DEVICE_CHECK = "/course/auth/course/device/check";
    //提交log
    public static final String COURSE_LOG = "/feign/course/courseUserStudyLog/save";
    //sign
    public static final String COURSE_SIGN = "/course/auth/course/sign";

    public static final String CLIENT_ID = "lkb65617f842ad4c37895a733b8de43cbb";
    public static final String IP = "";

    public static final String EDU_LOGIN_ACTIVITY_LOG = "EDU_LOGIN_ACTIVITY_LOG";
    public static final String EDU_MAIN_ACTIVITY_LOG = "EDU_MAIN_ACTIVITY_LOG";
    public static final String EDU_DETAIL_ACTIVITY_LOG = "EDU_DETAIL_ACTIVITY_LOG";

    public static final String INTENT_MAIN_TO_DETAIL = "courseViewResult";
}
