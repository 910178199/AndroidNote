package cn.handanlutong.parking.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.bean.UserInfoBean;
import cn.handanlutong.parking.customview.CircleImageView;
import cn.handanlutong.parking.customview.PickerScrollView;
import cn.handanlutong.parking.customview.PickerScrollView.onSelectListener;
import cn.handanlutong.parking.http.AnsynHttpRequest;
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
 * 个人信息
 * Created by zhangyonggang on 2017/4/13.
 */

public class PersonnalInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back_Personal_Info;
    private CircleImageView iv_MyHeadPhoto;
    private TextView tv_Sex, tv_Age, tv_UserName, tv_PhoneNUm;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View parentView;
    private RelativeLayout pop_parent;
    private Button bt1, bt2, bt3;
    private RelativeLayout rl_Sex, rl_Age, rl_nickName, rl_PhoneNum;
    private RelativeLayout picker_rel; // 选择器布局
    private PickerScrollView pickerscrlllview; // 滚动选择器
    private List<String> list; // 滚动选择器数据
    private Button bt_yes, bt_No;
    private String picker;
    private int flag, index;
    private SharedPreferenceUtil spUtil;

    private Uri photoUri;
    private String photoPath;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "head.jpg";
    private static String path = "/sdcard/myHead/";// sd路径
    private static final int IMAGE_REQUEST_CODE = 0;// 相册
    private static final int CAMERA_REQUEST_CODE = 1;// 拍照
    private static final int RESULT_REQUEST_CODE = 2;// 请求码
    private Bitmap head;// 头像Bitmap

    @Override
    public void initView() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        parentView = getLayoutInflater().inflate(R.layout.activity_personalinfo, null);
        setContentView(parentView);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, MODE_PRIVATE);
        iv_back_Personal_Info = (ImageView) findViewById(R.id.iv_back_Personal_Info);
        iv_MyHeadPhoto = (CircleImageView) findViewById(R.id.iv_MyHeadPhoto);
        rl_Sex = (RelativeLayout) findViewById(R.id.rl_Sex);
        rl_Age = (RelativeLayout) findViewById(R.id.rl_Age);
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        rl_PhoneNum = (RelativeLayout) findViewById(R.id.rl_PhoneNum);
        tv_Sex = (TextView) findViewById(R.id.tv_Sex);
        tv_Age = (TextView) findViewById(R.id.tv_Age);
        tv_UserName = (TextView) findViewById(R.id.tv_UserName);
        tv_PhoneNUm = (TextView) findViewById(R.id.tv_PhoneNUm);

        bt_yes = (Button) findViewById(R.id.picker_yes);
        bt_No = (Button) findViewById(R.id.picker_No);

        picker_rel = (RelativeLayout) findViewById(R.id.picker_rel);
        pickerscrlllview = (PickerScrollView) findViewById(R.id.pickerscrlllview);

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap

        String userImagePath = spUtil.getString("userImagePath");
        if (!StringUtil.isEmpty(userImagePath)) {
            ImageLoader.getInstance().loadImage(userImagePath, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view,
                                              Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    iv_MyHeadPhoto.setImageBitmap(loadedImage);
                }
            });
        }

        pop = new PopupWindow(PersonnalInfoActivity.this);
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
        JieKouDiaoYongUtils.verifyStoragePermissions(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_UserName.setText(spUtil.getString("fullName"));
        tv_PhoneNUm.setText(StringUtil.settingphone(spUtil.getString("phoneNo")));
        String age = spUtil.getString("age");
        tv_Age.setText(!StringUtil.isEmpty(age) ? age : "请选择");
        String sex = (spUtil.getString("sex"));
        tv_Sex.setText(sex.equals("1") ? "男" : sex.equals("0") ? "女" : "请选择");
    }

    @Override
    public void initData() {
        list = new ArrayList<String>();

    }

    // 滚动选择器选中事件
    onSelectListener pickerListener = new onSelectListener() {

        @Override
        public void onSelect(String pickers) {
            picker = pickers;
        }
    };

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
//        View view1= LayoutInflater.from(this).inflate(R.layout.dialog_personal_info,null);
        View view = getLayoutInflater().inflate(R.layout.dialog_personal_info, null);
        dialog.show();
        dialog.setContentView(view);
        TextView tv_Cancle = (TextView) view.findViewById(R.id.tv_Cancle);
        TextView tv_Sure = (TextView) view.findViewById(R.id.tv_Sure);
        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonnalInfoActivity.this.finish();
            }
        });
    }

    @Override
    public void setLisener() {
        iv_back_Personal_Info.setOnClickListener(this);
        iv_MyHeadPhoto.setOnClickListener(this);
        pop_parent.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);

        pickerscrlllview.setOnSelectListener(pickerListener);
        rl_Sex.setOnClickListener(this);
        rl_Age.setOnClickListener(this);
        bt_yes.setOnClickListener(this);
        bt_No.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_PhoneNum.setOnClickListener(this);
        picker_rel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_Personal_Info:
                finish();
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
                                Manifest.permission.CAMERA}, 222);
                        return;
                    } else {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                        startActivityForResult(intent2, CAMERA_REQUEST_CODE);// 采用ForResult打开
                    }
                } else {
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                    startActivityForResult(intent2, CAMERA_REQUEST_CODE);// 采用ForResult打开
                }
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_Photo:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.item_popupwindows_cancel:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.pop_parent:
                pop.dismiss();
                ll_popup.clearAnimation();
                break;
            case R.id.iv_MyHeadPhoto:
                ll_popup.startAnimation(AnimationUtils.loadAnimation(PersonnalInfoActivity.this, R.anim.activity_translate_in));
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_Sex:
                flag = 1;
                index = 0;
                picker = tv_Sex.getText().toString();
                list.clear();
                list.add("男");
                list.add("女");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(picker)) {
                        index = i;
                    }
                }
                if (index == 0) {
                    picker = list.get(0);
                }
                pickerscrlllview.setData(list, index);
                picker_rel.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_Age:
                flag = 2;
                index = 0;
                picker = tv_Age.getText().toString();
                list.clear();
                list.add("90后");
                list.add("80后");
                list.add("70后");
                list.add("60后");
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(picker)) {
                        index = i;
                    }
                }
                if (index == 0) {
                    picker = list.get(0);
                }
                pickerscrlllview.setData(list, index);
                picker_rel.setVisibility(View.VISIBLE);
                break;
            case R.id.picker_yes:
                if (flag == 1) {
                    tv_Sex.setText(picker);
                    if (picker.equals("男")) {
                        UpdateUserInfoSex(1);
                    } else {
                        UpdateUserInfoSex(0);
                    }

                } else if (flag == 2) {
                    tv_Age.setText(picker);
                    UpdateUserInfoAge(picker);
                }
                picker_rel.setVisibility(View.GONE);
                break;
            case R.id.picker_No:
                picker_rel.setVisibility(View.GONE);
                break;
            case R.id.picker_rel:
                picker_rel.setVisibility(View.GONE);
                break;
            case R.id.rl_nickName:
                Intent intent = new Intent(this, EditNikeNameActivity.class);
                intent.putExtra("nikeName", tv_UserName.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_PhoneNum:
                Intent intent1 = new Intent(this, UpdateUserPhoneNumActivity.class);
                intent1.putExtra("phoneNum", tv_PhoneNUm.getText().toString());
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void UpdateUserInfoSex(int type) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("sex", type);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserUpdateSex(httpUtils, jsobj1, this, UrlConfig.UPDATE_SEX_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private void UpdateUserInfoAge(String age) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("age", age);

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserUpdateAge(httpUtils, jsobj1, this, UrlConfig.UPDATE_AGE_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }

    private void uploadPic() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                JSONObject jsobj2 = new JSONObject();
                jsobj2.put("userImagePath", ImageUtils.bitmapToBase64(head));

                jsobj1.put("parameter", jsobj2);
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("authKey", spUtil.getString("authkey"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserUpdateHead(httpUtils, jsobj1, this, UrlConfig.UPLOADPHOTO_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络!");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_REQUEST_CODE:
                if (resultCode == -1){
                    if (data != null) {
                        cropPhoto(data.getData());// 裁剪图片
                    }
                } else {
                    ToastUtil.makeShortText(this, "上传失败");
                }
                break;
            case CAMERA_REQUEST_CODE:
                galleryAddPic();
                File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                cropPhoto(Uri.fromFile(temp));// 裁剪图片
                break;
            case RESULT_REQUEST_CODE:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        head = extras.getParcelable("data");
                        if (head != null) {
                            JieKouDiaoYongUtils.verifyStoragePermissions(this);
                            setPicToView(head);// 保存在SD卡中
                            iv_MyHeadPhoto.setImageBitmap(head);
                            uploadPic();
                        }
                    }
                }else{
                    ToastUtil.makeShortText(this, "上传失败");
                }
                break;
            default:
                break;
        }
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
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

    /**
     * 自定义图片名，获取照片的file
     */
    private File createImgFile() {
        //创建文件
        String fileName = "img_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";//确定文件名
        File dir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory();
        } else {
            dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }
        File tempFile = new File(dir, fileName);
        try {
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件路径
        photoPath = tempFile.getAbsolutePath();
        return tempFile;
    }


    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.UPDATE_SEX_CODE:
                LogUtils.d("修改性别成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        UserInfoBean.UserBean userinfobean = AnsynHttpRequest.parser.fromJson(result, UserInfoBean.UserBean.class);
                        spUtil.put("sex", userinfobean.getSex());
                        ToastUtil.makeShortText(this, "修改性别成功！");
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.UPDATE_AGE_CODE:
                LogUtils.d("修改年龄成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        UserInfoBean.UserBean userinfobean = AnsynHttpRequest.parser.fromJson(result, UserInfoBean.UserBean.class);
                        spUtil.put("age", userinfobean.getAge());
                        ToastUtil.makeShortText(this, "修改年龄成功！");
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.UPLOADPHOTO_CODE:
                LogUtils.d("上传头像成：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        UserInfoBean.UserBean userinfobean = AnsynHttpRequest.parser.fromJson(result, UserInfoBean.UserBean.class);
                        spUtil.put("userImagePath", userinfobean.getUserImagePath());
                        ToastUtil.makeShortText(this, "修改头像成功！");
                    } else if (code == 1001) { //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002) { //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else {
                        ToastUtil.makeShortText(this, result);
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
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                    startActivityForResult(intent2, CAMERA_REQUEST_CODE);// 采用ForResult打开
                } else {
                    // Permission Denied
                    ToastUtil.makeShortText(this, "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
