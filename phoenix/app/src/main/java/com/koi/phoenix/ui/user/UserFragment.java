package com.koi.phoenix.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.User;
import com.koi.phoenix.ui.LoginActivity;
import com.koi.phoenix.ui.user.invite.InviteActivity;
import com.koi.phoenix.ui.user.inviteVolume.InviteVolumeActivity;
import com.koi.phoenix.ui.user.redPacket.RedPacketActivity;
import com.koi.phoenix.ui.user.setting.SettingActivity;
import com.koi.phoenix.ui.user.userInfo.UserInfoActivity;
import com.koi.phoenix.ui.user.wallet.WalletActivity;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 用户页面
 */
public class UserFragment extends Fragment {
    private Activity myActivity;//上下文
    private ImageView ivAvatar;//用户头像
    private ImageView ivSex;//性别
    private TextView tvNickName;//昵称
    private TextView tvId;//ID
    private TickerView tvDhcNum;//dhc
    private TickerView tvPowerNum;//算力
    private TickerView tvBalance;//余额
    private TickerView tvMoney;//可提现金额
    private TickerView tvInviteTicket;//邀请卷
    private LinearLayout llHeader;//头部
    private LinearLayout llDhc;//算力
    private LinearLayout llPower;//余额
    private LinearLayout llBalance;//邀请卷
    private LinearLayout llInviteTicket;//邀请卷
    private LinearLayout llWallet;//我的钱包
    private LinearLayout llRedPacket;//助力领红包
    private LinearLayout llInvite;//我的邀请码
    private LinearLayout llHelp; //帮助与反馈
    private LinearLayout llSetting;//设置
    private SmartRefreshLayout srlUser;//下拉框架
    private User user;//用户信息
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myActivity= (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        ivAvatar=view.findViewById(R.id.iv_user_avatar);
        ivSex=view.findViewById(R.id.iv_user_sex);
        tvNickName=view.findViewById(R.id.tv_user_nickName);
        tvId=view.findViewById(R.id.tv_user_id);
        tvDhcNum=view.findViewById(R.id.tv_user_dhcNum);
        tvPowerNum=view.findViewById(R.id.tv_user_powerNum);
        tvBalance=view.findViewById(R.id.tv_user_balance);
        tvInviteTicket=view.findViewById(R.id.tv_user_inviteTicket);
        tvMoney=view.findViewById(R.id.tv_user_money);
        llHeader=view.findViewById(R.id.ll_header);
        llDhc = view.findViewById(R.id.ll_user_dhc);
        llPower = view.findViewById(R.id.ll_user_power);
        llBalance = view.findViewById(R.id.ll_user_balance);
        llInviteTicket = view.findViewById(R.id.ll_user_inviteTicket);
        llWallet=view.findViewById(R.id.ll_user_myWallet);
        llRedPacket=view.findViewById(R.id.ll_user_redPacket);
        llInvite = view.findViewById(R.id.ll_user_myInvite);
        llHelp = view.findViewById(R.id.ll_user_help);
        llSetting = view.findViewById(R.id.ll_user_setting);
        AnimationUtils.tickerRollView(new TickerView[]{tvDhcNum,tvPowerNum,tvBalance,tvInviteTicket,tvMoney});//设置需要滚动的数字
        srlUser=view.findViewById(R.id.srl_user);
        srlUser.setEnableLoadMore(false);//禁用上来加载
        initView();//初始化页面
        setContextListener();//设置监听事件
        return view;
    }

    /**
     * 初始化页面
     */
    private void initView() {
        srlUser.autoRefresh();//自动下拉动画
        Integer userId = (Integer) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        if (userId>0){
            String url= ServiceUrls.getUserMethodUrl("getUserInfoById");
            Map<String,Object> map=new HashMap<>();
            map.put("userId",userId);
            OkHttpTool.httpGet(url, map, new OkHttpTool.ResponseCallback() {
                @Override
                public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text=ServiceUrls.responseText;
                            if(isSuccess && responseCode==200){
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    int code=jsonObject.getInt("code");
                                    if (code==200){
                                        String data = jsonObject.getString("data");
                                        JSONObject jsonData=new JSONObject(data);
                                        String userStr=jsonData.getString("user");
                                        BigDecimal dhcNum= new BigDecimal(jsonData.getString("dhcNum"));
                                        BigDecimal balance= new BigDecimal(jsonData.getString("balance"));
                                        tvDhcNum.setText(String.format(Locale.getDefault(),"%.2f",dhcNum));//dhc总数
                                        tvPowerNum.setText(jsonData.getString("powerNum"));//算力总数
                                        tvBalance.setText(String.format(Locale.getDefault(),"%.2f",balance));//余额
                                        tvMoney.setText(String.format(Locale.getDefault(),"可提现%.1f元",balance));//可提现金额
                                        //用户信息
                                        user = gson.fromJson(userStr,User.class);
                                        //获取头像信息
                                        String photo = user.getAvatarUrl();
                                        String  photoUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + photo;
                                        //使用Glide加载头像信息
                                        //设置头像加载失败时的默认头像
                                        Glide.with(myActivity)
                                                .load(photoUrl)
                                                .apply(headerRO.error(R.drawable.ic_avatar_error))
                                                .into(ivAvatar);
                                        //加载性别信息
                                        int icErrorGender = user.getGender()==0? R.drawable.ic_sex_man : R.drawable.ic_sex_woman;
                                        ivSex.setImageResource(icErrorGender);//设置性别图标
                                        tvNickName.setText(user.getNickName().trim());//昵称
                                        tvId.setText(String.format(Locale.getDefault(),"ID:%06d", user.getUserId()));//ID
                                        tvInviteTicket.setText(String.valueOf(user.getInviteTicketNum()));//邀请卷
                                        srlUser.finishRefresh();//刷新成功
                                    }else {
                                        srlUser.finishRefresh(false);//刷新失败
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                srlUser.finishRefresh(false);
                            }
                        }
                    });
                }
            });
        }else {
            Intent intent=new Intent(myActivity, LoginActivity.class);//登录界面
            startActivity(intent);
        }
    }

    /**
     * 设置监听事件
     */
    private void setContextListener() {
        //下拉刷新
        srlUser.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initView();
            }
        });
        //头部
        llHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, UserInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",user);//传递用户信息
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //DHC
        llDhc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,HdcActivity.class);
                startActivity(intent);
            }
        });
        //算力
        llPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,PowerActivity.class);
                startActivity(intent);
            }
        });
        //余额
        llBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, WalletActivity.class);//钱包
                startActivity(intent);
            }
        });
        //邀请卷
        llInviteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, InviteVolumeActivity.class);//邀请卷
                startActivity(intent);
            }
        });
        //我的钱包
        llWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, WalletActivity.class);
                startActivity(intent);
            }
        });
        //我的邀请码
        llInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, InviteActivity.class);
                startActivity(intent);
            }
        });
        //助力领红包
        llRedPacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, RedPacketActivity.class);
                startActivity(intent);
            }
        });
        //帮助与反馈
        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,HelpActivity.class);
                startActivity(intent);
            }
        });
        //设置
        llSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        //刷新用户页面
        initView();
    }
}
