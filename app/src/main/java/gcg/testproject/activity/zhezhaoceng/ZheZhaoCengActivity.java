package gcg.testproject.activity.zhezhaoceng;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.DensityUtils;

public class ZheZhaoCengActivity extends BaseActivity {

    @Bind(R.id.fl_scan)
    View mFlScan;
    @Bind(R.id.iv_quan)
    ImageView mIvQuan;
    @Bind(R.id.zzc_view)
    ZheZhaoCengView mZzcView;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhe_zhao_ceng);
        ButterKnife.bind(this);
        initZheZhaoCeng();
    }
    private void initZheZhaoCeng() {
        //设置遮罩层的位置
        FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) mFlScan.getLayoutParams();
        layout.setMargins(DensityUtils.dp2px(this, 230),
                DensityUtils.dp2px(this, 200), 0, 0);
        mFlScan.setLayoutParams(layout);


        //设置遮罩层白圈的位置  注意：距离顶部要比遮罩层距离顶部小1dp,75是遮罩层透明圈的半径
        FrameLayout.LayoutParams mIvQuanLayoutParams = (FrameLayout.LayoutParams) mIvQuan.getLayoutParams();
        mIvQuanLayoutParams.setMargins(DensityUtils.dp2px(this, 230 - 75),
                DensityUtils.dp2px(this, 200 - 75), 0, 0);
        mIvQuan.setLayoutParams(mIvQuanLayoutParams);

        //设置遮罩层动画
        valueAnimator = ValueAnimator.ofFloat(1, 1.2f);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mZzcView.resetBackgroundHoleArea((Float) animation.getAnimatedValue());
                mIvQuan.setScaleX((Float) animation.getAnimatedValue());
                mIvQuan.setScaleY((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.start();
    }

}
