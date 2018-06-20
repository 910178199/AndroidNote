package cn.handanlutong.parking.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.customview.YWLoadingDialog;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.ImageUtils;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;

/**
 * 车辆认证页面
 * Created by ww on 2017/10/16.
 */

public class CarVehiclecardActivity extends BaseActivity implements View.OnClickListener {
    RelativeLayout back, pop_parent;
    private LinearLayout ll_popup;
    Button btn_go, bt1, bt2, bt3;
    TextView tv_car_num;
    private View parentView;
    private PopupWindow pop = null;
    private static String path = "/sdcard/myHead/";// sd路径
    private static final int IMAGE_REQUEST_CODE = 0;// 相册
    private static final int CAMERA_REQUEST_CODE = 1;// 拍照
    private static final int RESULT_REQUEST_CODE = 2;// 请求码
    private Bitmap head;// 头像Bitmap
    private Uri photoUri;
    private SharedPreferenceUtil spUtil;
    String str_num, type,cpys;
    private YWLoadingDialog mDialog1, mDialog2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.activity_carvehiclecard, null);
        setContentView(parentView);
        back = (RelativeLayout) findViewById(R.id.back);
        tv_car_num = (TextView) findViewById(R.id.tv_car_renzheng);
        btn_go = (Button) findViewById(R.id.btn_go);
        back.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        pop = new PopupWindow(CarVehiclecardActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popwindow_myhead, null);
        pop_parent = (RelativeLayout) view.findViewById(R.id.pop_parent);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        Intent intent = getIntent();
        try {
            str_num = intent.getStringExtra("car_num");
            cpys = intent.getStringExtra("cpys");
            type = intent.getStringExtra("type"); //去认证 1   去找回2
        } catch (Exception e) {
            e.printStackTrace();
        }
        JieKouDiaoYongUtils.verifyStoragePermissions(this);
        tv_car_num.setText("" + str_num + "");
    }

    /**
     * 车辆 去认证接口
     */
    private void upIMGpostData(Bitmap bitmap) {
        mDialog1 = new YWLoadingDialog(this);
        mDialog1.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("hphm", str_num);
                jsobj2.put("cpys", cpys);
                jsobj2.put("image", ImageUtils.bitmapToBase64(bitmap));
                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CarVehiclecard(httpUtils, jsobj1, this, UrlConfig.Car_Vehiclecard_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络!");
        }
    }

    /**
     * 车辆 去找回接口
     */
    private void upIMGpostData2(Bitmap bitmap) {
        mDialog2 = new YWLoadingDialog(this);
        mDialog2.show();
        if (NetWorkUtil.isNetworkConnected(this)) {
            spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("hphm", str_num);
                jsobj2.put("cpys", cpys);
                jsobj2.put("image", ImageUtils.bitmapToBase64(bitmap));

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.CarVehiclecard2(httpUtils, jsobj1, this, UrlConfig.Car_Vehiclecard_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_go:
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_camera:
//                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photoFile = createImgFile();
//
//                photoUri = Uri.fromFile(photoFile);
//                cropPhoto(photoUri);
//                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
//                startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.CAMERA
                        }, 222);
                        return;
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        photoUri = get24MediaFileUri(1);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")));
                        startActivityForResult(intent, IMAGE_REQUEST_CODE);
                    }
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                }
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_Photo:
//                if (ContextCompat.checkSelfPermission(this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new
//                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                } else {
//                    openAlbum();
//                }
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, CAMERA_REQUEST_CODE);
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_cancel:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
//                if (data != null) {
//                    cropPhoto(data.getData());// 裁剪图片
//                }
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = bundle.getParcelable("data");
                if (resultCode == -1){
                    try{
                        Bitmap take = resizeBitmap(getBitmapForFile(getRealFilePath(this,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")))),640);
                        if (type.equals("1")) {
                            upIMGpostData(take);
                        } else if (type.equals("2")) {
                            upIMGpostData2(take);
                        }
                    }catch(Exception e){
                        ToastUtil.makeShortText(this, "上传失败");
                    }
                } else {
                    ToastUtil.makeShortText(this, "上传失败");
                }
//                galleryAddPic();
//                if (data != null){
//                    Bitmap bitmap2 = data.getParcelableExtra("data");
//                    if (bitmap2 != null){
//                        if (type.equals("1")) {
//                            upIMGpostData(bitmap2);
//                        } else if (type.equals("2")) {
//                            upIMGpostData2(bitmap2);
//                        }
//                    } else {
//                        ToastUtil.makeShortText(this, "上传失败");
//                    }
//                } else {
//                    ToastUtil.makeShortText(this, "上传失败");
//                }
                break;
            case CAMERA_REQUEST_CODE:
//                galleryAddPic();
//                File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
//                cropPhoto(Uri.fromFile(temp));// 裁剪图片
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage,
                            filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();

                    head = BitmapFactory.decodeFile(picturePath);
                    if (type.equals("1")) {
                        upIMGpostData(head);
                    } else if (type.equals("2")) {
                        upIMGpostData2(head);
                    }
                } catch (Exception e) {
                    ToastUtil.makeShortText(this, "上传失败");
                }
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        head = extras.getParcelable("data");
                        if (head != null) {
                            JieKouDiaoYongUtils.verifyStoragePermissions(this);
                            setPicToView(head);// 保存在SD卡中
                            if (type.equals("1")) {
                                upIMGpostData(head);
                            } else if (type.equals("2")) {
                                upIMGpostData2(head);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1.5);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    private void openAlbum() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, IMAGE_REQUEST_CODE);
    }

    //将图片添加进手机相册
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null){
                    // 关闭流
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.Car_Vehiclecard_CODE:
                if (mDialog1 != null){
                    if (mDialog1.isShowing()){
                        mDialog1.dismiss();
                    }
                }
                if (mDialog2 != null){
                    if (mDialog2.isShowing()){
                        mDialog2.dismiss();
                    }
                }
                LogUtils.d("上传行驶证成功：" + responseInfo);
                try {
                    JSONObject object = new JSONObject(responseInfo);
                    int code = object.optInt("code");
                    String message = object.optString("message");
                    if (code == 0) {
                        ToastUtil.makeShortText(this, "上传成功");
                        Intent intent = new Intent(this, MyCarActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = object.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        if (!StringUtil.isEmpty(message)) {
                            ToastUtil.makeShortText(this, message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailureHttp(HttpException error, String msg, int resultCode) {
        super.onFailureHttp(error, msg, resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
//                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
//                    startActivityForResult(intent2, CAMERA_REQUEST_CODE);// 采用ForResult打开
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, IMAGE_REQUEST_CODE);
                } else {
                    // Permission Denied
                    ToastUtil.makeShortText(this, "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float temp = ((float) height) / ((float) width);
        int newHeight = (int) ((newWidth) * temp);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
//        matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    public static Bitmap getBitmapForFile(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
