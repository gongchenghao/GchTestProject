package gcg.testproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import com.blankj.utilcode.util.CleanUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跟App相关的辅助类
 * 
 * @author zhy
 * 
 */
public class AppUtils
{

	private AppUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断App是否安装
	 *
	 * @param packageName 包名
	 * @return {@code true}: 已安装<br>{@code false}: 未安装
	 */
	public static boolean isInstallApp(final String packageName) {
		return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
	}

	/**
	 * 安装App(支持7.0)
	 *
	 * @param filePath  文件路径
	 * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
	 *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
	 */
	public static void installApp(final String filePath, final String authority) {
		installApp(FileUtils.getFileByPath(filePath), authority);
	}

	/**
	 * 安装App（支持7.0）
	 *
	 * @param file      文件
	 * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
	 *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
	 */
	public static void installApp(final File file, final String authority) {
		if (!FileUtils.isFileExists(file)) return;
		Utils.getContext().startActivity(IntentUtils.getInstallAppIntent(file, authority));
	}

	/**
	 * 安装App（支持6.0）
	 *
	 * @param activity    activity
	 * @param filePath    文件路径
	 * @param authority   7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
	 *                    <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
	 * @param requestCode 请求值
	 */
	public static void installApp(final Activity activity, final String filePath, final String authority, final int requestCode) {
		installApp(activity, FileUtils.getFileByPath(filePath), authority, requestCode);
	}

