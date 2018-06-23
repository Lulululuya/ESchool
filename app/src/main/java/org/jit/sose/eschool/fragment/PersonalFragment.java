package org.jit.sose.eschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.activity.LoginActivity_;
import org.jit.sose.eschool.activity.PersonalActivity_;
import org.jit.sose.eschool.config.Parameter;

import static org.jit.sose.eschool.config.Parameter.SHAREDPREFERENCE_NAME;

/**
 * Author: chenmin
 * Date: 2018/6/23
 * GITHUB: https://github.com/JKchenmin/
 * Description: 用户个人中心的Fragment
 */
@EFragment(R.layout.personfragment)
public class PersonalFragment extends Fragment {


    //绑定控件
    @ViewById(R.id.LineLayout_login)
    LinearLayout loginLayout;
    @ViewById(R.id.tv_userName)
    TextView userNameTV;

    //当Fragment加载完到界面上时所执行的方法
    @AfterViews
    void showMy(){
        showUser();
    }


    //点击登录控件跳转到登录界面,如果已经登录则跳转到个人中心的详情界面
    @Click(R.id.LineLayout_login)
    void goLoin(){
        if(!islog()) {
            Intent intent = new Intent(getActivity(), LoginActivity_.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), PersonalActivity_.class);
            startActivity(intent);
        }
    }



    //获得用户是否登录的标识
    boolean islog(){
        SharedPreferences preferences=getActivity().getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String username=preferences.getString(Parameter.USERNAME, "点击登录");
        boolean islog = preferences.getBoolean(Parameter.ISLOG,false);
        return islog;
    }

    //获得用户登录的用户名
    public String getUserName(){
        SharedPreferences preferences=getActivity().getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String username=preferences.getString(Parameter.USERNAME, "点击登录");
        return username;
    }

    //展示用户的信息
    private void showUser(){
        SharedPreferences preferences=getActivity().getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String username=preferences.getString(Parameter.USERNAME, "点击登录");
        boolean islog = preferences.getBoolean(Parameter.ISLOG,false);
        if (islog){
            userNameTV.setText(username);
        }
    }

    //用户登录之后返回的方法
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences=getActivity().getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
        String username=preferences.getString(Parameter.USERNAME, "登录吗");
        boolean islog = preferences.getBoolean(Parameter.ISLOG,false);
        if (islog){
            userNameTV.setText(username);
        }
        if (!islog){
            userNameTV.setText("点击登录");
        }
    }

}
