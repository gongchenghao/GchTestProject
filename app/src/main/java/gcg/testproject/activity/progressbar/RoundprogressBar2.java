package gcg.testproject.activity.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import gcg.testproject.R;

/**
 * Created by Administrator on 2017/5/5 0005.
 */

public class RoundprogressBar2 extends View {
        private static final int START_ANGLE = -90; //进度条开始的角度的默认值
        private static final String CENTER_COLOR = "#eeff06"; //中间部分的颜色默认值
        private static final String RING_COLOR = "#FF7281E1"; //圆环的颜色默认值
        private static final String PROGRESS_COLOR = "#FFDA0F0F"; //进度条颜色默认值
        private static final String TEXT_COLOR = "#FF000000"; //文字颜色默认值
        private static final int TEXT_SIZE = 30; //文字大小默认值
        private static final int CIRCLE_RADIUS = 20;
        private static final int RING_WIDTH = 5;

        /**
         * 圆弧的起始角度，参考canvas.drawArc方法
         */
        private int startAngle;

        /**
         * 圆形内半径
         */
        private int radius;

        /**
         * 进度条的宽度
         */
        private int ringWidth;

        /**
         * 默认进度
         */
        private int mProgress = 0;

        /**
         * 圆形内部填充色
         */
        private int centerColor;

        /**
         * 进度条背景色
         */
        private int ringColor;

        /**
         * 进度条的颜色
         */
        private int progressColor;

        /**
         * 文字大小
         */
        private int textSize;

        /**
         * 文字颜色
         */
        private int textColor;

        /**
         * 文字是否需要显示
         */
        private boolean isTextDisplay;

        private String textContent;

        private Paint mPaint;

        public int max ;

        public RoundprogressBar2(Context context) {
            this(context, null);
        }

        public RoundprogressBar2(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public RoundprogressBar2(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);

            // 获取自定义属性：即获取xml中设置的值
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar2);
            for (int i = 0; i < a.length(); i ++) {
                int attr = a.getIndex(i);
                switch (attr) {
                    case R.styleable.RoundProgressBar2_startAngle:
                        startAngle = a.getInteger(attr, START_ANGLE);
                        break;
                    case R.styleable.RoundProgressBar2_centerColor:
                        centerColor = a.getColor(attr, Color.parseColor(CENTER_COLOR));
                        break;
                    case R.styleable.RoundProgressBar2_progressColor1:
                        progressColor = a.getColor(attr, Color.parseColor(PROGRESS_COLOR));
                        break;
                    case R.styleable.RoundProgressBar2_ringColor:
                        ringColor = a.getColor(attr, Color.parseColor(RING_COLOR));
                        break;
                    case R.styleable.RoundProgressBar2_textColor:
                        textColor = a.getColor(attr, Color.parseColor(TEXT_COLOR));
                        break;
                    case R.styleable.RoundProgressBar2_textSize:
                        textSize = (int) a.getDimension(attr, TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE,
                                getResources().getDisplayMetrics()));
                        break;
                    case R.styleable.RoundProgressBar2_isTextDisplay:
                        isTextDisplay = a.getBoolean(attr, true);
                        break;
                    case R.styleable.RoundProgressBar2_radius:
                        radius = (int) a.getDimension(attr, TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, CIRCLE_RADIUS,
                                getResources().getDisplayMetrics()
                        ));
                        break;
                    case R.styleable.RoundProgressBar2_ringWidth:
                        ringWidth = (int) a.getDimension(attr, TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, RING_WIDTH,
                                getResources().getDisplayMetrics()
                        ));
                        break;
                    default:
                        break;
                }
            }
            //回收资源
            a.recycle();

            // 初始化画笔设置
            setPaint();
        }

        private void setPaint() {
            mPaint = new Paint();
            //设置画笔抗锯齿
            mPaint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 获取圆心坐标：以这个控件为基准，获取到的圆心坐标其实就是这个控件的中心点
            int cx = getWidth() / 2;
            int cy = cx;

            /**
             * 画进度条内部的小圆
             */
//            if (centerColor != 0) {
//                drawCenterCircle(canvas, cx, cy);
//            }

            /**
             * 画外层大圆；这样画出来的就是个指定颜色、指定宽度的圆环
             */
            drawOuterCircle(canvas, cx, cy);

            /**
             * 画进度圆弧
             */
            drawProgress(canvas, cx, cy);

            /**
             * 画出进度百分比
             */
            drawProgressText(canvas, cx, cy);
        }

        private void drawProgressText(Canvas canvas, int cx, int cy) {
            if (!isTextDisplay) {
                return;
            }
            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setStrokeWidth(0);
            textContent = getProgress() + "%";
            float textWidth = mPaint.measureText(textContent);
            canvas.drawText(textContent, cx - textWidth / 2, cy + textSize / 2, mPaint);
        }

        //RectF坐标是float类型，Rect坐标是integer类型
        private void drawProgress(Canvas canvas, int cx, int cy) {
            mPaint.setColor(progressColor); //设置颜色为进度条颜色
            mPaint.setStrokeWidth(ringWidth); //设置宽度为进度条的宽度
            mPaint.setStyle(Paint.Style.STROKE);//设置只画轮廓
            RectF mRectF = new RectF(cx - radius, cy - radius, cx + radius, cy + radius); //参数：左上右下
            float sweepAngle = (float) (mProgress * 360.0 / max); //圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
            canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint); //表示画圆弧

            //获取圆弧内侧上的坐标点
            float x1   = (float) (cx + radius * Math.cos((sweepAngle+startAngle) * 3.14 /180));
            float y1   = (float) (cy + radius * Math.sin((sweepAngle+startAngle) * 3.14 /180));

            //设置圆弧的直径
            mPaint.setStrokeWidth(10);
            //以圆弧的半径为半径，以圆弧的中心为圆心，画圆;即进度条开始处的小圆
            canvas.drawCircle(x1,y1,5,mPaint);
        }

//    Paint.Style.STROKE 只绘制图形轮廓（描边）
//    Paint.Style.FILL 只绘制图形内容
//    Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容

        //画进度条外的大圆；这样画出来的就是个指定颜色、指定宽度的圆环
        private void drawOuterCircle(Canvas canvas, int cx, int cy) {
            mPaint.setStyle(Paint.Style.STROKE); //设置只绘制圆形轮廓，内部不用填充
            mPaint.setColor(ringColor); //设置轮廓的颜色
            mPaint.setStrokeWidth(ringWidth); //设置轮廓的宽度
            canvas.drawCircle(cx, cy, radius, mPaint);
        }

        //画进度条内的小圆
        private void drawCenterCircle(Canvas canvas, int cx, int cy) {
            mPaint.setColor(centerColor); //设置圆的背景颜色
            mPaint.setStyle(Paint.Style.FILL); //设置只绘制圆形内容，外部的轮廓线不要
            canvas.drawCircle(cx, cy, radius, mPaint); //画圆
        }


        public synchronized int getProgress() {
            return mProgress;
        }

        public synchronized void setProgress(int progress) {
            if (progress < 0) {
                progress = 0;
            } else if (progress > max) {
                progress = max;
            }
            mProgress = progress;
            // 进度改变时，需要通过invalidate方法进行重绘
            postInvalidate();
        }

    	public synchronized void setMax(int max) {
            if(max < 0){
                throw new IllegalArgumentException("max not less than 0");
            }
		    this.max = max;
	    }
}
