package gcg.testproject.activity.SelectVideo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.selectphoto.DaTuActivity;
import gcg.testproject.activity.selectphoto.DeleteDialog;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.widget.VideoPlayProgressBar;

/**
 * 视频播放类
 *
 * @ClassName:VideoPlayActivity
 * @PackageName:com.newtonapple.zhangyiyan.zhangyiyan.activity
 * @Create On 2017/5/23 0023   15:54
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2017/5/23 0023 handongkeji All rights reserved.
 */


public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnCompletionListener, OnErrorListener, OnInfoListener,
        OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback,View.OnClickListener {

    @Bind(R.id.su)
    SurfaceView surfaceView;
    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.fanhui)
    LinearLayout fanhui;
    @Bind(R.id.tuikuan)
    LinearLayout tuikuan;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.ib_more)
    ImageButton ibMore;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.tv_current)
    TextView tvCurrent;
    @Bind(R.id.tv_duration)
    TextView tvDuration;
    @Bind(R.id.tv_zan_ting)
    TextView tv_zan_ting;
    @Bind(R.id.progress)
    VideoPlayProgressBar progress;
    @Bind(R.id.activity_video)
    LinearLayout activityVideo;
    private Display currDisplay;
    private SurfaceHolder holder;
    private MediaPlayer player;
    private int vWidth, vHeight;
    private String path; //传递过来的视频路径
    private ProgressDialog progressDialog;
    private float downX, downY;
    private int screenWidth;
    private Handler handler = new Handler();
    private int FACTOR = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        initView();
        initSurfaceView();
        initOnClick();
    }

    private void initOnClick() {
        tv_zan_ting.setOnClickListener(this);
        ibMore.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_zan_ting:
                if (tv_zan_ting.getText().toString().equals("暂停"))
                {
                    player.pause();
                    tv_zan_ting.setText("开始");
                }else {
                    player.start();
                    tv_zan_ting.setText("暂停");
                }

                break;
            case R.id.ib_more:
                final DeleteDialog dialog = new DeleteDialog(VideoPlayActivity.this,"确定要删除这个视频吗？");
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (dialog.hasDelete == true) { //表示删除了
                            path = "";
                        }
                        Intent mIntent = new Intent();
                        mIntent.putExtra("jieGuoUrl", path);
                        // 设置结果，并进行传送
                        VideoPlayActivity.this.setResult(Word.REQUEST_CODE5, mIntent);
                        finish();
                    }
                });
                break;
            case R.id.fanhui:
                Intent mIntent = new Intent();
                mIntent.putExtra("jieGuoUrl", path);
                // 设置结果，并进行传送
                VideoPlayActivity.this.setResult(Word.REQUEST_CODE5, mIntent);
                finish();
                break;
        }
    }

    private void initView() {
        headTitle.setText("视频");
        progress.setProgressColor("#fe9400");
        progress.setTotalColor("#4caf65");
        progress.setCircleColor("#950706");
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMsg("正在加载");
//        if (progressDialog.isShowing() == false)
//        {
//            progressDialog.show();
//        }




        //给SurfaceView添加CallBack监听
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //下面开始实例化MediaPlayer对象
        player = new MediaPlayer();

        //然后指定需要播放文件的路径，初始化MediaPlayer
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        String fromWhere = intent.getStringExtra("fromWhere"); //用来判断是否显示删除按钮
        if (!TextUtils.isEmpty(fromWhere) && fromWhere.equals("Select"))
        {
            ibMore.setVisibility(View.VISIBLE);
        }

        Log.i("test", "videoPlay:path:" + path);

        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);

