package gcg.testproject.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.Utils;

/**
 * 打开或关闭软键盘
 * 
 * @author zhy
 * 
 */
public class KeyBoardUtils
{
	/**
	 * 打开软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/*
      避免输入法面板遮挡
      <p>在manifest.xml中activity中设置</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

	/**
	 * 动态显示软键盘
	 *
	 * @param activity activity
	 */
	public static void showSoftInput(final Activity activity) {
		View view = activity.getCurrentFocus();
		if (view == null) view = new View(activity);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 动态显示软键盘
	 *
	 * @param view 视图
	 */
	public static void showSoftInput(final View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 动态隐藏软键盘
	 *
	 * @param activity activity
	 */
	public static void hideSoftInput(final Activity activity) {
		View view = activity.getCurrentFocus();
		if (view == null) view = new View(activity);
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 动态隐藏软键盘
	 *
	 * @param view 视图
	 */
	public static void hideSoftInput(final View view) {
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 切换键盘显示与否状态
	 */
	public static void toggleSoftInput() {
		InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) return;
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
}
