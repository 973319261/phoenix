package com.koi.phoenix.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvDhcAdapter;
import com.koi.phoenix.bean.Pagination;
import com.koi.phoenix.bean.DhcRecordVo;
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
 * 我的DHC页面
 */
public class HdcActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private RvDhcAdapter rvDhcAdapter;//适配器
    private SmartRefreshLayout srlDhcList;//刷新框架
    private RecyclerView rvDhcList;//列表
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private RunningTextView rtvDhcNum;//Dhc总数
    private LinearLayout llDhcUse;//使用
    private Integer userId;//用户id
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_hdc);
        myActionBar = findViewById(R.id.myActionBar);
        srlDhcList=findViewById(R.id.srl_dhc_list);
        rvDhcList=findViewById(R.id.rv_dhc_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_empty);
        rtvDhcNum=findViewById(R.id.rtv_dhc_num);
        llDhcUse=findViewById(R.id.ll_dhc_use);
        myActionBar.setData("我的DHC", R.drawable.ic_custom_actionbar_left_black, "说明", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                DialogUtils.dialog(myActivity,getResources().getString(R.string.about_DHC_title),getResources().getString(R.string.about_DHC_context),"");
            }
        });
        initView();//初始化页面
        setContextListener();//设置监听事件
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        srlDhcList.autoRefresh();//自动下拉动画
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取用户ID
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvDhcList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvDhcAdapter=new RvDhcAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvDhcList.setAdapter(rvDhcAdapter);
        //==5、初始化加载数据
        loadListData(true);
    }
    /**
     * 设置监听事件
     */
    private void setContextListener() {
        //下拉加载
        srlDhcList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //上拉加载
        srlDhcList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlDhcList.autoRefresh();//自动刷新
                loadListData(true);//初始化
            }
        });
        //使用
        llDhcUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myActivity,"该功能未开放，敬请期待",Toast.LENGTH_LONG).show();
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
            srlDhcList.autoRefresh();//自动刷新
        }else {
            currentPage++;
        }
        String url= ServiceUrls.getUserMethodUrl("getDhcRecord");
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
                                rtvDhcNum.setValue(jsonData.getString("dhcNum"),"0.00");//设置算力总数
                                Pagination<DhcRecordVo> pagination;
                                Type type=new TypeToken<Pagination<DhcRecordVo>>(){}.getType();
                                pagination=gson.fromJson(jsonData.getString("pagination"),type);
                                if (pagination.getTotalRows()!=0) {
                                    llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                    rvDhcList.setVisibility(View.VISIBLE);//显示列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    List<DhcRecordVo> list = pagination.getData();//获取数据
                                    rvDhcAdapter.addItem(list, currentPage);
                                    if (isRefresh) {
                                        srlDhcList.finishRefresh();//刷新完成
                                    } else {
                                        srlDhcList.finishLoadMore();//加载更多完成
                                    }
                                    int totalPage = pagination.getTotalPage();
                                    if (currentPage == totalPage) {//最后一条
                                        srlDhcList.finishLoadMoreWithNoMoreData();
                                    }
                                }else {
                                    rvDhcList.setVisibility(View.GONE);//隐藏列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    llEmpty.setVisibility(View.VISIBLE);//显示图片
                                    //加载失败
                                    if (isRefresh) {
                                        srlDhcList.finishRefresh();//刷新失败
                                    } else {
                                        srlDhcList.finishLoadMore();//加载更多失败
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            rvDhcList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//隐藏图片
                            //加载失败
                            if (isRefresh) {
                                srlDhcList.finishRefresh(false);//刷新失败
                            } else {
                                srlDhcList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }
}
