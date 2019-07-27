package com.chen.fy.test2.slide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.fy.test2.R;

public class SlideActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slide);

        TextView textView = findViewById(R.id.slide);
        textView.setOnTouchListener(this);
        textView.setOnClickListener(this);
    }

    int lastX;
    int lastY;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int slideX = x-lastX;
                int slideY = y-lastY;

                int translationX = (int) (v.getTranslationX()+slideX);
                int translationY = (int) (v.getTranslationY()+slideY);
                v.setTranslationX(translationX);
                v.setTranslationY(translationY);


                break;

        }
        lastX = x;
        lastY = y;
        return false;   //返回false,此时onClick()可以被执行
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(SlideActivity.this,"test",Toast.LENGTH_SHORT).show();
    }
}
