package gcg.testproject.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import gcg.testproject.R;

/**
 * 进度加载框
 *
 * @ClassName:ProgressDialog

 * @PackageName:com.xiaoweiqiye.dialog

 * @Create On 2017/3/1 0001   15:26

 * @Site:http://www.handongkeji.com

 * @author:xuchuanting

 * @Copyrights 2017/3/1 0001 handongkeji All rights reserved.
 */


public class ProgressDialog extends Dialog {

    private ImageView ivPlace;
    private TextView tvMsg;
    private ProgressBar mPb;

    public ProgressDialog(Context context) {
        super(context, R.style.AppTheme_Blue);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        getWindow().setGravity(Gravity.CENTER);
        init();
    }

    private void init() {
        this.setContentView(R.layout.dialog_loading);
        mPb = (ProgressBar) findViewById(R.id.pb);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        ivPlace = (ImageView) findViewById(R.id.iv_place);
    }


    /**
     * 动画放完后，dialog会dismiss,后续操作可以实现OnCompleteListener接口，
     * 传null 只是简单播放完成动画，然后让dialog消失
     *
     * @param completeListener
     */
    public void complete(final OnCompleteListener completeListener, String completeText) {

        //先让ProgressBar隐藏
        mPb.setVisibility(View.GONE);

        //执行动画，往右扯动布，让对号露出来
        ObjectAnimator translationX = ObjectAnimator.ofFloat(ivPlace, "translationX", 0, ivPlace.getWidth());
        translationX.setDuration(700);
        translationX.setInterpolator(new AccelerateInterpolator());
        translationX.start();

        //改变提示信息内容和颜色
        tvMsg.setTextColor(Color.parseColor("#4caf65"));
        if (TextUtils.isEmpty(completeText)) {
            tvMsg.setText("完成");
        } else {
            tvMsg.setText(completeText);
        }


        //动画放完后再调用OnCompleteListener接口
        translationX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                tvMsg.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                        if (completeListener != null) {
                            completeListener.onComplete();
                        }
                    }
                },1000);

            }
        });
    }

    public void setMsg(String msg) {
        tvMsg.setText(msg);
    }


    /**
     * 一个接口，用来做完成动画放完后要做的事，比如finish();
     */
    public interface OnCompleteListener {
        void onComplete();
    }


}
