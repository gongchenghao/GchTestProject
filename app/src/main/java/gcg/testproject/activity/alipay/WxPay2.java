package gcg.testproject.activity.alipay;

import android.app.Activity;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.util.LinkedHashMap;

import gcg.testproject.bean.WeiXinPayOrderBean;
import gcg.testproject.utils.LogUtils;

/**
 * 作者: 宫成浩
 * 日期: 2018/1/25.
 */


public class WxPay2 {

    public void wxpay(Activity v,WeiXinPayOrderBean.PaymentBean.DatasBean datas) {//微信支付相关参数
        LogUtils.i("微信支付2 ==== wxpay");

        String appidStr = datas.getAppid();
        IWXAPI api = WXAPIFactory.createWXAPI(v, appidStr, false);
        api.registerApp(appidStr);
        PayReq request = new PayReq();
        request.appId = appidStr;
        request.partnerId = datas.getPartnerid();
        request.prepayId = datas.getPrepayid();
        request.packageValue = datas.getPack();
        request.nonceStr = datas.getNoncestr();
        request.timeStamp = datas.getTimestamp();
        request.sign = datas.getSign();

        boolean b = api.sendReq(request);
        LogUtils.i("b======================:"+b);
        if (b == false)
        {
            //调起微信支付失败，回调失败的方法
            LogUtils.i("调起微信支付失败");
        }
    }
}
