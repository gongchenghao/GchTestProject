package gcg.testproject.activity.RightTopPopWindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import gcg.testproject.R;

/**
 * Created by admin on 2016/12/12.
 */
public class RightTopPopWindow extends PopupWindow {
    private View mainView;
    private LinearLayout ll_main, ll_car,ll_my,ll_messag,ll_search,ll_guanzhu;
    private View v1,v2,v3,v4,v5;
    //参数一：上下文的Activity，第二个是菜单的点击事件，从外边传递进来的，要绑定给每一行的菜单，具体的事件实现当然要写在activity中，后面两个分别是弹出窗口的宽度和高度。
    public RightTopPopWindow(Activity paramActivity,
                             View.OnClickListener paramOnClickListener,
                             int paramInt1, int paramInt2, int flag){  //flag控制某些控件是否消失、显示
        super(paramActivity);
        //界面布局
        mainView = LayoutInflater.from(paramActivity).inflate(R.layout.dialog_more, null);
        //首页
        ll_main = ((LinearLayout)mainView.findViewById(R.id.ll_main));
        //购物车
        ll_car = (LinearLayout)mainView.findViewById(R.id.ll_car);
        //我的
        ll_my = (LinearLayout)mainView.findViewById(R.id.ll_my);
        //消息
        ll_messag = (LinearLayout)mainView.findViewById(R.id.ll_messag);
        //搜索
        ll_search = (LinearLayout)mainView.findViewById(R.id.ll_search);
        //关注
        ll_guanzhu = (LinearLayout)mainView.findViewById(R.id.ll_guanzhu);

        v1 = mainView.findViewById(R.id.ve1); //各个点击按钮之间的横线
        v2 = mainView.findViewById(R.id.ve2);
        v3 = mainView.findViewById(R.id.ve3);
        v4 = mainView.findViewById(R.id.ve4);
        v5 = mainView.findViewById(R.id.ve5);

        //设置布局中的控件可见和隐藏
        if (flag == 1) //只显示消息和首页
        {
            ll_car.setVisibility(View.GONE);
            ll_my.setVisibility(View.GONE);
            ll_guanzhu.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v5.setVisibility(View.GONE);
        }
        if (flag == 2) //不显示消息
        {
            ll_messag.setVisibility(View.GONE);
            ll_search.setVisibility(View.GONE);
            ll_guanzhu.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            v5.setVisibility(View.GONE);
        }
        if (flag == 3)
        {
            ll_car.setVisibility(View.GONE);
            ll_my.setVisibility(View.GONE);
            ll_guanzhu.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
            v5.setVisibility(View.GONE);
        }
        if (flag == 4)
        {
            ll_car.setVisibility(View.GONE);
            ll_my.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);


        }
        //设置每个子布局的事件监听器
        if (paramOnClickListener != null){
            ll_car.setOnClickListener(paramOnClickListener);
            ll_my.setOnClickListener(paramOnClickListener);
            ll_main.setOnClickListener(paramOnClickListener);
            ll_messag.setOnClickListener(paramOnClickListener);
            ll_search.setOnClickListener(paramOnClickListener);
            ll_guanzhu.setOnClickListener(paramOnClickListener);
        }
        setContentView(mainView);
        //设置宽度
        setWidth(paramInt1);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) ll_car.getLayoutParams();
        //设置高度
        setHeight(params.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.AnimTools);
        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }
}
