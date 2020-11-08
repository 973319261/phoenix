package com.koi.phoenix.ui.user.inviteVolume;

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
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvParticipateAdapter;
import com.koi.phoenix.bean.IssueVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽奖记录页面
 */
public class LotteryRecordActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private SmartRefreshLayout srlParticipateList;//下拉加载，上拉刷新控件
    private RecyclerView rvParticipateList;//列表
    private RvParticipateAdapter rvParticipateAdapter;//适配器
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private MyActionBar myActionBar;//标题栏
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private int userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_lottery_record);
        srlParticipateList = findViewById(R.id.srl_participate_list);
        rvParticipateList = findViewById(R.id.rv_participate_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_empty);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("参与记录", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
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
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvParticipateList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvParticipateAdapter=new RvParticipateAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvParticipateList.setAdapter(rvParticipateAdapter);
        //==5、初始化加载数据
        loadListData(true);

    }

    /**
     * 设置监听事件
     */
    private void setViewListener() {
        //下拉刷新
        srlParticipateList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //上拉加载
        srlParticipateList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlParticipateList.autoRefresh();//自动刷新
                loadListData(true);//初始化
            }
        });
        //item点击事件
        rvParticipateAdapter.setOnItemClickListener(new RvParticipateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IssueVo issueVo) {
                Intent intent=new Intent(myActivity,LotteryDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("issueVo",issueVo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 加载收益列表
     */
    private void loadListData(final boolean isRefresh){
        if (isRefresh){
            currentPage=1;
            srlParticipateList.autoRefresh();//自动刷新
        }else {
            currentPage++;
        }
        String url= ServiceUrls.getIssueMethodUrl("getParticipateRecord");
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("pageSize",pageSize);
        map.put("currentPage",currentPage);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            Pagination<IssueVo> pagination;
                            Type type=new TypeToken<Pagination<IssueVo>>(){}.getType();
                            pagination=gson.fromJson(response,type);
                            if (pagination.getTotalRows()!=0){
                                llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                rvParticipateList.setVisibility(View.VISIBLE);//显示列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                List<IssueVo> list=pagination.getData();
                                rvParticipateAdapter.addItem(list,currentPage);
                                if (isRefresh) {
                                    srlParticipateList.finishRefresh();//刷新完成
                                } else {
                                    srlParticipateList.finishLoadMore();//加载更多完成
                                }
                                int totalPage=pagination.getTotalPage();
                                if (currentPage==totalPage){
                                    srlParticipateList.finishLoadMoreWithNoMoreData();
                                }
                            }else {
                                rvParticipateList.setVisibility(View.GONE);//隐藏列表
                                llFailure.setVisibility(View.GONE);//加载失败
                                llEmpty.setVisibility(View.VISIBLE);//显示图片
                                //加载失败
                                if (isRefresh) {
                                    srlParticipateList.finishRefresh();//刷新失败
                                } else {
                                    srlParticipateList.finishLoadMore();//加载更多失败
                                }
                            }
                        }else {
                            rvParticipateList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//显示图片
                            //加载失败
                            if (isRefresh) {
                                srlParticipateList.finishRefresh(false);//刷新失败
                            } else {
                                srlParticipateList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
}
