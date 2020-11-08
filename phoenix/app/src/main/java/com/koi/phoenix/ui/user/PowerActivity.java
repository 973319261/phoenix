package com.koi.phoenix.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvPowerAdapter;
import com.koi.phoenix.bean.Pagination;
import com.koi.phoenix.bean.PowerRecordVo;
import com.koi.phoenix.ui.MainActivity;
import com.koi.phoenix.ui.work.WorkFragment;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;
import com.koi.phoenix.widget.RunningTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的算力页面
 */
public class PowerActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private MyActionBar myActionBar;//标题栏
    private RvPowerAdapter rvPowerAdapter;//适配器
    private SmartRefreshLayout srlPowerList;//刷新框架
    private RecyclerView rvPowerList;//列表
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private RunningTextView rtvPowerNum;//总数
    private LinearLayout llPowerAddNum;//增加数量
    private Integer userId;//用户id
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_power);
        myActionBar = findViewById(R.id.myActionBar);
        srlPowerList=findViewById(R.id.srl_power_list);
        rvPowerList=findViewById(R.id.rv_power_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_empty);
        rtvPowerNum=findViewById(R.id.rtv_power_num);
        llPowerAddNum=findViewById(R.id.ll_power_addNum);
        myActionBar.setData("我的算力", R.drawable.ic_custom_actionbar_left_black, "说明", 1, 0, new MyActionBar.ActionBarClickListener() {
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
        srlPowerList.autoRefresh();//自动下拉动画
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取用户ID
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvPowerList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvPowerAdapter=new RvPowerAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvPowerList.setAdapter(rvPowerAdapter);
        //==5、初始化加载数据
        loadListData(true);
    }
    /**
     * 设置监听事件
     */
    private void setViewListener() {
        //下拉加载
        srlPowerList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //上拉加载
        srlPowerList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlPowerList.autoRefresh();//自动刷新
                loadListData(true);//初始化
            }
        });
        //增加数量
        llPowerAddNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, MainActivity.class);
                intent.putExtra("isWorkFragment",true);//跳转到WorkFragment页面
                startActivity(intent);
                myApplication.closeActivity();//关闭主页面
                finish();
            }
        });
    }
    /**
     * 加载数据
     * @param isRefresh
     */
    private void loadListData(final boolean isRefresh) {
        if (isRefresh){
            currentPage=1;
            srlPowerList.autoRefresh();//自动刷新
        }else {
            currentPage++;
        }
        String url= ServiceUrls.getUserMethodUrl("getPowerRecord");
        Map<String,Object> map=new HashMap<>();
        map.put("userId", userId);
        map.put("pageSize",pageSize);
        map.put("currentPage",currentPage);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text=ServiceUrls.responseText;
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String data = jsonObject.getString("data");
                                JSONObject jsonData=new JSONObject(data);
                                rtvPowerNum.setValue(jsonData.getString("powerNum"),"0");//设置算力总数
                                Pagination<PowerRecordVo> pagination;
                                Type type=new TypeToken<Pagination<PowerRecordVo>>(){}.getType();
                                pagination=gson.fromJson(jsonData.getString("pagination"),type);
                                if (pagination.getTotalRows()!=0) {
                                    llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                    rvPowerList.setVisibility(View.VISIBLE);//显示列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    List<PowerRecordVo> list = pagination.getData();//获取数据
                                    rvPowerAdapter.addItem(list, currentPage);
                                    if (isRefresh) {
                                        srlPowerList.finishRefresh();//刷新完成
                                    } else {
                                        srlPowerList.finishLoadMore();//加载更多完成
                                    }
                                    int totalPage = pagination.getTotalPage();
                                    if (currentPage == totalPage) {//最后一条
                                        srlPowerList.finishLoadMoreWithNoMoreData();
                                    }
                                }else {
                                    rvPowerList.setVisibility(View.GONE);//隐藏列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    llEmpty.setVisibility(View.VISIBLE);//显示图片
                                    //加载失败
                                    if (isRefresh) {
                                        srlPowerList.finishRefresh();//刷新失败
                                    } else {
                                        srlPowerList.finishLoadMore();//加载更多失败
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            rvPowerList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//隐藏图片
                            //加载失败
                            if (isRefresh) {
                                srlPowerList.finishRefresh(false);//刷新失败
                            } else {
                                srlPowerList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
}
