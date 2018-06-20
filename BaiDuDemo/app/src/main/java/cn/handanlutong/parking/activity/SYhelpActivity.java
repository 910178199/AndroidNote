package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.handanlutong.parking.R;

/**
 * Created by ww on 2017/8/11.
 * 使用帮助页面
 */

public class SYhelpActivity extends BaseActivity implements View.OnClickListener{
    RelativeLayout rl_car;
    RelativeLayout rl_zhifu;
    RelativeLayout rl_findplace;
    RelativeLayout rl_youhuijuan;
    private ImageView iv_back_help;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syhelp);
        rl_car=(RelativeLayout) findViewById(R.id.rl_car);
        rl_zhifu=(RelativeLayout) findViewById(R.id.rl_zhifu);
        rl_findplace=(RelativeLayout) findViewById(R.id.rl_findplace);
        rl_youhuijuan=(RelativeLayout) findViewById(R.id.rl_youhuijuan);
        iv_back_help= (ImageView) findViewById(R.id.iv_back_help);
        iv_back_help.setOnClickListener(this);
        rl_car.setOnClickListener(this);
        rl_zhifu.setOnClickListener(this);
        rl_findplace.setOnClickListener(this);
        rl_youhuijuan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_help:
                finish();
                break;
            case R.id.rl_car:
                Intent intent1=new Intent(this, UserProtocolActivity.class);
                intent1.putExtra("H5_url","file:///android_asset/add_car.html");
                intent1.putExtra("tv_title","添加/删除车辆");
                intent1.putExtra("hdgqsj", "false");
                startActivity(intent1);
                break;
            case R.id.rl_zhifu:
                Intent intent2=new Intent(this, UserProtocolActivity.class);
                intent2.putExtra("H5_url","file:///android_asset/about_zhifu.html");
                intent2.putExtra("tv_title","关于电子支付");
                intent2.putExtra("hdgqsj", "false");
                startActivity(intent2);
                break;
            case R.id.rl_findplace:
                Intent intent3=new Intent(this, UserProtocolActivity.class);
                intent3.putExtra("H5_url","file:///android_asset/find.html");
                intent3.putExtra("tv_title","如何查找停车场");
                intent3.putExtra("hdgqsj", "false");
                startActivity(intent3);
                break;
            case R.id.rl_youhuijuan:
                Intent intent4=new Intent(this, UserProtocolActivity.class);
                intent4.putExtra("H5_url","file:///android_asset/about_yhq.html");
                intent4.putExtra("tv_title","关于优惠劵");
                intent4.putExtra("hdgqsj", "false");
                startActivity(intent4);
                break;
        }
    }
}
