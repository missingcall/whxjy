package com.example.myapplication.lcyedu.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyv.mediasdk.player.IjkMediaPlayer;
import com.easefun.polyvsdk.ijk.PolyvPlayerScreenRatio;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.video.listener.IPolyvOnPlayPauseListener;
import com.easefun.polyvsdk.video.listener.IPolyvOnPreparedListener2;
import com.example.myapplication.EduAppcalition;
import com.example.myapplication.R;
import com.example.myapplication.lcyedu.Constants;
import com.example.myapplication.lcyedu.adapter.DetailCourseAdapter;
import com.example.myapplication.lcyedu.bean.BaseRequest;
import com.example.myapplication.lcyedu.bean.CourseLogSaveRequest;
import com.example.myapplication.lcyedu.bean.CourseSignRequest;
import com.example.myapplication.lcyedu.bean.CourseSignResult;
import com.example.myapplication.lcyedu.bean.CourseViewResult;
import com.example.myapplication.lcyedu.bean.DeviceCheckResult;
import com.example.myapplication.lcyedu.bean.LoginResult;
import com.example.myapplication.lcyedu.player.PolyvPlayerMediaController;
import com.example.myapplication.lcyedu.utils.ConnUtils;
import com.example.myapplication.lcyedu.utils.GSONUtils;
import com.example.myapplication.lcyedu.utils.Utils;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.progress.materialprogressbar.MaterialProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.easefun.polyvsdk.ijk.PolyvPlayerScreenRatio.AR_16_9_FIT_PARENT;

public class EduDetailActivity extends BaseActivity implements Callback {

    private static final String TAG = EduDetailActivity.class.getSimpleName();
    @BindView(R.id.polyv_video_view)
    PolyvVideoView mPolyvVideoView;
    @BindView(R.id.rv_course_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_parent)
    RelativeLayout mRvParent;
    @BindView(R.id.video_prepare_pb)
    MaterialProgressBar mProgressBar;
    @BindView(R.id.video_prepare_tv)
    TextView mTextView;

    //章节条目
    List<CourseViewResult.DataDTO.ChapterListDTO> mChapterList = new ArrayList<CourseViewResult.DataDTO.ChapterListDTO>();
    //课时条目
    List<CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO> mPeriodList = new ArrayList<CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO>();
    //保存所有条目的list
    ArrayList mAllList = new ArrayList();

    //当前播放的vid
    public String mVid = "";
    private DetailCourseAdapter mAdapter;
    private PolyvPlayerMediaController mPolyvBaseMediaController;
    private AlertDialog.Builder mNormalDialog;
    private AlertDialog mAlertDialog;
    private String mVideoVid;


    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO 在此处添加执行的代码
            BaseRequest baseRequest = new BaseRequest();
            ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.DEVICE_CHECK), baseRequest, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: DEVICE_CHECK");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String bodyString = response.body().string();
                    Log.d(TAG, "device_check code bodyString :  " + bodyString);
                    DeviceCheckResult deviceCheckResult = GSONUtils.GSON.fromJson(bodyString, DeviceCheckResult.class);
                    Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "deviceCheckResult code :  " + deviceCheckResult.getCode() + " , msg : " + deviceCheckResult.getMsg());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (deviceCheckResult.getCode() == 200) {
                                //成功不做操作
                            } else if (deviceCheckResult.getCode() >= 301 && deviceCheckResult.getCode() <= 399) {
                                Toast.makeText(EduDetailActivity.this, "msg: " + deviceCheckResult.getMsg(), Toast.LENGTH_LONG).show();
                                //退出登录状态
                                finish();
                                Intent intent = new Intent(EduDetailActivity.this, EduLoginActivity.class);
                                startActivity(intent);
                            } else {
/*                    Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "onResponse: "
                            + courseSignRequest.getCode()
                            + "   请求失败  弹出弹窗");*/
                                showNormalDialog(deviceCheckResult.getMsg());
                            }

                        }
                    });
                }
            }, true);
            handler.postDelayed(this, 10000);// 50是延时时长
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_detail);
        ButterKnife.bind(this);

        initData();
        initView();
        initListeners();

    }

    private void initView() {
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new DetailCourseAdapter(mAllList));
        mAdapter.setSelectPosition(-1);
        mPolyvBaseMediaController = findViewById(R.id.polyv_player_media_controller);
        mPolyvBaseMediaController.initConfig(mRvParent);

