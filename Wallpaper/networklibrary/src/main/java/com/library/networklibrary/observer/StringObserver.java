package com.library.networklibrary.observer;

import android.text.TextUtils;

import com.library.networklibrary.base.BaseStringObserver;
import com.library.networklibrary.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

public abstract class StringObserver extends BaseStringObserver {

    /**
     * 失败回调
     *
     * @param errorMsg 错误信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param data 结果
     */
    protected abstract void onSuccess(String data);


    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(String string) {
        onSuccess(string);
    }


    @Override
    public void doOnCompleted() {
    }

}
