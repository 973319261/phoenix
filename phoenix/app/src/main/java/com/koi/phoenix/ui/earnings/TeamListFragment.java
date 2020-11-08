package com.koi.phoenix.ui.earnings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.koi.phoenix.adapter.RvTeamAdapter;
import com.koi.phoenix.bean.TeamVo;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的团队列表
 */
public class TeamListFragment extends Fragment {
    private Activity myActivity;
    //控件
    private SmartRefreshLayout srlTeamList;//下拉加载，上拉刷新控件
    private RecyclerView rvTeamList;//列表
    private RvTeamAdapter rvTeamAdapter;//适配器
    private LinearLayout llEmpty;//数据为空图片
    private LinearLayout llFailure;//加载失败页面
    private List<TeamVo> listTeam;//团队列表
    List<TeamVo> tudiList;//徒弟列表
    List<TeamVo> tusunList;//徒孙列表
    List<TeamVo> effectList;//未激活列表
    private Integer state;//状态
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_team_list,container,false);
        rvTeamList = view.findViewById(R.id.rv_team_list);
        srlTeamList = view.findViewById(R.id.srl_team_list);
        llFailure=view.findViewById(R.id.ll_failure);
        llEmpty = view.findViewById(R.id.ll_order_list_empty);
        Bundle bundle=getArguments();
       if (bundle!=null){
           state=bundle.getInt("state");
           //============RecyclerView 初始化=========
           //===1、设置布局控制器
           //=1.1、创建布局管理器
           LinearLayoutManager layoutManager=new LinearLayoutManager(myActivity);
           //=1.2、设置为垂直排列，用setOrientation方法设置(默认为垂直布局)
           layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
           //=1.3、设置recyclerView的布局管理器
           rvTeamList.setLayoutManager(layoutManager);
           //==2、实例化适配器
           //=2.1、初始化适配器
           List<TeamVo> list=new ArrayList<>();
           rvTeamAdapter=new RvTeamAdapter(myActivity,list);
           //=2.3、设置recyclerView的适配器
           rvTeamList.setAdapter(rvTeamAdapter);
           loadListData(state);//加载数据
           srlTeamList.autoRefresh();//下拉加载动画
           srlTeamList.setEnableLoadMore(false);//禁止下拉加载

           setViewListener();
        }
        return view;
    }
    /**
     * 监听事件
     */
    private void setViewListener() {
        //加载失败点击
        llFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                srlTeamList.autoRefresh();//自动刷新
                loadListData(state);//初始化
            }
        });
        //下拉刷新
        srlTeamList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadListData(state);
            }
        });
    }
    /**
     * 加载团队信息
     */
    private void loadListData(final int state) {
        Integer userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取本地Id
        if (userId > 0 ){
            String url= ServiceUrls.getUserMethodUrl("getTeamByUserId");
            Map<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                  myActivity.runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          String text="无法连接网络，请稍后再试";
                          if (isSuccess && responseCode==200){
                              try {
                                  JSONObject jsonObject=new JSONObject(response);
                                  Integer code=jsonObject.getInt("code");
                                  if (code==200){
                                      String data=jsonObject.getString("data");
                                      Type type=new TypeToken<List<TeamVo>>(){}.getType();
                                      listTeam =gson.fromJson(data,type);
                                      getTeam();//团队列表进行分类
                                      if (state==0){
                                          addItem(tudiList);
                                      }else if (state==1){
                                         addItem(tusunList);
                                      }else if (state==2){
                                         addItem(effectList);
                                      }
                                      srlTeamList.finishRefresh();//刷新完成
                                  }
                              } catch (JSONException e) {
                                  e.printStackTrace();
                                  srlTeamList.finishRefresh(false);//刷新失败
                                  text="数据解析失败";
                                  Toast.makeText(myActivity,text,Toast.LENGTH_LONG).show();
                              }
                          }else {
                              srlTeamList.finishRefresh(false);//刷新失败
                              rvTeamList.setVisibility(View.GONE);//隐藏列表
                              llFailure.setVisibility(View.VISIBLE);//加载失败
                              llEmpty.setVisibility(View.GONE);//显示图片
                          }
                      }
                  });
                }
            });
        }
    }
    /**
     * 获取团队信息(分类)
     * @return
     */
    private void getTeam(){
        tudiList=new ArrayList<>();
        tusunList=new ArrayList<>();
        effectList=new ArrayList<>();
        for (TeamVo tudi:listTeam){
            if (tudi.getIsEffect()==1){//已激活徒弟信息
                tudiList.add(tudi);//已激活徒弟信息
            }else {
                effectList.add(tudi);//未激活徒弟信息
            }
            if (tudi.getChildren()!=null){//有徒孙信息
                for (TeamVo tusun:tudi.getChildren()){
                    if (tusun.getIsEffect()==1){//已激活徒孙信息
                        tusunList.add(tusun);//已激活徒孙信息
                    }else {
                        effectList.add(tusun);//未激活徒孙信息
                    }
                }
            }
        }
    }

    /**
     * 添加到列表适配器
     * @param list
     */
    private void addItem(List<TeamVo> list){
        if (list.size()>0){
            rvTeamAdapter.addItem(list);
            llEmpty.setVisibility(View.GONE);//图片
            llFailure.setVisibility(View.GONE);//加载失败
            rvTeamList.setVisibility(View.VISIBLE);//列表
        }else {
            llEmpty.setVisibility(View.VISIBLE);//图片
            llFailure.setVisibility(View.GONE);//加载失败
            rvTeamList.setVisibility(View.GONE);//列表
        }
    }
}
