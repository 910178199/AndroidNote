package com.tools.apps.wallpaper.example.login;

import android.util.Log;

import com.tools.apps.wallpaper.R;
import com.tools.apps.wallpaper.base.BaseFragment;
import com.tools.apps.wallpaper.base.CreatePresenter;
import com.tools.apps.wallpaper.base.PresenterVariable;
import com.tools.apps.wallpaper.example.login.login.LoginPresenter;
import com.tools.apps.wallpaper.example.login.login.LoginView;
import com.tools.apps.wallpaper.example.login.register.RegistPresenter;
import com.tools.apps.wallpaper.example.login.register.RegistView;

@CreatePresenter(presenter = {LoginPresenter.class, RegistPresenter.class})
public class ExampleFragment extends BaseFragment implements LoginView, RegistView {

    private static final String TAG = ExampleFragment.class.getSimpleName();
    @PresenterVariable
    private LoginPresenter mLoginPresenter;
    @PresenterVariable
    private RegistPresenter mRegisPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        mRegisPresenter.regist();
        mLoginPresenter.login();
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "loginSuccess: 登陆成功");
    }

    @Override
    public void regisSuccess() {
        Log.e(TAG, "regisSuccess: 注册成功");
    }
}
