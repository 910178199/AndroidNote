package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.utils.StringUtil;


/**
 * Created by zhangyonggang on 2017/8/23.
 */

public class PayComplateActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_pay_complate;
    private TextView tv_com_price,tv_Paytime,tv_zhifufangshi;
    @Override
    public void initView() {
        setContentView(R.layout.activity_paycomplate);
        Intent intent = getIntent();
        btn_pay_complate= (Button) findViewById(R.id.btn_pay_complate);
        tv_com_price= (TextView) findViewById(R.id.tv_com_price);
        tv_Paytime= (TextView) findViewById(R.id.tv_Paytime);
        tv_zhifufangshi= (TextView) findViewById(R.id.tv_zhifufangshi);
        tv_com_price.setText(intent.getStringExtra("TotalMoney"));
        tv_Paytime.setText(intent.getStringExtra("PayTiem"));
        String str=intent.getStringExtra("PayType");
        if (!StringUtil.isEmpty(str)){
            if (str.equals("1")){
                tv_zhifufangshi.setText("钱包支付");
            }else if (str.equals("2")){
                tv_zhifufangshi.setText("微信支付");
            }else if (str.equals("3")){
                tv_zhifufangshi.setText("支付宝支付");
            }
        }
    }

    @Override
    public void setLisener() {
        btn_pay_complate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pay_complate:
                OrderPayActivity.instance.finish();
                MyRecordDetailActivity.instance.finish();
                this.finish();
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
