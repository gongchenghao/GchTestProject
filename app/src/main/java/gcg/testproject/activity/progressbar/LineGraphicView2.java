package gcg.testproject.activity.progressbar;

/**
 * discription:
 * Created by 宫成浩 on 2018/10/1.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ToastUtils;

class LineGraphicView2 extends View
{
    private Context mContext;
    private Paint mPaint;
    private Resources res;
    private DisplayMetrics dm;

    private int canvasHeight; //画布高度
    private int canvasWidth; //画布宽度
    private int progress = 100;

    /**
     * 曲线上总点数
     */
    private Point[] mPoints;
    private ArrayList<Integer> xList = new ArrayList<Integer>();// 横坐标
    private ArrayList<Integer> yList = new ArrayList<Integer>();// 纵坐标

    public LineGraphicView2(Context context)
    {
        this(context, null);
    }

    public LineGraphicView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView()
    {
        this.res = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
    }

    public void setProgress(int progress)
    {
        if (progress > 100)
        {
            ToastUtils.showShort(mContext,"进度不能大于100%");
            return;
        }
        this.progress = progress;
        postInvalidate(); //重绘

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        LogUtils.i("==== onDraw ====");
        canvasWidth = getWidth();
        canvasHeight = getHeight();

        mPaint.setColor(res.getColor(R.color.color_f2f2f2));

        getXYList();//获取x轴上的坐标点
        LogUtils.i("获取到的x坐标点："+xList.size());

        // 点的操作设置
        mPoints = getPoints();

        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Style.STROKE); //不能缺

        int progressX = canvasWidth * progress / 100; //获取当前进度下x轴的坐标

        //获取对应的y坐标
        //曲线在y轴最底部的坐标
        int bottomY = canvasHeight/2 - dip2px(10);

        //第一个20和第二个20：x轴被平均分成了伍分，每份占比20%
        //dip2px(20)：曲线在y轴最高点和最低点的间距
        double detalY = (double)progress%20 / 20 * dip2px(20) ;

        int progressY = 0;
        if ((progress>=0 && progress < 20) ||
                (progress>=40 && progress < 60) ||
                (progress>=80 && progress < 100))
        {
            progressY = (int) (bottomY + detalY);
        }else {
            progressY = canvasHeight - (int) (bottomY + detalY);
        }

        //设置渐变色
        int[] colors = new int[2];
        colors[0] = res.getColor(R.color.color_ff4631);
        colors[1] = res.getColor(R.color.color_999999);
        LinearGradient shader = new LinearGradient(0, 0,progressX , progressY, colors, null,
                Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        drawScrollLine(canvas); //画曲线

        mPaint.setStyle(Style.FILL); //不能缺

        //重新创建一个画圆圈的画笔
        Paint paintCycle = new Paint();
        //设置圆点的颜色
        paintCycle.setColor(res.getColor(R.color.color_ff4631));
        paintCycle.setAntiAlias(true);

        //画圆点
        canvas.drawCircle(progressX, progressY,dip2px(5), paintCycle);
        //画进度文字
        drawText(progress+"",progressX - dip2px(5),progressY-dip2px(7),canvas);
    }

    //获取x轴坐标点
    private void  getXYList()
    {
        if (xList.size() >= 6)
        {
            return;
        }
        xList.add(0); //将x轴的起点加上，否则曲线会缺少起始的一部分
        //将画布的宽度平分成五等分，每份是一个x轴坐标点
        for (int i = 0; i < 5; i++) {
            xList.add(canvasWidth / 5 * (i+1));
        }
        //y轴高度必须要有变化，才能实现曲线，如果y轴高度没有变化，则画出来的是直线
        yList.add(canvasHeight/2 - dip2px(10));
        for (int i = 0; i < 5; i++) {
            if (i%2 == 0)
            {
                yList.add(canvasHeight/2 + dip2px(10));
            }else {
                yList.add(canvasHeight/2 - dip2px(10));
            }
        }
    }


    private void drawScrollLine(Canvas canvas)
    {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < mPoints.length - 1; i++)
        {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;

            Path path = new Path();
            path.moveTo(startp.x, startp.y);
            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
            canvas.drawPath(path, mPaint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas)
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(dip2px(12));
        p.setColor(res.getColor(R.color.color_999999));
        p.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y, p);
    }

    private Point[] getPoints()
    {
        Point[] points = new Point[xList.size()];
        for (int i = 0; i < xList.size(); i++)
        {
            points[i] = new Point(xList.get(i), yList.get(i));
        }
        return points;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue)
    {
        return (int) (dpValue * dm.density + 0.5f);
    }

}
