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
 * Created by ww on 2017/8/31.
 */

public class LQAdapter extends BaseAdapter{
    Context mcontext;
    List<CouponReceive> mlist;
    public LQAdapter(Context context, List<CouponReceive> list){
        this.mcontext = context;
        this.mlist = list;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mcontext, R.layout.lv_lqitem, null);
        TextView tv_price= (TextView) convertView.findViewById(R.id.tv_youhui_price);
        TextView tv_time= (TextView) convertView.findViewById(R.id.tv_youhui_time);
        tv_price.setText(""+mlist.get(position).getYhjje()/100);
        tv_time.setText("有效期至"+mlist.get(position).getJlgqsj());
        return convertView;
    }
}
