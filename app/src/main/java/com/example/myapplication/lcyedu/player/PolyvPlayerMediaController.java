package com.example.myapplication.lcyedu.player;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easefun.polyvsdk.ijk.PolyvPlayerScreenRatio;
import com.easefun.polyvsdk.video.IPolyvVideoView;
import com.easefun.polyvsdk.video.PolyvBaseMediaController;
import com.easefun.polyvsdk.video.PolyvVideoView;
import com.easefun.polyvsdk.vo.PolyvVideoVO;
import com.example.myapplication.R;
import com.example.myapplication.lcyedu.adapter.DetailCourseAdapter;
import com.example.myapplication.lcyedu.ui.view.PolyvTickSeekBar;
import com.example.myapplication.lcyedu.utils.PolyvKeyBoardUtils;
import com.example.myapplication.lcyedu.utils.PolyvSPUtils;
import com.example.myapplication.lcyedu.utils.PolyvScreenUtils;
import com.example.myapplication.lcyedu.utils.PolyvSensorHelper;
import com.example.myapplication.lcyedu.utils.PolyvTimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;

public class PolyvPlayerMediaController extends PolyvBaseMediaController implements View.OnClickListener {
    private static final String TAG = PolyvPlayerMediaController.class.getSimpleName();
    private Context mContext = null;
    private PolyvVideoView videoView = null;
    private PolyvVideoVO videoVO;
    //播放器所在的activity
    private Activity videoActivity;
    //播放器的ParentView
    private View parentView;
    //显示的状态
    private boolean isShowing;
    //控制栏显示的时间
    private static final int longTime = 5000;
    private static final int HIDE = 12;
    private static final int SHOW_PROGRESS = 13;
    //controllerView
    private View view;
    /**
     * 竖屏的view
     */
    // 竖屏的控制栏
    private RelativeLayout rl_port;
    // 竖屏的切屏按钮，竖屏的播放/暂停按钮
    private ImageView iv_land, iv_play;
    // 竖屏的显示播放进度控件，切换清晰度按钮，切换倍速按钮，切换线路按钮
    private TextView tv_curtime, tv_tottime, tv_speed_portrait;
    // 竖屏的进度条
    private SeekBar sb_play;
    /**
     * 竖屏的播放速度布局
     */
    //播放速度布局
    private RelativeLayout rl_center_speed_portrait;
    //选择播放速度控件
    private TextView tv_speed05_portrait, tv_speed10_portrait, tv_speed12_portrait, tv_speed15_portrait, tv_speed20_portrait;

    /**
     * 横屏的view
     */
    //横屏的控制栏，顶部布局，底部布局
    private RelativeLayout rl_land, rl_top, rl_bot;
    //横屏的切屏按钮，横屏的播放/暂停按钮,横屏的返回按钮，设置按钮，分享按钮，弹幕开关
    private ImageView iv_port, iv_play_land, iv_finish;
    // 横屏的显示播放进度控件,视频的标题,选择播放速度按钮，选择码率按钮，选择线路按钮
    private TextView tv_curtime_land, tv_tottime_land, tv_title, tv_speed;
    // 横屏的进度条
    private PolyvTickSeekBar sb_play_land;

    /**
     * 设置布局的view
     */
    //设置布局
    private RelativeLayout rl_center_set;
    //调节亮度控件，调节音量控件
    private SeekBar sb_light, sb_volume;
    // 设置播放器银幕比率控件，设置字幕的控件
    private LinearLayout ll_adaptive_mode, ll_subtitle, ll_subtitle_b;
    private TextView tv_full, tv_fit, tv_sixteennine, tv_fourthree, tv_srt1, tv_srt2, tv_srt3, tv_srtnone;
    // 关闭布局按钮
    private ImageView iv_close_set;

    private int color;
    /**
     * 横屏的播放速度布局
     */
    //播放速度布局
    private RelativeLayout rl_center_speed;
    //选择播放速度控件
    private TextView tv_speed05, tv_speed10, tv_speed12, tv_speed15, tv_speed20;
    //关闭布局按钮
    private ImageView iv_close_speed;

    /**
     * 横屏的播放线路布局
     */
    //-----------------------------------------
    // 进度条是否处于拖动的状态
    private boolean status_dragging;
    // 控制栏是否处于一直显示的状态
    private boolean status_showalways;
    // 播放器在显示弹幕布局前的状态
    private boolean status_isPlaying;
    private PolyvSensorHelper sensorHelper;


    private PictureInPictureParams.Builder pipBuilder;
    private boolean isViceHideInPipMode;