//        mPolyvVideoView.getIjkMediaPlayer().setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "reconnect", 1);
        mPolyvVideoView.setMediaController(mPolyvBaseMediaController);
        mPolyvVideoView.setAspectRatio(PolyvPlayerScreenRatio.AR_ASPECT_FILL_PARENT);

        mPolyvBaseMediaController.setAdapter(mAdapter);

        mPolyvVideoView.setOnPreparedListener(new IPolyvOnPreparedListener2() {
            @Override
            public void onPrepared() {
                mPolyvBaseMediaController.preparedView();
                //视频准备完毕,进度条消失
                mProgressBar.setVisibility(View.GONE);
                mTextView.setVisibility(View.GONE);
                mPolyvVideoView.getIjkMediaPlayer().setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "reconnect", 1);
            }
        });

        //视频播放、暂停、播放完成回调，主线程中回调
        mPolyvVideoView.setOnPlayPauseListener(new IPolyvOnPlayPauseListener() {
            @Override
            public void onPause() {
                Log.d(TAG, "onPause: ");
                //停止发送校验
                handler.removeCallbacks(runnable);// 关闭定时器处理
            }

            @Override
            public void onPlay() {
                Log.d(TAG, "onPlay: ");
                //开始发送校验
                handler.postDelayed(runnable, 10000);// 打开定时器，执行操作
            }

            @Override
            public void onCompletion() {
                Log.d(TAG, "onCompletion: ");
                handler.removeCallbacks(runnable);// 关闭定时器处理
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        CourseViewResult courseViewResult = (CourseViewResult) intent.getSerializableExtra(Constants.INTENT_MAIN_TO_DETAIL);
        //获取章节列表
        if (courseViewResult == null
                || courseViewResult.getData() == null
                || courseViewResult.getData().getChapterList() == null
                || courseViewResult.getData().getChapterList().size() == 0) {
            return;
        }
        mChapterList = courseViewResult.getData().getChapterList();
        for (int i = 0; i < mChapterList.size(); i++) {
            CourseViewResult.DataDTO.ChapterListDTO chapter = courseViewResult.getData().getChapterList().get(i);
            mAllList.add(chapter);
//            Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "章节: " + chapter.getChapterName());
            for (CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO period : chapter.getPeriodList()) {
                mAllList.add(period);
//                Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "     课程: " + period.getPeriodName());
            }
        }


        //默认播放第一个
//        mVid = mChapterList.get(0).getPeriodList().get(0).getVideoVid();
//        mPolyvVideoView.setVid(mVid);

    }


    @Override
    protected void initListeners() {
        mAdapter.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int position) {
                mProgressBar.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.VISIBLE);

                mAdapter.setSelectPosition(position);
                CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO periodListDTO = (CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO) mAllList.get(position);
                mVideoVid = periodListDTO.getVideoVid();
                String periodName = periodListDTO.getPeriodName();
//                Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, periodName + " vid : " + mVideoVid);


                //调用接口记录观看记录
                requestSign(position);
            }
        });


    }

    private void requestSign(int position) {
        CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO period = (CourseViewResult.DataDTO.ChapterListDTO.PeriodListDTO) mAllList.get(position);
        CourseSignRequest courseSignRequest = new CourseSignRequest();
        courseSignRequest.setIp("string");
        courseSignRequest.setPeriodId(period.getId());
        courseSignRequest.setUserNo(EduAppcalition.userNo);
        courseSignRequest.setVideoVid(period.getVideoVid());


        Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "提交的sign: " + period.getId() + " , " + EduAppcalition.userNo + " , " + courseSignRequest.getVideoVid() + "\n" + courseSignRequest);
        ConnUtils.postJson(ConnUtils.getWebUrl(Constants.BASE_ADDRESS + Constants.COURSE_SIGN), courseSignRequest, this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPolyvVideoView.pause();
        mPolyvVideoView.destroy();
        mPolyvBaseMediaController.disable();
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String bodyString = response.body().string();
        Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "courseSignResult code bodyString :  " + bodyString);
        CourseSignResult courseSignResult = GSONUtils.GSON.fromJson(bodyString, CourseSignResult.class);
        Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "courseSignResult code :  " + courseSignResult.getCode() + " , msg : " + courseSignResult.getMsg());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (courseSignResult.getCode() == 200) {
//                    Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "onResponse: 200   请求成功  开始播放  " + courseSignResult.getMsg());
                    //根据点击的条目播放视频
                    mPolyvVideoView.pause();
                    mPolyvVideoView.setVid(mVideoVid);
                    mPolyvVideoView.start();
//                    showNormalDialog(courseSignResult.getMsg());
                } else if (courseSignResult.getCode() >= 301 && courseSignResult.getCode() <= 399) {
                    Toast.makeText(EduDetailActivity.this, "msg: " + courseSignResult.getMsg(), Toast.LENGTH_LONG).show();
                    //退出登录状态
                    handler.removeCallbacks(runnable);// 关闭定时器处理
                    finish();
                    Intent intent = new Intent(EduDetailActivity.this, EduLoginActivity.class);
                    startActivity(intent);
                } else {
/*                    Log.d(Constants.EDU_DETAIL_ACTIVITY_LOG, "onResponse: "
                            + courseSignResult.getCode()
                            + "   请求失败  弹出弹窗");*/
                    showNormalDialog(courseSignResult.getMsg());
                }
            }
        });
    }

    private void showNormalDialog(String msg) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        mNormalDialog = new AlertDialog.Builder(this);
        mNormalDialog.setIcon(R.mipmap.launcher_icon)
                .setTitle(getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .show();
    }


}