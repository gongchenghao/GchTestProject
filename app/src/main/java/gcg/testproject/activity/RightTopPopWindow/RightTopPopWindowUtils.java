package gcg.testproject.activity.RightTopPopWindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import gcg.testproject.R;
import gcg.testproject.utils.DensityUtils;

/**
 * Created by gongchenghao on 2016/12/13.
 */
public class RightTopPopWindowUtils {
    private Context context;
    private RightTopPopWindow rightTopPopWindow;
    public RightTopPopWindowUtils(Context context)
    {
        this.context = context;
    }

    //参数一：类对象   参数二：popwindow需要在哪个控件下
    public  void showPop(View view, int flag){
        if (rightTopPopWindow == null) {
            //自定义的单击事件
            MyOnClickLintener paramOnClickListener = new MyOnClickLintener();
            rightTopPopWindow = new RightTopPopWindow((Activity) context, paramOnClickListener,
                    DensityUtils.dp2px((Activity) context,160),
                    DensityUtils.dp2px((Activity) context, 160)
                    ,flag); //flag控制某些控件的显示和消息，代码在RightTopPopWindow.java中

            //监听窗口的焦点事件，点击窗口外面则取消显示
            final RightTopPopWindow finalRightTopPopWindow = rightTopPopWindow;
            rightTopPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        finalRightTopPopWindow.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        rightTopPopWindow.setFocusable(true);
        //参照控件，向左偏移量，向右偏移量
        rightTopPopWindow.showAsDropDown(view,-270,18);
        //如果窗口存在，则更新
        rightTopPopWindow.update();
    }
    public  final class MyOnClickLintener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Activity activity = (Activity) context;
            switch (v.getId()) {
                case R.id.ll_car: //购物车
                    Toast.makeText(context, "购物车", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_my: //我的
                    Toast.makeText(context, "我的", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_main: //首页
                    Toast.makeText(context, "首页", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_messag: //消息
                    Toast.makeText(context, "消息", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_search: //搜索
                    Toast.makeText(context, "搜索", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ll_guanzhu: //关注
                    Toast.makeText(context, "关注", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
