package com.tools.apps.wallpaper.base;

/**
 * @author superman
 */
public interface BaseView {

    /**
     * 显示错误信息
     *
     * @param err
     */
    void showError(String err);

    /**
     * 完成
     */
    void complete();

    /**
     * 用于展示进度
     *
     * @param isShow
     */
    void showProgressUI(boolean isShow);


}
