package com.koi.phoenix.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koi.phoenix.R;

/**
 * 公共弹窗
 */
public class CommonDialog extends Dialog implements View.OnClickListener{
    private TextView title;//标题
    private LinearLayout content;//内容
    private TextView close;//关闭
    private OnCloseListener listener;
    private String strTitle;//标题
    private View view;//内容

    public CommonDialog(Context context, CommonDialog.OnCloseListener listener){
        super(context, R.style.dialog);
        this.listener = listener;
    }


    public void setTitle(String title){
        this.strTitle=title;
    }
    public void setView(View view){
        this.view=view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView(){
        title = (TextView)findViewById(R.id.title);
        content=(LinearLayout)findViewById(R.id.content);
        close = (TextView)findViewById(R.id.close);
        close.setOnClickListener(this);

        if(!TextUtils.isEmpty(strTitle)){
            title.setText(strTitle);
        }
        if (view!=null){
            content.addView(view);
        }
    }
    @Override
    public void onClick(View v) {
        listener.onClick(this, false);
    }
    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
}