    //全屏策略
    private static final int FULLSCREEN_RATIO = 0;//根据视频宽高判断，当宽>=高时，使用横屏全屏
    private static final int FULLSCREEN_LANDSCAPE = 1;//使用横屏全屏
    private static final int FULLSCREEN_PORTRAIT = 2;//使用竖屏全屏
    private int fullScreenStrategy = FULLSCREEN_LANDSCAPE;
    private int videoWidth, videoHeight;
    private boolean isFullScreen;

    //进度条拖拽跳转播放进度策略
    public static final int DRAG_SEEK_ALLOW = 0;//允许拖动进度条跳转进度
    public static final int DRAG_SEEK_BAN = 1;//禁止拖动进度条跳转进度
    public static final int DRAG_SEEK_PLAYED = 2;//只允许在已播放进度区域拖动跳转播放进度
    private int dragSeekStrategy = DRAG_SEEK_PLAYED;
    private OnDragSeekListener onDragSeekListener;

    private static final int SAVE_PROGRESS = 30;

    //用于处理控制栏的显示状态
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE:
                    hide();
                    break;
                case SHOW_PROGRESS:
                    showProgress();
                    break;
                case SAVE_PROGRESS:
                    saveProgress();
                    break;
            }
        }
    };
    private int mVideoSizeWidth;
    private int mVideoSizeHeight;
    private DetailCourseAdapter mAdapter;

    private int getPosition() {
        if (videoView != null) {
            // 单位：毫秒
            int position = videoView.getCurrentPosition();
            int totalTime = videoView.getDuration() / 1000 * 1000;
            if (!videoView.isExceptionCompleted() && (videoView.isCompletedState() || position > totalTime))
                position = totalTime;
            return position;
        }
        return 0;
    }

    private void saveProgress() {
        if (videoView != null && videoView.getCurrentVid() != null) {
            // 单位：毫秒
            int position = getPosition();
            //保存当前播放进度
            int maxPosition = PolyvSPUtils.getInstance(getContext(), "videoProgress").getInt(videoView.getCurrentVid());
            if (position > maxPosition) {
                PolyvSPUtils.getInstance(getContext(), "videoProgress").put(videoView.getCurrentVid(), position);
            }
            handler.sendEmptyMessageDelayed(SAVE_PROGRESS, 3000);
        }
    }

    // 更新显示的播放进度，以及暂停/播放按钮
    private void showProgress() {
        if (isShowing && videoView != null) {
            // 单位：毫秒
            int position = getPosition();
            int totalTime = videoView.getDuration() / 1000 * 1000;
            int bufPercent = videoView.getBufferPercentage();
            //在拖动进度条的时候，这里不更新
            if (!status_dragging) {
                tv_curtime.setText(PolyvTimeUtils.generateTime(position));
                tv_curtime_land.setText(PolyvTimeUtils.generateTime(position));
                if (totalTime > 0) {
                    sb_play.setProgress((int) (sb_play.getMax() * 1L * position / totalTime));
                    sb_play_land.setProgress((int) (sb_play_land.getMax() * 1L * position / totalTime));
                } else {
                    sb_play.setProgress(0);
                    sb_play_land.setProgress(0);
                }
            }
            sb_play.setSecondaryProgress(sb_play.getMax() * bufPercent / 100);
            sb_play_land.setSecondaryProgress(sb_play_land.getMax() * bufPercent / 100);
            if (videoView.isPlaying()) {
                iv_play.setSelected(false);
                iv_play_land.setSelected(false);
            } else {
                iv_play.setSelected(true);
                iv_play_land.setSelected(true);
            }
            handler.sendMessageDelayed(handler.obtainMessage(SHOW_PROGRESS), 1000 - (position % 1000));
        }
    }

    public PolyvPlayerMediaController(Context context) {
        this(context, null);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PolyvPlayerMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.videoActivity = (Activity) mContext;
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.polyv_controller_media, this);
        findIdAndNew();
        initView();
    }

    /**
     * 初始化控制栏的配置
     *
     * @param parentView 播放器的父控件
     */
    public void initConfig(ViewGroup parentView) {
        this.parentView = parentView;
    }


    private void findIdAndNew() {
        //竖屏的view
        rl_port = (RelativeLayout) view.findViewById(R.id.rl_port);
        iv_land = (ImageView) view.findViewById(R.id.iv_land);
        iv_play = (ImageView) view.findViewById(R.id.iv_play);

        tv_curtime = (TextView) view.findViewById(R.id.tv_curtime);
        tv_tottime = (TextView) view.findViewById(R.id.tv_tottime);
        tv_speed_portrait = (TextView) view.findViewById(R.id.tv_speed_portrait);
        sb_play = (SeekBar) view.findViewById(R.id.sb_play);
        //竖屏的播放速度布局的view
        rl_center_speed_portrait = (RelativeLayout) view.findViewById(R.id.rl_center_speed_portrait);
        tv_speed05_portrait = (TextView) view.findViewById(R.id.tv_speed05_portrait);
        tv_speed10_portrait = (TextView) view.findViewById(R.id.tv_speed10_portrait);
        tv_speed12_portrait = (TextView) view.findViewById(R.id.tv_speed12_portrait);
        tv_speed15_portrait = (TextView) view.findViewById(R.id.tv_speed15_portrait);
        tv_speed20_portrait = (TextView) view.findViewById(R.id.tv_speed20_portrait);


        //横屏的view
        rl_land = (RelativeLayout) view.findViewById(R.id.rl_land);
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);
        rl_bot = (RelativeLayout) view.findViewById(R.id.rl_bot);
        iv_port = (ImageView) view.findViewById(R.id.iv_port);
        iv_play_land = (ImageView) view.findViewById(R.id.iv_play_land);
        iv_finish = (ImageView) view.findViewById(R.id.iv_finish);
        tv_curtime_land = (TextView) view.findViewById(R.id.tv_curtime_land);
        tv_tottime_land = (TextView) view.findViewById(R.id.tv_tottime_land);
        sb_play_land = (PolyvTickSeekBar) view.findViewById(R.id.sb_play_land);

        tv_speed = (TextView) view.findViewById(R.id.tv_speed);


        //设置布局的view
        rl_center_set = (RelativeLayout) view.findViewById(R.id.rl_center_set);
        sb_light = (SeekBar) view.findViewById(R.id.sb_light);
        sb_volume = (SeekBar) view.findViewById(R.id.sb_volume);
        tv_full = (TextView) view.findViewById(R.id.tv_full);
        tv_fit = (TextView) view.findViewById(R.id.tv_fit);
        tv_sixteennine = (TextView) view.findViewById(R.id.tv_sixteennine);
        tv_fourthree = (TextView) view.findViewById(R.id.tv_fourthree);
        tv_srt1 = (TextView) view.findViewById(R.id.tv_srt1);
        tv_srt2 = (TextView) view.findViewById(R.id.tv_srt2);
        tv_srt3 = (TextView) view.findViewById(R.id.tv_srt3);
        tv_srtnone = (TextView) view.findViewById(R.id.tv_srtnone);
        iv_close_set = (ImageView) view.findViewById(R.id.iv_close_set);
        ll_adaptive_mode = (LinearLayout) findViewById(R.id.ll_adaptive_mode);
        ll_subtitle = (LinearLayout) findViewById(R.id.ll_subtitle);
        ll_subtitle_b = (LinearLayout) findViewById(R.id.ll_subtitle_b);


        //横屏的播放速度布局的view
        rl_center_speed = (RelativeLayout) view.findViewById(R.id.rl_center_speed);
        tv_speed05 = (TextView) view.findViewById(R.id.tv_speed05);
        tv_speed10 = (TextView) view.findViewById(R.id.tv_speed10);
        tv_speed12 = (TextView) view.findViewById(R.id.tv_speed12);
        tv_speed15 = (TextView) view.findViewById(R.id.tv_speed15);
        tv_speed20 = (TextView) view.findViewById(R.id.tv_speed20);
        iv_close_speed = (ImageView) view.findViewById(R.id.iv_close_speed);


        sensorHelper = new PolyvSensorHelper(videoActivity);

        if (Build.VERSION.SDK_INT >= 26) {
            pipBuilder = new PictureInPictureParams.Builder();
        }
    }


    private void initView() {
        iv_land.setOnClickListener(this);
        iv_port.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_play_land.setOnClickListener(this);
        iv_finish.setOnClickListener(this);
        tv_full.setOnClickListener(this);
        tv_fit.setOnClickListener(this);
        tv_sixteennine.setOnClickListener(this);
        tv_fourthree.setOnClickListener(this);
        tv_srt1.setOnClickListener(this);
        tv_srt2.setOnClickListener(this);
        tv_srt3.setOnClickListener(this);
        tv_srtnone.setOnClickListener(this);
        tv_speed.setOnClickListener(this);
        tv_speed_portrait.setOnClickListener(this);
        tv_speed05.setOnClickListener(this);
        tv_speed05_portrait.setOnClickListener(this);
        tv_speed10.setOnClickListener(this);
        tv_speed10_portrait.setOnClickListener(this);
        tv_speed12.setOnClickListener(this);
        tv_speed12_portrait.setOnClickListener(this);
        tv_speed15.setOnClickListener(this);
        tv_speed15_portrait.setOnClickListener(this);
        tv_speed20.setOnClickListener(this);
        tv_speed20_portrait.setOnClickListener(this);
        iv_close_set.setOnClickListener(this);
        iv_close_speed.setOnClickListener(this);
        sb_play.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_play_land.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_light.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_volume.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    //是否显示左侧边的切换音视频的布局
    private boolean canShowLeftSideView() {
        //是否可以获取到音频的播放地址
        return videoVO != null && videoVO.hasAudioPath();
    }

    public void resetView() {
        if (ll_subtitle != null) {
            ll_subtitle.setVisibility(View.GONE);
        }
        if (ll_subtitle_b != null) {
            ll_subtitle_b.setVisibility(View.GONE);
        }
    }

    public void preparedSRT(IPolyvVideoView videoView) {
        if (videoView != null) {
            this.videoView = (PolyvVideoView) videoView;
            this.videoVO = videoView.getVideo();
            //初始化字幕控件
            initSrtView(videoView.getCurrSRTKey());
            int visibility = PolyvVideoVO.MODE_AUDIO.equals(videoView.getCurrentMode()) ? View.GONE : View.VISIBLE;
            ll_subtitle.setVisibility(visibility);
            ll_subtitle_b.setVisibility(visibility);
        }
    }

    public void preparedView() {
        if (videoView != null) {
            videoVO = videoView.getVideo();
            videoWidth = videoView.getVideoWidth();
            videoHeight = videoView.getVideoHeight();
            if (videoVO != null)
                tv_title.setText(videoVO.getTitle());
            int totalTime = videoView.getDuration();
            tv_tottime.setText(PolyvTimeUtils.generateTime(totalTime));
            tv_tottime_land.setText(PolyvTimeUtils.generateTime(totalTime));
            //初始化播放器的银幕比率的显示控件
            initRatioView(videoView.getCurrentAspectRatio());
            //初始化倍速控件及其可见性
            initSpeedView((int) (videoView.getSpeed() * 10));

            //音频模式下，隐藏切换码率/填充模式/字幕/截图的按钮
            int visibility = PolyvVideoVO.MODE_AUDIO.equals(videoView.getCurrentMode()) ? View.GONE : View.VISIBLE;
            ll_adaptive_mode.setVisibility(visibility);
            //设置进度条的打点位置
            if (PolyvVideoVO.MODE_VIDEO.equals(videoView.getCurrentMode())) {
                List<PolyvVideoVO.Videokeyframe> videokeyframes;
                if (videoVO != null && (videokeyframes = videoVO.getVideokeyframes()) != null) {
                    int maxProgress = videoView.getDuration() / 1000;
                    double rate = 1;
                    if (maxProgress < 1000) {//最大进度最小为1000
                        rate = 1000 * 1.0 / maxProgress;
                        maxProgress = 1000;
                    }
                    List<PolyvTickSeekBar.TickData> tickDataList = new ArrayList<>();
                    for (PolyvVideoVO.Videokeyframe videokeyframe : videokeyframes) {
                        //打点的颜色请设置和seekBar的thumb的颜色一致，因为打点是在thumb上层的
                        tickDataList.add(new PolyvTickSeekBar.TickData(videokeyframe.getKeytime(), (float) (videokeyframe.getKeytime() * rate), Color.WHITE, videokeyframe));
                    }
                    sb_play_land.setMax(maxProgress);
                    sb_play_land.setTicks(tickDataList);
                    sb_play_land.setOnTickClickListener(new PolyvTickSeekBar.OnTickClickListener() {
                        @Override
                        public void onTickClick(PolyvTickSeekBar.TickData tickData) {
                            resetHideTime(longTime);
                        }

                        @Override
                        public boolean onSeekBarClick() {
                            resetHideTime(longTime);
                            return true;//false：点击非打点处不触发onProgressChanged
                        }
                    });
                }
            }
        }
        // 视频准备完成后，开启随手势自动切换屏幕
        if (PolyvScreenUtils.isLandscape(mContext))
            sensorHelper.toggle(isAutoSwitchOrientation(), false);
        else
            sensorHelper.toggle(isAutoSwitchOrientation(), true);
        //视频准备完成后，定时记录当前播放的最大进度(用于禁止进度条拖拽功能)
        handler.removeMessages(SAVE_PROGRESS);
        handler.sendEmptyMessage(SAVE_PROGRESS);
        PolyvSPUtils.getInstance(getContext(), "dragSeekStrategy").put("dragSeekStrategy", dragSeekStrategy);
    }

    private boolean isAutoSwitchOrientation() {
        return fullScreenStrategy == FULLSCREEN_LANDSCAPE || (fullScreenStrategy == FULLSCREEN_RATIO && videoWidth >= videoHeight);
    }

    @Override
    public void setMediaPlayer(IPolyvVideoView player) {
        videoView = (PolyvVideoView) player;
    }

    @Override
    public void release() {
        //播放器release时主动调用
    }

    @Override
    public void destroy() {
        //播放器destroy时主动调用
    }

    @Override
    public void hide() {
        Log.d(TAG, "hide: ");
        if (isShowing) {
            handler.removeMessages(HIDE);
            handler.removeMessages(SHOW_PROGRESS);
            resetPopupLayout();
            isShowing = !isShowing;
            setVisibility(View.GONE);
        }
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setAnchorView(View view) {
        //...
    }
    // 关闭监听
    public void pause() {
        sensorHelper.disable();
    }

    // 开启监听
    public void resume() {
        sensorHelper.enable();
        if (PolyvScreenUtils.isLandscape(videoActivity)) {
            PolyvScreenUtils.hideNavigationBar(videoActivity);
        }
    }

    /**
     * 退出播放器的Activity时需调用
     */
    public void disable() {
        hide();
        sensorHelper.disable();
        handler.removeCallbacksAndMessages(null);
    }

    private void resetPopupLayout() {
        resetSpeedLayout(View.GONE);
    }

    /**
     * 显示控制栏
     *
     * @param timeout 显示的时间，<0时将一直显示
     */
    @Override
    public void show(int timeout) {
        Log.d(TAG, "show: ");
        if (timeout < 0)
            status_showalways = true;
        else
            status_showalways = false;
        if (isFullScreen ) {
            Log.d(TAG, "show is FullScreen ");
            setVisibility(View.VISIBLE);
            resetPopupLayout();
//            resetTopBottomLayout(View.GONE);
            isShowing = true;
        } else {
            if (!isShowing) {
                resetTopBottomLayout(View.VISIBLE);
                //获取焦点
                requestFocus();
                handler.removeMessages(SHOW_PROGRESS);
                handler.sendEmptyMessage(SHOW_PROGRESS);
                isShowing = !isShowing;
                setVisibility(View.VISIBLE);
            }
            sensorHelper.toggle(isAutoSwitchOrientation(), PolyvScreenUtils.isLandscape(getContext()));
        }


        resetHideTime(timeout);
    }


    @Override
    public void show() {
        show(longTime);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * 切换到全屏
     */
    public void changeToFullScreen() {
/*        if (fullScreenStrategy == FULLSCREEN_PORTRAIT) {
            changeToFullPortrait();
        } else if (fullScreenStrategy == FULLSCREEN_LANDSCAPE) {
            changeToFullLandscape();
        } else {
            if (videoWidth >= videoHeight) {
                changeToFullLandscape();
            } else {
                changeToFullPortrait();
            }
        }*/

        changeToFullLandscape();
    }

    /**
     * 切换到横屏全屏
     */
    public void changeToFullLandscape() {
        PolyvScreenUtils.setLandscape(videoActivity);
        //初始为横屏时，状态栏需要隐藏
        PolyvScreenUtils.hideStatusBar(videoActivity);
        //初始为横屏时，导航栏需要隐藏
        PolyvScreenUtils.hideNavigationBar(videoActivity);
        //初始为横屏时，控制栏的宽高需要设置
        initFullScreenWH();
    }

    private void initFullScreenWH() {
        isFullScreen = true;
        ViewGroup.LayoutParams vlp = parentView.getLayoutParams();
        //获取当前窗口大小并保存
        mVideoSizeWidth = vlp.width;
        mVideoSizeHeight = vlp.height;
        Log.d(TAG, "mVideoSizeWidth: " + mVideoSizeWidth);
        Log.d(TAG, "mVideoSizeHeight: " + mVideoSizeHeight);
        vlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        vlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        rl_land.setVisibility(View.VISIBLE);
        rl_port.setVisibility(View.GONE);
    }

    /**
     * 切换到竖屏全屏
     */
    public void changeToFullPortrait() {
        PolyvScreenUtils.hideStatusBar(videoActivity);
        PolyvScreenUtils.hideNavigationBar(videoActivity);
        initFullScreenWH();
    }

    /**
     * 切换到竖屏小窗
     */
    public void changeToSmallScreen() {
//        PolyvScreenUtils.setPortrait(videoActivity);
        initSmallScreenWH();
    }

    private void initSmallScreenWH() {
        isFullScreen = false;
        LinearLayout.LayoutParams vlp = (LinearLayout.LayoutParams) parentView.getLayoutParams();
        //获取保存的窗口大小
        vlp.width = mVideoSizeWidth;
        vlp.height = mVideoSizeHeight;
//        vlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl_port.setVisibility(View.VISIBLE);
        rl_land.setVisibility(View.GONE);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetControllerLayout();
    }

    //根据屏幕状态改变控制栏布局
    private void resetControllerLayout() {
        hide();
        PolyvScreenUtils.reSetStatusBar(videoActivity);
        if (PolyvScreenUtils.isLandscape(mContext)) {
            // 横屏下开启自动切换横竖屏
            sensorHelper.toggle(isAutoSwitchOrientation(), true);
            initFullScreenWH();
        } else {
            // 竖屏下开启自动切换横竖屏
            sensorHelper.toggle(isAutoSwitchOrientation(), false);
            initSmallScreenWH();
        }
    }

    //根据视频的播放状态去暂停或播放
    public void playOrPause() {
        if (videoView != null) {
            if (videoView.isPlaying()) {
                videoView.pause();
                status_isPlaying = false;
                iv_play.setSelected(true);
                iv_play_land.setSelected(true);
            } else {
                videoView.start();
                status_isPlaying = true;
                iv_play.setSelected(false);
                iv_play_land.setSelected(false);
            }
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (!b)
                return;
            switch (seekBar.getId()) {
                case R.id.sb_play:
                case R.id.sb_play_land:
                    resetHideTime(longTime);
                    status_dragging = true;
                    if (videoView != null) {
                        int newPosition = (int) (videoView.getDuration() * (long) i / seekBar.getMax());
                        tv_curtime.setText(PolyvTimeUtils.generateTime(newPosition));
                        tv_curtime_land.setText(PolyvTimeUtils.generateTime(newPosition));
                    }
                    break;
                case R.id.sb_light:
                    if (videoView != null)
                        videoView.setBrightness(videoActivity, i);
                    break;
                case R.id.sb_volume:
                    if (videoView != null)
                        videoView.setVolume(i);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!seekBar.isSelected())
                seekBar.setSelected(true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar.isSelected())
                seekBar.setSelected(false);
            switch (seekBar.getId()) {
                case R.id.sb_play:
                case R.id.sb_play_land:
                    if (videoView != null) {
                        int seekToPosition = (int) (videoView.getDuration() * (long) seekBar.getProgress() / seekBar.getMax());
                        if (!videoView.isCompletedState()) {
                            if (canDragSeek(seekToPosition)) {
                                videoView.seekTo(seekToPosition);
                            }
                        } else if (videoView.isCompletedState() && seekToPosition / seekBar.getMax() * seekBar.getMax() < videoView.getDuration() / seekBar.getMax() * seekBar.getMax()) {
                            if (canDragSeek(seekToPosition)) {
                                videoView.seekTo(seekToPosition);

                                videoView.start();
                            }
                        }
                    }
                    status_dragging = false;
                    break;
            }
        }
    };

    TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEND) {

                return true;
            }
            return false;
        }
    };

    //重置控制栏的隐藏时间
    private void resetHideTime(int delayedTime) {
        handler.removeMessages(HIDE);
        if (delayedTime >= 0)
            handler.sendMessageDelayed(handler.obtainMessage(HIDE), delayedTime);
    }

    //重置控制栏的顶部和底部布局以及进度条的显示状态
    private void resetTopBottomLayout(int isVisible) {
        resetTopBottomLayout(isVisible, false);
    }

    private void resetTopBottomLayout(int isVisible, boolean onlyHideTop) {
        rl_top.setVisibility(isVisible);
        if (!onlyHideTop) {
            rl_bot.setVisibility(isVisible);
            sb_play_land.setVisibility(isVisible);
        }
    }


    //重置选择播放器银幕比率控件的状态
    private void resetRatioView(int screenRatio) {
        initRatioView(screenRatio);
        if (videoView != null)
            videoView.setAspectRatio(screenRatio);
    }

    //初始化选择播放器银幕比率控件
    private void initRatioView(int screenRatio) {
        tv_full.setSelected(false);
        tv_fit.setSelected(false);
        tv_sixteennine.setSelected(false);
        tv_fourthree.setSelected(false);
        switch (screenRatio) {
            case PolyvPlayerScreenRatio.AR_ASPECT_FILL_PARENT:
                tv_full.setSelected(true);
                break;
            case PolyvPlayerScreenRatio.AR_ASPECT_FIT_PARENT:
                tv_fit.setSelected(true);
                break;
            case PolyvPlayerScreenRatio.AR_16_9_FIT_PARENT:
                tv_sixteennine.setSelected(true);
                break;
            case PolyvPlayerScreenRatio.AR_4_3_FIT_PARENT:
                tv_fourthree.setSelected(true);
                break;
        }
    }


    private String initSrtView(int srtPosition) {
        tv_srt1.setSelected(false);
        tv_srt2.setSelected(false);
        tv_srt3.setSelected(false);
        tv_srtnone.setSelected(false);
        List<String> srtKeys = new ArrayList<String>();
        if (videoVO != null)
            srtKeys.addAll(videoVO.getVideoSRT().keySet());
        switch (srtPosition) {
            case 0:
                tv_srt1.setSelected(true);
                break;
            case 1:
                tv_srt2.setSelected(true);
                break;
            case 2:
                tv_srt3.setSelected(true);
                break;
            case 3:
                tv_srtnone.setSelected(true);
                break;
        }
        return srtPosition == 3 ? "不显示" : srtKeys.get(srtPosition);
    }

    //初始化选择字幕的控件
    private void initSrtView(String srtKey) {
        tv_srt1.setSelected(false);
        tv_srt2.setSelected(false);
        tv_srt3.setSelected(false);
        tv_srt1.setVisibility(View.VISIBLE);
        tv_srt2.setVisibility(View.VISIBLE);
        tv_srt3.setVisibility(View.VISIBLE);
        tv_srtnone.setSelected(false);
        List<String> srtKeys = new ArrayList<String>();
        if (videoVO != null)
            srtKeys.addAll(videoVO.getVideoSRT().keySet());
        switch (srtKeys.size()) {
            case 0:
                tv_srt1.setVisibility(View.GONE);
                tv_srt2.setVisibility(View.GONE);
                tv_srt3.setVisibility(View.GONE);
                break;
            case 1:
                tv_srt1.setText(srtKeys.get(0));
                tv_srt2.setVisibility(View.GONE);
                tv_srt3.setVisibility(View.GONE);
                break;
            case 2:
                tv_srt1.setText(srtKeys.get(0));
                tv_srt2.setText(srtKeys.get(1));
                tv_srt3.setVisibility(View.GONE);
                break;
            default:
                tv_srt1.setText(srtKeys.get(0));
                tv_srt2.setText(srtKeys.get(1));
                tv_srt3.setText(srtKeys.get(2));
                break;
        }
        if (TextUtils.isEmpty(srtKey)) {
            tv_srtnone.setSelected(true);
            return;
        }
        switch (srtKeys.indexOf(srtKey)) {
            case 0:
                tv_srt1.setSelected(true);
                break;
            case 1:
                tv_srt2.setSelected(true);
                break;
            case 2:
                tv_srt3.setSelected(true);
                break;
        }
    }

    //重置选择播放速度的布局
    private void resetSpeedLayout(int isVisible) {
        resetSpeedLayout(isVisible, true);
    }

    private void resetSpeedLayout(int isVisible, boolean isShowTopBottomLayout) {
        if (isVisible == View.VISIBLE) {
            show(-1);
            resetTopBottomLayout(View.GONE, true);
        } else if (isShowTopBottomLayout) {
            resetTopBottomLayout(View.VISIBLE);
            requestFocus();
            resetHideTime(longTime);
        }
        rl_center_speed.setVisibility(isVisible);
    }

    //初始化选择播放速度的控件
    public void initSpeedView(int speed) {
        tv_speed05.setSelected(false);
        tv_speed05_portrait.setSelected(false);
        tv_speed10.setSelected(false);
        tv_speed10_portrait.setSelected(false);
        tv_speed12.setSelected(false);
        tv_speed12_portrait.setSelected(false);
        tv_speed15.setSelected(false);
        tv_speed15_portrait.setSelected(false);
        tv_speed20.setSelected(false);
        tv_speed20_portrait.setSelected(false);
        switch (speed) {
            case 5:
                tv_speed05.setSelected(true);
                tv_speed05_portrait.setSelected(true);
                tv_speed.setText("0.5x");
                tv_speed_portrait.setText("0.5x");
                break;
            case 10:
                tv_speed10.setSelected(true);
                tv_speed10_portrait.setSelected(true);
                tv_speed.setText("1x");
                tv_speed_portrait.setText("1x");
                break;
            case 12:
                tv_speed12.setSelected(true);
                tv_speed12_portrait.setSelected(true);
                tv_speed.setText("1.2x");
                tv_speed_portrait.setText("1.2x");
                break;
            case 15:
                tv_speed15.setSelected(true);
                tv_speed15_portrait.setSelected(true);
                tv_speed.setText("1.5x");
                tv_speed_portrait.setText("1.5x");
                break;
            case 20:
                tv_speed20.setSelected(true);
                tv_speed20_portrait.setSelected(true);
                tv_speed.setText("2x");
                tv_speed_portrait.setText("2x");
                break;
        }
    }

    //重置选择播放速度的控件
    private void resetSpeedView(int speed) {
        initSpeedView(speed);
        if (videoView != null) {
            videoView.setSpeed(speed / 10f);
        }
        hidePortraitPopupView();
        hide();
    }

    private void toastMsg(final String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    public void updatePictureInPictureActions(@DrawableRes int iconId, String title, int controlType, int requestCode) {
        if (Build.VERSION.SDK_INT < 26)
            return;
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        final PendingIntent intent =
                PendingIntent.getBroadcast(
                        videoActivity,
                        requestCode,
                        new Intent("media_control").putExtra("control_type", controlType),
                        0);
        final Icon icon = Icon.createWithResource(videoActivity, iconId);
        actions.add(new RemoteAction(icon, title, title, intent));

        pipBuilder.setActions(actions);

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        try {
            if (!videoActivity.isDestroyed()) {
                videoActivity.setPictureInPictureParams(pipBuilder.build());
            }
        } catch (Exception e) {
        }
    }

    public boolean isViceHideInPipMode() {
        return isViceHideInPipMode;
    }

    public void setOnDragSeekListener(OnDragSeekListener listener) {
        this.onDragSeekListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_land:
                changeToFullScreen();
                break;
            case R.id.iv_port:
                changeToSmallScreen();
                //退出全屏刷新外面的列表
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_play:
                playOrPause();
                break;
            case R.id.iv_play_land:
                playOrPause();
                break;
            case R.id.iv_finish:
                changeToSmallScreen();
                break;
            case R.id.tv_full:
                resetRatioView(PolyvPlayerScreenRatio.AR_ASPECT_FILL_PARENT);
                break;
            case R.id.tv_fit:
                resetRatioView(PolyvPlayerScreenRatio.AR_ASPECT_FIT_PARENT);
                break;
            case R.id.tv_sixteennine:
                resetRatioView(PolyvPlayerScreenRatio.AR_16_9_FIT_PARENT);
                break;
            case R.id.tv_fourthree:
                resetRatioView(PolyvPlayerScreenRatio.AR_4_3_FIT_PARENT);
                break;
            case R.id.tv_speed_portrait:
                boolean isVisible = rl_center_speed_portrait.getVisibility() == View.VISIBLE;
                hidePortraitPopupView();
                if (!isVisible) {
                    rl_center_speed_portrait.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_speed:
                if (rl_center_speed.getVisibility() == View.GONE)
                    resetSpeedLayout(View.VISIBLE);
                else
                    resetSpeedLayout(View.GONE);
                break;

            case R.id.tv_speed05_portrait:
            case R.id.tv_speed05:
                resetSpeedView(5);
                break;
            case R.id.tv_speed10_portrait:
            case R.id.tv_speed10:
                resetSpeedView(10);
                break;
            case R.id.tv_speed12_portrait:
            case R.id.tv_speed12:
                resetSpeedView(12);
                break;
            case R.id.tv_speed15_portrait:
            case R.id.tv_speed15:
                resetSpeedView(15);
                break;
            case R.id.tv_speed20_portrait:
            case R.id.tv_speed20:
                resetSpeedView(20);
                break;

            case R.id.iv_close_speed:
                hide();
                break;


        }
        //如果控制栏不是处于一直显示的状态，那么重置控制栏隐藏的时间
        if (!status_showalways)
            resetHideTime(longTime);
    }

    public void setAdapter(DetailCourseAdapter adapter) {
        mAdapter = adapter;
    }

    public interface OnDragSeekListener {
        void onDragSeekSuccess(int positionBeforeSeek, int positionAfterSeek);

        void onDragSeekBan(int dragSeekStrategy);
    }

    private void hidePortraitPopupView() {
        rl_center_speed_portrait.setVisibility(View.GONE);
    }
}
