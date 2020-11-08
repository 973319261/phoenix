package com.koi.phoenix.ui.user.userInfo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.WriterException;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.User;
import com.koi.phoenix.util.ImageUtil;
import com.koi.phoenix.util.QrCodeUtil;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

/**
 * 我的二维码页面
 */
public class QrCodeActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyActionBar myActionBar;//标题栏
    private ImageView ivAvatar;//头像
    private TextView tvNickName;//昵称
    private ImageView ivQrCode;//二维码
    private User userInfo;//接收页面传递的参数
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        setContentView(R.layout.activity_qr_code);
        myActionBar = findViewById(R.id.myActionBar);
        ivAvatar=findViewById(R.id.iv_user_avatar);
        tvNickName=findViewById(R.id.tv_user_nickName);
        ivQrCode=findViewById(R.id.iv_user_qrCode);
        myActionBar.setData("我的二维码", R.drawable.ic_custom_actionbar_left, "", 0, 0, new MyActionBar.ActionBarClickListener() {
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
        userInfo = (User) getIntent().getSerializableExtra("user");
        String inviteCode=(String) SPUtils.get(myActivity,SPUtils.SP_USER_INVITE_CODE,""); //获取邀请码
        try {
            Bitmap qrCode = QrCodeUtil.createCode("https://www.baidu.com/"+inviteCode, 1000,ImageUtil.drawableToBitmap(myActivity,R.drawable.ic_wechat));//生成二维码
            ivQrCode.setImageBitmap(qrCode);//设置二维码
        } catch (WriterException e) {
            e.printStackTrace();
        }

        if (userInfo!=null){
            //获取头像信息
            String photo = userInfo.getAvatarUrl();
            String  photoUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + photo;
            //使用Glide加载头像信息
            //设置头像加载失败时的默认头像
            Glide.with(this)
                    .load(photoUrl)
                    .apply(headerRO.error(R.drawable.ic_avatar_error))
                    .into(ivAvatar);
            tvNickName.setText(userInfo.getNickName().trim());//设置昵称
        }
    }

    /**
     * 设置监听事件
     */
    private void setViewListener() {
    }
}
