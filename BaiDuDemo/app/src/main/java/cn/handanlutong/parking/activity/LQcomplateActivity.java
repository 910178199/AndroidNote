package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.LQAdapter;
import cn.handanlutong.parking.bean.CouponReceive;

/**
 * Created by ww on 2017/8/30.
 */

public class LQcomplateActivity extends Activity implements View.OnClickListener{
    RelativeLayout back;
    ListView lv;
    ScrollView sv;
    List<CouponReceive> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lqcomplate);
        back=(RelativeLayout) findViewById(R.id.back);
        lv = (ListView) findViewById(R.id.lv_lqcomplate);
        sv = (ScrollView) findViewById(R.id.scroll);
        back.setOnClickListener(this);
        Intent intent=getIntent();
        if (intent != null){
            list = (List<CouponReceive>) intent.getSerializableExtra("coupon");
            lv.setAdapter(new LQAdapter(this, list));
            lv.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            return true;
                        default:
                            break;
                    }
                    return true;
//                    if(event.getAction() == MotionEvent.ACTION_UP){
//                        sv.requestDisallowInterceptTouchEvent(false);
//                    }else{
//                        sv.requestDisallowInterceptTouchEvent(true);
//                    }
                }
            });
            setListViewHeight(lv);
            sv.post(runnable);
        }
    }
    /**
     * 设置Listview的高度
     */
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    /**
     * 设置scrollview显示位置，解决scrollview嵌套listview页面过长时显示问题
     */
    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            sv.scrollTo(1,1);// 改变滚动条的位置
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
