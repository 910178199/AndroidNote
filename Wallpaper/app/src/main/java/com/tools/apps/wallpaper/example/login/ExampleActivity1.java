package com.tools.apps.wallpaper.example.login;

import android.util.Log;

import com.tools.apps.wallpaper.R;
import com.tools.apps.wallpaper.base.BaseActivity;
import com.tools.apps.wallpaper.base.CreatePresenter;
import com.tools.apps.wallpaper.base.PresenterVariable;
import com.tools.apps.wallpaper.example.login.login.LoginPresenter;
import com.tools.apps.wallpaper.example.login.login.LoginView;
import com.tools.apps.wallpaper.example.login.register.RegistPresenter;
import com.tools.apps.wallpaper.example.login.register.RegistView;

/**
 * 例子1：多个Presenter和使用@PresenterVariable注解
 * <p>
 * 不用MVP模式  直接继承BaseActivity/BaseFragment即可
 *
 * @author superman
 */
@CreatePresenter(presenter = {RegistPresenter.class, LoginPresenter.class})
public class ExampleActivity1 extends BaseActivity<RegistPresenter> implements RegistView, LoginView {

    private static final String TAG = ExampleActivity1.class.getSimpleName();
    @PresenterVariable
    private LoginPresenter mLoginPresenter;
    @PresenterVariable
    private RegistPresenter mRegisPresenter;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        mLoginPresenter.login();
        mRegisPresenter.regist();
    }

    @Override
    public void regisSuccess() {
        Log.e(TAG, "regisSuccess: 注册成功");
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "loginSuccess: 登陆成功");
    }
}
