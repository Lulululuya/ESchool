package org.jit.sose.eschool.lunbotu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jit.sose.eschool.R;

import java.util.List;


/**
 * Created by hz-pc on 2017/8/23.
 */

public class ImageBarnnerFramLayout extends FrameLayout implements ImageBarnnerViewGroup.ImageBarnnerViewGroupListener,ImageBarnnerViewGroup.ImageBarnnerListener {

    private ImageBarnnerViewGroup imageBarnnerViewGroup;
    private LinearLayout linearLayout;

    private FramLayoutListener listener;

    public FramLayoutListener getListener() {
        return listener;
    }

    public void setListener(FramLayoutListener listener) {
        this.listener = listener;
    }

    public ImageBarnnerFramLayout(@NonNull Context context) {
        super(context);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFramLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public ImageBarnnerFramLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initImageBarnnerViewGroup();
        initDotLinearLayout();
    }

    public void addBitmaps(List<Bitmap> list){
            for(int i=0;i<list.size();i++){
               Bitmap bitmap=list.get(i);
                addBitmapToImageBarnnerViewGroup(bitmap);
                addDotToLinearLayout();
            }
    }

    private void addDotToLinearLayout(){
        ImageView iv = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5,5,5,5);
        iv.setLayoutParams(lp);
        iv.setImageResource(R.drawable.dot_normal);
        linearLayout.addView(iv);
    }

    private void addBitmapToImageBarnnerViewGroup(Bitmap bitmap){
             ImageView iv=new ImageView(getContext());
             iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
             iv.setLayoutParams(new ViewGroup.LayoutParams(ImageBarnnerWidth.width,ViewGroup.LayoutParams.WRAP_CONTENT));
             iv.setImageBitmap(bitmap);
             imageBarnnerViewGroup.addView(iv);
    }


    /**
     * 初始化我们自定义的图片轮播功能的核心类
     */
    private void initImageBarnnerViewGroup(){
        imageBarnnerViewGroup = new ImageBarnnerViewGroup(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageBarnnerViewGroup.setLayoutParams(lp);
        imageBarnnerViewGroup.setBarnnerViewGroupListener(this);  //将listener传递给Framelayout
        imageBarnnerViewGroup.setListener(this);
        addView(imageBarnnerViewGroup);
    }

    /**
     * 初始化底部圆点布局
     */

    private void initDotLinearLayout(){
        linearLayout = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 40);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.GRAY);
        addView(linearLayout);

        LayoutParams layoutParams = (LayoutParams)linearLayout.getLayoutParams();
        layoutParams.gravity= Gravity.BOTTOM;
        linearLayout.setLayoutParams(layoutParams);

        /**
         * 设置透明度
         * 3.0之前和之后，都使用的是setAlpha(),但是调用者不同
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            linearLayout.setAlpha(0.5f);
        }else{
            linearLayout.getBackground().setAlpha(100);
        }
    }

    @Override
    public void selectImage(int index) {
        int count=linearLayout.getChildCount();
        for(int i=0;i<count;i++){
            ImageView iv = (ImageView)linearLayout.getChildAt(i);
            if(i==index){
                iv.setImageResource(R.drawable.dot_select);
            }else{
                iv.setImageResource(R.drawable.dot_normal);
            }
        }
    }

    @Override
    public void clickImageIndex(int pos) {
        listener.clickImageIndex(pos);
    }

    public interface FramLayoutListener{
        void clickImageIndex(int pos);
    }
}
