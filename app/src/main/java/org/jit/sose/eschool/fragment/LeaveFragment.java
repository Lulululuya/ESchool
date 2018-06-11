package org.jit.sose.eschool.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.jit.sose.eschool.R;
import org.jit.sose.eschool.UI.PictureDialog;
import org.jit.sose.eschool.global.Config;

import java.util.Calendar;

@EFragment(R.layout.leavefragment)
public class LeaveFragment extends Fragment {

    private String pz_time;
    private String[] leaveKinds = {"病假","事假","其他"};

    @ViewById(R.id.spin_leaveKind)
    Spinner leaveSpinner;
    @ViewById(R.id.dateP_begin)
    DatePicker beginDate;
    @ViewById(R.id.dateP_end)
    DatePicker endDate;
    @ViewById(R.id.iv_photo)
    ImageView photoIV;

    @AfterViews
    void show(){

        fillLeaverKindSpinner();
    }

    //初始化绑定控件
    void initWidget(View view){
        leaveSpinner = (Spinner) view.findViewById(R.id.spin_leaveKind);
    }

    //填补请假类型下拉框的数据
    void fillLeaverKindSpinner(){
        ArrayAdapter<String> adapterSP = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,leaveKinds);
        adapterSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveSpinner.setAdapter(adapterSP);
        leaveSpinner.setSelection(2);
    }

    //点击显示请假开始时间
    @Click(R.id.lay_dateBegin)
    void showDateBegin(){
        //beginDate.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //getActivity().mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    //点击显示结束时间
    @Click(R.id.lay_dateEnd)
    void showEndDate(){
        //endDate.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //getActivity().mEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Click(R.id.iv_photo)
    void takePhoto(){
        new PictureDialog(getActivity(), R.style.dialog, "", new PictureDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, int confirm) {
                if (confirm == 1) {
                    // 启动相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Bundle bundle = new Bundle();
                    /*pz_time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    File photoFile = createImgFile(pz_time);
                    bundle.putString("time",pz_time);
                    photoUri = Uri.fromFile(photoFile);
                    intent.putExtras(bundle);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);*/
                    startActivityForResult(intent, Config.TAKE_PHOTO);
                    dialog.dismiss();
                } else if (confirm == 2) {
                    //获取手机相册里面的图片
                    Intent intentFromAlbum = new Intent(Intent.ACTION_GET_CONTENT);         //ACTION_GET_CONTENT
                    intentFromAlbum.setType("image/*");                                     // 设置文件类型
                    startActivityForResult(intentFromAlbum, Config.PICK_PHOTO);
                    dialog.dismiss();
                }
            }
        }).setTitle(getString(R.string.choosepictureStr)).show();
    }

}
