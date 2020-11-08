package com.koi.phoenix.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.koi.phoenix.bean.User;
import com.koi.phoenix.ui.home.EventActivity;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部JS调用Java类方法
 */
public class JavaScriptObject {
    Activity myActivity;

    public JavaScriptObject(Activity myActivity) {
        this.myActivity = myActivity;
    }


    /**
     * 外部JS跳转Activity方法
     * @param activity 名称
     */
    @JavascriptInterface
    public void toActivity(String activity) {
        if (activity.equals("EventActivity")){//活动消息
            Intent intent=new Intent(myActivity, EventActivity.class);
            myActivity.startActivity(intent);
        }
    }

    /**
     * 显示提示
     * @param str
     */
    @JavascriptInterface
    public void showHint(String str) {
       Toast.makeText(myActivity,str,Toast.LENGTH_SHORT).show();
    }
    /**
     * js获取用户ID
     * @return
     */
    @JavascriptInterface
    public int getUserId() {
        int userId= (int) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        return userId;
    }
}  