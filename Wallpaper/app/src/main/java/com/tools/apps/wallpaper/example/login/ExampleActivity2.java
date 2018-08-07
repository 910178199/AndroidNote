package com.tools.apps.wallpaper.example.login;

import android.util.Log;

import com.tools.apps.wallpaper.R;
import com.tools.apps.wallpaper.base.BaseActivity;
import com.tools.apps.wallpaper.base.BasePresenter;
import com.tools.apps.wallpaper.base.CreatePresenter;
import com.tools.apps.wallpaper.example.login.login.LoginPresenter;
import com.tools.apps.wallpaper.example.login.login.LoginView;
import com.tools.apps.wallpaper.example.login.register.RegistPresenter;
import com.tools.apps.wallpaper.example.login.register.RegistView;

/**
 * 例子2：多个Presenter和使用 getPresenter 方法获取实例
 *
 * @author superman
 */
@CreatePresenter(presenter = {LoginPresenter.class, RegistPresenter.class})
public class ExampleActivity2 extends BaseActivity implements LoginView, RegistView {

    private static final String TAG = ExampleActivity2.class.getSimpleName();
    private LoginPresenter mLoginPresenter;
    private RegistPresenter mRegisPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        mLoginPresenter = (LoginPresenter) getPresenter(0);
        mRegisPresenter = (RegistPresenter) getPresenter(1);

        mLoginPresenter.login();
        mRegisPresenter.regist();
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
