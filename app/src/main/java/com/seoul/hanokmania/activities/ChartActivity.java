package com.seoul.hanokmania.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.fragments.graph.HanokBarChart;
import com.seoul.hanokmania.query.Footman;
import com.seoul.hanokmania.query.HanokGraphQuery;
import com.seoul.hanokmania.query.Sequel;

import org.achartengine.GraphicalView;

import java.util.List;

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
        Sequel aQuery = new Sequel(this);
        HanokGraphQuery hanokGraphQuery = new HanokGraphQuery(aQuery);
        Footman footman = new Footman();
        footman.takeQuery(hanokGraphQuery);
        List list= footman.placeQueries();
        HanokBarChart chart= new HanokBarChart();
        GraphicalView graphicalView = chart.getGraphView(this, list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            graphicalView.setTransitionName("chart");
        }

        rootLayout.addView(graphicalView);
    }

}
