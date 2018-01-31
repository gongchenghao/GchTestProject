package gcg.testproject.activity.SplashAndGuide;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.blankj.utilcode.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.MainActivity;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;

public class SplashActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.iv_splash)
    ImageView ivSplash;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.activity_spalh)
    RelativeLayout activitySpalh;
    private TimeCount timeCount = new TimeCount(4000, 1000);


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalh);
        ButterKnife.bind(this);
        tvTime.setOnClickListener(this);
        timeCount.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_time: //点击了跳过后，直接进入MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                timeCount.cancel(); //跳转后结束计时
                finish();
                break;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String s = millisUntilFinished / 1000 + "";
            tvTime.setText("跳过" + s);
        }

        @Override
        public void onFinish() {
            goWhere(); //判断跳转到哪个界面
        }
    }

    @Override
    public void onBackPressed() {

    }
    /**
     * 第一次启动进GuideActivity,否则进入MainActivity；
     * aBoolean1：true_进入GuideActivity  false_进入MainActivity
     */
    private void goWhere() {
        boolean aBoolean = spUtils.getBoolean(Word.FIRST_LAUNCH); //如果不存在，则返回默认值：false
        if (aBoolean == false) {
            spUtils.put(Word.FIRST_LAUNCH,true); //当用户第一次打开时，设置为true
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
        }
        finish();
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
