package org.jit.sose.eschool.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.lunbotu.ImageBarnnerFramLayout;
import org.jit.sose.eschool.lunbotu.ImageBarnnerWidth;

import java.util.ArrayList;
import java.util.List;


@EFragment(R.layout.schoolfragment)
public class SchoolFragment extends Fragment implements ImageBarnnerFramLayout.FramLayoutListener {

    private static final String TAG = "SchoolFragment";

    private int[] ids = new int[]{          //轮播图图片资源
            R.drawable.pic7,
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3
    };

    //顶部图片轮播布局控件
    @ViewById(R.id.image_group)
    ImageBarnnerFramLayout mGroup;

    @AfterViews
    void showSchool(){


        initsideShow();     // 初始化顶部的轮播图
    }



    //  初始化首页的图片轮播
    private void initsideShow() {
        Log.i(TAG, "initsideShow: 进入轮播图方法");
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        ImageBarnnerWidth.width= dm.widthPixels;
        mGroup.setListener(this);
        List<Bitmap> list = new ArrayList<>();
        for(int i=0;i<ids.length;i++) {
            Log.i(TAG, "initsideShow: 当前是第几张图片："+i);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),ids[i]);
            list.add(bitmap);
        }
        mGroup.addBitmaps(list);
    }


    //点击轮播图片调用的方法
    @Override
    public void clickImageIndex(int pos) {
        Log.i(TAG, "clickImageIndex: 点击的是第几张图片："+pos);
    }




}
