package cn.handanlutong.parking.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.CzfxListBean;
import cn.handanlutong.parking.bean.ParkingCardVo;
import cn.handanlutong.parking.utils.StringUtil;


public class ChoiseMonthCardAdapter extends BaseAdapter {

    private List<ParkingCardVo> mList;
    private Context mContex;
    private LayoutInflater inflater;
    private int selected = -1;

    public ChoiseMonthCardAdapter(Context context, List<ParkingCardVo> mList) {
        this.mContex = context;
        inflater = LayoutInflater.from(mContex);
        setData(mList);
    }

    private void setData(List<ParkingCardVo> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<>();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContex, R.layout.item_gridview_choise_month_card, null);
            viewHolder.tv_month_card_sj = (TextView) convertView.findViewById(R.id.tv_month_card_sj);
            viewHolder.tv_month_card_sj2 = (TextView) convertView.findViewById(R.id.tv_month_card_sj2);
            viewHolder.tv_month_card_je = (TextView) convertView.findViewById(R.id.tv_month_card_je);
            viewHolder.tv_month_card_je2 = (TextView) convertView.findViewById(R.id.tv_month_card_je2);
            viewHolder.tv_month_card_ago_je = (TextView) convertView.findViewById(R.id.tv_month_card_ago_je);
            viewHolder.ll_item_choise_month_card = (LinearLayout) convertView.findViewById(R.id.ll_item_choise_month_card);
            viewHolder.rl_item_choise_month_card = (RelativeLayout) convertView.findViewById(R.id.rl_item_choise_month_card);
            viewHolder.view_monthcard = convertView.findViewById(R.id.view_monthcard);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        double sjje = (double) mList.get(position).getSjje()/100;
        double yjje = (double) mList.get(position).getYjje()/100;
        if (yjje > 0){
            viewHolder.ll_item_choise_month_card.setVisibility(View.GONE);
            viewHolder.rl_item_choise_month_card.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ll_item_choise_month_card.setVisibility(View.VISIBLE);
            viewHolder.rl_item_choise_month_card.setVisibility(View.GONE);
        }
        viewHolder.tv_month_card_sj.setText("" + mList.get(position).getName());
        viewHolder.tv_month_card_sj2.setText("" + mList.get(position).getName());
        viewHolder.tv_month_card_je.setText("" + String.format("%.2f", sjje) + "元"); //售价金额
        viewHolder.tv_month_card_je2.setText("" + String.format("%.2f", sjje) + "元"); //售价金额
        viewHolder.tv_month_card_ago_je.setText("" + String.format("%.2f", yjje) + "元"); //原件金额
        if (mList.get(position).getStatus().equals("1")){ //月卡非禁用状态 可以点击选择
            viewHolder.ll_item_choise_month_card.setEnabled(true);
            viewHolder.rl_item_choise_month_card.setEnabled(true);
            viewHolder.ll_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt_hui2);
            viewHolder.rl_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt_hui2);
            viewHolder.tv_month_card_sj.setTextColor(Color.parseColor("#333333"));
            viewHolder.tv_month_card_sj2.setTextColor(Color.parseColor("#333333"));
            viewHolder.tv_month_card_je.setTextColor(Color.parseColor("#333333"));
            viewHolder.tv_month_card_je2.setTextColor(Color.parseColor("#333333"));
            viewHolder.tv_month_card_ago_je.setTextColor(Color.parseColor("#333333"));
            viewHolder.view_monthcard.setBackgroundColor(Color.parseColor("#333333"));
        }else{
            viewHolder.ll_item_choise_month_card.setEnabled(false);
            viewHolder.rl_item_choise_month_card.setEnabled(false);
            viewHolder.ll_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
            viewHolder.rl_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt_hui);
            viewHolder.tv_month_card_sj.setTextColor(Color.parseColor("#e7e7e7"));
            viewHolder.tv_month_card_sj2.setTextColor(Color.parseColor("#e7e7e7"));
            viewHolder.tv_month_card_je.setTextColor(Color.parseColor("#e7e7e7"));
            viewHolder.tv_month_card_je2.setTextColor(Color.parseColor("#e7e7e7"));
            viewHolder.tv_month_card_ago_je.setTextColor(Color.parseColor("#e7e7e7"));
            viewHolder.view_monthcard.setBackgroundColor(Color.parseColor("#e7e7e7"));
        }
        if (selected == position) {
            viewHolder.tv_month_card_sj.setTextColor(Color.parseColor("#29c08b"));
            viewHolder.tv_month_card_sj2.setTextColor(Color.parseColor("#29c08b"));
            viewHolder.tv_month_card_je.setTextColor(Color.parseColor("#29c08b"));
            viewHolder.tv_month_card_je2.setTextColor(Color.parseColor("#29c08b"));
            viewHolder.ll_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt);
            viewHolder.rl_item_choise_month_card.setBackgroundResource(R.mipmap.bg_moren_zt);
            viewHolder.tv_month_card_ago_je.setTextColor(Color.parseColor("#9B9B9B"));
            viewHolder.view_monthcard.setBackgroundColor(Color.parseColor("#999999"));
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_month_card_sj, tv_month_card_je, tv_month_card_sj2, tv_month_card_je2, tv_month_card_ago_je;
        LinearLayout ll_item_choise_month_card;
        RelativeLayout rl_item_choise_month_card;
        View view_monthcard;
    }

    public void setSelectedPosition(int position) {
        this.selected = position;
    }

    @Override
    public boolean isEnabled(int position) {
        //月卡非禁用状态 可以点击选择
//月卡禁用状态 不可以点击选择
        return mList.get(position).getStatus().equals("1");
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
}
