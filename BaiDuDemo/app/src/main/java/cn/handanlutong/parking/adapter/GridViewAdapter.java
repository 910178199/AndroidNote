package cn.handanlutong.parking.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CzfxListBean;


public class GridViewAdapter extends BaseAdapter {

    private List<CzfxListBean.DataBean> mList;
    private Context mContex;
    private LayoutInflater inflater;
    private int selected = -1;

    public GridViewAdapter(Context context, List<CzfxListBean.DataBean> mList) {
        this.mContex = context;
        inflater = LayoutInflater.from(mContex);
        setData(mList);
    }

    private void setData(List<CzfxListBean.DataBean> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        if (mList != null && mList.size() > 0) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContex, R.layout.item_gridview_recharge, null);
            viewHolder.tv_Recharge_Money = (TextView) convertView.findViewById(R.id.tv_Rechage_Money);
            viewHolder.tv_yuan = (TextView) convertView.findViewById(R.id.tv_yuan);
            viewHolder.rl_recharge = (RelativeLayout) convertView.findViewById(R.id.rl_recharge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_Recharge_Money.setText(""+mList.get(position).getCzje()/100+"元");
        if (mList.get(position).getFxje().equals("")){
            viewHolder.tv_yuan.setVisibility(View.GONE);
        }else{
            viewHolder.tv_yuan.setVisibility(View.VISIBLE);
            viewHolder.tv_yuan.setText(""+mList.get(position).getFxje());
        }
        if (selected == position) {
            viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_gaibian_zt);
            viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.tv_yuan.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt);
            viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#333333"));
            viewHolder.tv_yuan.setTextColor(Color.parseColor("#e62121"));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_Recharge_Money;
        RelativeLayout rl_recharge;
        TextView tv_yuan;
    }

    public void setSelectedPosition(int position) {
        this.selected = position;
    }
}
