package com.koi.phoenix.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.User;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.util.KeyBoardUtil;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.util.Tools;
import com.koi.phoenix.widget.MyActionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private EditText etPhone;//手机号
    private EditText etValidationCode;//验证码
    private EditText etInviteCode;//邀请码
    private TextView tvGetCode;//验证码文本
    private LinearLayout llGetCode;//获取验证码
    private LinearLayout llRgister;//注册
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private LoadingDialog loadingDialog;//加载中动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_register);
        etPhone = findViewById(R.id.et_register_phone);
        etValidationCode = findViewById(R.id.et_register_validationCode);
        etInviteCode = findViewById(R.id.et_register_inviteCode);
        tvGetCode = findViewById(R.id.tv_register_getCode);
        llGetCode = findViewById(R.id.ll_register_getCode);
        llRgister = findViewById(R.id.ll_register);
        loadingDialog = new LoadingDialog(myActivity, "正在加载中...");
        initView();
        setViewListener();
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("注册", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
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
    /*监听事件*/
    private void setViewListener() {
        //获取验证码
        llGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = Tools.toString(etPhone.getText().toString());//手机号
                if (!Tools.isMobile(phone)){
                    Toast toast= Toast.makeText(myActivity,"请输入正确的手机号",Toast.LENGTH_LONG);
                    toast.setGravity(0,0,Gravity.CENTER);
                    toast.show();
                    return;
                }
                String url= ServiceUrls.getUserMethodUrl("getValidationCode");//url
                Map<String,Object> map=new HashMap<>();
                map.put("phone",phone);
                KeyBoardUtil.hideKeyboard(v);//隐藏虚拟键盘
                loadingDialog.show();
                OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String text="无法连接到网络，请稍后再试";
                                if (isSuccess && responseCode==200){
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        int code=jsonObject.getInt("code");
                                        text=jsonObject.getString("text");
                                        if (code == 200) {
                                            //发送成功
                                            Toast.makeText(myActivity, text, Toast.LENGTH_LONG).show();
                                            CountDownTimer timer = new CountDownTimer(60*1000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    llGetCode.setEnabled(false);//禁止按钮
                                                    tvGetCode.setText(String.format(Locale.getDefault(),"%ds秒后重试",(millisUntilFinished/1000)));
                                                }

                                                @Override
                                                public void onFinish() {
                                                    llGetCode.setEnabled(true);//开启按钮
                                                    tvGetCode.setText("重新获取");
                                                }
                                            };
                                            //启动定时器
                                            timer.start();
                                        } else {
                                            Toast.makeText(myActivity, text, Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                }
                                loadingDialog.hide();
                            }
                        });
                    }
                });
            }
        });
        //注册
        llRgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = Tools.toString(etPhone.getText().toString());//手机号
                String validationCode=Tools.toString(etValidationCode.getText().toString());//验证码
                String inviteCode=Tools.toString(etInviteCode.getText().toString());//邀请码
                if(!Tools.isMobile(phone)){
                    Toast toast= Toast.makeText(myActivity,"请输入正确的手机号",Toast.LENGTH_LONG);
                    toast.setGravity(0,0,Gravity.CENTER);
                    toast.show();
                    return;
                }
                if(!Tools.isNotNull(validationCode)){
                    Toast toast= Toast.makeText(myActivity,"请输入正确的验证码",Toast.LENGTH_LONG);
                    toast.setGravity(0,0,Gravity.CENTER);
                    toast.show();
                    return;
                }
                String url=ServiceUrls.getUserMethodUrl("register");
                Map<String,Object> map=new HashMap<>();
                map.put("phone",phone);
                map.put("validationCode",validationCode);
                map.put("inviteCode",inviteCode);
                KeyBoardUtil.hideKeyboard(v);//隐藏虚拟键盘
                loadingDialog.show();
                OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                    @Override
                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String text="无法连接到网络，请稍后再试";
                                if (isSuccess && responseCode==200){
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        int code=jsonObject.getInt("code");//状态码
                                        text=jsonObject.getString("text");//提示
                                        if (code==200){
                                            String data=jsonObject.getString("data");
                                            if (data!=null){
                                                User user=gson.fromJson(data,User.class);//获取本地存储的用户信息
                                                SPUtils.put(myActivity, SPUtils.SP_USER_ID, user.getUserId());//保存用户ID
                                                SPUtils.put(myActivity, SPUtils.SP_USER_INVITE_CODE, user.getInviteCode().trim());//保存用户邀请码
                                                Intent intent=new Intent(myActivity,MainActivity.class);
                                                startActivity(intent);
                                                finish();//关闭当前页面
                                                myApplication.closeActivity();//关闭登录页面
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }{
                                    Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                }
                                loadingDialog.hide();
                            }
                        });
                    }
                });
            }
        });
    }
}
