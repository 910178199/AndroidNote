package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.HistoryBean;

public class HistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<HistoryBean> datas;

    public HistoryAdapter(Context mContext, List<HistoryBean> datas){
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null?0:datas.size();
    }

    @Override
    public HistoryBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if( convertView == null ){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_list_record, null);
            holder.tv_record = (TextView) convertView.findViewById(R.id.tv_record);
            holder.tv_history_item = (TextView) convertView.findViewById(R.id.tv_history_item);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HistoryBean bean = getItem(position);
        holder.tv_record.setText(bean.address);
        holder.tv_history_item.setText(""+bean.city+"-"+bean.district);
        return convertView;
    }

    /**
     * 清空所有历史记录
     */
    public void clearAllRecord(){
        datas = null;
    }

    class ViewHolder{
        TextView tv_record;
        TextView tv_history_item;
    }

}
