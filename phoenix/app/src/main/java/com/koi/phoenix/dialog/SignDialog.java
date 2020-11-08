package com.koi.phoenix.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvSignAdapter;
import com.koi.phoenix.bean.SignVo;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.RecyclerViewSpaces;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.robinhood.ticker.TickerView;

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
 * 签到弹窗
 */
public class SignDialog extends Dialog {
    private Activity myActivity;
    private RecyclerView rvSignOne;//签到列表
    private RecyclerView rvSignTow;//签到列表
    private RvSignAdapter rvSignOneAdapter;//签到适配器
    private RvSignAdapter rvSignTowAdapter;//签到适配器
    private ImageView ivClose;//关闭
    private TextView tvSignToday;//今天签到
    private TextView tvRewardNum;//工作页面今天签到
    private TextView tvRewardType;//工作页面今天签到
    private TickerView tvPowerNum;//工作页面算力
    private List<SignVo> signVoList;//签到列表
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private SimpleDateFormat sf=new SimpleDateFormat("MM/dd");
    private SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd");
    private SignVo signVo;//今天的签到信息
    private int position;//今天签到所在的索引
    private LoadingDialog loadingDialog;//加载中动画

    public SignDialog(Context context, List<SignVo> signVoList, TextView[] textViews, TickerView[] tickerViews){
        super(context, R.style.dialog);
        this.myActivity= (Activity) context;
        this.signVoList=signVoList;
        this.tvRewardNum=textViews[0];
        this.tvRewardType=textViews[1];
        this.tvPowerNum=tickerViews[0];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign);
        rvSignOne=findViewById(R.id.rv_sign_one);
        rvSignTow=findViewById(R.id.rv_sign_tow);
        ivClose=findViewById(R.id.close);
        tvSignToday=findViewById(R.id.tv_sign_today);
        loadingDialog = new LoadingDialog(myActivity, "正在加载中...");
        setCanceledOnTouchOutside(false);//禁止点击外部关闭
        initView();
        setViewListener();//监听事件
    }

    /**
     * 监听事件
     */
    private void setViewListener() {
        //关闭
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //第一行
        rvSignOneAdapter.setOnItemClickListener(new RvSignAdapter.OnItemClickListener() {
            @Override
            public void click(SignVo signVo,int position) {
                if (sf.format(signVo.getSignDay()).equals(sf.format(new Date()))){//今天签到
                    updateSignDetail(signVo,position,1);//修改
                }else {
                    Toast.makeText(myActivity,"观看视频中...",Toast.LENGTH_LONG).show();
//                    updateSignDetail(signVo,position);//修改
                }
            }
        });
        //第二行
        rvSignTowAdapter.setOnItemClickListener(new RvSignAdapter.OnItemClickListener() {
            @Override
            public void click(SignVo signVo,int position) {
                if (sf.format(signVo.getSignDay()).equals(sf.format(new Date()))){//今天签到
                    updateSignDetail(signVo,position,2);//修改
                }else {
                    Toast.makeText(myActivity,"观看视频中...",Toast.LENGTH_LONG).show();
//                    updateSignDetail(signVo,position);//修改
                }
            }

        });
        //今天签到
        tvSignToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSignDetail(signVo,position,0);
            }
        });
    }

    /**
     * 修改签到信息
     * @param signVo 当前签到信息
     * @param position 所在列表索引
     * @param adapter 第几个适配器 t
     */
    private void updateSignDetail(final SignVo signVo, final int position,final int adapter){
        String url=ServiceUrls.getTaskMethodUrl("updateSignDetail");
        Map<String,Object> map=new HashMap<>();
        map.put("signId",signVo.getSignId());//签到ID
        map.put("userId",signVo.getUserId());//签到用户
        map.put("signDay",sf1.format(signVo.getSignDay()));//签到日期
        map.put("rewardNum",signVo.getRewardNum());//奖励数量
        map.put("isSign",1);//签到状态
        loadingDialog.show();
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
                                int code=jsonObject.getInt("code");
                                if (code==200){
                                    if (sf.format(signVo.getSignDay()).equals(sf.format(new Date()))){//今天签到
                                        tvRewardType.setText("已完成");
                                        tvRewardType.setTextColor(Color.GRAY);//灰色字体
                                        tvRewardNum.setVisibility(View.GONE);//隐藏
                                        tvSignToday.setEnabled(false);//禁止点击
                                        tvSignToday.setText("今天已签到");
                                    }
                                    String data = jsonObject.getString("data");
                                    SignVo signVo1=gson.fromJson(data,SignVo.class);
                                    tvPowerNum.setText(String.valueOf(Integer.valueOf(tvPowerNum.getText().trim()) + signVo.getRewardNum()));//更新算力view
                                    //adapter==0 && position<3 用来判断是否是今天签到日期
                                    if (adapter==1 || (adapter==0 && position<3)){//第一个适配器
                                        rvSignOneAdapter.updateItem(signVo1,position);//更新签到列表
                                    }else if(adapter==2 || (adapter==0 && position>=3)){//第二个适配器
                                        rvSignTowAdapter.updateItem(signVo1,adapter==2?position:position-3);//更新签到列表
                                    }
                                }else {
                                    text=jsonObject.getString("text");
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
    }
    /**
     * 创建签到列表
     */
    private void initView(){
        LinearLayoutManager layoutSignOne=new LinearLayoutManager(myActivity);
        LinearLayoutManager layoutSignTow=new LinearLayoutManager(myActivity);
        layoutSignOne.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutSignTow.setOrientation(LinearLayoutManager.HORIZONTAL);
        HashMap<String, Integer> mapSpacesOne = new HashMap<>();
        mapSpacesOne.put(RecyclerViewSpaces.TOP_DECORATION, 10);//上间距
        mapSpacesOne.put(RecyclerViewSpaces.BOTTOM_DECORATION, 10);//下间距
        mapSpacesOne.put(RecyclerViewSpaces.LEFT_DECORATION, 20);//左间距
        mapSpacesOne.put(RecyclerViewSpaces.RIGHT_DECORATION, 20);//右间距
        HashMap<String, Integer> mapSpacesTow = new HashMap<>();
        mapSpacesTow.put(RecyclerViewSpaces.TOP_DECORATION, 10);//上间距
        mapSpacesTow.put(RecyclerViewSpaces.BOTTOM_DECORATION, 10);//下间距
        mapSpacesTow.put(RecyclerViewSpaces.LEFT_DECORATION, 10);//左间距
        mapSpacesTow.put(RecyclerViewSpaces.RIGHT_DECORATION, 10);//右间距
        rvSignOne.addItemDecoration(new RecyclerViewSpaces(mapSpacesOne));
        rvSignTow.addItemDecoration(new RecyclerViewSpaces(mapSpacesTow));
        rvSignOne.setLayoutManager(layoutSignOne);
        rvSignTow.setLayoutManager(layoutSignTow);
        rvSignOneAdapter=new RvSignAdapter(myActivity);
        rvSignTowAdapter=new RvSignAdapter(myActivity);
        rvSignOne.setAdapter(rvSignOneAdapter);
        rvSignTow.setAdapter(rvSignTowAdapter);
        rvSignOneAdapter.addItem(findSignVoByIndex(signVoList,0,3));//第一行签到列表
        rvSignTowAdapter.addItem(findSignVoByIndex(signVoList,3,7));//第二行签到列表
        //查找今天的数据
        signVo = findSignVoByNowDate(signVoList);
        if (signVo !=null){
            if (signVo.getIsSign()==0){//未签到
                tvSignToday.setEnabled(true);//禁止点击
                tvSignToday.setText("签到");
            }else {//已签到
                tvSignToday.setEnabled(false);//禁止点击
                tvSignToday.setText("今天已签到");
            }
        }
    }
    /**
     * 通过范围查询需要的签到列表
     * @param signVoList
     * @param startIndex
     * @param endIndex
     * @return
     */
    private List<SignVo> findSignVoByIndex(List<SignVo> signVoList, int startIndex,int endIndex){
        List<SignVo> list=new ArrayList<>();
        for (int i=startIndex; i<endIndex;i++){
            list.add(signVoList.get(i));
        }
        return list;
    }

    /**
     * 查询今天的签到数据
     * @param signVoList
     * @return
     */
    private SignVo findSignVoByNowDate(List<SignVo> signVoList){
        Date date=new Date();
        position=0;
        for (int i=0;i<signVoList.size();i++){
            if (sf.format(signVoList.get(i).getSignDay()).equals(sf.format(date))){//今天
                position=i;//设置今天签到信息所在的收益
                return signVoList.get(i);//返回今天的签到信息
            }
        }
        return null;
    }
}
