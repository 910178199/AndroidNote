package cn.handanlutong.parking.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.NeedHelpAdapter;
import cn.handanlutong.parking.adapter.NeedHelpAdapter.ViewHolder;
import cn.handanlutong.parking.bean.ParkingServiceType;
import cn.handanlutong.parking.customview.NoScrollListView;
import cn.handanlutong.parking.http.HttpMethod;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.JieKouDiaoYongUtils;
import cn.handanlutong.parking.utils.LogUtils;
import cn.handanlutong.parking.utils.NetWorkUtil;
import cn.handanlutong.parking.utils.SharedPreferenceUtil;
import cn.handanlutong.parking.utils.StringUtil;
import cn.handanlutong.parking.utils.ToastUtil;
import cn.handanlutong.parking.utils.UpdateManager;


/**
 * Created by zhangyonggang on 2018/1/11.
 */

public class NeedHelpActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView iv_back_needHelp;
    private EditText et_content;
    private long id;
    private SharedPreferenceUtil spUtil;
    private NoScrollListView lv_trouble;
    private NeedHelpAdapter adapter;
    private List<ParkingServiceType> mList;
    private RelativeLayout rl_call_phone;
    private Button btn_Submit;
    private String serviceTypeId = "";
    private List<Integer> intList = new ArrayList<>();
    private String bz;
    private TextView tv_count;

    @Override
    public void initView() {
        super.initView();
        setContentView(R.layout.activity_needhelp);
        spUtil = SharedPreferenceUtil.init(this, SharedPreferenceUtil.LOGIN_INFO, Activity.MODE_PRIVATE);
        iv_back_needHelp = (ImageView) findViewById(R.id.iv_back_needHelp);
        btn_Submit = (Button) findViewById(R.id.btn_Submit);
        et_content = (EditText) findViewById(R.id.et_content);
        lv_trouble = (NoScrollListView) findViewById(R.id.lv_trouble);
        rl_call_phone = (RelativeLayout) findViewById(R.id.rl_call_phone);
        tv_count = (TextView) findViewById(R.id.tv_count);
        et_content.addTextChangedListener(mTextWatcher);
        mList = new ArrayList<>();
        Intent intent = getIntent();
        id = intent.getLongExtra("jlid", 0);
        findHelpList();
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            temp = charSequence;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            editStart = et_content.getSelectionStart();
            editEnd = et_content.getSelectionEnd();
            tv_count.setText(String.valueOf(temp.length()));
//            if (temp.length() > 140) {
//                ToastUtil.makeShortText(NeedHelpActivity.this, "您输入的字数已经超过了限制!");
//                editable.delete(editStart - 1, editEnd);
//                int tempSelection = editEnd;
//                et_content.setText(editable);
//                et_content.setSelection(140);
//            }
        }
    };

    private void findHelpList() {
        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            try {
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("appType", UrlConfig.android_type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUserGetHelpList(httpUtils, jsobj1, this, UrlConfig.GET_TROUBLE_LIST_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    private void SubmitQuestion() {
        btn_Submit.setEnabled(false);
        if (!adapter.getIsSelected().isEmpty()) {
            Iterator iter = adapter.getIsSelected().keySet().iterator();
            while (iter.hasNext()) {
                int key = (int) iter.next();
                boolean value = adapter.getIsSelected().get(key);
                LogUtils.d("key " + key);
                LogUtils.d("value " + value);
                if (value == true) {
                    intList.add(key);
                }
            }
            LogUtils.d("intList.size() " + intList.size());
            if (intList.size() > 0) {
                for (int i = 0; i < intList.size(); i++) {
                    serviceTypeId += mList.get(intList.get(i)).getId() + ",";
                }
                LogUtils.d("serviceTypeId is " + serviceTypeId);
                serviceTypeId = serviceTypeId.substring(0, serviceTypeId.length() - 1);
                LogUtils.d("serviceTypeId ddd is " + serviceTypeId);
            }
        }
        bz = et_content.getText().toString();
        if (StringUtil.isEmpty(serviceTypeId) && StringUtil.isEmpty(bz)) {
            ToastUtil.makeShortText(this, "请选择或者输入问题");
            return;
        }

        if (NetWorkUtil.isNetworkConnected(this)) {
            JSONObject jsobj1 = new JSONObject();
            JSONObject jsobj2 = new JSONObject();
            try {
                jsobj2.put("serviceTypeId", serviceTypeId);
                jsobj2.put("tcjlId", id);
                jsobj2.put("bz", bz);
                jsobj1.put("authKey", spUtil.getString("authkey"));
                jsobj1.put("version", JieKouDiaoYongUtils.getVerName(this));
                jsobj1.put("appType", UrlConfig.android_type);
                jsobj1.put("userId", spUtil.getInt("id"));
                jsobj1.put("parameter", jsobj2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpMethod.AppUseraddServiceInfo(httpUtils, jsobj1, this, UrlConfig.SERVICE_ADDINFO_CODE);
        } else {
            ToastUtil.makeShortText(this, "请检查网络！");
        }
    }


    @Override
    public void setLisener() {
        super.setLisener();
        iv_back_needHelp.setOnClickListener(this);
        rl_call_phone.setOnClickListener(this);
        btn_Submit.setOnClickListener(this);
        lv_trouble.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_needHelp:
                finish();
                break;
            case R.id.btn_Submit:
                SubmitQuestion();
                break;
            case R.id.rl_call_phone:
                showAlertDialog();
                break;
            default:
                break;
        }
    }


    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_dial_phone, null);
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
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-055-5886"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置Listview的高度
     */
    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onSuccessHttp(String responseInfo, int resultCode) {
        super.onSuccessHttp(responseInfo, resultCode);
        switch (resultCode) {
            case UrlConfig.GET_TROUBLE_LIST_CODE:
                LogUtils.d("查找问题列表成功：" + responseInfo);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        JSONArray jarray = obj.optJSONArray("result");
                        for (int i = 0; i < jarray.length(); i++) {
                            ParkingServiceType parkingservicetype = new ParkingServiceType();
                            JSONObject obj1 = jarray.optJSONObject(i);
                            parkingservicetype.setLxms(obj1.optString("lxms"));
                            parkingservicetype.setId(obj1.optLong("id"));
                            mList.add(parkingservicetype);
                        }
                        adapter = new NeedHelpAdapter(this, mList);
                        lv_trouble.setAdapter(adapter);
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else{
                        if (! StringUtil.isEmpty(result)){
                            ToastUtil.makeShortText(this, result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case UrlConfig.SERVICE_ADDINFO_CODE:
                LogUtils.d("提交问题成功：" + responseInfo);
                btn_Submit.setEnabled(true);
                try {
                    JSONObject obj = new JSONObject(responseInfo);
                    int code = obj.optInt("code");
                    String result = obj.optString("result");
                    if (code == 0) {
                        ToastUtil.makeShortText(this, "提交成功");
                        finish();
                    } else if(code == 9999){
                        ToastUtil.makeShortText(this, "提交失败，可能是因为不支持特殊字符");
                    } else if (code == 1001){ //版本更新 弹框
                        JSONObject obj1 = obj.optJSONObject("result");
                        int newversionNo = Integer.parseInt(obj1.optString("versionNo"));
                        if (newversionNo > JieKouDiaoYongUtils.getVersionCode(this)) {
                            UpdateManager mUpdateManager = new UpdateManager(this);
                            mUpdateManager.showNoticeDialog(obj1.optString("versionPath"), newversionNo, obj1.optString("versionDescription"));
                        }
                    } else if (code == 1002){ //退出登录 弹框
                        JieKouDiaoYongUtils.loginTanKuan(this);
                    } else{
                        if (! StringUtil.isEmpty(result)){
                            ToastUtil.makeShortText(this, result);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.cb_select.toggle();
        adapter.getIsSelected().put(position, holder.cb_select.isChecked());
        adapter.notifyDataSetChanged();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
