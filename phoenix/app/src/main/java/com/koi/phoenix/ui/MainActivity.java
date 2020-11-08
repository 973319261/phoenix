package com.koi.phoenix.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.TaskVo;
import com.koi.phoenix.ui.earnings.EarningsFragment;
import com.koi.phoenix.ui.home.HomeFragment;
import com.koi.phoenix.ui.user.UserFragment;
import com.koi.phoenix.ui.work.WorkFragment;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * 主页页面
 */
public class MainActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;
    Fragment[] fragments = new Fragment[]{null, null, null,null};//存放Fragment
    //控件
    private LinearLayout llContent; //内容容器
    private RadioButton rbHome;//rb 主页
    private RadioButton rbEarnings;//收益
    private RadioButton rbWork;//工作
    private RadioButton rbUser;//用户
    private int lastfragment=0;//记录隐藏fragment的索引
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Integer userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;//设置上下文
        myApplication= (MyApplication) myActivity.getApplication();
        myApplication.setActivity(myActivity);//保存主页Activity
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        if (userId>0){//如果存在，直接显示主页面
            setContentView(R.layout.activity_main);
            //获取控件
            llContent = findViewById(R.id.ll_main_content);
            rbHome = findViewById(R.id.rb_main_home);
            rbEarnings = findViewById(R.id.rb_main_earnings);
            rbWork = findViewById(R.id.rb_main_work);
            rbUser = findViewById(R.id.rb_main_user);
            initView();//初始化页面
            setViewListener();//设置监听事件
        }else {//不存在，则返回到登录界面
            Intent intent=new Intent(myActivity, LoginActivity.class);//登录界面
            startActivity(intent);
            finish();
        }
    }

    /*页面初始化*/
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        //获取在线分钟
        //设置导航栏图标样式
        Drawable iconHome=getResources().getDrawable(R.drawable.selector_main_rb_home);//设置主页图标样式
        iconHome.setBounds(0,0,60,60);//设置图标边距 大小
        rbHome.setCompoundDrawables(null,iconHome,null,null);//设置图标位置
        rbHome.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconEarnings=getResources().getDrawable(R.drawable.selector_main_rb_earnings);//设置主页图标样式
        iconEarnings.setBounds(0,0,60,60);//设置图标边距 大小
        rbEarnings.setCompoundDrawables(null,iconEarnings,null,null);//设置图标位置
        rbEarnings.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconWork=getResources().getDrawable(R.drawable.selector_main_rb_work);//设置主页图标样式
        iconWork.setBounds(0,0,60,60);//设置图标边距 大小
        rbWork.setCompoundDrawables(null,iconWork,null,null);//设置图标位置
        rbWork.setCompoundDrawablePadding(5);//设置文字与图片的间距
        Drawable iconUser=getResources().getDrawable(R.drawable.selector_main_rb_user);//设置主页图标样式
        iconUser.setBounds(0,0,60,60);//设置图标边距 大小
        rbUser.setCompoundDrawables(null,iconUser,null,null);//设置图标位置

        rbUser.setCompoundDrawablePadding(5);//设置文字与图片的间距
        handler.postDelayed(task,60*1000);//设置延迟时间，此处是1分钟
        myApplication.setOnLineTask(handler,task);//设置统计在线分钟任务到内存
        Boolean isWorkFragment=myActivity.getIntent().getBooleanExtra("isWorkFragment",false);//判断是否要跳转到WorkFragment页面
        if (isWorkFragment){
            rbWork.setChecked(true);
            switchFragment(2);
        }else {
            rbHome.setChecked(true);//默认选中主页
            switchFragment(0);//默认显示HomeFragment
        }
    }
    /*监听事件*/
    private void setViewListener() {
        //导航栏点击事件
        //HomeFragment
        rbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(0);
            }
        });
        //EarningsFragment
        rbEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(1);
            }
        });
        //WorkFragment
        rbWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(2);
            }
        });
        //UserFragment
        rbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(3);
            }
        });
    }
    /**
     * 方法 - 切换Fragment
     *
     * @param fragmentIndex 要显示Fragment的索引
     */
    private void switchFragment(int fragmentIndex) {
        //在Activity中显示Fragment
        //1、获取Fragment管理器 FragmentManager
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //2、开启fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //懒加载 - 如果需要显示的Fragment为null，就new。并添加到Fragment事务中
        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0://Home
                    fragments[fragmentIndex] = new HomeFragment();
                    break;
                case 1://Earnings
                    fragments[fragmentIndex] = new EarningsFragment();
                    break;
                case 2://Work
                    fragments[fragmentIndex] = new WorkFragment();
                    break;
                case 3://User
                    fragments[fragmentIndex] = new UserFragment();
                    break;
            }
            //==添加Fragment对象到Fragment事务中
            //参数：显示Fragment的容器的ID，Fragment对象
            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);
        }

        //隐藏其他的Fragment
        for (int i = 0; i < fragments.length; i++) {
            if (fragmentIndex != i && fragments[i] != null) {
                //隐藏指定的Fragment
                transaction.hide(fragments[i]);
            }
        }
        //4、显示Fragment
        transaction.show(fragments[fragmentIndex]);

        //5、提交事务
        transaction.commit();
    }
    /**
     * 记录在线分钟
     */
    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {
            //需要执行的代码
            //要做的事情
            String url=ServiceUrls.getTaskMethodUrl("updateTaskDetailProgress");
            Map<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            map.put("taskId",3);//在线任务
            map.put("sourceTypeId",3);//签到获取
            OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSuccess && responseCode==200){
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String data=jsonObject.getString("data");
                                    TaskVo taskVo=gson.fromJson(data,TaskVo.class);
                                    if (taskVo.getHasDo()==1){//完成任务
                                        handler.removeCallbacks(task);//结束任务
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
            });
            handler.postDelayed(this,60*1000);//设置延迟时间，此处是1分钟
        }
    };

    private boolean isExit;

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                this.finish();

            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit= false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
