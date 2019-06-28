package gcg.testproject.activity.YinPinJinDuTiao;

import android.graphics.drawable.ClipDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.LogUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;

public class YinPinProgressDemoActivity extends AppCompatActivity implements View.OnClickListener
{

    @Bind(R.id.iv_paly)
    ImageView      ivPaly;
    @Bind(R.id.iv_clip)
    ImageView      ivClip;
    @Bind(R.id.fl_pro)
    FrameLayout    flPro;
    @Bind(R.id.tv_voice_second)
    TextView       tvVoiceSecond;
    @Bind(R.id.rv_yuyin)
    RelativeLayout rvYuyin;
    private MediaPlayer  mediaPlayer = null;
    private ClipDrawable clipDrawable; //声音进度条
    private boolean isPlaying;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_pin_progress_demo);
        ButterKnife.bind(this);

        clipDrawable = (ClipDrawable) ivClip.getDrawable();
        clipDrawable.setLevel(0); //设置语音进度条的黄色部分的显示程度

        rvYuyin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rv_yuyin:
                LogUtils.i("开始播放");
                setPlayerStatus();
                break;
        }
    }

    public void start()
    {
        LogUtil.i("GandaInfoActivity_start()");
        new Thread()
        {
            Handler handler = new Handler(getMainLooper())
            {
                @Override
                public void handleMessage(Message msg)
                {
                    if (msg.what == 1)
                    {
                        int arg1 = msg.arg1;
                        clipDrawable.setLevel(arg1); // level在0-10000之间，0表示完全裁剪，10000表示完全不裁剪。设置的值越大，裁剪的范围就越小。
                    }
                }
            };

            @Override
            public void run()
            {
                LogUtil.i("GandaInfoActivity_isPlaying:" + isPlaying);
                try
                {
                    while (isPlaying)
                    {
                        int duration = mediaPlayer.getDuration();
                        LogUtil.i("GandaInfoActivity_获取到的duration:" + duration);
                        Thread.sleep(500);
                        int currentPosition = mediaPlayer.getCurrentPosition();
                        LogUtil.i("GandaInfoActivity_发送的进度:" + currentPosition);
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = currentPosition * 10000 / duration; //设置进度,因为Level的最大值为10000,所以要除以10000
                        handler.sendMessage(message);
                        continue;
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setPlayerStatus()
    {


        if (mediaPlayer == null)
        {
            listenKada();
        }
        else
        {
            if (mediaPlayer.isPlaying())
            {
                LogUtil.i("GandaInfoActivity_正在播放,设置为false");
                isPlaying = false;
                mediaPlayer.pause();
                ivPaly.setBackgroundResource(R.mipmap.ganda_play);
            }
            else
            {
                LogUtil.i("GandaInfoActivity_没有播放,设置为true");
                isPlaying = true;
                mediaPlayer.start();
                start(); //开始更新进度条
                ivPaly.setBackgroundResource(R.mipmap.ganda_suspend);
            }
        }
    }


    private void listenKada()
    {
        mediaPlayer =MediaPlayer.create(this,R.raw.video);
        try
        {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    LogUtil.i("GandaInfoActivity_onPrepared,设置为true");
                    isPlaying = true; //必须先设置isPlaying,因为start()方法中要用到
                    mp.start();
                    start(); //开始更新进度条
                    ivPaly.setBackgroundResource(R.mipmap.ganda_suspend);
                }
            });

            //播放完成监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    LogUtil.i("GandaInfoActivity_onCompletion,设置为false");
                    isPlaying = false;
                    ivPaly.setBackgroundResource(R.mipmap.ganda_play);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
