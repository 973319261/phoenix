package com.koi.phoenix.ui.user.inviteVolume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvBetAdapter;
import com.koi.phoenix.adapter.RvRedeemCodeAdapter;
import com.koi.phoenix.bean.BetVo;
import com.koi.phoenix.bean.IssueVo;
import com.koi.phoenix.bean.Pagination;
import com.koi.phoenix.bean.VideoRecord;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.dialog.LotteryDialog;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.RecyclerViewSpaces;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.util.Tools;
import com.koi.phoenix.widget.MyActionBar;
import com.robinhood.ticker.TickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 抽奖详情
 */
public class LotteryDetailsActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private TextView tvIssueName;//期号名称
    private LinearLayout llBetLuck;//幸运儿
    private ImageView ivBetLuckAvatar;//幸运儿头像
    private TextView tvBetLuckNickName;//幸运儿昵称
    private TextView tvBetLuckNum;//幸运儿投注数
    private TextView tvBetLuckNumber;//幸运号码
    private TextView tvIssueCountdown;//倒计时
    private ProgressBar pbIssue;//进度条
    private TickerView tvIssueNum;//参与数
    private LinearLayout llIssueUse;//使用邀请卷
    private TextView tvParticipate;//参与记录按钮
    private TextView tvRedeemCode;//我的兑换码按钮
    private SmartRefreshLayout srlBetList;//下注下拉加载，上拉刷新控件
    private RecyclerView rvBetList;//下注列表
    private RvBetAdapter rvBetAdapter;//下注适配器
    private LinearLayout llBetEmpty;//下注数据为空图片
    private LinearLayout llBetFailure;//下注加载失败页面
    private SmartRefreshLayout srlRedeemCodeList;//兑换码下拉加载，上拉刷新控件
    private RecyclerView rvRedeemCodeList;//兑换码列表
    private RvRedeemCodeAdapter rvRedeemCodeAdapter;//兑换码适配器
    private LinearLayout llRedeemCodeEmpty;//兑换码数据为空图片
    private LinearLayout llRedeemCodeFailure;//兑换码加载失败页面
    private MyActionBar myActionBar;//标题栏
    private int pageSize = 10;//分页大小
    private int currentPage = 1;//当前页数
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    private IssueVo issueVoInfo;//期号信息
    private CountDownTimer timer;//倒计时
    private int userId;//用户ID
    private IssueVo issueVo;//期号信息
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_lottery_details);
        tvIssueName=findViewById(R.id.tv_issue_name);
        llBetLuck=findViewById(R.id.ll_bet_luck);
        ivBetLuckAvatar=findViewById(R.id.iv_bet_luck_avatar);
        tvBetLuckNickName=findViewById(R.id.tv_bet_luck_nickName);
        tvBetLuckNum=findViewById(R.id.tv_bet_luck_num);
        tvBetLuckNumber=findViewById(R.id.tv_bet_luck_number);
        tvIssueCountdown=findViewById(R.id.tv_issue_countdown);
        pbIssue=findViewById(R.id.pb_issue);
        tvIssueNum=findViewById(R.id.tv_issue_num);
        llIssueUse=findViewById(R.id.ll_issue_use);
        tvParticipate=findViewById(R.id.tv_participate);
        tvRedeemCode=findViewById(R.id.tv_redeemCode);
        srlBetList = findViewById(R.id.srl_bet_list);
        rvBetList = findViewById(R.id.rv_bet_list);
        llBetFailure=findViewById(R.id.ll_bet_failure);
        llBetEmpty = findViewById(R.id.ll_bet_empty);
        srlRedeemCodeList=findViewById(R.id.srl_redeemCode_list);
        rvRedeemCodeList = findViewById(R.id.rv_redeemCode_list);
        llRedeemCodeFailure=findViewById(R.id.ll_redeemCode_failure);
        llRedeemCodeEmpty = findViewById(R.id.ll_redeemCode_empty);
        myActionBar = findViewById(R.id.myActionBar);
        AnimationUtils.tickerRollView(new TickerView[]{tvIssueNum});//设置滚动数字
        myActionBar.setData("抽奖详情", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
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
        //获取参数
        issueVoInfo = (IssueVo) getIntent().getSerializableExtra("issueVo");
        userId = (int) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        //============下注RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvBetList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvBetAdapter=new RvBetAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvBetList.setAdapter(rvBetAdapter);
        //==5、初始化加载数据
        loadListData(true);

        //============兑换码RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        GridLayoutManager gridLayoutManager=new GridLayoutManager(myActivity,5);//4列
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvRedeemCodeList.setLayoutManager(gridLayoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvRedeemCodeAdapter=new RvRedeemCodeAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvRedeemCodeList.setAdapter(rvRedeemCodeAdapter);
        HashMap<String, Integer> mapSpaces = new HashMap<>();//间距
        mapSpaces.put(RecyclerViewSpaces.TOP_DECORATION, 20);//上间距
        mapSpaces.put(RecyclerViewSpaces.BOTTOM_DECORATION, 20);//下间距
        mapSpaces.put(RecyclerViewSpaces.LEFT_DECORATION, 20);//左间距
        mapSpaces.put(RecyclerViewSpaces.RIGHT_DECORATION, 20);//右间距
        rvRedeemCodeList.addItemDecoration(new RecyclerViewSpaces(mapSpaces));//设置间距
        //==5、初始化加载数据
        loadListData();
        srlRedeemCodeList.setEnableLoadMore(false);//禁止兑换码加载更多
    }

    /**
     * 设置监听事件
     */
    private void setViewListener() {
        //下注列表下拉刷新
        srlBetList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(true);
            }
        });
        //下注列表上拉加载
        srlBetList.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadListData(false);
            }
        });
        //下注列表加载失败点击
        llBetFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlBetList.autoRefresh();//自动刷新动画
                loadListData(true);//初始化
            }
        });
        //兑换码列表下拉刷新
        srlRedeemCodeList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData();
            }
        });
        //下注列表加载失败点击
        llRedeemCodeFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlRedeemCodeList.autoRefresh();//自动刷新动画
                loadListData();//初始化
            }
        });
        //参与记录
        tvParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlBetList.setVisibility(View.VISIBLE);//显示参与记录
                srlRedeemCodeList.setVisibility(View.GONE);//隐藏我的兑换码
                tvParticipate.setTextColor(getResources().getColor(R.color.colorBlack));
                tvRedeemCode.setTextColor(getResources().getColor(R.color.colorGray));
            }
        });
        //我的兑换码
        tvRedeemCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlBetList.setVisibility(View.GONE);//隐藏参与记录
                srlRedeemCodeList.setVisibility(View.VISIBLE);//显示我的兑换码
                tvRedeemCode.setTextColor(getResources().getColor(R.color.colorBlack));
                tvParticipate.setTextColor(getResources().getColor(R.color.colorGray));
            }
        });
        //使用邀请卷
        llIssueUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId>0){
                    String url= ServiceUrls.getIssueMethodUrl("getDrawCondition");//获取抽奖条件（视频观看5次，拥有邀请卷）
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",userId);
                    final LoadingDialog loadingDialog=new LoadingDialog(myActivity);
                    loadingDialog.show();
                    OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                        @Override
                        public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccess && responseCode==200){
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            int code = jsonObject.getInt("code");
                                            if (code==200){
                                                String data=jsonObject.getString("data");
                                                JSONObject jsonData=new JSONObject(data);
                                                Integer inviteNum = jsonData.getInt("inviteNum");//邀请卷数量
                                                //视频观看记录
                                                VideoRecord  videoRecord = gson.fromJson(jsonData.getString("videoRecord"), VideoRecord.class);
                                                if (inviteNum>0){//邀请卷大于0
                                                    LotteryDialog lotteryDialog=new LotteryDialog(myActivity,inviteNum,videoRecord,issueVo.getIssueId(),userId);//弹窗
                                                    lotteryDialog.show();//显示弹窗
                                                }else {//邀请卷不足
                                                    DialogUtils.dialog(myActivity,"温馨提示","邀请卷不足，快去邀请好友吧","");
                                                }
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        Toast.makeText(myActivity,ServiceUrls.responseText,Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.hide();
                                }
                            });
                        }
                    });
                }
            }
        });
        //幸运儿
        llBetLuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,LuckyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("issueVo",issueVo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 加载下注列表
     */
    private void loadListData(final boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
            srlBetList.autoRefresh();//自动刷新
        } else {
            currentPage++;
        }
        String url = ServiceUrls.getIssueMethodUrl("getBetByIssueId");
        Map<String, Object> map = new HashMap<>();
        map.put("issueId", issueVoInfo.getIssueId());
        map.put("pageSize", pageSize);
        map.put("currentPage", currentPage);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode == 200) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                final String data=jsonObject.getString("data");
                                JSONObject jsonData=new JSONObject(data);
                                String issueVoStr=jsonData.getString("issueVo");//期号信息
                                issueVo=gson.fromJson(issueVoStr,IssueVo.class);//期号信息
                                pbIssue.setMax(issueVo.getAllBetNum());//设置进度条最大值
                                tvIssueName.setText(String.format(Locale.getDefault(),"期号：%s",issueVo.getIssueName().trim()));//设置期号名称
                                if (issueVo.getIssueStateId()==1){//可参与
                                    pbIssue.setProgress(issueVo.getYetBetNum());//已参与数量
                                    tvIssueNum.setText(String.format(Locale.getDefault(),"%d/%d",issueVo.getYetBetNum(),issueVo.getAllBetNum()));//设置参与数
                                    timer=new CountDownTimer(24*60*60*1000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            Date endDate = issueVo.getAbortTime();//截止时间
                                            String str=Tools.getDatePoor(endDate,new Date());//时间差
                                            if (str.equals("00小时00分00秒")){
                                                timer.cancel();//取消倒计时
                                                tvIssueCountdown.setText("正在开奖中...");
                                                pbIssue.setProgress(issueVo.getAllBetNum());//进度条
                                                tvIssueNum.setText(String.format(Locale.getDefault(),"%d/%d",issueVo.getYetBetNum(),issueVo.getYetBetNum()));//设置参与数
                                                llIssueUse.setVisibility(View.GONE);//隐藏使用邀请卷按钮
                                            }else {
                                                tvIssueCountdown.setText(String.format(Locale.getDefault(),"开奖倒计时：%s",Tools.getDatePoor(endDate,new Date())));
                                                llIssueUse.setVisibility(View.VISIBLE);//显示使用邀请卷按钮
                                            }
                                        }

                                        @Override
                                        public void onFinish() {

                                        }
                                    };
                                    timer.start();
                                }else {//已开奖
                                    pbIssue.setProgress(issueVo.getAllBetNum());//进度条
                                    tvIssueNum.setText(String.format(Locale.getDefault(),"%d/%d",issueVo.getYetBetNum(),issueVo.getYetBetNum()));//设置参与数
                                    llIssueUse.setVisibility(View.GONE);//隐藏使用邀请卷按钮
                                    String luckStr=jsonData.getString("luck");//幸运儿信息
                                    if(Tools.isNotNull(luckStr)){//当有幸运儿时
                                        tvIssueCountdown.setVisibility(View.GONE);//隐藏开奖倒计时
                                        llBetLuck.setVisibility(View.VISIBLE);//显示幸运儿
                                        final BetVo luck=gson.fromJson(luckStr,BetVo.class);//幸运儿信息
                                        String strPicture = luck.getAvatarUrl().trim();//获取头像路径
                                        if (Tools.isNotNull(strPicture)) {
                                            String pictureUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + strPicture;
                                            //使用Glide加载图片
                                            Glide.with(myActivity)
                                                    .load(pictureUrl)
                                                    .apply(headerRO.error(R.drawable.ic_avatar_error))
                                                    .into(ivBetLuckAvatar);
                                        }
                                        tvBetLuckNickName.setText(luck.getNickName().trim());//设置幸运儿昵称
                                        tvBetLuckNum.setText(String.format(Locale.getDefault(),"本期参与：%d注",luck.getBetNum()));//设置下注总数量
                                        tvBetLuckNumber.setText(String.format(Locale.getDefault(),"幸运号码：%d",issueVo.getLuckNumber()));//设置幸运号码
                                    }else {
                                        tvIssueCountdown.setVisibility(View.VISIBLE);//显示开奖倒计时
                                        llBetLuck.setVisibility(View.GONE);//隐藏幸运儿
                                        tvIssueCountdown.setText("本期暂无幸运儿");
                                    }


                                }
                                String paginationStr=jsonData.getString("pagination");//投注信息
                                Type type = new TypeToken<Pagination<BetVo>>() {}.getType();
                                Pagination<BetVo> pagination = gson.fromJson(paginationStr, type);
                                if (pagination.getTotalRows() != 0) {
                                    llBetEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                    rvBetList.setVisibility(View.VISIBLE);//显示列表
                                    llBetFailure.setVisibility(View.GONE);//加载失败
                                    srlBetList.setEnableLoadMore(true);//开启上拉加载
                                    List<BetVo> list = pagination.getData();
                                    rvBetAdapter.addItem(list, currentPage);
                                    if (isRefresh) {
                                        srlBetList.finishRefresh();//刷新完成
                                    } else {
                                        srlBetList.finishLoadMore();//加载更多完成
                                    }
                                    int totalPage = pagination.getTotalPage();
                                    if (currentPage == totalPage) {
                                        srlBetList.finishLoadMoreWithNoMoreData();
                                    }
                                } else {
                                    rvBetList.setVisibility(View.GONE);//隐藏列表
                                    llBetFailure.setVisibility(View.GONE);//加载失败
                                    llBetEmpty.setVisibility(View.VISIBLE);//显示图片
                                    srlBetList.setEnableLoadMore(false);//禁止上拉加载
                                    //加载失败
                                    if (isRefresh) {
                                        srlBetList.finishRefresh();//刷新失败
                                    } else {
                                        srlBetList.finishLoadMore();//加载更多失败
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            rvBetList.setVisibility(View.GONE);//隐藏列表
                            llBetFailure.setVisibility(View.VISIBLE);//加载失败
                            llBetEmpty.setVisibility(View.GONE);//显示图片
                            //加载失败
                            if (isRefresh) {
                                srlBetList.finishRefresh(false);//刷新失败
                            } else {
                                srlBetList.finishLoadMore(false);//加载更多失败
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * 加载兑换码数据
     */
    private void loadListData(){
        String url=ServiceUrls.getIssueMethodUrl("getRedeemCode");
        Map<String,Object> map=new HashMap<>();
        map.put("issueId",issueVoInfo.getIssueId());
        map.put("userId",userId);
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    llRedeemCodeEmpty.setVisibility(View.GONE);//有数据时隐藏图片
                                    rvRedeemCodeList.setVisibility(View.VISIBLE);//显示列表
                                    llRedeemCodeFailure.setVisibility(View.GONE);//加载失败
                                    String data=jsonObject.getString("data");
                                    List<String> list=gson.fromJson(data,List.class);
                                    rvRedeemCodeAdapter.addItem(list);
                                    srlRedeemCodeList.finishRefresh();//刷新完成
                                }else {
                                    rvRedeemCodeList.setVisibility(View.GONE);//隐藏列表
                                    llRedeemCodeFailure.setVisibility(View.GONE);//加载失败
                                    llRedeemCodeEmpty.setVisibility(View.VISIBLE);//显示图片
                                    srlRedeemCodeList.finishRefresh();//刷新完成
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            rvRedeemCodeList.setVisibility(View.GONE);//隐藏列表
                            llRedeemCodeFailure.setVisibility(View.VISIBLE);//加载失败
                            llRedeemCodeEmpty.setVisibility(View.GONE);//显示图片
                            srlRedeemCodeList.finishRefresh(false);//刷新失败
                        }
                    }
                });
            }
        });
    }
}
