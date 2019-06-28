package gcg.testproject.activity.yyz3;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.donkingliang.imageselector.entry.Image;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.yinhe_xingxi.CustomScrollView;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ScreenUtils;
import gcg.testproject.utils.ToastUtils;

public class YyzDongHuaActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.iv_1)
    ImageView mIv1;
    @Bind(R.id.iv_2)
    ImageView mIv2;
    @Bind(R.id.iv_3)
    ImageView mIv3;
    @Bind(R.id.iv_4)
    ImageView mIv4;
    @Bind(R.id.iv_5)
    ImageView mIv5;
    @Bind(R.id.rv_dalu)
    RelativeLayout mRvDalu;
    private ValueAnimator valueAnimator;
    private boolean isMax = true; //用来判断当前是否有控件处于放大状态  true:表示点击的控件处于正常状态，需要放大

    private ValueAnimator valueAnimator1; //缩小动画及归位
    private ValueAnimator valueAnimator2; //放大动画及归位
    private ValueAnimator valueAnimator3; //移动到中间的动画及归位
    private int which; //用来判断当前点击的是哪个控件
    private int screenHeight; //屏幕高度
    private int screenWidth; //屏幕宽度
    private float detalX; //x轴全部需要移动的距离
    private float detalY; //y轴全部需要移动的距离


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yyz_dong_hua);
        ButterKnife.bind(this);

        //设置本页面为侵入式状态栏的核心代码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        screenWidth = ScreenUtils.getScreenWidth(YyzDongHuaActivity.this); //获取屏幕宽度
        screenHeight = ScreenUtils.getScreenHeight(YyzDongHuaActivity.this); //获取屏幕高度

        initClick();
        initRvAnimation();
        initChildViewAnimation();
    }

    private void initClick() {
        mIv1.setOnClickListener(this);
        mIv2.setOnClickListener(this);
        mIv3.setOnClickListener(this);
        mIv4.setOnClickListener(this);
        mIv5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.iv_1:
                LogUtils.i("左上");
                ToastUtils.showShort(YyzDongHuaActivity.this,"左上");
                valueAnimation(mIv1);
                break;
            case R.id.iv_2:
                LogUtils.i("右上");
                ToastUtils.showShort(YyzDongHuaActivity.this,"右上");
                valueAnimation(mIv2);
                break;
            case R.id.iv_3:
                LogUtils.i("左下");
                ToastUtils.showShort(YyzDongHuaActivity.this,"左下");
                valueAnimation(mIv3);
                break;
            case R.id.iv_4:
                LogUtils.i("右下");
                ToastUtils.showShort(YyzDongHuaActivity.this,"右下");
                valueAnimation(mIv4);
                break;
            case R.id.iv_5:
                LogUtils.i("最下");
                ToastUtils.showShort(YyzDongHuaActivity.this,"最下");
                valueAnimation(mIv5);
                break;
        }
    }


    private void valueAnimation(View childView)
    {
        which = mRvDalu.indexOfChild(childView);
        if (isMax == true) //如果是正常大小，就缩小，否则就放大
        {
            int targetX = screenWidth/2 - childView.getWidth()/2; //目标位置x轴坐标
            int targetY = screenHeight/2 - childView.getHeight()/2; //目标位置y轴坐标
            detalX = targetX - childView.getLeft(); //获取控件距离目标坐标的x轴距离
            detalY = targetY - childView.getTop(); //获取控件距离目标坐标的y轴距离
            LogUtils.i("获取到的x轴需要移动的距离："+detalX);
            LogUtils.i("获取到的y轴需要移动的距离："+detalY);
            viewToCenter(); //需要在获取到x轴和y轴的移动位置后，再初始化valueAnimator3和valueAnimator4

            setCannotClick(which); //设置其他的子控件不能点击
            valueAnimator.pause(); //先暂停，再执行其他动画
            valueAnimator1.start();
            valueAnimator2.start();
            valueAnimator3.start();
        }else {
            setCanClick(); //设置其他的子控件的可以点击
            valueAnimator1.reverse();
            valueAnimator2.reverse();
            valueAnimator3.reverse();
            valueAnimator.resume(); //恢复
        }
        isMax = !isMax;
    }
    private void initRvAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0,150);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mRvDalu.setTranslationY(animatedValue);
            }
        });
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }
    private void initChildViewAnimation() {
        //缩小到0
        valueAnimator1 = ValueAnimator.ofFloat(1,0);
        valueAnimator1.setDuration(300);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                for (int i = 0; i <mRvDalu.getChildCount() ; i++) {
                    View childAt = mRvDalu.getChildAt(i);
                    if (i != which)
                    {
                        childAt.setScaleY(animatedValue);
                        childAt.setScaleX(animatedValue);
                    }
                }
            }
        });

        //放大二倍
        valueAnimator2 = ValueAnimator.ofFloat(1,2);
        valueAnimator2.setDuration(300);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                for (int i = 0; i <mRvDalu.getChildCount() ; i++) {
                    View childAt = mRvDalu.getChildAt(i);
                    if (i == which)
                    {
                        childAt.setScaleY(animatedValue);
                        childAt.setScaleX(animatedValue);
                    }
                }
            }
        });
    }
    private void viewToCenter()
    {
        valueAnimator3 = ValueAnimator.ofFloat(0,1);
        valueAnimator3.setDuration(800);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float animatedValue = (float) animation.getAnimatedValue();
                LogUtils.i("animatedValue:"+animatedValue);
                for (int i = 0; i <mRvDalu.getChildCount() ; i++) {
                    final View childAt = mRvDalu.getChildAt(i);
                    if (i == which)
                    {
                        childAt.setTranslationX(animatedValue*detalX);
                        childAt.setTranslationY(animatedValue*detalY);
                    }
                }
            }
        });
    }

    //设置控件不能点击
    private void setCannotClick(int which)
    {
        LogUtils.i("setCannotClick");
        for (int i = 0; i <mRvDalu.getChildCount() ; i++) {
            View childAt = mRvDalu.getChildAt(i);
            if (i != which)
            {
                childAt.setClickable(false); //缩小的控件禁止点击
            }
        }
    }
    private void setCanClick()
    {
        LogUtils.i("setCanClick");
        LogUtils.i("mRvDalu.getChildCount():"+mRvDalu.getChildCount());
        for (int i = 0; i <mRvDalu.getChildCount() ; i++) {
            View childAt = mRvDalu.getChildAt(i);
            LogUtils.i("设置控件可以点击");
            childAt.setClickable(true); //缩小的控件能够点击
            childAt.setFocusable(true);
            childAt.requestFocus();
            childAt.setOnClickListener(this);
        }
    }
}
