package gcg.testproject.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import gcg.testproject.R;

/**
 * Toast统一管理类
 * 
 */
public class ToastUtils
{

	private ToastUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义时间显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义时间显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}



	// 自定义UI的toast
	public static void showMyToast(Context context,int layoutID,int findID) {
		if (context == null)
		{
			return;
		}
		final Toast toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
		View view = LayoutInflater.from(context).inflate(layoutID,null,false);
		//布局文件中设置的宽高不顶用，需要重新设置;注意:不能设置最外层控件的宽高，会报空指针，可以设置第二层控件的宽高
		Activity activity = (Activity) context;
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		View viewById = view.findViewById(findID);
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		viewById.getLayoutParams().width = (int) (screenWidth*0.411);
		viewById.getLayoutParams().height = (int) (screenHeight*0.18);

		//设置吐司居中显示
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(view);
		toast.show();
	}

}