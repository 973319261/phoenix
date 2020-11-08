package com.koi.phoenix.widget;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import com.koi.phoenix.R;

/**
 * 自定义ActionBar
 */
public final class MyActionBar extends LinearLayout {

    private LinearLayout llActionbarRoot;//自定义ActionBar根节点
    private View vStatusBar;//状态栏位置
    private RelativeLayout rvLeft;//左边图标，文字容器容器
    private ImageView ivLeft;//左边图标
    private TextView tvLeft;//左边文字
    private TextView tvTitle;//中间标题
    private RelativeLayout rlRight;//右边图标，文字容器容器
    private ImageView ivRight;//右边图标
    private TextView tvRight;//右边文字

    public MyActionBar(Context context) {
        this(context, null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);//设置横向布局
        View contentView = inflate(getContext(), R.layout.widget_actionbar, this);
        //获取控件
        llActionbarRoot = contentView.findViewById(R.id.ll_actionbar_root);
        vStatusBar = contentView.findViewById(R.id.v_statusbar);
        rvLeft = contentView.findViewById(R.id.rl_actionbar_left);
        ivLeft = contentView.findViewById(R.id.iv_actionbar_left);
        tvTitle = contentView.findViewById(R.id.tv_actionbar_title);
        rlRight = contentView.findViewById(R.id.rl_actionbar_right);
        tvRight = contentView.findViewById(R.id.tv_actionbar_right);
    }

    /**
     * 设置透明度
     *
     * @param transAlpha{Integer} 0-255 之间
     */
    public void setTranslucent(int transAlpha) {
        //设置透明度
        llActionbarRoot.setBackgroundColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.colorPrimary), transAlpha));
        tvTitle.setAlpha(transAlpha);
        tvLeft.setAlpha(transAlpha);
        tvRight.setAlpha(transAlpha);
        ivLeft.setAlpha(transAlpha);
        ivRight.setAlpha(transAlpha);
    }
    /**
     * 设置数据
     *
     * @param strTitle   标题
     * @param resIdLeft  左边图标资源
     * @param strRight   右边文字
     * @param intColor   内容颜色 0为白色 1为黑色
     * @param backgroundColor   背景颜色
     * @param listener   点击事件监听
     */
    public void setData(String strTitle, int resIdLeft, String strRight,int intColor,int backgroundColor, final ActionBarClickListener listener) {
        String textColor=intColor==0?"#FFFFFF":"#000000";
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
            tvTitle.setTextColor(Color.parseColor(textColor));
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strRight)) {
            tvRight.setText(strRight);
            tvRight.setTextColor(Color.parseColor(textColor));
        } else {
            tvRight.setVisibility(View.GONE);
        }
        if (resIdLeft == 0) {
            ivLeft.setVisibility(View.GONE);
        } else {
            ivLeft.setBackgroundResource(resIdLeft);
            ivLeft.setVisibility(View.VISIBLE);
        }
        if (backgroundColor==0){
            llActionbarRoot.setBackgroundResource(0);
        }else {
            llActionbarRoot.setBackgroundColor(backgroundColor);//设置标题栏背景颜色
        }
        if (listener != null) {
            rvLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeftClick();
                }
            });
            rlRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRightClick();
                }
            });
        }
    }

    public interface ActionBarClickListener {
        //左边点击
        void onLeftClick();
        //右边点击
        void onRightClick();
    }
}
