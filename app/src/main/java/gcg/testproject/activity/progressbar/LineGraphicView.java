package gcg.testproject.activity.progressbar;

/**
 * discription:
 * Created by 宫成浩 on 2018/10/1.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;

/**********************************************************
 * @文件名称：LineGraphicView.java
 * @文件作者：rzq
 * @创建时间：2015年5月27日 下午3:05:19
 * @文件描述：自定义简单曲线图
 * @修改历史：2015年5月27日创建初始版本
 **********************************************************/
class LineGraphicView extends View
{
    /**
     * 公共部分
     */
    private static final int CIRCLE_SIZE = 10;

    private static enum Linestyle
    {
        Line, Curve
    }

    private Context mContext;
    private Paint mPaint;
    private Resources res;
    private DisplayMetrics dm;

    /**
     * data
     */
    private Linestyle mStyle = Linestyle.Curve;

    private int canvasHeight;
    private int canvasWidth;
    private int bheight = 0;
    private int blwidh;
    private boolean isMeasure = true;
    /**
     * Y轴最大值
     */
    private int maxValue;

    private int marginTop = 20;
    private int marginBottom = 40;

    /**
     * 曲线上总点数
     */
    private Point[] mPoints;
    /**
     * 纵坐标值
     */
    private ArrayList<Double> yRawData;
    /**
     * 横坐标值
     */
    private ArrayList<Integer> xList = new ArrayList<Integer>();// 记录每个x的值

    public LineGraphicView(Context context)
    {
        this(context, null);
    }

    public LineGraphicView(Context context, AttributeSet attrs)
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        if (isMeasure)
        {
            this.canvasHeight = getHeight();
            this.canvasWidth = getWidth();
            if (bheight == 0)
                bheight = (int) (canvasHeight - marginBottom);
            blwidh = dip2px(30);
            isMeasure = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        mPaint.setColor(res.getColor(R.color.color_f2f2f2));

        getXList();//获取x轴上的坐标点
        LogUtils.i("x轴最大值："+xList.get(xList.size()-1));
        LogUtils.i("宽度："+getWidth());

        // 点的操作设置
        mPoints = getPoints();

        mPaint.setColor(res.getColor(R.color.color_ff4631));
        mPaint.setStrokeWidth(dip2px(2.5f));
        mPaint.setStyle(Style.STROKE);
        if (mStyle == Linestyle.Curve)
        {
            drawScrollLine(canvas);
        }

        mPaint.setStyle(Style.FILL);
        for (int i = 0; i < mPoints.length; i++)
        {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, CIRCLE_SIZE / 2, mPaint);
        }
    }

    //获取x轴坐标点
    private void  getXList()
    {
        for (int i = 0; i < yRawData.size(); i++)
        {
            xList.add(blwidh + (canvasWidth - blwidh) / yRawData.size() * i);
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
        Point[] points = new Point[yRawData.size()];
        for (int i = 0; i < yRawData.size(); i++)
        {
            int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));

            points[i] = new Point(xList.get(i), ph + marginTop);
        }
        return points;
    }

    public void setData(ArrayList<Double> yRawData, int maxValue, int averageValue)
    {
        this.maxValue = maxValue; //Y轴最大值
        this.mPoints = new Point[yRawData.size()]; //曲线上的总点数
        this.yRawData = yRawData; //纵坐标的值
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue)
    {
        return (int) (dpValue * dm.density + 0.5f);
    }

}
