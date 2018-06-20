package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;
import cn.handanlutong.parking.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 * Created by zhangyonggang on 2017/8/17.
 */

public class MyOrderAdapter extends BaseAdapter {
    private List<ParkingRecordHistoryVo> mList;
    private Context ctx;
    private LayoutInflater inflater;

    public MyOrderAdapter(Context ctx, List<ParkingRecordHistoryVo> mList) {
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        setData(mList);
    }

    public void setData(List<ParkingRecordHistoryVo> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<ParkingRecordHistoryVo>();
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
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_activity_myorder, null);
            vh = new ViewHolder();
            vh.tv_new_time = (TextView) convertView.findViewById(R.id.tv_new_time);
            vh.tv_new_status = (TextView) convertView.findViewById(R.id.tv_new_status);
            vh.tv_Duration = (TextView) convertView.findViewById(R.id.tv_Duration);
            vh.tv_totalFee = (TextView) convertView.findViewById(R.id.tv_totalFee);
            vh.tv_Stop_Date = (TextView) convertView.findViewById(R.id.tv_Stop_Date);
            vh.tv_ParkingName = (TextView) convertView.findViewById(R.id.tv_ParkingName);
            vh.iv_yueka = (ImageView) convertView.findViewById(R.id.iv_yueka);
            vh.img_owe = (ImageView) convertView.findViewById(R.id.img_owe);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ParkingRecordHistoryVo billrecordvo = mList.get(position);
        String PayStatus = billrecordvo.getIsPay();
        if (PayStatus.equals("已完成")) {
            vh.tv_new_time.setText("离场时间 " + billrecordvo.getLwsjSFM());
            vh.tv_Stop_Date.setText(billrecordvo.getLwsjYR());
        } else {
            vh.tv_new_time.setText("入场时间 " + billrecordvo.getRwsjSFM());
            vh.tv_Stop_Date.setText(billrecordvo.getRwsjYR());
        }
        vh.tv_new_status.setText(PayStatus);
        if (PayStatus.equals("未支付")) {
            vh.tv_new_status.setTextColor(Color.parseColor("#e62121"));
        } else {
            vh.tv_new_status.setTextColor(Color.parseColor("#999999"));
        }
        vh.tv_Duration.setText("停车时长 " + billrecordvo.getTcsc());
        double money = (double) Integer.parseInt(billrecordvo.getZje()) / 100;
        vh.tv_totalFee.setText("￥" + String.format("%.2f", money));
        vh.tv_ParkingName.setText(billrecordvo.getCcmc());
        vh.img_owe.setVisibility(View.GONE); //隐藏欠缴费
        if (mList.get(position).getZflx().equals("5")) {
            vh.iv_yueka.setVisibility(View.VISIBLE);
            vh.tv_new_status.setVisibility(View.GONE);
        } else if (mList.get(position).getZflx().equals("6")) {
            vh.img_owe.setVisibility(View.VISIBLE); //显示欠缴费
        } else {
            vh.iv_yueka.setVisibility(View.GONE);
            vh.tv_new_status.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_new_time, tv_new_status, tv_Duration, tv_totalFee, tv_Stop_Date, tv_ParkingName;
        ImageView iv_yueka, img_owe;
    }
}
