package com.koi.phoenix.ui.user.wallet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.RvWithdrawSettingAdapter;
import com.koi.phoenix.bean.WithdrawSettingVo;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.RecyclerViewSpaces;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MultiLineRadioGroup;
import com.koi.phoenix.widget.MyActionBar;
import com.koi.phoenix.widget.RunningTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 我的钱包页面
 */
public class WalletActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private RunningTextView rtvBalance;//余额
    private RadioGroup rgMode;//提现方式
    private RadioButton rbWeiXin;//微信
    private RvWithdrawSettingAdapter rvWithdrawSettingAdapter;//适配器
    private RecyclerView rvWithdrawSettingList;//钱包金额设置列表
    private MultiLineRadioGroup multiLineRadioGroup;//金额父容器
    private Button btnWithdraw;//提现
    private MyActionBar myActionBar;//标题栏
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private Integer userId;//用户ID
    private BigDecimal money;//提现金额
    private BigDecimal balance;//余额
    private LoadingDialog loadingDialog;//加载动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_user_wallet);
        rtvBalance=findViewById(R.id.tv_wallet_balance);
        rgMode = findViewById(R.id.rg_wallet_mode);
        rbWeiXin = findViewById(R.id.rb_wallet_mode_0);
        rvWithdrawSettingList=findViewById(R.id.rv_withdraw_setting_list);
        multiLineRadioGroup=findViewById(R.id.rg_wallet_money);
        btnWithdraw=findViewById(R.id.btn_withdraw);
        initView();//初始化
        setViewListener();//事件监听
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("我的钱包", R.drawable.ic_custom_actionbar_left, "零钱明细", 0, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭当前页面
            }
            @Override
            public void onRightClick() {//零钱记录
                Intent intent=new Intent(myActivity, WalletRecordActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 初始化
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        rgMode.check(R.id.rb_wallet_mode_0);//默认选中第一个支付方式
        Drawable iconWeiXin=getResources().getDrawable(R.drawable.ic_wechat);//设置微信图标样式
        iconWeiXin.setBounds(0,0,60,60);//设置图标边距 大小
        rbWeiXin.setCompoundDrawables(iconWeiXin,null,null,null);//设置图标位置
        //============RecyclerView 初始化=========
        //===1、设置布局控制器
        GridLayoutManager layoutManager = new GridLayoutManager(myActivity,3);//三列
        rvWithdrawSettingList.setLayoutManager(layoutManager);
        //==2、实例化适配器
        //=2.1、初始化适配器
        rvWithdrawSettingAdapter=new RvWithdrawSettingAdapter(myActivity);
        //=2.3、设置recyclerView的适配器
        rvWithdrawSettingList.setAdapter(rvWithdrawSettingAdapter);
        HashMap<String, Integer> mapSpaces = new HashMap<>();//间距
        mapSpaces.put(RecyclerViewSpaces.TOP_DECORATION, 0);//上间距
        mapSpaces.put(RecyclerViewSpaces.BOTTOM_DECORATION, 20);//下间距
        mapSpaces.put(RecyclerViewSpaces.LEFT_DECORATION, 0);//左间距
        mapSpaces.put(RecyclerViewSpaces.RIGHT_DECORATION, 20);//右间距
        rvWithdrawSettingList.addItemDecoration(new RecyclerViewSpaces(mapSpaces));//设置间距
        String url= ServiceUrls.getWalletMethodUrl("getWithdrawSetting");
        Map<String,Object> map=new HashMap<>();
        map.put("userId", userId);
        loadingDialog=new LoadingDialog(myActivity);
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
                                String data=jsonObject.getString("data");
                                JSONObject jsonData=new JSONObject(data);
                                balance=new BigDecimal(jsonData.getString("balance"));//余额
                                rtvBalance.setValue(String.format(Locale.getDefault(),"%.2f",balance),"0.00");//设置余额
                                Type type=new TypeToken<List<WithdrawSettingVo>>(){}.getType();
                                String withdrawSettingVoList=jsonData.getString("withdrawSettingVoList");
                                List<WithdrawSettingVo> list = gson.fromJson(withdrawSettingVoList,type);
                                rvWithdrawSettingAdapter.addItem(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadingDialog.hide();
                    }
                });
            }
        });
    }
    /**
     * 事件监听
     */
    private void setViewListener(){
        //选择金额
        rvWithdrawSettingAdapter.setOnItemClickListener(new RvWithdrawSettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WithdrawSettingVo withdrawSettingVo, int position) {
                multiLineRadioGroup.check(withdrawSettingVo.getWithdrawSettingId());//通过ID选择金额
                money=withdrawSettingVo.getWithdrawMoney();//获取提现金额
            }
        });
        //提现
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiLineRadioGroup.getCheckedRadioButtonId()==-1){//未选择
                    Toast.makeText(myActivity,"请选择提现金额",Toast.LENGTH_LONG).show();
                    return;
                }
                if (balance.compareTo(money)==-1){//余额不足
                    Toast.makeText(myActivity,"账号余额不足",Toast.LENGTH_LONG).show();
                    return;
                }
                String url=ServiceUrls.getWalletMethodUrl("addMoneyRecord");
                Map<String,Object> map=new HashMap();
                map.put("userId",userId);
                map.put("moneySourceId",1);//零钱来源（微信提现）
                map.put("moneyStateId",1);//零钱状态（处理中）
                map.put("money",String.format(Locale.getDefault(),"-%.2f",money));//提现金额
                map.put("moneyType",1);//零钱类型（提现）
                loadingDialog=new LoadingDialog(myActivity,"提现中...");
                loadingDialog.show();
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
                                        int code=jsonObject.getInt("code");
                                        if (code==200){
                                            Intent intent=new Intent(myActivity,WithdrawResultActivity.class);
                                            intent.putExtra("money",String.valueOf(money));
                                            startActivity(intent);
                                            balance=balance.subtract(money);//重新计算余额
                                            rtvBalance.setText(String.format(Locale.getDefault(),"%.2f",balance));//设置余额

                                        }else {
                                            Toast.makeText(myActivity,"提现失败，请稍后再试",Toast.LENGTH_LONG).show();
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
        });
    }
}
