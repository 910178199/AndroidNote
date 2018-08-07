package com.tools.apps.wallpaper.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tools.apps.wallpaper.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * PresenterDispatch 分发类
 *
 * @author superman
 */
public class PresenterDispatch {

    private PresenterProviders mProviders;

    public PresenterDispatch(PresenterProviders providers) {
        mProviders = providers;
    }

    public <P extends BasePresenter> void attachView(Context context, Object view) {
        PresenterStore store = mProviders.getPresenterStore();
        HashMap<String, P> map = store.getMap();
        for (Map.Entry<String, P> entry : map.entrySet()) {
            P presenterValue = entry.getValue();
            if (presenterValue != null) {
                presenterValue.attachView(context, view);
            }
        }
    }

    public <P extends BasePresenter> void detachView() {
        PresenterStore presenterStore = mProviders.getPresenterStore();
        HashMap<String, P> map = presenterStore.getMap();
        for (Map.Entry<String, P> entry : map.entrySet()) {
            P presenterValue = entry.getValue();
            if (presenterValue != null) {
                presenterValue.detachView();
            }
        }
    }

    public <P extends BasePresenter> void onCreatePresenter(@Nullable Bundle bundle) {
        PresenterStore presenterStore = mProviders.getPresenterStore();
        HashMap<String, P> map = presenterStore.getMap();
        for (Map.Entry<String, P> entry : map.entrySet()) {
            P presenterValue = entry.getValue();
            if (presenterValue != null) {
                presenterValue.onCreatePresenter(bundle);
            }
        }
    }

    public <P extends BasePresenter> void onDestroyPresenter() {
        PresenterStore presenterStore = mProviders.getPresenterStore();
        HashMap<String, P> map = presenterStore.getMap();
        for (Map.Entry<String, P> entry : map.entrySet()) {
            P value = entry.getValue();
            if (value != null) {
                value.onDestroyPresenter();
            }
        }
    }

    public <P extends BasePresenter> void onSaveInstanceState(Bundle outState) {
        PresenterStore presenterStore = mProviders.getPresenterStore();
        HashMap<String, P> map = presenterStore.getMap();
        for (Map.Entry<String, P> entry : map.entrySet()) {
            P value = entry.getValue();
            if (value != null) {
                value.onSaveInstanceState(outState);
            }
        }
    }
}
