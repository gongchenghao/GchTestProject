package gcg.testproject;

import gcg.testproject.dialog.MySimpleDialog;
import gcg.testproject.dialog.ProgressDialog;

/**
 *
 * @ClassName:Guide

 * @PackageName:gcg.testproject

 * @Create On 2018/1/25   11:31

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2018/1/25 宫成浩 All rights reserved.
 */


public class Guide {
//  ================================================= 本项目各个包使用说明 ================================================

// ================================================== utils包工具类使用指南 ===============================================

//    DensityUtils : dp、px、sp互相转换

//    WebViewUtils : WebView使用工具类

//    OpenFileUtils : 打开文件

//    GetSqurePic : 获取正方形图片、获取长方形图片

//    GetPhoneMessage : 获取手机信息

//    GetRoundPicUtils : 获取圆角图片、获取圆形图片、放大缩小图片、将Drawable转化为Bitmap、获得带倒影的图片

//    IconUtil : 设置圆形头像

//    CheckUtils : 检查手机号、身份证号、字符串是否为数字、设置地区编码、验证日期字符串是否是YYYY-MM-DD格式、邮箱、银行卡号、是否是中文、是否包含中文、验证URL、判断是否匹配正则、
//                 验证IP地址、替换所有正则匹配的部分、获取正则匹配的部分、获取正则匹配分组、替换正则匹配的第一部分

//    CacheManager ： 获取缓存大小、清理缓存、获取文件大小、格式化单位

//    GetUpLoadPicUtils ：上传图片时对图片进行压缩（掌一眼中用）

//    TextViewUtils ：处理TextView中文字换行的工具类

//    VideoPlayUtils ：视频播放工具类

//    DBHelper、DBHelperDAO ：SQLite数据库工具类

//    DownLoadUtils ：下载文件的工具类

//    ListViewHeightBasedOnChildren ：重新绘制列表条目的高度

//    AppUtils ：获取应用程序名称、获取应用程序版本名称信息、判断App是否安装、安装App、静默安装App、卸载App、静默卸载App、判断App是否有root权限、打开App、获取App包名、
//               获取App具体设置、获取App名称、获取App图标、获取App路径、获取App版本号、判断App是否是系统应用、判断App是否是Debug版本、获取App签名、获取应用签名的的SHA1值
//               判断App是否处于前台、封装App信息的Bean类、得到AppInfo的Bean、获取所有已安装App信息、清除App所有数据、判断设备是否root、获取设备系统版本号、获取设备AndroidID
//               获取设备MAC地址、获取设备厂商、关机、重启、重启到recovery、重启到bootloader、

//    FileSizeUtil ：获取文件或文件夹的大小

//    KeyBoardUtils ：打开或关闭软键盘

//    LogUtils ：日志打印工具类

//    MoveUtils ：跳转工具类

//    NetUtils ：判断是否联网、是否是WIFI、跳转到网络设置界面

//    ScreenUtils ：获取屏幕高度、宽度、状态栏高度、 获取当前屏幕截图（包含状态栏）、获取当前屏幕截图（不包含状态栏）、设置屏幕为横屏、设置屏幕为竖屏、判断是否横屏、判断是否竖屏、
//                  获取屏幕旋转角度、判断是否锁屏、设置进入休眠时长、获取进入休眠时长

//    SDCardUtils ：判断SD卡是否可用、获取SD卡路径、获取SD卡的剩余容量(单位byte)、获取指定路径所在空间的剩余可用容量字节数(单位byte)、获取系统存储路径、获取SD卡data路径、获取SD卡剩余空间
//                  获取SD卡信息

//    ToastUtils ：显示短时间的toast、长时间的toast、自定义时间的toast、自定义UI的toast

//    TimeUtils ：格式化时间戳的工具类 获取星座、获取生肖、获取年份中的第几周、获取星期索引、 获取美式星期、获取中式星期、判断是否闰年、判断是否今天、 获取与当前时间等于时间差的Date
//                获取与当前时间等于时间差的时间字符串、获取与给定时间等于时间差的Date、获取与给定时间等于时间差的时间字符串、获取与给定时间等于时间差的时间戳、获取友好型与当前时间的差
//                获取合适型与当前时间的差、获取当前Date、获取当前时间字符串、获取当前毫秒时间戳、将时间戳转为Date类型、将Date类型转为时间戳、将Date类型转为时间字符串
//                将时间字符串转为Date类型、将时间字符串转为时间戳、将时间戳转为时间字符串、

//    ActivityUtils ：判断是否存在Activity、启动Activity、获取launcher activity、获取栈顶Activity

//    BarUtils ：设置状态栏颜色、为滑动返回界面设置状态栏颜色、设置状态栏纯色(不加半透明效果)、使状态栏半透明、设置状态栏全透明、隐藏伪状态栏、判断状态栏是否存在、
//               获取ActionBar高度、显示通知栏、 隐藏通知栏、获取导航栏高度

//    CameraUtils ：获取打开照程序界面的Intent、获取跳转至相册选择界面的Intent、获取[跳转至相册选择界面,并跳转至裁剪界面，默认可缩放裁剪区域]的Intent
//                  获取[跳转至相册选择界面,并跳转至裁剪界面，可以指定是否缩放裁剪区域]的Intent、获得选中相册的图片、获得选中相册的图片路径、获取拍照之后的照片文件（JPG格式）

//    ClipboardUtils ：剪贴板相关工具类，复制文本到剪贴板、获取剪贴板的文本、复制uri到剪贴板、获取剪贴板的uri、复制意图到剪贴板、获取剪贴板的意图

//    CloseUtils ：关闭IO、安静关闭IO

//    CrashUtils ：崩溃相关工具类

//    EncodeUtils ：编码解码相关工具类 URL编码、URL解码、Base64编码、Base64解码、Base64URL安全编码、Html编码、Html解码

//    EncryptUtils ：加密解密工具类 MD2加密、MD5加密、MD5加密文件、SHA1加密、SHA224加密、SHA256加密、SHA384加密、SHA512加密、hash加密模板、HmacMD5加密
//                   HmacSHA1加密、HmacSHA224加密、HmacSHA256加密、HmacSHA384加密、HmacSHA512加密、Hmac加密模板、DES转变、DES加密后转为Base64编码、
//                   DES加密后转为16进制、DES加密、DES解密Base64编码密文、DES解密16进制密文、DES解密、3DES转变、3DES加密后转为Base64编码、3DES加密后转为16进制、
//                   3DES加密、3DES解密Base64编码密文、3DES解密16进制密文、3DES解密、AES转变、AES加密后转为Base64编码、AES加密后转为16进制、AES加密、AES解密Base64编码密文
//                   AES解密16进制密文、AES解密、DES加密模板

//    FileIOUtils ：将输入流写入文件、将字节数组写入文件、将字符串写入文件、读取文件到字符串链表中、读取文件到字符串中、读取文件到字节数组中、设置缓冲区尺寸、

//    FileUtils ：根据文件路径获取文件、判断文件是否存在、重命名文件、判断是否是目录、判断是否是文件、判断目录是否存在，不存在则判断是否创建成功、
//                判断文件是否存在，不存在则判断是否创建成功、判断文件是否存在，存在则在创建之前删除、复制或移动目录、复制或移动文件、复制目录、复制文件、移动目录、
//                移动文件、删除目录、删除文件、删除目录下的所有文件、获取目录下所有文件、获取目录下所有文件包括子目录、根据后缀名获取目录下所有后缀名文件、
//                根据后缀名获取目录下所有文件包括子目录、获取目录下所有符合FilenameFilter的文件、获取目录下所有符合FilenameFilter的文件包括子目录
//                获取目录下指定文件名的文件包括子目录、获取文件最后修改的毫秒时间戳、简单获取文件编码格式、获取文件行数、获取目录大小、获取文件大小、获取目录长度、获取文件长度
//                获取文件的MD5校验码、获取全路径中的最长目录、获取全路径中的文件名、获取全路径中的不带拓展名的文件名、获取全路径中的文件拓展名、

//    FragmentUtils ：新增fragment、先隐藏后新增fragment、新增多个fragment、移除fragment、移除到指定fragment、移除同级别fragment、移除所有fragment、替换fragment、
//                    出栈fragment、出栈到指定fragment、出栈同级别fragment、出栈所有fragment、先出栈后新增fragment、隐藏fragment、隐藏同级别fragment、显示fragment、
//                    先隐藏后显示fragment、传参、获取参数、操作fragment、获取同级别最后加入的fragment、根据栈参数获取同级别最后加入的fragment、 获取顶层可见fragment、
//                    获取栈中顶层可见fragment、根据栈参数获取顶层可见fragment、获取同级别fragment、获取栈中同级别fragment、根据栈参数获取同级别fragment、
//                    获取所有fragment、获取栈中所有fragment、根据栈参数获取所有fragment、获取目标fragment的前一个fragment、查找fragment、处理fragment回退键、
//                    设置背景色、设置背景资源、设置背景、

//    ImageUtils ：bitmap转byte[]、byte[]转bitmap、drawable转bitmap、bitmap转drawable、drawable转byte[]、byte[]转drawable、view转Bitmap、计算采样大小、获取bitmap
//                 缩放图片、裁剪图片、倾斜图片、旋转图片、获取图片旋转角度、转为圆形图片、转为圆角图片、快速模糊、renderScript模糊图片、stack模糊图片、添加颜色边框、
//                 添加倒影、添加文字水印、添加图片水印、转为alpha位图、转为灰度图片、保存图片、根据文件名判断文件是否为图片、获取图片类型、流获取图片类型、判断bitmap对象是否为空
//                 按缩放压缩、按质量压缩、按采样大小压缩、

//    LocationUtils ：定位工具类  判断Gps是否可用、判断定位是否可用、打开Gps设置界面、注册、注销、设置定位参数、根据经纬度获取地理位置、根据经纬度获取所在国家、
//                    根据经纬度获取所在地、根据经纬度获取所在街道、是否更好的位置、是否相同的提供者、

//    LunarUtils ：日历工具类  根据农历年份获取天干地支、农历转公历、公历转农历、

//    PhoneUtils ：手机相关工具类 判断设备是否是手机、获取IMEI码、获取移动终端类型、判断sim卡是否准备好、获取Sim卡运营商名称、跳至拨号界面、拨打电话、跳至发送短信界面、
//                 发送短信、获取手机联系人、打开手机联系人界面点击联系人后便获取该号码、获取手机短信并保存到xml中、

//    PinyinUtils ：拼音相关工具类 汉字转拼音、获取第一个汉字首字母、获取所有汉字的首字母、根据名字获取姓氏的拼音、根据名字获取姓氏的首字母、多音字姓氏映射表、

//    ProcessUtils ：进程相关工具类 获取前台线程包名、获取后台服务进程、杀死所有的后台服务进程、杀死后台服务进程、

//    ServiceUtils ：服务相关工具类 获取所有运行的服务、启动服务、停止服务、绑定服务、解绑服务、判断服务是否运行

//    ShellUtils ：Shell相关工具类 是否是在root下执行命令、返回的命令结果

//    GetViewSizeUtils : 在onCreate中获取视图的尺寸、获取到View尺寸的监听、测量视图尺寸、获取测量视图宽度、获取测量视图高度

//    SPUtils : 获取SP实例、写入String、读取String、写入int、读取int、写入long、读取long、写入float、读取float、写入boolean、读取boolean、写入String集合、
//              读取StringSet、获取所有键值对、是否存在该key、移除该key、清除所有数据

//    StringUtils : 判断字符串是否为null或长度为0、判断字符串是否为null或全为空格、判断字符串是否为null或全为空白字符、判断两字符串是否相等、判断两字符串忽略大小写是否相等、
//                  null转为长度为0的字符串、返回字符串长度、首字母大写、首字母小写、反转字符串、转化为半角字符、转化为全角字符

//    ThreadPoolUtils ：线程池相关工具类  在未来某个时间执行给定的命令、在未来某个时间执行给定的命令链表、待以前提交的任务执行完毕后关闭线程池、试图停止所有正在执行的活动任务、
//                      判断线程池是否已关闭、关闭线程池后判断所有任务是否都已完成、请求关闭发生超时或者当前线程中断、提交一个Callable任务用于执行、提交一个Runnable任务用于执行、
//                      执行给定的任务、延迟执行Runnable命令、延迟执行Callable命令、延迟并循环执行命令、延迟并以固定休息时间循环执行命令

//    VibrationUtils ：震动相关工具类  震动、指定手机以pattern模式振动、取消振动

//    ZipUtils ：文件压缩相关工具类  批量压缩文件、压缩文件、批量解压文件、解压文件、解压带有关键字的文件、获取压缩文件中的文件路径链表、获取压缩文件中的注释链表、
//               获取压缩文件中的文件对象、



// ============================================== widget包自定义控件 ============================================

//    OvalImageView ：加载圆角矩形的图片

//    XCRoundRectImageView ：加载圆角矩形的图片

//    TimeTextView ： 列表中用到的计时器

//    ZoomImageView ：可缩放图片的ImageView

//    OScrollView ：自定义ScrollView，是为了解决在ScrollView中嵌套viewpager时viewpager滑动不了的问题

//    OListView ：自定义listview，为了解决在ScrollView中嵌套listview时显示不全的问题

//    OGridView ：自定义gridview，为了解决在ScrollView中嵌套gridview时显示不全的问题

//    ORecycleView ：自定义gridview，为了解决在ScrollView中嵌套gridview时显示不全的问题

//    CustomVideoView ：使用videoview播放视频时，如果视频的分辨率不够，右侧可能会有白边，这个自定义videoview，修复了这个问题(播放视频)
//    (videoview是Android自身为我们提供的视频播放的组件，但是它仅支持播放mp4和3gp格式的文件，能播放的视频文件格式非常少，建议使用surfaceView结合mediaplayer播放视频)

//    VideoPlayProgressBar ：自定义进度条，可以用于播放视频时的进度显示

//    NoScrollGridView : 解决滑动冲突的GirdView

//    NoScrollListView ：解决滑动冲突的ListView

//    RoundImageView : 圆形头像

//    SquareImage : 方形头像

//    MyRatingBar ：自定义ratingbar,可以改变星星的样式、大小

//    ListSlideView ：自定义ListView，可以单个条目侧滑删除

//    NoScrollViewPager ：自定义ViewPager，不可滑动




//  =========================================== selectphoto包 =============================
//  选择照片的功能，包括拍摄照片和选择本地照片


//  ============================================ selectvideo包 ==============================
//  选择视频的功能，包括拍摄视频和选择本地视频，支持选择后进行播放


//  ============================================ banner包 =================================
//  轮播图


//  =============================== selectdate包 =============================================
//  日期选择器1


//  =============================== selectdate2包 ============================================
//  日期选择器2


//  ================================ contactlist包 ============================================
//  仿联系人列表


//  ================================ dialog包 =================================================
//  ProgressDialog : 获取数据时的加载提示框

//  MySimpleDialog ：功能性对话框

//  ================================ RightTopPopWindow包 ======================================
//  仿微信右上角弹框


//  ================================ SplashAndGuide包 =========================================
//  Splash界面和Guide界面


//  ================================ progressbar包 ============================================
//  掌一眼中的圆形进度条
//  Android自带的progressbar进度条的两种样式


// ================================= ratingbar包 ==============================================
// 系统自带的ratingbar
// 自定义ratingbar,可以控制星星的大小和间距

// ================================= uodate包 =================================================
// 版本更新

// ================================= sanji包 ==================================================
// 省市区三级联动


// ================================= erweima包 ================================================
// 扫描二维码的功能，支持打开闪光灯，自定义扫码界面


// ================================= http包 ===================================================
// Xutils工具类，是xutils网络框架的封装类，包括六个个方法，即普通异步get请求，普通异步post请求，带缓存的异步get请求，带缓存的异步post请求，文件上传，文件下载


// ================================= RefreshAndLoadMore =======================================
// 上拉加载、下拉刷新功能 注意点：里面有五个“注意点”，是必须调用的


// ================================= ListViewDelete包 ==========================================
// listview的条目删除功能，可以整体侧滑删除，也可以单个侧滑删除


// ================================= ListCountDown包 ==========================================
// listview的列表倒计时


// ================================= location包 ===============================================
// 高德定位
}
