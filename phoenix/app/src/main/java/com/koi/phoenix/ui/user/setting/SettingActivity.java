package com.koi.phoenix.ui.user.setting;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.dialog.MessageDialog;
import com.koi.phoenix.ui.LoginActivity;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

/**
 * 设置页面
 */
public class SettingActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private LinearLayout llService;//服务协议
    private LinearLayout llPrivacy;//隐私协议
    private LinearLayout llAbout;//关于
    private Button btnLogout; //退出

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_user_setting);
        llService = findViewById(R.id.ll_user_setting_service);//服务协议
        llPrivacy = findViewById(R.id.ll_user_setting_privacy);//隐私协议
        llAbout = findViewById(R.id.ll_user_setting_about);//关于
        btnLogout = findViewById(R.id.btn_setting_logout); //退出
        initView();//初始化页面
        setContextListener();//设置监听事件
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("设置", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
    }
    /**
     * 设置监听事件
     */
    private void setContextListener() {
        //服务协议
        llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,ServiceProtocolActivity.class);
                startActivity(intent);
            }
        });
        //隐私协议
        llPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,PrivacyProtocolActivity.class);
                startActivity(intent);
            }
        });
        //关于
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myActivity,SettingAboutActivity.class);
                startActivity(intent);
            }
        });
        //退出登录按钮
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, "确定退出登录吗?", new MessageDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm==true){//确定按钮
                            Intent intent=new Intent(myActivity, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            SPUtils.clear(myActivity);//清空用户信息
                            myApplication.closeActivity();//销毁主页activity
                            myApplication.closeOnLineTask();//结束在线统计任务
                            Toast.makeText(myActivity,"退出成功",Toast.LENGTH_LONG).show();

                        }
                        dialog.dismiss();//关闭弹出框
                    }
                });
                messageDialog.setTitle("退出登录");
                messageDialog.show();//显示弹出框
            }
        });
    }
}
