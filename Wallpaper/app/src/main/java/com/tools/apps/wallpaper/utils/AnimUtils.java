package com.tools.apps.wallpaper.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Property;

public class AnimUtils {

    static abstract class FloatProperty<T> extends Property<T, java.lang.Float> {

        /**
         * A constructor that takes an identifying name and {@link #getType() type} for the property.
         *
         * @param name
         */
        public FloatProperty(String name) {
            super(Float.class, name);
        }

        public abstract void setValue(T object, Float value);

        @Override
        public void set(T object, Float value) {
            setValue(object, value);
        }
    }

    public static class ObservableColorMatrix extends ColorMatrix {

        private float saturation = 1f;

        public ObservableColorMatrix() {
            super();
        }

        @Override
        public void setSaturation(float saturation) {
            this.saturation = saturation;
            super.setSaturation(saturation);
        }

        public float getSaturation() {
            return saturation;
        }

        public static final Property<ObservableColorMatrix, Float> SATURATION =
                new FloatProperty<ObservableColorMatrix>("saturation") {
                    @Override
                    public void setValue(ObservableColorMatrix object, Float value) {
                        object.setSaturation(value);
                    }

                    @Override
                    public Float get(ObservableColorMatrix object) {
                        return object.getSaturation();
                    }
                };

    }

    /**
     * 修改图片饱和度
     */
    public static Bitmap setBitmapSaturation(Bitmap bitmap, int sat, int rotate, int scale) {
        Bitmap bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint();

        //设置图片饱和度
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(sat);

        //修改色调，即色彩矩阵围绕某种颜色分量
        ColorMatrix rotateMatrix = new ColorMatrix();
        // 0,1,2分别代表像素点颜色矩阵中的Red，Green,Blue分量
        rotateMatrix.setRotate(0, rotate);
        rotateMatrix.setRotate(1, rotate);
        rotateMatrix.setRotate(2, rotate);

        //修改亮度，即某种颜色分量的缩放
        ColorMatrix scaleMatrix = new ColorMatrix();
        scaleMatrix.setScale(scale, scale, scale, 1);

        //合并三种效果
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(colorMatrix);
//        imageMatrix.postConcat(rotateMatrix);
//        imageMatrix.postConcat(scaleMatrix);

/*        imageMatrix.preConcat(rotateMatrix);
        imageMatrix.preConcat(colorMatrix);
        imageMatrix.preConcat(scaleMatrix);*/

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bm;
    }

}
