package gcg.testproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import gcg.testproject.R;

/**
 *
 * @ClassName:VideoPlayProgressBar

 * @PackageName:gcg.testproject.widget

 * @Create On 2017/12/28   14:08

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2017/12/28 handongkeji All rights reserved.
 */

//仿微信小视频播放时的进度条

public class VideoPlayProgressBar extends View{


    private Paint paint;
    private int width; //这个控件在xml中的宽度
    private int height; //这个控件在xml中的高度的一半，用来画进度条最后的小圆
    private int progress; //进度
    private int max; //最大进度
    private String prgressColor = "#fe9400";
    private String totalColor = "#4caf65";
    private String circleColor = "#950706";


    public VideoPlayProgressBar(Context context) {
        super(context);
    }
    public VideoPlayProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        paint = new Paint();
        paint.setAntiAlias(true); //设置抗锯齿
        paint.setStrokeWidth(5);

        //画整个进度
        paint.setColor(Color.parseColor(totalColor));
        canvas.drawLine(0,height/2,width,height/2,paint);

        if (progress != 0)
        {
            paint.setColor(Color.parseColor(prgressColor)); //设置播放进度的颜色
            canvas.drawLine(0,height/2,progress*width/max,height/2,paint);
            paint.setColor(Color.parseColor(circleColor)); //设置圆点的颜色
            canvas.drawCircle(progress*width/max,height/2,10,paint);
        }
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > max) {
            progress = max;
        }
        this.progress = progress;
        // 进度改变时，需要通过invalidate方法进行重绘
        postInvalidate();
    }

    public synchronized void setMax(int max) {
        if(max <= 0){
            max = 1;
        }
        this.max = max;
    }
    //设置播放进度的颜色
    public void setProgressColor(String progressColor)
    {
        this.prgressColor = progressColor;
    }
    //设置整个进度条的颜色
    public void setTotalColor(String totalColor)
    {
        this.totalColor = totalColor;
    }
    //设置当前进度的小圆点的颜色
    public void setCircleColor(String circleColor)
    {
        this.circleColor = circleColor;
    }
}
