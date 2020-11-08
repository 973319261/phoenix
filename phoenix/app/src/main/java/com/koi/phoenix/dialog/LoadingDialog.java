package com.koi.phoenix.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.koi.phoenix.R;

/**
 * 加载中动画弹窗
 */
public class LoadingDialog extends Dialog {
    private String text;//提示
    private AnimationDrawable animationDrawable;
 
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialog);
    }
 
    public LoadingDialog(Context context, String text) {
        super(context, R.style.loadingDialog);  //这里设置了Dialog的Style让其背景为透明的，并且没有title，具体代码就不贴了
        this.text = text;
    }
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        if (text!= null){
            TextView textView = (TextView) findViewById(R.id.loading_text);
            textView.setText(text);
        }
        ImageView progressImageView = (ImageView) findViewById(R.id.loading_image); //在ImageView上使用帧动画
        animationDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.progress_loading); //帧动画的初始化
        progressImageView.setImageDrawable(animationDrawable); //将动画设置在ImageView上
    }
 
    /**
     * 开始帧动画
     */
    @Override
    protected void onStart() {
        animationDrawable.start();
        super.onStart();
    }
 
    /**
     * 停止帧动画
     */
    @Override
    protected void onStop() {
        animationDrawable.stop();
        super.onStop();
    }
}