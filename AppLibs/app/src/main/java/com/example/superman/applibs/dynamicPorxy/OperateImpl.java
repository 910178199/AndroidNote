package com.example.superman.applibs.dynamicPorxy;

import android.util.Log;

public class OperateImpl implements Operate {


    private static final String TAG = OperateImpl.class.getSimpleName();

    @Override
    public void method1() {
        Log.e(TAG, "method1: ");
        sleep(100);
    }

    @Override
    public void method2() {
        Log.e(TAG, "method2: ");
        sleep(110);
    }

    @Override
    public void method3() {
        Log.e(TAG, "method3: ");
        sleep(120);
    }

    private static void sleep(int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
