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
 * Author: chenmin
 * Date: 2018/6/23
 * GITHUB: https://github.com/JKchenmin/
 * Description: 处理用户登录及注册的activity
 */
@EActivity(R.layout.login)
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

    //使用依赖注入创建接口的实例
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
            //调用登录的接口，实现用户登录功能
            String loginResult = userservice.login(username,passwd);
            try {
                JSONObject json = new JSONObject(loginResult);
                String logFlag = json.getString(Parameter.LOGFLAG);
                final String logResult = json.getString(Parameter.LOGRESULT);
                if(logFlag.equals(Parameter.LOGINSUCCESSFLAG)) {
                    SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Parameter.USERNAME, username); //将登录成功的用户名存入SharedPrefence中
                    editor.putBoolean(Parameter.ISLOG,true);        //将登录成功的标识存入SharedPrefence中
                    editor.commit();
                    finish();
                }else {
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
            //调用登录的接口，实现用户登录功能
            String registerResult = userservice.register(username,passwd);
            try {
                JSONObject json = new JSONObject(registerResult);
                final String regResult = json.getString(Parameter.REGRESULT);
                if(regResult.equals(Parameter.REGISTERSUCCESS)) {
                    SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString(Parameter.USERNAME, username);     //将注册成功的用户名存入SharedPrefence中
                    editor.putBoolean(Parameter.ISLOG,true);            //将注册成功的标识存入SharedPrefence中
                    editor.commit();
                    finish();
                }else {
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
