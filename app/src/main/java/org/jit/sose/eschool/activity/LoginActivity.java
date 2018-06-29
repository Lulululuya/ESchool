package org.jit.sose.eschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.config.Parameter;
import org.jit.sose.eschool.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static org.jit.sose.eschool.config.Parameter.SHAREDPREFERENCE_NAME;

/**
 * Author: chenlu
 * Date: 2018/6/23
 * GITHUB: hhttps://github.com/Lulululuya/ESchool
 * Description: 处理用户登录及注册的activity
 */
@EActivity(R.layout.login)              //绑定到login.xml的视图界面
public class LoginActivity extends Activity {

    //创建上下文的实例
    private Context context = this;

    /*绑定控件*/
    @ViewById(R.id.edit_username)
    EditText usernameEDIT;
    @ViewById(R.id.edit_password)
    EditText passwordEDIT;
    @ViewById(R.id.btn_login)
    Button loginBtn;
    @ViewById(R.id.btn_register)
    Button registerBtn;

    //使用依赖注入创建接口的实例，创建的是UserVice的网络请求的接口
    @RestService
    UserService userservice;


    //点击登录控件所执行的方法
    @Click(R.id.btn_login)
    @Background
    void loginHttp(){
        //获取用户输入的用户名和密码
        String username = usernameEDIT.getText().toString();
        String passwd = passwordEDIT.getText().toString();
        //判断用户名或密码是否为空
        //不为空则执行用户登录的网络请求
        if (username.length()>0 && passwd.length()>0){
            //调用登录的接口，实现用户登录功能，并将方法执行返回的结果赋值给loginResult的的字符串
           /*
           登录成功时loginResult的数据：
           {
                "logResult": "登陆成功",                 //登录的具体结果
                    "logFlag": 1,                       //登录结果的标识
                    "username": "chenlu"
            }*/
            String loginResult = userservice.login(username,passwd);
            try {
                //使用JSONObject的数据类型来解析loginResult（登陆的结果）。
                JSONObject json = new JSONObject(loginResult);
                String logFlag = json.getString(Parameter.LOGFLAG);
                final String logResult = json.getString(Parameter.LOGRESULT);
                //登录成功时候所执行的方法
                if(logFlag.equals(Parameter.LOGINSUCCESSFLAG)) {
                    //将登录成功的用户名存入SharedPreferences中
                    SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Parameter.USERNAME, username); //将登录成功的用户名存入SharedPrefence中
                    editor.putBoolean(Parameter.ISLOG,true);        //将登录成功的标识存入SharedPrefence中
                    editor.commit();
                    finish();
                }else {
                    //登录失败所调用的方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "登录失败，"+logResult, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 当用户名或密码为空，通过吐司来反馈给用户
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                }
            });
        }
}


    //点击注册控件所执行的方法
    @Click(R.id.btn_register)
    @Background
    void registerHttp(){
        //获取用户输入的用户名和密码
        String username = usernameEDIT.getText().toString();
        String passwd = passwordEDIT.getText().toString();
        //判断用户名或密码是否为空
        //不为空则执行用户注册的网络请求
        if (username.length()>0 && passwd.length()>0){
            //调用注册的网络请求的接口，实现用户注册功能，并将注册的结果赋值给registerResult
            /*注册成功时候的registerResult的数据为：
            {
                "regResult": "注册成功",
                    "username": "chenmin"
            }
            */
            String registerResult = userservice.register(username,passwd);
            try {
                //使用JSONObject的数据类型来解析registerResult（注册的结果）。
                JSONObject json = new JSONObject(registerResult);
                final String regResult = json.getString(Parameter.REGRESULT);
                //注册成功所执行的方法
                if(regResult.equals(Parameter.REGISTERSUCCESS)) {
                    //将注册的用户信息存进SharedPreferences中
                    SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Parameter.USERNAME, username);     //将注册成功的用户名存入SharedPrefence中
                    editor.putBoolean(Parameter.ISLOG,true);            //将注册成功的标识存入SharedPrefence中
                    editor.commit();
                    finish();
                }else {
                    //注册失败所执行的方法
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "注册失败，"+regResult, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 当用户名或密码为空，通过吐司来反馈给用户
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}
