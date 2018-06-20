package cn.handanlutong.parking.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.BuyMonthCardActivity;
import cn.handanlutong.parking.activity.MyRecordDetailActivity;
import cn.handanlutong.parking.activity.OrderPayActivity;
import cn.handanlutong.parking.activity.PayComplateActivity;
import cn.handanlutong.parking.activity.RechargeActivity;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_pay_result);
    	api = WXAPIFactory.createWXAPI(this, "wx542c9c76b9331d84");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		LogUtils.d("req="+req);
	}

	@Override
	public void onResp(BaseResp resp) {

		LogUtils.d("resp.errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			Intent intent;
			int code = resp.errCode;
			switch (code) {
			case 0:
				ToastUtil.makeShortText(this, "支付成功");
				if (UrlConfig.type == 1){
					intent=new Intent(this, PayComplateActivity.class);
					intent.putExtra("TotalMoney", OrderPayActivity.TotalMoney);
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date curDate = new Date(System.currentTimeMillis());//获取当前时间
					String str = formatter.format(curDate);
					intent.putExtra("PayTiem", str);//支付时间
					intent.putExtra("PayType", "2");
					startActivity(intent);
 					this.finish();
				} else if (UrlConfig.type == 3){
					intent=new Intent(this, BuyMonthCardActivity.class);
					intent.putExtra("result", 0);
					startActivity(intent);
					finish();
				} else {
					intent=new Intent(this, RechargeActivity.class);
					intent.putExtra("result", 0);
					startActivity(intent);
					finish();
				}
				break;
			case -1:
				ToastUtil.makeShortText(this, "支付失败");
//				intent=new Intent(this,RechargeActivity.class);
//				intent.putExtra("result", -1);
//				startActivity(intent);
				finish();
				break;
			case -2:
				ToastUtil.makeShortText(this, "支付取消");
//				intent=new Intent(this,RechargeActivity.class);
//				intent.putExtra("result", -2);
//				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	}
}