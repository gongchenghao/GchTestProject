package gcg.testproject.activity.xiaohongshu_login;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.ToastUtils;

public class XiaoHongShuLoginActivity extends BaseActivity {

    @Bind(R.id.videoView)
    FullScreenVideoView mVideoView;
    @Bind(R.id.tv_denglu)
    TextView mTvDenglu;
    @Bind(R.id.tv_zhuce)
    TextView mTvZhuce;
    @Bind(R.id.activity_video_view_background)
    RelativeLayout mActivityVideoViewBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_hong_shu_login);
        ButterKnife.bind(this);
        playVideoView();

        mTvDenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(XiaoHongShuLoginActivity.this,"登录");
            }
        });
        mTvZhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort(XiaoHongShuLoginActivity.this,"注册");
            }
        });
    }
    private void playVideoView() {
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        mVideoView.start();
        //循环播放
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoView.start();
            }
        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        playVideoView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mVideoView.stopPlayback();
        super.onStop();
    }
}
