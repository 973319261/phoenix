package com.koi.phoenix.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.koi.phoenix.R;
import com.koi.phoenix.bean.VideoRecord;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.widget.MultiLineRadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 抽奖弹窗
 */
public class LotteryDialog extends Dialog {
    private Activity myActivity;
    private ImageView ivClose;//关闭
    private TextView tvInviteNum;//剩余邀请卷
    private TextView tvParticipation;//参与
    private MultiLineRadioGroup rgCheck;//选择邀请卷
    private VideoRecord videoRecord;//视频观看记录
    private int inviteNum;//邀请卷数量
    private int issueId;//期号ID
    private int userId;//用户ID
    public LotteryDialog(@NonNull Context context,int inviteNum,VideoRecord videoRecord,int issueId,int userId) {
        super(context, R.style.dialog);
        this.myActivity= (Activity) context;
        this.inviteNum=inviteNum;
        this.videoRecord=videoRecord;
        this.issueId=issueId;
        this.userId=userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lottery);
        ivClose = findViewById(R.id.close);
        tvInviteNum = findViewById(R.id.tv_invite_num);
        tvParticipation = findViewById(R.id.tv_participation);
        rgCheck=findViewById(R.id.rg_invite_check);
        setCanceledOnTouchOutside(false);//禁止点击外部关闭
        initView();
        setViewListener();//监听事件
    }

    /**
     * 初始化
     */
    private void initView() {
        tvInviteNum.setText(String.format(Locale.getDefault(),"剩余邀请卷：%d张",inviteNum));//设置邀请卷数量
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
        rgCheck.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultiLineRadioGroup group, int checkedId) {
                tvParticipation.setEnabled(true);//开启按钮
            }
        });
        //立即参与
        tvParticipation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rgCheck.getCheckedRadioButtonId() > 0){//选中
                    RadioButton radioButton=findViewById(rgCheck.getCheckedRadioButtonId());//获取选中的按钮
                    int num=Integer.valueOf(radioButton.getText().toString().replace("张",""));//获取数量
                    if (inviteNum>=num){
                        if (videoRecord.getVideoRecordNum()>=5){//观看5次视频即可参与
                            String url = ServiceUrls.getIssueMethodUrl("addBet");//投注
                            Map<String,Object> map =new HashMap<>();
                            map.put("issueId",issueId);//期号ID
                            map.put("userId",userId);//投注用户
                            map.put("betNum",num);//投注数
                            map.put("inviteNum",inviteNum);//用有的邀请卷
                            final LoadingDialog loadingDialog=new LoadingDialog(myActivity,"正在下注中...");//加载框
                            loadingDialog.show();//显示
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
                                                        dismiss();//先关闭当前弹窗
                                                        DialogUtils.dialog(myActivity,"温馨提示","恭喜您下注成功，祝你好运！","");
                                                    }else {
                                                        text=jsonObject.getString("text");
                                                        dismiss();//先关闭当前弹窗
                                                        DialogUtils.dialog(myActivity,"温馨提示",text,"");
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }else {
                                                Toast.makeText(myActivity,text,Toast.LENGTH_SHORT).show();
                                            }
                                            loadingDialog.hide();//隐藏
                                        }
                                    });
                                }
                            });
                        }else {
                            dismiss();//先关闭当前弹窗
                            DialogUtils.dialog(myActivity,"温馨提示","每天观看5次视频即可参与","");
                        }
                    }else {
                        Toast.makeText(myActivity,"邀请卷数量不足，无法完成下注",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
