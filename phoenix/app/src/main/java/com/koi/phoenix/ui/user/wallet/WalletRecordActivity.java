package com.koi.phoenix.ui.user.wallet;

import android.app.Activity;
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
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvEarningsAdapter;
import com.koi.phoenix.adapter.RvWalletRecordAdapter;
import com.koi.phoenix.bean.IncomeRecord;
import com.koi.phoenix.bean.MoneyRecordVo;
import com.koi.phoenix.bean.Pagination;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 零钱明细页面
 */
public class WalletRecordActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private RvWalletRecordAdapter rvWalletAdapter;//适配器
    private SmartRefreshLayout srlWalletList;//刷新框架
    private RecyclerView rvWalletList;//列表
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private Integer userId;//用户id
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myActivity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_record);
        myActionBar = findViewById(R.id.myActionBar);
        srlWalletList=findViewById(R.id.srl_wallet_record_list);
        rvWalletList=findViewById(R.id.rv_wallet_record_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_empty);
        myActionBar.setData("零钱明细", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭当前页面
            }
            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
        setViewListener();//监听事件
    }

    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        srlWalletList.autoRefresh();//自动下拉动画
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取用户ID
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvWalletList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvWalletAdapter=new RvWalletRecordAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvWalletList.setAdapter(rvWalletAdapter);
        //==5、初始化加载数据
        loadListData(true);
    }
    /**
     * 监听事件
     */
    private void setViewListener() {
        //下拉加载
        srlWalletList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //上拉加载
        srlWalletList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlWalletList.autoRefresh();//自动刷新
                loadListData(true);//初始化
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
            srlWalletList.autoRefresh();//自动刷新
        }else {
            currentPage++;
        }
        String url= ServiceUrls.getWalletMethodUrl("getMoneyRecord");
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
                            Pagination<MoneyRecordVo> pagination;
                            Type type=new TypeToken<Pagination<MoneyRecordVo>>(){}.getType();
                            pagination=gson.fromJson(response,type);
                            if (pagination.getTotalRows()!=0) {
                                llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                rvWalletList.setVisibility(View.VISIBLE);//显示列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                List<MoneyRecordVo> list = pagination.getData();//获取数据
                                rvWalletAdapter.addItem(list, currentPage);
                                if (isRefresh) {
                                    srlWalletList.finishRefresh();//刷新完成
                                } else {
                                    srlWalletList.finishLoadMore();//加载更多完成
                                }
                                int totalPage = pagination.getTotalPage();
                                if (currentPage == totalPage) {//最后一条
                                    srlWalletList.finishLoadMoreWithNoMoreData();
                                }
                            }else {
                                rvWalletList.setVisibility(View.GONE);//隐藏列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                llEmpty.setVisibility(View.VISIBLE);//显示图片
                                //加载失败
                                if (isRefresh) {
                                    srlWalletList.finishRefresh();//刷新失败
                                } else {
                                    srlWalletList.finishLoadMore();//加载更多失败
                                }
                            }
                        }else {
                            rvWalletList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//隐藏图片
                            //加载失败
                            if (isRefresh) {
                                srlWalletList.finishRefresh(false);//刷新失败
                            } else {
                                srlWalletList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
}
