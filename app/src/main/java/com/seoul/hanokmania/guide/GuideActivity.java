package com.seoul.hanokmania.guide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.seoul.hanokmania.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Choe on 2015-10-23.
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = GuideActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);


        mViewPager = (ViewPager)findViewById(R.id.guide_view_pager);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new GuideFragment_1());
        mFragmentList.add(new GuideFragment_2());

        mViewPager.setAdapter(new GuideAdapter(getSupportFragmentManager(), mFragmentList));



    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("hanokmania", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("first", false).commit();
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
