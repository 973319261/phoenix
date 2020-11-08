package com.koi.phoenix.util;

/**
 * 服务器访问路径工具类
 */
public class ServiceUrls {

    public static String responseText="无法连接网络，请稍后再试";
    //private static String serviceUrl="http://192.168.43.170:8080/phoenixServer/";
    //Android虚拟机 AVD访问电脑的地址
    private static String serviceUrl="http://10.0.2.2:8080/phoenixServer/";
    private static String urlPostfix=".do";

    /**
     * 获取 AppUserController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getUserMethodUrl(String method){
        return serviceUrl+"app/user/"+method+urlPostfix;
    }
    /**
     * 获取 AppIncomeController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getIncomeMethodUrl(String method){
        return serviceUrl+"app/income/"+method+urlPostfix;
    }
    /**
     * 获取 AppDividendController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getDividendMethodUrl(String method){
        return serviceUrl+"app/dividend/"+method+urlPostfix;
    }
    /**
     * 获取 AppTaskController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getTaskMethodUrl(String method){
        return serviceUrl+"app/task/"+method+urlPostfix;
    }
    /**
     * 获取 AppWalletController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getWalletMethodUrl(String method){
        return serviceUrl+"app/wallet/"+method+urlPostfix;
    }
    /**
     * 获取 AppIssueController 方法的路径
     * @param method 方法
     * @return url
     */
    public static String getIssueMethodUrl(String method){
        return serviceUrl+"app/issue/"+method+urlPostfix;
    }
}
