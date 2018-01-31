package gcg.testproject.activity.selectphoto;

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
 * Created by Administrator on 2017/5/3 0003.
 */

public class DeleteDialog extends Dialog implements View.OnClickListener {


    @Bind(R.id.tv_woman)
    TextView tvWoman;
    @Bind(R.id.btn_cancle)
    Button btnCancle;
    @Bind(R.id.ll_out)
    LinearLayout llOut;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    private Context context;
    public boolean hasDelete = false;

    public DeleteDialog(BaseActivity activity, String msg) {
        super(activity);

        setContentView(R.layout.dialog_delete_photo);
        ButterKnife.bind(this);
        //设置弹框在屏幕的底部
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        this.context = activity;

        tvDelete.setText(msg);
        init();
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
                hasDelete = true;
                break;
            case R.id.btn_cancle:
                hasDelete = false;
                ToastUtils.showShort(context, "取消");
                dismiss();
                break;
        }
    }

}
