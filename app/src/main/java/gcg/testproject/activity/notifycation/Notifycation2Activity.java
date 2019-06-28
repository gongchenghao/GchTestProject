package gcg.testproject.activity.notifycation;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

public class Notifycation2Activity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_common)
    TextView mTvCommon;
    @Bind(R.id.tv_remove)
    TextView mTvRemove;
    @Bind(R.id.tv_bigicon)
    TextView mTvBigicon;
    @Bind(R.id.tv_progress)
    TextView mTvProgress;
    @Bind(R.id.tv_multi)
    TextView mTvMulti;
    @Bind(R.id.tv_custom)
    TextView mTvCustom;

    private int num_else;
    private int num;
    private String id = "my_channel_01";
    private String name="我是渠道名字";
    private NotificationChannel mChannel;
    private NotificationManager manager; //通知管理器，用于发送通知Notification对象
    private boolean isO; //用来判断是不是8.0系统

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifycation2);
        ButterKnife.bind(this);

        mTvCommon.setOnClickListener(this);
        mTvRemove.setOnClickListener(this);
        mTvBigicon.setOnClickListener(this);
        mTvProgress.setOnClickListener(this);
        mTvMulti.setOnClickListener(this);
        mTvCustom.setOnClickListener(this);

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            isO = true;
            mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setBypassDnd(true); //设置绕过免打扰模式
            mChannel.canBypassDnd(); //检测是否绕过免打扰模式
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);//设置在锁屏界面上显示这条通知

            //设置通知出现时的呼吸灯
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setLightColor(Color.WHITE);

            //卸载重装，让设置的震动生效  注意：设置震动时不用动态申请震动权限，只在清单文件中声明即可
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            Uri mUri = Settings.System.DEFAULT_NOTIFICATION_URI;
            mChannel.setSound(mUri, Notification.AUDIO_ATTRIBUTES_DEFAULT);


            //自定义声音    注意：要想让设置的声音生效，必须卸载重装
//            AudioAttributes.Builder audioAttributesBuilder = new AudioAttributes.Builder();
//            audioAttributesBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
//            AudioAttributes aa = audioAttributesBuilder.build();
//            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.umeng_push_notification_default);
//            mChannel.setSound(uri, aa);
            manager.createNotificationChannel(mChannel);
        } else {
            isO = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_common://发送一个普通通知
                LogUtils.i("送一个普通通知");
                if (isO)
                {
                    btn_8_common();
                }else {
                    else_common();
                }
                break;
            case R.id.tv_remove://移除通知
                LogUtils.i("移除通知");
//                manager.cancel(5);
                manager.cancelAll();
                break;
            case R.id.tv_bigicon://设置大图通知
                LogUtils.i("设置大图通知");
                if (isO)
                {
                    btn_8_bigicon();
                }else {
                    else_big();
                }
                break;
            case R.id.tv_progress://发送带进度条的通知
                LogUtils.i("发送带进度条的通知");
                if (isO)
                {
                    btn_8_progress();
                }else {
                    else_progress();
                }
                break;
            case R.id.tv_multi://封装多行文本样式
                LogUtils.i("封装多行文本样式");
                if (isO)
                {
                    btn_8_multi();
                }else {
                    else_multi();
                }
                break;
            case R.id.tv_custom://完全自定义的通知
                LogUtils.i("完全自定义的通知");
                if (isO)
                {
                    btn_8_custom();
                }else {
                    else_custom();
                }
                break;
        }
    }
//    ========================== 以下是8.0以下的通知 ========================================
    private void else_common(){
        LogUtils.i("else_common");
//        注意：Android3.0之前使用new Notification()创建Notification对象
//        Notification notification = new Notification();
//        Android Support v4:这个包是为了照顾1.6及更高版本而设计的，这个包是使用最广泛的。
//        Android Support v7:这个包是为了考虑照顾2.1及以上版本而设计的，但不包含更低，故如果不考虑1.6,
//        我们可以采用再加上这个包，另外注意，v7是要依赖v4这个包的，即，两个得同时被包含。
//        Android Support v13:这个包的设计是为了android 3.2及更高版本的，一般我们都不常用，平板开发中能用到

        //Android3.0之后用
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);
        builder.setContentTitle("设置标题" + System.currentTimeMillis());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));//设置大图标
        builder.setContentText("内容文本部分");
        builder.setContentInfo("info信息");//设置info信息，即设置显示在时间右下角的文字
        builder.setSmallIcon(R.mipmap.ic_launcher);//必须要设置的小图标
        builder.setWhen(System.currentTimeMillis());//设置通知时间
        builder.setTicker("设置滚动提示的文字设置滚动提示的文字设置滚动提示的文字设置滚动提示的文字设置滚动提示的文字");
        builder.setOngoing(false); //设置左右滑动时是否会消失
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND); //设置默认铃声
        //设置自定义铃声
//        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.umeng_push_notification_default)); //调用系统多媒体裤内的铃声

        //设置震动;部分手机必须打开震动功能才能震动
        long[] vibrate = new long[]{0, 500, 500, 500,500};
        builder.setVibrate(vibrate);
