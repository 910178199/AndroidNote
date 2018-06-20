package cn.handanlutong.parking.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import cn.handanlutong.parking.R;

/**
 * Created by xxx on 2015/9/22.
 */
public class LicenseKeyboardUtil  {
    private Context ctx;
    private KeyboardView keyboardView;
    private Keyboard k1;// 省份简称键盘
    private Keyboard k2;// 数字字母键盘

    private String provinceShort[];
    private String letterAndDigit[];

    private EditText edits[];
    private int currentEditText = 0;//默认当前光标在第一个EditText
    private ImageView iv_blue, iv_yellow, iv_green;

    private int SelectFlag = 1;

    public LicenseKeyboardUtil(Context ctx, final EditText edits[],ImageView iv_blue,ImageView iv_yellow,ImageView iv_green) {
        this.ctx = ctx;
        this.edits = edits;
        this.iv_blue=iv_blue;
        this.iv_yellow=iv_yellow;
        this.iv_green=iv_green;
        edits[0].setText("冀");
        k1 = new Keyboard(ctx, R.xml.province_short_keyboard);
        k2 = new Keyboard(ctx, R.xml.file_paths);
        keyboardView = (KeyboardView) ((Activity) ctx).findViewById(R.id.keyboard_view);

//        keyboardView.setKeyboard(k2);
        keyboardView.setEnabled(true);
        //设置为true时,当按下一个按键时会有一个popup来显示<key>元素设置的android:popupCharacters=""
        keyboardView.setPreviewEnabled(false);
        for (int i = 0; i < edits.length; i++) {
            final int finalI = i;
            edits[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (SelectFlag == 1) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_lanse_xuanzhong_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_xuanzhong_kuang_carnumber);
                            }
                        } else if (SelectFlag == 2) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huangse_xuanzhong_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_xuanzhong_kuang_carnumber);
                            }
                        } else if (SelectFlag == 3) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_lvse_xuanzhong_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_xuanzhong_kuang_carnumber);
                            }
                        }

                        currentEditText = finalI;
                        if (currentEditText == 0) {
                            keyboardView.setKeyboard(k1);
                        } else {
                            keyboardView.setKeyboard(k2);
                        }
                    } else {
                        if (SelectFlag == 1) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_lanse_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_kuang_carnumber);
                            }
                        } else if (SelectFlag == 2) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huangse_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_kuang_carnumber);
                            }
                        } else if (SelectFlag == 3) {
                            if (!StringUtil.isEmpty(edits[finalI].getText().toString())) {
                                edits[finalI].setBackgroundResource(R.drawable.bg_lvse_kuang_carnumber);
                            } else {
                                edits[finalI].setBackgroundResource(R.drawable.bg_huise_kuang_carnumber);
                            }
                        }

                    }

                }
            });
        }
        edits[1].requestFocus();
        //设置键盘按键监听器
        keyboardView.setOnKeyboardActionListener(listener);
        provinceShort = new String[]{"京", "津", "冀", "鲁", "晋", "蒙", "辽", "吉", "黑"
                , "沪", "苏", "浙", "皖", "闽", "赣", "豫", "鄂", "湘"
                , "粤", "桂", "渝", "川", "贵", "云", "藏", "陕", "甘"
                , "青", "琼", "新", "港", "澳", "台", "宁"};

        letterAndDigit = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "Q", "W", "E", "R", "T", "Y", "U", "P"
                , "A", "S", "D", "F", "G", "H", "J", "K", "L"
                , "Z", "X", "C", "V", "B", "N", "M", "澳", "港"};
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            if (primaryCode == 112) { //xml中定义的删除键值为112
                edits[currentEditText].setText("");//将当前EditText置为""并currentEditText-1
//                edits[currentEditText].setBackgroundResource(R.drawable.bg_huise_kuang_carnumber);
                currentEditText--;
//                if(currentEditText < 1){
//                    //切换为省份简称键盘
//                    keyboardView.setKeyboard(k1);
//                }
                if (currentEditText < 0) {
                    currentEditText = 0;
                    edits[currentEditText].setBackgroundResource(R.drawable.bg_huise_xuanzhong_kuang_carnumber);
                }
            } else { //其它字符按键
                if (currentEditText == 0) {

                    edits[0].setText(provinceShort[primaryCode]);
                    currentEditText = 1;
                    //切换为字母数字键盘
                    keyboardView.setKeyboard(k2);
                } else {
                    //第二位必须大写字母
                    if (currentEditText == 1 && !letterAndDigit[primaryCode].matches("[A-Z]{1}")) {
                        return;
                    }
                    edits[currentEditText].setText(letterAndDigit[primaryCode]);
                    currentEditText++;
                    if (SelectFlag == 1) {
                        if (currentEditText > 6) {
                            currentEditText = 6;
                            edits[6].setBackgroundResource(R.drawable.bg_lanse_xuanzhong_kuang_carnumber);
                        }
                    } else if (SelectFlag == 2) {
                        if (currentEditText > 6) {
                            currentEditText = 6;
                            edits[6].setBackgroundResource(R.drawable.bg_huangse_xuanzhong_kuang_carnumber);
                        }
                    } else if (SelectFlag == 3) {
                        if (currentEditText > 7) {
                            currentEditText = 7;
                            edits[7].setBackgroundResource(R.drawable.bg_lvse_xuanzhong_kuang_carnumber);
                        }
                    }
                }
            }
            edits[currentEditText].requestFocus();
        }
    };

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 强制隐藏系统的软键盘
     */
    public void hideSystemKeyboard() {
        for (int i = 0; i < edits.length; i++) {
            edits[i].setInputType(InputType.TYPE_NULL);
        }
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    public void BluePress() {
        SelectFlag = 1;
        iv_blue.setEnabled(false);
        iv_yellow.setEnabled(true);
        iv_green.setEnabled(true);
        iv_blue.setImageResource(R.mipmap.blue_select_yes);
        iv_yellow.setImageResource(R.mipmap.yellow_select_no);
        iv_green.setImageResource(R.mipmap.green_select_no);
        edits[7].setVisibility(View.GONE);
        ClearEditText();
    }

    public void YellowPress() {
        SelectFlag = 2;
        iv_blue.setEnabled(true);
        iv_yellow.setEnabled(false);
        iv_green.setEnabled(true);
        iv_blue.setImageResource(R.mipmap.blue_select_no);
        iv_yellow.setImageResource(R.mipmap.yellow_select_yes);
        iv_green.setImageResource(R.mipmap.green_select_no);
        edits[7].setVisibility(View.GONE);
        ClearEditText();
    }

    public void GreenPress() {
        SelectFlag = 3;
        iv_blue.setEnabled(true);
        iv_yellow.setEnabled(true);
        iv_green.setEnabled(false);
        iv_blue.setImageResource(R.mipmap.blue_select_no);
        iv_yellow.setImageResource(R.mipmap.yellow_select_no);
        iv_green.setImageResource(R.mipmap.green_select_yes);
        edits[7].setVisibility(View.VISIBLE);
        ClearEditText();
    }

    private void ClearEditText() {
        currentEditText = 0;
        for (int i = 0; i < edits.length; i++) {
            edits[i].setText("");
            edits[i].setBackgroundResource(R.drawable.bg_huise_kuang_carnumber);
        }
        edits[0].requestFocus();
        edits[0].setBackgroundResource(R.drawable.bg_huise_xuanzhong_kuang_carnumber);
    }


}