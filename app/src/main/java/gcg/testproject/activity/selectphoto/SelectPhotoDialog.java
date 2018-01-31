package gcg.testproject.activity.selectphoto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

/**
 * 选择照片弹窗
 *
 * @ClassName:SelectPhotoDialog
 * @PackageName:com.xiaoweiqiye.dialog
 * @Create On 2017/3/22 0022   13:08
 * @Site:http://www.handongkeji.com
 * @author:xuchuanting
 * @Copyrights 2017/3/22 0022 handongkeji All rights reserved.
 */
public class SelectPhotoDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.ll_take)
    LinearLayout llTake;
    @Bind(R.id.tv_woman)
    TextView tvWoman;
    @Bind(R.id.btn_cancle)
    Button btnCancle;
    @Bind(R.id.ll_out)
    LinearLayout llOut;
    private GalleryFinal.OnHanlderResultCallback mcallback;
    private int requestCode;
    private int num; //可选照片的数量
    private Context context;

    public SelectPhotoDialog(int num, Context context, int requestCode, GalleryFinal.OnHanlderResultCallback callback) {
        super(context);
        setContentView(R.layout.dialog_take_photo);
        ButterKnife.bind(this);
        this.mcallback = callback;
        this.requestCode = requestCode;
        this.num = num;
        this.context = context;
        //设置弹框在屏幕的底部
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        setClickListeners();
    }

    private void setClickListeners() {
        llTake.setOnClickListener(this);//拍照
        tvWoman.setOnClickListener(this);//从手机选择
        btnCancle.setOnClickListener(this);//取消
    }


//  需要依赖   compile 'cn.finalteam:galleryfinal:1.4.6'
    private void camera() {
        //设置主题
        ThemeConfig theme = ThemeConfig.DEFAULT;
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(context, new GlideImageLoader(), theme)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
        GalleryFinal.openCamera(requestCode, mcallback);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                dismiss();
                break;
            case R.id.ll_take://照相机获取图片
                camera();
                dismiss();
                break;
            case R.id.tv_woman://本地相册获取图片
//                if (isIcon == false) {
//                    //参数三：是否是单张  参数四：可以上传的张数
//                    //数据结果写在ImageSelectorActivity的confirm()方法中，从这个方法中将数据
//                    //传递到UpLoadPicAndVideoActivity中
//                    ImageSelectorUtils.openPhoto(activity, requestCode, false, num);
//                } else {
//                    ImageSelectorUtils.openPhoto(activity, requestCode, true, 0);
//                }
                Activity activity = (Activity) context;
                //参数三：false_不是头像  true_是头像
                ImageSelectorUtils.openPhoto(activity, requestCode, false, num);
                dismiss();
                break;
        }
    }
}
