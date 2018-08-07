package com.tools.apps.wallpaper.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author superman
 */
public class BasePresenter<V> {

    protected Context mContext;
    protected V mView;

    protected void onCleared() {

    }

    /**
     * 附加View
     *
     * @param context
     * @param view
     */
    public void attachView(Context context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    /**
     * 分离View
     */
    public void detachView() {
        this.mView = null;
    }

    /**
     * 判断是否是附加
     *
     * @return
     */
    public boolean isAttachView() {
        return this.mView != null;
    }

    public void onCreatePresenter(@Nullable Bundle saveState) {

    }

    /**
     * 销毁
     */
    public void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }

    /**
     * 储存值
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {

    }


}
