package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.ParkingActivityVo;

/**
 * Created by ww on 2017/8/16.
 * 活动列表的adapter
 */

public class ActionLBAdapter extends BaseAdapter{
    Context mcontext;
    List<ParkingActivityVo> mlist;
    private int selected=-1;
    public ActionLBAdapter(Context context, List<ParkingActivityVo> list){
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
            convertView = View.inflate(mcontext, R.layout.item_actionlb, null);
            viewHolder.iv_lv_actionlb = (ImageView) convertView.findViewById(R.id.iv_lv_actionlb);
            viewHolder.iv_hd_no_gq = (ImageView) convertView.findViewById(R.id.iv_hd_no_gq);
            viewHolder.iv_hd_gq = (ImageView) convertView.findViewById(R.id.iv_hd_gq);
            viewHolder.tv_hd_bt = (TextView) convertView.findViewById(R.id.tv_hd_bt);
            viewHolder.tv_hd_sj = (TextView) convertView.findViewById(R.id.tv_hd_sj);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        new BitmapUtils(mcontext).display(viewHolder.iv_lv_actionlb, mlist.get(position).getHdbttp());
        if (mlist.get(position).getSfgq().equals("true")){ //活动已过期
            viewHolder.iv_hd_gq.setVisibility(View.VISIBLE);
            viewHolder.iv_hd_no_gq.setVisibility(View.GONE);
        }else{
            viewHolder.iv_hd_gq.setVisibility(View.GONE);
            viewHolder.iv_hd_no_gq.setVisibility(View.VISIBLE);
        }
        viewHolder.tv_hd_bt.setText("" + mlist.get(position).getHdbt());
        viewHolder.tv_hd_sj.setText("" + mlist.get(position).getHdsksj());
        if (position == selected){

        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_lv_actionlb, iv_hd_no_gq, iv_hd_gq;
        TextView tv_hd_sj, tv_hd_bt;
    }

    public void setSelectedPosition(int position) {
        this.selected=position;
    }
}
