package com.koi.phoenix.ui.user.inviteVolume;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvIssueAdapter;
import com.koi.phoenix.bean.IssueVo;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;
import com.robinhood.ticker.TickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 邀请卷-抽奖页面
 */
public class InviteVolumeActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private TickerView tvIssueTitle;//标题
    private RvIssueAdapter rvIssueAdapter;//适配器
    private SmartRefreshLayout srlIssueList;//刷新框架
    private RecyclerView rvIssueList;//列表
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_invite_volume);
        myActionBar = findViewById(R.id.myActionBar);
        tvIssueTitle=findViewById(R.id.tv_issue_title);
        srlIssueList=findViewById(R.id.srl_issue_list);
        rvIssueList=findViewById(R.id.rv_issue_list);
        llFailure=findViewById(R.id.ll_failure);
        llEmpty = findViewById(R.id.ll_empty);
        TickerView[] tickerViews=new TickerView[]{tvIssueTitle};
        AnimationUtils.tickerRollView(tickerViews);//设置滚动数字
        myActionBar.setData("邀请卷-抽奖", R.drawable.ic_custom_actionbar_left_black, "我的记录", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Intent intent=new Intent(myActivity,LotteryRecordActivity.class);
                startActivity(intent);
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
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvIssueList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvIssueAdapter=new RvIssueAdapter(myActivity,tvIssueTitle);
        //=2.3、设置recyclerView的适配器
        rvIssueList.setAdapter(rvIssueAdapter);
        srlIssueList.autoRefresh();//自动刷新动画
        srlIssueList.setEnableLoadMore(false);//关闭加载更多
        //==5、初始化加载数据
        loadListData();
    }

    /**
     * 设置监听事件
     */
    private void setViewListener() {
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlIssueList.autoRefresh();//自动刷新
                loadListData();//初始化
            }
        });
        //下拉刷新
        srlIssueList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData();
            }
        });
        //期号item
        rvIssueAdapter.setOnItemClickListener(new RvIssueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IssueVo data) {
                Intent intent=new Intent(myActivity,LotteryDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("issueVo",data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    /**
     * 加载数据
     */
    private void loadListData() {
        String url = ServiceUrls.getIssueMethodUrl("getIssueByDay");
        Map<String, Object> map = new HashMap<>();
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    String data = jsonObject.getString("data");
                                    Type type = new TypeToken<List<IssueVo>>() {}.getType();
                                    List<IssueVo> list = gson.fromJson(data, type);
                                    tvIssueTitle.setVisibility(View.VISIBLE);//显示标题
                                    llEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                    rvIssueList.setVisibility(View.VISIBLE);//显示列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    rvIssueAdapter.addItem(list);
                                    srlIssueList.finishRefresh();//刷新完成
                                }else {
                                    tvIssueTitle.setVisibility(View.GONE);//隐藏标题
                                    rvIssueList.setVisibility(View.GONE);//隐藏列表
                                    llFailure.setVisibility(View.GONE);//加载失败
                                    llEmpty.setVisibility(View.VISIBLE);//显示图片
                                    srlIssueList.finishRefresh();//刷新完成
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                srlIssueList.finishRefresh(false);//刷新失败
                            }
                        } else {
                            tvIssueTitle.setVisibility(View.GONE);//隐藏标题
                            rvIssueList.setVisibility(View.GONE);//隐藏列表
                            llFailure.setVisibility(View.VISIBLE);//加载失败
                            llEmpty.setVisibility(View.GONE);//隐藏图片
                            srlIssueList.finishRefresh(false);//刷新失败
                        }
                    }
                });
            }
        });
    }
}
