package cn.handanlutong.parking.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;

import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.customview.swipemenulistview.SwipeMenuListView;
import cn.handanlutong.parking.http.ObserverCallBack;
import cn.handanlutong.parking.utils.LogUtils;


/**
 * Created by zhangyonggang on 2017/4/6.
 */

public abstract class BaseFragment extends Fragment implements ObserverCallBack {
    protected boolean isVisible;
    protected HttpUtils httpUtils = new HttpUtils();// 网络访问声明
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStartHttp() {
    }

    @Override
    public void onLoadingHttp(long total, long current, boolean isUploading) {
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {

    }

    @Override
    public void onFailureHttp(HttpException error, String msg) {

    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {

    }

    @Override
    public void onSuccessHttp(String result, int i, Object extraData) {

    }

    /**
     * 在这里实现Fragment数据的懒加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    /**
     * 设置Listview的高度
     */
    public void setListViewHeight(SwipeMenuListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    protected void onVisible(){
        lazyLoad();
    }
    protected abstract void lazyLoad();
}
