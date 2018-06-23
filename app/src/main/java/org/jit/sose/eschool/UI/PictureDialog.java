package org.jit.sose.eschool.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.jit.sose.eschool.R;

/**
 * Author: chenmin
 * Date: 2018/6/23
 * GITHUB: https://github.com/JKchenmin/
 * Description: 实现自定义的Dialog布局
 */
public class PictureDialog extends Dialog implements View.OnClickListener {
    private TextView takephotosTV;
    private TextView titleTxt;
    private TextView photosTV;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    public PictureDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PictureDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public PictureDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected PictureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public PictureDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public PictureDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public PictureDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);
        setCanceledOnTouchOutside(false);
        initView();
    }

    //实例化选择框控件
    private void initView() {
        titleTxt = (TextView) findViewById(R.id.dialog_choosePicture);
        takephotosTV = (TextView) findViewById(R.id.dialog_takephotos);
        takephotosTV.setOnClickListener(this);
        photosTV = (TextView) findViewById(R.id.dialog_photos);
        photosTV.setOnClickListener(this);
        cancelTxt = (TextView) findViewById(R.id.dialog_piccancal);
        cancelTxt.setOnClickListener(this);


        titleTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            photosTV.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_takephotos:
                if (listener != null) {
                    listener.onClick(this, 1);
                }
                break;
            case R.id.dialog_photos:
                if (listener != null) {
                    listener.onClick(this, 2);
                }
                break;
            case R.id.dialog_piccancal:
                if (listener != null) {
                    listener.onClick(this, 3);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, int confirm);
    }
}