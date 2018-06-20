package com.example.superman.applibs.dynamicPorxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimingInvocationHandler implements InvocationHandler {

    private static final String TAG = TimingInvocationHandler.class.getSimpleName();
    private Object mObject;

    public TimingInvocationHandler() {
    }

    public TimingInvocationHandler(Object mObject) {
        this.mObject = mObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = method.invoke(mObject, args);
        Log.e(TAG, "invoke: " + method.getName() + "time=" + (System.currentTimeMillis() - start));
        return obj;
    }
}
