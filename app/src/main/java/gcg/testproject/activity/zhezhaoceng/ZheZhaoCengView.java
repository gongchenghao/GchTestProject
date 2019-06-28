package gcg.testproject.activity.zhezhaoceng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import gcg.testproject.R;

/**
 * discription:根据layout中子View的位置，确定局部透明区域
 * Created by 宫成浩 on 2018/10/8.
 */
//参考链接：https://blog.csdn.net/qq_20785431/article/details/81159210

public class ZheZhaoCengView extends FrameLayout {

    private Context mContext;
    private CustomDrawable background;

    public ZheZhaoCengView(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public ZheZhaoCengView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView(context, attrs, 0);
    }

    public ZheZhaoCengView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        background = new CustomDrawable(getBackground());
        setBackground(background);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetBackgroundHoleArea(1);
    }

    @SuppressLint("NewApi")
    public void resetBackgroundHoleArea(float x) {
        Path path = null;
        // 以子View为范围构造需要透明显示的区域
        View view = findViewById(R.id.fl_scan);
        if (view != null) {
            path = new Path();
            // 矩形透明区域
//            path.addRoundRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), dp2Px(mContext,10), dp2Px(mContext,10),Path.Direction.CW);
            //圆形透明区域
            path.addCircle(view.getLeft(), view.getTop(), dp2Px(mContext,75*x), Path.Direction.CW);
        }
        if (path != null) {
            background.setSrcPath(path);
            postInvalidate(); //重绘界面
        }
    }

    public int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
