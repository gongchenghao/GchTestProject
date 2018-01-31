package gcg.testproject.widget;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import gcg.testproject.R;


/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class TimeTextView extends TextView {
    long Time;
    private boolean run = true; // 是否启动了
    private ImageView imageView;

    public TimeTextView(Context context) {
        super(context);
    };

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 在控件被销毁时移除消息
        handler.removeMessages(0);
    }



    @SuppressLint("NewApi")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        Log.i("test111","run111:"+run);
                        Log.i("test111","Time："+Time);

                        long mTime = Time; //已经过去的发布时间
                        if (mTime >= 1000*60*5) //超过五分钟
                        {
                            Log.i("test111","超过5分钟");

                            imageView.setImageResource(R.mipmap.icon_huihongbao);
                            TimeTextView.this.setBackgroundResource(R.mipmap.huiseshijian);
                            TimeTextView.this.setTextColor(Color.parseColor("#666666"));
                            TimeTextView.this.setText("00      00");
                        }else {
                            long leftTime = 1000*60*5 - mTime;

                            imageView.setImageResource(R.mipmap.icon_hongbao);
                            TimeTextView.this.setBackgroundResource(R.mipmap.shijian);
                            TimeTextView.this.setTextColor(Color.parseColor("#D15402"));


                            long minute1 = leftTime/1000/60; //获取分钟数
                            Log.i("test111","剩下的分数：minute1:"+minute1);

                            long second2 = (leftTime - minute1*60*1000)/1000; //获取剩余的秒数
                            if (second2>=10)
                            {
                                TimeTextView.this.setText("0"+minute1+"       "+second2);
                            }else {
                                TimeTextView.this.setText("0"+minute1+"      "+"0"+second2);
                            }
                            Time += 1000; //注意：Time是毫秒值，所以每次增加一秒应该是加1000毫秒
                            handler.sendEmptyMessageDelayed(0, 1000);
                        }
                    } else {
                        TimeTextView.this.setText("00      00");
                    }
                    break;
            }
        }
    };



    //传进来的时间是创建时间
    @SuppressLint("NewApi")
    public void setTimes(long mT, ImageView imageView) {
        this.imageView = imageView;

        long t2 = System.currentTimeMillis(); //获取系统当前的时间
        Time = t2 - mT; //当前时间-创建时间 = 已经创建时间

        handler.removeMessages(0);
        handler.sendEmptyMessage(0);
//
//        if (Time > 0) {
//            handler.removeMessages(0);
//            handler.sendEmptyMessage(0);
//        } else {
////            TimeTextView.this.setVisibility(View.GONE);
//            TimeTextView.this.setText("000");
//        }
    }

    public void stop() {
        run = false;
    }
}
