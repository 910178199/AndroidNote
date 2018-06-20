package cn.handanlutong.parking.zoom;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.utils.ToastUtil;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by mine on 2015/9/24.
 * */
public class ImagPagerUtil {
    private List<String> mPicList;
    private Activity mActivity;
    private Context mcontext;
    private Dialog dialog;
    private MultiTouchViewPager mViewPager;
    private List<Map<String, String>> mmList;
    private int screenWidth;
    private TextView tv_img_current_index,tv_rworlw,tv_rcsj;
    private TextView tv_img_count;
    private TextView tv_count;
    private TextView tv_baocun;
    int mType=0;
    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败
    private static final int SAVE_BEGIN = 2;//开始保存图片
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_BEGIN:
                    tv_baocun.setClickable(false);
                    break;
                case SAVE_SUCCESS:
                    ToastUtil.makeShortText(mActivity, "已经保存到系统相册");
                    tv_baocun.setClickable(true);
                    break;
                case SAVE_FAILURE:
                    tv_baocun.setClickable(true);
                    break;
            }
        }
    };
    Handler handler2 = new Handler() {
        public void handleMessage(Message msg) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            handler2.sendEmptyMessageDelayed(0, 3000);
        }
    };
    public ImagPagerUtil(Activity activity, List<String> mPicList, List<Map<String, String>> mmList,int type) {
        this.mPicList = mPicList;
        this.mActivity = activity;
        this.mmList=mmList;
        mType = type;
        mcontext = activity;
        init();
    }

    public void show() {
        dialog.show();
    }
    public void stop(){
        if (dialog != null){
            dialog.dismiss();
        }
    }
    private void init() {
        dialog = new Dialog(mActivity, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(mActivity, R.layout.view_dialogpager_img, null);
        mViewPager = getView(contentView, R.id.view_pager);
        tv_img_current_index = getView(contentView, R.id.tv_img_current_index);
        tv_img_count = getView(contentView, R.id.tv_img_count);
        tv_rworlw = getView(contentView, R.id.tv_rworlw);
        tv_rcsj = getView(contentView, R.id.tv_rcsj);
        tv_count = getView(contentView, R.id.tv_count);
        tv_baocun = getView(contentView, R.id.tv_baocun);
        dialog.setContentView(contentView);
        tv_img_count.setText(mPicList.size() + "");

        initViewPager();
    }

    private void initViewPager() {
        final MyImagPagerAdapter myImagPagerAdapter = new MyImagPagerAdapter();
        myImagPagerAdapter.notifyDataSetChanged();
        mViewPager.setAdapter(myImagPagerAdapter);
        Map<String,String> map = mmList.get(mType-1);
        Set set = map.keySet();
        Iterator iter = set.iterator();
        String key = null;
        while (iter.hasNext()) {
            key = (String) iter.next();
        }
        if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
            tv_rcsj.setText(map.get("rwsj"));
            tv_rworlw.setText("入场图片");
        } else {
            tv_rcsj.setText(map.get("lwsj"));
            tv_rworlw.setText("离场图片");
        }

        if (mType==1){
            tv_img_current_index.setText("1");
            mViewPager.setCurrentItem(0);
        }else if (mType==2){
                tv_img_current_index.setText("2");
            mViewPager.setCurrentItem(1);
        }else if (mType==3){
            tv_img_current_index.setText("3");
            mViewPager.setCurrentItem(2);
        }else if (mType==4){
            tv_img_current_index.setText("4");
            mViewPager.setCurrentItem(3);
        }else{
            mViewPager.setCurrentItem(0);
        }
//        handler2.sendEmptyMessageDelayed(0, 3000);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Map<String,String> map=mmList.get(position % mPicList.size());
                Set set = map.keySet();
                Iterator iter = set.iterator();
                String key = null;
                while (iter.hasNext()) {
                    key = (String) iter.next();
                }
                if (key.equals("rwtp1Path") || key.equals("rwtp2Path")) {
                    tv_rcsj.setText(map.get("rwsj"));
                    tv_rworlw.setText("入场图片");
                } else {
                    tv_rcsj.setText(map.get("lwsj"));
                    tv_rworlw.setText("离场图片");
                }

                if (mPicList.size()==4){
                    tv_img_current_index.setText("" + (position%mPicList.size() + 1));
                }else{
                    if (mType==1){
                        tv_img_current_index.setText("" + (position%mPicList.size() + 1));
                    }else if (mType==2){
                        tv_img_current_index.setText("" + (position%mPicList.size() + 1));
                    }else{
                        tv_img_current_index.setText("" + (position%mPicList.size() + 1));
                    }
                }
                tv_count.setText("" + mPicList.get(position%mPicList.size()));
                myImagPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mViewPager.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.makeShortText(mActivity,"-------....");
                return false;
            }
        });
        myImagPagerAdapter.notifyDataSetChanged();
    }

    class MyImagPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            tv_baocun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_baocun.setClickable(false);//不可重复点击
                    //保存图片必须在子线程中操作，是耗时操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                            Bitmap bp = returnBitMap(mPicList.get(position));
                            saveImageToPhotos(mActivity, bp);
                        }
                    }).start();
                }
            });
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(mcontext);
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setUri(Uri.parse(mPicList.get(position%mPicList.size())));
            photoDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            controller.setOldController(photoDraweeView.getController());
            // 需要设置 ControllerListener，获取图片大小后，传递给 PhotoDraweeView 更新图片长宽
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
//                    dialog.dismiss();
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());
            photoDraweeView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);//设置空白处可以点击
                    dialog.setCancelable(false);
                    dialog.getWindow().setContentView(R.layout.img_dialog);
                    final Button btn_take = (Button) dialog.getWindow().findViewById(R.id.btn_pick_photo);
                    btn_take.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            btn_take.setClickable(false);//不可重复点击
                            //保存图片必须在子线程中操作，是耗时操作
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mHandler.obtainMessage(SAVE_BEGIN).sendToTarget();
                                    Bitmap bp = returnBitMap(mPicList.get(position));
                                    saveImageToPhotos(mActivity, bp);
                                }
                            }).start();
                        }
                    });
                    Button btn_cancle = (Button) dialog.getWindow().findViewById(R.id.btn_cancel);
                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    return false;
                }
            });
//            photoDraweeView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()){
//                        case MotionEvent.ACTION_DOWN:
//                            dialog.dismiss();
//                            break;
//                    }
//                    return false;
//                }
//            });
            try {
                container.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return photoDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (null == mPicList || mPicList.size() <= 0) {
                return 0;
            }
            return mPicList.size()*100;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Activity context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
            return;
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }

    @SuppressWarnings("unchecked")
    public static final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            Log.e("ImagPageUtil", "Could not cast View to concrete class \n" + ex.getMessage());
            throw ex;
        }
    }
}
