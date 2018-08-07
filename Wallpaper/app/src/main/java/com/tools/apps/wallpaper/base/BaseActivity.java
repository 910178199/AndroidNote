package com.tools.apps.wallpaper.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author superman
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    protected P getPresenter(int index) {
        return mPresenterProviders.getPresenter(index);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
    }

    @Override
    public void showError(String err) {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showProgressUI(boolean isShow) {

    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化
     */
    public abstract void init();

}
