package gcg.testproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.blankj.utilcode.util.*;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/31 0031.
 */


//用到这个jar:    compile 'com.blankj:utilcode:1.7.1'



public class GetUpLoadPicUtils {
    public static File getPic(String path) {
        File file = null;
        Bitmap bitmap = ImageUtils.getBitmap(new File(path));

        //获取原始宽高
        int height = bitmap.getHeight();
        Log.i("test111","height:"+height);

        int width = bitmap.getWidth();
        Log.i("test111","width:"+width);

        //获取新宽高
        int newWidth = 1024;
        int newHeight = 1024*height/width;

        Log.i("test111","newHeight:"+newHeight);

        Bitmap bitmap1 = ImageUtils.compressByScale(bitmap, newWidth, newHeight);
        try {
            Log.i("test111","try");
            file = saveFile(bitmap1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveFile(Bitmap bm) {
        String path = Environment.getExternalStorageDirectory().getPath() + "/zuoxun/";
        File dirFile = new File(path);
        String fileName = SystemClock.currentThreadTimeMillis() + ".jpg";
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("test111","myCaptureFile:"+myCaptureFile.length());
        return myCaptureFile;
    }
}
