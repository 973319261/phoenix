package com.koi.phoenix.ui.earnings;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.R;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

/**
 * 收益说明
 */
public class ExplainActivity extends AppCompatActivity {
    Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myActivity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_explain);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("收益说明", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭当前页面
            }
            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }
}
