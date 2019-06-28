package gcg.testproject.activity.yinhe_xingxi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * 加载特大图片
 * @ClassName:LoadBigImageUtils

 * @PackageName:eiyudensetsu.ginga.youxin.com.yinheyingxiong.utils2

 * @Create On 2018/6/6   11:01

 * @author:gongchenghao

 * @Copyrights 2018/6/6 宫成浩 All rights reserved.
 */


public class LoadBigImageUtils {
	public static Bitmap readBitMap(Context context, int resId){
		 BitmapFactory.Options opt = new BitmapFactory.Options();
		 opt.inPreferredConfig = Bitmap.Config.RGB_565;
		 opt.inPurgeable = true;
		 opt.inInputShareable = true;
		 //获取资源图片
	     InputStream is = context.getResources().openRawResource(resId);
		 return BitmapFactory.decodeStream(is,null,opt);
	}
}
