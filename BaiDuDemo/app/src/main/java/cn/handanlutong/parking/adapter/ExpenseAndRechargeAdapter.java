package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.RchargeRecordListBean;

/**
 * Created by zhangyonggang on 2017/4/12.
 */

public class ExpenseAndRechargeAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater inflater;
    private String flag;
    private List<RchargeRecordListBean.ResultBean.ItemsBean> mList;

    public ExpenseAndRechargeAdapter(List<RchargeRecordListBean.ResultBean.ItemsBean> mList, Context ctx, String flag) {
        this.ctx = ctx;
        this.flag = flag;
        inflater = LayoutInflater.from(ctx);
        setDate(mList);

    }

    public void setDate(List<RchargeRecordListBean.ResultBean.ItemsBean> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<RchargeRecordListBean.ResultBean.ItemsBean>();
        }
    }

    @Override
    public int getCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        } else {
            return mList.size();
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

        ViewHoleder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.itme_fragmeng_expenserecharge, null);
            vh = new ViewHoleder();
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_rechargeTiem = (TextView) convertView.findViewById(R.id.tv_rechargeTiem);
            vh.tv_rechaegeType = (TextView) convertView.findViewById(R.id.tv_rechaegeType);
            vh.tv_PayOrStop = (TextView) convertView.findViewById(R.id.tv_PayOrStop);
            convertView.setTag(vh);
        } else {
            vh = (ViewHoleder) convertView.getTag();
        }
        RchargeRecordListBean.ResultBean.ItemsBean rechargevobean = mList.get(position);
        if (flag.equals("recharge")) {
            vh.tv_PayOrStop.setText("付款成功");
            vh.tv_money.setText("+" +String.format("%.2f", (double) rechargevobean.getJe() / 100) + "元");
            vh.tv_rechargeTiem.setText(rechargevobean.getZfsj());
            vh.tv_rechaegeType.setVisibility(View.VISIBLE);
            vh.tv_rechaegeType.setText(rechargevobean.getZfqd());
        }else if(flag.equals("expense")){
            vh.tv_PayOrStop.setText("" + rechargevobean.getZflx());
            vh.tv_money.setText(String.format("%.2f",(double) rechargevobean.getJe() / 100 )+ "元");
            vh.tv_rechargeTiem.setText(rechargevobean.getZfsj());
            vh.tv_rechaegeType.setVisibility(View.GONE);
            vh.tv_rechaegeType.setText(rechargevobean.getZfqd());
        }
        return convertView;
    }

    class ViewHoleder {
        TextView tv_money, tv_rechargeTiem, tv_rechaegeType, tv_PayOrStop;
    }

}
