package gcg.testproject.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.Utils;

/**
 * 获得屏幕相关的辅助类
 * 
 * @author zhy
 * 
 */
public class ScreenUtils
{
	private ScreenUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context)
	{
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context)
	{

		int statusHeight = -1;
		try
		{
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity)
	{
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 设置屏幕为横屏
	 * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
	 * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
	 * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
	 * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
	 * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
	 *
	 * @param activity activity
	 */
	public static void setLandscape(final Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * 设置屏幕为竖屏
	 *
	 * @param activity activity
	 */
	public static void setPortrait(final Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * 判断是否横屏
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isLandscape() {
		return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	/**
	 * 判断是否竖屏
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isPortrait() {
		return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * 获取屏幕旋转角度
	 *
	 * @param activity activity
	 * @return 屏幕旋转角度
	 */
	public static int getScreenRotation(final Activity activity) {
		switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
			default:
			case Surface.ROTATION_0:
				return 0;
			case Surface.ROTATION_90:
				return 90;
			case Surface.ROTATION_180:
				return 180;
			case Surface.ROTATION_270:
				return 270;
		}
	}

	/**
	 * 判断是否锁屏
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isScreenLock() {
		KeyguardManager km = (KeyguardManager) Utils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
		return km.inKeyguardRestrictedInputMode();
	}

	/**
	 * 设置进入休眠时长
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
	 *
	 * @param duration 时长
	 */
	public static void setSleepDuration(final int duration) {
		Settings.System.putInt(Utils.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
	}

	/**
	 * 获取进入休眠时长
	 *
	 * @return 进入休眠时长，报错返回-123
	 */
	public static int getSleepDuration() {
		try {
			return Settings.System.getInt(Utils.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
			return -123;
		}
	}

}
