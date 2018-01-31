package gcg.testproject.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.io.IOException;
import java.util.HashMap;

import gcg.testproject.activity.http.Xutils;
import gcg.testproject.common.Word;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 版本更新类
 * @ClassName:UpDataUtils

 * @PackageName:com.xinyi8090.sharism.common.utils

 * @Create On 2018/1/19   9:17

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2018/1/19 handongkeji All rights reserved.
 */

public class UpDataUtils {
    private Context context;

    public UpDataUtils(Context context) {
        this.context = context;
        getVersionInfo();
    }

    public void getVersionInfo(){
        Log.i("test111","getVersionInfo");
        HashMap<String,String> map = new HashMap<>();
        Xutils.getInstance().get("http://api.customer.xinyi8090.cn/version.do", map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {
                valueVersion("2.6.7","http://www.baidu.com","修复部分已知Bug");
            }
        });

    }

    private void valueVersion(String versionNum, String url, String content) {

//        String thisversionNum=AppUtils.getVersionName(context); //当前的软件版本名
//        if ( versionNum.compareToIgnoreCase(thisversionNum)>0 ){
//            String versionInfoStr="版本号："+versionNum+"\n"+"版本描述："+content;
//            initupUpdate(versionInfoStr,url);
//        }
        String versionInfoStr="版本号："+versionNum+"\n"+"版本描述："+content;
        initupUpdate(versionInfoStr,url);

    }

    //版本更新弹框
    private void initupUpdate(String versionInfo, final String appPathload){
        Log.i("test111","initupUpdate");
        final Activity activity = (Activity) context;
        new SystemDialogUtils().showMissingPermissionDialog(
                context,
                "提示",
                "版本更新："+versionInfo,
                "确定",
                "取消", new SystemDialogUtils.AfterClick() {
                    @Override
                    public void confirm() {
                        //请求存储权限
                        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                        if (!hasPermission) {
                            Log.i("test111","没有权限");

                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        } else {
                            Log.i("test111","有权限");
                            DownLoadTask task=new DownLoadTask(appPathload, activity);
                            task.execute();
                        }
                    }

                    @Override
                    public void cancle() {
                    }
                });
    }
}
