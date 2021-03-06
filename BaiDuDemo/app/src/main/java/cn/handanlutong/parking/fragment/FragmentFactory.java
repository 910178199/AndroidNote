package cn.handanlutong.parking.fragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {

	private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = null;
		fragment = mFragments.get(position);
		if (fragment == null) {
			if (position == 0) {
				fragment = new ExpenseFragment();
			} else if (position == 1) {
				fragment = new RechargeFragmet();
			}
			if (fragment != null) {
				mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
			}
		}
		return fragment;

	}
}
