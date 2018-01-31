package gcg.testproject.dialog;

import android.app.Dialog;
import android.content.Context;
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
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.ToastUtils;

/**
 * 
 * @ClassName:MySimpleDialog

 * @PackageName:gcg.testproject.dialog

 * @Create On 2018/1/16   14:24

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2018/1/16 handongkeji All rights reserved.
 */


public class MySimpleDialog extends BaseDialog implements View.OnClickListener {


    @Bind(R.id.tv_woman)
    TextView tvWoman;
    @Bind(R.id.btn_cancle)
    Button btnCancle;
    @Bind(R.id.ll_out)
    LinearLayout llOut;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    private Context context;

    public MySimpleDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_delete_photo);
        ButterKnife.bind(this);
        this.context = context;
        setLoaction(Gravity.BOTTOM); //调用父类方法，设置弹框位置
        
        init(); //初始化控件的点击事件
    }

    private void init() {
        btnCancle.setOnClickListener(this);
        tvWoman.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_woman:  //删除按钮
                dismiss();
                ToastUtils.showMyToast(context, R.layout.layout_toast_delete, R.id.toast);
                break;
            case R.id.btn_cancle:
                dismiss();
                break;
        }
    }

}
