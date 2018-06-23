package org.jit.sose.eschool.activity;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.controller.MainFragmentController;


@EActivity(R.layout.main)
public class MainActivity extends AppCompatActivity {


    // 实例化fragmentcontroller（fragment的管理类）
    private MainFragmentController mainFragmentController;

    //private AbTitleBar mAbtitleBar;         // 顶部标题栏控件
    //private AbBottomBar mAbBottomBar;       // 底部标题栏控件

    //private RadioGroup rb_bottomBar;



    private static final String TAG = "MainActivity";

    @ViewById(R.id.rg_bottomBar)
    RadioGroup rb_bottomBar;



    @AfterViews
    void showMain(){

        //initWidget();                                 //绑定控件
        //initMainTitle();                              //初始化顶部标题栏
        initFragment();                                 //初始化底部导航栏的fragment
    }

    void initWidget(){
        rb_bottomBar = (RadioGroup) findViewById(R.id.rg_bottomBar);
    }

    //初始化主标题栏
/*    void initMainTitle(){
        mAbtitleBar = this.getTitleBar();
        mAbtitleBar.setTitleText(R.string.app_name);
        mAbtitleBar.setLogo(R.drawable.button_selector_back);
        mAbtitleBar.setTitleBarBackground(R.drawable.top_bg);
        mAbtitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbtitleBar.setLogoLine(R.drawable.line);
    }*/

    //初始化副标题栏
/*    void initBottomBar(){
        mAbBottomBar = this.getBottomBar();
        mAbBottomBar.setVisibility(View.VISIBLE);
        View view = mInflater.inflate(R.layout.bottom_bar, null);
        Button searchBtn = (Button) view.findViewById(R.id.rb_leave);
        Button selectBtn = (Button) view.findViewById(R.id.rb_person);
        mAbBottomBar.setBottomView(view);
    }*/

    //初始化底部导航栏显示的按钮（通过RadioGroup来绑定Fragment）
    void initFragment(){
        mainFragmentController = MainFragmentController.getInstance(this, R.id.content);
        mainFragmentController.showFragment(0);
        rb_bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_school:{
                        mainFragmentController.showFragment(0);
                        break;
                    }
                    case R.id.rb_course:{
                        mainFragmentController.showFragment(1);
                        break;
                    }
                    case R.id.rb_leave:{
                        mainFragmentController.showFragment(2);
                        Log.i(TAG, "onCheckedChanged: 点击请假");
                        break;
                    }
                    case R.id.rb_person:{
                        mainFragmentController.showFragment(3);
                        Log.i(TAG, "onCheckedChanged: 点击个人中心");
                        break;
                    }

                }
            }
        });
    }





    //解决fragment残留的视图问题
    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());         //获取PID
        System.exit(0);
    }


}
