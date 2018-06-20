package cn.handanlutong.parking.activity.baidu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.handanlutong.parking.R;
import cn.handanlutong.parking.adapter.HistoryAdapter;
import cn.handanlutong.parking.adapter.SuggestionAdapter;
import cn.handanlutong.parking.bean.HistoryBean;
import cn.handanlutong.parking.db.HistoryDao;
import cn.handanlutong.parking.http.UrlConfig;
import cn.handanlutong.parking.utils.CustomDialog;
import cn.handanlutong.parking.utils.StringUtil;

/**
 * Created by ww on 2017/4/15.
 * 百度搜索页面
 */

public class SerchActivity extends Activity implements View.OnClickListener{
    RelativeLayout iv_back;
    private RelativeLayout rl_history_record;
    private ListView lv_record;
    private TextView tv_clear_all;
    private ListView lv_suggestion;
    private ImageView iv_close;
    private EditText et_search;
    private String mCity;
    private LatLng mlatLng;
    private HistoryAdapter historyAdapter;
    private SuggestionAdapter suggestionAdapter;
    /**查询到的所有的记录 */
    private List<HistoryBean> allHistoryBeans;
    private HistoryDao historyDao;

    /** 初始化建议*/
    private SuggestionSearch mSuggestionSearch;
    private MyOnGetSuggestionResultListener suggestionlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        iv_back = (RelativeLayout) findViewById(R.id.back);
        rl_history_record = (RelativeLayout) findViewById(R.id.rl_history_record);
        View view= View.inflate(this, R.layout.activity_serch_lv,null);
        lv_record = (ListView) findViewById(R.id.lv_record);
        tv_clear_all = (TextView) view.findViewById(R.id.tv_clear_all);
        lv_suggestion = (ListView) findViewById(R.id.lv_suggestion);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_back.setOnClickListener(this);
        tv_clear_all.setOnClickListener(this);
        lv_record.addFooterView(view);
        iv_close.setOnClickListener(this);
        Intent intent=getIntent();
        if( intent != null ) {
            mCity = intent.getStringExtra("city");
            mlatLng = intent.getParcelableExtra("latlng");
        }
        et_search.requestFocus();
        StringUtil.showKeyBoard(SerchActivity.this, et_search);

        historyDao = new HistoryDao(SerchActivity.this);
        allHistoryBeans = historyDao.queryAll();
        if( allHistoryBeans.size() > 0  ){
            Collections.reverse(allHistoryBeans);
        }
        setHistoryAdapter(allHistoryBeans);

        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_close.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                iv_close.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (str.length()==0){
                    iv_close.setVisibility(View.GONE);
                }
                if( !TextUtils.isEmpty(str) ){
                    rl_history_record.setVisibility(View.GONE);
                    lv_suggestion.setVisibility(View.VISIBLE);
                    if(suggestionlistener != null){
                        suggestionlistener.setKeyWordAndData(str, new ArrayList<HistoryBean>());
                    }
//                    Log.d("SerchActivity", "str:" + str + ", mCity:" + mCity);
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(str).city(str));
                }else {
                    rl_history_record.setVisibility(View.VISIBLE);
                    lv_suggestion.setVisibility(View.GONE);
                }
            }
        });
        mSuggestionSearch = SuggestionSearch.newInstance();
        suggestionlistener = new MyOnGetSuggestionResultListener();
        mSuggestionSearch.setOnGetSuggestionResultListener(suggestionlistener);
        lv_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HistoryBean historyBean = allHistoryBeans.get(position-1);
                SharedPreferences sp = getSharedPreferences("search", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("search_address", historyBean.address);
                editor.commit();
                Intent intent=new Intent();
                Log.d("serch","000"+historyBean.address);
                intent.putExtra("et_search",historyBean.address);
                setResult(1001,intent);
                finish();
                Log.d("serch",mCity+"------"+historyBean.address+"-----"+et_search.getText().toString().trim());
                IndoorLocationActivity.startSearch(historyBean.city, historyBean.address);
            }
        });

        lv_suggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent=new Intent();
