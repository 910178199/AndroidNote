package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;

/**
 * Created by ww on 2017/9/8.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout back;
    TextView tv_vercode;
    RelativeLayout rl_Feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        back=(RelativeLayout) findViewById(R.id.back);
        tv_vercode = (TextView) findViewById(R.id.tv_vercode);
        rl_Feedback = (RelativeLayout) findViewById(R.id.rl_Feedback);
        back.setOnClickListener(this);
//        rl_Feedback.setOnClickListener(this);
        String currentCode = JieKouDiaoYongUtils.getVerName(this);
        tv_vercode.setText("邯郸泊车 V"+currentCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.rl_Feedback:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
