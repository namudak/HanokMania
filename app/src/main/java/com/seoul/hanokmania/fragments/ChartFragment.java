package com.seoul.hanokmania.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;

import com.seoul.hanokmania.fragments.dummy.DummyContent;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-15.
 */
public class ChartFragment extends Fragment{
    private Context mConText;
    private List<DummyContent> mDummyList;

    public ChartFragment(Context context, ArrayList<DummyContent> list) {
        mConText = context;
        mDummyList = list;
    }

    public GraphicalView drawLineChart() {

        // x labels(yyyy-mm-dd)
        String[] strYmd = new String[mDummyList.size()];

        // Series Set
        XYSeries goldSeries = new XYSeries("LBMA Gold price");
        XYSeries silverSeries = new XYSeries("LBMA Silver price");
        for( int i = 0; i<mDummyList.size();i++) {
//            strYmd[i] = mDummyList.get(i).getTime();
//            goldSeries.add(i, Float.parseFloat(mDummyList.get(i).getGoldAm()));
//            silverSeries.add(i, 60.0 * Float.parseFloat(mDummyList.get(i).getSilver()));
        }

        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Add series to the dataset
        dataset.addSeries(goldSeries);
        dataset.addSeries(silverSeries);

        // Now we create Gold Renderer
        XYSeriesRenderer goldRenderer = new XYSeriesRenderer();
        goldRenderer.setLineWidth(2);
        goldRenderer.setColor(Color.YELLOW);
        goldRenderer.setDisplayBoundingPoints(true);
        goldRenderer.setPointStyle(PointStyle.POINT);
        goldRenderer.setPointStrokeWidth(3);

        // Now we create Silver Renderer
        XYSeriesRenderer silverRenderer = new XYSeriesRenderer();
        silverRenderer.setLineWidth(2);
        silverRenderer.setColor(Color.RED);
        silverRenderer.setDisplayBoundingPoints(true);
        silverRenderer.setPointStyle(PointStyle.POINT);
        silverRenderer.setPointStrokeWidth(3);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.LTGRAY);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Gold & Silver Chart");
        multiRenderer.setXTitle("Year 2015");
        multiRenderer.setYTitle("Amount in US Dollars");
        multiRenderer.setZoomButtonsVisible(true);
        for( int i= 0;i<strYmd.length;i++){
            if (i % 30 == 0) {
                multiRenderer.addXTextLabel(i, strYmd[i].substring(4, 8));
            }
        }

        // Adding gold and silver renderer to multiRenderer
        multiRenderer.addSeriesRenderer(goldRenderer);
        multiRenderer.addSeriesRenderer(silverRenderer);

        // Create view
        return ChartFactory.getLineChartView(
                mConText,
                dataset,
                multiRenderer);
    }

}
