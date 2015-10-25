package com.seoul.hanokmania.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.query.QueryContract;

import org.achartengine.GraphicalView;

/**
 * Created by junsuk on 2015. 10. 22..
 *
 * 차트 상세 보기
 */
public class ChartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart);


        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootLayout);

        // TODO: 차트 정보 받아서 그려주도록 수정
        // 차트 프로토타입
//        Sequel aQuery = new Sequel(this);
//        HanokGraphQuery hanokGraphQuery = new HanokGraphQuery(aQuery);
//        Footman footman = new Footman();
//        footman.takeQuery(hanokGraphQuery);
//        List list= footman.placeQueries();
//
//        HanokBarChart chart= new HanokBarChart();
//        GraphicalView graphicalView = chart.getGraphView(this, list);

        GraphicalView graphicalView= QueryContract.mChartView;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            graphicalView.setTransitionName("chart");
        }

        // If the specified child already has a parent,
        // You must call removeView() on the child's parent first
        if(graphicalView.getParent()!= null) {
            ((ViewGroup) graphicalView.getParent()).removeAllViews();
        }

        rootLayout.addView(graphicalView);
    }

}
