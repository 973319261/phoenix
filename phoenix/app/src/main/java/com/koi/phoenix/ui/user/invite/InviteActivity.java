package com.koi.phoenix.ui.user.invite;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.User;
import com.koi.phoenix.dialog.CommonDialog;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.ui.earnings.MyTeamActivity;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;
import com.koi.phoenix.widget.RunningTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 邀请好友页面
 */
public class InviteActivity extends AppCompatActivity {
    private Activity myActivity;
    private MyActionBar myActionBar;
    private TextView tvInviteCode;//邀请码
    private TextView tvCopy;//复制
    private RunningTextView rtvTudiNum;//徒弟数
    private RunningTextView rtvTudiPower;//徒弟获取的算力数
    private RunningTextView rtvTusunNum;//徒孙数
    private RunningTextView rtvTusunPower;//徒孙获取的算力数
    private LinearLayout llTudi;//徒弟
    private LinearLayout llTusun;//徒孙
    private Button btnLink;//生成邀请链接
    private Button btnCard;//生成卡片
    private int userId;//用户ID
    private String inviteCode;//用户邀请码
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private LoadingDialog loadingDialog;
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_user_invite);
        tvInviteCode = findViewById(R.id.tv_inviteCode);
        tvCopy=findViewById(R.id.tv_copy);
        rtvTudiNum=findViewById(R.id.rtv_tudi_num);
        rtvTudiPower=findViewById(R.id.rtv_tudi_power_num);
        rtvTusunNum=findViewById(R.id.rtv_tusun_num);
        rtvTusunPower=findViewById(R.id.rtv_tusun_power_num);
        llTudi=findViewById(R.id.ll_invite_tudi);
        llTusun=findViewById(R.id.ll_invite_tusun);
        btnLink = findViewById(R.id.btn_invite_link);//生成邀请链接
        btnCard = findViewById(R.id.btn_invite_card);//生成卡片
        myActionBar = findViewById(R.id.myActionBar);
        userId= (int) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);//获取用户ID
        inviteCode= (String) SPUtils.get(myActivity,SPUtils.SP_USER_INVITE_CODE,"");//获取用户邀请码
        loadingDialog=new LoadingDialog(myActivity);
        myActionBar.setData("邀请好友", R.drawable.ic_custom_actionbar_left, "我的师傅", 0, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {//返回
                finish();//关闭页面
            }

            @Override
            public void onRightClick() {
                if (userId>0){
                    String url= ServiceUrls.getUserMethodUrl("getFromUser");
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",userId);
                    loadingDialog.show();//显示加载动画
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
                                                String data = jsonObject.getString("data");
                                                Type type=new TypeToken<User>(){}.getType();
                                                User user = gson.fromJson(data,type);
                                                CommonDialog commonDialog =new CommonDialog(myActivity, new CommonDialog.OnCloseListener() {
                                                    @Override
                                                    public void onClick(Dialog dialog, boolean confirm) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                commonDialog.setTitle("我的师傅");
                                                LinearLayout layout = new LinearLayout(myActivity);
                                                LinearLayout.LayoutParams imageViewLayout=new LinearLayout.LayoutParams(150, 150);//头像大小
                                                layout.setOrientation(LinearLayout.VERTICAL);//垂直显示
                                                layout.setGravity(Gravity.CENTER);//居中显示

                                                ImageView imageView =new ImageView(myActivity);//创建图片容器
                                                imageView.setLayoutParams(imageViewLayout);//设置布局
                                                //使用Glide加载头像信息
                                                //设置头像加载失败时的默认头像
                                                //获取头像信息
                                                String photo = user.getAvatarUrl();
                                                String  photoUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + photo;
                                                Glide.with(myActivity)
                                                        .load(photoUrl)
                                                        .apply(headerRO.error(R.drawable.ic_avatar_error))
                                                        .into(imageView);
                                                layout.addView(imageView);//添加到LinearLayout容器
                                                TextView textView=new TextView(myActivity);//创建昵称容器
                                                textView.setText(user.getNickName().trim());//设置昵称
                                                textView.setTextSize(16);//设置字体大小
                                                textView.setGravity(Gravity.CENTER);//居中显示
                                                textView.setPadding(0,20,0,0);//设置内边距
                                                layout.addView(textView);//添加到LinearLayout容器
                                                commonDialog.setView(layout);//设置到弹窗中
                                                commonDialog.show();
                                            }else {
                                                text=jsonObject.getString("text");
                                                Toast.makeText(myActivity,text,Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }else {
                                        Toast.makeText(myActivity,text,Toast.LENGTH_SHORT).show();
                                    }
                                    loadingDialog.hide();//隐藏加载动画
                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText(myActivity,"参数错误",Toast.LENGTH_SHORT).show();
                }

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
        tvInviteCode.setText(inviteCode);//设置用户邀请码
        if (userId>0){
            String url=ServiceUrls.getUserMethodUrl("getTeamNumAndPowerNum");
            Map<String,Object> map=new HashMap<>();
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
                                    String data = jsonObject.getString("data");
                                    JSONObject jsonData=new JSONObject(data);
                                    Type type=new TypeToken<Map<String,Integer>>(){}.getType();
                                    Map<String,Integer> tudi=gson.fromJson(jsonData.getString("tudi"),type);
                                    Map<String,Integer> tusun=gson.fromJson(jsonData.getString("tusun"),type);
                                    rtvTudiNum.setValue(tudi.get("teamNum")==null?"0":tudi.get("teamNum").toString(),"0");
                                    rtvTudiPower.setValue(tudi.get("powerNum")==null?"0":tudi.get("powerNum").toString(),"0");
                                    rtvTusunNum.setValue(tusun.get("teamNum")==null?"0":tusun.get("teamNum").toString(),"0");
                                    rtvTusunPower.setValue(tusun.get("powerNum")==null?"0":tusun.get("powerNum").toString(),"0");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
            });
        }
    }
    /**
     * 设置监听事件
     */
    private void setViewListener() {
        //复制邀请码
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", inviteCode);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(myActivity,"邀请码已复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
        //徒弟
        llTudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, MyTeamActivity.class);
                startActivity(intent);
            }
        });
        //徒孙
        llTusun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity, MyTeamActivity.class);
                intent.putExtra("currentItem",1);//徒孙页
                startActivity(intent);
            }
        });
        //生成邀请链接
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", "https:www.baidu.com");
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(myActivity,"邀请链接已复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
        //生成卡片
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,InviteCardActivity.class);
                startActivity(intent);
                //返回
            }
        });
    }

}
