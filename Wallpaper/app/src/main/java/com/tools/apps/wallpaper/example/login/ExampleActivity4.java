package com.tools.apps.wallpaper.example.login;

import com.tools.apps.wallpaper.R;
import com.tools.apps.wallpaper.base.BaseActivity;

/**
 * Fragment
 */
public class ExampleActivity4 extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    public void init() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, new ExampleFragment())
                .commit();
    }
}
