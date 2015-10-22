package com.seoul.hanokmania.fragments.graph;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by namudak on 2015-10-22.
 */
public class HanokLineChart extends AbstractChart {
    /**
     * Line chart.
     */

    /**
     * Returns the chart name.
     *
     * @return the chart name
     */
    public String getName() {
        return "Sales stacked pie chart";
    }

    /**
     * Returns the chart description.
     *
     * @return the chart description
     */
    public String getDesc() {
        return "The monthly sales for the last 2 years (stacked pie chart)";
    }

    /**
     * Executes the chart.
     *
     * @param context the context
     * @return the built graphicalview
     */


    public GraphicalView getGraphView(Context context, List list) {

        //String[] strAddr= new String[mHanokList.size()];
        String[] strAddr = new String[18];

        // Series Set
        XYSeries plottageSeries = new XYSeries("한옥 대지면적");
        XYSeries buildAreaSeries = new XYSeries("한옥 건축면적");
        for (int i = 0; i < 18; i++) {
            strAddr[i] = "" + i + "th";
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
        multiRenderer.setChartTitle("한옥 대지면적 규모");
        multiRenderer.setXTitle("면적(㎡)");
        multiRenderer.setYTitle("숫자");
        multiRenderer.setZoomButtonsVisible(true);
        for (int i = 0; i < strAddr.length; i++) {
            if (i % 3 == 0) {
                multiRenderer.addXTextLabel(i, strAddr[i]);
            }
        }

        // Adding Plottage and BuildArea renderer to multiRenderer
        multiRenderer.addSeriesRenderer(plottageRenderer);
        multiRenderer.addSeriesRenderer(buildAreaRenderer);

        // Create view
        return ChartFactory.getLineChartView(
                context,
                dataset,
                multiRenderer);

    }

}