	/**
	 * 安装App(支持6.0)
	 *
	 * @param activity    activity
	 * @param file        文件
	 * @param authority   7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
	 *                    <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
	 * @param requestCode 请求值
	 */
	public static void installApp(final Activity activity, final File file, final String authority, final int requestCode) {
		if (!FileUtils.isFileExists(file)) return;
		activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority), requestCode);
	}

	/**
	 * 静默安装App
	 * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
	 *
	 * @param filePath 文件路径
	 * @return {@code true}: 安装成功<br>{@code false}: 安装失败
	 */
	public static boolean installAppSilent(final String filePath) {
		File file = FileUtils.getFileByPath(filePath);
		if (!FileUtils.isFileExists(file)) return false;
		String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
		ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(), true);
		return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
	}

	/**
	 * 卸载App
	 *
	 * @param packageName 包名
	 */
	public static void uninstallApp(final String packageName) {
		if (isSpace(packageName)) return;
		Utils.getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName));
	}

	/**
	 * 卸载App
	 *
	 * @param activity    activity
	 * @param packageName 包名
	 * @param requestCode 请求值
	 */
	public static void uninstallApp(final Activity activity, final String packageName, final int requestCode) {
		if (isSpace(packageName)) return;
		activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
	}

	/**
	 * 静默卸载App
	 * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.DELETE_PACKAGES" />}</p>
	 *
	 * @param packageName 包名
	 * @param isKeepData  是否保留数据
	 * @return {@code true}: 卸载成功<br>{@code false}: 卸载失败
	 */
	public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
		if (isSpace(packageName)) return false;
		String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall " + (isKeepData ? "-k " : "") + packageName;
		ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, !isSystemApp(), true);
		return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
	}


	/**
	 * 判断App是否有root权限
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isAppRoot() {
		ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
		if (result.result == 0) {
			return true;
		}
		if (result.errorMsg != null) {
			LogUtils.d("isAppRoot", result.errorMsg);
		}
		return false;
	}

	/**
	 * 打开App
	 *
	 * @param packageName 包名
	 */
	public static void launchApp(final String packageName) {
		if (isSpace(packageName)) return;
		Utils.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName));
	}

	/**
	 * 打开App
	 *
	 * @param activity    activity
	 * @param packageName 包名
	 * @param requestCode 请求值
	 */
	public static void launchApp(final Activity activity, final String packageName, final int requestCode) {
		if (isSpace(packageName)) return;
		activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
	}

	/**
	 * 获取App包名
	 *
	 * @return App包名
	 */
	public static String getAppPackageName() {
		return Utils.getContext().getPackageName();
	}

	/**
	 * 获取App具体设置
	 */
	public static void getAppDetailsSettings() {
		getAppDetailsSettings(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App具体设置
	 *
	 * @param packageName 包名
	 */
	public static void getAppDetailsSettings(final String packageName) {
		if (isSpace(packageName)) return;
		Utils.getContext().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
	}

	/**
	 * 获取App名称
	 *
	 * @return App名称
	 */
	public static String getAppName() {
		return getAppName(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App名称
	 *
	 * @param packageName 包名
	 * @return App名称
	 */
	public static String getAppName(final String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取App图标
	 *
	 * @return App图标
	 */
	public static Drawable getAppIcon() {
		return getAppIcon(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App图标
	 *
	 * @param packageName 包名
	 * @return App图标
	 */
	public static Drawable getAppIcon(final String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.applicationInfo.loadIcon(pm);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取App路径
	 *
	 * @return App路径
	 */
	public static String getAppPath() {
		return getAppPath(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App路径
	 *
	 * @param packageName 包名
	 * @return App路径
	 */
	public static String getAppPath(final String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.applicationInfo.sourceDir;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取App版本号
	 *
	 * @return App版本号
	 */
	public static String getAppVersionName() {
		return getAppVersionName(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App版本号
	 *
	 * @param packageName 包名
	 * @return App版本号
	 */
	public static String getAppVersionName(final String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取App版本码
	 *
	 * @return App版本码
	 */
	public static int getAppVersionCode() {
		return getAppVersionCode(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App版本码
	 *
	 * @param packageName 包名
	 * @return App版本码
	 */
	public static int getAppVersionCode(final String packageName) {
		if (isSpace(packageName)) return -1;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? -1 : pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 判断App是否是系统应用
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isSystemApp() {
		return isSystemApp(Utils.getContext().getPackageName());
	}

	/**
	 * 判断App是否是系统应用
	 *
	 * @param packageName 包名
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isSystemApp(final String packageName) {
		if (isSpace(packageName)) return false;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
			return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断App是否是Debug版本
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isAppDebug() {
		return isAppDebug(Utils.getContext().getPackageName());
	}

	/**
	 * 判断App是否是Debug版本
	 *
	 * @param packageName 包名
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isAppDebug(final String packageName) {
		if (isSpace(packageName)) return false;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
			return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取App签名
	 *
	 * @return App签名
	 */
	public static Signature[] getAppSignature() {
		return getAppSignature(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App签名
	 *
	 * @param packageName 包名
	 * @return App签名
	 */
	public static Signature[] getAppSignature(final String packageName) {
		if (isSpace(packageName)) return null;
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			@SuppressLint("PackageManagerGetSignatures")
			PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
			return pi == null ? null : pi.signatures;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取应用签名的的SHA1值
	 * <p>可据此判断高德，百度地图key是否正确</p>
	 *
	 * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
	 */
	public static String getAppSignatureSHA1() {
		return getAppSignatureSHA1(Utils.getContext().getPackageName());
	}

	/**
	 * 获取应用签名的的SHA1值
	 * <p>可据此判断高德，百度地图key是否正确</p>
	 *
	 * @param packageName 包名
	 * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88:1B:8B:E8:54:42
	 */
	public static String getAppSignatureSHA1(final String packageName) {
		Signature[] signature = getAppSignature(packageName);
		if (signature == null) return null;
		return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
				replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
	}

	/**
	 * 判断App是否处于前台
	 *
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isAppForeground() {
		ActivityManager manager = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
		if (info == null || info.size() == 0) return false;
		for (ActivityManager.RunningAppProcessInfo aInfo : info) {
			if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return aInfo.processName.equals(Utils.getContext().getPackageName());
			}
		}
		return false;
	}

	/**
	 * 判断App是否处于前台
	 * <p>当不是查看当前App，且SDK大于21时，
	 * 需添加权限 {@code <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>}</p>
	 *
	 * @param packageName 包名
	 * @return {@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isAppForeground(final String packageName) {
		return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
	}

	/**
	 * 封装App信息的Bean类
	 */
	public static class AppInfo {

		private String   name;
		private Drawable icon;
		private String   packageName;
		private String   packagePath;
		private String   versionName;
		private int      versionCode;
		private boolean  isSystem;

		public Drawable getIcon() {
			return icon;
		}

		public void setIcon(final Drawable icon) {
			this.icon = icon;
		}

		public boolean isSystem() {
			return isSystem;
		}

		public void setSystem(final boolean isSystem) {
			this.isSystem = isSystem;
		}

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(final String packageName) {
			this.packageName = packageName;
		}

		public String getPackagePath() {
			return packagePath;
		}

		public void setPackagePath(final String packagePath) {
			this.packagePath = packagePath;
		}

		public int getVersionCode() {
			return versionCode;
		}

		public void setVersionCode(final int versionCode) {
			this.versionCode = versionCode;
		}

		public String getVersionName() {
			return versionName;
		}

		public void setVersionName(final String versionName) {
			this.versionName = versionName;
		}

		/**
		 * @param name        名称
		 * @param icon        图标
		 * @param packageName 包名
		 * @param packagePath 包路径
		 * @param versionName 版本号
		 * @param versionCode 版本码
		 * @param isSystem    是否系统应用
		 */
		public AppInfo(String packageName, String name, Drawable icon, String packagePath,
					   String versionName, int versionCode, boolean isSystem) {
			this.setName(name);
			this.setIcon(icon);
			this.setPackageName(packageName);
			this.setPackagePath(packagePath);
			this.setVersionName(versionName);
			this.setVersionCode(versionCode);
			this.setSystem(isSystem);
		}

		@Override
		public String toString() {
			return "pkg name: " + getPackageName() +
					"\napp name: " + getName() +
					"\napp path: " + getPackagePath() +
					"\napp v name: " + getVersionName() +
					"\napp v code: " + getVersionCode() +
					"\nis system: " + isSystem();
		}
	}

	/**
	 * 获取App信息
	 * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
	 *
	 * @return 当前应用的AppInfo
	 */
	public static AppInfo getAppInfo() {
		return getAppInfo(Utils.getContext().getPackageName());
	}

	/**
	 * 获取App信息
	 * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
	 *
	 * @param packageName 包名
	 * @return 当前应用的AppInfo
	 */
	public static AppInfo getAppInfo(final String packageName) {
		try {
			PackageManager pm = Utils.getContext().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return getBean(pm, pi);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到AppInfo的Bean
	 *
	 * @param pm 包的管理
	 * @param pi 包的信息
	 * @return AppInfo类
	 */
	private static AppInfo getBean(final PackageManager pm, final PackageInfo pi) {
		if (pm == null || pi == null) return null;
		ApplicationInfo ai = pi.applicationInfo;
		String packageName = pi.packageName;
		String name = ai.loadLabel(pm).toString();
		Drawable icon = ai.loadIcon(pm);
		String packagePath = ai.sourceDir;
		String versionName = pi.versionName;
		int versionCode = pi.versionCode;
		boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
		return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
	}

	/**
	 * 获取所有已安装App信息
	 * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本Code，是否系统应用）</p>
	 * <p>依赖上面的getBean方法</p>
	 *
	 * @return 所有已安装的AppInfo列表
	 */
	public static List<AppInfo> getAppsInfo() {
		List<AppInfo> list = new ArrayList<>();
		PackageManager pm = Utils.getContext().getPackageManager();
		// 获取系统中安装的所有软件信息
		List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
		for (PackageInfo pi : installedPackages) {
			AppInfo ai = getBean(pm, pi);
			if (ai == null) continue;
			list.add(ai);
		}
		return list;
	}

	/**
	 * 清除App所有数据
	 *
	 * @param dirPaths 目录路径
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean cleanAppData(final String... dirPaths) {
		File[] dirs = new File[dirPaths.length];
		int i = 0;
		for (String dirPath : dirPaths) {
			dirs[i++] = new File(dirPath);
		}
		return cleanAppData(dirs);
	}

	/**
	 * 清除App所有数据
	 *
	 * @param dirs 目录
	 * @return {@code true}: 成功<br>{@code false}: 失败
	 */
	public static boolean cleanAppData(final File... dirs) {
		boolean isSuccess = CleanUtils.cleanInternalCache();
		isSuccess &= CleanUtils.cleanInternalDbs();
		isSuccess &= CleanUtils.cleanInternalSP();
		isSuccess &= CleanUtils.cleanInternalFiles();
		isSuccess &= CleanUtils.cleanExternalCache();
		for (File dir : dirs) {
			isSuccess &= CleanUtils.cleanCustomCache(dir);
		}
		return isSuccess;
	}

	private static boolean isSpace(final String s) {
		if (s == null) return true;
		for (int i = 0, len = s.length(); i < len; ++i) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断设备是否root
	 *
	 * @return the boolean{@code true}: 是<br>{@code false}: 否
	 */
	public static boolean isDeviceRooted() {
		String su = "su";
		String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
				"/data/local/xbin/", "/data/local/bin/", "/data/local/"};
		for (String location : locations) {
			if (new File(location + su).exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取设备系统版本号
	 *
	 * @return 设备系统版本号
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}


	/**
	 * 获取设备AndroidID
	 *
	 * @return AndroidID
	 */
	@SuppressLint("HardwareIds")
	public static String getAndroidID() {
		return Settings.Secure.getString(Utils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	/**
	 * 获取设备MAC地址
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
	 *
	 * @return MAC地址
	 */
	public static String getMacAddress() {
		String macAddress = getMacAddressByWifiInfo();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		macAddress = getMacAddressByNetworkInterface();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		macAddress = getMacAddressByFile();
		if (!"02:00:00:00:00:00".equals(macAddress)) {
			return macAddress;
		}
		return "please open wifi";
	}

	/**
	 * 获取设备MAC地址
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
	 *
	 * @return MAC地址
	 */
	@SuppressLint("HardwareIds")
	private static String getMacAddressByWifiInfo() {
		try {
			@SuppressLint("WifiManagerLeak")
			WifiManager wifi = (WifiManager) Utils.getContext().getSystemService(Context.WIFI_SERVICE);
			if (wifi != null) {
				WifiInfo info = wifi.getConnectionInfo();
				if (info != null) return info.getMacAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备MAC地址
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
	 *
	 * @return MAC地址
	 */
	private static String getMacAddressByNetworkInterface() {
		try {
			List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface ni : nis) {
				if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
				byte[] macBytes = ni.getHardwareAddress();
				if (macBytes != null && macBytes.length > 0) {
					StringBuilder res1 = new StringBuilder();
					for (byte b : macBytes) {
						res1.append(String.format("%02x:", b));
					}
					return res1.deleteCharAt(res1.length() - 1).toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备MAC地址
	 *
	 * @return MAC地址
	 */
	private static String getMacAddressByFile() {
		ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
		if (result.result == 0) {
			String name = result.successMsg;
			if (name != null) {
				result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
				if (result.result == 0) {
					if (result.successMsg != null) {
						return result.successMsg;
					}
				}
			}
		}
		return "02:00:00:00:00:00";
	}

	/**
	 * 获取设备厂商
	 * <p>如Xiaomi</p>
	 *
	 * @return 设备厂商
	 */

	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 * 获取设备型号
	 * <p>如MI2SC</p>
	 *
	 * @return 设备型号
	 */
	public static String getModel() {
		String model = Build.MODEL;
		if (model != null) {
			model = model.trim().replaceAll("\\s*", "");
		} else {
			model = "";
		}
		return model;
	}

	/**
	 * 关机
	 * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
	 */
	public static void shutdown() {
		ShellUtils.execCmd("reboot -p", true);
		Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
		intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Utils.getContext().startActivity(intent);
	}

	/**
	 * 重启
	 * <p>需要root权限或者系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
	 *
	 */
	public static void reboot() {
		ShellUtils.execCmd("reboot", true);
		Intent intent = new Intent(Intent.ACTION_REBOOT);
		intent.putExtra("nowait", 1);
		intent.putExtra("interval", 1);
		intent.putExtra("window", 0);
		Utils.getContext().sendBroadcast(intent);
	}

	/**
	 * 重启
	 * <p>需系统权限 {@code <android:sharedUserId="android.uid.system"/>}</p>
	 *
	 * @param reason  传递给内核来请求特殊的引导模式，如"recovery"
	 */
	public static void reboot(final String reason) {
		PowerManager mPowerManager = (PowerManager) Utils.getContext().getSystemService(Context.POWER_SERVICE);
		try {
			mPowerManager.reboot(reason);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重启到recovery
	 * <p>需要root权限</p>
	 */
	public static void reboot2Recovery() {
		ShellUtils.execCmd("reboot recovery", true);
	}

	/**
	 * 重启到bootloader
	 * <p>需要root权限</p>
	 */
	public static void reboot2Bootloader() {
		ShellUtils.execCmd("reboot bootloader", true);
	}
}
