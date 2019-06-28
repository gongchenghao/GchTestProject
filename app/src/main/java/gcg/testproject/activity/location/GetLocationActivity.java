package gcg.testproject.activity.location;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.service.LocationService;
import gcg.testproject.utils.LocationUtils;
import gcg.testproject.utils.ToastUtils;

/**
 * 定位
 *
 * @ClassName:GetLocationActivity
 * @PackageName:gcg.testproject.activity.location
 * @Create On 2018/1/29   18:32
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/29 宫成浩 All rights reserved.
 */

//注意：用的是高德定位，需要在高德开放平台上注册账号，然后创建应用，申请应用
public class GetLocationActivity extends BaseActivity {

    @Bind(R.id.findPhone)
    Switch findPhone;
    private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //打开找回手机功能
        findPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    boolean b = verifyPermissions(permissions); //检查是否具有所需的权限
                    if (b) {
                        forAndroidSdk8();
                    }else {
                        ActivityCompat.requestPermissions(GetLocationActivity.this, permissions, 123); //申请权限
                    }
                } else {
                    //关闭服务
                    stopService(new Intent(GetLocationActivity.this, LocationService.class));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123) {
            boolean b = verifyPermissions(permissions); //检查是否具有所需的权限
            if (b) {
                forAndroidSdk8();
            }else {
                findPhone.setChecked(false);
                showMissingPermissionDialog();
            }
        }
    }

    //Android8.0以后，无论是否获取到了权限，都必须打开手机的位置信息才能定位成功
    private void forAndroidSdk8()
    {
        //如果是8.0，则需要打开位置服务才能获取位置，否则即便获取到定位权限，也获取不到位置信息
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) //如果是8.0系统，则判断是否缺少悬浮框权限和自动更新权限
        {
            boolean locationEnabled = LocationUtils.isLocationEnabled();
            //如果允许就开始定位，如果不允许就跳转到打开定位的界面
            if(locationEnabled)
            {
                startService(new Intent(GetLocationActivity.this, LocationService.class));
            }else {
                findPhone.setChecked(false); //设置为未选中
                LocationUtils.openGpsSettings();
            }
        }else {
            ToastUtils.showShort(GetLocationActivity.this,"即将跳转到设置");
            startService(new Intent(GetLocationActivity.this, LocationService.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止定位
        stopService(new Intent(GetLocationActivity.this,LocationService.class));
    }
}
