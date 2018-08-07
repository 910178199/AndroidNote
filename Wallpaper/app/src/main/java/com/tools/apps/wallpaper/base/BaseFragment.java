package com.tools.apps.wallpaper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    protected View mRootView;
    protected LayoutInflater mInflater;
    /**
     * 标志已经初始化完成
     */
    protected boolean isPrepared;

    /**
     * fragment是否可见
     */
    protected boolean isVisible;
    protected Context mContext;
    protected Activity mActivity;

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mActivity = getActivity();
            mContext = mActivity;
            this.mInflater = inflater;
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);

        mPresenterDispatch.attachView(getActivity(), this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        isPrepared = true;
        init();
        lazyLoad();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoadData();
        isPrepared = false;
    }

    /**
     * 加载数据
     */
    protected void lazyLoadData() {

    }

    /**
     * findViewById
     *
     * @param id
     * @return
     */
    public View findViewById(@IdRes int id) {
        View view;
        if (mRootView != null) {
            view = mRootView.findViewById(id);
            return view;
        }
        return null;
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInVisible() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInVisible();
        }
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
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenterDispatch.detachView();
    }

    @Override
    public void onDestroy() {
        this.mActivity = null;
        super.onDestroy();
    }
}
