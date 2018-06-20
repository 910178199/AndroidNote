package com.example.superman.expandabletextview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MyAllSelectAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private SparseArray<Boolean> checkStates;

    public MyAllSelectAdapter(Context mContext, SparseArray<Boolean> checkStates) {
        this.mContext = mContext;
        this.checkStates = checkStates;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.checkBox.setText(checkStates.keyAt(position) + "");
        holder.checkBox.setChecked(checkStates.valueAt(position));

//            用户点击checkbox行为会需要增加监听来改变checkStates对应项的状态
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("check", position + ":" + isChecked);
                checkStates.setValueAt(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkStates.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    public CheckBox checkBox;

    public MyViewHolder(View itemView) {
        super(itemView);
        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
    }
}
