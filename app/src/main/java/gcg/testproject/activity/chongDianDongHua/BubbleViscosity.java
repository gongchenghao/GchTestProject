package gcg.testproject.activity.chongDianDongHua;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by gongchenghao on 2019/6/28 0028.
 * describe:
 */
public class BubbleViscosity extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    private static ScheduledExecutorService scheduledThreadPool;
    private        Context                  context;
    private        String                   paintColor     = "#25DA29";// 不透明圆弧的颜色
    private        String                   centreColor    = "#00000000"; // 中间圆的颜色
    private        String                   minCentreColor = "#9025DA29"; //透明的圆弧
    private        int                      screenHeight;
    private        int                      screenWidth;


    private float  lastRadius;  // 底部半圆的半径
    private float  rate              = 0.32f;// 底部曲线的控制点
    private float  rate2             = 0.45f;// 底部曲线的控制点
    private PointF lastCurveStrat    = new PointF();//底部圆的起点坐标
    private PointF lastCurveEnd      = new PointF();//底部圆的结束坐标
    private PointF centreCirclePoint = new PointF();// 中间圆的坐标
    private float  centreRadius;//中间圆的 半径
    private float  bubbleRadius;


    // 所有圆弧的坐标数组
    private PointF[] arcPointStrat = new PointF[8];
    private PointF[] arcPointEnd   = new PointF[8];
    private PointF[] control       = new PointF[8];
    private PointF   arcStrat      = new PointF();
    private PointF   arcEnd        = new PointF();
    private PointF   controlP      = new PointF();

    // 气泡的其实点保存集合
    List<PointF>     bubbleList  = new ArrayList<>();
    List<BubbleBean> bubbleBeans = new ArrayList<>();

    private int           rotateAngle = 0;  //旋转的角度
    private float         controlrate = 1.66f; // 圆弧的控制点
    private float         controlrateS = 1.3f; // 可变圆弧的控制点
    private int           i = 0;// 无限循环的下标
    private SurfaceHolder mHolder;
    private float         scale = 0;//  圆得开口值

    private Paint  arcPaint;
    private Paint  minCentrePaint;
    private Paint  bubblePaint;
    private Paint  centrePaint;
    private Paint  lastPaint;
    private Path   lastPath; // 所有的路劲
    private Random random;
    private Paint  textPaint;
    private String text ="78 %";
    private Rect   rect;

    public BubbleViscosity(Context context) {
        this(context, null);
    }

    public BubbleViscosity(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleViscosity(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initTool();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenHeight = getMeasuredHeight();
        screenWidth = getMeasuredWidth();
        setBubbleList();
    }

    private void initTool() {
        rect = new Rect();
        mHolder = getHolder();//获取SurfaceHolder对象
        mHolder.addCallback(this);//注册SurfaceHolder的回调方法
        setFocusable(true); // 设置焦点
        //TODO:保证该SurfaceView在最上层,避免两个SurfaceView叠加，遮挡问题
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        setZOrderOnTop(true);
        lastRadius = dip2Dimension(40f, context);//底部圆的半径
        centreRadius = dip2Dimension(100f, context);//中间圆的半径
        bubbleRadius = dip2Dimension(10f, context);//气泡的半径
        random = new Random();
        //底部圆
        lastPaint = new Paint();
        lastPaint.setAntiAlias(true);
        lastPaint.setStyle(Paint.Style.FILL);
        lastPaint.setColor(Color.parseColor(paintColor));
        lastPaint.setStrokeWidth(2);

        lastPath = new Path();

        //中间圆的画笔
        centrePaint = new Paint();
        centrePaint.setAntiAlias(true);
        centrePaint.setStyle(Paint.Style.FILL);
        centrePaint.setStrokeWidth(2);
        centrePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        centrePaint.setColor(Color.parseColor(centreColor));
        // 不透明圆弧的画笔
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.FILL);
        arcPaint.setColor(Color.parseColor(paintColor));
        arcPaint.setStrokeWidth(2);
        //  透明圆弧的画笔
        minCentrePaint = new Paint();
        minCentrePaint.setAntiAlias(true);
        minCentrePaint.setStyle(Paint.Style.FILL);
        minCentrePaint.setColor(Color.parseColor(minCentreColor));
        minCentrePaint.setStrokeWidth(2);
        // 气泡的画笔
        bubblePaint = new Paint();
        bubblePaint.setAntiAlias(true);
        bubblePaint.setStyle(Paint.Style.FILL);
        bubblePaint.setColor(Color.parseColor(minCentreColor));
        bubblePaint.setStrokeWidth(2);
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#FFFFFF"));
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(dip2Dimension(40f, context));



    }


    private void onMDraw() {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        bubbleDraw(canvas);  //画气泡
        lastCircleDraw(canvas);//画底部的半圆
        centreCircleDraw(canvas);// 中间的圆
        textPaint.getTextBounds(text,0,text.length(),rect);
        canvas.drawText(text,centreCirclePoint.x-rect.width()/2,centreCirclePoint.y+rect.height()/2,textPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    private void centreCircleDraw(Canvas canvas) {
        centreCirclePoint.set(screenWidth / 2, screenHeight / 2);
        circleInCoordinateDraw(canvas);//绘制四个圆弧
        canvas.drawCircle(centreCirclePoint.x, centreCirclePoint.y, centreRadius, centrePaint);

    }

    private void lastCircleDraw(Canvas canvas) {
        lastCurveStrat.set(screenWidth / 2 - lastRadius, screenHeight);//第一段曲线的开始点
        lastCurveEnd.set((screenWidth / 2), screenHeight);//第一段的结束点

        float k = (lastRadius / 2) / lastRadius;// 三角函数 正切

        float aX = lastRadius - lastRadius * rate2; //任意取一个点 为控制点 rate 控制曲线的弯曲度
        float aY = lastCurveStrat.y - aX * k;
        float bX = lastRadius - lastRadius * rate;
        float bY = lastCurveEnd.y - bX * k;

        lastPath.rewind();
        lastPath.moveTo(lastCurveStrat.x, lastCurveStrat.y);
        lastPath.cubicTo(lastCurveStrat.x + aX, aY, lastCurveEnd.x - bX, bY, lastCurveEnd.x, lastCurveEnd.y - lastRadius / 2);
        lastPath.cubicTo(lastCurveEnd.x + bX, bY, lastCurveEnd.x + lastRadius - aX, aY, lastCurveEnd.x + lastRadius, lastCurveEnd.y);

        lastPath.lineTo(lastCurveStrat.x, lastCurveStrat.y);//闭合曲线 进行填充
        canvas.drawPath(lastPath, lastPaint);

    }

    private int bubbleIndex = 0;

    private void bubbleDraw(Canvas canvas) {
        for (int i = 0; i < bubbleBeans.size(); i++) {
            if (bubbleBeans.get(i).getY() <= (int) (screenHeight / 2 + centreRadius)) {
                bubblePaint.setAlpha(000);
                canvas.drawCircle(bubbleBeans.get(i).getX(), bubbleBeans.get(i).getY(), bubbleRadius, bubblePaint);
            }else {
                bubblePaint.setAlpha(150);
                canvas.drawCircle(bubbleBeans.get(i).getX(), bubbleBeans.get(i).getY(), bubbleRadius, bubblePaint);
            }
            //底部黏性动画
            if (bubbleBeans.get(i).getY()<=bubbleList.get(0).y&&bubbleList.get(0).y-bubbleBeans.get(i).getY()<15){
                lastPath.moveTo(lastCurveStrat.x,lastCurveStrat.y);
                int y= (int) (screenHeight-(screenHeight-bubbleBeans.get(i).getY())/2);
                lastPath.quadTo(lastCurveEnd.x-lastRadius,y,bubbleBeans.get(i).getX()-bubbleRadius,bubbleBeans.get(i).getY());
                lastPath.lineTo(bubbleBeans.get(i).getX()+bubbleRadius,bubbleBeans.get(i).getY());
                lastPath.quadTo(lastCurveEnd.x+lastRadius,y,lastCurveEnd.x+lastRadius,lastCurveStrat.y);
                canvas.drawPath(lastPath,bubblePaint);
            }
            //气泡和 中部圆的黏性动画

            if (bubbleBeans.get(i).getY()-arcPointStrat[5].y<110&&rotateAngle>20&&rotateAngle<100){

                lastPath.moveTo(arcPointStrat[5].x,arcPointStrat[5].y);
                float  x= Math.abs(arcPointEnd[5].x - arcPointStrat[5].x)/2;
                float  y=screenHeight/2+centreRadius+(bubbleBeans.get(i).getY()-arcPointStrat[5].y)/2;
                lastPath.quadTo(screenWidth/2+x,y,bubbleBeans.get(i).getX()+bubbleRadius,bubbleBeans.get(i).getY());
                lastPath.lineTo(bubbleBeans.get(i).getX()-bubbleRadius,bubbleBeans.get(i).getY());
                lastPath.quadTo(screenWidth/2-x,y,arcPointEnd[5].x,arcPointEnd[5].y);
                canvas.drawPath(lastPath,bubblePaint);
            }
        }

    }




    /**
     * dip 转换成 px
     *
     * @param dip
     * @param context
     * @return
     */
    public  float dip2Dimension(float dip, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }


    public void circleInCoordinateDraw(Canvas canvas) {
        int angle;
        for (int i = 0; i < arcPointStrat.length; i++) {
            if (i > 3 && i < 6) {//透明圆弧的位置
                if (i == 4) {
                    angle = rotateAngle + i * 60;

                } else {
                    angle = rotateAngle + i * 64;
                }
            } else if (i > 5) {//隐藏显示圆弧的位置
                if (i == 6) {
                    angle = rotateAngle + i * 25;
                } else {
                    angle = rotateAngle + i * 48;
                }

            } else {
                angle = rotateAngle + i * 90;
            }

            //求弧度坐标点的 公式
            float radian = (float) Math.toRadians(angle);
            float adjacent = (float) Math.cos(radian) * centreRadius; //邻边
            float right = (float) Math.sin(radian) * centreRadius;//对边
            //求控制点的坐标 公式
            float radianControl = (float) Math.toRadians(90 - (45 + angle));
            float xStrat = (float) Math.cos(radianControl) * centreRadius; //邻边
            float yEnd = (float) Math.sin(radianControl) * centreRadius; //对边
            if (i == 0 || i == 1) {
                if (i == 1) {
                    arcStrat.set(centreCirclePoint.x + adjacent - scale, centreCirclePoint.y + right + scale);//这个是改变开口大小
                    arcEnd.set(centreCirclePoint.x - right, centreCirclePoint.y + adjacent);

                } else {
                    arcStrat.set(centreCirclePoint.x + adjacent, centreCirclePoint.y + right);
                    arcEnd.set(centreCirclePoint.x - right - scale, centreCirclePoint.y + adjacent + scale);//这个是改变开口大小

                }
                controlP.set(centreCirclePoint.x + yEnd * controlrate, centreCirclePoint.y + xStrat * controlrate);
            } else {
                arcStrat.set(centreCirclePoint.x + adjacent, centreCirclePoint.y + right);
                arcEnd.set(centreCirclePoint.x - right, centreCirclePoint.y + adjacent);
                if (i > 5) {
                    controlP.set(centreCirclePoint.x + yEnd * controlrateS, centreCirclePoint.y + xStrat * controlrateS);
                } else {
                    controlP.set(centreCirclePoint.x + yEnd * controlrate, centreCirclePoint.y + xStrat * controlrate);
                }
            }
            arcPointStrat[i] = arcStrat;
            arcPointEnd[i] = arcEnd;
            control[i] = controlP;

            lastPath.rewind();
            lastPath.moveTo(arcPointStrat[i].x, arcPointStrat[i].y);
            lastPath.quadTo(control[i].x, control[i].y, arcPointEnd[i].x, arcPointEnd[i].y);

            if (i > 3 && i < 6) {
                canvas.drawPath(lastPath, minCentrePaint);
            } else {
                canvas.drawPath(lastPath, arcPaint);
            }
            lastPath.rewind();
        }
    }


    private void setAnimation() {
        setScheduleWithFixedDelay(this, 0, 5);
        setScheduleWithFixedDelay(new Runnable() {//添加气泡的线程
            @Override
            public void run() {//添加气泡
                if (bubbleIndex > 2) bubbleIndex = 0;
                if (bubbleBeans.size() < 8) {
                    bubbleBeans.add(new BubbleBean(bubbleList.get(bubbleIndex).x, bubbleList.get(bubbleIndex).y,random.nextInt(4)+2,bubbleIndex));
                }else {
                    for (int i=0;i<bubbleBeans.size();i++){
                        if (bubbleBeans.get(i).getY() <= (int) (screenHeight / 2 + centreRadius)) {
                            bubbleBeans.get(i).set(bubbleList.get(bubbleIndex).x, bubbleList.get(bubbleIndex).y,random.nextInt(4)+2,bubbleIndex);
                            if (random.nextInt(bubbleBeans.size())+3==3? true : false){
                            }else {
                                break;
                            }
                        }
                    }
                }
                bubbleIndex++;
            }
        }, 0, 300);
    }


    private static ScheduledExecutorService getInstence() {
        if (scheduledThreadPool == null) {
            synchronized (BubbleViscosity.class) {
                if (scheduledThreadPool == null) {
                    scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();//newScheduledThreadPool
                }
            }
        }
        return scheduledThreadPool;
    }

    /*
     * 定时任务 每隔多少秒执行一次
     * */
    private static void setScheduleWithFixedDelay(Runnable var1, long var2, long var4) {
        getInstence().scheduleWithFixedDelay(var1, var2, var4, TimeUnit.MILLISECONDS);

    }


    /*
     * 结束线程 任务
     * */
    public static void onDestroyThread() {
        getInstence().shutdownNow();
        if (scheduledThreadPool != null) {
            scheduledThreadPool = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setAnimation();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        onDestroyThread();
    }

    @Override
    public void run() {
        i++;
        rotateAngle = i;
        if (i > 90 && i < 180) {
            scale += 0.25;
            if (controlrateS < 1.66)
                controlrateS += 0.005;
        } else if (i >= 180) {
            scale -= 0.12;
            if (i > 300)
                controlrateS -= 0.01;
        }
        onMDraw();
        if (i == 360) {
            i = 0;
            rotateAngle = 0;
            controlrate = 1.66f;
            controlrateS = 1.3f;
            scale = 0;
        }

    }


    public void setBubbleList() {
        //求弧度坐标点的 公式
        float radian = (float) Math.toRadians(35);
        float adjacent = (float) Math.cos(radian) * lastRadius / 3; //邻边
        float right = (float) Math.sin(radian) * lastRadius / 3;//对边
        if (!bubbleList.isEmpty()) return;
        bubbleList.add(new PointF(screenWidth / 2 - adjacent, screenHeight - right)); //左边气泡的位置
        bubbleList.add(new PointF(screenWidth / 2, screenHeight - lastRadius / 4));  // 中间气泡的位置
        bubbleList.add(new PointF(screenWidth / 2 + adjacent, screenHeight - right));  //右边气泡的位置


        setScheduleWithFixedDelay(new Runnable() {// 移动气泡的线程
            @Override
            public void run() {
                for (int i = 0; i < bubbleBeans.size(); i++) {
                    bubbleBeans.get(i).setMove(screenHeight,(int) (screenHeight / 2 + centreRadius));
                }
            }
        }, 0, 4);

    }
}
