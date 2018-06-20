package cn.handanlutong.parking.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.activity.MainActivity;

/**
 * Created by ww on 2017/6/21.
 */

public class UpdateManager {
    private Context mContext; //上下文
    private static final String savePath = "/sdcard/HandanbocheupdateAPK/"; //apk保存到SD卡的路径
    private static final String saveFileName = savePath + "apkName.apk"; //完整路径名
    private ProgressBar mProgress; //下载进度条控件
    private static final int DOWNLOADING = 1; //表示正在下载
    private static final int DOWNLOADED = 2; //下载完毕
    private static final int DOWNLOAD_FAILED = 3; //下载失败
    private int progress; //下载进度
    private boolean cancelFlag = false; //取消下载标志位
    private TextView tv_progress;
    private AlertDialog  alertDialog2; //表示提示对话框、进度条对话框
    AlertDialog dialo;

    /**
     * 构造函数
     */
    public UpdateManager(Context context) {
        this.mContext = context;
    }


    /**
     * 显示更新对话框
     */
    public void showNoticeDialog(final String url, final int code, String descirp) {
        if (descirp.equals("1")) {//强制更新
            showAlertDialog(R.layout.dialog_addcar_full, url);
        } else {
            showAlertDialog(R.layout.dialog_personal_info, url);
        }
    }

    public void showAlertDialog(int layout, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        View view;
        switch (layout) {
            case R.layout.dialog_personal_info:
                view = LayoutInflater.from(mContext).inflate(R.layout.dialog_personal_info, null);
                dialog.show();

                dialog.setContentView(view);
                TextView tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
                TextView tv_Cancle = (TextView) view.findViewById(R.id.tv_Cancle);
                TextView tv_Sure = (TextView) view.findViewById(R.id.tv_Sure);
                tv_dialog_title.setText("发现新版本，赶快更新吧~");
                tv_Cancle.setText("离开");
                tv_Sure.setText("去升级");
                tv_Cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                tv_Sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else {
                        showDownloadDialog(url);
                        }
                    }
                });
                break;
            case R.layout.dialog_addcar_full:
                view = LayoutInflater.from(mContext).inflate(layout, null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);//设置外部不可点击
                dialog.setCancelable(false);
                dialog.setContentView(view);
                TextView tv_Remind = (TextView) view.findViewById(R.id.tv_Remind);
                TextView tv_AddCar_full = (TextView) view.findViewById(R.id.tv_AddCar_full);
                Button btn_Sure = (Button) view.findViewById(R.id.btn_Sure);
                tv_Remind.setText("升级提示：");
                btn_Sure.setText("去升级");
                tv_AddCar_full.setText("发现新版本，赶快更新吧~");
                btn_Sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else {
                        showDownloadDialog(url);
                        }
                    }
                });
                break;
            default:
                break;
        }

    }


    /**
     * 显示进度条对话框
     */
    public void showDownloadDialog(String url) {
        dialo = new AlertDialog.Builder(mContext).create();
        dialo.show();
        dialo.setCanceledOnTouchOutside(false);//设置外部不可点击
        dialo.setCancelable(false);
        dialo.getWindow().setContentView(R.layout.softupdate_progress);
        tv_progress = (TextView) dialo.getWindow().findViewById(R.id.tv_progress);
        tv_progress.setText("正在下载");
        mProgress = (ProgressBar) dialo.getWindow().findViewById(R.id.update_progress);
        //下载apk
        downloadAPK(url);
    }

    /**
     * 下载apk的线程
     */
    public void downloadAPK(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    String sttr = url;
                    URL url = new URL(sttr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    conn.setConnectTimeout(10000);//设置连接超时时间
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
//                    File file = new File(savePath);
                    StringBuilder path = new StringBuilder();
                    if (isSDAvailable()) {
                        path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
                        path.append(File.separator);
                        path.append("HandanbocheupdateAPK");
                    } else {
                        File filesDir = UiUtils.getContext().getCacheDir();
                        path.append(filesDir.getAbsolutePath());
                        path.append(File.separator);
                        path.append("HandanbocheupdateAPK");
                    }
                    File file = new File(path.toString());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String apkFile = saveFileName;
                    File ApkFile = new File(apkFile);

                    OutputStream fos = new FileOutputStream(ApkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOADING);
                        if (numread <= 0) {
                            //下载完成通知安装
                            mHandler.sendEmptyMessage(DOWNLOADED);
                            break;
                        }
                        fos.write(buf, 0, numread);
                    } while (!cancelFlag); //点击取消就停止下载.

                    fos.close();
                    is.close();
                } catch (Exception e) {
                    LogUtils.d("e is:" + e);
                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更新UI的handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case DOWNLOADING:
                    tv_progress.setText("正在下载（"+(mProgress.getProgress()*100/mProgress.getMax())+"%）");
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOADED:
                    if (alertDialog2 != null)
                        alertDialog2.dismiss();
                    if (dialo != null)
                        dialo.dismiss();
                    tv_progress.setText("下载完成");
                    installAPK();
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(mContext, "网络断开，请稍候再试", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 下载完成后自动安装apk
     */
    public void installAPK() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    ProgressDialog dialog;


    protected void initProgressDialog(long total, long current) {
        dialog.setTitle("更新app");//设置标题
        dialog.setMessage("版本号：  ");//设置dialog内容
        dialog.setCancelable(false);//点击空白处不可取消
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 水平线进度条,STYLE_SPINNER:圆形进度条
        dialog.setMax((int) total);//最大值
        dialog.setProgress((int) current);
        dialog.show();
    }

    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


}