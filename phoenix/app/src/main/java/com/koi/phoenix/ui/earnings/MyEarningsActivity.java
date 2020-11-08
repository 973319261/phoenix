package com.koi.phoenix.ui.earnings;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvEarningsAdapter;
import com.koi.phoenix.adapter.RvTeamAdapter;
import com.koi.phoenix.bean.IncomeRecord;
import com.koi.phoenix.bean.Pagination;
import com.koi.phoenix.bean.TeamVo;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.leefeng.promptlibrary.PromptDialog;

/**
 * 我的收益
 */
public class MyEarningsActivity extends AppCompatActivity {
    private Activity myActivity;
    //控件
    private SmartRefreshLayout srlEarningsList;//下拉加载，上拉刷新控件
    private RecyclerView rvEarningsList;//列表
    private RvEarningsAdapter rvEarningsAdapter;//适配器
    private TextView tvDate;//时间
    private LinearLayout llDate;//选择时间
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private MyActionBar myActionBar;//标题栏
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat sf1=new SimpleDateFormat("yyyy月M月");
    private Calendar selectedCalendar= Calendar.getInstance();
    private Calendar startCalendar= Calendar.getInstance();//开始时间
    private Calendar endCalendar= Calendar.getInstance();//结束时间
    private Date selectedDate=null;//选择时间
    private String date;//选择时间
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myActivity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings_myearnings);
        tvDate=findViewById(R.id.tv_earnings_date);
        llDate=findViewById(R.id.ll_earnings_date);
        srlEarningsList = findViewById(R.id.srl_earnings_list);
        rvEarningsList = findViewById(R.id.rv_earnings_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_order_list_empty);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("我的收益", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭当前页面
            }
            @Override
            public void onRightClick() {

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
        Date nowDate = new Date();//当前时间
        startCalendar.set(2019,0,1);//设置开始时间
        tvDate.setText(sf1.format(nowDate));
        date=sf.format(nowDate);
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvEarningsList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        List<IncomeRecord> list=new ArrayList<>();
        rvEarningsAdapter=new RvEarningsAdapter(myActivity,list);
        //=2.3、设置recyclerView的适配器
        rvEarningsList.setAdapter(rvEarningsAdapter);
        //==5、初始化加载数据
        loadListData(true);
    }
    /**
     * 监听事件
     */
    private void setViewListener() {
        //选中时间
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    selectedDate = sf1.parse(tvDate.getText().toString());//选择时间
                    selectedCalendar.setTime(selectedDate);//设置时间
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TimePickerView pvTime = new TimePickerView.Builder(myActivity, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date d, View v) {
                        tvDate.setText(sf1.format(d));
                        date=sf.format(d);//选择时间
                        loadListData(true);
                    }
                }).setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setRangDate(startCalendar,endCalendar)
                        .setTitleText("选择时间")//标题文字
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setSubmitColor(getResources().getColor(R.color.colorPrimary))//确定按钮文字颜色
                        .setCancelColor(getResources().getColor(R.color.colorBlack))//取消按钮文字颜色*/
                        .setDate(selectedCalendar)// 如果不设置的话，默认是系统时间*/
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .build();
                pvTime.show();
            }
        });
        //下拉刷新
        srlEarningsList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //上拉加载
        srlEarningsList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlEarningsList.autoRefresh();//自动刷新
                loadListData(true);//初始化
            }
        });
    }
    /**
     * 加载收益列表
     */
    private void loadListData(final boolean isRefresh){
        if (isRefresh){
            currentPage=1;
            srlEarningsList.autoRefresh();//自动刷新
        }else {
            currentPage++;
        }
        String url= ServiceUrls.getIncomeMethodUrl("getIncomeByDate");
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("date",date);
        map.put("pageSize",pageSize);
        map.put("currentPage",currentPage);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            Pagination<IncomeRecord> pagination;
                            Type type=new TypeToken<Pagination<IncomeRecord>>(){}.getType();
                            pagination=gson.fromJson(response,type);
                            if (pagination.getTotalRows()!=0){
                                llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                rvEarningsList.setVisibility(View.VISIBLE);//显示列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                List<IncomeRecord> list=pagination.getData();
                                rvEarningsAdapter.addItem(list,currentPage);
                                if (isRefresh) {
                                    srlEarningsList.finishRefresh();//刷新完成
                                } else {
                                    srlEarningsList.finishLoadMore();//加载更多完成
                                }
                                int totalPage=pagination.getTotalPage();
                                if (currentPage==totalPage){
                                    srlEarningsList.finishLoadMoreWithNoMoreData();
                                }
                            }else {
                                rvEarningsList.setVisibility(View.GONE);//隐藏列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                llEmpty.setVisibility(View.VISIBLE);//显示图片
                                //加载失败
                                if (isRefresh) {
                                    srlEarningsList.finishRefresh();//刷新失败
                                } else {
                                    srlEarningsList.finishLoadMore();//加载更多失败
                                }
                            }
                        }else {
                            rvEarningsList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//显示图片
                            //加载失败
                            if (isRefresh) {
                                srlEarningsList.finishRefresh(false);//刷新失败
                            } else {
                                srlEarningsList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
}
