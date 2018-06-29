package org.jit.sose.eschool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.config.Parameter;

import static org.jit.sose.eschool.config.Parameter.SHAREDPREFERENCE_NAME;


/**
 * Author: chenlu
 * Date: 2018/6/23
 * GITHUB: https://github.com/Lulululuya/ESchool
 * Description: 用户个人中心详情的Activity
 */
@EActivity(R.layout.personality)
public class PersonalActivity extends Activity{

    //绑定控件
    @ViewById(R.id.tv_userNmaeDetail)
    TextView userNameTV;
    @ViewById(R.id.tv_userEmail)
    TextView userEmailTV;
    @ViewById(R.id.btn_exit)
    Button exitBtn;

    //当前界面加载成功所执行的方法
    @AfterViews
    void init(){

        showUser();             //显示用户个人信息
    }

    //显示个人信息
    void showUser(){
        //从SharedPreferences获取用户名
        SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String username=preferences.getString(Parameter.USERNAME, "点击登录");
        //加用户名渲染到userNameTV的控件上
        userNameTV.setText(username);
    }

    //点击个人中心的退出登录按钮，将SharedPreference里面的登录标识修改为false
    @Click(R.id.btn_exit)
    void exit(){
        SharedPreferences preferences=getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        Editor editor=preferences.edit();
        editor.putBoolean(Parameter.ISLOG,false);
        editor.commit();
        finish();
    }

}
