package gcg.testproject.activity.SelectVideo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.common.Word;

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
public class SelectVideoDialog extends Dialog implements View.OnClickListener {

    @Bind(R.id.ll_take)
    LinearLayout llTake;
    @Bind(R.id.tv_woman)
    TextView tvWoman;
    @Bind(R.id.tv_take)
    TextView tv_take;
    @Bind(R.id.btn_cancle)
    Button btnCancle;
    @Bind(R.id.ll_out)
    LinearLayout llOut;
    private Context context;

    public SelectVideoDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_take_photo);
        ButterKnife.bind(this);
        this.context = context;
        //设置弹框在屏幕的底部
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        tv_take.setText("拍摄");
        tvWoman.setText("从手机视频中选择");
        setClickListeners();
    }

    private void setClickListeners() {
        llTake.setOnClickListener(this);//拍摄
        tvWoman.setOnClickListener(this);//从手机选择
        btnCancle.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                break;
            case R.id.ll_take://照相机拍摄视频
                takeVideo1();
                break;
            case R.id.tv_woman://获取本地视频
                Intent intent = new Intent(context, VideoSelectActivity.class);
                intent.putExtra("code", Word.REQUEST_CODE3);
                intent.putExtra("isSingle", true);
                intent.putExtra("num", 9);
                Activity activity = (Activity) context;
                activity.startActivityForResult(intent, Word.REQUEST_CODE3);
                break;
        }
        SelectVideoDialog.this.dismiss();
    }

    private void takeVideo1() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, Word.REQUEST_CODE4);
    }
}
