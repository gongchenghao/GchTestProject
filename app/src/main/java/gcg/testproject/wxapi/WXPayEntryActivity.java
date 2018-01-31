package gcg.testproject.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import gcg.testproject.activity.alipay.PayCallback;
import gcg.testproject.base.BaseActivity;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	private IWXAPI api;

//	private int getCount = 0;
	private int flg = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_weixin_entry_pay);

		Log.i("test111","wxapientry  ----------->  createLayout");

		//参数二：微信的APPID
		api = WXAPIFactory.createWXAPI(this, "wxda5a31f867e9c122"); //appid 在微信开放平台帐的【管理中心】查找。如果ip不对导致onResp不能回调

		api.handleIntent(getIntent(), this);

		if (flg == 1) {
			Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {


	}

	// 输入密码正确，点击完成按钮后会走这个回调方法;输入密码时用户取消支付操作
	@Override
	public void onResp(BaseResp resp) {
		Log.i("test1","resp.errCode:"+resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Intent intent = new Intent("123456789");
			LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getBaseContext());
			localBroadcastManager.sendBroadcast(intent);

			//resp.errCode：支付结果返回的错误码
			if (resp.errCode == 0) { //支付成功
				Log.i("test111","支付成功");
				PayCallback.getInstance().notifyPaySuccess();
				WXPayEntryActivity.this.finish();
			}
			if(resp.errCode == -1){// 支付失败：可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
				Log.i("test111","resp.errCode == -1");
				PayCallback.getInstance().notifyPayFailed();
				flg = 1;
//				WXPayEntryActivity.this.finish();
			}
			if (resp.errCode == -2){ //用户取消：无需处理。发生场景：用户不支付了，点击取消，返回APP。
				Log.i("test111","resp.errCode == -2");
				Log.i("test111","用户取消222");

				PayCallback.getInstance().notifyPayFailed();
//				WXPayEntryActivity.this.finish();
			}
		}
	}
}