//        Log.i("test", "Begin:::surfaceDestroyed called");

        try {
            player.setDataSource(path);
//            Log.i("test", "Next:::surfaceDestroyed called");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //然后，我们取得当前Display对象
        currDisplay = this.getWindowManager().getDefaultDisplay();
    }

    private void initSurfaceView() {
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // TODO 音量
                        float distanceX = event.getX() - downX;
                        float distanceY = event.getY() - downY;
                        if (downX > screenWidth - 200
                                && Math.abs(distanceX) < 50
                                && distanceY > FACTOR) {
                            // TODO 减小音量
                            setVolume(false);
                        } else if (downX > screenWidth - 200
                                && Math.abs(distanceX) < 50
                                && distanceY < -FACTOR) {
                            // TODO 增加音量
                            setVolume(true);

                        }
                        // TODO 播放进度调节
                        if (Math.abs(distanceY) < 50 && distanceX > FACTOR) {
                            // TODO 快进
                            int currentT = player.getCurrentPosition();//播放的位置
                            player.seekTo(currentT + 5000);
                            downX = event.getX();
                            downY = event.getY();
                            Log.i("info", "distanceX快进=" + distanceX);
                        } else if (Math.abs(distanceY) < 50
                                && distanceX < -FACTOR) {
                            // TODO 快退
                            int currentT = player.getCurrentPosition();
                            player.seekTo(currentT - 5000);
                            downX = event.getX();
                            downY = event.getY();
                            Log.i("info", "distanceX=" + distanceX);
                        }
                        break;
                }
                return true;
            }
        });
    }

    //设置视频音量
    private void setVolume(boolean flag) {
        // 获取音量管理器
        AudioManager manager = (AudioManager) getSystemService(AUDIO_SERVICE);
        // 获取当前音量
        int curretnV = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (flag) {
            curretnV++;
        } else {
            curretnV--;
        }
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, curretnV, AudioManager.FLAG_SHOW_UI);
        LogUtils.i("当前音量值："+curretnV);
        /**
         * 1.AudioManager.STREAM_MUSIC 多媒体 2.AudioManager.STREAM_ALARM 闹钟
         * 3.AudioManager.STREAM_NOTIFICATION 通知 4.AudioManager.STREAM_RING 铃音
         * 5.AudioManager.STREAM_SYSTEM 系统提示音 6.AudioManager.STREAM_VOICE_CALL
         * 电话
         *
         * AudioManager.FLAG_SHOW_UI:显示音量控件
         */
    }
    /**
     * 更新播放进度的递归
     */
    private void updateView()
    {
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // TODO 设置进度控件
                if (player != null && player.isPlaying())
                {
                    int duration = player.getDuration();
                    int durationM = duration / 1000 / 60; //总时长的分钟数
                    int durationS = (duration - durationM*60*100)/1000 ; //除去分钟数后剩下的秒数
                    String durationM1 = durationM+"";
                    String durationS1 = durationS+"";
                    if (durationM < 10)
                    {
                        durationM1 = "0"+durationM;
                    }
                    if (durationS < 10)
                    {
                        durationS1 = "0"+durationS;
                    }
                    tvDuration.setText(durationM1+":"+durationS1);


                    int currentPosition = player.getCurrentPosition();
                    int currentPositionM = currentPosition / 1000 / 60; //当前时长的分钟数
                    int currentPositionS = (currentPosition - durationM*60*100)/1000 ; //当前时长除去分钟数后剩下的秒数
                    String currentPositionM1 = currentPositionM+"";
                    String currentPositionS1 = currentPositionS+"";
                    if (currentPositionM < 10)
                    {
                        currentPositionM1 = "0"+currentPositionM;
                    }
                    if (currentPositionS < 10)
                    {
                        currentPositionS1 = "0"+currentPositionS;
                    }
                    tvCurrent.setText(currentPositionM1 +":"+currentPositionS1);

                    progress.setMax(player.getDuration());
                    progress.setProgress(player.getCurrentPosition());
                }
                updateView();
            }
        }, 1000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // 当Surface尺寸等参数改变时触发
//        Log.i("test", "Surface Change:::____surfaceChanged called");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

//        Log.i("test", "surfaceCreated");

        // 当SurfaceView中的Surface被创建的时候被调用
        //在这里我们指定MediaPlayer在当前的Surface中进行播放
        player.setDisplay(holder);
        //在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
        player.prepareAsync();
//        Log.i("test", "异步准备");


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.i("test", "Surface Destory:::____surfaceDestroyed called");
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
//        Log.i("test", "Video Size Change____onVideoSizeChanged called");

    }

    @Override
    public void onSeekComplete(MediaPlayer arg0) {
        // seek操作完成时触发
//        Log.i("test", "Seek Completion____onSeekComplete called");

    }

    @Override
    public void onPrepared(MediaPlayer player) {
//        Log.i("test", "准备完成");

        // 当prepare完成后，该方法触发，在这里我们播放视频

        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();

        if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
//            Log.i("test", "宽高超出界限，进行调整");

            //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
            float wRatio = (float) vWidth / (float) currDisplay.getWidth();
            float hRatio = (float) vHeight / (float) currDisplay.getHeight();

            //选择小的一个进行缩放
//            float ratio = Math.max(wRatio, hRatio);
            float ratio = Math.min(wRatio, hRatio);

            vWidth = (int) Math.ceil((float) vWidth / ratio);
            vHeight = (int) Math.ceil((float) vHeight / ratio);

            //设置surfaceView的布局参数
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));
        }
//        progressDialog.dismiss();
        //然后开始播放视频
        player.start();
        updateView();//更新播放进度
//        Log.i("test", "开始播放");

    }

    @Override
    public boolean onInfo(MediaPlayer player, int whatInfo, int extra) {
        // 当一些特定信息出现或者警告时触发
        switch (whatInfo) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                break;
        }
        return false;
    }

    @Override
    public boolean onError(MediaPlayer player, int whatError, int extra) {
//        Log.v("Play Error:::", "onError called");
        switch (whatError) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
//                Log.i("test", "Play Error:::____MEDIA_ERROR_SERVER_DIED,服务死了");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
//                Log.i("test", "Play Error:::____MEDIA_ERROR_UNKNOWN，未知");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        // 当MediaPlayer播放完成后触发
//        Log.i("test", "Play Over:::____onComletion called,播放完成");
        this.finish();
    }
}
