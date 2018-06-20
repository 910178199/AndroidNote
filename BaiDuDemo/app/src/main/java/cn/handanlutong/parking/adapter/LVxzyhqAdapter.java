package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CouponReceive;

/**
 * Created by ww on 2017/9/6.
 */

public class LVxzyhqAdapter extends BaseAdapter{
    Context mContext;
    List<CouponReceive> mlist;
    public LVxzyhqAdapter(Context context, List<CouponReceive> list){
        mContext = context;
        mlist = list;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.lv_xzyhq_item, null);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_youhui_price);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_youhui_time);
        tv_time.setText("有效期至"+mlist.get(position).getJlgqsj());
        tv_price.setText(""+ mlist.get(position).getYhjje()/100);
        return convertView;
    }
}
