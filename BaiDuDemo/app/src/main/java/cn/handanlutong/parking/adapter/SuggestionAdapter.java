package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.HistoryBean;

/**
 * Created by ww on 2017/4/24.
 */

public class SuggestionAdapter extends BaseAdapter {
    private static final String TAG = "SuggestionAdapter";
    private List<HistoryBean> datas;
    private Context mContext = null;
    private String keyWord;
    private int keyWordColor;


    public List<HistoryBean> getDatas() {
        return datas;
    }

    public void setDatas(List<HistoryBean> datas) {
        this.datas = datas;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public SuggestionAdapter(Context context, List<HistoryBean> datas, String keyWord) {
        super();
        mContext = context;
        this.datas = datas;
        this.keyWord = keyWord;

        keyWordColor = context.getResources().getColor(R.color.bg_blue);
    }

    @Override
    public int getCount() {
        return datas==null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = View.inflate(mContext , R.layout.item_list_suggestion,null);
            holder = new ViewHolder();
            holder.tv_suggestion = (TextView)convertView.findViewById(R.id.tv_suggestion);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }

        HistoryBean historyBean = datas.get(position);
        if(!TextUtils.isEmpty(keyWord)){
            int index = historyBean.address.indexOf(keyWord);
            if(index != -1){//有关键字
                SpannableString ss = new SpannableString(historyBean.address +"\n"+ historyBean.city + historyBean.district);
                ss.setSpan(new ForegroundColorSpan(keyWordColor), index, keyWord.length()+index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv_suggestion.setText(ss);
            }else{
                holder.tv_suggestion.setText(historyBean.address + "\n" + historyBean.city + historyBean.district);
            }
        }else{
            holder.tv_suggestion.setText(historyBean.address + "\n" + historyBean.city + historyBean.district);
        }

        return convertView;
    }

    private static class ViewHolder{
        TextView tv_suggestion;
    }
}
