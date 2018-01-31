package gcg.testproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

/**
 * 
 * @ClassName:VideoPlayUtils

 * @PackageName:gcg.testproject.utils

 * @Create On 2017/12/25   18:11

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2017/12/25 handongkeji All rights reserved.
 */

public class VideoPlayUtils implements MediaPlayer.OnCompletionListener, OnErrorListener, OnInfoListener,
        OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

    private Display currDisplay;
    private SurfaceHolder holder;
    private MediaPlayer player;
    private int vWidth, vHeight;
    private String path;
    private Activity activity;
    private SurfaceView surfaceView;


    //调用系统本身的播放器
    private void initView(String path,Context context)
    {
        activity = (Activity) context;

//        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/Test_Movie.m4v");
        Uri uri = Uri.parse(path);
        //调用系统自带的播放器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.v("URI:::::::::", uri.toString());
        intent.setDataAndType(uri, "video/mp4");
        activity.startActivity(intent);
    }

    //使用SurfaceView播放    参数二：视频路径
    private void initView(SurfaceView surfaceView, String path, Context context) {
        this.path = path;
        this.activity = (Activity) context;
        this.surfaceView = surfaceView;

        //给SurfaceView添加CallBack监听
        holder = surfaceView.getHolder();
        holder.addCallback(this);
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //下面开始实例化MediaPlayer对象
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);
        Log.v("Begin:::", "surfaceDestroyed called");


        try {
            player.setDataSource(path);
            Log.v("Next:::", "surfaceDestroyed called");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //然后，我们取得当前Display对象
        currDisplay = activity.getWindowManager().getDefaultDisplay();
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // 当Surface尺寸等参数改变时触发
        Log.v("Surface Change:::", "surfaceChanged called");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 当SurfaceView中的Surface被创建的时候被调用
        //在这里我们指定MediaPlayer在当前的Surface中进行播放
        player.setDisplay(holder);
        //在指定了MediaPlayer播放的容器后，我们就可以使用prepare或者prepareAsync来准备播放了
        player.prepareAsync();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.v("Surface Destory:::", "surfaceDestroyed called");
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
        Log.v("Video Size Change", "onVideoSizeChanged called");

    }

    @Override
    public void onSeekComplete(MediaPlayer arg0) {
        // seek操作完成时触发
        Log.v("Seek Completion", "onSeekComplete called");

    }

    @Override
    public void onPrepared(MediaPlayer player) {
        // 当prepare完成后，该方法触发，在这里我们播放视频

        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();

        if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
            //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
            float wRatio = (float) vWidth / (float) currDisplay.getWidth();
            float hRatio = (float) vHeight / (float) currDisplay.getHeight();

            //选择大的一个进行缩放
            float ratio = Math.max(wRatio, hRatio);

            vWidth = (int) Math.ceil((float) vWidth / ratio);
            vHeight = (int) Math.ceil((float) vHeight / ratio);

            //设置surfaceView的布局参数
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));

            //然后开始播放视频

            player.start();
        }
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
        Log.v("Play Error:::", "onError called");
        switch (whatError) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.v("Play Error:::", "MEDIA_ERROR_SERVER_DIED");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.v("Play Error:::", "MEDIA_ERROR_UNKNOWN");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer player) {
        // 当MediaPlayer播放完成后触发
        Log.v("Play Over:::", "onComletion called");
        activity.finish();
    }
}
