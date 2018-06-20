package cn.handanlutong.parking.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Map;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;

/**
 * Created by ww on 2017/11/21.
 */

public class FaPiaoMoreMessageActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_go;
    private RelativeLayout back;
    private Map<String, String> map_message = new HashMap<>();
    private EditText et_fapiao_shuoming, et_fapiao_yinghang, et_fapiao_yinghang_zhanghao, et_fapiao_dizhi;
    private SharedPreferenceUtil spUtil;
    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_fapiao_more);
        back=(RelativeLayout) findViewById(R.id.back);
        btn_go = (Button) findViewById(R.id.btn_go);
        et_fapiao_shuoming = (EditText) findViewById(R.id.et_fapiao_shuoming);
        et_fapiao_yinghang = (EditText) findViewById(R.id.et_fapiao_yinghang);
        et_fapiao_dizhi = (EditText) findViewById(R.id.et_fapiao_dizhi);
        et_fapiao_yinghang_zhanghao = (EditText) findViewById(R.id.et_fapiao_yinghang_zhanghao);
        JieKouDiaoYongUtils.NoShuRuBiaoQingFuHao(this, et_fapiao_shuoming);
        JieKouDiaoYongUtils.NoShuRuBiaoQingFuHao(this, et_fapiao_dizhi);
        JieKouDiaoYongUtils.NoShuRuBiaoQingFuHao(this, et_fapiao_yinghang);
        SharedPreferenceUtil spUtil2 = SharedPreferenceUtil.init(this,SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
        String str_ghfYhzh = spUtil2.getString("ghfYhzh");
        if (! StringUtil.isEmpty(str_ghfYhzh)){
            et_fapiao_dizhi.setText("" + spUtil2.getString("ghfDz"));
            et_fapiao_shuoming.setText("" + spUtil2.getString("bz"));
            et_fapiao_yinghang_zhanghao.setText("" + str_ghfYhzh);
        }
    }

    @Override
    public void setLisener() {
        super.setLisener();
        back.setOnClickListener(this);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_go:
                map_message.put("bz", et_fapiao_shuoming.getText().toString().trim()); //备注 说明
                map_message.put("ghfDz", et_fapiao_dizhi.getText().toString().trim());
                map_message.put("ghfYhzh", et_fapiao_yinghang.getText().toString().trim() +
                        et_fapiao_yinghang_zhanghao.getText().toString().trim());
                LogUtils.d("更多页面 参数-----" + map_message);
                spUtil = SharedPreferenceUtil.init(this,SharedPreferenceUtil.FaPiaoMore, MODE_PRIVATE);
                spUtil.put("bz", et_fapiao_shuoming.getText().toString().trim()); //备注 说明
                spUtil.put("ghfDz", et_fapiao_dizhi.getText().toString().trim()); //联系地址
                spUtil.put("ghfYhzh", et_fapiao_yinghang.getText().toString().trim() +
                        et_fapiao_yinghang_zhanghao.getText().toString().trim()); //开户行及账号
//                Intent intent = new Intent(this, FaPiaoADDActivity.class);
//                intent.putExtra("message", (Serializable) map_message);
//                setResult(0, intent);
                finish();
                break;
            default:
                break;
        }
    }
}
