package com.seoul.hanokmania.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seoul.hanokmania.R;
import com.seoul.hanokmania.models.HanokItem;
import com.seoul.hanokmania.provider.HanokContract;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by namudak on 2015-10-15.
 */
public class ChartFragment extends Fragment
                            implements View.OnKeyListener{

    private static final String TAG = ListFragment.class.getSimpleName();

    private List<HanokItem> mHanokList = null;

    private LinearLayout mChartContainer;

    private ProgressBar mProgressBar;
    private TextView mProgressBarTextView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        mChartContainer = (LinearLayout) view.findViewById(R.id.chart);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mProgressBarTextView= (TextView) view.findViewById(R.id.progressbar_text_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: Pull to refresh 동작시 처리 구현

                // refresh 애니메이션 종료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mHanokList= new ArrayList<HanokItem>();

        try {
            mHanokList= new ChartTask().execute().get();
        } catch (ExecutionException| InterruptedException ie) {
            ie.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mChartContainer.removeAllViews();
        // Add the view to the linearlayout
        mChartContainer.addView(getLineChart());
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    class ChartTask extends AsyncTask<Void, Void, List> {
        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBarTextView.setText("Retrieving data...Please wait.");
        }

        @Override
        protected List doInBackground(Void... params) { // 첫번째 인자

            try {
                HanokContract.setHanokContract("hanok");
                Uri uri = HanokContract.CONTENT_URI;
                String[] projection= new String[] {
                        HanokContract.HanokCol.ADDR,
                        HanokContract.HanokCol.PLOTTAGE,
                        HanokContract.HanokCol.BUILDAREA
                };

                Cursor cursor= getContext().getContentResolver().query(
                        uri,
                        projection,
                        null,
                        null,
                        null
                );

                String[] val= new String[6];
                while(cursor.moveToNext()) {
                    val[1]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.ADDR));
                    val[2]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.PLOTTAGE));
                    val[3]= cursor.getString(cursor.getColumnIndexOrThrow(
                            HanokContract.HanokCol.BUILDAREA));
                    val[0]= "";val[4]= ""; val[5]= "";
                    mHanokList.add(new HanokItem(val));
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return mHanokList;
        }

        // publishUpdate로만 호출
        @Override
        protected void onProgressUpdate(Void... values) { // 두번째 인자
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List list) { // 세번째 인자

            mProgressBar.setVisibility(View.GONE);
            mProgressBarTextView.setText("");

        }
    }
    private GraphicalView getLineChart() {

        String[] strAddr= new String[mHanokList.size()];

        // Series Set
        XYSeries plottageSeries = new XYSeries("한옥 대지면적");
        XYSeries buildAreaSeries = new XYSeries("한옥 건축면적");
        for( int i = 0; i< mHanokList.size(); i++) {
            strAddr[i] = mHanokList.get(i).getADDR();
            plottageSeries.add(i, Float.parseFloat(mHanokList.get(i).getPLOTTAGE()));
            buildAreaSeries.add(i, Float.parseFloat(mHanokList.get(i).getBUILDAREA()));
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
            if (i % 30 == 0) {
                multiRenderer.addXTextLabel(i, strAddr[i]);
            }
        }

        // Adding Plottage and BuildArea renderer to multiRenderer
        multiRenderer.addSeriesRenderer(plottageRenderer);
        multiRenderer.addSeriesRenderer(buildAreaRenderer);

        // Create view
//        return ChartFactory.getLineChartView(
//                getContext(),
//                dataset,
//                multiRenderer);
        return ChartFactory.getBarChartView(getActivity(),
                dataset,
                multiRenderer,
                BarChart.Type.DEFAULT);
    }

}
