package com.example.myapplication.lcyedu.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.lcyedu.Constants;
import com.example.myapplication.lcyedu.adapter.FlexboxLayoutAdapter;
import com.example.myapplication.lcyedu.adapter.ItemAdapter;
import com.example.myapplication.lcyedu.bean.CourseCategoriesResult;
import com.example.myapplication.lcyedu.bean.CourseListRequest;
import com.example.myapplication.lcyedu.bean.CourseListResult;
import com.example.myapplication.lcyedu.bean.CourseViewRequest;
import com.example.myapplication.lcyedu.bean.CourseViewResult;
import com.example.myapplication.lcyedu.utils.ConnUtils;
import com.example.myapplication.lcyedu.utils.GSONUtils;
import com.example.myapplication.lcyedu.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//进入Activity后请求一次数据  基本就不用更新了
public class EduMainActivity extends BaseActivity {

    @BindView(R.id.rv_class)
    RecyclerView mRecyclerView_Class;
    @BindView(R.id.rv_grade)
    RecyclerView mRecyclerView_Grade;
    @BindView(R.id.rv_course)
    RecyclerView mRecyclerView_Course;
    @BindView(R.id.rv_item)
    RecyclerView mRecyclerView_Item;


    private FlexboxLayoutAdapter mAdapterClass;
    private FlexboxLayoutAdapter mAdapterGrade;
    private FlexboxLayoutAdapter mAdapterCourse;
    private ItemAdapter mAdapterItem;


    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO> mListClass;
    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO> mListGrade;
    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO.ThreeListDTO> mListCourse;

    private int mClassPostion = 0;
    private int mGradePostion = 0;
    private int mCoursePostion = 0;

    private List<String> mListClassString = new ArrayList<>();
    private List<String> mListGradeString = new ArrayList<>();
    private List<String> mListCourseString = new ArrayList<>();
    private List<CourseListResult.DataDTO.ListDTO> mListItem = new ArrayList<>();
    private List<String> mListItemID = new ArrayList<>();


