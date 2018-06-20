package com.example.superman.expandabletextview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.superman.expandabletextview.ui.DialogHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView main_rv;
    MainAdapter adapter;
    ArrayList<DataBean> models;
    private Button swipeMenu;
    private Button allSelectOrNoSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View viewById = findViewById(R.id.ad);
        viewById.setVisibility(View.VISIBLE);

        swipeMenu = findViewById(R.id.SwipeMenu);
        allSelectOrNoSelect = findViewById(R.id.AllSelectOrNoSelect);
        swipeMenu.setOnClickListener(this);
        allSelectOrNoSelect.setOnClickListener(this);

        //新闻列表展开收起
        initExpandData();

        //AdDialog
        final AppCompatDialog adDialog = DialogHelper.getADDialog(this, R.layout.dialog_ad_layout);
        AppCompatButton appCompatButton = adDialog.findViewById(R.id.btn);
        AppCompatImageView appCompatImageView = adDialog.findViewById(R.id.ad);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDialog.dismiss();
            }
        });
        appCompatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SwipeMenuActivity.class));
            }
        });
        adDialog.show();
    }

    private void initExpandData() {
        models = new ArrayList<>();
        String[] arrays = getResources().getStringArray(R.array.news);
        for (String array : arrays) {
            DataBean bean = new DataBean();
            bean.setText(array);
            models.add(bean);
        }

        main_rv = (RecyclerView) findViewById(R.id.main_rv);
        main_rv.setHasFixedSize(true);
        main_rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, models);
        main_rv.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SwipeMenu:
                //ListView侧滑删除
                startActivity(new Intent(this, SwipeMenuActivity.class));
                break;
            case R.id.AllSelectOrNoSelect:
                //全选反选
                startActivity(new Intent(this, AllSelectOrNoSelectActivity.class));
                break;
        }
    }
}
