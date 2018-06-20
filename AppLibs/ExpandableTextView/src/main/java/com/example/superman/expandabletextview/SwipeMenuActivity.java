package com.example.superman.expandabletextview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.superman.expandabletextview.swipemenulistview.SwipeMenu;
import com.example.superman.expandabletextview.swipemenulistview.SwipeMenuCreator;
import com.example.superman.expandabletextview.swipemenulistview.SwipeMenuItem;
import com.example.superman.expandabletextview.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class SwipeMenuActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<String> strList;
    private MyAdapter myAdapter;
    private SwipeMenuListView mListView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu);

        initSwipeMenu();
    }

    private void initSwipeMenu() {
        //侧滑删除
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem delItem = new SwipeMenuItem(getApplicationContext());
                delItem.setBackground(R.color.colorAccent);
                delItem.setWidth(dp2px(75));
//                delItem.setHeigh(dp2px(100));
                delItem.setTitle("删除");
                delItem.setTitleSize(14);
                delItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(delItem);
            }
        };

        mListView = findViewById(R.id.listView);
        mListView.setMenuCreator(creator);

        strList = new ArrayList<>();
        strList.add("jsdfsdfsd");
        strList.add("jsdfsdfsd");
        strList.add("jsdfsdfsd");
        myAdapter = new MyAdapter(this, strList);
        mListView.setAdapter(myAdapter);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                Log.d(TAG, "onMenuItemClick: " + position);
                strList.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    // 设置侧滑出来部分的宽度，否没有划出的效果
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
