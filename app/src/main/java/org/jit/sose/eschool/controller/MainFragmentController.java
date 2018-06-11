package org.jit.sose.eschool.controller;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import org.jit.sose.eschool.fragment.CourseFragment;
import org.jit.sose.eschool.fragment.LeaveFragment_;
import org.jit.sose.eschool.fragment.PersonalFragment_;
import org.jit.sose.eschool.fragment.SchoolFragment_;

import java.util.ArrayList;


public class MainFragmentController {

    private ArrayList<Fragment> fragments;
    private int containerId;
    private android.support.v4.app.FragmentManager fm;

    private static MainFragmentController mainFragmentController;

    public static MainFragmentController getInstance(FragmentActivity activity, int containerId) {
        if (mainFragmentController == null) {
            mainFragmentController = new MainFragmentController(activity, containerId);
        }
        return mainFragmentController;
    }

    private MainFragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new SchoolFragment_());
        fragments.add(new CourseFragment());
        fragments.add(new LeaveFragment_());
        fragments.add(new PersonalFragment_());

        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public static void destoryController(){
        mainFragmentController = null;
    }

}