    //进入界面的请求回调
    Callback mCallbackFirst = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackFirst 请求失败: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String resp = response.body().string();
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackFirst body: " + resp);
            CourseCategoriesResult courseCategoriesResult = GSONUtils.GSON.fromJson(resp, CourseCategoriesResult.class);
            if (courseCategoriesResult.getCode() == 200) {
//                Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackFirst请求成功,body: " + GSONUtils.GSON.toJson(courseCategoriesResult));
                initDataFromNet(courseCategoriesResult);

                //在主线程中初始化界面
                runOnUiThread(() -> {
                    initView();
                    initListeners();
                });

            }
        }
    };


    //班级列表的请求的回调
    Callback mCallbackForClassList = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList 请求失败: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String resp = response.body().string();
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList body: " + resp);
            CourseListResult courseListResult = GSONUtils.GSON.fromJson(resp, CourseListResult.class);
            if (courseListResult.getCode() == 200) {
//                Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList 请求成功,body: " + GSONUtils.GSON.toJson(courseListResult));
                List<CourseListResult.DataDTO.ListDTO> list = courseListResult.getData().getList();
                updateItems(list);
                runOnUiThread(() -> {
                    //刷新item界面
                    mAdapterItem.refresh(mListItem);
                });

            } else if (courseListResult.getCode() >= 301 && courseListResult.getCode() <= 399) {
                Toast.makeText(EduMainActivity.this, "msg: " + courseListResult.getMsg(), Toast.LENGTH_LONG).show();
                //退出登录状态
                finish();
                Intent intent = new Intent(EduMainActivity.this, EduLoginActivity.class);
                startActivity(intent);
            }
        }
    };


    //年级列表的请求的回调
    Callback mCallbackForGradeList = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList 请求失败: " + e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String resp = response.body().string();
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList body: " + resp);
            CourseListResult courseListResult = GSONUtils.GSON.fromJson(resp, CourseListResult.class);
            if (courseListResult.getCode() == 200) {
//                Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "mCallbackForCourseList 请求成功,body: " + GSONUtils.GSON.toJson(courseListResult));
                List<CourseListResult.DataDTO.ListDTO> list = courseListResult.getData().getList();
                updateItems(list);
                runOnUiThread(() -> {
                    //刷新item界面
                    mAdapterItem.refresh(mListItem);
                });

            }
        }
    };

    Callback mItemPostCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String resp = response.body().string();
            CourseViewResult courseViewResult = GSONUtils.GSON.fromJson(resp, CourseViewResult.class);
            runOnUiThread(() -> {
                //跳转到播放页
                Intent intent = new Intent(EduMainActivity.this, EduDetailActivity.class);
                intent.putExtra(Constants.INTENT_MAIN_TO_DETAIL, courseViewResult);
                startActivity(intent);
            });
        }
    };


    private void updateItems(List<CourseListResult.DataDTO.ListDTO> list) {
        mListItemID.clear();
        //每次刷新前都清楚
        mListItem.clear();
        for (int i = 0; i < list.size(); i++) {
            String courseName = list.get(i).getCourseName();
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "updateItems: " + courseName);
            mListItem.add(list.get(i));
            mListItemID.add(list.get(i).getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edu_main);
        ButterKnife.bind(this);

//        initView();
        //请求课程列表
        ConnUtils.post(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_CATEGORIES_ADDRESS), "", mCallbackFirst, true);

    }

    private void initView() {

        mRecyclerView_Class.setLayoutManager(Utils.getFlexboxLayoutManager(this));
        mRecyclerView_Grade.setLayoutManager(Utils.getFlexboxLayoutManager(this));
        mRecyclerView_Course.setLayoutManager(Utils.getFlexboxLayoutManager(this));
        mRecyclerView_Item.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));

        mRecyclerView_Class.setAdapter(mAdapterClass = new FlexboxLayoutAdapter(mListClassString));
        mRecyclerView_Grade.setAdapter(mAdapterGrade = new FlexboxLayoutAdapter(mListGradeString));
        mRecyclerView_Course.setAdapter(mAdapterCourse = new FlexboxLayoutAdapter(mListCourseString));
        mRecyclerView_Item.setAdapter(mAdapterItem = new ItemAdapter(mListItem));


    }


    //从服务器初始化数据
    private void initDataFromNet(CourseCategoriesResult courseCategoriesResult) {

        mListClass = getClassList(courseCategoriesResult);
        for (int i = 0; i < mListClass.size(); i++) {
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "班级 : " + mListClass.get(i).getCategoryName().toString());
            mListClassString.add(mListClass.get(i).getCategoryName());
            mListGrade = getGradeList(courseCategoriesResult, i);
            for (int j = 0; j < mListGrade.size(); j++) {
//                Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "--年级 : " + mListGrade.get(j).getCategoryName().toString());
//                mListGradeString.add(mListGrade.get(j).getCategoryName());
                mListCourse = getCourseList(courseCategoriesResult, i, j);
                for (int k = 0; k < mListCourse.size(); k++) {
//                    Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, "----学科 : " + mListCourse.get(k).getCategoryName().toString());
//                    mListCourseString.add(mListCourse.get(k).getCategoryName());
                    //最后是Item
//                    mListItemString.add
                }
            }
        }

    }


    //返回课程分类一级目录什么班的集合
    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO> getClassList(CourseCategoriesResult courseCategoriesResult) {
        return courseCategoriesResult.getData().getCourseCategoryList();
    }

    //输入一级目录,查看二级目录年级数量
    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO> getGradeList(CourseCategoriesResult courseCategoriesResult, int classNumber) {
        return courseCategoriesResult.getData().getCourseCategoryList().get(classNumber).getTwoList();
    }

    //输入一级目录和耳机目录,查看三级目录数量
    private List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO.ThreeListDTO> getCourseList(CourseCategoriesResult courseCategoriesResult, int classNumber, int gradeNumber) {
        return courseCategoriesResult.getData().getCourseCategoryList().get(classNumber).getTwoList().get(gradeNumber).getThreeList();
    }


    @Override
    protected void initListeners() {
        mAdapterClass.setOnItemClickListener((itemView, item, position) -> {
            //设置选中状态
            mAdapterClass.select(position);
            //记录当前点击的一级页面postion
            mClassPostion = position;
            //班级目录  1.点哪个就通过id去请求这个班级对应的子列表  2.将请求到的子列表添加到mAdapterGrade  3.刷新二级界面及item界面
            String id = mListClass.get(position).getId();
            CourseListRequest courseListRequest = generateCourseBean(id, "", "");

            ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_LIST_ADDRESS), courseListRequest, mCallbackForClassList, true);
            setGradeList();
        });

        mAdapterGrade.setOnItemClickListener((itemView, item, position) -> {
            mAdapterGrade.select(position);
            //记录当前选中的二级页面
            mGradePostion = position;
            //年级目录 1.刷新三级界面及item  2.请求年级子列表
            CourseCategoriesResult.DataDTO.CourseCategoryListDTO dto = mListClass.get(mClassPostion);
            CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO twoListDTO = dto.getTwoList().get(position);

            CourseListRequest courseListRequest = generateCourseBean(dto.getId(), twoListDTO.getId(), "");

//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, Constants.BASE_ADDRESS + Constants.COURSE_LIST_ADDRESS + courseListRequest.getCategoryId1() +courseListRequest.getCategoryId2());
            ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_LIST_ADDRESS), courseListRequest, mCallbackForGradeList, true);
            setCourseList();
        });

        //科目点击事件处理
        mAdapterCourse.setOnItemClickListener((itemView, item, position) -> {
            mAdapterCourse.select(position);
            mCoursePostion = position;
            CourseCategoriesResult.DataDTO.CourseCategoryListDTO dto = mListClass.get(mClassPostion);
            CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO twoListDTO = dto.getTwoList().get(mGradePostion);
            CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO.ThreeListDTO threeListDTO = twoListDTO.getThreeList().get(position);

            CourseListRequest courseListRequest = generateCourseBean(dto.getId(), twoListDTO.getId(), threeListDTO.getId());

//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, Constants.BASE_ADDRESS + Constants.COURSE_LIST_ADDRESS + courseListRequest.getCategoryId1() +courseListRequest.getCategoryId2());
            ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_LIST_ADDRESS), courseListRequest, mCallbackForGradeList, true);

        });

        //条目点击时间
        mAdapterItem.setOnItemClickListener((itemView, item, position) -> {
            mAdapterItem.select(position);
            //获得item对应的id
            String name = mListItem.get(position).getCourseName();
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, " name : " + name);
            String id = mListItemID.get(position);
//            Log.d(Constants.EDU_MAIN_ACTIVITY_LOG, " id : " + id);
            CourseViewRequest courseViewRequest = generateCourseViewBean(id);

            ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_LIST_VIEW), courseViewRequest, mItemPostCallback, true);

        });
    }


    //根据点击的一级页面设置二级页面
    private void setGradeList() {
        //点击一级目录的时候设置二级目录可见,三级目录不可见
        mRecyclerView_Grade.setVisibility(View.VISIBLE);
        mRecyclerView_Course.setVisibility(View.GONE);
        mListGradeString.clear();
        CourseCategoriesResult.DataDTO.CourseCategoryListDTO dto = mListClass.get(mClassPostion);
        List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO> twoList = dto.getTwoList();
        for (int i = 0; i < twoList.size(); i++) {
            String categoryName = twoList.get(i).getCategoryName();
            mListGradeString.add(categoryName);
        }
        mAdapterGrade.refresh(mListGradeString);
    }

    //根据点击的二级页面设置三级页面
    private void setCourseList() {
        //点击er级目录的时候设置三级目录可见,三级目录不可见
        mRecyclerView_Course.setVisibility(View.VISIBLE);
        //记录当前点击的二级页面postion
        mListCourseString.clear();
        List<CourseCategoriesResult.DataDTO.CourseCategoryListDTO.TwoListDTO.ThreeListDTO> threeList = mListClass.get(mClassPostion).getTwoList().get(mGradePostion).getThreeList();
        for (int i = 0; i < threeList.size(); i++) {
            String categoryName = threeList.get(i).getCategoryName();
            mListCourseString.add(categoryName);
        }
        mAdapterCourse.refresh(mListCourseString);
    }

    private CourseListRequest generateCourseBean(String category1, String category2, String category3) {
        CourseListRequest courseListRequest = new CourseListRequest();
        courseListRequest.setCategoryId1(category1);
        courseListRequest.setCategoryId2(category2);
        courseListRequest.setCategoryId3(category3);
        courseListRequest.setOrgNo("lingke");
        courseListRequest.setPageCurrent(1);
        courseListRequest.setPageSize(1000);
        return courseListRequest;
    }

    private CourseViewRequest generateCourseViewBean(String id) {
        CourseViewRequest courseViewRequest = new CourseViewRequest();
        courseViewRequest.setCourseId(id);
        return courseViewRequest;
    }

}