//                intent.putExtra("et_search",allHistoryBeans.get(position).address);
//                setResult(1001,intent);
                HistoryBean historyBean = (HistoryBean) suggestionAdapter.getItem(position);
                SharedPreferences sp = getSharedPreferences("search", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("search_address", historyBean.address);
                Log.d("serch","8888888888");
                editor.commit();
                historyDao.addOne(historyBean);
                Log.d("serch",mCity+"------"+historyBean.address+"-----"+et_search.getText().toString().trim());
                finish();
                IndoorLocationActivity.startSearch(mCity, historyBean.address);
            }
        });

        /**
         * 监听软件盘中搜素按钮点击事件
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String str = et_search.getText().toString().trim();
                    if( TextUtils.isEmpty(str) ){
                        return true;
                    }else {
                        // 先隐藏键盘
                        StringUtil.hindKeyBoard(SerchActivity.this);
//                        SharedPreferences sp = getSharedPreferences("search", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putString("search_address", str);
//                        editor.commit();
                        historyDao.addOne(new HistoryBean(str, str, str));
                        finish();
                        IndoorLocationActivity.startSearch(mCity, str);
                    }
                    return true;
                }
                return false;
            }
        });
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    iv_close.setVisibility(View.VISIBLE);
                }else{
                    iv_close.setVisibility(View.GONE);
                }
            }
        });
    }
    private void setHistoryAdapter(List<HistoryBean> historyBeans) {
        if (historyBeans == null || historyBeans.size() == 0) {
            tv_clear_all.setText("暂无搜索记录");
            return;
        }

        tv_clear_all.setText("清空全部历史记录");
        if (historyAdapter == null) {
            View view2= View.inflate(this, R.layout.activity_serch_lv2,null);
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            lv_record.addHeaderView(view2);
            historyAdapter = new HistoryAdapter(this, historyBeans);
            lv_record.setAdapter(historyAdapter);
            historyAdapter.notifyDataSetChanged();
        }
    }
    private class MyOnGetSuggestionResultListener implements OnGetSuggestionResultListener {
        private String keyWord;
        private List<HistoryBean> datas;
        public void setKeyWordAndData(String keyWord, List<HistoryBean> datas){
            this.keyWord = keyWord;
            this.datas = datas;
        }

        @Override
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                return;
            }
            List<SuggestionResult.SuggestionInfo> allSuggestions = res.getAllSuggestions();
            if(allSuggestions.size() == 0){
                return;
            }
            String text = et_search.getText().toString().trim();
            if(!keyWord.equals(text)){
                return;
            }

            for (SuggestionResult.SuggestionInfo suggestionInfo : allSuggestions) {
                mCity = suggestionInfo.city;
                Log.d("serchactivity",text+"-----"+suggestionInfo.city+"----"+suggestionInfo.district
                       +"------"+suggestionInfo.key+"-------"+suggestionInfo.pt+"------"+suggestionInfo.uid);
                if (suggestionInfo.pt != null && suggestionInfo.key != null ) {
                    Log.d("SerchActivity", "suggestionInfo.key"+suggestionInfo.key);
                    datas.add(new HistoryBean(suggestionInfo.key, suggestionInfo.city, suggestionInfo.district));
                }
            }
            setSuggestionAdapter(datas, keyWord);
        }
    }
    public void setSuggestionAdapter(List<HistoryBean> allSuggestionBeans, String keyWord) {
        if (allSuggestionBeans == null || allSuggestionBeans.size() == 0) {
            return;
        }

        if (suggestionAdapter == null) {
            suggestionAdapter = new SuggestionAdapter(SerchActivity.this, allSuggestionBeans, keyWord);
            lv_suggestion.setAdapter(suggestionAdapter);
        } else {
            suggestionAdapter.setDatas(allSuggestionBeans);
            suggestionAdapter.setKeyWord(keyWord);
            suggestionAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_clear_all:
                CustomDialog.Builder builder = new CustomDialog.Builder(SerchActivity.this);
                builder.setTitle("客服");
                builder.setMessage("\n确定清除全部历史记录？\n");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if( !(allHistoryBeans == null || allHistoryBeans.size() == 0) ){
                            historyAdapter.clearAllRecord();
                            historyAdapter.notifyDataSetChanged();
                            tv_clear_all.setText("暂无搜索记录");
                            tv_clear_all.setEnabled(false);
                            historyDao.deleteAll();
                        }
                    }
                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
                break;
            case R.id.iv_close:
                et_search.setText("");
                iv_close.setVisibility(View.GONE);
                break;
            case R.id.et_search:
                et_search.setCursorVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        historyDao.deleteAll();
        mSuggestionSearch.destroy();
    }
}
