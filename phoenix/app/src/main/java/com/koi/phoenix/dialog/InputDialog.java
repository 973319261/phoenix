package com.koi.phoenix.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.koi.phoenix.R;
import com.koi.phoenix.util.KeyBoardUtil;
import com.koi.phoenix.util.Tools;

/**
 * 输入框弹框
 */
public class InputDialog extends DialogFragment {
    private TextView tvTitle;//标题
    private EditText etContent;//内容
    private TextView tvNumber;//字数
    private TextView tvConfirm;//确认
    private TextView tvCancel;//取消

    private String title;//标题
    private String content;//内容
    private Integer type;//文本类型
    private Integer length;//可输入字体长度
    private Dialog dialog;//弹框
    private OnConfirmClickListener onConfirmClickListener;//存放对外部的开发的点击事件

    //InputType.TYPE_CLASS_TEXT 输入类型为普通文本
    //InputType.TYPE_CLASS_NUMBER 输入类型为数字文本
    //InputType.TYPE_CLASS_PHONE 输入类型为电话号码
    //InputType.TYPE_CLASS_DATETIME 输入类型为日期和时间
    public interface OnConfirmClickListener {
        void onConfirmClick(Dialog dialog, String content);
    }
    public InputDialog(String title,String content,Integer type,Integer length,OnConfirmClickListener onConfirmClickListener) {
        this.length=length;
        this.type=type;
        this.content = content;
        this.title = title;
        this.onConfirmClickListener=onConfirmClickListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog=new Dialog(getActivity(), R.style.dialog);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//软键盘就会把dialog弹起，有的手机则会遮住dialog布局。
        View view=View.inflate(getActivity(),R.layout.dialog_input,null);
        dialog.setContentView(view);
        //设置参数
        tvTitle=view.findViewById(R.id.et_dialog_title);
        etContent=view.findViewById(R.id.et_dialog_content);
        tvNumber=view.findViewById(R.id.tv_dialog_number);
        tvCancel=view.findViewById(R.id.tv_dialog_cancel);
        tvConfirm=view.findViewById(R.id.tv_dialog_confirm);
        tvTitle.setText(title);
        etContent.setText(content);
        tvNumber.setText(content.length()+"/"+length);
        etContent.setInputType(type);
        tvConfirm.setBackgroundResource(R.drawable.btn_border_fill);
        tvCancel.setBackgroundResource(R.drawable.btn_border_fill_gray);
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //绑定事件
        if(onConfirmClickListener != null) {
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoardUtil.hideKeyboard(v);//隐藏键盘
                    dismiss();
                }
            });
        }
        if(onConfirmClickListener != null){
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    KeyBoardUtil.hideKeyboard(v);//隐藏键盘
                    content=etContent.getText().toString();
                    if (Tools.isNotNull(content)){
                        onConfirmClickListener.onConfirmClick(dialog,content);
                    }else {
                        tvConfirm.setBackgroundResource(R.drawable.btn_border_fill_gray);
                        return;
                    }
                }
            });
        }
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNumber.setText(s.length()+"/"+length);
                if (s.length()>0){
                    tvConfirm.setBackgroundResource(R.drawable.btn_border_fill);
                    if (s.length()>length){//超出字体数量
                        int selectionEnd=etContent.getSelectionEnd();
                        s.delete(length,selectionEnd);
                    }else {
                        etContent.setEnabled(true);
                    }
                }else {
                    tvConfirm.setBackgroundResource(R.drawable.btn_border_fill_gray);
                }
            }
        });
        return dialog;
    }
}
