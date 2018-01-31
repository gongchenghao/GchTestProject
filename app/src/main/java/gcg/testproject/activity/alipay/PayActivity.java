package gcg.testproject.activity.alipay;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.bean.AlipayOrderBean;
import gcg.testproject.bean.WeiXinPayOrderBean;
import gcg.testproject.utils.LogUtils;

public class PayActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_weixin)
    TextView tvWeixin;
    @Bind(R.id.tv_zhifubao)
    TextView tvZhifubao;
    @Bind(R.id.activity_pay)
    LinearLayout activityPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initClick();
    }

    private void initClick() {
        tvWeixin.setOnClickListener(this);
        tvZhifubao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_weixin:
                LogUtils.i("微信支付");
                weixinPay();
                break;
            case R.id.tv_zhifubao:
                LogUtils.i("支付宝支付");
                alipay();
                break;
        }
    }

    private void alipay() {
        //下单成功后返回的Json
        String json = "{\"code\":\"1\",\"msg\":\"下单成功！\",\"payment\":{\"code\":\"1\",\"data\":\"app_id=2016093002017342&biz_content=%7B%22total_amount%22%3A%226380.00%22%2C%22body%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%B8%86%E8%88%B9%E5%A4%8F%E4%BB%A4%E8%90%A5%22%2C%22seller_id%22%3A%222088421370251733%22%2C%22out_trade_no%22%3A%2218012517250001%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.customer.xinyi8090.cn%2Fxinyi_interfice%2Fcallbacks.do&sign_type=RSA&timestamp=2018-01-25+17%3A25%3A14&version=1.0&sign=EGV7%2BC%2FlvlPqEEHgHz7nE%2ByGIr6MY%2B79bpZnjYGetFm0xBLr8%2BrfK0vuRjAr9jU70S5%2BEoQxa9wL%2Br1aMFOE3tdYVCJlPA6RKvzjd4pwR05qP3AQXjFThVLfa7IQQZcdC2A89T1IhKan21G7o913vnxWm%2FimR27ODVJ9uYkScBo%3D\",\"msg\":\"支付成功\"},\"datas\":{\"androidpath\":\"http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk\",\"map\":{},\"orderId\":1670,\"order_code\":\"18012517250001\",\"usecode\":\"61182288\"}}";
        Gson gson = new Gson();
        AlipayOrderBean alipayOrderBean = gson.fromJson(json, AlipayOrderBean.class);

        String data = alipayOrderBean.getPayment().getData();
        if (!TextUtils.isEmpty(data))
        {
            new Alipay().pay(PayActivity.this,data);
        }
    }

    private void weixinPay() {
        //下单成功后返回的Json
        String json = "{\"code\":\"1\",\"msg\":\"下单成功！\",\"payment\":{\"code\":\"1\",\"msg\":\"查询成功！\",\"datas\":{\"appid\":\"wxda5a31f867e9c122\",\"noncestr\":\"JPAeashB2nMDMuzJ\",\"pack\":\"Sign=WXPay\",\"partnerid\":\"1418263602\",\"prepayid\":\"wx201801251751536852328f960715753585\",\"sign\":\"BF4EEF2252A272C4BC0C2DA52925CEDB\",\"timestamp\":\"1516873913\"}},\"datas\":{\"androidpath\":\"http://home.console.xinyi8090.cn/files/01/19/ed71e90.apk\",\"map\":{},\"orderId\":1673,\"order_code\":\"18012517510001\",\"usecode\":\"61006249\"}}";
        Gson gson = new Gson();
        WeiXinPayOrderBean wxpayOrderBean = gson.fromJson(json, WeiXinPayOrderBean.class);

        WeiXinPayOrderBean.PaymentBean.DatasBean datas = wxpayOrderBean.getPayment().getDatas();
        if (datas != null)
        {
            LogUtils.i("datas != null");
            new WxPay2().wxpay(PayActivity.this,datas);
        }
    }
}
