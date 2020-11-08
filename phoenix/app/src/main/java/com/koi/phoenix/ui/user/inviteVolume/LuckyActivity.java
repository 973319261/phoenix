package com.koi.phoenix.ui.user.inviteVolume;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.IssueVo;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

import java.text.SimpleDateFormat;

/**
 * 幸运儿页面
 */
public class LuckyActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局
    private TextView tvAbortTime;//截止时间
    private TextView tvLotteryTime;//开奖时间
    private TextView tvBlockNumber;//区块号
    private TextView tvLuckNumber;//幸运号码
    private TextView tvBetNum;//本期参与数
    private TextView tvBlock;//区块号（计算）
    private TextView tvBet;//参与人数（计算）
    private TextView tvLuck;//幸运号码（计算）
    private MyActionBar myActionBar;//标题栏
    private IssueVo issueVoInfo;//期号信息
    private SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_lucky);
        tvAbortTime = findViewById(R.id.tv_lucky_abort_time);
        tvLotteryTime = findViewById(R.id.tv_lucky_lottery_time);
        tvBlockNumber = findViewById(R.id.tv_lucky_block_number);
        tvLuckNumber = findViewById(R.id.tv_lucky_luck_number);
        tvBetNum = findViewById(R.id.tv_lucky_bet_num);
        tvBlock=findViewById(R.id.tv_block_number);
        tvBet=findViewById(R.id.tv_yet_bet_num);
        tvLuck=findViewById(R.id.tv_luck_number);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("中奖码", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
    }
    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        issueVoInfo = (IssueVo) getIntent().getSerializableExtra("issueVo");
        tvAbortTime.setText(sf.format(issueVoInfo.getAbortTime()));
        tvLotteryTime.setText(sf.format(issueVoInfo.getLotteryTime()));
        tvBlockNumber.setText(String.valueOf(issueVoInfo.getBlockNumber()));
        tvLuckNumber.setText(String.valueOf(issueVoInfo.getLuckNumber()));
        tvBetNum.setText(String.valueOf(issueVoInfo.getYetBetNum()));
        tvBlock.setText(String.valueOf(issueVoInfo.getBlockNumber()));
        tvBet.setText(String.valueOf(issueVoInfo.getYetBetNum()));
        tvLuck.setText(String.valueOf(issueVoInfo.getLuckNumber()));
    }
}
