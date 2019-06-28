/**
 * ClassName: Constants.java
 * created on 2013-1-24
 * Copyrights 2013-1-24 hjgang All rights reserved.
 * site: http://t.qq.com/hjgang2012
 * email: hjgang@yahoo.cn
 */
package gcg.testproject.common;

import android.content.Context;

import java.io.File;

/**
 * @author gongchenghao
 * @日期 2017-12-25
 */

public final class Constants {
    /**
     * 系统初始化配置文件名
     */
    public static final String SYSTEM_INIT_FILE_NAME = "gch.android.sysini";
    public static final String FLAG = "com.gch.android";


    /**
     * 图片类型
     */
    public static final String IMAGE_UNSPECIFIED = "image/*";
    /**
     * 本地缓存目录
     */
    public static String CACHE_DIR;
    /**
     * 图片缓存目录
     */
    public static String CACHE_DIR_IMAGE;
    /**
     * 待上传图片缓存目录
     */
    public static String CACHE_DIR_UPLOADING_IMG;
    /**
     * 图片目录
     */
    public static String CACHE_IMAGE;
    /**
     * 图片名称
     */
    public static final String PHOTO_PATH = "handongkeji_android_photo";
    /**
     * 语音缓存目录
     */
    public static String CACHE_VOICE;

    public static void init(Context context) {
        CACHE_DIR = context.getCacheDir().getAbsolutePath();
        File file = new File(CACHE_DIR, "image");
        file.mkdirs();
        CACHE_IMAGE = file.getAbsolutePath();
        CACHE_DIR_IMAGE = CACHE_IMAGE;
        file = new File(CACHE_DIR, "temp");
        file.mkdirs();
        CACHE_DIR_UPLOADING_IMG = file.getAbsolutePath();
        file = new File(CACHE_DIR, "voice");
        file.mkdirs();
        CACHE_VOICE = file.getAbsolutePath();

        file = new File(CACHE_DIR, "html");
        file.mkdirs();
        ENVIROMENT_DIR_CACHE = file.getAbsolutePath();
    }


    public static String ENVIROMENT_DIR_CACHE;

    private Constants() {

    }

    /**
     * 数据库版本号
     */
    public static final int DB_VERSION = 1;
    /**
     * 数据库名
     */
    public static final String DB_NAME = "android.db";

    //接口的Base url
    public static final String URL_CONTEXTPATH = "http://app.newtonapple.cn/zhangyiyan/";

    //银河英雄传说：图片服务器地址
    public static final String URL_IMAGE = "http://www.hhhxin.com/";


//    ==============================================================================================================

    //微信支付回调url(掌一眼中未用此地址)
    public static String wxUrl =URL_CONTEXTPATH+
            "wxpay/getnotify.json";

    //微信下单(掌一眼)
    public static String wxOrder =URL_CONTEXTPATH+
            "wxpay/auth/placeOrder.json";

    //第三方登录(掌一眼)
    public static final String URL_SAN_FANG_DENG_LU = URL_CONTEXTPATH +
            "mbuser/loginByOpenNew.json";

    //大家看列表(掌一眼)
    public static final String URL_DA_JIA_LIE_BIAO = URL_CONTEXTPATH +
            "look/looklist.json";


}
