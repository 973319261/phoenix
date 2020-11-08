package com.koi.phoenix;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;


import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.List;

public class MyApplication extends Application {
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    //在app启动时执行
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //==========设置全局的参数
    private List<Bitmap> listCardPicture;
    public void setListCardPicture(List<Bitmap> listCardPicture){
        this.listCardPicture=listCardPicture;
    }
    public List<Bitmap> getListCardPicture(){
        return listCardPicture;
    }

    private Activity activity;//页面
    public void setActivity(Activity activity){
        this.activity=activity;
    }
    public void closeActivity(){
        if ( this.activity!=null){
            this.activity.finish();
        }
    }
    private Handler handler;
    private Runnable runnable;

    /**
     * 设置在线分钟统计任务
     * @param handler
     * @param runnable
     */
    public void setOnLineTask(Handler handler,Runnable runnable){
        this.handler=handler;
        this.runnable=runnable;
    }

    /**
     * 结束在线分钟统计任务
     */
    public void closeOnLineTask(){
        handler.removeCallbacks(runnable);//结束任务
    }
}
