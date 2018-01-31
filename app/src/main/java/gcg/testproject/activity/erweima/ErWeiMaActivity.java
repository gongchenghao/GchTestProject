package gcg.testproject.activity.erweima;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.erweima.zxing.CaptureActivity;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

public class ErWeiMaActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_saomiao)
    TextView tvSaomiao;

    public final static int SCANNING_REQUEST_CODE = 1;
    private String[] mPermissionList = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_wei_ma);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        tvSaomiao.setOnClickListener(this);
    }


    //检查权限
    private void checkPromission() {
        LogUtils.i("checkPromission");
        if (Build.VERSION.SDK_INT >= 23) { //判断是否是Android6.0以上的系统，如果是，就申请权限
            if (verifyPermissions(mPermissionList)) //有权限
            {
                startSaoMiao();
            } else {
                ActivityCompat.requestPermissions(this, mPermissionList, 123); //申请权限
            }
        } else { //小于23，不用管
            startSaoMiao();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!verifyPermissions(permissions)) { //未申请到权限
            showMissingPermissionDialog();
        } else {
            startSaoMiao();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_saomiao:
                //检查权限，这个方法写在点击事件中
                checkPromission();
                break;
        }
    }
    //开始扫描
    private void startSaoMiao() {
        Intent intent = new Intent(ErWeiMaActivity.this, CaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNING_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    final Bundle bundle = data.getExtras();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tvSaomiao.setText(bundle.getString("result"));
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

}
