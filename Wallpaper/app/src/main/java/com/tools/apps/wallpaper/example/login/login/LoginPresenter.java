package com.tools.apps.wallpaper.example.login.login;

import com.tools.apps.wallpaper.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView> {

    /**
     * 登陆
     */
    public void login() {
        mView.loginSuccess();
    }
}
