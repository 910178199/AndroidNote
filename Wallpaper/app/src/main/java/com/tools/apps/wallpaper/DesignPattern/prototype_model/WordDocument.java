package com.tools.apps.wallpaper.DesignPattern.prototype_model;

import android.util.Log;

import java.util.ArrayList;


/**
 * @author superman
 * 原型模式
 */
public class WordDocument implements Cloneable {

    private static final String TAG = WordDocument.class.getSimpleName();
    /**
     * 文本
     */
    private String mText;

    /**
     * 图片名列表
     */
    private ArrayList<String> mImages = new ArrayList<>();

    public WordDocument() {
        Log.e(TAG, "WordDocument: 构造函数");
    }

    @Override
    public WordDocument clone() {
        try {
            WordDocument wordDocument = (WordDocument) super.clone();
            wordDocument.mText = this.mText;
            /**
             * 浅拷贝：单纯的引用形式
             */
            wordDocument.mImages = this.mImages;
            /**
             * 深拷贝：在拷贝对象时，对引用型的字段也要进行拷贝形式
             */
            wordDocument.mImages = (ArrayList<String>) this.mImages.clone();
            return wordDocument;
        } catch (Exception e) {

        }
        return null;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void addImages(String img) {
        this.mImages.add(img);
    }

    /**
     * 打印文档内容
     */
    public void showDoc() {
        Log.e(TAG, "showDoc: " + "---Word Content Start ---");
        Log.e(TAG, "showDoc: " + "Text:" + mText);
        Log.e(TAG, "showDoc: " + "Image List:");
        for (String strName : mImages) {
            Log.e(TAG, "showDoc: " + strName);
        }
        Log.e(TAG, "showDoc: " + "---Word Content End ---");
    }
}
