package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.ParkingFP;

/**
 * Created by ww on 2017/8/16.
 * 开发票 历史 记录
 */

public class FaPiaoAdapter extends BaseAdapter{
    Context mcontext;
    List<ParkingFP> mlist;
    private int selected=-1;
    public FaPiaoAdapter(Context context, List<ParkingFP> list){
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
            convertView = View.inflate(mcontext, R.layout.lv_fapiao_item,null);
            viewHolder.tv_fapiao_item_zt = (TextView) convertView.findViewById(R.id.tv_fapiao_item_zt);
            viewHolder.tv_fapiao_item_sj = (TextView) convertView.findViewById(R.id.tv_fapiao_item_sj);
            viewHolder.tv_fapiao_item_je = (TextView) convertView.findViewById(R.id.tv_fapiao_item_je);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mlist.get(position).getFpzt().equals("1")){  // 1开票中 2开票失败 3已开票
            viewHolder.tv_fapiao_item_zt.setText("开票中");
            viewHolder.tv_fapiao_item_zt.setTextColor(Color.parseColor("#29c08b"));
        }else if (mlist.get(position).getFpzt().equals("2")){
            viewHolder.tv_fapiao_item_zt.setText("开票失败");
            viewHolder.tv_fapiao_item_zt.setTextColor(Color.parseColor("#E62121"));
        }else if (mlist.get(position).getFpzt().equals("3")){
            viewHolder.tv_fapiao_item_zt.setText("开票成功");
            viewHolder.tv_fapiao_item_zt.setTextColor(Color.parseColor("#999999"));
        }
        viewHolder.tv_fapiao_item_sj.setText("开票时间: " + mlist.get(position).getCjsj());
        double money = (double) Integer.parseInt(mlist.get(position).getJe()) / 100;
        viewHolder.tv_fapiao_item_je.setText("开票金额: " + String.format("%.2f", money) + "元");
        return convertView;
    }

    class ViewHolder{
        TextView tv_fapiao_item_zt, tv_fapiao_item_je, tv_fapiao_item_sj;
    }

    public void setSelectedPosition(int position) {
        this.selected=position;
    }
}
