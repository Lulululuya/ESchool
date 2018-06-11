package org.jit.sose.eschool.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import org.jit.sose.eschool.R;
import org.jit.sose.eschool.course.AbsGridAdapter;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends Fragment {

    private static final String TAG = "CourseFragment";
    private Spinner spinner;
    private Spinner yearSpiner;

    private GridView detailCource;

    private String[][] contents;

    private AbsGridAdapter secondAdapter;

    private List<String> dataList;
    private List<String> yearList;

    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> spinnerYearAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: 进入课程表的界面");
        View view = inflater.inflate(R.layout.course, container, false);

        initCourse(view);
        return view;
    }

    void initCourse(View view){
        spinner = (Spinner)view.findViewById(R.id.switchWeek);
        yearSpiner = (Spinner) view.findViewById(R.id.switchYear);
        detailCource = (GridView)view.findViewById(R.id.courceDetail);
        ///////////////第一种方式创建Adapater
//        List<String> list = init();
//        adapter = new MyAdapter(this, list);
//        detailCource.setAdapter(adapter);
        ///////////////第二种方式创建Adapter
        fillStringArray();
        secondAdapter = new AbsGridAdapter(getActivity());
        secondAdapter.setContent(contents, 6, 7);
        detailCource.setAdapter(secondAdapter);
        //////////////创建Spinner数据
        fillDataList();
        fillYearList();
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, dataList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinnerYearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearList);
        spinnerYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpiner.setAdapter(spinnerYearAdapter);

        yearSpiner.setSelection(2);
    }

    /**
     * 准备数据
     */
    private List<String> init() {
        List<String> list = new ArrayList<String>();
        list.add("现代测试技术B211");
        list.add("数据结构与算法B211");
        list.add("微机原理及应用E203");
        list.add("面向对象程序设计A309");
        list.add("数据结构与算法B207");
        list.add("");
        list.add("");
        list.add("微机原理及应用E203");
        list.add("");
        list.add("电磁场理论A212");
        list.add("传感器电子测量A\nC309");
        list.add("微机原理及应用E203");
        list.add("");
        list.add("");
        list.add("电磁场理论A212");
        list.add("面向对象程序设计A309");
        list.add("现代测试技术B211");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("传感器电子测量A\nC309");
        list.add("面向对象程序设计A309");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        return list;
    }


    public void fillStringArray() {
        contents = new String[6][7];
        contents[0][0] = "现代测试技术\nB211";
        contents[1][0] = "微机原理及应用\nE203";
        contents[2][0] = "电磁场理论\nA212";
        contents[3][0] = "传感器电子测量A\nC309";
        contents[4][0] = "";
        contents[5][0] = "";
        contents[0][1] = "数据结构与算法\nB211";
        contents[1][1] = "";
        contents[2][1] = "面向对象程序设计\nA309";
        contents[3][1] = "面向对象程序设计\nA309";
        contents[4][1] = "";
        contents[5][1] = "";
        contents[0][2] = "微机原理及应用\nE203";
        contents[1][2] = "电磁场理论\nA212";
        contents[2][2] = "现代测试技术\nB211";
        contents[3][2] = "";
        contents[4][2] = "";
        contents[5][2] = "";
        contents[0][3] = "面向对象程序设计\nA309";
        contents[1][3] = "传感器电子测量A\nC309";
        contents[2][3] = "";
        contents[3][3] = "";
        contents[4][3] = "";
        contents[5][3] = "";
        contents[0][4] = "数据结构与算法\nB211";
        contents[1][4] = "微机原理及应用\nE203";
        contents[2][4] = "";
        contents[3][4] = "";
        contents[4][4] = "";
        contents[5][4] = "";
        contents[0][5] = "";
        contents[1][5] = "";
        contents[2][5] = "";
        contents[3][5] = "";
        contents[4][5] = "";
        contents[5][5] = "";
        contents[0][6] = "";
        contents[1][6] = "";
        contents[2][6] = "";
        contents[3][6] = "";
        contents[4][6] = "";
        contents[5][6] = "测试基础万盛道";
    }

    public void fillDataList() {
        dataList = new ArrayList<>();
        for(int i = 1; i < 21; i++) {
            dataList.add("第" + i + "周");
        }
    }

    public void fillYearList() {
        yearList = new ArrayList<>();
        for(int i = 2015; i <= 2017; i++) {
            yearList.add(i + "年");
        }
    }
}
