package com.koi.phoenix.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.util.ImageUtil;
import com.koi.phoenix.util.MPermissionUtils;
import com.koi.phoenix.util.PlatformUtil;
import com.koi.phoenix.util.StatusBarUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private LinearLayout ll_login;//登录按钮
    // 微信登录
    private static IWXAPI api;
    private String WX_APP_ID = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;//设置上下文
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_login);
        ll_login=findViewById(R.id.ll_login);
        initView();//初始化页面
        setViewListener();//设置监听事件
    }
    /*页面初始化*/
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //检查权限
        final String[] strPermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE   //外部存储器写权限
        };
        if (!MPermissionUtils.checkPermissions(myActivity, strPermissions)) {//检查是否有权限  //没有权限
            //申请权限
            MPermissionUtils.requestPermissionsResult(myActivity,200, strPermissions,
            new MPermissionUtils.OnPermissionListener() {
                //权限通过的回调
                @Override
                public void onPermissionGranted() {
                }
                //权限不通过的回调
                @Override
                public void onPermissionDenied() {
                    //判断是否有永久拒绝的权限
                    if (MPermissionUtils.hasAlwaysDeniedPermission(myActivity, strPermissions)) {
                        MPermissionUtils.showTipsDialog(myActivity);
                    } else {
                        Toast.makeText(myActivity, "您拒绝了应用需要的权限，部分功能将无法使用"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /*监听事件*/
    private void setViewListener() {
        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*WXLogin();*/
                Intent intent=new Intent(myActivity,RegisterActivity.class);//注册页面
                startActivity(intent);
                myApplication.setActivity(myActivity);//保存登录页面
            }
        });
    }
    /**
     * 登录微信
     */
    private void WXLogin() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(WX_APP_ID);
        if (!api.isWXAppInstalled()) {
            Toast.makeText(myActivity, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }
    }

    /**
     * 申请权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
