package gcg.testproject.activity.SelectVideo;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;
import gcg.testproject.utils.LogUtils;

public class SelectVideoActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.iv_video)
    ImageView ivVideo;

    private String videoPath; //选择的视频路径
    //权限数组
    private String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivVideo.setOnClickListener(this);
    }

    //检查权限
    private void checkPromission() {
        if (Build.VERSION.SDK_INT >= 23) { //判断是否是Android6.0以上的系统，如果是，就申请权限
            if (verifyPermissions(mPermissionList)) //有权限
            {
                initData();
            } else {
                ActivityCompat.requestPermissions(this, mPermissionList, 123); //申请权限
            }
        } else {
            initData();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_video:
                checkPromission();
                break;
        }
    }

    private void initData() {
        if (TextUtils.isEmpty(videoPath))
        {
            SelectVideoDialog dialog = new SelectVideoDialog(SelectVideoActivity.this);
            dialog.show();
        }else {
            Intent intent = new Intent(SelectVideoActivity.this,VideoPlayActivity.class);
            intent.putExtra("fromWhere","Select"); //用来在视频播放界面判断是否显示删除按钮
            intent.putExtra("path",videoPath);
            startActivityForResult(intent,Word.REQUEST_CODE5);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Word.REQUEST_CODE3 && data != null) //选择视频
        {
            ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
            videoPath = stringArrayListExtra.get(0);
            Log.i("test1", "选择视频的视频路径：" + videoPath);


            Bitmap videoThumb2 = MyVideoThumbLoader.getVideoThumb2(videoPath); //获取视频封面图
//            File file = saveBitmapFile(videoThumb2); //将视频封面图保存成图片
            ivVideo.setImageBitmap(videoThumb2); //将视频封面图设置到控件上


        }
        if (requestCode == Word.REQUEST_CODE4 && data != null) //拍摄视频
        {
            Uri data1 = data.getData();
            if(data1 == null)
            {
                return;
            }
            Cursor c = getContentResolver().query(data1, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
            if (c != null && c.moveToFirst()) {
                videoPath = c.getString(0);
                Log.i("test1", "拍摄视频的视频路径：" + videoPath);

                Bitmap videoThumb2 = MyVideoThumbLoader.getVideoThumb2(videoPath); //获取视频封面图
//                File file = saveBitmapFile(videoThumb2); //将视频封面图保存成图片
                ivVideo.setImageBitmap(videoThumb2); //将视频封面图设置到控件上
            }
        }
        if (requestCode == Word.REQUEST_CODE5 && data != null) //删除视频
        {
            LogUtils.i("删除视频");
            String jieGuoUrl = data.getStringExtra("jieGuoUrl");
            LogUtils.i("jieGuoUrl:"+jieGuoUrl);
            if (TextUtils.isEmpty(jieGuoUrl))
            {
                videoPath = "";
                Glide.with(this).load(R.mipmap.icon_add_jia).into(ivVideo);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!verifyPermissions(permissions)) { //未申请到权限
            showMissingPermissionDialog();
        } else {
            initData();
        }
    }

}