//        builder.setDefaults(Notification.DEFAULT_VIBRATE); //设置默认震动
//          builder.setDefaults(Notification.DEFAULT_ALL);

        //设置呼吸灯（不是闪光灯）；注意：颜色跟设备有关，不是所有的颜色都可以，要看具体设备。
        //注意：闪烁时长和熄灭时长也会跟设备有关，不完全跟设置的一样
        builder.setLights(0xff0000ff,300,0);

        num_else++;
        manager.notify(num_else, builder.build());   //发送通知
    }

    //部分手机的大图通知在6.0系统上亲测不能显示大图，如果要显示大图，建议设置自定义view
    private void else_big()
    {
        LogUtils.i("else_big");
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.t2);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.t1)); //设置大图标
        builder.setContentTitle("大图片通知");
        builder.setContentText("内容文本部分");
        builder.setTicker("大图通知来了");
        builder.setContentInfo("info信息");//设置info信息，即设置显示在时间右下角的文字
        builder.setDefaults(Notification.DEFAULT_SOUND); //设置默认铃声
        manager.notify(7, builder.build());
    }

    private void else_progress()
    {
        LogUtils.i("else_progress");
        final NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("带进度条的通知");
        builder.setProgress(100,0,false);
        builder.setDefaults(Notification.DEFAULT_SOUND); //设置默认铃声
        manager.notify(3, builder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    builder.setProgress(100, i, false);
                    manager.notify(3, builder.build());
                    SystemClock.sleep(200);
                    if (i == 100) //当进度条走完后，清除这个通知
                    {
                        manager.cancel(3);
                    }
                }
            }
        }).start();
    }
    private void else_multi()
    {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (int i = 0; i < 10; i++) {
            inboxStyle.addLine("这是第" + i + "行" + "文本");
        }
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("多行文本");
        inboxStyle.setBigContentTitle("多行文本标题");
        builder.setStyle(inboxStyle);
        builder.setDefaults(Notification.DEFAULT_SOUND); //设置默认铃声
        manager.notify(4, builder.build());
    }
    private void else_custom()
    {
        RemoteViews views = new RemoteViews(getPackageName(),R.layout.remote);
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);
        builder.setContent(views);
        builder.setSmallIcon(R.mipmap.ic_launcher); //设置了setContent属性后，setSmallIcon就不生效了
        builder.setContentTitle("自定义布局");//设置了setContent属性后，setContentTitle就不生效了
        builder.setContentText("点击联系人行可跳转");//设置了setContent属性后，setContentText就不生效了
        builder.setAutoCancel(true); //设置点击通知自动消失,8.0时并没有用
        builder.setOngoing(false); //true:左右滑动时通知不会消失  false：左右滑动时可以滑动消失
        builder.setDefaults(Notification.DEFAULT_SOUND); //设置默认铃声

        Notification build = builder.build();
        build.flags = Notification.FLAG_AUTO_CANCEL;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                new Intent(Notifycation2Activity.this, NotifycationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        //当前设置代表点击id为R.id.but_re的按钮时，要执行跳转到TwoActivity页面的操作
        views.setOnClickPendingIntent(R.id.item, pendingIntent);
        manager.notify(5, build);
    }

//    ========================== 以下是8.0上的通知 ==========================================
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void btn_8_common() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setChannelId(id); //8.0必须设置信道
        builder.setContentTitle("设置标题" + System.currentTimeMillis());
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));//设置大图标
        builder.setContentText("内容文本部分");
        builder.setContentInfo("info信息");//设置info信息，即设置显示在时间右下角的文字
        builder.setSmallIcon(R.mipmap.ic_launcher);//必须要设置的小图标
        builder.setWhen(System.currentTimeMillis());//设置通知时间
        builder.setTicker("设置滚动提示的文字");
        builder.setOngoing(false);
        num++;
        manager.notify(num, builder.build());   //发送通知
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void btn_8_bigicon() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setChannelId(id); //8.0必须设置信道
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.t1)); //设置大图标
        builder.setContentTitle("大图片通知");
        builder.setContentText("内容文本部分");
        builder.setTicker("大图通知来了");
        manager.notify(7, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void btn_8_progress() {
        final Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("带进度条的通知");
        builder.setProgress(100,0,false);
        builder.setChannelId(id); //8.0必须设置信道

        manager.notify(3, builder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    builder.setProgress(100, i, false);
                    manager.notify(3, builder.build());
                    SystemClock.sleep(500);
                }
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void btn_8_multi() {
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
        for (int i = 0; i < 10; i++) {
            inboxStyle.addLine("这是第" + i + "行" + "文本");
        }
        Notification.Builder builder = new Notification.Builder(this);
        builder.setChannelId(id);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("多行文本");
        inboxStyle.setBigContentTitle("多行文本标题");
        builder.setStyle(inboxStyle);
        manager.notify(4, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void btn_8_custom() {
        RemoteViews views = new RemoteViews(getPackageName(),R.layout.remote);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContent(views);
        builder.setSmallIcon(R.mipmap.ic_launcher); //设置了setContent属性后，setSmallIcon就不生效了
        builder.setContentTitle("自定义布局");//设置了setContent属性后，setContentTitle就不生效了
        builder.setContentText("点击联系人行可跳转");//设置了setContent属性后，setContentText就不生效了
        builder.setChannelId(id);
        builder.setAutoCancel(true); //设置点击通知自动消失,8.0时并没有用
        builder.setOngoing(false); //true:左右滑动时通知不会消失  false：左右滑动时可以滑动消失

        Notification build = builder.build();
        build.flags = Notification.FLAG_AUTO_CANCEL;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1,
                new Intent(Notifycation2Activity.this, NotifycationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        //当前设置代表点击id为R.id.but_re的按钮时，要执行跳转到TwoActivity页面的操作
        views.setOnClickPendingIntent(R.id.item, pendingIntent);
        manager.notify(5, build);
    }
}
