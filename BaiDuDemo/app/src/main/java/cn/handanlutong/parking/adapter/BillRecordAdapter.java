package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.ParkingRecordHistoryVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单记录
 * Created by zhangyonggang on 2017/8/15.
 */

public class BillRecordAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater inflater;
    private List<ParkingRecordHistoryVo> mList;
    public BillRecordAdapter(Context ctx,List<ParkingRecordHistoryVo> mList){
        this.ctx=ctx;
        inflater=LayoutInflater.from(ctx);
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
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_activity_bill_record,null);
            vh=new ViewHolder();
            vh.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            vh.tv_Address= (TextView) convertView.findViewById(R.id.tv_Address);
            vh.tv_totalFee= (TextView) convertView.findViewById(R.id.tv_totalFee);
            vh.tv_backMoney= (TextView) convertView.findViewById(R.id.tv_backMoney);
            vh.iv_yueka = (ImageView) convertView.findViewById(R.id.iv_yueka);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        ParkingRecordHistoryVo billrecordvo=mList.get(position);
        vh.tv_date.setText(billrecordvo.getLwsj());
        vh.tv_Address.setText(billrecordvo.getCcmc());
        double money=(double)Integer.parseInt(billrecordvo.getZje())/100;
        vh.tv_totalFee.setText("￥"+String.format("%.2f",money));
        double tkje=(double)Integer.parseInt(billrecordvo.getTkje())/100;
        if(tkje>0){
            vh.tv_backMoney.setText("已退款￥"+String.format("%.2f",tkje));
        }else{
            vh.tv_backMoney.setText("");
        }
        if (mList.get(position).getZflx().equals("5")){ //月卡账单记录
            vh.iv_yueka.setVisibility(View.VISIBLE);
        }else{
            vh.iv_yueka.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_date,tv_Address,tv_totalFee,tv_backMoney;
        ImageView iv_yueka;
    }

}
