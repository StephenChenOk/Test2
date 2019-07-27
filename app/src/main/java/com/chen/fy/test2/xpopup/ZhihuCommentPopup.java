package com.chen.fy.test2.xpopup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chen.fy.test2.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;

import java.util.ArrayList;

class ZhihuCommentPopup extends BottomPopupView implements View.OnClickListener{

    VerticalRecyclerView recyclerView;
    private ArrayList<String> data;
    private MyAdapter myAdapter;

    public ZhihuCommentPopup(Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();
        initData();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        TextView click_comment_tv = findViewById(R.id.tv_temp);
        click_comment_tv.setOnClickListener(this);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(i+"这是一个自定义Bottom类型的弹窗！你可以在里面添加任何滚动的View，我已经智能处理好嵌套滚动，你只需编写UI和逻辑即可！");
        }
        myAdapter = new MyAdapter(data);
        myAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //不要直接这样做，会导致消失动画未执行完就跳转界面，不流畅。
//                dismiss();
//                getContext().startActivity(new Intent(getContext(), DemoActivity.class))
                //可以等消失动画执行完毕再开启新界面
                dismissWith(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });
    }

    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {

    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .85f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_temp:
                //弹出新的弹窗用来输入
                final CustomEditTextBottomPopup textBottomPopup = new CustomEditTextBottomPopup(getContext());
                new XPopup.Builder(getContext())
                      //  .autoOpenSoftInput(true)
                      //   .hasShadowBg(false)
                        .setPopupCallback(new SimpleCallback() {
                            @Override
                            public void onShow() {
                            }

                            @Override
                            public void onDismiss() {   //弹窗消失
                                String comment = textBottomPopup.getComment();
                                if (!comment.isEmpty()) {  //如果有发表评论，则增加到数据集中
                                    data.add(0,comment);
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .asCustom(textBottomPopup)   //添加弹窗
                        .show();
                break;
        }
    }
}
