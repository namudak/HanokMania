package com.seoul.hanokmania.fragments;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.query.Footman;
import com.seoul.hanokmania.query.HanokQuery;
import com.seoul.hanokmania.query.Sequel;
import com.seoul.hanokmania.views.adapters.HanokGraphAdapter;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-18.
 */
public class HanokGraphFragment extends Fragment implements
        ExpandableListView.OnChildClickListener {

    private static final String TAG = HanokTextFragment.class.getSimpleName();

    private HanokGraphAdapter mAdapter;
    private ExpandableListView mhanokListView;

    private List mHanokList = null;

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_main, container, false);

        mhanokListView = (ExpandableListView) view.findViewById(R.id.expandable_list);

        mhanokListView.setDividerHeight(2);
        mhanokListView.setClickable(true);

//        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
//        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);

        // Retrieve query result as list
        Sequel aQuery = new Sequel(getContext());

        HanokQuery hanokQuery = new HanokQuery(aQuery);

        Footman footman = new Footman();
        footman.takeQuery(hanokQuery);

        List list= footman.placeQueries();


        mAdapter = new HanokGraphAdapter((ArrayList)list.get(0), (ArrayList)list.get(1));

        mAdapter.setInflater(
                inflater,
                getActivity());
        mhanokListView.setAdapter(mAdapter);
        mhanokListView.setOnChildClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Pull to refresh 동작시 처리 구현

                // refresh 애니메이션 종료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(getContext(), "Clicked On Child",
                Toast.LENGTH_SHORT).show();
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private GraphicalView getLineGraph() {

        //String[] strAddr= new String[mHanokList.size()];
        String[] strAddr= new String[18];

        // Series Set
        XYSeries plottageSeries = new XYSeries("한옥 대지면적");
        XYSeries buildAreaSeries = new XYSeries("한옥 건축면적");
        for( int i = 0; i< 18; i++) {
            strAddr[i] = ""+ i+ "th";
            plottageSeries.add(i, (float) (i * 2));
            buildAreaSeries.add(i, (float) i);
        }

        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Add series to the dataset
        dataset.addSeries(plottageSeries);
        dataset.addSeries(buildAreaSeries);

        // Now we create Plottage Renderer
        XYSeriesRenderer plottageRenderer = new XYSeriesRenderer();
        plottageRenderer.setLineWidth(2);
        plottageRenderer.setColor(Color.YELLOW);
        plottageRenderer.setDisplayBoundingPoints(true);
        plottageRenderer.setPointStyle(PointStyle.POINT);
        plottageRenderer.setPointStrokeWidth(3);

        // Now we create BuildArea Renderer
        XYSeriesRenderer buildAreaRenderer = new XYSeriesRenderer();
        buildAreaRenderer.setLineWidth(2);
        buildAreaRenderer.setColor(Color.RED);
        buildAreaRenderer.setDisplayBoundingPoints(true);
        buildAreaRenderer.setPointStyle(PointStyle.POINT);
        buildAreaRenderer.setPointStrokeWidth(3);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.LTGRAY);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("한옥 대지/건축 면적");
        multiRenderer.setXTitle("지역(동)");
        multiRenderer.setYTitle("면적(㎡)");
        multiRenderer.setZoomButtonsVisible(true);
        for( int i= 0;i<strAddr.length;i++){
            if (i % 3 == 0) {
                multiRenderer.addXTextLabel(i, strAddr[i]);
            }
        }

        // Adding Plottage and BuildArea renderer to multiRenderer
        multiRenderer.addSeriesRenderer(plottageRenderer);
        multiRenderer.addSeriesRenderer(buildAreaRenderer);

        // Create view
//        return ChartFactory.getLineChartView(
//                getActivity(),
//                dataset,
//                multiRenderer);

        return ChartFactory.getBarChartView(getActivity(),
                dataset,
                multiRenderer,
                BarChart.Type.DEFAULT);

    }

//    private GraphicalView getPieGraph() {
//
//        int[] COLORS = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA, Color.CYAN };
//
//        double[] VALUES = new double[] { 10, 11, 12, 13 };
//
//        String[] NAME_LIST = new String[] { "A", "B", "C", "D" };
//
//        CategorySeries mSeries = new CategorySeries("");
//
//        DefaultRenderer mRenderer = new DefaultRenderer();
//
//        mRenderer.setApplyBackgroundColor(true);
//        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
//        mRenderer.setChartTitleTextSize(20);
//        mRenderer.setLabelsTextSize(15);
//        mRenderer.setLegendTextSize(15);
//        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
//        mRenderer.setZoomButtonsVisible(true);
//        mRenderer.setStartAngle(90);
//
//        for (int i = 0; i < VALUES.length; i++) {
//            mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);
//            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
//            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
//            mRenderer.addSeriesRenderer(renderer);
//        }
//
//        return ChartFactory.getBarChartView(getActivity(),
//                dataset,
//                multiRenderer,
//                BarChart.Type.DEFAULT);

//        protected void onResume() {
//            super.onResume();
//            if (mChartView == null) {
//                LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
//                mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
//                mRenderer.setClickEnabled(true);
//                mRenderer.setSelectableBuffer(10);
//
//                mChartView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
//
//                        if (seriesSelection == null) {
//                            Toast.makeText(AChartEnginePieChartActivity.this,"No chart element was clicked",Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(AChartEnginePieChartActivity.this,"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//                mChartView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
//                        if (seriesSelection == null) {
//                            Toast.makeText(AChartEnginePieChartActivity.this,"No chart element was long pressed", Toast.LENGTH_SHORT);
//                            return false;
//                        } else {
//                            Toast.makeText(AChartEnginePieChartActivity.this,"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);
//                            return true;
//                        }
//                    }
//                });
//                layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
//            }
//            else {
//                mChartView.repaint();
//            }
//        }
//    }
}
