package com.koi.phoenix.ui.work;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvWorkAdapter;
import com.koi.phoenix.bean.SignVo;
import com.koi.phoenix.bean.TaskVo;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.dialog.SignDialog;
import com.koi.phoenix.ui.user.invite.InviteCardActivity;
import com.koi.phoenix.util.AnimationUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.robinhood.ticker.TickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作页面
 */
public class WorkFragment extends Fragment {
    private Activity myActivity;//上下文
    private TickerView tvPowerNum;//算力总数
    private SmartRefreshLayout srlWork;//刷新框架
    private RecyclerView rvEverydayList;//每日任务列表
    private RecyclerView rvRecommendList;//推荐任务列表
    private RvWorkAdapter rvEverydayAdapter;//每日任务适配器
    private RvWorkAdapter rvRecommendAdapter;//推荐任务适配器
    private Integer userId;//当前用户
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private SimpleDateFormat sf=new SimpleDateFormat("MM/dd");
    private LoadingDialog loadingDialog;//加载中动画
    private Runnable minutes;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work,container,false);
        tvPowerNum=view.findViewById(R.id.tv_work_powerNum);
        rvEverydayList = view.findViewById(R.id.rv_work_everyday);
        rvRecommendList = view.findViewById(R.id.rv_work_recommend);
        srlWork=view.findViewById(R.id.srl_work);
        loadingDialog = new LoadingDialog(myActivity, "正在加载中...");
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        //=1.1、创建布局管理器
        LinearLayoutManager layoutEveryday=new LinearLayoutManager(myActivity);
        LinearLayoutManager layoutRecommend=new LinearLayoutManager(myActivity);
        //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
        layoutEveryday.setOrientation(LinearLayoutManager.VERTICAL);
        layoutRecommend.setOrientation(LinearLayoutManager.VERTICAL);
        //=1.3、设置recyclerView的布局管理器
        rvEverydayList.setLayoutManager(layoutEveryday);
        rvRecommendList.setLayoutManager(layoutRecommend);

        //==2、实例化适配器
        //=2.1、初始化适配器
        rvEverydayAdapter=new RvWorkAdapter(myActivity);
        rvRecommendAdapter=new RvWorkAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvEverydayList.setAdapter(rvEverydayAdapter);
        rvRecommendList.setAdapter(rvRecommendAdapter);
        srlWork.setEnableLoadMore(false);//禁止上拉加载
        srlWork.autoRefresh();//自动下拉
        AnimationUtils.tickerRollView(new TickerView[]{tvPowerNum});//设置数字滚动动画
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
            String url= ServiceUrls.getTaskMethodUrl("getTaskByUserId");
            Map<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text = "当前无法连接网络，请稍后再试";
                            if (isSuccess && responseCode==200){
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    int code=jsonObject.getInt("code");
                                    if (code==200){
                                        String data=jsonObject.getString("data");
                                        JSONObject jsonData=new JSONObject(data);
                                        int powerNum=jsonData.getInt("powerNum");//算力总数
                                        tvPowerNum.setText(String.valueOf(powerNum));//设置算力
                                        String taskVoListStr=jsonData.getString("taskVoList");//任务明细列表
                                        Type type=new TypeToken<List<TaskVo>>(){}.getType();
                                        List<TaskVo> taskVoList = gson.fromJson(taskVoListStr,type);// 任务明细列表
                                        rvEverydayAdapter.addItem(findTaskVoByType(taskVoList,1));//每日任务
                                        rvRecommendAdapter.addItem(findTaskVoByType(taskVoList,2));//推荐任务
                                        srlWork.finishRefresh();//刷新成功
                                    }else {
                                        srlWork.finishRefresh(false);//刷新失败
                                        text=jsonObject.getString("code");
                                        Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    srlWork.finishRefresh(false);//刷新失败
                                }
                            }else {
                                srlWork.finishRefresh(false);//刷新失败
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
        //下拉刷新
        srlWork.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initView();
            }
        });
        rvEverydayAdapter.setOnItemClickListener(new RvWorkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskVo taskVo, final TextView tvRewardNum, final TextView tvRewardType) {
                final TextView[] textViews = new TextView[]{tvRewardNum,tvRewardType};//需要更改的view
                final TickerView[] tickerViews=new TickerView[]{tvPowerNum};//需要更改的view
                if (taskVo.getTaskId()==1){//每日签到
                    String url= ServiceUrls.getTaskMethodUrl("getSignByUserId");
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",userId);
                    loadingDialog.show();
                    OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                        @Override
                        public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String text = "当前无法连接网络，请稍后再试";
                                    if (isSuccess && responseCode==200){
                                        try {
                                            JSONObject jsonObject=new JSONObject(response);
                                            int code=jsonObject.getInt("code");
                                            if (code==200){
                                                String data=jsonObject.getString("data");
                                                Type type=new TypeToken<List<SignVo>>(){}.getType();
                                                List<SignVo> signVoList = gson.fromJson(data,type);
                                                boolean flag = findSignVoByNowDate(signVoList);//查询是否存在
                                                if (flag){
                                                    //创建弹窗并且传签到列表到弹窗
                                                    SignDialog signDialog =new SignDialog(myActivity,signVoList,textViews,tickerViews);
                                                    signDialog.show();//显示
                                                }else {
                                                    Toast.makeText(myActivity,"数据未更新，请稍后再试",Toast.LENGTH_LONG).show();
                                                }

                                            }else {
                                                text=jsonObject.getString("code");
                                                Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                                    }
                                    loadingDialog.hide();
                                }
                            });
                        }
                    });
                }else if (taskVo.getTaskId()==2){//邀请好友
                    Intent intent=new Intent(myActivity, InviteCardActivity.class);
                    startActivity(intent);
                }else if (taskVo.getTaskId()==4){//观看视频
                    if (taskVo.getHasDo()==0){//未完成
                        Toast.makeText(myActivity,"观看视频中...",Toast.LENGTH_LONG).show();
                        String url=ServiceUrls.getTaskMethodUrl("updateTaskDetailProgress");
                        Map<String,Object> map=new HashMap<>();
                        map.put("userId",userId);
                        map.put("taskId",4);//在线任务
                        map.put("sourceTypeId",12);//签到获取
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
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }else {//已完成
                        Toast.makeText(myActivity,"今日的视频已看完，明天再来吧",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    /**
     * 修改当前用户任务完成状态
     */
    private void updateTaskDetailHasDo(final TaskVo taskVo, final TextView tvRewardNum, final TextView tvRewardType){
        String url=ServiceUrls.getTaskMethodUrl("updateTaskDetailHasDo");
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("taskId",taskVo.getTaskId());
        map.put("hasDo",1);//完成状态
        map.put("rewardNum",taskVo.getRewardNum());//奖励数
        map.put("rewardType",taskVo.getRewardType());//奖励类型
        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
            @Override
            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String text="无法连接网络，请稍后再试";
                        if (isSuccess && responseCode==200){
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                int code=jsonObject.getInt("code");//设置状态码到全局
                                if (code==200){
                                    if (taskVo.getTaskId()==1){//每日签到
                                        tvRewardType.setText("已完成");
                                        tvRewardType.setTextColor(Color.GRAY);//灰色字体
                                        tvRewardNum.setVisibility(View.GONE);//隐藏
                                    }
                                }
                            return;
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
     * 通过任务类型进行分类
     * @param type
     * @return
     */
    private List<TaskVo> findTaskVoByType(List<TaskVo> taskVoList,int type){
        List<TaskVo> list=new ArrayList<>();
        for (TaskVo taskVo:taskVoList){
            if (taskVo.getTaskType()==type){
                list.add(taskVo);
            }
        }
        return list;
    }

    /**
     * 通过任务Id查询TaskVo
     * @param taskVoList
     * @param taskId
     * @return
     */
    private TaskVo findTaskVoByTaskId(List<TaskVo> taskVoList,int taskId){
        for (TaskVo taskVo:taskVoList){
            if (taskVo.getTaskId()==taskId){
                return taskVo;
            }
        }
        return null;
    }
    /**
     * 查询今天的签到数据是否存在
     * @param signVoList
     * @return
     */
    private boolean findSignVoByNowDate(List<SignVo> signVoList){
        Date date=new Date();
        for (int i=0;i<signVoList.size();i++){
            if (sf.format(signVoList.get(i).getSignDay()).equals(sf.format(date))){//今天
                return true;//返回
            }
        }
        return false;
    }
}
