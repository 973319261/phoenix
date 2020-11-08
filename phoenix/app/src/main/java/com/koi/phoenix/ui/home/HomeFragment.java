package com.koi.phoenix.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koi.phoenix.R;
import com.koi.phoenix.util.JavaScriptObject;

/**
 * 游戏页面
 */
public class HomeFragment extends Fragment {
    private WebView webView;//网页容器
    private Activity myActivity;
    private String loadUrl="http://10.0.2.2:7456/build";//网页地址

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }

    @Nullable
    @Override
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        webView = view.findViewById(R.id.webView);
        initView();//初始化页面
        setViewListener();//事件监听
        return view;
    }


    /**
     * 初始化页面
     */
    private void initView() {

    }

    /**
     * 事件监听
     */
    private void setViewListener() {
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);//开启编译JavaScript脚本，否则加载不了网页脚本
        //设置本地JS调用对象及其接口
        webView.addJavascriptInterface(new JavaScriptObject(myActivity), "MyJSInterface");//设置JS接口调用Android方法
        webView.loadUrl(loadUrl);//网页加载

    }


}
