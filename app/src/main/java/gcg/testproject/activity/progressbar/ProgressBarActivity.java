package gcg.testproject.activity.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

public class ProgressBarActivity extends BaseActivity {

    @Bind(R.id.roundProgressBar)
    RoundprogressBar2 roundProgressBar;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.progress_bar1)
    ProgressBar progressBar2;
    @Bind(R.id.line_view)
    LineGraphicView2 line_view;

    private int roundProgress = 0;
    private int horizontalProgress = 0;
    private int horizontalProgress1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.bind(this);

        initRoundProgressBar(); //初始化圆形进度条
        initHorizontalProgressBar();//初始化水平进度条
        initHorizontalProgressBar1();//初始化水平进度条

        setQuxianBar();
    }

    private void setQuxianBar() {
        line_view.setProgress(88);
    }


    private void initRoundProgressBar() {
        roundProgressBar.setMax(100);
        handler.sendEmptyMessage(0);
    }

    private void initHorizontalProgressBar() {
        progressBar.setMax(100);
        handler.sendEmptyMessage(1);
    }

    private void initHorizontalProgressBar1() {
        progressBar2.setMax(100);
        handler.sendEmptyMessage(2);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: //圆形进度条
                    if (roundProgress < 100) {
                        roundProgress++;
                        roundProgressBar.setProgress(roundProgress);
                        tvProgress.setText(roundProgress + "%");
                        //每隔一秒，走一下进度条
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                    break;
                case 1: //水平进度条
                    if (horizontalProgress < 100) {
                        horizontalProgress++;
                        progressBar.setProgress(horizontalProgress);
                        if ((horizontalProgress + 10) <= 100) {
                            progressBar.setSecondaryProgress(horizontalProgress + 10); //设置二级进度
                        }

                        //每隔一秒，走一下进度条
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 2: //水平进度条2
                    if (horizontalProgress1 < 100) {
                        horizontalProgress1++;
                        progressBar2.setProgress(horizontalProgress1);
                        if ((horizontalProgress1 + 10) <= 100) {
                            progressBar2.setSecondaryProgress(horizontalProgress1 + 10); //设置二级进度
                        }
                        //每隔一秒，走一下进度条
                        handler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
            }
        }
    };
}
