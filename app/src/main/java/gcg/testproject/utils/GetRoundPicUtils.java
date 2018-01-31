package gcg.testproject.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class GetRoundPicUtils {

    //获取圆角图片
    public void rectRoundBitmap(int picID,ImageView iv,Context context,int redias){
        //得到资源文件的BitMap
        Bitmap image= BitmapFactory.decodeResource(context.getResources(),picID);
        //创建RoundedBitmapDrawable对象
        RoundedBitmapDrawable roundImg =RoundedBitmapDrawableFactory.create(context.getResources(),image);
        //抗锯齿
        roundImg.setAntiAlias(true);
        //设置圆角半径
        roundImg.setCornerRadius(redias);
        //设置显示图片
        iv.setImageDrawable(roundImg);
    }

    //获取圆形图片
    public void roundBitmap(int picID,ImageView iv,Context context){
        //如果是圆的时候，我们应该把bitmap图片进行剪切成正方形， 然后再设置圆角半径为正方形边长的一半即可
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), picID);
        Bitmap bitmap = null;
        //将长方形图片裁剪成正方形图片
        if (image.getWidth() == image.getHeight()) {
            bitmap = Bitmap.createBitmap(image, image.getWidth() / 2 - image.getHeight() / 2, 0, image.getHeight(), image.getHeight());
        } else {
            bitmap = Bitmap.createBitmap(image, 0, image.getHeight() / 2 - image.getWidth() / 2, image.getWidth(), image.getWidth());
        }
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        //圆角半径为正方形边长的一半
        roundedBitmapDrawable.setCornerRadius(bitmap.getWidth() / 2);
        //抗锯齿
        roundedBitmapDrawable.setAntiAlias(true);
        iv.setImageDrawable(roundedBitmapDrawable);
    }

    //放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float)w / width);
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }
    //将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable){
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,width,height);
        drawable.draw(canvas);
        return bitmap;

    }
    //获得带倒影的图片方法
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap){
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap,
                0, height/2, width, height/2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height,width,height + reflectionGap,
                deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
