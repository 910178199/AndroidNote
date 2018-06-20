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
import cn.handanlutong.parking.bean.MessageUserInfo;
import cn.handanlutong.parking.bean.ParkingActivityVo;
import cn.handanlutong.parking.utils.LogUtils;

/**
 * Created by ww on 2017/8/16.
 * 消息模块的adapter
 */

public class MyNewsAdapter extends BaseAdapter{
    Context mcontext;
    List<MessageUserInfo> mlist;
    private int selected=-1;
    public MyNewsAdapter(Context context, List<MessageUserInfo> list){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mcontext, R.layout.item_my_news, null);
            viewHolder.iv_my_news_tp = (ImageView) convertView.findViewById(R.id.iv_my_news_tp);
            viewHolder.tv_my_news_sj = (TextView) convertView.findViewById(R.id.tv_my_news_sj);
            viewHolder.tv_my_news_bt = (TextView) convertView.findViewById(R.id.tv_my_news_bt);
            viewHolder.tv_my_news_nr = (TextView) convertView.findViewById(R.id.tv_my_news_nr);
            viewHolder.view_my_news_xian = convertView.findViewById(R.id.view_my_news_xian);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_my_news_sj.setText("" + mlist.get(position).getCjsj());
        viewHolder.tv_my_news_bt.setText("" + mlist.get(position).getBt());
        viewHolder.tv_my_news_nr.setText("" + mlist.get(position).getNr());
        if (! mlist.get(position).getTpdz().equals("")){
            viewHolder.iv_my_news_tp.setVisibility(View.VISIBLE);
            viewHolder.view_my_news_xian.setVisibility(View.GONE);
            new BitmapUtils(mcontext).display(viewHolder.iv_my_news_tp, mlist.get(position).getTpdz());
        } else {
            viewHolder.iv_my_news_tp.setVisibility(View.GONE);
            viewHolder.view_my_news_xian.setVisibility(View.VISIBLE);
        }
        if (position == selected){

        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_my_news_tp;
        TextView tv_my_news_sj, tv_my_news_bt, tv_my_news_nr;
        View view_my_news_xian;
    }

    public void setSelectedPosition(int position) {
        this.selected=position;
    }
}
