package com.koi.phoenix.ui.user.invite;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.zxing.WriterException;
import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.adapter.CardAdapter;
import com.koi.phoenix.util.ImageUtil;
import com.koi.phoenix.util.MPermissionUtils;
import com.koi.phoenix.util.PlatformUtil;
import com.koi.phoenix.util.QrCodeUtil;
import com.koi.phoenix.util.ScaleTransformer;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.widget.MyActionBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 邀请卡页面
 */
public class InviteCardActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;//全局变量
    private MyActionBar myActionBar;//标题栏
    private ViewPager viewPager;//切换页面（卡片图片）
    private LinearLayout llWeiXin;//微信好友
    private LinearLayout llFriend;//朋友圈
    private LinearLayout llSave;//保存图片
    private List<Bitmap> list = new ArrayList<>();//图片集合
    private  Bitmap qrCode;//邀请二维码
    private int current;//当前卡片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) getApplication();
        setContentView(R.layout.activity_invite_card);
        llWeiXin = findViewById(R.id.ll_invite_card_weixin);
        llFriend = findViewById(R.id.ll_invite_card_friend);
        llSave = findViewById(R.id.ll_invite_card_save);
        viewPager = findViewById(R.id.viewpager);
        myActionBar = findViewById(R.id.myActionBar);
        myActionBar.setData("分享好友", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        initView();//初始化页面
        setContextListener();//设置监听事件
    }


    /**
     * 初始化页面
     */
    private void initView() {
        StatusBarUtil.setStatusBar(myActivity,true);//设置当前界面是否是全屏模式（状态栏）
        StatusBarUtil.setStatusBarLightMode(myActivity,true);//状态栏文字颜色
        try {
            qrCode = QrCodeUtil.createCode("https://www.baidu.com/", 100,ImageUtil.drawableToBitmap(myActivity,R.drawable.ic_wechat));//生成二维码
        } catch (WriterException e) {
            e.printStackTrace();
        }
        loadCardImage();//加载卡片
    }

    /**
     * 设置监听事件
     */
    private void setContextListener() {
        //微信好友
        llWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                    File f = ImageUtil.saveImageToGallery(myActivity, list.get(current),false);//保存当前图片到图库
                    PlatformUtil.shareWechatFriend(myActivity, f);//分享图片到微信好友
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(myActivity);//提示
                }
            }
        });
        //朋友圈
        llFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                    File f = ImageUtil.saveImageToGallery(myActivity, list.get(current),false);//保存当前图片到图库
                    PlatformUtil.shareWechatMoment(myActivity, "",f);//分享图片到微信好友
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(myActivity);//提示
                }
            }
        });
        //保存图片
        llSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                    ImageUtil.saveImageToGallery(myActivity, list.get(current),true);//保存当前图片到图库
                    Toast.makeText(myActivity,"图片保存成功",Toast.LENGTH_LONG).show();
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(myActivity);//提示
                }
            }
        });
        //viewPager滑动事件  获取当前卡片current
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                current=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 加载卡片
     */
    private void loadCardImage()  {
        viewPager.setPageMargin(-200);//卡片之间间距
        viewPager.setOffscreenPageLimit(3);//显示多少个卡片
        //添加图片
        list.add(ImageUtil.compoundBitmap(ImageUtil.drawableToBitmap(myActivity,R.drawable.ic_0),qrCode));
        list.add(ImageUtil.compoundBitmap(ImageUtil.drawableToBitmap(myActivity,R.drawable.ic_1),qrCode));
        list.add(ImageUtil.compoundBitmap(ImageUtil.drawableToBitmap(myActivity,R.drawable.ic_2),qrCode));
        CardAdapter adapter = new CardAdapter(myActivity, list);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false,new ScaleTransformer());
    }

}
