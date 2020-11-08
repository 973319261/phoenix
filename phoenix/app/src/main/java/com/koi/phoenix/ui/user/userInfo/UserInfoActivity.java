package com.koi.phoenix.ui.user.userInfo;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.bryant.selectorlibrary.DSelectorPopup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koi.phoenix.MyApplication;
import com.koi.phoenix.R;
import com.koi.phoenix.bean.User;
import com.koi.phoenix.dialog.InputDialog;
import com.koi.phoenix.dialog.LoadingDialog;
import com.koi.phoenix.util.DialogUtils;
import com.koi.phoenix.util.GetImagePath;
import com.koi.phoenix.util.ImageUtil;
import com.koi.phoenix.util.KeyBoardUtil;
import com.koi.phoenix.util.MPermissionUtils;
import com.koi.phoenix.util.OkHttpTool;
import com.koi.phoenix.util.PlatformUtil;
import com.koi.phoenix.util.SPUtils;
import com.koi.phoenix.util.ServiceUrls;
import com.koi.phoenix.util.StatusBarUtil;
import com.koi.phoenix.util.Tools;
import com.koi.phoenix.widget.MyActionBar;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人资料页面
 */
public class UserInfoActivity extends AppCompatActivity {
    private Activity myActivity;//上下文
    private MyApplication myApplication;
    private MyActionBar myActionBar;//标题栏
    private ImageView ivAvatar;//头像
    private TextView tvNickName;//昵称
    private TextView tvGender;//性别
    private LinearLayout llAvatar;
    private LinearLayout llNickName;
    private LinearLayout llGender;
    private LinearLayout llQrCode;//二维码
    private int userId;
    private User userInfo;//接收页面参数
    private User user;//修改参数
    private LoadingDialog loadingDialog;
    private RequestOptions headerRO = new RequestOptions().circleCrop();//圆角变换
    private Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static final int IMAGE_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE_GE7 = 101;
    private static final int CAMERA_REQUEST_CODE = 104;
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 200;
    private File mGalleryFile;//存放图库选择是返回的图片
    private File mCropFile;//存放图像裁剪的图片

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myActivity=this;
        myApplication= (MyApplication) myActivity.getApplication();
        setContentView(R.layout.activity_user_info);
        myActionBar = findViewById(R.id.myActionBar);
        ivAvatar=findViewById(R.id.iv_user_avatar);
        tvNickName=findViewById(R.id.tv_user_nickName);
        tvGender=findViewById(R.id.tv_user_gender);
        llAvatar=findViewById(R.id.ll_user_avatar);
        llNickName=findViewById(R.id.ll_user_nickName);
        llGender=findViewById(R.id.ll_user_gender);
        llQrCode=findViewById(R.id.ll_user_qr_code);
        myActionBar.setData("编辑资料", R.drawable.ic_custom_actionbar_left_black, "", 1, 0, new MyActionBar.ActionBarClickListener() {
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

        userId = (int) SPUtils.get(myActivity,SPUtils.SP_USER_ID,0);
        userInfo = (User) getIntent().getSerializableExtra("user");
        String path = myApplication.getCacheDir().getAbsolutePath();
        //相册的File对象
        mGalleryFile = new File(path, "IMAGE_GALLERY_NAME.jpg");
        //裁剪后的File对象
        mCropFile = new File(path, "PHOTO_FILE_NAME.jpg");
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
            tvGender.setText(userInfo.getGender()==0?"男":"女");//设置性别
        }
    }

