package com.koi.phoenix.ui.user.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.koi.phoenix.R;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 提现结果页面
 */
public class WithdrawResultActivity extends AppCompatActivity {
    private Activity myActivity;
    private MyActionBar myActionBar;
    private TextView tvMoney;//提现金额
    private TextView tvCommission;//手续费
    private TextView tvAccount;//实际到账
    private TextView tvFinish;//完成
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_withdraw_result);
        tvMoney=findViewById(R.id.tv_withdraw_money);
        tvCommission=findViewById(R.id.tv_withdraw_commission);
        tvAccount=findViewById(R.id.tv_withdraw_account);
        tvFinish=findViewById(R.id.btn_finish);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("提现结果", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
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
        BigDecimal money=new BigDecimal(getIntent().getStringExtra("money"));//获取提现金额
        BigDecimal commission = money.multiply(new BigDecimal("0.03"));//手续费
        tvMoney.setText(String.format(Locale.getDefault(),"%.2f元",money));//
        tvCommission.setText(String.format(Locale.getDefault(),"%.2f元",commission));//
        tvAccount.setText(String.format(Locale.getDefault(),"%.2f元",money.subtract(commission)));//实际到账
    }

    /**
     * 设置监听事件
     */
    private void setViewListener() {
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
