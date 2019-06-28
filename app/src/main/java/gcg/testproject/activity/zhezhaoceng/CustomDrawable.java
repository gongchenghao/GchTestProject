package gcg.testproject.activity.zhezhaoceng;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * discription:
 * Created by 宫成浩 on 2018/10/8.
 */

//参考链接：https://blog.csdn.net/qq_20785431/article/details/81159210

public class CustomDrawable extends Drawable {
    private Paint srcPaint;
    private Path srcPath = new Path();

    private Drawable innerDrawable;


    public CustomDrawable(Drawable innerDrawable) {
        this.innerDrawable = innerDrawable;
        srcPath.addRect(100, 100, 200, 200, Path.Direction.CW);
        srcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        srcPaint.setColor(0xffffffff);
    }

    /**
     * 设置内部透明的部分
     *
     * @param srcPath
     */
    public void setSrcPath(Path srcPath) {
        this.srcPath = srcPath;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        innerDrawable.setBounds(getBounds());
        if (srcPath == null || srcPath.isEmpty()) {
            innerDrawable.draw(canvas);
        } else {
            //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
            int saveCount = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), srcPaint, Canvas.ALL_SAVE_FLAG);

            //dst 绘制目标图
            innerDrawable.draw(canvas);

            //设置混合模式
            srcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //src 绘制源图
            canvas.drawPath(srcPath, srcPaint);
            //清除混合模式
            srcPaint.setXfermode(null);
            //还原画布
            canvas.restoreToCount(saveCount);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        innerDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        innerDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return innerDrawable.getOpacity();
    }
}