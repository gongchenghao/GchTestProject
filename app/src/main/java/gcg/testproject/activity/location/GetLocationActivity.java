package gcg.testproject.activity.location;

import android.Manifest;
import android.content.Intent;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.service.LocationService;
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
                    ToastUtils.showShort(GetLocationActivity.this,"打开了位置服务");
                    boolean b = verifyPermissions(permissions); //检查是否具有所需的权限
                    if (b) {
                        startService(new Intent(GetLocationActivity.this, LocationService.class));
                    }else {
                        ActivityCompat.requestPermissions(GetLocationActivity.this, permissions, 123); //申请权限
                    }
                } else {
                    ToastUtils.showShort(GetLocationActivity.this,"关闭了位置服务");
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
                startService(new Intent(GetLocationActivity.this, LocationService.class));
            }else {
                findPhone.setChecked(false);
                showMissingPermissionDialog();
            }
        }
    }
}
