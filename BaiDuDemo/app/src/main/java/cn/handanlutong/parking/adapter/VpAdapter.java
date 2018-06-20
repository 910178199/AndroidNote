package cn.handanlutong.parking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.UserProtocolActivity;
import cn.handanlutong.parking.bean.FirstPageImageBean;
import cn.handanlutong.parking.bean.ParkingActivityVo;
import cn.handanlutong.parking.http.AnsynHttpRequest;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.ObserverCallBack;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;

/**
 * Created by ww on 2017/4/14.
 */

public class VpAdapter extends PagerAdapter {
    Context mcontext;
    List<FirstPageImageBean.ResultBean> mlist;
    private int selected = -1;
    //    int zje_price;
    public boolean isClickable = true;
    String[] str = {"http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg",
            "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"};
    int[] sttr = {R.mipmap.bannerer0, R.mipmap.bannerer1, R.mipmap.bannerer2};

    public VpAdapter(Context context, List<FirstPageImageBean.ResultBean> list) {
        mcontext = context;
        mlist = list;
//        zje_price = zje;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(mcontext, R.layout.vp_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.vp_item_iv);
        final List<ImageView> zong_list = new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            zong_list.add(iv);
        }
        zong_list.get(position % mlist.size()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_url = mlist.get(position % mlist.size()).getNr();
                if (! StringUtil.isEmpty(str_url)){
                    Intent intent = new Intent(mcontext, UserProtocolActivity.class);
                    intent.putExtra("H5_url", str_url);
                    intent.putExtra("yhjurl", "");
                    intent.putExtra("fxurl", "");
                    intent.putExtra("tv_title", "活动详情");
                    intent.putExtra("hdgqsj", mlist.get(position % mlist.size()).getSfgq());
                    mcontext.startActivity(intent);
                }
//                ToastUtil.showToast(mcontext,"点击了"+position%mlist.size());
            }
        });
        //用Volley加载图片
//        ImageLoaderPicture vlp = new ImageLoaderPicture(mcontext, iv);
//        vlp.getmImageLoader().get(mlist.get(position).getTpdz(), vlp.getOne_listener());
//        iv.setImageResource(sttr[position%sttr.length]);
        new BitmapUtils(mcontext).display(iv, mlist.get(position % mlist.size()).getTpdz());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
