package com.koi.phoenix.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.koi.phoenix.R;
import com.koi.phoenix.dialog.MessageDialog;
import com.koi.phoenix.ui.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.0 运行时权限处理工具类。
 */
public class MPermissionUtils {

    private static String TAG=MPermissionUtils.class.getSimpleName();

    private static int mRequestCode = -1;

    public static void requestPermissionsResult(Activity activity, int requestCode
            , String[] permission, OnPermissionListener callback) {
        requestPermissions(activity, requestCode, permission, callback);
    }

    public static void requestPermissionsResult(android.app.Fragment fragment, int requestCode
            , String[] permission, OnPermissionListener callback) {
        requestPermissions(fragment, requestCode, permission, callback);
    }

    public static void requestPermissionsResult(Fragment fragment, int requestCode
            , String[] permission, OnPermissionListener callback) {
        requestPermissions(fragment, requestCode, permission, callback);
    }

    /**
     * 请求权限结果，对应onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == mRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionGranted();
            } else {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionDenied();
            }

            //调试打印授权情况
            Log.d(TAG,"-----------------------------权限授权信息------------------------------------");
            for(int i=0,len=permissions.length;i<len;i++){
                String strText="未知情况";
                switch (grantResults[i]){
                    case 0:
                        strText="授权";
                        break;
                    case 1:
                        strText="被拒绝";
                        break;
                }

                Log.d(TAG,"权限："+permissions[i]+" 的授权情况为:  "+strText);
            }
            Log.d(TAG,"-----------------------------------------------------------------------------");
        }
    }

    /**
     * 是否彻底拒绝了某项权限
     */
    public static boolean hasAlwaysDeniedPermission(@NonNull Context context, @NonNull String... deniedPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false;
        boolean rationale;
        for (String permission : deniedPermissions) {
            rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(context), permission);
            if (!rationale) return true;
        }
        return false;
    }

    /**
     * 显示提示对话框
     */
    public static void showTipsDialog(final Context context) {
        final MessageDialog messageDialog = new MessageDialog(context, R.style.dialog,
                "当前应用缺少必要权限，该功能暂时无法使用。如若需要，请立即前往设置中心进行权限授权。", new MessageDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm==true){//确定按钮
                    startAppSettings(context);
                }
                dialog.dismiss();//关闭弹出框
            }
        });
        messageDialog.setTitle("应用权限");
        messageDialog.setPositiveButton("立即前往");
        messageDialog.show();//显示弹出框
    }


    //=============私有方法===================================

    /**
     * 请求权限处理
     *
     * @param object      activity or fragment
     * @param requestCode 请求码
     * @param permissions 需要请求的权限
     * @param callback    结果回调
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode
            , String[] permissions, OnPermissionListener callback) {

        checkCallingObjectSuitability(object);
        mOnPermissionListener = callback;

        if (checkPermissions(getContext(object), permissions)) {
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted();
        } else {
            List<String> deniedPermissions = getDeniedPermissions(getContext(object), permissions);
            int len = deniedPermissions.size();
            if (len > 0) {
                mRequestCode = requestCode;
                String[] strDeniedPermissions = deniedPermissions.toArray(new String[len]);
                if (object instanceof Activity) {
                    ((Activity) object).requestPermissions(strDeniedPermissions, requestCode);
                } else if (object instanceof Fragment) {
                    ((Fragment) object).requestPermissions(strDeniedPermissions, requestCode);
                } else if (object instanceof android.app.Fragment) {
                    ((android.app.Fragment) object).requestPermissions(strDeniedPermissions, requestCode);
                }
            }
        }
    }

    /**
     * 获取上下文
     */
    private static Context getContext(Object object) {
        Context context;
        if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        } else {
            context = (Activity) object;
        }
        return context;
    }


    /**
     * 启动当前应用设置页面
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    /**
     * 验证权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        // 如果请求被取消，则结果数组为空
        if (grantResults.length <= 0)
            return false;

        // 循环判断每个权限是否被拒绝
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限列表中所有需要授权的权限
     *
     * @param context     上下文
     * @param permissions 权限列表
     * @return 需要授权的权限列表
     */
    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 检查所传递对象的正确性
     *
     * @param object 必须为 activity or fragment
     */
    private static void checkCallingObjectSuitability(Object object) {
        if (object == null) {
            throw new NullPointerException("Activity or Fragment should not be null");
        }

        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;

        if (!(isActivity || isSupportFragment || isAppFragment)) {
            throw new IllegalArgumentException(
                    "Caller must be an Activity or a Fragment");
        }
    }

    /**
     * 检查所有的权限是否已经被授权
     *
     * @param permissions 权限列表
     * @return 所有的权限是否已经被授权
     */
    public static boolean checkPermissions(Context context, String... permissions) {
        if (isOverMarshmallow()) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断当前手机API版本是否 >= 6.0
     */
    private static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public interface OnPermissionListener {
        void onPermissionGranted();

        void onPermissionDenied();
    }

    private static OnPermissionListener mOnPermissionListener;
}