    /**
     * 监听事件
     */
    private void setViewListener() {
        //头像
        llAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions=new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};//写入存储权限
                if (MPermissionUtils.checkPermissions(myActivity, permissions)) {//检查是否有权限
                    //访问系统图库
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);//设置打开文件的模式 读取
                    intent.setType("image/*");//告诉系统我要获取图片
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //Android7.0及以上
                        Uri uriForFile = FileProvider.getUriForFile(myActivity,
                                "com.koi.phoenix.fileprovider", mGalleryFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //启动页面，设置请求码
                        startActivityForResult(intent, IMAGE_REQUEST_CODE_GE7);
                    } else {
                        //Android7.0一下
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mGalleryFile));
                        startActivityForResult(intent, IMAGE_REQUEST_CODE);
                    }
                }else {//没有权限
                    MPermissionUtils.showTipsDialog(myActivity);//提示
                }
            }
        });
        //昵称
        llNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final InputDialog inputDialog = new InputDialog("请输入昵称",tvNickName.getText().toString(), InputType.TYPE_CLASS_TEXT,15,new InputDialog.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(final Dialog dialog, final String content) {
                        if (!tvNickName.getText().toString().equals(content)){
                            user=new User();
                            user.setUserId(userId);
                            user.setNickName(content);
                            String url= ServiceUrls.getUserMethodUrl("updateUserById");
                            Map<String,Object> map= JSON.parseObject(JSON.toJSONString(user),Map.class);
                            loadingDialog=new LoadingDialog(myActivity,"正在修改中...");
                            loadingDialog.show();
                            OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                                @Override
                                public void onResponse(final boolean isSuccess, final int responseCode, String response, Exception exception) {
                                    myActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (isSuccess && responseCode==200){
                                                tvNickName.setText(content);
                                                userInfo.setNickName(content);//更新传递的信息
                                                if (dialog!=null){
                                                    dialog.dismiss();
                                                }
                                            }else {
                                                Toast.makeText(myActivity,"修改失败，请稍后再试",Toast.LENGTH_LONG).show();
                                            }
                                            loadingDialog.hide();
                                        }
                                    });
                                }
                            });
                        }else {//没有修改
                            Toast.makeText(myActivity,"昵称与原来的一致，不需要修改",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                inputDialog.show(getSupportFragmentManager(),"");

            }
        });
        //性别
        llGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listName=new ArrayList<>();
                listName.add("男");
                listName.add("女");
                final DSelectorPopup dSelectorPopup=new DSelectorPopup(myActivity,listName);
                dSelectorPopup.setTextcolor_unchecked(getResources().getColor(R.color.colorPrimary))
                        .setOffset(userInfo==null?0:userInfo.getGender())//默认选中
                        .setButtonText("确定")
                        .setGradual_color(0xffD81B60)
                        .setTitleText("请选择性别")
                        .setTitleColor(getResources().getColor(R.color.colorPrimary)).build();
                dSelectorPopup.popOutShadow(v);
                dSelectorPopup.setSelectorListener(new DSelectorPopup.SelectorClickListener() {
                    @Override
                    public void onSelectorClick(int position, String text) {
                        int index=text=="男"?0:1;
                        user=new User();
                        user.setUserId(userId);
                        user.setGender(index);
                        String url= ServiceUrls.getUserMethodUrl("updateUserById");
                        Map<String,Object> map= JSON.parseObject(JSON.toJSONString(user),Map.class);
                        loadingDialog=new LoadingDialog(myActivity,"正在修改中...");
                        loadingDialog.show();
                        OkHttpTool.httpPost(url, map, new OkHttpTool.ResponseCallback() {
                            @Override
                            public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                myActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isSuccess && responseCode==200){
                                            userInfo.setGender(user.getGender());
                                            tvGender.setText(user.getGender()==0?"男":"女");
                                        }else {
                                            Toast.makeText(myActivity,"修改失败，请稍后再试",Toast.LENGTH_LONG).show();
                                        }
                                        loadingDialog.hide();
                                    }
                                });
                            }
                        });
                        dSelectorPopup.dismissPopup();
                    }
                });
            }
        });
        //二维码
        llQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(myActivity,QrCodeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",userInfo);//传递用户信息
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 返回图片
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && (data != null || requestCode == CAMERA_REQUEST_CODE)) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE://版本<7.0  图库返回
                    //获取图片的全路径
                    Uri uri = data.getData();
                    //进行图像裁剪
                    startPhotoZoom(uri);
                    break;
                case IMAGE_REQUEST_CODE_GE7://版本>= 7.0 图库返回
                    //获取文件路径
                    String strPath = GetImagePath.getPath(myActivity, data.getData());
                    if (Tools.isNotNull(strPath)) {
                        File imgFile = new File(strPath);
                        //通过FileProvider创建一个content类型的Uri
                        Uri dataUri = FileProvider.getUriForFile(myActivity, "com.koi.phoenix.fileprovider", imgFile);
                        //进行图像裁剪
                        startPhotoZoom(dataUri);
                    } else {
                        Toast.makeText(myActivity, "选择图片失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case UCrop.REQUEST_CROP://Ucrop裁剪返回
                    Uri resultUri = UCrop.getOutput(data);
                    if (resultUri != null) {
                        //uri转文件路径
                        String strPathCrop = GetImagePath.getPath(myActivity, resultUri);
                        if (Tools.isNotNull(strPathCrop)) {
                            File fileUp = new File(strPathCrop);
                            //保存到服务器
                            if (fileUp.exists()) {
                                //=====上传文件
                                String url = ServiceUrls.getUserMethodUrl("uploadUserAvatar");
                                //参数map
                                Map<String, Object> map = new HashMap<>();
                                map.put("userId", userId);
                                //文件map
                                Map<String, File> fileMap = new HashMap<>();
                                fileMap.put("avatar", fileUp);
                                //显示加载层
                                loadingDialog=new LoadingDialog(myActivity,"正在上传中...");
                                loadingDialog.show();
                                //发送请求
                                OkHttpTool.httpPostWithFile(url, map, fileMap, new OkHttpTool.ResponseCallback() {
                                    @Override
                                    public void onResponse(final boolean isSuccess, final int responseCode, final String response, Exception exception) {
                                        myActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                String strText = ServiceUrls.responseText;
                                                if (isSuccess && responseCode == 200) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        int code = jsonObject.getInt("code");
                                                        strText = jsonObject.getString("text");
                                                        if (code == 200) {
                                                            String data=jsonObject.getString("data");
                                                            Type type=new TypeToken<User>(){}.getType();
                                                            User user=gson.fromJson(data,type);
                                                            //获取头像信息
                                                            String photo = user.getAvatarUrl();
                                                            String  photoUrl = ServiceUrls.getUserMethodUrl("getUserAvatar") + "?pictureName=" + photo;
                                                            //使用Glide加载头像信息
                                                            //设置头像加载失败时的默认头像
                                                            Glide.with(myActivity)
                                                                    .load(photoUrl)
                                                                    .apply(headerRO.error(R.drawable.ic_avatar_error))
                                                                    .into(ivAvatar);
                                                            Toast.makeText(myActivity,"上传成功",Toast.LENGTH_SHORT).show();
                                                            userInfo.setAvatarUrl(photo);//更新传递的信息
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                //关闭加载层
                                                loadingDialog.hide();
                                                Toast.makeText(myActivity, strText, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                                return;
                            }
                        }
                    }
                    Toast.makeText(myActivity, "图片裁剪失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    //权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * 开始图片裁剪 使用UCrop
     *
     * @param uri uri地址
     */
    private void startPhotoZoom(Uri uri) {
        //裁剪后保存到文件中
        Uri cropFileUri = Uri.fromFile(mCropFile);
        UCrop uCrop = UCrop.of(uri, cropFileUri);//源文件url,裁剪后输出文件uri
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(false);
        uCrop.withOptions(options);
        //设置比例为1:1
        uCrop.withAspectRatio(1, 1);
        //注意！！！！Fragment中使用uCrop 必须这样，否则Fragment的onActivityResult接收不到回调
        uCrop.start(this);
    }
}
