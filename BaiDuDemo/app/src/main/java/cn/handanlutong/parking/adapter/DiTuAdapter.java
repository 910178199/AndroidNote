package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.GarageBean;
import cn.handanlutong.parking.bean.SFguize;
import cn.handanlutong.parking.utils.NavigateUtils;
import cn.handanlutong.parking.utils.StringUtil;

/**
 * Created by ww on 2017/5/31.
 */

public class DiTuAdapter extends BaseAdapter {
    Context mcontext;
    List<GarageBean.ResultBean> list_garage;
    LatLng mlatlng;
    List<SFguize> mlist;

    public DiTuAdapter(Context context, List<GarageBean.ResultBean> garage, LatLng latlng, List<SFguize> list) {
        mcontext = context;
        list_garage = garage;
        mlatlng = latlng;
        mlist = list;
        //初始化百度导航
        NavigateUtils.getInstance().initNavigate(mcontext);
    }

    @Override
    public int getCount() {
        return list_garage.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list_garage.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.lv_ditu_item, null);
            vh = new ViewHolder();
            vh.tv_dizhi = (TextView) convertView.findViewById(R.id.tv_place_dizhi);
            vh.tv_xxdizhi = (TextView) convertView.findViewById(R.id.tv_place_xxdizhi);
            vh.residuals = (TextView) convertView.findViewById(R.id.residuals);
            vh.distance = (TextView) convertView.findViewById(R.id.distance);
            vh.tv_ditu_price = (TextView) convertView.findViewById(R.id.tv_ditu_price);
            vh.tv_ditu_lx = (TextView) convertView.findViewById(R.id.tv_ditu_lx);
            vh.iv_go = (ImageView) convertView.findViewById(R.id.btn_go);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_dizhi.setText("" + list_garage.get(position).getCcmc());
        vh.tv_xxdizhi.setText("" + list_garage.get(position).getCcdz());
        vh.residuals.setText("" + list_garage.get(position).getKbwsl());
        String newDistance = StringUtil.convertMtoKM((int) DistanceUtil.getDistance(
                new LatLng(Double.valueOf(list_garage.get(position).getCczbY()), Double.valueOf(list_garage.get(position).getCczbX())), mlatlng));
        vh.distance.setText(newDistance + "");
        vh.tv_ditu_price.setText("" + StringUtil.convertFentoYuanWithout(mlist.get(position).getSfje()) + "元");
        if (mlist.get(position).getSfmc().equals("按次收费")) {
            vh.tv_ditu_lx.setText("/次");
        } else {
            vh.tv_ditu_lx.setText("/" + mlist.get(position).getSfmc());
        }
        vh.iv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到百度地图导航
                NaviParaOption option = new NaviParaOption().startName("我的位置").startPoint(mlatlng).endName("我要去的车场")
                        .endPoint(new LatLng(Double.valueOf(list_garage.get(position).getCczbY()), Double.valueOf(list_garage.get(position).getCczbX())));
//                BaiduMapNavigation.openBaiduMapNavi(option, mcontext);

                //百度经纬度坐标
                NavigateUtils.getInstance().openNavigate(option);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_dizhi, tv_xxdizhi, residuals, distance, tv_ditu_price, tv_ditu_lx;
        ImageView iv_go;
    }
}
