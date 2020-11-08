package com.koi.phoenix.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koi.phoenix.R;
import com.koi.phoenix.dialog.CommonDialog;
/**
 * 弹窗工具类
 */
public class DialogUtils {
    /**
     * 弹窗
     * @param activity 上下文
     * @param title 标题
     * @param content 内容
     * @param tip 提示
     */
    public static void dialog(Activity activity,String title, String content, String tip){
        CommonDialog commonDialog =new CommonDialog(activity, new CommonDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        });
        commonDialog.setTitle(title);
        LinearLayout layout = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//头像大小
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);//垂直显示
        layout.setGravity(Gravity.CENTER);//居中显示
        TextView contentView=new TextView(activity);//创建内容容器
        contentView.setText(content);//设置内容
        contentView.setTextSize(16);//设置字体大小
        contentView.setGravity(Gravity.CENTER);//居中显示
        layout.addView(contentView);//添加到LinearLayout容器
        if (Tools.isNotNull(tip)){
            TextView tipView=new TextView(activity);//创建昵称日期
            tipView.setText(tip);//设置提示
            tipView.setGravity(Gravity.CENTER);//居中显示
            tipView.setPadding(0,40,0,0);//边距
            tipView.setTextColor(activity.getResources().getColor(R.color.colorPrimaryTint));//字体颜色
            layout.addView(tipView);//添加到LinearLayout容器
        }
        commonDialog.setView(layout);//设置到弹窗中
        commonDialog.show();
    }

}
