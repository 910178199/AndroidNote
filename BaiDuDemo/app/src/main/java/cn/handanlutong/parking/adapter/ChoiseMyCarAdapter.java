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
import cn.handanlutong.parking.bean.MyCarBean;


public class ChoiseMyCarAdapter extends BaseAdapter {

    private List<MyCarBean.ResultBean> mList;
    private Context mContex;
    private LayoutInflater inflater;
    private int selected = -1;
    private int carFlag;

    public ChoiseMyCarAdapter(Context context, List<MyCarBean.ResultBean> mList, int carFlag) {
        this.mContex = context;
        inflater = LayoutInflater.from(mContex);
        setData(mList);
        this.carFlag = carFlag;
    }

    private void setData(List<MyCarBean.ResultBean> mList) {
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
            convertView = View.inflate(mContex, R.layout.item_gridview_choise_mycar, null);
            viewHolder.tv_Recharge_Money = (TextView) convertView.findViewById(R.id.tv_Rechage_Money);
            viewHolder.tv_yuan = (TextView) convertView.findViewById(R.id.tv_yuan);
            viewHolder.rl_recharge = (RelativeLayout) convertView.findViewById(R.id.rl_recharge);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_Recharge_Money.setText("" + mList.get(position).getHphm());
        if (mList.get(position).getBdzt().equals("2")) { // 2 已认证
            viewHolder.tv_yuan.setVisibility(View.GONE);
            if (mList.get(position).getSfxf() == 1) { //可以续费
                if (carFlag == 2) {
                    viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui2);
                    viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#333333"));
                } else {
                    viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
                    viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#e7e7e7"));
                }
            } else if (!mList.get(position).getBqzt().equals("2") //包期状态 非使用中
                    && mList.get(position).getHpzt().equals("0")) { //号牌状态 空闲
                if (carFlag == 2) {
                    viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui2);
                    viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#333333"));
                } else {
                    viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
                    viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#e7e7e7"));
                }
            } else {
                viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
                viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#e7e7e7"));
            }
        } else { // 未认证和审核中
            viewHolder.tv_yuan.setVisibility(View.VISIBLE);
            viewHolder.tv_yuan.setText("未认证");
            viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
            viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#e7e7e7"));
        }
        if (mList.get(position).getBqzt().equals("2") && mList.get(position).getSfxf() == 0) {
            viewHolder.tv_yuan.setVisibility(View.VISIBLE);
            viewHolder.tv_yuan.setText("已购月卡");
            viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
            viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#e7e7e7"));
        }
        if (selected == position) { //选中后
            viewHolder.rl_recharge.setBackgroundResource(R.mipmap.bg_moren_zt);
            viewHolder.tv_Recharge_Money.setTextColor(Color.parseColor("#29c08b"));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_Recharge_Money, tv_yuan;
        RelativeLayout rl_recharge;
    }

    public void setSelectedPosition(int position) {
        this.selected = position;
    }

    @Override
    public boolean isEnabled(int position) {
        boolean openCard = false;
        if (mList.get(position).getBdzt().equals("2")) { //已认证
            if (mList.get(position).getSfxf() == 1) {//可以续费
                openCard = carFlag == 2;
            } else if (!mList.get(position).getBqzt().equals("2") //包期状态 非使用中
                    && mList.get(position).getHpzt().equals("0")) { //号牌状态 空闲
                openCard = carFlag == 2;
            }
        }
        //可以选择车辆
        return openCard;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
