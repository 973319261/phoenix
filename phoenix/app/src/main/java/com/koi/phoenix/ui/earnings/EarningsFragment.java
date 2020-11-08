package com.koi.phoenix.ui.earnings;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.IncomeRecord;
import com.koi.phoenix.bean.IncomeStage;
import com.koi.phoenix.bean.Progress;
import com.koi.phoenix.dialog.MessageDialog;
import com.koi.phoenix.ui.user.invite.InviteCardActivity;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.ImageUtil;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.robinhood.ticker.TickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 收益页面
 */
public class EarningsFragment extends Fragment {
    private Activity myActivity;//上下文
    private TextView tvName;//阶段名称
    private TickerView tvMoney;//阶段金额
    private TickerView tvIncome;//收益进度数
    private TickerView tvIncomeNum;//收益加速数
    private TickerView tvFenHong;//分红进度数
    private TickerView tvAll;//累计总收益
    private TickerView tvTuDi;//徒弟总收益
    private TickerView tvTuSun;//徒孙总收益
    private TickerView tvAllToday;//今天总收益
    private TickerView tvTuDiToday;//今天徒弟收益
    private TickerView tvTuSunToday;//今天徒孙收益
    private TickerView tvAllYesterday;//昨天总收益
    private TickerView tvTuDiYesterday;//昨天徒弟收益
    private TickerView tvTuSunYesterday;//昨天徒孙收益
    private TickerView tvTodayAll;//今天总收益提示提示
    private TickerView tvYesterdayAll;//昨天总收益
    private TickerView tvProgressTuDi;//徒弟助力进度
    private TickerView tvProgressTuSun;//徒孙助力进度
    private TickerView tvProgressTuOther;//其他助力进度
    private TextView tvCompound;//合成分红
    private LinearLayout llToggle;//展开收起
    private TextView tvToggle;//展开收起
    private ImageView ivToggle;//展开收起
    private TextView tvExplain;//收益说明
    private LinearLayout llYesterday;//昨天收益
    private LinearLayout llAllQuery;//总收益疑问
    private LinearLayout llTuDiQuery;//徒弟收益疑问
    private LinearLayout llTuSunQuery;//徒孙收益疑问
    private LinearLayout llTodayQuery;//今天收益疑问
    private LinearLayout llYesterdayQuery;//昨天收益疑问
    private LinearLayout llTeam;//我的团队
    private LinearLayout llMoney;//我的收益
    private LinearLayout llInvitation;//分享
    private ProgressBar pbIncome;//所以进度条
    private ProgressBar pbFenHong;//返回进度条
    private SmartRefreshLayout srlEarnings;//刷新框架
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private IncomeStage incomeStage = null;//阶段状态
    private IncomeRecord allIncome=null;//收益记录
    private Integer userId;//当前用户

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_earnings,container,false);
        tvName = view.findViewById(R.id.tv_earnings_name);
        tvMoney = view.findViewById(R.id.tv_earnings_money);
        tvIncome = view.findViewById(R.id.tv_earnings_income);
        tvIncomeNum = view.findViewById(R.id.tv_earnings_income_num);
        tvFenHong = view.findViewById(R.id.tv_earnings_fenhong);
        tvAll = view.findViewById(R.id.tv_earnings_all);
        tvTuDi = view.findViewById(R.id.tv_earnings_tudi);
        tvTuSun = view.findViewById(R.id.tv_earnings_tusun);
        tvAllToday = view.findViewById(R.id.tv_earnings_all_today);
        tvTuDiToday = view.findViewById(R.id.tv_earnings_tudi_today);
        tvTuSunToday = view.findViewById(R.id.tv_earnings_tusun_today);
        tvAllYesterday = view.findViewById(R.id.tv_earnings_all_yesterday);
        tvTuDiYesterday = view.findViewById(R.id.tv_earnings_tudi_yesterday);
        tvTuSunYesterday = view.findViewById(R.id.tv_earnings_tusun_yesterday);
        tvTodayAll = view.findViewById(R.id.tv_earnings_today_all);
        tvYesterdayAll = view.findViewById(R.id.tv_earnings_yesterday_all);
        tvProgressTuDi = view.findViewById(R.id.tv_earnings_progress_tudi);
        tvProgressTuSun = view.findViewById(R.id.tv_earnings_progress_tusun);
        tvProgressTuOther = view.findViewById(R.id.tv_earnings_progress_other);
        tvCompound=view.findViewById(R.id.tv_earnings_fenhong_compound);
        llToggle=view.findViewById(R.id.ll_earnings_toggle);
        tvToggle=view.findViewById(R.id.tv_earnings_toggle);
        ivToggle=view.findViewById(R.id.iv_earnings_toggle);
        tvExplain=view.findViewById(R.id.tv_earnings_explain);
        llYesterday=view.findViewById(R.id.ll_earnings_yesterday);
        llTeam=view.findViewById(R.id.ll_earnings_team);
        llMoney=view.findViewById(R.id.ll_earnings_money);
        llInvitation=view.findViewById(R.id.ll_earnings_invitation);
        llAllQuery = view.findViewById(R.id.ll_earnings_all_query);
        llTuDiQuery = view.findViewById(R.id.ll_earnings_tudi_query);
        llTuSunQuery = view.findViewById(R.id.ll_earnings_tusun_query);
        llTodayQuery = view.findViewById(R.id.ll_earnings_today_query);
        llYesterdayQuery = view.findViewById(R.id.ll_earnings_yesterday_query);
        pbIncome = view.findViewById(R.id.pb_earnings_income);
        pbFenHong = view.findViewById(R.id.pb_earnings_fenhong);
        srlEarnings=view.findViewById(R.id.srl_earnings);
        srlEarnings.setEnableLoadMore(false);//禁止上拉加载
        srlEarnings.autoRefresh();//自动刷新
        TickerView[] tickerViews=new TickerView[]{tvMoney,tvIncome,tvIncomeNum,tvFenHong,tvAll,tvTuDi,tvTuSun,tvAllToday,
                tvTuDiToday,tvTuSunToday,tvAllYesterday,tvTuDiYesterday,tvTuSunYesterday,tvTodayAll,tvYesterdayAll,
                tvProgressTuDi,tvProgressTuSun,tvProgressTuOther};
        AnimationUtils.tickerRollView(tickerViews);//设置滚动金额
        initView();//初始化页面
        setViewListener();//事件监听
        return view;
    }


    /**
     * 初始化页面
     */
    private void initView() {
        //获取本地Id
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        if (userId > 0 ){
            String url= ServiceUrls.getIncomeMethodUrl("getIncomeByUserId");
            Map<String,Object> map=new HashMap<>();
            map.put("userId", userId);
            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text="无法连接到网络，请稍后再试";
                            if(isSuccess && responseCode==200){
                                try {
                                    JSONObject jsonObject = jsonObject = new JSONObject(response);
                                    String strData = jsonObject.getString("data");
                                    final JSONObject jsonData = new JSONObject(strData);
                                    String strIncomeStage = jsonData.getString("incomeStage");//获取出收益阶段
                                    String strProgress = jsonData.getString("progress");//获取出分红进度
                                    String strAllIncome = jsonData.getString("allIncome");//获取出总收益
                                    String strTodayIncome = jsonData.getString("todayIncome");//获取出今天收益
                                    String strYesterdayIncome = jsonData.getString("yesterdayIncome");//获取出昨天收益
                                    //反序列化数据
                                    incomeStage = gson.fromJson(strIncomeStage, IncomeStage.class);
                                    Progress progress = gson.fromJson(strProgress, Progress.class);
                                    allIncome = gson.fromJson(strAllIncome, IncomeRecord.class);
                                    IncomeRecord todayIncome = gson.fromJson(strTodayIncome, IncomeRecord.class);
                                    IncomeRecord yesterdayIncome = gson.fromJson(strYesterdayIncome, IncomeRecord.class);
                                    tvName.setText(incomeStage.getIncomeStageName().trim());
                                    tvMoney.setText(String.format(Locale.getDefault(),"%.0f元",incomeStage.getIncomeStageMoney()));
                                    if (progress!=null){
                                        final BigDecimal fenHongProgerss = progress.getTudi().add(progress.getTushu()).add(progress.getOther());//分红总进度百分比
                                        if (fenHongProgerss.intValue() >= 100) {//当分红进度到100%时
                                            tvCompound.setVisibility(View.VISIBLE);//显示合成按钮
                                        }
                                        setFenHongProgeress(pbFenHong,fenHongProgerss);//设置进度条动画
                                        tvFenHong.setText(String.format(Locale.getDefault(),"必得分红：%.3f%%",fenHongProgerss));//进度提示
                                        tvProgressTuDi.setText(String.format(Locale.getDefault(),"%.3f%%",progress.getTudi()));//徒弟助力
                                        tvProgressTuSun.setText(String.format(Locale.getDefault(),"%.3f%%",progress.getTushu()));//徒孙助力
                                        tvProgressTuOther.setText(String.format(Locale.getDefault(),"%.3f%%",progress.getOther()));//其他助力
                                    }
                                    if (allIncome!=null){
                                        final BigDecimal incomeProgress=(allIncome.getIncomeAll().divide(incomeStage.getIncomeStageMoney())).multiply(new BigDecimal(100));//收益进度百分比
                                        if (incomeProgress.intValue() >= 100){//当完成一个阶段时
                                            updateIncomeStage(userId);//更新阶段信息
                                        }else {
                                            setIncomeProgress(pbIncome,incomeProgress);//设置进度条动画
                                            tvIncomeNum.setText(String.format(Locale.getDefault(),"×%.1f倍加速",incomeStage.getIncomeStageNum()));
                                            tvTodayAll.setText(String.format(Locale.getDefault(),"今天总收益(%.1f倍加速)",incomeStage.getIncomeStageNum()));
                                            tvYesterdayAll.setText(String.format(Locale.getDefault(),"昨天总收益(%.1f倍加速)",incomeStage.getIncomeStageNum()));
                                            tvIncome.setText(String.format(Locale.getDefault(),"已解锁%.2f元，进度%.2f%%，",allIncome.getIncomeAll(),incomeProgress));//进度提示
                                        }
                                        tvAll.setText(String.format(Locale.getDefault(),"%.2f元",allIncome.getIncomeAll()));
                                        tvTuDi.setText(String.format(Locale.getDefault(),"%.2f元",allIncome.getIncomeTudi()));
                                        tvTuSun.setText(String.format(Locale.getDefault(),"%.2f元",allIncome.getIncomeTusun()));
                                    }
                                    tvName.setText(incomeStage.getIncomeStageName().trim());
                                    tvMoney.setText(String.format(Locale.getDefault(),"%.0f元",incomeStage.getIncomeStageMoney()));
                                   if(todayIncome!=null){
                                       tvAllToday.setText(String.format(Locale.getDefault(),"%.2f元",todayIncome.getIncomeAll()));
                                       tvTuDiToday.setText(String.format(Locale.getDefault(),"%.2f元",todayIncome.getIncomeTudi()));
                                       tvTuSunToday.setText(String.format(Locale.getDefault(),"%.2f元",todayIncome.getIncomeTusun()));
                                   }
                                   if (yesterdayIncome!=null){
                                       tvAllYesterday.setText(String.format(Locale.getDefault(),"%.2f元",yesterdayIncome.getIncomeAll()));
                                       tvTuDiYesterday.setText(String.format(Locale.getDefault(),"%.2f元",yesterdayIncome.getIncomeTudi()));
                                       tvTuSunYesterday.setText(String.format(Locale.getDefault(),"%.2f元",yesterdayIncome.getIncomeTusun()));
                                   }
                                    srlEarnings.finishRefresh();//刷新完成
                                } catch (JSONException e) {
                                    srlEarnings.finishRefresh(false);//刷新失败
                                    e.printStackTrace();
                                }
                            }else {
                                srlEarnings.finishRefresh(false);//刷新失败
                                Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }

    /**
     * 事件监听
     */
    private void setViewListener() {
        //收益说明
        tvExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,ExplainActivity.class);
                startActivity(intent);
            }
        });
        //展开收起
        llToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llYesterday.getVisibility()==View.VISIBLE){//判断是否显示
                    llYesterday.setVisibility(View.GONE);//隐藏昨天收益
                    tvToggle.setText("展开");//设置文本
                    ivToggle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_earnings_down,0));//设置0度的旋转图片
                }else {
                    llYesterday.setVisibility(View.VISIBLE);//显示昨天收益
                    tvToggle.setText("收起");//设置文本
                    ivToggle.setImageBitmap(ImageUtil.rotateBitmap(myActivity,R.drawable.ic_earnings_down,180));//设置180度的旋转图片
                }
            }
        });
        //总收益疑问
        llAllQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.dialog(myActivity,"累计总收益","累计总收益实时更新，由于系统数据量比较大，可能存在稍微延迟展示。","");
            }
        });
        //徒弟收益疑问
        llTuDiQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.dialog(myActivity,"徒弟贡献收益","根据徒弟们的[活跃时长]、[任务完成情况]、[看视频贡献比例]、" +
                        "[邀请好友数量]等因素综合计算；徒弟越多，每天坐享活跃收益越多。","更新时间：每天中午12点左右");
            }
        });
        //徒孙收益疑问
        llTuSunQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.dialog(myActivity,"徒孙贡献收益","根据徒孙们的[活跃时长]、[任务完成情况]、[看视频贡献比例]、" +
                        "[邀请好友数量]等因素综合计算；徒孙越多，每天坐享活跃收益越多；收益比例：1个徒弟=2个徒孙","更新时间：每天中午12点左右");
            }
        });
        //今天收益疑问
        llTodayQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.dialog(myActivity,"今日预估收益","今日预估收益实时更新，由于系统数据量比较大，可能存在稍微延迟展示。","以次日中午12点平台审核结算为准");
            }
        });
        //昨天收益疑问
        llYesterdayQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.dialog(myActivity,"昨天预估收益","每天凌晨左右更新，由于系统数据量比较大，可能存在稍微延迟展示。","以次日中午12点平台审核结算为准");
            }
        });
        //我的团队
        llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(myActivity,MyTeamActivity.class);
            startActivity(intent);
            }
        });
        //我的收益
        llMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,MyEarningsActivity.class);
                startActivity(intent);
            }
        });
        //分享
        llInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, InviteCardActivity.class);
                startActivity(intent);
            }
        });
        //SmartRefreshLayout 下拉刷新监听
        srlEarnings.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initView();
            }
        });
        //合成分红
        tvCompound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog messageDialog = new MessageDialog(myActivity, R.style.dialog, "确定合成分红吗?", new MessageDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm==true){//确定按钮
                            String url=ServiceUrls.getDividendMethodUrl("insertDividend");//新增分红
                            Map<String,Object> map=new HashMap<>();
                            map.put("userId",userId);//用户Id
                            map.put("sourceTypeId",7);//来源Id
                            OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                                @Override
                                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                    myActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String text = "无法连接网络，请稍后再试";
                                            if (isSuccess && responseCode==200){
                                                try {
                                                    JSONObject jsonObject=new JSONObject(response);
                                                    int code=jsonObject.getInt("code");
                                                    text=jsonObject.getString("text");
                                                    if (code==200){//合成成功
                                                        Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                                    }else {
                                                        Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }else {
                                                Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                            dialog.dismiss();//取消关闭弹出框
                        }else {
                            dialog.dismiss();//取消关闭弹出框
                        }
                    }
                });
                messageDialog.setTitle("合成分红");
                messageDialog.show();//显示弹出框

            }
        });
    }

    private void updateIncomeStage(Integer userId){
        String url=ServiceUrls.getIncomeMethodUrl("updateIncomeStage");
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);//当前用户id
        map.put("incomeStageId",incomeStage.getIncomeStageId()+1);//升级阶段
        OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
            IncomeStage incomeStage = null;
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text="无法连接网络，请稍后再试";
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    String data=jsonObject.getString("data");
                                    incomeStage=gson.fromJson(data,IncomeStage.class);
                                    final BigDecimal incomeProgress=(allIncome.getIncomeAll().divide(incomeStage.getIncomeStageMoney())).multiply(new BigDecimal(100));//收益进度百分比
                                    setIncomeProgress(pbIncome,incomeProgress);//设置进度条动画
                                    tvName.setText(incomeStage.getIncomeStageName().trim());
                                    tvMoney.setText(String.format(Locale.getDefault(),"%.0f元",incomeStage.getIncomeStageMoney()));
                                    tvIncomeNum.setText(String.format(Locale.getDefault(),"×%.1f倍加速",incomeStage.getIncomeStageNum()));
                                    tvTodayAll.setText(String.format(Locale.getDefault(),"今天总收益(%.1f倍加速)",incomeStage.getIncomeStageNum()));
                                    tvYesterdayAll.setText(String.format(Locale.getDefault(),"昨天总收益(%.1f倍加速)",incomeStage.getIncomeStageNum()));
                                    tvIncome.setText(String.format(Locale.getDefault(),"已解锁%.2f元，进度%.2f%%，",allIncome.getIncomeAll(),incomeProgress));//进度提示
                                }else {
                                    text = jsonObject.getString("text");
                                    Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    /**
     * 设置收益进度条
     * @param progressBar
     * @param progressNum
     */
    int IncomeProgressNum=0;//记录收益进度条
    private void setIncomeProgress(final ProgressBar progressBar, final BigDecimal progressNum) {
        int endProgressNum=progressNum.multiply(new BigDecimal(1000)).intValue();//结束进度数
        if (endProgressNum<IncomeProgressNum){
            IncomeProgressNum=0;
        }
        ValueAnimator animator = ValueAnimator.ofInt(IncomeProgressNum, endProgressNum).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            progressBar.setProgress((int) valueAnimator.getAnimatedValue());
        }
        });
        IncomeProgressNum=endProgressNum;//记录进度条数
        animator.start();
    }


    /**
     * 设置分红进度条
     * @param progressBar
     * @param progressNum
     */
    int fenHongProgeressNum=0;//记录分红进度条
    private void setFenHongProgeress(final ProgressBar progressBar, final BigDecimal progressNum) {
        int endProgressNum=progressNum.multiply(new BigDecimal(1000)).intValue();//结束进度数
        ValueAnimator animator = ValueAnimator.ofInt(fenHongProgeressNum, endProgressNum).setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                progressBar.setProgress((int) valueAnimator.getAnimatedValue());
            }
        });
        fenHongProgeressNum=endProgressNum;//记录进度条数
        animator.start();
    }
}
