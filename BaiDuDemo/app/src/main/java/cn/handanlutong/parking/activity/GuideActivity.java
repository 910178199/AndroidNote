package cn.handanlutong.parking.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;

/**
 * 引导页
 * Created by zhangyonggang on 2017/8/2.
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private static final int[] pics = { R.layout.guide_view1, R.layout.guide_view2, R.layout.guide_view3, R.layout.guide_view4 };
    private List<View> views;
    private List<View> dots;
    private GuideAdapter vpAdapter;
    private ViewPager viewPager;
    private Button startBtn;
    private int oldPosition = 0;
    private int currentItem;
    private boolean flag;

    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        initPages();
        initDatas();
    }

    private void initDatas() {
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                startBtn = (Button) view.findViewById(R.id.btn_start);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }

        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        vpAdapter = new GuideAdapter(views);
        // 设置数据
        viewPager.setAdapter(vpAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);
    }

    private void initPages() {
        views = new ArrayList<View>();
        dots = new ArrayList<View>();
        // 实例化ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        // 实例化ViewPager适配器

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dots.get(position).setBackgroundResource(R.drawable.dot_guide_focus);
        dots.get(oldPosition).setBackgroundResource(R.drawable.dot_guide_normal);
        oldPosition = position;
        currentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class GuideAdapter extends PagerAdapter {

        // 界面列表
        private List<View> views;

        public GuideAdapter(List<View> views) {
            this.views = views;
        }

        /**
         * 获得当前界面数
         */
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            } else
                return 0;
        }

        /**
         * 判断是否由对象生成界面
         */
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        /**
         * 销毁position位置的界面
         */
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        /**
         * 初始化position位置的界面
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }

    }
}