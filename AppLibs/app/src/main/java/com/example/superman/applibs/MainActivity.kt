package com.example.superman.applibs

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.superman.applibs.dynamicPorxy.Operate
import com.example.superman.applibs.dynamicPorxy.OperateImpl
import com.example.superman.applibs.dynamicPorxy.TimingInvocationHandler
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Proxy


class MainActivity : AppCompatActivity() {

    /**
     * val v1 =num?.toInt() //不做处理返回 null

    val v2 =num?.toInt() ?:0 //判断为空时返回0

    val v3 =num!!.toInt() //抛出空指针异常（用“!!”表示不能为空）
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dm = resources.displayMetrics
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels
        Log.e("MainActivity", "" + screenHeight + "x" + screenWidth)

        button.setOnClickListener(View.OnClickListener {
            //动态代理

            /**
             * 1.目标函数之间解耦  Spring
             * 2.简化网络请求操作 Retrofit
             */
            val timingInvocationHandler = TimingInvocationHandler(OperateImpl())
            val operate = Proxy.newProxyInstance(Operate::class.java.classLoader, arrayOf<Class<*>>(Operate::class.java), timingInvocationHandler) as Operate
            operate.method1()
            System.out.println()
            operate.method2()
            System.out.println()
            operate.method3()
        })
    }
}
