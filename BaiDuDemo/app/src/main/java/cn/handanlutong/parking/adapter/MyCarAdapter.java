package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.MyCarInfoActivity;
import cn.handanlutong.parking.bean.MyCarBean;

/**
 * 我的车辆
 * Created by zhangyonggang on 2017/4/12.
 */

public class MyCarAdapter extends BaseAdapter {
    private Context ctx;
    private List<MyCarBean.ResultBean> mList;
    private LayoutInflater inflater;
    protected HttpUtils httpUtils;// 网络访问声明

    public MyCarAdapter(Context ctx, List<MyCarBean.ResultBean> mList) {
        this.ctx = ctx;
        setData(mList);
        inflater = LayoutInflater.from(ctx);
        httpUtils = new HttpUtils();
    }

    public void setData(List<MyCarBean.ResultBean> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList = mList;
        } else {
            mList = new ArrayList<MyCarBean.ResultBean>();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_activity_mycar_liebiao, null);
            vh = new ViewHolder();
            vh.tv_listCarNum = (TextView) convertView.findViewById(R.id.tv_listCarNum);
            vh.iv_mycar_moren = (ImageView) convertView.findViewById(R.id.iv_mycar_moren);
            vh.iv_mycar_yueka = (ImageView) convertView.findViewById(R.id.iv_mycar_yueka);
            vh.iv_mycar_item_icon = (ImageView) convertView.findViewById(R.id.iv_mycar_item_icon);
            vh.iv_next_right = (ImageView) convertView.findViewById(R.id.iv_next_right);
            vh.tv_addname = (TextView) convertView.findViewById(R.id.tv_addname);
            vh.tv_mycar_shenhe_miaoshu = (TextView) convertView.findViewById(R.id.tv_mycar_shenhe_miaoshu);
            vh.tv_mycar_zong_miaoshu = (TextView) convertView.findViewById(R.id.tv_mycar_zong_miaoshu);
            vh.ll_item_mycar = (LinearLayout) convertView.findViewById(R.id.ll_item_mycar);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ll_item_mycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MyCarInfoActivity.class);
                intent.putExtra("mycar_item", (Serializable) mList);
                intent.putExtra("position", position);
                ctx.startActivity(intent);
            }
        });
        if (mList.get(position).getBdzt().equals("0")) { //未认证
            vh.tv_listCarNum.setTextColor(Color.parseColor("#333333"));
            vh.iv_mycar_item_icon.setImageResource(R.mipmap.mycar_item_icon);
            vh.tv_mycar_shenhe_miaoshu.setText("未认证");
            vh.tv_mycar_shenhe_miaoshu.setTextColor(Color.parseColor("#777777"));
            vh.tv_mycar_zong_miaoshu.setText("邯郸泊车，尽享智能贴心停车服务\n");
            vh.ll_item_mycar.setEnabled(true);
            vh.iv_next_right.setVisibility(View.VISIBLE);
        } else if (mList.get(position).getBdzt().equals("1")) { //审核中
            if (mList.get(position).getType().equals("1")){
                vh.tv_listCarNum.setTextColor(Color.parseColor("#333333"));
                vh.iv_mycar_item_icon.setImageResource(R.mipmap.mycar_item_icon);
                vh.tv_mycar_zong_miaoshu.setText("审核时间需要1-2个工作日，请耐心等待。" +
                        "\n客服妹子将竭诚为您服务：400-810-6188");
                vh.tv_mycar_shenhe_miaoshu.setText("车辆认证审核中，不影响您享受停车服务");//正常流程车辆 审核中
                vh.tv_mycar_shenhe_miaoshu.setTextColor(Color.parseColor("#777777"));
                vh.ll_item_mycar.setEnabled(true);
                vh.iv_next_right.setVisibility(View.VISIBLE);
            } else { //找回 审核中
                vh.tv_listCarNum.setTextColor(Color.parseColor("#999999"));
                vh.iv_mycar_item_icon.setImageResource(R.mipmap.mycar_item_icon_hui);
                vh.tv_mycar_shenhe_miaoshu.setText("找回车辆审核中，不能为您提供停车服务");//找回的车辆 审核中
                vh.tv_mycar_zong_miaoshu.setText("审核时间需要1-2个工作日，请耐心等待。" +
                        "\n客服妹子将竭诚为您服务：400-810-6188");
                vh.tv_mycar_shenhe_miaoshu.setTextColor(Color.parseColor("#E62121"));
                vh.ll_item_mycar.setEnabled(false);
                vh.iv_next_right.setVisibility(View.GONE);
            }
        } else if (mList.get(position).getBdzt().equals("2")) { //已认证
            vh.tv_listCarNum.setTextColor(Color.parseColor("#333333"));
            vh.iv_mycar_item_icon.setImageResource(R.mipmap.mycar_item_icon);
            vh.tv_mycar_zong_miaoshu.setText("邯郸泊车，尽享智能贴心停车服务\n");
            vh.ll_item_mycar.setEnabled(true);
            vh.tv_mycar_shenhe_miaoshu.setText("已认证");
            vh.tv_mycar_shenhe_miaoshu.setTextColor(Color.parseColor("#29c08b"));
            vh.iv_next_right.setVisibility(View.VISIBLE);
        }
        if (mList.get(position).getBqzt().equals("2")){ //已购买月卡
            vh.iv_mycar_yueka.setVisibility(View.VISIBLE);
        }else{
            vh.iv_mycar_yueka.setVisibility(View.GONE);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(
                "审核时间需要1-2工作日，请耐心等待。如有问题，请拨打客服电话咨询: 400-810-6188");
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#45a6cd"));
        builder.setSpan(redSpan, 35, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(redSpan, 1, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        vh.tv_addname.setText(builder);
//        vh.tv_addname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-810-6188"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
//            }
//        });
        vh.tv_listCarNum.setText(mList.get(position).getHphm());
        return convertView;
    }


    class ViewHolder {
        ImageView iv_mycar_moren, iv_mycar_item_icon, iv_next_right, iv_mycar_yueka;
        TextView tv_listCarNum, tv_addname, tv_mycar_shenhe_miaoshu, tv_mycar_zong_miaoshu;
        LinearLayout ll_item_mycar;
    }
}
