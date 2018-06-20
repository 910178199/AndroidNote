package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.ParkingServiceType;
import cn.handanlutong.parking.utils.LogUtils;

/**
 * Created by zhangyonggang on 2018/1/11.
 */

public class NeedHelpAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater inflater;
    private List<ParkingServiceType> mList;
    private int selected = -1;
    private static HashMap<Integer, Boolean> isSelected;
    public NeedHelpAdapter(Context ctx,List<ParkingServiceType> mList){
        this.ctx=ctx;
        inflater=LayoutInflater.from(ctx);
        isSelected = new HashMap<Integer, Boolean>();
        setData(mList);
    }

    public void setData(List<ParkingServiceType> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<ParkingServiceType>();
        }
        initDate();
    }

    private void initDate() {
        for (int i = 0; i < mList.size(); i++) {
            getIsSelected().put(i, false);
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_activity_needhelp,null);
            vh=new ViewHolder();
            vh.cb_select= (CheckBox) convertView.findViewById(R.id.cb_select);
            vh.tv_lxms= (TextView) convertView.findViewById(R.id.tv_lxms);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        ParkingServiceType parkingservicetype=mList.get(position);
        vh.tv_lxms.setText(parkingservicetype.getLxms());
        vh.cb_select.setChecked(getIsSelected().get(position));

        if(vh.cb_select.isChecked()){
            vh.cb_select.setBackgroundResource(R.mipmap.xuanzhongzhifufangshi);
        }else {
            vh.cb_select.setBackgroundResource(R.drawable.bg_oval);
        }
        return convertView;
    }

    public static class ViewHolder{
        public CheckBox cb_select;
        public TextView tv_lxms;
    }
    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        NeedHelpAdapter.isSelected = isSelected;
    }
}
