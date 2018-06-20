package com.example.superman.expandabletextview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class AllSelectOrNoSelectActivity extends AppCompatActivity implements View.OnClickListener {
    SparseArray<Boolean> checkStates;
    RecyclerView listView;
    MyAllSelectAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_select_no_select);

        initDate();
        findViewById(R.id.select_all).setOnClickListener(this);
        findViewById(R.id.reverse).setOnClickListener(this);
        findViewById(R.id.cancel_all).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        listView = (RecyclerView) findViewById(R.id.listView);
        adapter = new MyAllSelectAdapter(this, checkStates);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                selectAll();
                break;
            case R.id.reverse:
                reverse();
                break;
            case R.id.cancel_all:
                cancelAll();
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    private void delete() {
        for (int i = 0; i < checkStates.size(); i++) {
            if (checkStates.valueAt(i)) {
                checkStates.delete(checkStates.keyAt(i));
//                当前的项已被删除，记得i要自减，否则会出现混乱
                i--;
            }
        }
        adapter.notifyDataSetChanged();
    }

    //    取消所有选择
    private void cancelAll() {
        Log.v("check", listView.getChildCount() + "");
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.setValueAt(i, false);
        }
        adapter.notifyDataSetChanged();
    }

    //    反选
    private void reverse() {
        for (int i = 0; i < checkStates.size(); i++) {
            if (checkStates.valueAt(i)) {
                checkStates.setValueAt(i, false);
            } else {
                checkStates.setValueAt(i, true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    //    全选
    private void selectAll() {
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.setValueAt(i, true);
        }
        adapter.notifyDataSetChanged();
    }

    //    初始化
    private void initDate() {
        checkStates = new SparseArray<>();
        for (int i = 0; i < 30; i++) {
            checkStates.put(i, false);
        }
    }


}
