package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.fragment.FragmentFactory;

/**
 * 我的钱包交易明细
 * Created by zhangyonggang on 2017/4/11.
 */

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_TransationDetail;
    private ViewPager vp_MviewPage;
    private TextView tv_ExpenseDetail, tv_Rechargedetail, tv_kaifapiao;
    private View vv_Rechargedetail,vv_ExpenseDetail;

    @Override
    public void initView() {
        setContentView(R.layout.activity_transation_detail);
        iv_back_TransationDetail = (ImageView) findViewById(R.id.iv_back_TransationDetail);
        vp_MviewPage = (ViewPager) findViewById(R.id.vp_MviewPage);
        tv_ExpenseDetail = (TextView) findViewById(R.id.tv_ExpenseDetail);
        tv_Rechargedetail = (TextView) findViewById(R.id.tv_Rechargedetail);
        tv_kaifapiao = (TextView) findViewById(R.id.tv_kaifapiao);
        vv_ExpenseDetail = findViewById(R.id.vv_ExpenseDetail);
        vv_Rechargedetail = findViewById(R.id.vv_Rechargedetail);
        vp_MviewPage.setAdapter(new MainAdpater(getSupportFragmentManager()));
        vp_MviewPage.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
//                        tv_ExpenseDetail.setTextColor(getResources().getColor(R.color.parking_hava_pay));
//                        tv_Rechargedetail.setTextColor(getResources().getColor(R.color.parking_not_pay));
                        vv_ExpenseDetail.setBackgroundColor(getResources().getColor(R.color.bg_blue_pay));
                        vv_Rechargedetail.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 1:
//                        tv_ExpenseDetail.setTextColor(getResources().getColor(R.color.parking_not_pay));
//                        tv_Rechargedetail.setTextColor(getResources().getColor(R.color.parking_hava_pay));
                        vv_ExpenseDetail.setBackgroundColor(getResources().getColor(R.color.white));
                        vv_Rechargedetail.setBackgroundColor(getResources().getColor(R.color.bg_blue_pay));
                        break;

                    default:
                        break;
                }
            }
        }
        );

    }

    @Override
    public void setLisener() {
        iv_back_TransationDetail.setOnClickListener(this);
        tv_ExpenseDetail.setOnClickListener(this);
        tv_Rechargedetail.setOnClickListener(this);
        tv_kaifapiao.setOnClickListener(this);
    }

    private class MainAdpater extends FragmentStatePagerAdapter {
        public MainAdpater(FragmentManager fm) {
            super(fm);
        }

        // 每个条目返回的fragment
        //  0
        @Override
        public Fragment getItem(int position) {
            //  通过Fragment工厂  生产Fragment
            return FragmentFactory.createFragment(position);
        }

        // 一共有几个条目
        @Override
        public int getCount() {
            return 2;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_TransationDetail:
                this.finish();
                break;
            case R.id.tv_ExpenseDetail:
                vp_MviewPage.setCurrentItem(0);
                break;
            case R.id.tv_Rechargedetail:
                vp_MviewPage.setCurrentItem(1);
                break;
            case R.id.tv_kaifapiao:
                Intent intent = new Intent(this, FaPiaoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
