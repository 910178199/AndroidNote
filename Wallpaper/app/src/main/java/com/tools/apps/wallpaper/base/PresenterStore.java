package com.tools.apps.wallpaper.base;


import java.util.HashMap;

/**
 * 储存获取Presenter
 *
 * @author superman
 */

public class PresenterStore<P extends BasePresenter> {

    private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
    private HashMap<String, P> mMap = new HashMap<>();

    /**
     * 添加Presenter类
     *
     * @param key
     * @param presenter
     */
    public final void put(String key, P presenter) {
        P oldPresenter = mMap.put(DEFAULT_KEY + ":" + key, presenter);
        if (oldPresenter != null) {
            oldPresenter.onCleared();
        }
    }

    /**
     * 获取Presenter类
     *
     * @return
     */
    public final P get(String key) {
        return mMap.get(DEFAULT_KEY + ":" + key);
    }

    /**
     * 清除
     */
    public void clear() {
        for (P presenter : mMap.values()) {
            presenter.onCleared();
        }
        mMap.clear();
    }

    public int getSize() {
        return mMap.size();
    }

    public HashMap<String, P> getMap() {
        return mMap;
    }

}
