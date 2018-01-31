package gcg.testproject.activity.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gcg.testproject.utils.LogUtils;

/**
 * 作者: 宫成浩
 * 日期: 2018/1/25.
 */


public class Alipay {
    public Activity activity;
    private static final int SDK_PAY_FLAG = 1;

    /**
     * call alipay sdk pay. 调用SDK支付
     * 支付宝相关参数
     */
    public void pay(final Activity activity, String sign) {
        this.activity = activity;
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;

                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch ( msg.what ) {
                case SDK_PAY_FLAG: {//支付宝支付相关处理
                    LogUtils.i("msg.obj--->" + msg.obj);
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if ( TextUtils.equals(resultStatus, "9000") ) {
                        LogUtils.i("支付成功");

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if ( TextUtils.equals(resultStatus, "8000") ) {
                            LogUtils.i("支付失败-----8000");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            LogUtils.i("支付失败-----else");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
