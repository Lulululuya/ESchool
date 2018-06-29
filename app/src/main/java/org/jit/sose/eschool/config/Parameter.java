package org.jit.sose.eschool.config;

/**
 * Author: chenmin
 * Date: 2018/6/23
 * GITHUB: https://github.com/JKchenmin/
 * Description: 参数管理的常量类
 */

public class Parameter {

    public static String SHAREDPREFERENCE_NAME = "loginStatus_data";    //SharedPrefence的key

    public static String USERNAME = "username";     //用户名参数



    public static String LOGRESULT = "logResult";   //用户登录的网络请求的结果
    public static String LOGFLAG = "logFlag";       //用户登录的网络请求的标识
    public static String ISLOG = "islog";           //登录标识
    public static String LOGINSUCCESSFLAG = "1";    //登录成功的标识为1
    public static String REGRESULT = "regResult";   //用户登录的网络请求的标识



    public static String LOGINSUCCESS = "登录成功";
    public static String REGISTERSUCCESS = "注册成功";

    public static final int TAKE_PHOTO = 1;         // 请求缩略图信号标识
    public static final int REQUEST_ORIGINPIC = 2;  // 请求原图信号标识
    public static final int PICK_PHOTO = 3;      // 请求相册图片信号标识


}
