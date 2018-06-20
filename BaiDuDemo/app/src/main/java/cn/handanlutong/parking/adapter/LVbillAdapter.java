package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.BillVo;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.StringUtil;

/**
 * Created by ww on 2017/8/31.
 */

public class LVbillAdapter extends BaseAdapter{
    private Context mcontext;
    private LayoutInflater inflater;
    List<BillVo> mlist;
    public LVbillAdapter(Context context, List<BillVo> list){
        this.mcontext = context;
        this.mlist = list;
        inflater= LayoutInflater.from(context);
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
        ViewHodler vh=null;
        if(convertView==null){
            vh=new ViewHodler();
            convertView = inflater.inflate(R.layout.lv_bill, null);
            vh.tv_money= (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_zfqd= (TextView) convertView.findViewById(R.id.tv_zfqd);
            vh.tv_PayTiem= (TextView) convertView.findViewById(R.id.tv_PayTiem);
            convertView.setTag(vh);
        }else {
            vh= (ViewHodler) convertView.getTag();
        }
        double je=(double)Integer.parseInt(mlist.get(position).getMoney())/100;
        vh.tv_money.setText("￥"+String.format("%.2f",je));
        vh.tv_zfqd.setText(mlist.get(position).getZfqd());
        vh.tv_PayTiem.setText(mlist.get(position).getZfsj());
        return convertView;
    }
    class ViewHodler{
        TextView tv_money,tv_zfqd,tv_PayTiem;
    }
}
