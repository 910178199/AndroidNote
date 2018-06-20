#include <jni.h>
#include <string>
#include <stdio.h>
#include <string.h>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_app_nekdemo_ndkdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_app_nekdemo_ndkdemo_MainActivity_getStringFromC(JNIEnv *env, jobject instance) {

    //定义一个C语言字符串
    char *cstr = "hello form c";

    //第二种方法，推荐
    jstring jstr2 = env->NewStringUTF(cstr);
    return jstr2;
}