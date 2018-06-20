package cn.handanlutong.parking.fragment;

import java.util.HashMap;
import java.util.Map;

import cn.handanlutong.parking.activity.TingcheJiLuActivity;

public class FragmentFactory2 {

    private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int position, TingcheJiLuActivity.onPullUpDownRefreshListener onPullUpDownRefreshListener) {
        BaseFragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new NoComplateFragment(onPullUpDownRefreshListener);
            } else if (position == 1) {
                fragment = new ComplateFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
            }
        }
        return fragment;

    }
}
