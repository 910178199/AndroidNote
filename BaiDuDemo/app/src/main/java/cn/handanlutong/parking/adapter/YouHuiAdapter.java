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
 * Created by ww on 2017/8/16.
 * 优惠券列表的adapter
 */

public class YouHuiAdapter extends BaseAdapter{
    Context mcontext;
    List<CouponReceive> mlist;
    private int selected=-1;
    public YouHuiAdapter(Context context, List<CouponReceive> list){
        this.mcontext = context;
        this.mlist = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.lv_youhuijuan,null);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_youhui_time);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_youhui_price);
            viewHolder.tv_youhui_fanwei = (TextView) convertView.findViewById(R.id.tv_youhui_fanwei);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_time.setText("有效期至 "+mlist.get(position).getJlgqsj());
        viewHolder.tv_price.setText(""+mlist.get(position).getYhjje()/100);
//        viewHolder.tv_youhui_fanwei.setText("适用范围：" + mlist.get(position).getHdbttp()); //区分 1 2 3类区
        if (position == selected){

        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_time, tv_price, tv_youhui_fanwei;
    }

    public void setSelectedPosition(int position) {
        this.selected=position;
    }
}
