package gcg.testproject.activity.notifycation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

//参考链接：https://www.jianshu.com/p/f2cf23d6d836
public class NotifycationActivity extends BaseActivity{

    @Bind(R.id.tv_send)
    TextView mTvSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifycation);
        ButterKnife.bind(this);
        //关闭通知栏上的通知
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(5);
    }
}
