package gcg.testproject.utils;

import java.io.File;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * SD卡相关的辅助类
 * 
 * @author zhy
 * 
 */
public class SDCardUtils
{
	private SDCardUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable()
	{
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize()
	{
		if (isSDCardEnable())
		{
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath)
	{
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath()))
		{
			filePath = getSDCardPath();
		} else
		{// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * 获取SD卡data路径
	 *
	 * @return SD卡data路径
	 */
	public static String getDataPath() {
		if (!isSDCardEnable()) return null;
		return Environment.getExternalStorageDirectory().getPath() + File.separator + "data" + File.separator;
	}

	/**
	 * 获取SD卡剩余空间
	 *
	 * @return SD卡剩余空间
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static String getFreeSpace() {
		if (!isSDCardEnable()) return null;
		StatFs stat = new StatFs(getSDCardPath());
		long blockSize, availableBlocks;
		availableBlocks = stat.getAvailableBlocksLong();
		blockSize = stat.getBlockSizeLong();
		return ConvertUtils.byte2FitMemorySize(availableBlocks * blockSize);
	}

	/**
	 * 获取SD卡信息
	 *
	 * @return SDCardInfo
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static String getSDCardInfo() {
		if (!isSDCardEnable()) return null;
		SDCardInfo sd = new SDCardInfo();
		sd.isExist = true;
		StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
		sd.totalBlocks = sf.getBlockCountLong();
		sd.blockByteSize = sf.getBlockSizeLong();
		sd.availableBlocks = sf.getAvailableBlocksLong();
		sd.availableBytes = sf.getAvailableBytes();
		sd.freeBlocks = sf.getFreeBlocksLong();
		sd.freeBytes = sf.getFreeBytes();
		sd.totalBytes = sf.getTotalBytes();
		return sd.toString();
	}

	public static class SDCardInfo {
		boolean isExist;
		long    totalBlocks;
		long    freeBlocks;
		long    availableBlocks;
		long    blockByteSize;
		long    totalBytes;
		long    freeBytes;
		long    availableBytes;

		@Override
		public String toString() {
			return "isExist=" + isExist +
					"\ntotalBlocks=" + totalBlocks +
					"\nfreeBlocks=" + freeBlocks +
					"\navailableBlocks=" + availableBlocks +
					"\nblockByteSize=" + blockByteSize +
					"\ntotalBytes=" + totalBytes +
					"\nfreeBytes=" + freeBytes +
					"\navailableBytes=" + availableBytes;
		}
	}

}
