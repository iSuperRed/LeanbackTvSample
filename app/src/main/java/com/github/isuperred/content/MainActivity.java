package com.github.isuperred.content;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.widget.HorizontalGridView;
import androidx.viewpager.widget.ViewPager;

import com.github.isuperred.R;
import com.github.isuperred.title.Title;
import com.github.isuperred.utils.LocalJsonResolutionUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private HorizontalGridView mHorizontalGridView;
    private ViewPager mViewPager;

    private void initView() {
        mHorizontalGridView = findViewById(R.id.hg_title);
        mViewPager = findViewById(R.id.vp_content);
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName = "title.json";
                String titleJson = LocalJsonResolutionUtil.getJson(MainActivity.this, fileName);
                Log.e(TAG, "run: " + titleJson);
                //转换为对象
                Title title = LocalJsonResolutionUtil.JsonToObject(titleJson, Title.class);
                List<Title.DataBean> dataBeans = title.getData();
                for (int i = 0; i < dataBeans.size(); i++) {
//                    dataBeans.get(i).getName();
                    Log.e(TAG, "run: " + dataBeans.get(i).getName());
                }
            }
        }).start();
    }

}