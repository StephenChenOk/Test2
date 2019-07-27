package com.chen.fy.test2.xpopup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chen.fy.test2.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;

public class XPopupActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_popup_layout);

        initView();
    }

    private void initView() {
        Button btn_1 = findViewById(R.id.popup_btn_1);
        Button btn_2 = findViewById(R.id.popup_btn_2);
        Button btn_3 = findViewById(R.id.popup_btn_3);
        Button btn_4 = findViewById(R.id.popup_btn_4);
        Button btn_5 = findViewById(R.id.popup_btn_5);
        Button btn_6 = findViewById(R.id.popup_btn_6);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popup_btn_1:
                showPopup_1();
                break;
            case R.id.popup_btn_2:
                showPopup_2();
                break;
            case R.id.popup_btn_3:
                showPopup_3();
                break;
            case R.id.popup_btn_4:
                showPopup_4();
                break;
            case R.id.popup_btn_5:
                showPopup_5();
                break;
            case R.id.popup_btn_6:
                showPopup_6();
                break;
        }
    }

    private void showPopup_6() {
        new XPopup.Builder(this)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(new ZhihuCommentPopup(this)/*.enableDrag(false)*/)
                .show();
    }

    private void showPopup_4() {
        new XPopup.Builder(this)
                .asCenterList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4"},
                        null, 1,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                toast("click " + text);
                            }
                        })
                .show();
    }

    private void showPopup_5() {
        final BasePopupView loadingPopup = new XPopup.Builder(this)
                .asLoading("正在加载中")
                .show();
        loadingPopup.delayDismissWith(1500,new Runnable() {
            @Override
            public void run() {
                toast("我消失了！！！");
            }
        });
    }

    private void showPopup_3() {
        new XPopup.Builder(this)
//                        .enableDrag(false)
                .asBottomList("请选择一项", new String[]{"条目1", "条目2", "条目3", "条目4", "条目5"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                toast("click " + text);
                            }
                        })
                .show();
    }

    private void showPopup_2() {
        new XPopup.Builder(this)
                //.dismissOnBackPressed(false)
                .autoOpenSoftInput(true)
//                .autoFocusEditText(false) //是否让弹窗内的EditText自动获取焦点，默认是true
                .isRequestFocus(false)
                //.moveUpToKeyboard(false)   //是否移动到软键盘上面，默认为true
                .asInputConfirm("我是标题", "请输入内容。", "啊啊啊啊", "我是默认Hint文字",
                        new OnInputConfirmListener() {
                            @Override
                            public void onConfirm(String text) {
                                toast("input text: " + text);
//                                new XPopup.Builder(getContext()).asLoading().show();
                            }
                        }).show();
    }

    private void showPopup_1() {
        new XPopup.Builder(this)
                 .dismissOnTouchOutside(false)   //设置点击外部是否隐藏
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onCreated() {
                        Log.e("showPopup_1", "弹窗创建了");
                    }

                    @Override
                    public void onShow() {
                        Log.e("showPopup_1", "onShow");
                    }

                    @Override
                    public void onDismiss() {
                        Log.e("showPopup_1", "onDismiss");
                    }

                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    @Override
                    public boolean onBackPressed() {
                        toast("已拦截返回键，返回键不起作用");
                        return true;
                    }
                }).asConfirm("我是标题", "床前明月光，疑是地上霜；举头望明月，低头思故乡。",
                "取消", "确定",
                new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        toast("确定... ");
                    }
                }, null, false)
                .show();
    }

    private void toast(String text) {
        Toast.makeText(XPopupActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
