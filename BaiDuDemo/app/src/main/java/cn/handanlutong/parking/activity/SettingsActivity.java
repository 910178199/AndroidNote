package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.zoom.DataCleanManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhangyonggang on 2017/4/27.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    private SharedPreferenceUtil spUtil;
    private ImageView iv_back_Settings;
    private Button btn_SignOut;
    private RelativeLayout rl_Help,rl_User_Protocol,rl_ReCharge_Protocol,rl_Evaluation,rl_Feedback,rl_AboutOurs;
    @Override
    public void initView() {
        setContentView(R.layout.activity_settings);
        iv_back_Settings= (ImageView) findViewById(R.id.iv_back_Settings);
        btn_SignOut= (Button) findViewById(R.id.btn_SignOut);
        rl_Help= (RelativeLayout) findViewById(R.id.rl_Help);
        rl_User_Protocol= (RelativeLayout) findViewById(R.id.rl_User_Protocol);
        rl_ReCharge_Protocol= (RelativeLayout) findViewById(R.id.rl_ReCharge_Protocol);
        rl_Evaluation= (RelativeLayout) findViewById(R.id.rl_Evaluation);
        rl_Feedback= (RelativeLayout) findViewById(R.id.rl_Feedback);
        rl_AboutOurs= (RelativeLayout) findViewById(R.id.rl_AboutOurs);
    }

    @Override
    public void setLisener() {
        iv_back_Settings.setOnClickListener(this);
        btn_SignOut.setOnClickListener(this);
        rl_Help.setOnClickListener(this);
        rl_User_Protocol.setOnClickListener(this);
        rl_ReCharge_Protocol.setOnClickListener(this);
        rl_Evaluation.setOnClickListener(this);
        rl_Feedback.setOnClickListener(this);
        rl_AboutOurs.setOnClickListener(this);
        btn_SignOut.setOnClickListener(this);
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog =builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_personal_info,null);
        dialog.show();
        dialog.setContentView(view);
        TextView tv_Cancle= (TextView) view.findViewById(R.id.tv_Cancle);
        TextView tv_Sure= (TextView) view.findViewById(R.id.tv_Sure);
        TextView tv_dialog_title= (TextView) view.findViewById(R.id.tv_dialog_title);
        tv_dialog_title.setText("是否注销？");
        tv_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("setactivity",""+ DataCleanManager.getTotalCacheSize(SettingsActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DataCleanManager.clearAllCache(SettingsActivity.this);//清空缓存
                spUtil = SharedPreferenceUtil.init(SettingsActivity.this,SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
                spUtil.removeCurrentUserInfo();
                SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(SettingsActivity.this, SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
                spUtil2.removeCurrentUserInfo();
                dialog.dismiss();
                killAll();
                JPushInterface.setAlias(SettingsActivity.this, "5555", new TagAliasCallback() {

                    @Override
                    public void gotResult(int arg0, String arg1, Set<String> arg2) {
//                            ToastUtil.showToast(MaininActivity.this, arg1 + arg2 + arg0);
                    }
                });
                Intent intent = new Intent(SettingsActivity.this,LoginActivity.class);
                startActivity(intent);
                SettingsActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_Settings:
                this.finish();
                break;
            case R.id.rl_Help:
                Intent intent1=new Intent(this,SYhelpActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_User_Protocol://用户协议
                Intent intent = new Intent(this,UserProtocolActivity.class);
                intent.putExtra("H5_url","file:///android_asset/agreement.html");
                intent.putExtra("tv_title","用户协议");
                intent.putExtra("hdgqsj", "false");
                startActivity(intent);
                break;
            case R.id.rl_ReCharge_Protocol://充值协议
                Intent intent_ReCharge_Protocol = new Intent(this,UserProtocolActivity.class);
                intent_ReCharge_Protocol.putExtra("H5_url","file:///android_asset/agreement_cz.html");
                intent_ReCharge_Protocol.putExtra("tv_title","充值协议");
                intent_ReCharge_Protocol.putExtra("hdgqsj", "false");
                startActivity(intent_ReCharge_Protocol);
                break;
            case R.id.rl_Evaluation:
                Intent intent_Evaluationl = new Intent(this,UserProtocolActivity.class);
                intent_Evaluationl.putExtra("H5_url","http://a.app.qq.com/o/simple.jsp?pkgname=cn.handanlutong.parking");
                intent_Evaluationl.putExtra("tv_title","应用宝");
                intent_Evaluationl.putExtra("hdgqsj", "false");
                startActivity(intent_Evaluationl);
                break;
            case R.id.rl_Feedback:
                Intent intent2 = new Intent(this,YiJianActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_AboutOurs:
//                Intent intent_AboutOurs = new Intent(this,UserProtocolActivity.class);
//                intent_AboutOurs.putExtra("H5_url","file:///android_asset/about_sm.html");
//                intent_AboutOurs.putExtra("tv_title","关于我们");
//                startActivity(intent_AboutOurs);
                Intent intent3 = new Intent(this,AboutActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_SignOut:
                showAlertDialog();
                break;

            default:
                break;
        }
    }
}
