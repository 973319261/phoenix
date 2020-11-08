package com.koi.phoenix.ui.user.redPacket;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

/**
 * 助力领红包页面
 */
public class RedPacketActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_red_packet);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("助力领红包", R.drawable.ic_custom_actionbar_left_black, "说明", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                DialogUtils.dialog(myActivity,getResources().getString(R.string.about_hashrate_title),getResources().getString(R.string.about_hashrate_context),"");
            }
        });
        initView();//初始化页面
        setViewListener();//设置监听事件
    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }

    /**
     * 监听事件
     */
    private void setViewListener() {
    }
}
