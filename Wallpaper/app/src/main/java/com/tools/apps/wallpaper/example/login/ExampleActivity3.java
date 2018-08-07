package com.tools.apps.wallpaper.example.login;

import android.util.Log;

import com.tools.apps.wallpaper.R;
import com.tools.apps.wallpaper.base.BaseActivity;
import com.tools.apps.wallpaper.base.CreatePresenter;
import com.tools.apps.wallpaper.example.login.login.LoginPresenter;
import com.tools.apps.wallpaper.example.login.login.LoginView;

/**
 * 例子3：一个Presenter和使用 getPresenter 方法获取实例
 *
 * @author superman
 */
@CreatePresenter(presenter = LoginPresenter.class)
public class ExampleActivity3 extends BaseActivity<LoginPresenter> implements LoginView {
    private static final String TAG = ExampleActivity3.class.getSimpleName();

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        getPresenter().login();
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "loginSuccess: success");
    }

    @Override
    public void complete() {
        super.complete();

    }
}
